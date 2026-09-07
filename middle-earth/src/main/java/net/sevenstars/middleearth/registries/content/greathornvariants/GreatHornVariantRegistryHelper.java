package net.sevenstars.middleearth.registries.content.greathornvariants;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;

public class GreatHornVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/great_horn/";
    private static final String ENTITY_NAME = "_great_horn";

    public static GreatHornVariant.GreatHornAssetInfo createAssetInfos(String textureName){
        return new GreatHornVariant.GreatHornAssetInfo(
                MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME));
    }

    public static SpawnPrioritySelectors createSpawnConditions(BootstrapContext<GreatHornVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.lookup(Registries.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnPrioritySelectors createSpawnConditions(HolderSet<Biome> requiredBiomes, int priority) {
        return SpawnPrioritySelectors.single(new BiomeCheck(requiredBiomes), priority);
    }
}
