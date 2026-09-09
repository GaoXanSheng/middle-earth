package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargEyeVariant;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;

import java.util.Map;

public class WargEyesFeatureRenderer extends RenderLayer<WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/eyes/";
    private static final Map<WargEyeVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargEyeVariant.class), (map) -> {
                map.put(WargEyeVariant.BLUE,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_eyes_blue.png"));
                map.put(WargEyeVariant.ORANGE,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_eyes_orange.png"));
            });

    public WargEyesFeatureRenderer(RenderLayerParent<WargEntityRenderState, WargModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        Identifier eyeTexture = LOCATION_BY_VARIANT.get(state.eyeVariant);
        if(eyeTexture != null) {
            submitNodeCollector.order(1).submitModel(this.getParentModel(), state, poseStack, RenderTypes.entityTranslucentEmissive(eyeTexture), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);
        }
    }
}
