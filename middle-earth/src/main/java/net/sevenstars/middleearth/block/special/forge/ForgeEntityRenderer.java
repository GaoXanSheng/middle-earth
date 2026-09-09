package net.sevenstars.middleearth.block.special.forge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ForgeEntityRenderer implements BlockEntityRenderer<ForgeBlockEntity, ForgeEntityRenderer.State> {

    private final ItemModelResolver itemModelResolver;

    public ForgeEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(ForgeBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        Direction facing = blockEntity.getBlockState().getValue(ForgeBlock.FACING);
        ItemStack stack = blockEntity.getRenderStack(blockEntity);

        if (!stack.isEmpty()) {
            int seed = (int) blockEntity.getBlockPos().asLong();
            this.itemModelResolver.updateForTopItem(state.item, stack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed);
            state.zRotation = switch (facing) {
                case NORTH -> 225.0F;
                case EAST -> 135.0F;
                case SOUTH -> 45.0F;
                case WEST -> 315.0F;
                default -> 0.0F;
            };
            if (blockEntity.getLevel() != null) {
                state.frontLightCoords = LightCoordsUtil.getLightCoords(blockEntity.getLevel(), blockEntity.getBlockPos().relative(facing).above());
            }
        } else {
            state.item.clear();
            state.zRotation = 0.0F;
        }
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        if (state.item.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.025D, 0.5D);
        poseStack.scale(0.65F, 0.65F, 0.65F);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.zRotation));
        state.item.submit(poseStack, collector, state.frontLightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public float zRotation;
        public int frontLightCoords;
    }
}
