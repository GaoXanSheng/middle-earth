package net.sevenstars.middleearth.block.special.coffers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class LarchCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BASE = "bottom";

    public LarchCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.LARCH_COFFER, "larch_coffer", false);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 21).addBox(-8.0F, -2.0F, -10.0F, 16.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(49, 57).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 3.0F, 1.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 5.0F));

        PartDefinition bottom = modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 58).addBox(-7.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 58).addBox(-7.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 58).addBox(4.0F, -2.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 58).addBox(4.0F, -2.0F, -5.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -10.0F, -5.0F, 14.0F, 8.0F, 10.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition handle_r1 = bottom.addOrReplaceChild("handle_r1", CubeListBuilder.create().texOffs(0, 0)
                .mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .mirror(false), PartPose.offsetAndRotation(7.0F, -6.1F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition handle_r2 = bottom.addOrReplaceChild("handle_r2", CubeListBuilder.create().texOffs(0, 0)
                .addBox(0.0F, 0.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.0F, -6.1F, 0.0F, 0.0F, 0.0F, 0.3927F));
        return LayerDefinition.create(modelData, 64, 64);
    }
}
