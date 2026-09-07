package net.sevenstars.middleearth.entity.spider.larva;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderState;

public class ShelobiteLarvaRenderer extends MobRenderer<ShelobiteLarvaEntity, ShelobiteScuttlerRenderState, ShelobiteLarvaModel> {
    private static final String PATH = "textures/entities/spiders/";

    public ShelobiteLarvaRenderer(EntityRendererProvider.Context context) {
        this(context, 0.2F, EntityModelLayersME.SHELOBITE_LARVA);
    }

    @Override
    public ShelobiteScuttlerRenderState createRenderState() {
        return new ShelobiteScuttlerRenderState();
    }

    protected ShelobiteLarvaRenderer(EntityRendererProvider.Context ctx, float shadowRadius, ModelLayerLocation layer) {
        super(ctx, new ShelobiteLarvaModel(ctx.bakeLayer(layer)), shadowRadius);
    }

    @Override
    public Identifier getTextureLocation(ShelobiteScuttlerRenderState state) {
        return state.spiderVariant.assetInfo().larva();
    }

    @Override
    public void extractRenderState(ShelobiteLarvaEntity larvaEntity, ShelobiteScuttlerRenderState shelobiteScuttlerEntityRenderState, float f) {
        super.extractRenderState(larvaEntity, shelobiteScuttlerEntityRenderState, f);
        shelobiteScuttlerEntityRenderState.walkAnimationState.copyFrom(larvaEntity.walkingAnimation);
        shelobiteScuttlerEntityRenderState.biteAnimationState.copyFrom(larvaEntity.biteAnimation);
        shelobiteScuttlerEntityRenderState.spiderVariant = larvaEntity.getVariant();
    }
}
