package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.*;

public class BiomeEventDataLookup {
    public static HashMap<EntityType<?>, Set<UUID>> entities = new HashMap<>();

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForBiome(Level world, Holder<Biome> biome, NpcEntity entity) {
        Identifier biomeEventId = Identifier.parse(biome.getRegisteredName());
        BiomeEventData eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.BIOME_EVENT).getValue(biomeEventId);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.BIOME_EVENT).getValue(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.BIOME_EVENT).getValue(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static BiomeEventData.ContextualizedBiomeData findNpcDataForStructure(Level world, Identifier structure, NpcEntity entity) {
        BiomeEventData eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).getValue(structure);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).getValue(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.registryAccess().lookupOrThrow(DynamicRegistriesME.STRUCTURE_EVENT).getValue(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }

    public static boolean canEntitySpawn(ServerLevel world, Holder<Biome> biome, BlockPos pos, EntityType<?> type, RandomSource random) {
        Holder.Reference<BiomeEventData> dataRef = world.registryAccess().lookupOrThrow(DynamicRegistriesME.BIOME_EVENT).get(MiddleEarth.fetchId(biome.getRegisteredName())).orElse(null);
        if(dataRef == null)
            return true;
        BiomeEventData data = dataRef.value();
        boolean canSpawn = data.canSpawn(type, world, pos, random);

        if(!canSpawn)
            return false;
        if(type.getCategory() != MobCategory.MONSTER)
            return true;

        if(!entities.containsKey(type)){
            entities.put(type, new HashSet<>());
        }
        return canSpawn(type);
    }

    public static boolean canSpawn(EntityType<?> type) {
        return entities.getOrDefault(type, Collections.emptySet()).size() < ModServerConfigs.GLOBAL_MOB_CAP;
    }

    public static void addEntity(LivingEntity entity){
        if (!entity.level().dimension().equals(ModDimensions.ME_WORLD_KEY)) {
            return;
        }

        EntityType<?> type = entity.getType();
        UUID uuid = entity.getUUID();
        if(type.getCategory() != MobCategory.MONSTER)
            return;
        if(entity instanceof Mob mobEntity && mobEntity.isPersistenceRequired()){
            return;
        }
        entities.computeIfAbsent(type, t -> new HashSet<>()).add(uuid);
    }

    public static void removeEntity(EntityType<?> type, UUID uuid){
        if(type.getCategory() != MobCategory.MONSTER) return;
        Set<UUID> set = entities.get(type);
        if (set == null) return;
        set.remove(uuid);
        if (set.isEmpty()) entities.remove(type);
    }
}
