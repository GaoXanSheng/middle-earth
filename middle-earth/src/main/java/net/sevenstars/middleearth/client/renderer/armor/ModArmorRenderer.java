package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class ModArmorRenderer implements ArmorRenderer {

    public ModArmorRenderer() {
    }

    /**
     * Toggles only the top-level humanoid parts (same as the old HumanoidModel#setVisible):
     * child parts (hat, cape, extensions) keep their own flag, so an enabled parent still renders them.
     */
    static void setAllVisible(HumanoidModel<HumanoidRenderState> model, boolean visible) {
        model.head.visible = visible;
        model.hat.visible = visible;
        model.body.visible = visible;
        model.rightArm.visible = visible;
        model.leftArm.visible = visible;
        model.rightLeg.visible = visible;
        model.leftLeg.visible = visible;
    }

    static void renderArmor(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                            ItemStack stack, HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture, boolean dyeable) {
        if (dyeable) {
            renderDyeable(matrices, collector, state, light, stack, contextModel, model, texture);
            if (Boolean.TRUE.equals(DyeablePiecesME.dyeablePieces.get(stack.getItem()))) {
                renderPart(matrices, collector, state, light, contextModel, model, overlay(texture, "_overlay"));
            }
        } else {
            renderPart(matrices, collector, state, light, contextModel, model, texture);
        }
    }

    static void renderDyeable(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                              ItemStack stack, HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        int color = DyedItemColor.getOrDefault(stack, DyedItemColor.LEATHER_COLOR);
        renderColored(matrices, collector, state, light, contextModel, model, texture, color, false, false);
    }

    static void renderPart(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                           HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        renderColored(matrices, collector, state, light, contextModel, model, texture, -1, false, false);
    }

    static void renderTranslucentPiece(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                       HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        renderColored(matrices, collector, state, light, contextModel, model, texture, -1, true, false);
    }

    static void renderDyeableAttachment(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                        HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture, int color) {
        renderColored(matrices, collector, state, light, contextModel, model, texture, color, false, false);
    }

    /**
     * Cape-style pieces: runs the delegate model's own setupAnim (swing physics) after the transform copy,
     * matching the old copyTransforms -> setAngles order.
     */
    static void renderPartWithAngles(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                     HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture) {
        renderColored(matrices, collector, state, light, contextModel, model, texture, -1, false, true);
    }

    static void renderDyeableAttachmentWithAngles(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                                  HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model, Identifier texture, int color) {
        renderColored(matrices, collector, state, light, contextModel, model, texture, color, false, true);
    }

    private static void renderColored(PoseStack matrices, SubmitNodeCollector collector, HumanoidRenderState state, int light,
                                      HumanoidModel<HumanoidRenderState> contextModel, HumanoidModel<HumanoidRenderState> model,
                                      Identifier texture, int color, boolean translucent, boolean setDelegateAngles) {
        RenderType renderType = translucent ? RenderTypes.entityTranslucent(texture) : RenderTypes.entityCutout(texture);
        // The 12-arg overload's int parameter is OUTLINE COLOR, not the dye tint — passing the dye
        // color there drew a colored glow outline around every piece and dropped the tint entirely.
        // Use the 14-arg overload: tint goes to tintedColor, outlineColor stays 0 (no outline).
        ArmorRenderer.submitTransformCopyingModel(contextModel, state, model, state, setDelegateAngles,
                collector, matrices, renderType, light, OverlayTexture.NO_OVERLAY, color, (TextureAtlasSprite) null, 0, null);
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
