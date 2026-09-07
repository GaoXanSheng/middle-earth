package net.sevenstars.middleearth.mixin;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {

    @Mutable
    @Final @Shadow protected static Map<Block, BlockState> FLATTENABLES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addModdedDirtBlocks(CallbackInfo ci) {
        Map<Block, BlockState> pathStates = FLATTENABLES;

        pathStates.put(ModBlocks.DRY_DIRT, Blocks.DIRT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.CHALKSOIL_GRASS_BLOCK, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        pathStates.put(ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        pathStates.put(ModBlocks.GRASSY_CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        pathStates.put(ModBlocks.COARSE_CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        pathStates.put(ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM_PATH.defaultBlockState());
        pathStates.put(ModBlocks.LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        pathStates.put(ModBlocks.GRASSY_LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        pathStates.put(ModBlocks.COARSE_LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        pathStates.put(ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.GRASSY_PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.COARSE_PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.SILT, ModBlocks.SILT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.GRASSY_SILT, ModBlocks.SILT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.COARSE_SILT, ModBlocks.SILT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.DIRTY_ROOTS, Blocks.DIRT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.GRASSY_DIRT, Blocks.DIRT_PATH.defaultBlockState());
        pathStates.put(ModBlocks.TURF, Blocks.DIRT_PATH.defaultBlockState());

        FLATTENABLES = pathStates;
    }
}
