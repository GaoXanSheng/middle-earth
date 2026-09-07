package net.sevenstars.middleearth.block.special.structureManager;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
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
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureNestList;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class StructureManagerBlockEntity extends BlockEntity implements ExtendedMenuProvider<StructureManagerScreenData> {
    private static final String ID = "structure_manager";

    private enum SyncedData {
        ENABLED("%s.Enabled".formatted(ID)),
        TO_INITIALIZE("%s.ToInitialize".formatted(ID)),
        SPAWN_NEST_LIST("%s.Nests".formatted(ID)),
        IDENTIFIER("%s.Identifier".formatted(ID));

        public final String name;
        SyncedData(String name){
            this.name = name;
        }
    }

    // Synced Data
    private boolean enabled;
    private boolean toInitialize;
    @Nullable
    protected Identifier structureManagerIdentifier;
    private StructureNestList structureNestList;
    private boolean wellnessChecked;

    boolean firstTick = true;
    // Runtime
    private StructureManagerData managerData;
    private boolean worldWasSet = false;
    private boolean registered = false;

    @Override
    public void setRemoved() {
        StructureManagerService.unregister(this);
        super.setRemoved();
    }

    public StructureManagerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STRUCTURE_MANAGER, pos, state);
        // Default values
        this.enabled = false;
        this.toInitialize = false;
        this.structureManagerIdentifier = null;
        this.structureNestList = null;
        this.firstTick = true;
        this.wellnessChecked = false;
    }

    // region [Basic Overrides]
    public void updateData(Identifier structureManagerId, boolean isActive, boolean toInitialize) {
        this.structureManagerIdentifier = structureManagerId;
        this.enabled = isActive;
        this.toInitialize = toInitialize;
        updateListeners();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.%s.%s".formatted(MiddleEarth.MOD_ID, ID));
    }

    @Override
    public StructureManagerScreenData getScreenOpeningData(ServerPlayer serverPlayerEntity) {
        return new StructureManagerScreenData(this.worldPosition, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier));
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
        return new StructureManagerScreenHandler(syncId, playerInventory,
                new StructureManagerScreenData(this.worldPosition, this.enabled, this.toInitialize, Optional.ofNullable(this.structureManagerIdentifier))
        );
    }

    public static void tickEvent(Level world, BlockPos blockPos, BlockState blockState, StructureManagerBlockEntity entity) {
        entity.tickEvent(world, blockPos, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        view.putBoolean(SyncedData.ENABLED.name, this.enabled);
        view.putBoolean(SyncedData.TO_INITIALIZE.name, this.toInitialize);
        if(structureManagerIdentifier != null)
            view.store(SyncedData.IDENTIFIER.name, Identifier.CODEC, this.structureManagerIdentifier);
        if(structureNestList != null)
            view.store(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC, this.structureNestList);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.enabled = view.getBooleanOr(SyncedData.ENABLED.name, false);
        this.toInitialize = view.getBooleanOr(SyncedData.TO_INITIALIZE.name, false);
        view.read(SyncedData.IDENTIFIER.name, Identifier.CODEC)
                .ifPresent(x -> structureManagerIdentifier = x);
        view.read(SyncedData.SPAWN_NEST_LIST.name, StructureNestList.CODEC)
                .ifPresent(x -> structureNestList = x);
    }
    // endregion

    @Override
    public void setLevel(Level world) {
        super.setLevel(world);
        worldWasSet = true;
    }

    public void showAllEntities() {
        if(structureNestList == null)
            return;
        for(SpawnNestManager nest : structureNestList.getManagers()){
            for(UUID uuid : nest.getEntityUuids()){
                if(level.getEntity(uuid) instanceof LivingEntity livingEntity){
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10*20));
                }
            }
        }
    }

    public void respawnAllEntities() {
        if(structureNestList == null)
            return;
        if(level == null || level.isClientSide())
            return;
        for(var nest : structureNestList.getManagers()){
            nest.forceRespawn(managerData, (ServerLevel) level, worldPosition);
        }
    }

    public boolean subscribeNest(BlockPos nestPos, Identifier managerId, Identifier nestId, int spawnRadius) {
        if(!enabled || managerId == null || structureManagerIdentifier == null || managerData == null || managerId.compareTo(this.structureManagerIdentifier) != 0)
            return false;

        SpawnNestNodeData data = managerData.getNpcSpawnNest(nestId);
        SpawnNestManager manager = new SpawnNestManager(data, nestPos, spawnRadius);
        this.structureNestList.addNest(manager);
        return true;
    }

    public static void triggerDeathSignal(BlockPos pos, LivingEntity entity) {
        if(entity.level().isClientSide())
            return;
        StructureManagerBlockEntity blockEntity = (StructureManagerBlockEntity) entity.level().getBlockEntity(pos);
        if(blockEntity != null && !blockEntity.isRemoved()){
            blockEntity.structureNestList.computeDeath(entity);
            blockEntity.level.blockEntityChanged(pos);
        }
    }

    private void tickEvent(Level world, BlockPos blockPos, BlockState blockState) {
        if(!world.isClientSide() && worldWasSet){
            tryToInitializeManager(world);
            this.worldWasSet = false;
        }

        if (!world.isClientSide() && !this.registered) {
            StructureManagerService.register(this);
            this.registered = true;
        }

        if(!enabled)
            return;

        ServerLevel serverWorld = (ServerLevel) world;
        if(structureNestList == null)
            return;

        long timeOfDay = serverWorld.getGameTime() % 24000;
        long gameTick = serverWorld.getGameTime();
        if((timeOfDay > 0 && timeOfDay < 11000) || (timeOfDay > 12000 && timeOfDay < 23000) && wellnessChecked)
            wellnessChecked = false;

        boolean haveToDoWellnessCheck = (timeOfDay > 11000 && timeOfDay < 12000) || (timeOfDay >= 23000) && !wellnessChecked;
        for(SpawnNestManager data : structureNestList.getManagers()){
            if(managerData == null)
                managerData = StructureManagerService.getStructureManagerData(serverWorld, structureManagerIdentifier);
            if(haveToDoWellnessCheck){
                data.doWellnessCheck(managerData, serverWorld, blockPos);
            }
            data.tick(managerData, gameTick, serverWorld, blockPos);
        }
        if(haveToDoWellnessCheck && !wellnessChecked)
            wellnessChecked = true;
    }

    private void tryToInitializeManager(Level world){
        if(world.isClientSide())
            return;
        if(!toInitialize || enabled)
            return;
        if(structureManagerIdentifier == null)
            return;

        this.managerData = StructureManagerService.getStructureManagerData(world, structureManagerIdentifier);
        if(structureNestList == null)
            this.structureNestList = new StructureNestList();
        if(managerData == null) {
            return;
        };

        this.toInitialize = false;
        this.enabled = true;
    }

    public void setInitializationState(boolean toInitialize) {
        this.toInitialize = toInitialize;
        updateListeners();
    }

    public void setActiveState(boolean activate) {
        this.enabled = activate;
        updateListeners();
    }

    public void setStructureManagerId(Identifier identifier) {
        this.structureManagerIdentifier = identifier;
        updateListeners();
    }

    private void updateListeners() {
        this.setChanged();
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    public void fetchBeds(){
        // TODO : Fetch all beds surrounding the nodes, making sure there's no duplicate
        StructureManagerData managerData = getLevel().registryAccess().lookup(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).get().getValue(structureManagerIdentifier);
        for(SpawnNestManager data : structureNestList.getManagers()) {
            SpawnNestNodeData nodeData = managerData.getNpcSpawnNest(data.getId());
            if(nodeData == null)
                continue;

            int bedRadius = nodeData.getBedRadius();
            BlockPos origin = data.getOriginPos();
        }
    }

    public void redistributeBeds(){
        // TODO : Redistribute beds to the nest nodes
        // TODO : Makes sure the beds are still distributed to the correct npcs
    }
}
