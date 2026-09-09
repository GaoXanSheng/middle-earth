package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

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
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class BroadhoofGoatSaddleFeatureRenderer extends RenderLayer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final Identifier SADDLE_TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/broadhoof_goat/feature/broadhoof_goat_saddle.png");
    private final BroadhoofGoatSaddleModel model;

    public BroadhoofGoatSaddleFeatureRenderer(RenderLayerParent<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new BroadhoofGoatSaddleModel(loader.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.saddle;
        if(!itemStack.isEmpty()) {
            this.model.setupAnim(state);
            // TODO 26.2: was armorCutoutNoCull + glint vertex pipeline; now generic cutout overlay
            submitNodeCollector.order(0).submitModel(this.model, state, poseStack, RenderTypes.armorCutoutNoCull(SADDLE_TEXTURE), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            if (itemStack.hasFoil()) {

                submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            }
        }
    }
}
