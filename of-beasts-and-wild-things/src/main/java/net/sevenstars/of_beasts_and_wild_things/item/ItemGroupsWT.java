package net.sevenstars.of_beasts_and_wild_things.item;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

import java.util.LinkedList;
import java.util.List;

public class ItemGroupsWT {
    public static final List<ItemLike> BLOCKS_CONTENTS = new LinkedList<>();
    public static final List<ItemLike> ITEMS_CONTENTS = new LinkedList<>();
    public static final List<ItemLike> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final CreativeModeTab WILD_THINGS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OfBeastsAndWildThings.MOD_ID + ".wild_things"))
            .icon(() -> new ItemStack(EggItemsWT.DEER_SPAWN_EGG))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
                for (ItemLike item : ITEMS_CONTENTS) {
                    entries.accept(item);
                }
                for (ItemLike item : SPAWN_EGGS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, OfBeastsAndWildThings.of("wild_things"), WILD_THINGS);
    }

}
