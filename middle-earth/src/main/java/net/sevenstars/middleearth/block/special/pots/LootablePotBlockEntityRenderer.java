package net.sevenstars.middleearth.block.special.pots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.client.renderer.blockentity.state.DecoratedPotRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class LootablePotBlockEntityRenderer implements BlockEntityRenderer<DecoratedPotBlockEntity, LootablePotBlockEntityRenderer.State> {

    private final DecoratedPotRenderer vanillaRenderer;

    public LootablePotBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.vanillaRenderer = new DecoratedPotRenderer(context);
    }

    private static void applyWobble(State state, PoseStack poseStack) {
        DecoratedPotBlockEntity.WobbleStyle wobbleStyle = state.wobbleStyle;
        float progress = state.wobbleProgress;
        if (wobbleStyle == null || progress < 0.0F || progress > 1.0F) {
            return;
        }

        if (wobbleStyle == DecoratedPotBlockEntity.WobbleStyle.POSITIVE) {
            float positiveWobbleScale = 0.015625F;
            float angle = progress * (float) (Math.PI * 2);
            float xRotation = -1.5F * (Mth.cos(angle) + 0.5F) * Mth.sin(angle / 2.0F);
            poseStack.rotateAround(Axis.XP.rotation(xRotation * positiveWobbleScale), 0.5F, 0.0F, 0.5F);
            poseStack.rotateAround(Axis.ZP.rotation(Mth.sin(angle) * positiveWobbleScale), 0.5F, 0.0F, 0.5F);
        } else {
            float yaw = Mth.sin(-progress * 3.0F * (float) Math.PI) * 0.125F;
            float strength = 1.0F - progress;
            poseStack.rotateAround(Axis.YP.rotation(yaw * strength), 0.5F, 0.0F, 0.5F);
        }
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(DecoratedPotBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        this.vanillaRenderer.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.wobbleStyle = blockEntity.lastWobbleStyle;
        state.lootable = blockEntity.getBlockState().getBlock() instanceof LootablePotBlock;

        if (state.lootable && blockEntity.getLevel() instanceof ClientLevel level) {
            BlockPos pos = blockEntity.getBlockPos();
            MovingBlockRenderState blockModel = state.blockModel;
            blockModel.randomSeedPos = pos;
            blockModel.blockPos = pos;
            blockModel.blockState = blockEntity.getBlockState();
            blockModel.biome = level.getBiome(pos);
            blockModel.cardinalLighting = level.cardinalLighting();
            blockModel.lightEngine = level.getLightEngine();
        }
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        if (!state.lootable) {
            this.vanillaRenderer.submit(state, poseStack, collector, camera);
            return;
        }

        poseStack.pushPose();
        this.applyWobble(state, poseStack);
        collector.submitMovingBlock(poseStack, state.blockModel, 0);
        poseStack.popPose();
    }

    public static class State extends DecoratedPotRenderState {
        public final MovingBlockRenderState blockModel = new MovingBlockRenderState();
        public boolean lootable;
    }
}
