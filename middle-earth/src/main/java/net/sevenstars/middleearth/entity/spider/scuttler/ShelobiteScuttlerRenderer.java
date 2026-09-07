package net.sevenstars.middleearth.entity.spider.scuttler;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class ShelobiteScuttlerRenderer extends MobRenderer<ShelobiteScuttlerEntity, ShelobiteScuttlerRenderState, ShelobiteScuttlerModel> {

    public ShelobiteScuttlerRenderer(EntityRendererProvider.Context context) {
        this(context, 0.45F, EntityModelLayersME.SHELOBITE_SCUTTLER);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteScuttlerRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteScuttlerModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public Identifier getTextureLocation(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().scuttler();
    }

    @Override
    public void extractRenderState(ShelobiteScuttlerEntity shelobiteScuttlerEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.extractRenderState(shelobiteScuttlerEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(shelobiteScuttlerEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(shelobiteScuttlerEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(shelobiteScuttlerEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(shelobiteScuttlerEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.climbingTicks = shelobiteScuttlerEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = shelobiteScuttlerEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = shelobiteScuttlerEntity.getVariant();
    }
}
