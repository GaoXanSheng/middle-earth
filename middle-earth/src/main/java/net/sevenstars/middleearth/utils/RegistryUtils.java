package net.sevenstars.middleearth.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class RegistryUtils {
    public static <V, T extends V> T register(Registry<V> registry, String name, T entry) {
        return Registry.register(registry, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), entry);
    }
}
