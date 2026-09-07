package net.sevenstars.middleearth.registries.content.spidervariants;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;

public class SpiderVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/spiders/";
    private static final String ENTITY_NAME = "_shelobite_";

    public static SpiderVariant.SpiderAssetInfo createAssetInfos(String textureName){
        return new SpiderVariant.SpiderAssetInfo(
            MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "larva"),
            MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "scuttler"),
            MiddleEarth.of(TEXTURE_PATH + textureName + "_spawn_of_shelob"));
    }

    public static SpawnPrioritySelectors createSpawnConditions(BootstrapContext<SpiderVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.lookup(Registries.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnPrioritySelectors createSpawnConditions(HolderSet<Biome> requiredBiomes, int priority) {
        return SpawnPrioritySelectors.single(new BiomeCheck(requiredBiomes), priority);
    }
}
