package net.sevenstars.middleearth.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.sevenstars.middleearth.datageneration.providers.BlockLootTableProvider;
import net.sevenstars.middleearth.datageneration.providers.DataWorldGenerator;
import net.sevenstars.middleearth.datageneration.providers.EnchantmentProvider;
import net.sevenstars.middleearth.datageneration.providers.LanguageProvider;
import net.sevenstars.middleearth.datageneration.providers.models.BlockModelProvider;
import net.sevenstars.middleearth.datageneration.providers.models.ItemModelProvider;
import net.sevenstars.middleearth.datageneration.providers.recipes.*;
import net.sevenstars.middleearth.datageneration.providers.tags.BlockTagProvider;
import net.sevenstars.middleearth.datageneration.providers.tags.ItemTagProvider;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterMaterialsRegistryME;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;
import net.sevenstars.middleearth.world.biomes.surface.ModBiomes;
import net.sevenstars.middleearth.world.features.boulder.BoulderConfiguredFeatures;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatures;
import net.sevenstars.middleearth.world.features.chain.ChainConfiguredFeatures;
import net.sevenstars.middleearth.world.features.misc.ModMiscConfiguredFeatures;
import net.sevenstars.middleearth.world.features.misc.ModMiscPlacedFeatures;
import net.sevenstars.middleearth.world.features.ores.OreConfiguredFeatures;
import net.sevenstars.middleearth.world.features.ores.OrePlacedFeatures;
import net.sevenstars.middleearth.world.features.platedfood.PlatedFoodConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreePlacedFeatures;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatures;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationConfiguredFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationPlacedFeatures;

public class DataGeneration implements DataGeneratorEntrypoint {
    public static boolean isDataGen = false;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        isDataGen = true;

        HelpingGenerator.generateFiles();

        var pack = fabricDataGenerator.createPack();
        // Atlases
        AtlasesME.addProviders(pack);
        // Custom Dynamic Registries
        DynamicRegistriesME.addProviders(pack);
        // Others
        pack.addProvider(InscriptionRecipeProvider::new);
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockModelProvider::new);
        pack.addProvider(ItemModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(ArtisanTableHandheldRecipeProvider::new);
        pack.addProvider(ArtisanTableArmorRecipeProvider::new);
        pack.addProvider(ArtisanTableGenericArmorRecipeProvider::new);
        pack.addProvider(DataWorldGenerator::new);
        pack.addProvider(LanguageProvider::new);
        pack.addProvider(EnchantmentProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.add(Registries.BIOME, ModBiomes::bootstrap);
        registryBuilder.add(Registries.BIOME, ModCaveBiomes::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModTreeConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModVegetationConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, BoulderConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, OreConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, CavesConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModMiscConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, MushroomTreeConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ChainConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModTreePlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModVegetationPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, BoulderPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, OrePlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, CavesPlacedFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModMiscPlacedFeatures::bootstrap);

        registryBuilder.add(DynamicRegistriesME.SKIN_MATERIAL, CharacterMaterialsRegistryME::bootstrapSkins);
        registryBuilder.add(DynamicRegistriesME.SKIN_PATTERN, CharacterPatternsRegistryME::bootstrapSkins);

        registryBuilder.add(DynamicRegistriesME.HAIR_MATERIAL, CharacterMaterialsRegistryME::bootstrapHairs);
        registryBuilder.add(DynamicRegistriesME.HAIR_PATTERN, CharacterPatternsRegistryME::bootstrapHairs);

        registryBuilder.add(DynamicRegistriesME.EYE_MATERIAL, CharacterMaterialsRegistryME::bootstrapEyes);
        registryBuilder.add(DynamicRegistriesME.EYE_PATTERN, CharacterPatternsRegistryME::bootstrapEyes);

        // Mod Dynamic
        DynamicRegistriesME.prepareBoostrap(registryBuilder);

        // Vanilla registries
        registryBuilder.add(Registries.TRIM_MATERIAL, SmithingTrimMaterialsME::bootstrap);
        registryBuilder.add(Registries.TRIM_PATTERN, SmithingTrimPatternsME::bootstrap);
        registryBuilder.add(Registries.ENCHANTMENT, EnchantmentsME::bootstrap);
    }
}
