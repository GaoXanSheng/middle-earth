package net.sevenstars.middleearth.entity.npcs.renderer.features.hair;

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

public class HairModel extends EntityModel<NpcEntityRenderState> {
    // https://i.pinimg.com/736x/9c/56/05/9c560508ceba0bc87b9d5beda7391adc.jpg
    public static final String BEARD = "beard";
    public final ModelPart hair;
    public final ModelPart hairBase;
    public final ModelPart hairHat;
    public final ModelPart largeBeard;

    public HairModel(ModelPart modelPart) {
        super(modelPart);

        this.hair = modelPart.getChild("hair");
        this.hairBase = hair.getChild("hairBase");
        this.hairHat = hair.getChild("hairHat");
        this.largeBeard = hair.getChild("largeBeard");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition hairGroup = modelPartData.addOrReplaceChild("hair", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("hairBase", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, 0f, -4.0f, 8, 11, 8, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("hairHat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, 1f, -4f, 8, 11, 8, CubeDeformation.NONE.extend(0.5f)), PartPose.offset(0.0F, 0.0F, 0.0F));
        hairGroup.addOrReplaceChild("largeBeard", CubeListBuilder.create().texOffs(37, 37).addBox(-6.5F, -3.5F, -4.1F,14.0F, 25.0F, -0.5F, CubeDeformation.NONE.extend(0.25f)), PartPose.ZERO);
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(NpcEntityRenderState state) {
        super.setupAnim(state);

        // Taken from BipedEntityModel.class
        float f = state.swimAmount;
        boolean bl = state.isFallFlying;
        this.hair.xRot = state.xRot * 0.017453292F;
        this.hair.yRot = state.yRot * 0.017453292F;

        if (bl) {
            this.hair.xRot = -0.7853982F;
        } else if (f > 0.0F) {
            this.hair.xRot = Mth.rotLerpRad(f, this.hair.xRot, -0.7853982F);
        }
    }
}
