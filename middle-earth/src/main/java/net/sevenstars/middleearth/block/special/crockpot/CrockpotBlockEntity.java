package net.sevenstars.middleearth.block.special.crockpot;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import net.sevenstars.middleearth.recipe.CrockpotRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrockpotBlockEntity extends BlockEntity implements ExtendedMenuProvider<BlockPos>, WorldlyContainer {
    private static final String ID = "crockpot";
    public static final int OUTPUT_SLOT = 4;
    public static final int COOK_TIME = 60;
    private final NonNullList<ItemStack> inventory =
            NonNullList.withSize(5, ItemStack.EMPTY);
    protected final ContainerData propertyDelegate;
    private final RecipeManager.CachedCheck<MultipleStackRecipeInput, ? extends CrockpotRecipe> matchGetter;
    private int progress = 0;
    private RandomSource random;
    private float liquidTopLevel;

    public CrockpotBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, 0.5f);
    }

    public CrockpotBlockEntity(BlockPos pos, BlockState state, float liquidTopLevel) {
        super(null, pos, state);
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress;
                    default -> throw new IllegalStateException("Unexpected value: " + index);
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrockpotBlockEntity.this.progress = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        this.matchGetter = RecipeManager.createCheck(RecipesME.CROCKPOT);
        this.liquidTopLevel = liquidTopLevel;
        random = RandomSource.create();
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        ArrayList<ItemStack> ingredients = new ArrayList<>();
        for(int i = 0; i < blockEntity.inventory.size(); i++) {
            ItemStack ingredient = blockEntity.inventory.get(i);
            if(!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        boolean markDirty = false;
        if (blockEntity.isBoiling() && !world.isClientSide()) {
            blockEntity.randomBubbles();
            ServerLevel serverWorld = (ServerLevel) world;
            MultipleStackRecipeInput recipeInput = new MultipleStackRecipeInput(ingredients);
            RecipeHolder recipeEntry;
            if (ingredients.size() >= 2) {
                recipeEntry = blockEntity.matchGetter.getRecipeFor(recipeInput, serverWorld).orElse(null);
            } else {
                recipeEntry = null;
                blockEntity.progress = Math.max(blockEntity.progress - 1, 0);
            }
            markDirty = true;
            ++blockEntity.progress;
            if (blockEntity.progress >= COOK_TIME) {
                blockEntity.progress = 0;
                craftRecipe(world.registryAccess(), recipeEntry, recipeInput, blockEntity.inventory);
                blockEntity.recipeCraftedSound();
            }
        }

        if (markDirty) {
            setChanged(world, pos, state);
        }

    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, CrockpotBlockEntity blockEntity) {
        if(blockEntity.isCooking())
        {
            double x = (double)pos.getX() + 0.5;
            double y = (double)pos.getY() + 0.5;
            double z = (double)pos.getZ() + 0.5;
            if (blockEntity.random.nextDouble() < 0.12) {
                world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double i = blockEntity.random.nextDouble() * 0.4 - 0.2;
            double j = blockEntity.random.nextDouble() * 0.4 - 0.2;
            world.addParticle(ParticleTypes.BUBBLE, x + i, y, z + j, 0.0, 0.1, 0.0);
        }
    }

    public void randomBubbles() {
        if(isCooking())
        {
            double x = (double)worldPosition.getX() + 0.5;
            double y = (double)worldPosition.getY() + 0.5;
            double z = (double)worldPosition.getZ() + 0.5;
            if (random.nextDouble() < 0.12) {
                level.playSound(null, worldPosition, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double i = random.nextDouble() * 0.4 - 0.2;
            double j = random.nextDouble() * 0.4 - 0.2;
            level.addParticle(ParticleTypes.BUBBLE, x + i, y, z + j, 0.0, 0.1, 0.0);
        }
    }

    private static boolean craftRecipe(RegistryAccess dynamicRegistryManager, @Nullable RecipeHolder<CrockpotRecipe> recipe,
                                       MultipleStackRecipeInput input, NonNullList<ItemStack> inventory) {
        if (recipe != null) {
            ItemStack craftedStack = recipe.value().craft(input, dynamicRegistryManager);
            inventory.set(OUTPUT_SLOT, craftedStack.copy());
            for(int i = 0; i < OUTPUT_SLOT; i++) {
                inventory.set(i, ItemStack.EMPTY);
            }
            return true;
        } else {
            return false;
        }
    }

    public void recipeCraftedSound() {
        double x = (double)worldPosition.getX() + 0.5;
        double y = (double)worldPosition.getY() + 0.5;
        double z = (double)worldPosition.getZ() + 0.5;
        level.playSound(null, worldPosition, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.1F, 0.8F);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new CrockpotScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    public boolean isCooking() {
        return progress > 0;
    }

    public boolean isHanging() {
        if(level != null) {
            BlockState blockState = level.getBlockState(getBlockPos());
            if(blockState == null || blockState.isAir()) return false;
            return blockState.getValue(CrockpotBlock.HANGING);
        }
        return false;
    }

    public boolean isBoiling() {
        return isHanging() && hasOutput();
    }

    public boolean hasOutput() {
        return !getItem(OUTPUT_SLOT).isEmpty();
    }

    public float getLiquidTopLevel() {
        return this.liquidTopLevel;
    }

    public boolean fill(ItemStack itemStack) {
        if(getItem(OUTPUT_SLOT).isEmpty()) {
            if(itemStack.getItem() == Items.WATER_BUCKET) {
                setItem(OUTPUT_SLOT, itemStack);
                return true;
            }
        }
        return false;
    }

    public ItemStack fillBowl(Item remainder) {
        if(hasOutput()) {
            ItemStack outputStack = getItem(OUTPUT_SLOT);
            UseRemainder remainderComponent = outputStack.get(DataComponents.USE_REMAINDER);
            if(remainderComponent != null) {
                ItemStack recipeRemainder = remainderComponent.convertInto().create();
                if (recipeRemainder.getItem() == remainder) {
                    ItemStack result = outputStack.copy();
                    result.setCount(1);
                    outputStack.shrink(1);
                    if(outputStack.getCount() == 0) {
                        outputStack = ItemStack.EMPTY;
                    }
                    setItem(OUTPUT_SLOT, outputStack);
                    System.out.println(getItem(OUTPUT_SLOT));
                    return result;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState oldState) {
        if (this.level != null) {
            List<ItemStack> items = new ArrayList<>(getList());
            items.removeLast();
            for (ItemStack item : items) {
                Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), item);
            }
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] slots = new int[inventory.size()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = i;
        }
        return slots;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return false; // Do not extract liquid output into hopper.
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    public List<ItemStack> getList() {
        return new ArrayList<>(this.inventory.stream().toList());
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(inventory, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return worldPosition;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, this.inventory);
        view.putInt(ID + ".progress", this.progress);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.inventory.clear();
        ContainerHelper.loadAllItems(view, this.inventory);
        this.progress = view.getIntOr(ID + ".progress", 0);
    }
}
