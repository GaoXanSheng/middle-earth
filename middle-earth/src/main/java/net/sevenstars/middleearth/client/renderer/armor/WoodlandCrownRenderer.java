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
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.HelmetAddonModel;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class WoodlandCrownRenderer implements ArmorRenderer {
    private final CustomHelmetModel customHelmetModel = new CustomHelmetModel(CustomHelmetModel.getTexturedModelData().bakeRoot());
    private HelmetAddonModel helmetAddonModel;

    public WoodlandCrownRenderer(HelmetAddonModel helmetModel) {
        this.helmetAddonModel = helmetModel;
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.HEAD) {
            ModArmorRenderer.setAllVisible(customHelmetModel, false);
            customHelmetModel.head.visible = true;
            customHelmetModel.hat.visible = true;
            customHelmetModel.body.visible = true;
            customHelmetModel.leftArm.visible = true;
            customHelmetModel.rightArm.visible = true;

            if (DyeablePiecesME.dyeablePieces.containsKey(stack.getItem())) {
                dyeable = true;
            }

            SeasonDataComponent biomeDataComponent = stack.getComponents().get(DataComponentTypesME.SEASON_DATA);
            SeasonDataComponent.Season season = null;
            if (biomeDataComponent != null) season = biomeDataComponent.season();

            String texture = "textures/models/armor/woodland_realm_crown";

            if (season != null) {
                if (season.equals(SeasonDataComponent.Season.SPRING)) {
                    texture += "_spring";
                } else if (season.equals(SeasonDataComponent.Season.SUMMER)) {
                    texture += "_summer";
                } else if (season.equals(SeasonDataComponent.Season.AUTUMN)) {
                    texture += "_autumn";
                } else if (season.equals(SeasonDataComponent.Season.WINTER)) {
                    texture += "_winter";
                }
            }

            texture += ".png";

            ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, customHelmetModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture), dyeable);
            if (this.helmetAddonModel != null) {
                ModArmorRenderer.setAllVisible(this.helmetAddonModel, false);
                this.helmetAddonModel.head.visible = true;
                ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, this.helmetAddonModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture.replaceAll(".png", "_addition.png")), dyeable);
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
                    ModArmorRenderer.renderDyeableAttachment(matrices, collector, humanoidRenderState, light, helmetAttachmentModel, textureHelmetAttachment, color);
                    if (Boolean.TRUE.equals(DyeablePiecesME.dyeableHelmetAttachments.get(hoodDataComponent.helmetAttachment()))) {
                        ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, helmetAttachmentModel, Identifier.fromNamespaceAndPath(textureHelmetAttachment.getNamespace(), textureHelmetAttachment.getPath().replaceAll(".png", "_overlay.png")));
                    }
                } else {
                    ModArmorRenderer.renderTranslucentPiece(matrices, collector, humanoidRenderState, light, helmetAttachmentModel, textureHelmetAttachment);
                }
            }
        }
    }
}
