package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatBeads;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

import java.util.Map;

public class BroadhoofGoatBeadsFeatureRenderer extends RenderLayer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/beads/";
    private static final Identifier INVISIBLE_ID = Identifier.withDefaultNamespace("invisible");

    private static final Map<BroadhoofGoatBeads, Identifier> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatBeads.NONE,
                    INVISIBLE_ID,
                    BroadhoofGoatBeads.LEATHER,
                    MiddleEarth.of(PATH + "broadhoof_goat_leather_beads.png"),
                    BroadhoofGoatBeads.COAL,
                    MiddleEarth.of(PATH + "broadhoof_goat_coal_beads.png"),
                    BroadhoofGoatBeads.COPPER,
                    MiddleEarth.of(PATH + "broadhoof_goat_copper_beads.png"),
                    BroadhoofGoatBeads.GOLD,
                    MiddleEarth.of(PATH + "broadhoof_goat_gold_beads.png"),
                    BroadhoofGoatBeads.ALMANDINE,
                    MiddleEarth.of(PATH + "broadhoof_goat_almandine_beads.png")
            )
    );

    public BroadhoofGoatBeadsFeatureRenderer(RenderLayerParent<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void submit(
            PoseStack matrixStack, SubmitNodeCollector vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        Identifier identifier = TEXTURES.get(state.beads);
        if (identifier != INVISIBLE_ID && !state.isInvisible) {
            // TODO 26.2: was translucent entity overlay; rendered as generic cutout overlay
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), identifier, matrixStack, vertexConsumerProvider, i, state, -1, 0);
        }
    }
}
