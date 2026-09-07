package net.sevenstars.middleearth.block.special.wood_pile;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreenHandler;
import net.sevenstars.middleearth.utils.ImplementedInventory;
import org.jetbrains.annotations.Nullable;

import static net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlock.STAGE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class WoodPileBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider, ImplementedInventory {

    private static final String ID = "wood_pile";
    private NonNullList<ItemStack> inventory = NonNullList.withSize(9, ItemStack.EMPTY);

    public WoodPileBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WOOD_PILE, pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + "." + ID);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("me.container.wood_pile");
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new WoodPileScreenHandler(syncId, playerInventory, this);
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return null;
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(view)) {
            ContainerHelper.loadAllItems(view, this.inventory);
        }
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!this.trySaveLootTable(view)) {
            ContainerHelper.saveAllItems(view, this.inventory);
        }
    }

    public void setInventory(NonNullList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        if(this.hasAmount(9)){
            this.getLevel().setBlockAndUpdate(this.getBlockPos(), ModDecorativeBlocks.WOOD_PILE.defaultBlockState()
                    .setValue(STAGE, 3)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, this.getLevel().getBlockState(this.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)));
        } else if (this.hasAmount(5)){
            this.getLevel().setBlockAndUpdate(this.getBlockPos(), ModDecorativeBlocks.WOOD_PILE.defaultBlockState()
                    .setValue(STAGE, 2)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, this.getLevel().getBlockState(this.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)));
        } else if (this.isEmpty()){
            this.getLevel().setBlockAndUpdate(this.getBlockPos(), ModDecorativeBlocks.WOOD_PILE.defaultBlockState()
                    .setValue(STAGE, 0)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, this.getLevel().getBlockState(this.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)));
        } else if (!this.hasAmount(5) && !isEmpty()){
            this.getLevel().setBlockAndUpdate(this.getBlockPos(), ModDecorativeBlocks.WOOD_PILE.defaultBlockState()
                    .setValue(STAGE, 1)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, this.getLevel().getBlockState(this.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)));
        }
    }
    @Override
    public ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);
        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    public boolean hasAmount(int amount) {
        int result = 0;
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                result++;
            }
        }
        return result >= amount;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }
}