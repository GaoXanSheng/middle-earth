package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollHeldItemFeatureRenderer extends RenderLayer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    public CaveTrollHeldItemFeatureRenderer(RenderLayerParent<CaveTrollEntityRenderState, CaveTrollEntityModel> context) {
        super(context);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.handItemState.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        this.getParentModel().setArmAngle(poseStack);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(0.3F, 0.0F, -2.6F);
        poseStack.scale(1.5F, 1.5F, 1.5F);
        state.handItemState.submit(poseStack, submitNodeCollector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
        poseStack.popPose();
    }
}
