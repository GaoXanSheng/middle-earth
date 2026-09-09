package net.sevenstars.middleearth.entity.beasts.cave_troll.feature;

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
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntityRenderState;

public class CaveTrollSaddleFeatureRenderer extends RenderLayer<CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final Identifier SADDLE_TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/trolls/cave/cave_troll_platform.png");
    private final CaveTrollSaddleModel model;

    public CaveTrollSaddleFeatureRenderer(RenderLayerParent<CaveTrollEntityRenderState, CaveTrollEntityModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        model = new CaveTrollSaddleModel(loader.bakeLayer(EntityModelLayersME.CAVE_TROLL_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, CaveTrollEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            model.setupAnim(state);
            submitNodeCollector.order(0).submitModel(model, state, poseStack, RenderTypes.armorCutoutNoCull(SADDLE_TEXTURE), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            if (itemStack.hasFoil()) {

                submitNodeCollector.order(1).submitModel(model, state, poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            }
        }
    }
}
