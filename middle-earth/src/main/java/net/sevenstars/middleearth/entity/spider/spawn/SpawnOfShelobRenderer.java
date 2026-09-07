package net.sevenstars.middleearth.entity.spider.spawn;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class SpawnOfShelobRenderer extends MobRenderer<SpawnOfShelobEntity, SpawnOfShelobRenderState, SpawnOfShelobModel> {
    private static final String PATH = "textures/entities/spiders/";

    public SpawnOfShelobRenderer(EntityRendererProvider.Context context) {
        this(context, 0.75F, EntityModelLayersME.SPAWN_OF_SHELOB);
    }

    @Override
    public SpawnOfShelobRenderState createRenderState() {
        return new SpawnOfShelobRenderState();
    }

    protected SpawnOfShelobRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new SpawnOfShelobModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public Identifier getTextureLocation(SpawnOfShelobRenderState state) {
        return state.spiderVariant.assetInfo().spawnOfShelob();
    }

    @Override
    public void extractRenderState(SpawnOfShelobEntity spawnofShelobEntity, SpawnOfShelobRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.extractRenderState(spawnofShelobEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.idleAnimationState.copyFrom(spawnofShelobEntity.idleAnimation);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(spawnofShelobEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(spawnofShelobEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.blockAnimationState.copyFrom(spawnofShelobEntity.blockAnimation);
        shelobiteScuttlerEntityRenderState.pounceAnimationState.copyFrom(spawnofShelobEntity.pounceAnimation);
        shelobiteScuttlerEntityRenderState.timelineTicks = spawnofShelobEntity.getTimelineTicks();
        shelobiteScuttlerEntityRenderState.climbingTicks = spawnofShelobEntity.getClimbingTicks();
        shelobiteScuttlerEntityRenderState.leapingTicks = spawnofShelobEntity.getLeapingTicks();
        shelobiteScuttlerEntityRenderState.spiderVariant = spawnofShelobEntity.getVariant();
    }
}
