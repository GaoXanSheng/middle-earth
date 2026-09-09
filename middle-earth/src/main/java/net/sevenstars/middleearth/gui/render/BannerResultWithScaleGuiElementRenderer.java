package net.sevenstars.middleearth.gui.render;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.gui.render.states.BannerResultWithScaleGuiElementRenderState;

@Environment(EnvType.CLIENT)
public class BannerResultWithScaleGuiElementRenderer extends InstancedGuiElementRenderer<BannerResultWithScaleGuiElementRenderState> {
    public BannerResultWithScaleGuiElementRenderer() {
        super();
    }

    public Class<BannerResultWithScaleGuiElementRenderState> getRenderStateClass() {
        return BannerResultWithScaleGuiElementRenderState.class;
    }

    private static void submitLayer(SubmitNodeCollector collector, PoseStack poseStack, net.minecraft.client.resources.model.sprite.SpriteGetter sprites,
                                    net.minecraft.client.model.geom.ModelPart flag, SpriteId sprite, DyeColor color) {
        collector.submitModelPart(
                flag,
                poseStack,
                sprite.renderType(RenderTypes::bannerPattern),
                15728880,
                OverlayTexture.NO_OVERLAY,
                sprites.get(sprite),
                color.getTextureDiffuseColor(),
                null,
                0
        );
    }

    @Override
    protected void renderToTexture(BannerResultWithScaleGuiElementRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
        Minecraft.getInstance().gameRenderer.lighting().setupFor(Lighting.Entry.ITEMS_FLAT);
        poseStack.translate(0.0F, 0.25F, 0.0F);

        var sprites = Minecraft.getInstance().getAtlasManager();
        DyeColor baseColor = state.baseColor();
        BannerPatternLayers patterns = state.resultBannerPatterns();

        submitLayer(submitNodeCollector, poseStack, sprites, state.flag(), Sheets.BANNER_BASE, baseColor);
        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            BannerPatternLayers.Layer layer = patterns.layers().get(i);
            submitLayer(submitNodeCollector, poseStack, sprites, state.flag(), Sheets.getBannerSprite(layer.pattern()), layer.color());
        }
    }

    protected String getTextureLabel() {
        return "banner result";
    }
}
