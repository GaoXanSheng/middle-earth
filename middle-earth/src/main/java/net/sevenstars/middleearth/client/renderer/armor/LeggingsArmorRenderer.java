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
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;

public class LeggingsArmorRenderer implements ArmorRenderer {

    private final CustomLeggingsModel customLeggingsModel = new CustomLeggingsModel(CustomLeggingsModel.getTexturedModelData().bakeRoot());

    public LeggingsArmorRenderer() {
    }

    @Override
    public void render(PoseStack matrices, SubmitNodeCollector collector, ItemStack stack, HumanoidRenderState humanoidRenderState, EquipmentSlot slot, int light, HumanoidModel<HumanoidRenderState> contextModel) {
        boolean dyeable = false;

        if (slot == EquipmentSlot.LEGS) {
            if (DyeablePiecesME.dyeablePieces.containsKey(stack.getItem())) {
                dyeable = true;
            }

            String texture = "textures/models/armor/" + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + ".png";

            ModArmorRenderer.setAllVisible(this.customLeggingsModel, false);
            this.customLeggingsModel.body.visible = true;
            this.customLeggingsModel.rightLeg.visible = true;
            this.customLeggingsModel.leftLeg.visible = true;
            ModArmorRenderer.renderArmor(matrices, collector, humanoidRenderState, light, stack, contextModel, this.customLeggingsModel, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, texture), dyeable);
        }
    }
}
