package net.sevenstars.middleearth.block.special.bellows;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;

@Environment(EnvType.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity, BellowsBlockEntityRenderer.State> {

    public static class State extends BlockEntityRenderState {
    }

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public State createRenderState() {
        return new State();
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

    @Override
    public void submit(State state, com.mojang.blaze3d.vertex.PoseStack matrices, net.minecraft.client.renderer.SubmitNodeCollector submitNodeCollector, net.minecraft.client.renderer.state.level.CameraRenderState cameraRenderState) {
    }
}
