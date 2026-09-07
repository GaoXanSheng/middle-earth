package net.sevenstars.middleearth.entity.spider;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.EntityModelLayersME;

@Environment(EnvType.CLIENT)
public class EnwebbedFeatureRenderer <S extends HumanoidRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/spiders/enwebbed.png");

    private final EnwebbedModel model;

    public EnwebbedFeatureRenderer(RenderLayerParent<S, M> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new EnwebbedModel(loader.bakeLayer(EntityModelLayersME.ENWEBBED));
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, int light, S bipedEntityRenderState, float f, float g) {
        ArmedEntityRenderStateAccess renderStateAccess = ((ArmedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess.isRestrained()) {
            EnwebbedModel entityModel = this.model;
            entityModel.setupAnim(bipedEntityRenderState);
            submitNodeCollector.submitModel(entityModel, bipedEntityRenderState, matrixStack, RenderTypes.entityTranslucent(TEXTURE), light, OverlayTexture.NO_OVERLAY, -1, null);
        }
    }
}
