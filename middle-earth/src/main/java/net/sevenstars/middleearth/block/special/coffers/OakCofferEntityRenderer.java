package net.sevenstars.middleearth.block.special.coffers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlock;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class OakCofferEntityRenderer<T extends ChestBlockEntity> extends ChestRenderer<T> {
    private static final String BODY = "body";
    private static final String LID = "lid";

    private final ModelPart lid;
    private final ModelPart body;

    public OakCofferEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx);
        ModelPart modelPart = ctx.bakeLayer(EntityModelLayersME.OAK_COFFER);
        this.lid = modelPart.getChild(LID);
        this.body = modelPart.getChild(BODY);
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
