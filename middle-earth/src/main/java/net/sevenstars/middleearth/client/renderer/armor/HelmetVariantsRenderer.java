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
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.ArmorVariantDataComponent;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class HelmetVariantsRenderer implements ArmorRenderer {
    private final CustomHelmetModel customHelmetModel = new CustomHelmetModel(CustomHelmetModel.getTexturedModelData().bakeRoot());
    private HelmetAddonModel helmetAddonModel;

    public HelmetVariantsRenderer(HelmetAddonModel helmetModel) {
        this.helmetAddonModel = helmetModel;
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            if (DyeablePiecesME.dyeablePieces.containsKey(stack.getItem())) {
                dyeable = true;
            }

            ArmorVariantDataComponent armorVariantDataComponent = stack.getComponents().get(DataComponentTypesME.ARMOR_VARIANT_DATA);
            int variant = 0;
            if (armorVariantDataComponent != null) variant = armorVariantDataComponent.id();
            String texture = "textures/models/armor/" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png";

            ModArmorRenderer.setAllVisible(this.customHelmetModel, false);
            this.customHelmetModel.head.visible = true;
            this.customHelmetModel.hat.visible = true;
            this.customHelmetModel.body.visible = true;
            this.customHelmetModel.leftArm.visible = true;
            this.customHelmetModel.rightArm.visible = true;
            ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, contextModel, this.customHelmetModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture), dyeable);

            if (this.helmetAddonModel != null) {
                ModArmorRenderer.setAllVisible(this.helmetAddonModel, false);
                this.helmetAddonModel.head.visible = true;

                if (texture.contains("_helmet.png")) {
                    if (variant > 0) {
                        String newTex = texture.replaceAll("_helmet.png", "_addition_" + variant + ".png");
                        ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, contextModel, this.helmetAddonModel,
                                Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, newTex), dyeable);
                    } else {
                        ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, contextModel, this.helmetAddonModel,
                                Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture.replaceAll("_helmet.png", "_addition.png")), dyeable);
                    }
                } else {
                    ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, contextModel, this.helmetAddonModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
                }
            }

            HelmetAttachmentDataComponent hoodDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);

            if (hoodDataComponent != null) {
                Identifier textureHelmetAttachment;
                HelmetAddonModel helmetAttachmentModel;
                if (hoodDataComponent.down()) {
                    textureHelmetAttachment = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + hoodDataComponent.helmetAttachment().getName().toLowerCase() + "_down.png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(hoodDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getArmoredDownModel();
                } else {
                    textureHelmetAttachment = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/models/helmet_attachment/" + hoodDataComponent.helmetAttachment().getName().toLowerCase() + ".png");
                    helmetAttachmentModel = ArmorModelsME.ModHelmetAttachmentPairedModels.valueOf(hoodDataComponent.helmetAttachment().getName().toUpperCase()).getModel().getArmoredModel();
                }
                ModArmorRenderer.setAllVisible(helmetAttachmentModel, false);
                helmetAttachmentModel.head.visible = true;
                helmetAttachmentModel.hat.visible = true;
                if (DyeablePiecesME.dyeableHelmetAttachments.containsKey(hoodDataComponent.getHelmetAttachment())) {
                    int color = (0xFF << 24) | (hoodDataComponent.helmetAttachmentColor() & 0xFFFFFF);
                    ModArmorRenderer.renderDyeableAttachment(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, textureHelmetAttachment, color);
                    if (Boolean.TRUE.equals(DyeablePiecesME.dyeableHelmetAttachments.get(hoodDataComponent.helmetAttachment()))) {
                        ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, Identifier.fromNamespaceAndPath(textureHelmetAttachment.getNamespace(), textureHelmetAttachment.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, contextModel, helmetAttachmentModel, textureHelmetAttachment);
                }
            }
        }
    }
}
