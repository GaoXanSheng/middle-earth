package net.sevenstars.middleearth.datageneration;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;

/**
 * The datagen environment bootstraps registries without freezing them, leaving item
 * holders without bound component maps — any ItemStack construction then crashes with
 * "Components not bound yet". Binding each item's own component map to its holder makes
 * stacks safe to build while providers run.
 */
public class DatagenComponentBinder {
    private static boolean bound = false;

    public static void bindItemComponents() {
        if (bound) {
            return;
        }
        bound = true;
        BuiltInRegistries.ITEM.listElements().forEach(reference -> reference.bindComponents(DataComponentMap.EMPTY));
    }
}
