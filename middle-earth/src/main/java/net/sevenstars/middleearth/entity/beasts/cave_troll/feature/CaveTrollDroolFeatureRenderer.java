package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollDroolFeatureRenderer extends RenderLayer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_green_drooling.png");
    public CaveTrollDroolFeatureRenderer(RenderLayerParent<CaveTrollEntityRenderState, CaveTrollEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        if(state.tameness < 50 && state.isTame) {
            // TODO 26.2: was entityCutoutNoCull overlay on parent model; now generic cutout overlay
            submitNodeCollector.order(0).submitModel(this.getParentModel(), state, poseStack, RenderTypes.armorCutoutNoCull(TEXTURE), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);
        }
    }
}
