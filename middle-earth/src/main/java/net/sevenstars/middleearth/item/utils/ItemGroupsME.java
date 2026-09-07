package net.sevenstars.middleearth.item.utils;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ItemGroupsME {
    public static final List<ItemLike> STONE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab STONE_BLOCKS = FabricCreativeModeTab.builder()
            .title(Component.translatable(MiddleEarth.of("stone_blocks").toLanguageKey("itemGroup")))
            .icon(() -> new ItemStack(StoneBlockSets.CALCITE_SET.brickBlocks.base().asItem()))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : STONE_BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> WOOD_BLOCKS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab WOOD_BLOCKS = FabricCreativeModeTab.builder()
            .title(Component.translatable(MiddleEarth.of("wood_blocks").toLanguageKey("itemGroup")))
            .icon(() -> new ItemStack(WoodBlockSets.WILLOW_SET.logBlocks.log().asItem()))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : WOOD_BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> MISC_BLOCKS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab MISC_BLOCKS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".misc_blocks"))
            .icon(() -> new ItemStack(GenericBlockSets.STRAW.blockSet.base().asItem()))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : MISC_BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> DECORATIVES_BLOCKS_CONTENT = new LinkedList<>();
    public static final CreativeModeTab DECORATIVES_BLOCKS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".decorative_blocks"))
            .icon(() -> new ItemStack(DecorativeItemsME.DWARVEN_LANTERN))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : DECORATIVES_BLOCKS_CONTENT) {
                    entries.accept(item);
                };
                entries.acceptAll(ItemGroupsUtil.addFactionBanners(displayContext.holders()));
            })
            .build();

    public static final List<ItemLike> NATURE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab NATURE_BLOCKS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".nature_blocks"))
            .icon(() -> new ItemStack(ModNatureBlocks.HEATHER.asItem()))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : NATURE_BLOCKS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> FOOD_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab FOOD = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".food_items"))
            .icon(() -> new ItemStack(FoodItemsME.LEMBAS))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : FOOD_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> WEAPONS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab WEAPONS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".weapon_items"))
            .icon(() -> new ItemStack(WeaponItemsME.GONDORIAN_SWORD))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : WEAPONS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> EQUIPMENT_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab EQUIPMENT = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".equipment_items"))
            .icon(() -> new ItemStack(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : EQUIPMENT_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> TOOLS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab TOOLS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".tool_items"))
            .icon(() -> new ItemStack(ToolItemsME.KHAZAD_STEEL_PICKAXE))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : TOOLS_CONTENTS) {
                    entries.accept(item);
                }
            })
            .build();

    public static final List<ItemLike> RESOURCES_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab RESOURCES = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".resource_items"))
            .icon(() -> new ItemStack(ResourceItemsME.MITHRIL_INGOT))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : RESOURCES_CONTENTS) {
                    entries.acceptAll(ItemGroupsUtil.processResourceItem(item, displayContext));
                }
            })
            .build();

    public static final List<ItemLike> SPAWN_EGGS_CONTENTS = new LinkedList<>();
    public static final CreativeModeTab SPAWN_EGGS = FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MiddleEarth.MOD_ID + ".spawn_egg_items"))
            .icon(() -> new ItemStack(EggItemsME.BROADHOOF_GOAT_SPAWN_EGG))
            .displayItems((displayContext, entries) -> {
                for (ItemLike item : SPAWN_EGGS_CONTENTS) {
                    entries.accept(item);
                };
                displayContext.holders().lookup(DynamicRegistriesME.NPC_TYPE)
                        .ifPresent(registryWrapper -> ItemGroupsUtil.addNpcEggs(
                                entries,
                                registryWrapper,
                                registryEntry -> true,
                                displayContext.holders(),
                                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS));
            })
            .build();

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("stone_blocks"), STONE_BLOCKS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("wood_blocks"), WOOD_BLOCKS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("misc_blocks"), MISC_BLOCKS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("decorative"), DECORATIVES_BLOCKS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("nature_blocks"), NATURE_BLOCKS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("food_items"), FOOD);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("weapon_items"), WEAPONS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("equipment_items"), EQUIPMENT);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("tool_items"), TOOLS);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("resource_items"), RESOURCES);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MiddleEarth.of("spawn_egg_items"), SPAWN_EGGS);
    }

}
