package net.sevenstars.middleearth.block.special.plate;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity implements ContainerSingleItem.BlockContainerSingleItem {
    private ItemStack food = ItemStack.EMPTY;
    private ResourceKey lootTable;
    private long lootTableSeed;
    private boolean blockPlaced = false;

    public PlateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PLATE, pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.store("placed", Codec.BOOL, blockPlaced);

        if (!this.writeLootTableToData(view) && !this.food.isEmpty()) {
            view.store("item", ItemStack.CODEC, this.food);
        }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.food.copyAndClear();
        this.blockPlaced = view.getBooleanOr("placed", false);
        if (!this.readLootTableFromData(view)) {
            this.food = view.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        } else {
            this.food = ItemStack.EMPTY;
        }
    }

    public void setLootTable(ResourceKey<LootTable> lootTable, long seed) {
        this.lootTable = lootTable;
        this.lootTableSeed = seed;
    }

    private boolean readLootTableFromData(ValueInput view) {
        this.lootTable = (ResourceKey)view.read("LootTable", LootTable.KEY_CODEC).orElse(null);
        this.lootTableSeed = view.getLongOr("LootTableSeed", 0L);
        return this.lootTable != null;
    }

    private boolean writeLootTableToData(ValueOutput view) {
        if (this.lootTable == null) {
            return false;
        } else {
            view.store("LootTable", LootTable.KEY_CODEC, this.lootTable);
            if (this.lootTableSeed != 0L) {
                view.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public void setBlockPlaced() {
        blockPlaced = true;
    }

    public boolean isBlockPlaced() {
        return blockPlaced;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, PlateBlockEntity blockEntity) {
        if(blockEntity.blockPlaced) {
            blockEntity.generateItem((ServerLevel) world);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public BlockEntity getContainerBlockEntity() {
        return this;
    }

    @Override
    public ItemStack getTheItem() {
        return food;
    }

    @Override
    public void setTheItem(ItemStack stack) {
        this.food = stack;
        update();
    }

    public void generateItem(ServerLevel world) {
        if (this.lootTable != null && blockPlaced) {
            LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(this.lootTable);

            LootParams lootWorldContext = (new LootParams.Builder(world)).create(LootContextParamSets.EMPTY);
            ObjectArrayList<ItemStack> lootList = lootTable.getRandomItems(lootWorldContext, this.lootTableSeed);
            ItemStack itemLoot = ItemStack.EMPTY;
            if(!lootList.isEmpty()) itemLoot = lootList.get(world.getRandom().nextInt(lootList.size()));

            this.food = itemLoot;
            this.lootTable = null;
            blockPlaced = false;
            update();
        }
    }

    public void update() {
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }
}
