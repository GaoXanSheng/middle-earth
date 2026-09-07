package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.world.item.equipment.ArmorType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomLeggingsItem extends ArmorItem {

    public CustomLeggingsItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings.humanoidArmor(material.material(), ArmorType.LEGGINGS).stacksTo(1));
    }
}
