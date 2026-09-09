package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornSaddleFeatureRenderer extends RenderLayer<GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornSaddleModel model;
    private static final String PATH = "textures/entities/great_horn/feature/great_horn_saddle";

    public GreatHornSaddleFeatureRenderer(RenderLayerParent<GreatHornEntityRenderState, GreatHornModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornSaddleModel(loader.bakeLayer(EntityModelLayersME.GREAT_HORN_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            Identifier texture = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + (state.blueSaddle ? "_blue.png" : ".png"));
            model.setupAnim(state);
            submitNodeCollector.order(0).submitModel(model, state, poseStack, RenderTypes.armorCutoutNoCull(texture), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            if (itemStack.hasFoil()) {

                submitNodeCollector.order(1).submitModel(model, state, poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            }
        }
    }
}
