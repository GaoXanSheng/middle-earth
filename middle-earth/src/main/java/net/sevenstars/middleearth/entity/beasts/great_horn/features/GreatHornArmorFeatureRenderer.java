package net.sevenstars.middleearth.entity.beasts.great_horn.features;

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
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

public class GreatHornArmorFeatureRenderer extends RenderLayer<GreatHornEntityRenderState, GreatHornModel> {
    private final GreatHornArmorModel model;
    private final EquipmentLayerRenderer equipmentRenderer;

    public GreatHornArmorFeatureRenderer(RenderLayerParent<GreatHornEntityRenderState, GreatHornModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new GreatHornArmorModel(loader.bakeLayer(EntityModelLayersME.GREAT_HORN_ARMOR));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        Equippable equippableComponent = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippableComponent != null && !equippableComponent.assetId().isEmpty()) {
            model.setupAnim(state);
            this.equipmentRenderer.renderLayers(EquipmentClientInfo.LayerType.HORSE_BODY, equippableComponent.assetId().get(), model, state, itemStack, poseStack, submitNodeCollector, light, 0);
            // TODO 26.2: custom dyed multi-piece great horn armour (custom texture path + DyeablePiecesME overlay) awaits art pass on the new equipment pipeline
        }
    }
}
