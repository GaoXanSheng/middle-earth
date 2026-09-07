package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

import java.util.concurrent.CompletableFuture;

public class EntityTagProvider extends FabricTagsProvider<EntityType<?>> {

    public EntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENTITY_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        TagKey<EntityType<?>> swan_food = TagKey.create(Registries.ENTITY_TYPE, OfBeastsAndWildThings.of("swan_food"));

        builder(swan_food).add(BuiltInRegistries.ENTITY_TYPE.getResourceKey(EntitiesWT.SNAIL).orElseThrow());
        builder(swan_food).add(BuiltInRegistries.ENTITY_TYPE.getResourceKey(EntityTypes.TADPOLE).orElseThrow());
    }
}
