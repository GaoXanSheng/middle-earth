package net.sevenstars.middleearth.mixin;

import net.minecraft.world.level.chunk.status.ChunkStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChunkStep.class)
public class ChunkGenerationStepMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int ChunkGenerationStep(int blockStateWriteRadius) {
        if(blockStateWriteRadius < 0) return  blockStateWriteRadius - 1;
        else return blockStateWriteRadius + 1;
    }
}
