package net.sevenstars.middleearth.entity.projectile.smoke;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.MiddleEarth;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class SmokeRingProjectileRenderer extends EntityRenderer<SmokeRingProjectileEntity, SmokeRingProjectileRenderState> {
    private final TextureAtlasSprite[] frames;

    private static final Identifier SPRITES_ATLAS_ID = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "sprites");
    private static final String SPRITE_PATH_PREFIX = "sprites/smoke_ring/big_smoke_ring_";
    private static final int FRAME_COUNT = 12;
    private static final int FAILED_FIRST_FRAME = 7;
    private static final int FAILED_FRAME_COUNT = 5;
    private static final float SMOKE_RING_SIZE = 1.0f;

    public SmokeRingProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        frames = loadFrames();
    }

    @Override
    public void submit(
            SmokeRingProjectileRenderState state,
            PoseStack matrices,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraRenderState) {
        matrices.pushPose();
        matrices.translate(0, 0.2, 0);
        matrices.mulPose(state.orientationQuat);

        int firstFrame = state.failed ? FAILED_FIRST_FRAME : 0;
        int frameCount = state.failed ? FAILED_FRAME_COUNT : frames.length;
        int frame = firstFrame + Math.min((int) (state.ageInTicks / state.maxLifespan * frameCount), frameCount - 1);
        TextureAtlasSprite sprite = frames[frame];

        float minU = sprite.getU0();
        float maxU = sprite.getU1();
        float minV = sprite.getV0();
        float maxV = sprite.getV1();

        int overlay = net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;
        int light = state.lightCoords;

        submitNodeCollector.submitCustomGeometry(matrices, RenderTypes.entityTranslucent(sprite.atlasLocation()), (pose, vc) -> {
            drawQuad(vc, pose.pose(), SMOKE_RING_SIZE, minU, maxU, minV, maxV, light, overlay);
        });

        matrices.popPose();
        super.submit(state, matrices, submitNodeCollector, cameraRenderState);
    }

    @Override
    public SmokeRingProjectileRenderState createRenderState() {
        SmokeRingProjectileRenderState state = new SmokeRingProjectileRenderState();
        state.maxLifespan = SmokeRingProjectileEntity.MAX_LIFESPAN_TICKS;
        state.failed = false;

        return state;
    }

    @Override
    public void extractRenderState(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        super.extractRenderState(entity, state, tickDelta);

        state.maxLifespan = entity.getMaxLifespanTicks();
        state.failed = entity.isFailed();
        this.updateOrientationQuaternion(entity, state, tickDelta);
    }

    private TextureAtlasSprite[] loadFrames() {
        TextureAtlas atlas = (TextureAtlas) Minecraft.getInstance().getTextureManager().getTexture(
                SPRITES_ATLAS_ID);

        TextureAtlasSprite[] sprites = new TextureAtlasSprite[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            Identifier spriteId = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, SPRITE_PATH_PREFIX + i);
            sprites[i] = atlas.getSprite(spriteId);
        }
        return sprites;
    }

    private void drawQuad(
            VertexConsumer vc,
            Matrix4f matrix,
            float size,
            float minU,
            float maxU,
            float minV,
            float maxV,
            int light,
            int overlay) {
        float half = size / 2f;
        vc.addVertex(matrix, -half, -half, 0).setColor(255, 255, 255, 255).setUv(minU, minV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, -half, +half, 0).setColor(255, 255, 255, 255).setUv(minU, maxV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, +half, +half, 0).setColor(255, 255, 255, 255).setUv(maxU, maxV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
        vc.addVertex(matrix, +half, -half, 0).setColor(255, 255, 255, 255).setUv(maxU, minV).setOverlay(
                overlay).setLight(light).setNormal(0, 0, 1);
    }

    private void updateOrientationQuaternion(
            SmokeRingProjectileEntity entity,
            SmokeRingProjectileRenderState state,
            float tickDelta) {
        float yawRad = (float) Math.toRadians(-Mth.lerp(tickDelta,
                entity.yRotO,
                entity.getYRot()));
        float pitchRad = (float) Math.toRadians(Mth.lerp(tickDelta,
                entity.xRotO,
                entity.getXRot()));
        state.orientationQuat = new Quaternionf().rotateYXZ(yawRad, pitchRad, 0f);
    }
}

