package net.sevenstars.middleearth.entity.beasts.trolls.snow;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

public class SnowTrollRenderer extends MobRenderer<SnowTrollEntity, TrollEntityRenderState, SnowTrollModel> {
    private static final String PATH = "textures/entities/trolls/snow/snow_troll1.png";

    public SnowTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SnowTrollModel(ctx.bakeLayer(EntityModelLayersME.SNOW_TROLL)), 1.1f);
    }

    @Override
    public TrollEntityRenderState createRenderState() {
        return new TrollEntityRenderState();
    }
    @Override
    public Identifier getTextureLocation(TrollEntityRenderState state) {
        return Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH);
    }
}
