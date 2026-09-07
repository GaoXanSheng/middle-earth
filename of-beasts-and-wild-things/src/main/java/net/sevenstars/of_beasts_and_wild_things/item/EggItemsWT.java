package net.sevenstars.of_beasts_and_wild_things.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.datageneration.models.SimpleItemModels;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

import java.util.function.Function;


public class EggItemsWT {

    // Animals
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            (settings) -> new SpawnEggItem(settings.spawnEgg(EntitiesWT.DEER)), new Item.Properties());
    public static final Item SWAN_SPAWN_EGG = registerItem("swan_spawn_egg",
            (settings) -> new SpawnEggItem(settings.spawnEgg(EntitiesWT.SWAN)), new Item.Properties());
    public static final Item PHEASANT_SPAWN_EGG = registerItem("pheasant_spawn_egg",
            (settings) -> new SpawnEggItem(settings.spawnEgg(EntitiesWT.PHEASANT)), new Item.Properties());
    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            (settings) -> new SpawnEggItem(settings.spawnEgg(EntitiesWT.SNAIL)), new Item.Properties());

    public static ResourceKey<Item> keyOfItem(String idPath) {
        return ResourceKey.create(Registries.ITEM, OfBeastsAndWildThings.of(idPath));
    }
    private static Item registerItem(String idPath, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings.setId(keyOfItem(idPath)));
        ItemGroupsWT.SPAWN_EGGS_CONTENTS.add(item);
        SimpleItemModels.items.add(item);
        TranslationEntries.itemEntries.add(item);
        return Registry.register(BuiltInRegistries.ITEM, OfBeastsAndWildThings.of(idPath), item);
    }

    public static void registerModItems() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Egg Items for " + OfBeastsAndWildThings.MOD_ID);
    }
}
