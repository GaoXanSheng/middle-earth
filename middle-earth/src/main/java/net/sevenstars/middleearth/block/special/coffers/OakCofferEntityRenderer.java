package net.sevenstars.middleearth.block.special.coffers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class OakCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BODY = "body";

    public OakCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.OAK_COFFER, "oak_coffer", false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addOrReplaceChild(LID, CubeListBuilder.create(),
                PartPose.offset(0.0F, 13.0F, 6.0F));

        lid.addOrReplaceChild("lid_r1", CubeListBuilder.create()
                        .texOffs(0, 9).addBox(-12.0F, -3.0F, -16.0F, 12.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild(BODY, CubeListBuilder.create()
                        .texOffs(0, 56).addBox(-11.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 56).addBox(-11.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(39, 56).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(13, 56).addBox(-2.0F, 0.0F, -14.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 32).addBox(-11.0F, -7.0F, -14.0F, 12.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.0F, 20.0F, 5.0F, 0.0F, -1.5708F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                        .texOffs(0, -2).addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, -5.0F, -14.0F, -1.5708F, 1.2654F, -1.5708F));

        body.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                        .texOffs(0, -2).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-5.0F, -5.0F, 0.0F, 1.5708F, 1.2654F, 1.5708F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
