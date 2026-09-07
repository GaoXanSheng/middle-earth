package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class HelmetAttachmentItem extends Item{

    public HelmetAttachmentItem(Item.Properties settings, ExtendedArmorMaterial armorMaterial) {
        super(settings.humanoidArmor(armorMaterial.material(), ArmorType.HELMET).stacksTo(1));
    }

    public static void toggleHelmetAttachmentState(ServerPlayer player, ItemStack stack){
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = stack.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        if (helmetAttachmentDataComponent != null){
            if (helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null) {
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendOverlayMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"));
            } else if (!helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null){
                stack.set(DataComponentTypesME.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendOverlayMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"));
            }
        }
    }
}
