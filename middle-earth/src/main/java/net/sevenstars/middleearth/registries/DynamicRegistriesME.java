package net.sevenstars.middleearth.registries;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.providers.dynamic.*;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornVariant;
import net.sevenstars.middleearth.entity.spider.SpiderVariant;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;
import net.sevenstars.middleearth.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.sevenstars.middleearth.registries.content.texturepresets.TexturePresetsRegistry;
import net.sevenstars.middleearth.registries.content.factions.FactionRegistry;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.registries.content.spidervariants.SpiderVariantRegistry;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

public class DynamicRegistriesME extends net.sevenstars.api.registries.DynamicRegistries {
    public static final ResourceKey<Registry<Race>> RACE = ResourceKey.createRegistryKey(MiddleEarth.of("race"));
    public static final ResourceKey<Registry<Faction>> FACTION = ResourceKey.createRegistryKey(MiddleEarth.of("faction"));
    public static final ResourceKey<Registry<NpcType>> NPC_TYPE = ResourceKey.createRegistryKey(MiddleEarth.of("npc_type"));
    public static final Codec<Holder<NpcType>> NPC_TYPE_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.NPC_TYPE);
    public static final ResourceKey<Registry<StructureManagerData>> STRUCTURE_MANAGER_DATA  = ResourceKey.createRegistryKey(MiddleEarth.of("structure_manager_data"));
    public static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT = ResourceKey.createRegistryKey(MiddleEarth.of("biome_event"));
    public static final ResourceKey<Registry<BiomeEventData>> STRUCTURE_EVENT = ResourceKey.createRegistryKey(MiddleEarth.of("structure_event"));

    public static final ResourceKey<Registry<TexturePresetDataPool>> TEXTURE_PRESETS = ResourceKey.createRegistryKey(MiddleEarth.of( "texture_presets"));

    public static final ResourceKey<Registry<CharacterTextureMaterial>> SKIN_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("skin_material"));
    public static final ResourceKey<Registry<CharacterTextureMaterial>> EYE_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("eye_material"));
    public static final ResourceKey<Registry<CharacterTextureMaterial>> HAIR_MATERIAL = ResourceKey.createRegistryKey(MiddleEarth.of("hair_material"));

    public static final ResourceKey<Registry<CharacterTexturePattern>> SKIN_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("skin_pattern"));
    public static final ResourceKey<Registry<CharacterTexturePattern>> EYE_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("eye_pattern"));
    public static final ResourceKey<Registry<CharacterTexturePattern>> HAIR_PATTERN = ResourceKey.createRegistryKey(MiddleEarth.of("hair_pattern"));

    public static final ResourceKey<Registry<SpiderVariant>> SPIDER_VARIANTS = ResourceKey.createRegistryKey(MiddleEarth.of("spider_variants"));
    public static final ResourceKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS = ResourceKey.createRegistryKey(MiddleEarth.of("great_horn_variants"));

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Dynamic Entries for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(RACE, Race.CODEC);
        DynamicRegistries.registerSynced(NPC_TYPE, NpcType.CODEC);
        DynamicRegistries.registerSynced(FACTION, Faction.CODEC);
        DynamicRegistries.registerSynced(BIOME_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_EVENT, BiomeEventData.CODEC);
        DynamicRegistries.registerSynced(STRUCTURE_MANAGER_DATA, StructureManagerData.CODEC);
        DynamicRegistries.registerSynced(TEXTURE_PRESETS, TexturePresetDataPool.CODEC);

        DynamicRegistries.registerSynced(SKIN_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_PATTERN, CharacterTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_PATTERN, CharacterTexturePattern.CODEC);

        DynamicRegistries.registerSynced(SKIN_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(EYE_MATERIAL, CharacterTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(HAIR_MATERIAL, CharacterTextureMaterial.CODEC);

        DynamicRegistries.registerSynced(SPIDER_VARIANTS, SpiderVariant.CODEC);
        DynamicRegistries.registerSynced(GREAT_HORN_VARIANTS, GreatHornVariant.CODEC);
    }

    public static void prepareBoostrap(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(RACE, RaceRegistry::bootstrap);
        registryBuilder.add(NPC_TYPE, NpcRegistry::bootstrap);
        registryBuilder.add(FACTION, FactionRegistry::bootstrap);
        registryBuilder.add(BIOME_EVENT, BiomeEventRegistry::bootstrap);
        registryBuilder.add(STRUCTURE_EVENT, BiomeEventRegistry::bootstrapStructureEvents);
        registryBuilder.add(STRUCTURE_MANAGER_DATA, StructureManagerDataRegistry::bootstrap);
        registryBuilder.add(TEXTURE_PRESETS, TexturePresetsRegistry::bootstrap);

        registryBuilder.add(SPIDER_VARIANTS, SpiderVariantRegistry::bootstrap);
        registryBuilder.add(GREAT_HORN_VARIANTS, GreatHornVariantRegistry::bootstrap);
    }

    public static void addProviders(FabricDataGenerator.Pack pack) {
        pack.addProvider(SpiderVariantsProvider::new);
        pack.addProvider(RaceProvider::new);
        pack.addProvider(TexturePresetsProvider::new);
        pack.addProvider(NpcProvider::new);
        pack.addProvider(FactionProvider::new);
        pack.addProvider(StructureDataProvider::new);
        pack.addProvider(BiomeEventProvider::new);
        pack.addProvider(StructureEventProvider::new);
        pack.addProvider(GreatHornVariantsProvider::new);
    }
}