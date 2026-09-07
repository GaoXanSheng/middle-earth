package net.sevenstars.middleearth.entity.barrel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.BoatRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import org.joml.Quaternionf;

public class BarrelEntityRenderer extends EntityRenderer<BarrelEntity, BoatRenderState> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/reinforced_barrel/reinforced_barrel.png");
    private final ModelPart modelPart;

    public BarrelEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.modelPart = context.bakeLayer(EntityModelLayersME.REINFORCED_BARREL);
        this.shadowRadius = 0.6F;
    }

    @Override
    public BoatRenderState createRenderState() {
        return new BoatRenderState();
    }

    @Override
    public void submit(BoatRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        matrices.pushPose();
        matrices.scale(1.35f, 1.35f, 1.35f);
        matrices.mulPose(Axis.YP.rotationDegrees(180.0F - state.yRot));
        matrices.mulPose(Axis.ZP.rotationDegrees(180.0F));

        float f = state.hurtTime;
        if (f > 0.0F) {
            matrices.mulPose(Axis.XP.rotationDegrees(Mth.sin(f) * f * state.damageTime / 10.0F * (float)state.hurtDir));
        }

        if (!state.isUnderWater && !Mth.equal(state.bubbleAngle, 0.0F)) {
            matrices.mulPose((new Quaternionf()).setAngleAxis(state.bubbleAngle * 0.017453292F, 1.0F, 0.0F, 1.0F));
        }

        matrices.translate(0f, -1.4f, 0f);
        submitNodeCollector.submitModelPart(this.modelPart, matrices, RenderTypes.entityCutout(TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, null);
        matrices.popPose();
        super.submit(state, matrices, submitNodeCollector, cameraRenderState);
    }

    @Override
    public void extractRenderState(BarrelEntity barrelEntity, BoatRenderState boatEntityRenderState, float f) {
        super.extractRenderState(barrelEntity, boatEntityRenderState, f);
        boatEntityRenderState.yRot = barrelEntity.getYRot(f);
        boatEntityRenderState.hurtTime = (float)barrelEntity.getHurtTime() - f;
        boatEntityRenderState.hurtDir = barrelEntity.getHurtDir();
        boatEntityRenderState.damageTime = Math.max(barrelEntity.getDamage() - f, 0.0F);
        boatEntityRenderState.bubbleAngle = barrelEntity.getBubbleAngle(f);
        boatEntityRenderState.isUnderWater = barrelEntity.isUnderWater();
        boatEntityRenderState.rowingTimeLeft = barrelEntity.getRowingTime(0, f);
        boatEntityRenderState.rowingTimeRight = barrelEntity.getRowingTime(1, f);
    }
}
