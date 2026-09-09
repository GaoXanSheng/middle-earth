package net.sevenstars.middleearth.block.special.coffers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class SpruceCofferEntityRenderer<T extends ChestBlockEntity> extends AbstractCofferEntityRenderer<T> {

    private static final String LID = "lid";
    private static final String BASE = "bottom";

    public SpruceCofferEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context, EntityModelLayersME.SPRUCE_COFFER, "spruce_coffer", true);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition lid = modelPartData.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 45)
                .addBox(0.0F, -3.0F, -8.0F, 16.0F, 3.0F, 16.0F,
                new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 16.0F, 0.0F));

        PartDefinition lock_r1 = lid.addOrReplaceChild("lock_r1", CubeListBuilder.create().texOffs(0, 1)
                .addBox(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 38)
                        .addBox(5.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 38).addBox(-8.0F, -3.0F, -8.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(24, 38).addBox(-8.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 38).addBox(5.0F, -3.0F, 5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-5.0F, -3.0F, -8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(20, 36).addBox(-5.0F, -3.0F, 8.0F, 10.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 24).addBox(8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 24).addBox(-8.0F, -3.0F, -5.0F, 0.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    protected void applyPose(ChestRenderState state, PoseStack poseStack) {
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        float rotation = state.facing.toYRot();
        if (state.facing == Direction.NORTH || state.facing == Direction.SOUTH) {
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation - 90.0F));
        } else {
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation + 90.0F));
        }
    }
}
