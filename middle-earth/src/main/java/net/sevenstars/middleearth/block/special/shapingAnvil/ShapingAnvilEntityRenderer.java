package net.sevenstars.middleearth.block.special.shapingAnvil;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

public class ShapingAnvilEntityRenderer implements BlockEntityRenderer<ShapingAnvilBlockEntity, ShapingAnvilEntityRenderer.State> {

    public static class State extends BlockEntityRenderState {
    }

    public ShapingAnvilEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void submit(State state, com.mojang.blaze3d.vertex.PoseStack matrices, net.minecraft.client.renderer.SubmitNodeCollector submitNodeCollector, net.minecraft.client.renderer.state.level.CameraRenderState cameraRenderState) {
    }
}
