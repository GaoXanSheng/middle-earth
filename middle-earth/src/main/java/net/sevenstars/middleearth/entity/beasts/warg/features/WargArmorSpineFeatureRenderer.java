package net.sevenstars.middleearth.entity.beasts.warg.features;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.item.EquipmentItemsME;

public class WargArmorSpineFeatureRenderer extends RenderLayer<WargEntityRenderState, WargModel> {
    private static final Identifier BONE_TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_bone_spine_addon.png");
    private static final Identifier MORDOR_TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_mordor_spine_addon.png");
    private final WargArmorBaseAddonsModel model;

    public WargArmorSpineFeatureRenderer(RenderLayerParent<WargEntityRenderState, WargModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorBaseAddonsModel(loader.bakeLayer(EntityModelLayersME.WARG_ARMOR_ADDONS_SPINE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        Identifier texture = null;
        if(itemStack.is(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR)) {
            texture = BONE_TEXTURE;
        }
        else if(itemStack.is(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR) || itemStack.is(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)) {
            texture = MORDOR_TEXTURE;
        }
        if(texture != null) {
            this.model.setupAnim(state);
            // TODO 26.2: was armorCutoutNoCull + glint vertex pipeline; now generic cutout overlay
            RenderLayer.renderColoredCutoutModel(this.model, texture, poseStack, submitNodeCollector, light, state, -1, 0);
        }
    }
}
