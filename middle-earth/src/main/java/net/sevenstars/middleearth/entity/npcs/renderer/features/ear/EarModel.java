package net.sevenstars.middleearth.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;

public class EarModel extends EntityModel<NpcEntityRenderState> {
    public final ModelPart ears;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;

    public EarModel(ModelPart modelPart) {
        super(modelPart);

        this.ears = modelPart.getChild("ears");

        this.planeFlatLeft = this.ears.getChild("ear_left");
        this.planeFlatRight = this.ears.getChild("ear_right");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition ears = root.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        ears.addOrReplaceChild("ear_right",
                CubeListBuilder.create().texOffs(0, 6).addBox(3.5F, -8.0F, 2F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.ZERO);

        ears.addOrReplaceChild("ear_left",
                CubeListBuilder.create().texOffs(0, 6).addBox(3.5F, -8.0F, -2F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.ZERO);

        return LayerDefinition.create(modelData, 16, 16);
    }

    @Override
    public void setupAnim(NpcEntityRenderState state) {
        super.setupAnim(state);

        // Taken from BipedEntityModel.class
        float f = state.swimAmount;
        boolean bl = state.isFallFlying;
        this.ears.xRot = state.xRot * 0.017453292F;
        this.ears.yRot = state.yRot * 0.017453292F;

        if (bl) {
            this.ears.xRot = -0.7853982F;
        } else if (f > 0.0F) {
            this.ears.xRot = Mth.rotLerpRad(f, this.ears.xRot, -0.7853982F);
        }

        this.planeFlatLeft.yRot = (float)Math.toRadians(-20);
        this.planeFlatRight.yRot = (float)Math.toRadians(-160);
    }
}
