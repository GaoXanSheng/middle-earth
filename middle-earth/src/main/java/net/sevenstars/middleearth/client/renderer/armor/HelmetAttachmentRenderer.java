package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class HelmetAttachmentRenderer implements ArmorRenderer {

    public HelmetAttachmentRenderer() {
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        if (slot != EquipmentSlot.HEAD) {
            return;
        }
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (helmetAttachmentDataComponent == null) {
            return;
        }

        Identifier texture;
        HelmetAddonModel helmetAttachmentModel;
        if (helmetAttachmentDataComponent.down()) {
            texture = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + helmetAttachmentDataComponent.helmetAttachment().getName().toLowerCase() + "_down.png");
            helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(helmetAttachmentDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getUnarmoredDownModel();
        } else {
            texture = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + helmetAttachmentDataComponent.helmetAttachment().getName().toLowerCase() + ".png");
            helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(helmetAttachmentDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getUnarmoredModel();
        }
        ModArmorRenderer.setAllVisible(helmetAttachmentModel, false);
        helmetAttachmentModel.head.visible = true;
        helmetAttachmentModel.hat.visible = true;

        if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(helmetAttachmentDataComponent.getHelmetAttachment())) {
            int color = (0xFF << 24) | (helmetAttachmentDataComponent.helmetAttachmentColor() & 0xFFFFFF);
            ModArmorRenderer.renderDyeableAttachment(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, texture, color);
            if (Boolean.TRUE.equals(DyeablePiecesME.dyeableHelmetAttachments.get(helmetAttachmentDataComponent.getHelmetAttachment()))) {
                ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, Identifier.fromNamespaceAndPath(texture.getNamespace(), texture.getPath().replaceAll(".png", "_overlay.png")));
            }
        } else {
            ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, texture);
        }
    }
}
