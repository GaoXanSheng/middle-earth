package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.concurrent.CompletableFuture;

public class FactionProvider extends FabricDynamicRegistryProvider {
    public FactionProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.FACTION));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.FACTION.identifier().getPath();
    }
}
