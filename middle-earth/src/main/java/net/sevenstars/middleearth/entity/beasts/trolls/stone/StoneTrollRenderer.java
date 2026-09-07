package net.sevenstars.middleearth.entity.beasts.trolls.stone;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;

public class StoneTrollRenderer extends MobRenderer<StoneTrollEntity, TrollEntityRenderState, StoneTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/stone_troll1.png";

    public StoneTrollRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneTrollModel(context.bakeLayer(EntityModelLayersME.STONE_TROLL)), 1.1f);
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
