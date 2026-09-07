package net.sevenstars.middleearth.resources.datas.biome_events.data;
import net.minecraft.world.phys.Vec3;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SpawnEventDataUtil {
    // # Comparators
    static boolean compareId(NpcEntity entity, Identifier npcTypeToCompare) {
        if(entity == null)
            return false;
        Identifier entityId = entity.getNpcTypeIdentifier();
        if(entityId == null || npcTypeToCompare == null)
            return false;
        return MiddleEarth.compareId(entityId, npcTypeToCompare);
    }

    static boolean compareEntitiesByType(LivingEntity entity, Identifier entityType) {
        if(entity == null)
            return false;
        return MiddleEarth.compareId(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), entityType);
    }

    // Conditions
    static boolean meetEntityThresholdRequirements(WildSpawnEventData data, Level world, BlockPos pos) {
        EntityType<?> targetEntityType = BuiltInRegistries.ENTITY_TYPE.getValue(data.getEntityType());
        int sameEntityDistance = data.getSameEntityLimitDistance().orElse(256);
        int sameEntityAmount = data.getSameEntityLimitAmount().orElse(10);
        boolean sameEntitySurfaceOnly = data.getSameEntitySurfaceOnly().orElse(false);

        AABB searchBox = AABB.ofSize(Vec3.atCenterOf(pos), sameEntityDistance, sameEntityDistance, sameEntityDistance);

        boolean hasNpcTypeLimit = targetEntityType == EntitiesME.NPC && data.getNpcType(null) != null;
        int sameNpcTypeAmount = data.getSameNpcTypeLimitAmount().orElse(5);
        int sameNpcTypeDistance = data.getSameNpcTypeLimitDistance().orElse(128);
        boolean sameNpcTypeSurfaceOnly = data.getSameNpcTypeSurfaceOnly().orElse(false);
        AABB npcSearchBox = hasNpcTypeLimit ? AABB.ofSize(Vec3.atCenterOf(pos), sameNpcTypeDistance, sameNpcTypeDistance, sameNpcTypeDistance) : null;

        int[] counts = new int[2]; // [0] = entity count, [1] = npc type count
        world.getEntities((Entity) null, searchBox, entity -> {
            // Same entity type limit
            boolean isSurface = isSurface(world, entity.blockPosition());
            if (entity.getType() == targetEntityType) {
                if(sameEntitySurfaceOnly && !isSurface)
                    return false;
                counts[0]++;
                if (counts[0] >= sameEntityAmount)
                    return true;
            }
            // Same NPC type limit
            if (hasNpcTypeLimit && entity instanceof NpcEntity npc && npcSearchBox.contains(entity.position()) && SpawnEventDataUtil.compareId(npc, data.getNpcType(null))) {
                if(sameNpcTypeSurfaceOnly && !isSurface)
                    return false;

                counts[1]++;
                return counts[1] >= sameNpcTypeAmount;
            }
            return false;
        });
        // Entity amount exceeded
        if (counts[0] >= sameEntityAmount)
            return false;
        // Same NPC type amount exceeded
        return !hasNpcTypeLimit || counts[1] < sameNpcTypeAmount;
    }

    static boolean meetsStructureManagerClearance(WildSpawnEventData data, Level world, BlockPos pos) {
        int structureManagerDistance = data.getStructureManagerRadiusAvoidance().orElse(64);
        return !StructureManagerService.isClose(world, pos, structureManagerDistance);
    }

    static boolean meetLightLevelRequirement(WildSpawnEventData data, Level world, BlockPos pos) {
        int currentLightLevel = world.getMaxLocalRawBrightness(pos);
        int minimumLight = data.getLightLevelMinimum().orElse(0);
        if(currentLightLevel < minimumLight)
            return false;
        int maximumLight = data.getLightLevelMaximum().orElse(Integer.MAX_VALUE);
        return currentLightLevel < maximumLight;
    }

    static boolean meetWorldHeightRequirement(WildSpawnEventData data, BlockPos pos) {
        int currentY = pos.getY();
        if(currentY < data.getShouldSpawnAbove().orElse(Integer.MIN_VALUE))
            return false;
        return currentY < data.getShouldSpawnBelow().orElse(Integer.MAX_VALUE);
    }

    static boolean meetEnvironmentRequirements(WildSpawnEventData data, Level world, BlockPos pos) {
        boolean requireSky = data.getSkyRequirement().orElse(false);
        boolean requireUnderground = data.getUndergroundRequirement().orElse(false);

        boolean isSurface = isSurface(world, pos);
        boolean isUnderground = isUnderground(world, pos);

        if (requireSky && !isSurface) {
            return false;
        }

        if (requireUnderground && !isUnderground) {
            return false;
        }

        return true;
    }

    public static boolean isSurface(Level world, BlockPos pos) {
        int surfaceY = world.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() >= surfaceY - 1 && pos.getY() <= surfaceY + 2;
    }
    public static boolean isUnderground(Level world, BlockPos pos) {
        int surfaceY = world.getHeight(
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                pos.getX(),
                pos.getZ()
        );
        return pos.getY() < surfaceY - 2;
    }

    static boolean meetNightTimeRequirement(WildSpawnEventData data, Level world) {
        boolean requireNight = data.getNightRequirement().orElse(false);
        return !requireNight || world.isDarkOutside();
    }

    private static boolean meetMinimumSpaceRequirement(WildSpawnEventData data, Level world, BlockPos blockPos) {
        Vec3i size = data.getMinimumSpaceCubeSize().orElse(null);
        if(size == null)
            return true;
        BlockPos max = blockPos.offset(size.getX() - 1, size.getY() - 1, size.getZ() - 1);
        for (BlockPos pos : BlockPos.betweenClosed(blockPos, max)) {
            if (!world.getBlockState(pos).isRedstoneConductor(world, pos)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isConsideredForSpawning(WildSpawnEventData data, Identifier id, Level world, BlockPos blockPos) {
        if(!data.getEntityType().equals(id))
            return false;
        if(!meetLightLevelRequirement(data, world, blockPos))
            return false;
        if(!meetWorldHeightRequirement(data, blockPos))
            return false;
        if(!meetMinimumSpaceRequirement(data, world, blockPos))
            return false;
        if(!meetEnvironmentRequirements(data, world, blockPos))
            return false;
        if(!meetNightTimeRequirement(data, world))
            return false;
        if(!meetEntityThresholdRequirements(data, world, blockPos))
            return false;
        if(!meetsStructureManagerClearance(data, world, blockPos))
            return false;
        return true;
    }

}
