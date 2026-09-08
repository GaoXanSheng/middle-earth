package net.sevenstars.middleearth.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class ChestplateArmorRenderer implements ArmorRenderer {

    private final CustomChestplateModel customChestplateModel = new CustomChestplateModel(CustomChestplateModel.getTexturedModelData().bakeRoot());

    private ChestplateAddonModel chestplateAddonModel;

    public ChestplateArmorRenderer() {
    }

    public ChestplateArmorRenderer(ChestplateAddonModel chestplateModel) {
        this.chestplateAddonModel = chestplateModel;
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.CHEST) {
            ModArmorRenderer.setAllVisible(customChestplateModel, false);
            customChestplateModel.body.visible = true;
            customChestplateModel.rightArm.visible = true;
            customChestplateModel.leftArm.visible = true;
            customChestplateModel.rightLeg.visible = true;
            customChestplateModel.leftLeg.visible = true;

            if (DyeablePiecesME.dyeablePieces.containsKey(stack.getItem())) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png";
            ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, customChestplateModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.chestplateAddonModel != null) {
                ModArmorRenderer.setAllVisible(this.chestplateAddonModel, false);
                this.chestplateAddonModel.body.visible = true;
                this.chestplateAddonModel.rightArm.visible = true;
                this.chestplateAddonModel.leftArm.visible = true;
                if (texture.contains("_chestplate.png")) {
                    ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, this.chestplateAddonModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture.replaceAll("_chestplate.png", "_addition.png")), dyeable);
                } else {
                    ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, this.chestplateAddonModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            BackAttachmentDataComponent capeDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
            if (capeDataComponent != null) {
                ChestplateAddonModel capeModel = ArmorModelsME.ModBackAttachmentPairedModels.valueOf(capeDataComponent.backAttachment().getName().toUpperCase()).getModel().getArmoredModel();
                ModArmorRenderer.setAllVisible(capeModel, false);
                capeModel.body.visible = true;
                capeModel.rightArm.visible = true;
                capeModel.leftArm.visible = true;
                capeModel.rightLeg.visible = true;
                capeModel.leftLeg.visible = true;
                // Cape swing physics run through CloakCapeModel.setupAnim, invoked by submitModel.

                if (DyeablePiecesME.dyeableBackAttachments.containsKey(capeDataComponent.getBackAttachment())) {
                    int color = (0xFF << 24) | (capeDataComponent.backAttachmentColor() & 0xFFFFFF);
                    ModArmorRenderer.renderDyeableAttachment(matrices, collector, humanoidRenderState, light, capeModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + ".png"), color);
                    if (Boolean.TRUE.equals(DyeablePiecesME.dyeableBackAttachments.get(capeDataComponent.getBackAttachment()))) {
                        ModArmorRenderer.renderPart(matrices, collector, humanoidRenderState, light, capeModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + "_overlay.png"));
                    }
                } else {
                    ModArmorRenderer.renderPart(matrices, collector, humanoidRenderState, light, capeModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/back_attachment/" + capeDataComponent.backAttachment().getName() + ".png"));
                }
            }
        }
    }
}
