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
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class BackAttachmentRenderer implements ArmorRenderer {

    public BackAttachmentRenderer() {
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        if (slot != EquipmentSlot.CHEST) {
            return;
        }
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        if (backAttachmentDataComponent == null) {
            return;
        }

        ChestplateAddonModel backAttachmentModel = ArmorModelsME.ModBackAttachmentPairedModels.valueOf(backAttachmentDataComponent.backAttachment().getName().toUpperCase()).getModel().getUnarmoredModel();
        ModArmorRenderer.setAllVisible(backAttachmentModel, false);
        backAttachmentModel.body.visible = true;
        backAttachmentModel.rightArm.visible = true;
        backAttachmentModel.leftArm.visible = true;
        backAttachmentModel.rightLeg.visible = true;
        backAttachmentModel.leftLeg.visible = true;

        Identifier texture = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + ".png");
        if (DyeablePiecesME.dyeableBackAttachments.containsKey(backAttachmentDataComponent.getBackAttachment())) {
            int color = (0xFF << 24) | (backAttachmentDataComponent.backAttachmentColor() & 0xFFFFFF);
            ModArmorRenderer.renderDyeableAttachmentWithAngles(matrices, collector, humanoidRenderState, light, contextModel, backAttachmentModel, texture, color);
            if (Boolean.TRUE.equals(DyeablePiecesME.dyeableBackAttachments.get(backAttachmentDataComponent.getBackAttachment()))) {
                ModArmorRenderer.renderPartWithAngles(matrices, collector, humanoidRenderState, light, contextModel, backAttachmentModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + backAttachmentDataComponent.backAttachment().getName() + "_overlay.png"));
            }
        } else {
            ModArmorRenderer.renderPartWithAngles(matrices, collector, humanoidRenderState, light, contextModel, backAttachmentModel, texture);
        }
    }
}
