package net.sevenstars.middleearth.mixin.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.gui.render.GuiRenderer;
import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.sevenstars.middleearth.gui.render.InstancedGuiElementRenderer;
import net.sevenstars.middleearth.gui.render.states.InstancedGuiElementRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.Map;

@Mixin(GuiRenderer.class)
public class GuiRendererMixin {
    @Shadow
    @Final
    private GuiRenderState renderState;
    @Shadow
    @Final
    private FeatureRenderDispatcher featureRenderDispatcher;
    @Unique
    private final Map<InstancedGuiElementRenderState, InstancedGuiElementRenderer<?>> instancedRenderers = new Object2ObjectOpenHashMap<>();

    @Inject(method = "preparePictureInPictureState", at = @At("HEAD"), cancellable = true)
    private <T extends PictureInPictureRenderState> void skyblocker$instancedGuiElementRendering(T specialGuiElementRenderState, int windowScaleFactor, CallbackInfo ci) {
        if (specialGuiElementRenderState instanceof InstancedGuiElementRenderState instanced) {
            @SuppressWarnings("unchecked")
            InstancedGuiElementRenderer<InstancedGuiElementRenderState> renderer = (InstancedGuiElementRenderer<InstancedGuiElementRenderState>) this.instancedRenderers.computeIfAbsent(instanced, ignored -> (InstancedGuiElementRenderer<InstancedGuiElementRenderState>) instanced.newRenderer());
            renderer.prepare(instanced, this.renderState, this.featureRenderDispatcher, windowScaleFactor);

            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void skyblocker$clearUnusedRenderers(CallbackInfo ci) {
        this.closeUnusedRenderers();
    }

    @Inject(method = "close", at = @At("TAIL"))
    public void skyblocker$closeInstancedRenderers(CallbackInfo ci) {
        this.instancedRenderers.values().forEach(PictureInPictureRenderer::close);
    }

    @Unique
    private void closeUnusedRenderers() {
        Iterator<Map.Entry<InstancedGuiElementRenderState, InstancedGuiElementRenderer<?>>> iterator = this.instancedRenderers.entrySet().iterator();

        while (iterator.hasNext()) {
            InstancedGuiElementRenderer<?> renderer = iterator.next().getValue();

            if (!renderer.usedThisFrame()) {
                renderer.close();
                iterator.remove();
            } else {
                renderer.resetUsedThisFrame();
            }
        }
    }
}
