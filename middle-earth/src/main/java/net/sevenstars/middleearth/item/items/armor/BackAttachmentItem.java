package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class BackAttachmentItem extends Item {

    public BackAttachmentItem(Properties settings, ExtendedArmorMaterial material) {
        super(settings.humanoidArmor(material.material(), ArmorType.CHESTPLATE).stacksTo(1));
    }
}
