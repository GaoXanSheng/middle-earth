package net.sevenstars.middleearth.block.special.plate;

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
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class PlateEntityRenderer implements BlockEntityRenderer<PlateBlockEntity, PlateEntityRenderer.State> {

    private final ItemModelResolver itemModelResolver;

    public PlateEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public void extractRenderState(PlateBlockEntity blockEntity, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        ItemStack stack = blockEntity.getTheItem();

        if (!stack.isEmpty()) {
            Identifier oldId = stack.get(DataComponents.ITEM_MODEL);
            Identifier modelId = PlateFoodModels.getPlateIdentifier(oldId);
            ItemStack copyStack = stack.copy();
            copyStack.set(DataComponents.ITEM_MODEL, modelId);

            boolean is3D = oldId != modelId;
            Direction direction = blockEntity.getBlockState().getValue(PlateBlock.FACING);

            long seed = Mth.getSeed(blockEntity.getBlockPos());
            double xOffset = ((seed & 15L) / 15.0 - 0.5) * 0.15;
            double zOffset = (((seed >> 4 & 15L) / 15.0) - 0.5) * 0.15;
            float rotOffset = (float) ((((seed >> 5 & 15L) / 15.0) - 0.5) * 35);

            int seed2 = (int) blockEntity.getBlockPos().asLong();
            this.itemModelResolver.updateForTopItem(state.item, copyStack, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed2);

            state.xOffset = xOffset;
            state.zOffset = zOffset;
            state.rotOffset = rotOffset;
            state.is3D = is3D;
            state.facingRotation = switch (direction) {
                case NORTH -> 0.0F;
                case EAST -> 90.0F;
                case SOUTH -> 180.0F;
                case WEST -> 270.0F;
                default -> 0.0F;
            };
        } else {
            state.item.clear();
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
        if (!state.is3D) {
            poseStack.translate(0.5D + state.xOffset, 0.085D, 0.5D + state.zOffset);
            poseStack.scale(0.65F, 0.65F, 0.65F);
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(state.facingRotation));
            poseStack.mulPose(Axis.ZP.rotationDegrees(state.rotOffset));
        } else {
            poseStack.translate(0.5D + state.xOffset, 0.56D, 0.5D + state.zOffset);
            poseStack.mulPose(Axis.YP.rotationDegrees(state.facingRotation));
            poseStack.mulPose(Axis.YP.rotationDegrees(state.rotOffset));
        }
        state.item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public static class State extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public double xOffset;
        public double zOffset;
        public float rotOffset;
        public float facingRotation;
        public boolean is3D;
    }
}
