package net.sevenstars.middleearth.gui.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.sevenstars.middleearth.gui.render.states.BannerResultWithScaleGuiElementRenderState;

@Environment(EnvType.CLIENT)
public class BannerResultWithScaleGuiElementRenderer extends InstancedGuiElementRenderer<BannerResultWithScaleGuiElementRenderState> {
    public BannerResultWithScaleGuiElementRenderer() {
        super();
    }

    public Class<BannerResultWithScaleGuiElementRenderState> getRenderStateClass() {
        return BannerResultWithScaleGuiElementRenderState.class;
    }

    @Override
    protected void renderToTexture(BannerResultWithScaleGuiElementRenderState bannerResultWithStateGuiElementRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        // TODO 26.2: banner pattern preview rendering through the feature dispatcher is not yet ported.
    }

    protected String getTextureLabel() {
        return "banner result";
    }
}
