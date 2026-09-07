package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.world.item.equipment.ArmorType;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

public class CustomBootsItem extends ArmorItem {

    public CustomBootsItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings.humanoidArmor(material.material(), ArmorType.BOOTS).stacksTo(1));
    }
}
