package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntityRenderState;

public class PetrifiedTrollRenderer extends MobRenderer<PetrifiedTrollEntity, LivingEntityRenderState, PetrifiedTrollModel> {
    private static final String PATH = "textures/entities/trolls/stone/";

    public PetrifiedTrollRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new PetrifiedTrollModel(ctx.bakeLayer(EntityModelLayersME.PETRIFIED_TROLL)), 0.0f);
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new TrollEntityRenderState();
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "petrified_stone_troll.png");
    }
}
