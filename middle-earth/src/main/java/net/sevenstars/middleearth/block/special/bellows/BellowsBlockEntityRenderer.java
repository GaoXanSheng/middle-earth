package net.sevenstars.middleearth.block.special.bellows;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
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
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity, BellowsBlockEntityRenderer.State> {
    private static final float BELLOW_MAX_ANGLE = 0.72F;

    private final SpriteGetter sprites;
    private final BellowsModel model;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
        this.model = new BellowsModel(context.bakeLayer(EntityModelLayersME.BELLOWS));
    }

    private static float getAnimationProgress(BellowsBlockEntity blockEntity) {
        float animationProgress = 0;
        if (blockEntity.pumping) {
            animationProgress = blockEntity.animationProgress;
            if (animationProgress > (float) BellowsBlockEntity.MAX_TICKS / 2) {
                animationProgress = BellowsBlockEntity.MAX_TICKS - animationProgress;
            }
            animationProgress /= BellowsBlockEntity.MAX_TICKS;
        }
        return animationProgress;
    }

    @Override
    public void extractRenderState(BellowsBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        Level level = blockEntity.getLevel();
        BlockState blockState = level != null
                ? blockEntity.getBlockState()
                : ModDecorativeBlocks.BELLOWS.defaultBlockState().setValue(BellowsBlock.FACING, Direction.SOUTH);
        state.facing = blockState.getValue(BellowsBlock.FACING);
        state.pumpProgress = getAnimationProgress(blockEntity);
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-state.facing.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));

        int spriteState = (int) Math.max(0, Math.min(2, state.pumpProgress * 7.5F));
        SpriteId sprite = new SpriteId(TextureAtlas.LOCATION_BLOCKS, MiddleEarth.ofPath("model", "bellows", "bellows_" + spriteState));
        collector.submitModel(this.model, state, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, sprite, this.sprites, 0, state.breakProgress);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public Direction facing = Direction.SOUTH;
        public float pumpProgress;
    }

    private static class BellowsModel extends Model<State> {
        private final ModelPart top;

        BellowsModel(ModelPart root) {
            super(root, RenderTypes::entityCutout);
            this.top = root.getChild("top");
        }

        @Override
        public void setupAnim(State state) {
            super.setupAnim(state);
            this.top.xRot = 0.37F + state.pumpProgress * -BELLOW_MAX_ANGLE;
        }
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 12)
                .addBox(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F,
                        new CubeDeformation(0.0F))
                .texOffs(1, 1).mirror().addBox(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 1).mirror().addBox(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 17).addBox(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -9.0F));

        modelPartData.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(18, 12).addBox(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F,
                        new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild("cavity", CubeListBuilder.create().texOffs(10, 17).mirror()
                .addBox(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F,
                        new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(modelData, 48, 48);
    }
}
