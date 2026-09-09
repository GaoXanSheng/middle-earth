package net.sevenstars.middleearth.block.special.reinforcedChest;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
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
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class ReinforcedChestEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {

    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private static final String LATCH = "lock";
    private static final SpriteId SPRITE = new SpriteId(Sheets.CHEST_SHEET, MiddleEarth.ofPath("model", "reinforced_chest"));

    private final SpriteGetter sprites;
    private final ReinforcedChestModel model;

    public ReinforcedChestEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.sprites = context.sprites();
        this.model = new ReinforcedChestModel(context.bakeLayer(EntityModelLayersME.REINFORCED_CHEST));
    }

    @Override
    public void submit(ChestRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.0D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));
        collector.submitModel(this.model, state, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, SPRITE, this.sprites, 0, state.breakProgress);
        poseStack.popPose();
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, 0.0F, -6.0F, 16.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 1).addBox(-2.0F, -3.0F, -13.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 6.0F));
        modelPartData.addOrReplaceChild(LATCH, CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -12.0F, 16.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 6.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    private static class ReinforcedChestModel extends Model<ChestRenderState> {
        private final ModelPart lid;
        private final ModelPart latch;

        ReinforcedChestModel(ModelPart root) {
            super(root, RenderTypes::entityCutout);
            this.lid = root.getChild(LID);
            this.latch = root.getChild(LATCH);
        }

        @Override
        public void setupAnim(ChestRenderState state) {
            super.setupAnim(state);
            float openness = 1.0F - state.open;
            openness = 1.0F - openness * openness * openness;
            this.lid.xRot = openness * 1.5707964F;
            this.latch.xRot = openness * 1.5707964F;
        }
    }
}
