package net.sevenstars.middleearth.block.special.coffers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class BeechCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BODY = "body";

    public BeechCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.BEECH_COFFER, "beech_coffer", false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild(BODY, CubeListBuilder.create()
                        .texOffs(8, 50).addBox(-12.0F, -1.0F, -3.0F, 12.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.0F, 19.0F, 1.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                        .texOffs(43, 48).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(43, 48).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create()
                        .texOffs(12, 35).addBox(-6.0F, -2.0F, -6.0F, 12.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 18.0F, 5.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
