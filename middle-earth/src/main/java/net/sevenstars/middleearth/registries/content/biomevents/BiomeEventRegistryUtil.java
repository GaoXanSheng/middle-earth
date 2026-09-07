package net.sevenstars.middleearth.registries.content.biomevents;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;

import java.util.ArrayList;
import java.util.List;

public class BiomeEventRegistryUtil {
    private static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;

    public static List<ResourceKey<Biome>> biomeEntries = new ArrayList<>();

    public static void addBiomeEntry(ResourceKey<Biome> biome) {
        if(biomeEntries == null) {
            biomeEntries = new ArrayList<>();
        }
        if(biomeEntries.contains(biome)) {
            return;
        }
        biomeEntries.add(biome);
    }

    public static void removeBiomeEntry(Identifier biomeId) {
        if (biomeEntries != null) {
            biomeEntries.removeIf(entry -> entry.identifier().equals(biomeId));
        }
    }

    public static void registerDefaults(BootstrapContext<BiomeEventData> context, HolderGetter<BiomeEventData> registryEntryLookup) {
        if(biomeEntries == null)
            return;

        for(ResourceKey<Biome> key : biomeEntries){
            DynamicRegistriesME.register(context, registryEntryLookup, of(key), GenericHostilesBiomeEventPool.EMPTY);
        }
    }

    public static ResourceKey<BiomeEventData> of(ResourceKey<Biome> key){
        return DynamicRegistriesME.of(BIOME_EVENT_KEY, key.identifier());
    }

    public static void register(BootstrapContext<BiomeEventData> context, HolderGetter<BiomeEventData> registryEntryLookup, ResourceKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        BiomeEventRegistryUtil.removeBiomeEntry(registryKey.identifier());
        // [LANG datagen]
        // None
    }

    public static ResourceKey<Structure> register(String name) {
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }
}
