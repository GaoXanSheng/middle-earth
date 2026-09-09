package net.sevenstars.middleearth.block.special.coffers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class PineCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BASE = "bottom";

    public PineCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.PINE_COFFER, "pine_coffer", false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 23)
                .addBox(-8.0F, -2.0F, -14.0F, 16.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 10.0F, 7.0F));

        PartDefinition bottom = modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(48, 29).
                addBox(7.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(3, 29).addBox(7.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(3, 45).addBox(-8.0F, -4.0F, 4.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(47, 45).addBox(-8.0F, -4.0F, -6.0F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 41).addBox(-8.0F, -14.0F, -6.0F, 16.0F, 10.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(14, 5).addBox(8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(30, 5).addBox(-8.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bottom.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 0)
                .mirror().addBox(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .mirror(false), PartPose.offsetAndRotation(-8.0F, -12.0F, 1.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r2 = bottom.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 0)
                .addBox(0.0F, 0.0F, -4.0F, 0.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(8.0F, -12.0F, 1.0F, 0.0F, 0.0F, -0.3491F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
