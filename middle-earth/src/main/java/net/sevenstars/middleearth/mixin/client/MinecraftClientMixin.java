package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final
    public Options options;

    @Inject(method = "updateLevelInEngines", at = @At("HEAD"))
    private void resetGlintInNether(ClientLevel world, CallbackInfo ci) {
        if (ModClientConfigs.DISABLE_GLINT && world != null && ModDimensions.isInMiddleEarth(world)) {
            this.options.glintStrength().set(0.0);
        }
    }
}
