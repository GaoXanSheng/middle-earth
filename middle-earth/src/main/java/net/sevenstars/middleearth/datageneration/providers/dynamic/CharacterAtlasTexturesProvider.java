package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.concurrent.CompletableFuture;

public class CharacterAtlasTexturesProvider extends FabricDynamicRegistryProvider {
    public CharacterAtlasTexturesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.SKIN_PATTERN));
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.SKIN_MATERIAL));
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.EYE_PATTERN));
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.EYE_MATERIAL));
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.HAIR_PATTERN));
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.HAIR_MATERIAL));
    }

    @Override
    public String getName() {
        return "CharacterAtlasTextures";
    }
}
