package net.sevenstars.middleearth.datageneration.providers.dynamic;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.concurrent.CompletableFuture;

public class SpiderVariantsProvider extends FabricDynamicRegistryProvider {
    public SpiderVariantsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(DynamicRegistriesME.SPIDER_VARIANTS));
    }

    @Override
    public String getName() {
        return DynamicRegistriesME.SPIDER_VARIANTS.identifier().getPath();
    }
}
