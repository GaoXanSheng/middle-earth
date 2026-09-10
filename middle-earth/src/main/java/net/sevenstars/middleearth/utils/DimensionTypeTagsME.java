package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.dimension.DimensionType;
import net.sevenstars.middleearth.MiddleEarth;

public class DimensionTypeTagsME {
    public static final TagKey<DimensionType> NPC_SPAWN_RULES = TagKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "npc_spawn_rules"));
}
