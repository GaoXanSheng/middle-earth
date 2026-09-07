package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.world.item.equipment.ArmorMaterial;

public record ExtendedArmorMaterial(ArmorMaterial material, int durabilityModifier, ArmorMaterialsME.Tiers tier) {
}
