package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class ModArmorRenderer implements ArmorRenderer {

    public ModArmorRenderer() {
    }

    static void setAllVisible(HumanoidModel<HumanoidRenderState> model, boolean visible) {
        for (ModelPart part : model.allParts()) {
            part.visible = visible;
        }
    }

    static void renderArmor(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                            ItemStack stack, HumanoidModel<HumanoidRenderState> model, Identifier texture, boolean dyeable) {
        if (dyeable) {
            renderDyeable(matrices, collector, state, light, stack, model, texture);
            if (Boolean.TRUE.equals(DyeablePiecesME.dyeablePieces.get(stack.getItem()))) {
                renderPart(matrices, collector, state, light, model, overlay(texture, "_overlay"));
            }
        } else {
            renderPart(matrices, collector, state, light, model, texture);
        }
    }

    static void renderDyeable(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                              ItemStack stack, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        int color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        renderColored(matrices, collector, state, light, model, texture, color);
    }

    static void renderPart(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        renderColored(matrices, collector, state, light, model, texture, -1);
    }

    static void renderTranslucentPiece(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                       HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        // 10-arg overload: (model, state, matrices, renderType, light, overlay, tint, sprite, outlineColor, crumbling)
        collector.submitModel(model, state, matrices, RenderTypes.entityTranslucent(texture),
                light, OverlayTexture.NO_OVERLAY, -1, null, 0, null);
    }
    static void renderDyeableAttachment(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                        HumanoidModel<HumanoidRenderState> model, Identifier texture, int color) {
        renderColored(matrices, collector, state, light, model, texture, color);
    }

    private static void renderColored(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light, HumanoidModel<HumanoidRenderState> model, Identifier texture, int color) {
        // Pass the dye as tintedColor; the 8-arg overload would misroute it into outlineColor.
        collector.submitModel(model, state, matrices, RenderTypes.entityCutout(texture), light, OverlayTexture.NO_OVERLAY, color, null, 0, null);
    }

    private static Identifier overlay(Identifier texture, String suffix) {
        String path = texture.getPath();
        return Identifier.fromNamespaceAndPath(texture.getNamespace(),
                path.endsWith(".png") ? path.substring(0, path.length() - 4) + suffix + ".png" : path + suffix);
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
    }
}
