package net.sevenstars.middleearth.entity.beasts.broadhoof.features;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatModel;

public class BroadhoofGoatArmorFeatureRenderer extends RenderLayer<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private final BroadhoofGoatArmorModel model;
    private final EquipmentLayerRenderer equipmentRenderer;

    public BroadhoofGoatArmorFeatureRenderer(RenderLayerParent<BroadhoofGoatEntityRenderState, BroadhoofGoatModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.equipmentRenderer = equipmentRenderer;
        this.model = new BroadhoofGoatArmorModel(loader.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_ARMOR));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        Equippable equippableComponent = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.assetId().isEmpty()) {
            model.setupAnim(state);
            this.equipmentRenderer.renderLayers(EquipmentClientInfo.LayerType.HORSE_BODY, equippableComponent.assetId().get(), model, state, itemStack, poseStack, submitNodeCollector, light, 0);
        }
    }
}
