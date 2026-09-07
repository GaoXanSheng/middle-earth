package net.sevenstars.middleearth.client;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;

import java.util.List;

public class BlockColorsME {

    public static void initializeBlockColors() {
        registerConstant(0x677006, WoodBlockSets.BEECH_SET.leaves);
        registerConstant(0x758f28, WoodBlockSets.LARCH_SET.leaves);
        registerConstant(0x628842, WoodBlockSets.CHESTNUT_SET.leaves);
        registerConstant(0x46684a, WoodBlockSets.FIR_SET.leaves);
        registerConstant(0x3f5f3f, WoodBlockSets.HOLLY_SET.leaves);
        registerConstant(0xffe45a, ModNatureBlocks.FLOWERING_MALLORN_LEAVES);
        registerConstant(0xffe45a, WoodBlockSets.MALLORN_SET.leaves);
        registerConstant(0x9c802a, WoodBlockSets.MAPLE_SET.leaves);
        registerConstant(0x0e260c, WoodBlockSets.MIRKWOOD_SET.leaves);
        registerConstant(0x6c8031, WoodBlockSets.PALM_SET.leaves);
        registerConstant(0x41461c, WoodBlockSets.PINE_SET.leaves);
        registerConstant(0x324931, WoodBlockSets.BLACK_PINE_SET.leaves);
        registerConstant(0x415730, WoodBlockSets.WILLOW_SET.leaves);

        registerConstant(0x876b00, ModNatureBlocks.DRY_LARCH_LEAVES);
        registerConstant(0x5b4f2c, ModNatureBlocks.DRY_PINE_LEAVES);
        registerConstant(0x244324, ModNatureBlocks.LEBETHRON_LEAVES);
        registerConstant(0x925121, ModNatureBlocks.ORANGE_MAPLE_LEAVES);
        registerConstant(0x913720, ModNatureBlocks.RED_MAPLE_LEAVES);
        registerConstant(0x926821, ModNatureBlocks.YELLOW_MAPLE_LEAVES);

        BlockColorRegistry.register(List.of(grassTint()), ModNatureBlocks.WILD_GRASS, ModNatureBlocks.LARGE_BUSH, ModNatureBlocks.GRASS_TUFT, ModNatureBlocks.WHEATGRASS,
                ModNatureBlocks.BRACKEN, ModNatureBlocks.GIANT_BUTTERBUR,
                ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT_STAIRS,
                ModBlocks.CHALKSOIL_GRASS_BLOCK, ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.SILT_GRASS_BLOCK,
                ModBlocks.GRASSY_CHALKSOIL, ModBlocks.GRASSY_CHALKSOIL_SLAB, ModBlocks.GRASSY_CHALKSOIL_STAIRS,
                ModBlocks.GRASSY_LOAM, ModBlocks.GRASSY_LOAM_SLAB, ModBlocks.GRASSY_LOAM_STAIRS,
                ModBlocks.GRASSY_PEAT, ModBlocks.GRASSY_PEAT_SLAB, ModBlocks.GRASSY_PEAT_STAIRS,
                ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_SILT_SLAB, ModBlocks.GRASSY_SILT_STAIRS,
                ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS_STAIRS,
                ModBlocks.TURF, ModBlocks.TURF_SLAB, ModBlocks.TURF_STAIRS, ModBlocks.TURF_VERTICAL_SLAB,
                ModNatureBlocks.FOREST_MOSS, ModNatureBlocks.FOREST_MOSS_BLOCK, ModNatureBlocks.FOREST_MOSS_CARPET,
                ModNatureBlocks.DUCKWEED, ModNatureBlocks.CLOVERS, ModNatureBlocks.MEADOWGRASS,
                ModNatureBlocks.SPARSE_GRASS, ModNatureBlocks.NETTLES, ModNatureBlocks.THISTLE,
                ModNatureBlocks.SMALL_LILY_PADS, ModNatureBlocks.SMALL_FLOWERING_LILY_PADS,
                ModNatureBlocks.LILY_PADS, ModNatureBlocks.FLOWERING_LILY_PADS,
                ModNatureBlocks.LARGE_LILY_PAD, ModNatureBlocks.LARGE_FLOWERING_LILY_PAD);

        BlockColorRegistry.register(List.of(foliageTint()), ModNatureBlocks.FALLEN_LEAVES, WoodBlockSets.BEECH_SET.leaves);
    }

    private static void registerConstant(int color, Block... blocks) {
        BlockColorRegistry.register(List.of(BlockTintSources.constant(color)), blocks);
    }

    private static BlockTintSource grassTint() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return GrassColor.getDefaultColor();
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter view, BlockPos pos) {
                if (view == null || pos == null) {
                    return GrassColor.getDefaultColor();
                }
                return BiomeColors.getAverageGrassColor(view, pos);
            }
        };
    }

    private static BlockTintSource foliageTint() {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return FoliageColor.FOLIAGE_DEFAULT;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter view, BlockPos pos) {
                if (view == null || pos == null) {
                    return FoliageColor.FOLIAGE_DEFAULT;
                }
                return BiomeColors.getAverageFoliageColor(view, pos);
            }
        };
    }
}
