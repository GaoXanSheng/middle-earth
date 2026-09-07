package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagsProvider<Item> {

    public ItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        TagKey<Item> swan_food = TagKey.create(Registries.ITEM, OfBeastsAndWildThings.of("swan_food"));

        builder(swan_food).add(BuiltInRegistries.ITEM.getResourceKey(Items.TADPOLE_BUCKET).orElseThrow());
    }
}
