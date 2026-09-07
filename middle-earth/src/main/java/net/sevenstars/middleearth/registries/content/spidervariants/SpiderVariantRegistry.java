package net.sevenstars.middleearth.registries.content.spidervariants;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

public class SpiderVariantRegistry {
    private static final ResourceKey<Registry<SpiderVariant>> SPIDER_VARIANTS_KEY = DynamicRegistriesME.SPIDER_VARIANTS;

    public static final ResourceKey<SpiderVariant> MIRKWOOD = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("mirkwood"));
    public static final ResourceKey<SpiderVariant> CAVE = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("cave"));
    public static final ResourceKey<SpiderVariant> MORDOR = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("mordor"));
    public static final ResourceKey<SpiderVariant> DEFAULT = MIRKWOOD;

    public static void bootstrap(BootstrapContext<SpiderVariant> context) {
        HolderGetter<SpiderVariant> registryEntryLookup = context.lookup(SPIDER_VARIANTS_KEY);

        register(context, registryEntryLookup, MIRKWOOD, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mirkwood"),
            SpawnPrioritySelectors.fallback(0)));
        register(context, registryEntryLookup, CAVE, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("blind"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.create(Registries.BIOME, MiddleEarth.of("is_cave")), 1)));
        register(context, registryEntryLookup, MORDOR, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mordor"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.create(Registries.BIOME, MiddleEarth.of("is_mordor")), 2)));
    }

    private static void register(BootstrapContext<SpiderVariant> context, HolderGetter<SpiderVariant> registryEntryLookup, ResourceKey<SpiderVariant> registryKey, SpiderVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }

}
