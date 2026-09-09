package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatPattern;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class BroadhoofGoatPatternFeatureRenderer extends RenderLayer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/patterns/";
    private static final Identifier INVISIBLE_ID = Identifier.withDefaultNamespace("invisible");

    private static final Map<BroadhoofGoatPattern, Identifier> TEXTURES = Maps.newEnumMap(
            Map.ofEntries(
                    Map.entry(BroadhoofGoatPattern.NONE, INVISIBLE_ID),

                    Map.entry(BroadhoofGoatPattern.BLACK_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BLACK_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_black_strip.png")),

                    Map.entry(BroadhoofGoatPattern.BROWN_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_mask.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_patches.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_spots.png")),
                    Map.entry(BroadhoofGoatPattern.BROWN_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_brown_strip.png")),

                    Map.entry(BroadhoofGoatPattern.PALE_MASK,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_mask.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_PATCHES,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_patches.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SIDE_PATCH,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_side_patch.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_SPOTS,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_spots.png")),
                    Map.entry(BroadhoofGoatPattern.PALE_STRIPS,
                            MiddleEarth.of(PATH + "broadhoof_goat_pale_strip.png")),

                    Map.entry(BroadhoofGoatPattern.GRAY_BEARD,
                            MiddleEarth.of(PATH + "broadhoof_goat_gray_beard.png"))

            )
    );

    public BroadhoofGoatPatternFeatureRenderer(RenderLayerParent<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> featureRendererContext) {
        super(featureRendererContext);
    }

    public void submit(
            PoseStack matrixStack, SubmitNodeCollector vertexConsumerProvider, int i, BroadhoofGoatEntityRenderState state, float f, float g
    ) {
        Identifier identifier = TEXTURES.get(state.pattern);
        if (identifier != INVISIBLE_ID && !state.isInvisible) {
            vertexConsumerProvider.order(0).submitModel(this.getParentModel(), state, matrixStack, RenderTypes.armorTranslucent(identifier), i, OverlayTexture.NO_OVERLAY, -1, null, 0, null);
        }
    }
}
