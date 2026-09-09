package net.sevenstars.middleearth.block.special.coffers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class WillowCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BODY = "body";

    public WillowCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.WILLOW_COFFER, "willow_coffer", false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addOrReplaceChild(BODY, CubeListBuilder.create()
                        .texOffs(0, 36).addBox(-1.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(16, 36).addBox(-1.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 36).addBox(11.0F, -4.0F, 7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 44).addBox(11.0F, -4.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(1, 0).addBox(-1.0F, -13.0F, -1.0F, 16.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-7.0F, 24.0F, -5.0F));

        modelPartData.addOrReplaceChild(LID, CubeListBuilder.create()
                        .texOffs(1, 21).addBox(-8.0F, -3.0F, -12.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 11.0F, 6.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }
}
