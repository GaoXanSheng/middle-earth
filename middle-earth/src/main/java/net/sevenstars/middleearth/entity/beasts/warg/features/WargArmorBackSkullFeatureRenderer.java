package net.sevenstars.middleearth.entity.beasts.warg.features;

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
import net.sevenstars.middleearth.entity.beasts.warg.WargEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.warg.WargModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;

public class WargArmorBackSkullFeatureRenderer extends RenderLayer<WargEntityRenderState, WargModel> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_addons.png");
    private final WargArmorTopAddonsModel model;

    public WargArmorBackSkullFeatureRenderer(RenderLayerParent<WargEntityRenderState, WargModel> context, EntityModelSet loader, EquipmentLayerRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorTopAddonsModel(loader.bakeLayer(EntityModelLayersME.WARG_ARMOR_ADDONS_BACK));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        MountArmorAddonComponent component = itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA);
        if(component != null && component.topArmorAddon() && itemStack.is(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR)) {
            this.model.setupAnim(state);
            // TODO 26.2: was armorCutoutNoCull + glint vertex pipeline; now generic cutout overlay
            submitNodeCollector.order(0).submitModel(this.model, state, poseStack, RenderTypes.armorCutoutNoCull(TEXTURE), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            if (itemStack.hasFoil()) {

                submitNodeCollector.order(1).submitModel(this.model, state, poseStack, RenderTypes.armorEntityGlint(), light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);

            }
        }
    }
}
