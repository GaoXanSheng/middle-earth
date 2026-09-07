package net.sevenstars.middleearth.item.utils;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.trim.TrimPattern;

public class SmithingTrimPatternsME {
    public static final ResourceKey<TrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(BootstrapContext<TrimPattern> registry) {
        register(registry, SMITHING_PART);
    }

    public static void register(BootstrapContext<TrimPattern> registry, ResourceKey<TrimPattern> key) {
        TrimPattern armorTrimPattern = new TrimPattern(key.identifier(), Component.translatable(Util.makeDescriptionId("trim_pattern", key.identifier())), false);
        registry.register(key, armorTrimPattern);
    }

    private static ResourceKey<TrimPattern> of(String id) {
        return ResourceKey.create(Registries.TRIM_PATTERN, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, id));
    }
}
