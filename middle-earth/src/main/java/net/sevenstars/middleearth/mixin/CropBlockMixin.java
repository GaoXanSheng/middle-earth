package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.sevenstars.middleearth.block.special.torches.METorchBlock;
import net.sevenstars.middleearth.block.special.torches.MEWallTorchBlock;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Inject(at = @At("TAIL"), method = "mayPlaceOn", cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local BlockState blockState) {
        if (floor.is(BlockTagsME.FARMLANDS) || floor.is(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "farmlands")))){
            cir.setReturnValue(true);
        }
    }
}
