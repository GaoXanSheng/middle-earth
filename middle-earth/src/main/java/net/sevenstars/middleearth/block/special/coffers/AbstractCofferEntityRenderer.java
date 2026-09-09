package net.sevenstars.middleearth.block.special.coffers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.MiddleEarth;

@Environment(EnvType.CLIENT)
public abstract class AbstractCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private final SpriteGetter sprites;
    private final SpriteId sprite;
    private final CofferModel model;

    protected AbstractCofferEntityRenderer(BlockEntityRendererProvider.Context context, ModelLayerLocation layer,
                                           String texturePath, boolean rollLid) {
        super(context);
        this.sprites = context.sprites();
        this.sprite = new SpriteId(Sheets.CHEST_SHEET, MiddleEarth.ofPath("model", texturePath));
        this.model = new CofferModel(context.bakeLayer(layer), rollLid);
    }

    @Override
    public void submit(ChestRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        this.applyPose(state, poseStack);
        collector.submitModel(this.model, state, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, this.sprite, this.sprites, 0, state.breakProgress);
        poseStack.popPose();
    }

    protected void applyPose(ChestRenderState state, PoseStack poseStack) {
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(state.facing.toYRot() - 180.0F));
    }

    protected static class CofferModel extends Model<ChestRenderState> {
        private final ModelPart lid;
        private final boolean rollLid;

        protected CofferModel(ModelPart root, boolean rollLid) {
            super(root, RenderTypes::entityCutout);
            this.lid = root.getChild("lid");
            this.rollLid = rollLid;
        }

        @Override
        public void setupAnim(ChestRenderState state) {
            super.setupAnim(state);
            float openness = 1.0F - state.open;
            openness = 1.0F - openness * openness * openness;
            if (this.rollLid) {
                this.lid.zRot = openness * -1.5707964F;
            } else {
                this.lid.xRot = openness * -1.5707964F;
            }
        }
    }
}
