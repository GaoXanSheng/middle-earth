package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class ItemUtil {

    public static Identifier getIdentifier(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
