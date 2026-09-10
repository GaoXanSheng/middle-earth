package net.sevenstars.middleearth.block.special.structureManager.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(UUIDUtil.AUTHLIB_CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius)
    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private Identifier id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;

    // Head positions of the beds found around the nest origin (transient, re-scanned on demand).
    private List<BlockPos> bedPositions = new ArrayList<>();
    // Round-robin assignment of the fetched beds to the living NPCs of this nest.
    private HashMap<UUID, BlockPos> bedAssignments = new HashMap<>();

    public SpawnNestManager(Identifier dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius) {
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        this.respawnEventTriggerTick = 0;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public Identifier getId() {
        return this.id;
    }

    public ArrayList<UUID> getEntityUuids() {
        return entities;
    }

    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public BlockPos getOriginPos() {
        return this.originPos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }

    public void addEntity(LivingEntity entity){
        if(entity.level().isClientSide())
            return;

        UUID uuid = entity.getUUID();
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        this.entities.add(uuid);
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.level().isClientSide() || !this.entities.contains(entity.getUUID()))
            return false;
        this.entities.remove(entity.getUUID());
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.level());
        }
        return true;
    }
    public void removeEntity(Level world, UUID uuid){
        if(world.isClientSide() || !this.entities.contains(uuid))
            return;
        this.entities.remove(uuid);
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
    }

    private void beginRespawnSequence(Level world) {
        this.respawnEventTriggerTick = world.getGameTime();
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, ServerLevel world, BlockPos sourcePos) {
        if(canRespawn(currentTick)){
            respawnAll(structureManagerData, world, sourcePos);
        }
    }

    public void doWellnessCheck(StructureManagerData structureManagerData, Level world, BlockPos sourcePos) {
        if(entities != null && !entities.isEmpty()){
            List<UUID> toRemove = new ArrayList<>();
            for (UUID uuid : entities){ // Wellness check
                var entity = world.getEntity(uuid);
                if(entity == null || !entity.isAlive())
                    toRemove.add(uuid);
            }
            for (UUID uuid : toRemove)
                removeEntity(world, uuid);
        }
    }

    private void respawnAll(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        if(structureManagerData == null)
            return;
        SpawnNestNodeData data = structureManagerData.getNpcSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            int entityAmountToSpawn = pool.getEntityAmount();
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = StructureManagerService.spawnEntity(world, pool, originPos, spawnRadius);
                if(entityToAdd instanceof NpcEntity npcEntity){
                    npcEntity.assignStructureManager((StructureManagerBlockEntity) world.getBlockEntity(structureManagerPos));
                }
                if(entityToAdd != null)
                    addEntity(entityToAdd);
            }
            world.blockEntityChanged(structureManagerPos);
        }
        this.respawnEventTriggerTick = -1;
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            return true;
        }
        return false;
    }

    public void forceRespawn(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        for(var uuid : getEntityUuids()){
            if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                livingEntity.setRemoved(Entity.RemovalReason.DISCARDED);
                MiddleEarth.LOGGER.logDebugMsg("Removed %s".formatted(uuid));
            }
        }
        entities = new ArrayList<UUID>();

        refreshBeds(structureManagerData, world);
        respawnAll(structureManagerData, world, structureManagerPos);
    }

    public void refreshBeds(StructureManagerData structureManagerData, Level world){
        bedPositions.clear();
        SpawnNestNodeData nodeData = structureManagerData == null ? null : structureManagerData.getNpcSpawnNest(id);
        int bedRadius = nodeData != null ? nodeData.getBedRadius() : 10;

        // Only the head block (BlockType.FIRST) of each bed counts as one bed.
        for (BlockPos pos : BlockPos.betweenClosed(
                originPos.offset(-bedRadius, -2, -bedRadius),
                originPos.offset(bedRadius, 3, bedRadius))) {
            var blockState = world.getBlockState(pos);
            if(blockState.getBlock() instanceof BedBlock
                    && BedBlock.getBlockType(blockState) == DoubleBlockCombiner.BlockType.FIRST){
                bedPositions.add(pos.immutable());
            }
        }
    }

    public List<BlockPos> getBedPositions() {
        return bedPositions;
    }

    /**
     * Round-robins the fetched beds to the living NPCs of this nest. More NPCs than beds
     * means beds are shared; more beds than NPCs means surplus beds stay unassigned.
     */
    public void distributeBeds(Level world) {
        bedAssignments.clear();
        if(bedPositions.isEmpty() || entities.isEmpty())
            return;

        int index = 0;
        for (UUID uuid : entities) {
            if (world.getEntity(uuid) instanceof NpcEntity) {
                bedAssignments.put(uuid, bedPositions.get(index % bedPositions.size()));
                index++;
            }
        }
        MiddleEarth.LOGGER.logDebugMsg("Spawn nest %s distributed %d bed(s) to %d npc(s)".formatted(id, bedPositions.size(), index));
    }

    public BlockPos getBedAssignment(UUID entityUuid) {
        return bedAssignments.get(entityUuid);
    }
}

