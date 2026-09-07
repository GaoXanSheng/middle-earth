package net.sevenstars.middleearth.block.special.structureManager.nest;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class StructureNestBlockEntity extends BlockEntity implements ExtendedMenuProvider<StructureNestScreenData> {
    private static final String ID = "structure_nest";

    private enum SyncedData {
        MANAGER_ID("%s.ManagerId".formatted(ID)),
        NEST_ID("%s.NestId".formatted(ID)),
        SPAWN_RADIUS("%s.SpawnRadius".formatted(ID)),
        IS_ENABLED("%s.IsEnabled".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }
    @Nullable
    protected Identifier managerId;
    @Nullable
    protected Identifier nestId;
    protected int spawnRadius;
    protected boolean isEnabled;

    protected int fails = 0;
    boolean initialized = false;

    public StructureNestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_NEST, pos, state);
    }

    @Override
    public StructureNestScreenData getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return new StructureNestScreenData(this.worldPosition,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                spawnRadius,
                isEnabled
            );
    }

    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new StructureNestScreenHandler(syncId, playerInventory, new StructureNestScreenData(this.worldPosition,
                Optional.ofNullable(this.managerId),
                Optional.ofNullable(this.nestId),
                this.spawnRadius,
                this.isEnabled));
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        Optional<Identifier> managerId = view.read(SyncedData.MANAGER_ID.name, Identifier.CODEC);
        managerId.ifPresent(identifier -> this.managerId = identifier);
        Optional<Identifier> nestId = view.read(SyncedData.NEST_ID.name, Identifier.CODEC);
        nestId.ifPresent(identifier -> this.nestId = identifier);
        spawnRadius = view.getIntOr(SyncedData.SPAWN_RADIUS.name, 0);
        isEnabled = view.getBooleanOr(SyncedData.IS_ENABLED.name, false);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if(nestId != null)
            view.store(SyncedData.NEST_ID.name, Identifier.CODEC, this.nestId);
        if(managerId != null)
            view.store(SyncedData.MANAGER_ID.name, Identifier.CODEC, this.managerId);
        view.store(SyncedData.SPAWN_RADIUS.name, Codec.INT, this.spawnRadius);
        view.store(SyncedData.IS_ENABLED.name, Codec.BOOL, this.isEnabled);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setStructureManagerId(Identifier structureManagerId) {
        this.managerId = structureManagerId;
        updateListeners();
    }

    public void setStructureNestId(Identifier structureNestId) {
        this.nestId = structureNestId;
        updateListeners();
    }

    public void setSpawnRadius(int newRadius) {
        this.spawnRadius = newRadius;
        updateListeners();
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        updateListeners();
    }

    private void updateListeners() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureNestBlockEntity entity) {
        entity.tickEvent(world, blockState);
    }

    private void tickEvent(Level world, BlockState blockState) {
        if(world.isClientSide())
            return;

        if(!blockState.getValue(StructureNestBlock.ENABLED)) {
            fails = 0;
            return;
        }

        if(managerId == null || nestId == null || world.nextSubTickCount() % 20 != 0) // every 1 seconds
            return;

        StructureManagerBlockEntity structureManagerBlockEntity = StructureManagerService.getClosest(world, worldPosition, 20);
        if(structureManagerBlockEntity == null) {
            fails++;
        }
        else {
            if(structureManagerBlockEntity.subscribeNest(this.worldPosition, this.managerId, this.nestId, this.spawnRadius))
            {
                world.destroyBlock(worldPosition, false);
                world.removeBlockEntity(worldPosition);
                initialized = true;
                updateListeners();
            } else {
                fails++;
            }
        }
        if(fails >= 12) {
            world.destroyBlock(worldPosition, false);
            world.removeBlockEntity(worldPosition);
            updateListeners();
        }
    }
}
