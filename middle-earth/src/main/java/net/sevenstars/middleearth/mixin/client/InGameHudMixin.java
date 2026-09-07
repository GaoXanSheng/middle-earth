package net.sevenstars.middleearth.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class InGameHudMixin {
    @Shadow @Final private Minecraft minecraft;

    // @Shadow protected abstract void renderTextureOverlay(GuiGraphicsExtractor context, Identifier texture, float opacity);

    //TODO broky
    /*
    @Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderTickCounter;getLastFrameDuration()F", shift = At.Shift.AFTER))
    public void injected(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            this.renderOverlay(context, HALLUCINATION_OUTLINE, intensity);

        }
    }*/
}

