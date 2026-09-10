package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.sevenstars.middleearth.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> CAN_WEAR_GOAT_ARMOR = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "can_wear_goat_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_WARG_ARMOR = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "can_wear_warg_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_GREAT_HORN_ARMOR = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "can_wear_great_horn_armor"));
    public static TagKey<EntityType<?>> NPC_ALWAYS_TARGET = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "npc_always_target"));
}
