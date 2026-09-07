package net.sevenstars.middleearth.block.special.skull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class OldSkullBlockEntityRenderer implements BlockEntityRenderer<OldSkullBlockEntity, OldSkullBlockEntityRenderer.State> {

    public static class State extends BlockEntityRenderState {
    }

    private final ModelPart skull;

    public OldSkullBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart modelPart = ctx.bakeLayer(EntityModelLayersME.OLD_SKULL);
        this.skull = modelPart.getChild("skull");
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition skull = modelPartData.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-12.0F, -8.0F, 4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void submit(State state, com.mojang.blaze3d.vertex.PoseStack matrices, net.minecraft.client.renderer.SubmitNodeCollector submitNodeCollector, net.minecraft.client.renderer.state.level.CameraRenderState cameraRenderState) {
    }
}
