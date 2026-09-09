package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

@Environment(EnvType.CLIENT)
public class FireOfOrthancEntityRenderer extends EntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {

    public FireOfOrthancEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }

    @Override
    public boolean shouldRender(FireOfOrthancEntity entity, net.minecraft.client.renderer.culling.Frustum cullingView,
                                double camX, double camY, double camZ) {
        // Skip once the entity has been placed as an actual block at its own position.
        return super.shouldRender(entity, cullingView, camX, camY, camZ)
                && entity.level().getBlockState(entity.blockPosition()) != ModDecorativeBlocks.FIRE_OF_ORTHANC.defaultBlockState();
    }

    @Override
    public void extractRenderState(FireOfOrthancEntity entity, FireOfOrthancEntityRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        BlockPos pos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        state.movingBlockRenderState.randomSeedPos = entity.blockPosition();
        state.movingBlockRenderState.blockPos = pos;
        state.movingBlockRenderState.blockState = ModDecorativeBlocks.FIRE_OF_ORTHANC.defaultBlockState();
        if (entity.level() instanceof ClientLevel clientLevel) {
            state.movingBlockRenderState.biome = clientLevel.getBiome(pos);
            state.movingBlockRenderState.cardinalLighting = clientLevel.cardinalLighting();
            state.movingBlockRenderState.lightEngine = clientLevel.getLightEngine();
        }
    }

    @Override
    public void submit(FireOfOrthancEntityRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));

        float scale = (state.ageInTicks / 36.0F) + 1.0F;
        scale = Math.min(1.2F, scale);
        poseStack.scale(scale, scale, scale);

        poseStack.translate(-0.5D, 0.0D, -0.5D);
        collector.submitMovingBlock(poseStack, state.movingBlockRenderState, state.outlineColor);
        poseStack.popPose();
        super.submit(state, poseStack, collector, camera);
    }
}
