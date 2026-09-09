package net.sevenstars.middleearth.block.special.skull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity, OldSkullBlockEntityRenderer.State> {

    private static final SpriteId SKULL_SPRITE = new SpriteId(TextureAtlas.LOCATION_BLOCKS, MiddleEarth.ofPath("model", "old_skull"));
    private final SpriteGetter sprites;

    public OldSkullBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        ModelPart modelPart = context.bakeLayer(EntityModelLayersME.OLD_SKULL);
        this.skull = modelPart.getChild("skull");
    }
    private final ModelPart skull;

    @Override
    public void extractRenderState(OldSkullBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        BlockState blockState = blockEntity.getBlockState();
        state.yaw = -RotationSegment.convertToDegrees(blockState.getValue(OldSkullBlock.ROTATION));
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.translate(0.0F, 1.5F, 0.0F);
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yaw));
        collector.submitModelPart(this.skull, poseStack, RenderTypes.entityCutoutZOffset(TextureAtlas.LOCATION_BLOCKS),
                state.lightCoords, OverlayTexture.NO_OVERLAY, this.sprites.get(SKULL_SPRITE));
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public float yaw;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition skull = modelPartData.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }
}
