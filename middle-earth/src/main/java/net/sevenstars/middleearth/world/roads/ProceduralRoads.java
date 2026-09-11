package net.sevenstars.middleearth.world.roads;

import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.config.ModServerConfigs;

/**
 * Stateless helpers baking roads into the chunk surface generation.
 * Block picks are coordinate-hashed so identical (x, z) always yields identical blocks.
 */
public class ProceduralRoads {

    /** Single query reused by buildSurface for the height, surface and bridge passes. */
    public static RoadNetwork.RoadHit queryRoad(int x, int z) {
        if(!ModServerConfigs.ENABLE_ROADS) return null;
        return RoadNetwork.query(x, z);
    }

    public static float applyHeightModifier(int x, int z, float height) {
        if(!ModServerConfigs.ENABLE_ROADS) return height;
        return applyHeightModifier(x, z, height, RoadNetwork.query(x, z));
    }

    public static float applyHeightModifier(int x, int z, float height, RoadNetwork.RoadHit hit) {
        if(hit == null) return height;
        float halfWidth = hit.width() / 2;
        float falloff = hit.width() * RoadNetwork.INFLUENCE_MULTIPLIER - halfWidth;
        float strength = hit.distance() <= halfWidth ? 1 : Mth.clamp(1 - (hit.distance() - halfWidth) / falloff, 0, 1);
        return Mth.lerp(strength, height, hit.relHeight());
    }

    public static BlockState getRoadSurfaceBlock(int x, int z, RoadNetworkData.RoadStyle style) {
        if(!ModServerConfigs.ENABLE_ROADS) return Blocks.DIRT.defaultBlockState();
        int hash = x * 374761393 + z * 668265263;
        hash = (hash ^ (hash >> 13)) * 1274126177;
        hash = hash ^ (hash >> 16);
        int roll = Math.floorMod(hash, 100);
        return switch (style) {
            case SHIRE -> roll < 30 ? Blocks.PACKED_MUD.defaultBlockState()
                    : roll < 55 ? Blocks.ROOTED_DIRT.defaultBlockState()
                    : roll < 75 ? ModBlocks.COBBLY_DIRT.defaultBlockState()
                    : roll < 90 ? Blocks.COARSE_DIRT.defaultBlockState()
                    : Blocks.GRASS_BLOCK.defaultBlockState();
            case GONDOR -> roll < 35 ? ModBlocks.DRY_DIRT.defaultBlockState()
                    : roll < 55 ? Blocks.PACKED_MUD.defaultBlockState()
                    : roll < 75 ? ModBlocks.COBBLY_DIRT.defaultBlockState()
                    : roll < 90 ? Blocks.ROOTED_DIRT.defaultBlockState()
                    : ModBlocks.GRASSY_DIRT.defaultBlockState();
            case ROHAN -> roll < 30 ? ModBlocks.DRY_DIRT.defaultBlockState()
                    : roll < 55 ? Blocks.COARSE_DIRT.defaultBlockState()
                    : roll < 75 ? Blocks.GRASS_BLOCK.defaultBlockState()
                    : roll < 90 ? Blocks.ROOTED_DIRT.defaultBlockState()
                    : ModBlocks.DIRTY_ROOTS.defaultBlockState();
            case DALE -> roll < 30 ? ModBlocks.COBBLY_DIRT.defaultBlockState()
                    : roll < 55 ? GenericBlockSets.MIXED_STONES.blockSet.base().defaultBlockState()
                    : roll < 70 ? GenericBlockSets.MOSSY_MIXED_STONES.blockSet.base().defaultBlockState()
                    : roll < 85 ? Blocks.GRASS_BLOCK.defaultBlockState()
                    : Blocks.COARSE_DIRT.defaultBlockState();
            case MORDOR -> roll < 30 ? StoneBlockSets.ASHENSTONE_SET.baseBlocks.base().defaultBlockState()
                    : roll < 55 ? ModBlocks.ASHEN_DIRT.defaultBlockState()
                    : roll < 75 ? ModBlocks.FOUL_DIRT.defaultBlockState()
                    : roll < 90 ? ModBlocks.ASHEN_GRAVEL.defaultBlockState()
                    : ModBlocks.COARSE_PEAT.defaultBlockState();
            case WLR -> roll < 40 ? ModBlocks.LOAM_PATH.defaultBlockState()
                    : roll < 70 ? ModBlocks.LOAM.defaultBlockState()
                    : roll < 85 ? ModBlocks.COARSE_LOAM.defaultBlockState()
                    : Blocks.GRASS_BLOCK.defaultBlockState();
        };
    }

    public static BlockState getRoadBridgeBlock(RoadNetworkData.RoadStyle style) {
        if(!ModServerConfigs.ENABLE_ROADS) return Blocks.SPRUCE_PLANKS.defaultBlockState();
        if(style == RoadNetworkData.RoadStyle.MORDOR) {
            return WoodBlockSets.MIRKWOOD_SET.planksBlocks.base().defaultBlockState();
        }
        return Blocks.SPRUCE_PLANKS.defaultBlockState();
    }
}
