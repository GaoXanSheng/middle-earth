package net.sevenstars.middleearth.datageneration.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.OreRockSets;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.*;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    private static ResourceKey<Item> key(Item item) {
        return item.builtInRegistryHolder().key();
    }

    private static ResourceKey<Item>[] keysOf(Item... items) {
        ResourceKey<Item>[] keys = new ResourceKey[items.length];
        for (int i = 0; i < items.length; i++) {
            keys[i] = key(items[i]);
        }
        return keys;
    }

    private static ResourceKey<Item>[] keysOf(List<? extends Item> items) {
        return keysOf(items.toArray(new Item[0]));
    }

    public ItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        var bones = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "bones")));
        var feathers = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "cloaks")));
        var warg_food = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_food")));
        var warg_armor = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "warg_armor")));
        var broadhoof_goat_armor = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "broadhoof_goat_armor")));
        var great_horn_armor = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "great_horn_armor")));
        var dyeable = builder(TagKey.create(Registries.ITEM, Identifier.parse("dyeable")));
        var chains = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "chains")));
        var troll_weapons = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_weapons")));
        var troll_food = builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_food")));

        var characterHideHair = builder(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR);
        var characterHideBeard = builder(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
        var characterShowEars = builder(ItemTagsME.CHARACTER_HELMET_SHOW_EARS);

        TagKey<Item> iron_ores = TagKey.create(Registries.ITEM, Identifier.parse("iron_ores"));
        TagKey<Item> gold_ores = TagKey.create(Registries.ITEM, Identifier.parse("gold_ores"));
        TagKey<Item> copper_ores = TagKey.create(Registries.ITEM, Identifier.parse("copper_ores"));
        TagKey<Item> coal_ores = TagKey.create(Registries.ITEM, Identifier.parse("coal_ores"));

        TagKey<Item> saplings = TagKey.create(Registries.ITEM, Identifier.parse("saplings"));
        TagKey<Item> wooden_slabs = TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs"));
        TagKey<Item> wooden_vertical_slabs = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "wooden_vertical_slabs"));
        TagKey<Item> wooden_fences = TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences"));
        TagKey<Item> logs_that_burn = TagKey.create(Registries.ITEM, Identifier.parse("logs_that_burn"));
        TagKey<Item> stone_crafting_materials = TagKey.create(Registries.ITEM, Identifier.parse("stone_crafting_materials"));
        TagKey<Item> stone_tool_materials = TagKey.create(Registries.ITEM, Identifier.parse("stone_tool_materials"));
        TagKey<Item> leaves = TagKey.create(Registries.ITEM, Identifier.parse("leaves"));

        TagKey<Item> ingot_shaping = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "ingot_shaping"));
        TagKey<Item> nugget_shaping = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "nugget_shaping"));

        TagKey<Item> tin_ores = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "tin_ores"));
        TagKey<Item> lead_ores = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "lead_ores"));
        TagKey<Item> silver_ores = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "silver_ores"));
        TagKey<Item> mithril_ores = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "mithril_ores"));
        TagKey<Item> shingles = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "shingles"));

        TagKey<Item> mod_stripped_logs = TagKey.create(Registries.ITEM, MiddleEarth.of("mod_stripped_logs"));
        TagKey<Item> stripped_logs = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "stripped_logs"));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.of("mod_planks"))).add(keysOf(Planks.getItemPlanksWithoutVanilla()));

        builder(TagKey.create(Registries.ITEM, Identifier.parse("planks"))).add(keysOf(Planks.getItemPlanks()));
        builder(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs"))).add(keysOf(Planks.getItemPlanksSlabs()));
        builder(TagKey.create(Registries.ITEM, Identifier.parse("logs"))).add(keysOf(Logs.getItemLogs()));
        builder(TagKey.create(Registries.ITEM, Identifier.parse("logs_that_burn"))).add(keysOf(Logs.getItemLogs()));
        builder(TagKey.create(Registries.ITEM, Identifier.parse("leaves"))).add(keysOf(LeavesSets.getItemLeaves()));

        builder(TagKey.create(Registries.ITEM, Identifier.parse("axes"))).add(keysOf(WeaponEnchants.axes));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.of("daggers"))).add(keysOf(WeaponEnchants.daggers));
        builder(TagKey.create(Registries.ITEM, Identifier.parse("swords"))).add(keysOf(WeaponEnchants.swords));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.of("spears"))).add(keysOf(WeaponItemsME.spears));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/weapon"))).add(keysOf(WeaponEnchants.weapons));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/sword"))).add(keysOf(WeaponEnchants.sharpWeapons));

        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/bow"))).add(keysOf(Bows.bows));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/ranged_weapon"))).add(keysOf(Bows.bows));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/bow"))).add(keysOf(Bows.bows));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/bows"))).add(keysOf(Bows.bows));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/crossbow"))).add(keysOf(Crossbows.crossbows));

        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(keysOf(ArmorTags.armors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(keysOf(WeaponEnchants.weapons));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(keysOf(ToolItemsME.smithingHammers));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/durability"))).add(keysOf(Crossbows.crossbows));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(keysOf(ArmorTags.armors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(keysOf(WeaponEnchants.weapons));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/vanishing"))).add(keysOf(ToolItemsME.smithingHammers));

        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shields"))).add(keysOf(WeaponItemsME.shields));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shield"))).add(keysOf(WeaponItemsME.shields));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/equippable"))).add(keysOf(ArmorTags.armors));

        ArmorTags.basicArmors.addAll(List.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS));
        ArmorTags.mediumArmors.addAll(List.of(Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS,
                Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS));
        ArmorTags.sturdyArmors.addAll(List.of(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.TURTLE_HELMET));
        ArmorTags.heavyArmors.addAll(List.of(Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS,
                Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS));

        ArmorTags.incompleteArmors.addAll(List.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS,
                Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS,
                Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS,
                Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS, Items.TURTLE_HELMET));

        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/armor"))).add(keysOf(ArmorTags.armors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/head_armor"))).add(keysOf(ArmorTags.headArmors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/chest_armor"))).add(keysOf(ArmorTags.chestArmors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/leg_armor"))).add(keysOf(ArmorTags.legArmors));
        builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("minecraft", "enchantable/foot_armor"))).add(keysOf(ArmorTags.footArmors));

        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "basic_armor"))).add(keysOf(ArmorTags.basicArmors));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "light_armor"))).add(keysOf(ArmorTags.lightArmors));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "medium_armor"))).add(keysOf(ArmorTags.mediumArmors));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "sturdy_armor"))).add(keysOf(ArmorTags.sturdyArmors));

        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "heavy_armor"))).add(keysOf(ArmorTags.heavyArmors));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "incomplete_armors"))).add(keysOf(ArmorTags.incompleteArmors));

        ArrayList<Item> upToArmor = (ArrayList<Item>) ArmorTags.basicArmors;
        upToArmor.addAll(ArmorTags.lightArmors);

        ArrayList<Item> lightChest = new ArrayList<>();
        ArrayList<Item> lightLegging = new ArrayList<>();
        for (Item chestItem : EquipmentItemsME.armorPiecesListChestplates) {
            if (upToArmor.contains(chestItem)) {
                lightChest.add(chestItem);
            }
        }
        for (Item legItem : EquipmentItemsME.armorPiecesListLeggings) {
            if (upToArmor.contains(legItem)) {
                lightLegging.add(legItem);
            }
        }
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "light_chest"))).add(keysOf(lightChest));
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "light_leg"))).add(keysOf(lightLegging));

        upToArmor.addAll(ArmorTags.mediumArmors);

        ArrayList<Item> mediumBoots = new ArrayList<>();
        for (Item bootItem : EquipmentItemsME.armorPiecesListBoots) {
            if (upToArmor.contains(bootItem)) {
                mediumBoots.add(bootItem);
            }
        }
        builder(TagKey.create(Registries.ITEM, MiddleEarth.ofPath("enchantable", "medium_foot"))).add(keysOf(mediumBoots));

        warg_food.add(key(Items.RABBIT));
        warg_food.add(key(Items.CHICKEN));
        warg_food.add(key(Items.PORKCHOP));
        warg_food.add(key(Items.BEEF));
        warg_food.add(key(Items.MUTTON));
        warg_food.add(key(FoodItemsME.RAW_HORSE));
        warg_food.add(key(ItemsWT.RAW_VENISON));

        warg_armor.add(key(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR));
        warg_armor.add(key(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR));
        warg_armor.add(key(EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR));
        warg_armor.add(key(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR));
        warg_armor.add(key(EquipmentItemsME.WARG_LEATHER_ARMOR));
        warg_armor.add(key(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR));

        broadhoof_goat_armor.add(key(EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR));
        broadhoof_goat_armor.add(key(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR));
        broadhoof_goat_armor.add(key(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR));

        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR));
        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR));
        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR));
        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR));
        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR));
        great_horn_armor.add(key(EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR));

        bones.add(key(Items.BONE));
        bones.add(key(ResourceItemsME.DIRTY_BONE));
        bones.add(key(ResourceItemsME.FANG));

        feathers.add(key(ResourceItemsME.SWAN_FEATHER));
        feathers.add(key(Items.FEATHER));

        EquipmentItemsME.helmetAttachments.forEach(item -> cloaks.add(key(item)));
        EquipmentItemsME.backAttachments.forEach(item -> cloaks.add(key(item)));

        dyeable.add(key(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR));
        dyeable.add(key(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR));

        dyeable.add(key(EquipmentItemsME.WARG_LEATHER_ARMOR));
        dyeable.add(key(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR));

        dyeable.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR));
        dyeable.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR));
        dyeable.add(key(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR));

        chains.add(key(Items.IRON_CHAIN));
        chains.add(key(ModDecorativeBlocks.BRONZE_CHAIN.asItem()));
        chains.add(key(ModDecorativeBlocks.BRONZE_BROAD_CHAIN.asItem()));
        chains.add(key(ModDecorativeBlocks.CRUDE_CHAIN.asItem()));
        chains.add(key(ModDecorativeBlocks.CRUDE_BROAD_CHAIN.asItem()));
        chains.add(key(ModDecorativeBlocks.SPIKY_CHAIN.asItem()));

        troll_weapons.add(key(WeaponItemsME.TROLL_MACE));
        troll_weapons.add(key(WeaponItemsME.MACE_OF_SAURON));

        troll_food.add(key(FoodItemsME.RAW_HORSE));
        troll_food.add(key(FoodItemsME.COOKED_HORSE));
        troll_food.add(key(ItemsWT.RAW_VENISON));
        troll_food.add(key(ItemsWT.COOKED_VENISON));
        troll_food.add(key(FoodItemsME.COOKED_MEAT_SKEWER));
        troll_food.add(key(Items.PORKCHOP));
        troll_food.add(key(Items.COOKED_PORKCHOP));
        troll_food.add(key(Items.MUTTON));
        troll_food.add(key(Items.COOKED_MUTTON));
        troll_food.add(key(Items.BEEF));
        troll_food.add(key(Items.COOKED_BEEF));
        troll_food.add(key(Items.CHICKEN));
        troll_food.add(key(Items.COOKED_CHICKEN));
        troll_food.add(key(Items.ROTTEN_FLESH));
        troll_food.add(key(Items.MUSHROOM_STEW));
        troll_food.add(key(Items.BROWN_MUSHROOM));
        troll_food.add(key(Items.RED_MUSHROOM));

        // SHOW Ears
        characterShowEars.add(key(EquipmentItemsME.LORIEN_DIADEM));
        characterShowEars.add(key(EquipmentItemsME.KETTLE_HAT));
        characterShowEars.add(key(EquipmentItemsME.MORDOR_KETTLE_HAT));
        characterShowEars.add(key(EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT));
        characterShowEars.add(key(EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR));
        characterShowEars.add(key(EquipmentItemsME.WEATHERED_DOL_GULDUR_JAILER_COLLAR));

        characterShowEars.add(key(EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET));
        characterShowEars.add(key(EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET));
        characterShowEars.add(key(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET));

        // HIDE Hairs
        characterHideHair.add(key(EquipmentItemsME.MAIL_COIF));
        characterHideHair.add(key(EquipmentItemsME.CLOSED_MAIL_COIF));

        characterHideHair.add(key(EquipmentItemsME.HOOD));
        characterHideHair.add(key(EquipmentItemsME.TALL_HOOD));
        characterHideHair.add(key(EquipmentItemsME.TAN_FUR_HOOD));
        characterHideHair.add(key(EquipmentItemsME.BLACK_FUR_HOOD));
        characterHideHair.add(key(EquipmentItemsME.GRAY_FUR_HOOD));
        characterHideHair.add(key(EquipmentItemsME.BROWN_FUR_HOOD));
        characterHideHair.add(key(EquipmentItemsME.WHITE_FUR_HOOD));

        characterHideHair.add(key(EquipmentItemsME.ELVEN_MAIL_COIF));
        characterHideHair.add(key(EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM));

        characterHideHair.add(key(EquipmentItemsME.DWARVEN_MAIL_COIF));
        characterHideHair.add(key(EquipmentItemsME.EREBOR_MAIL_COIF));
        characterHideHair.add(key(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF));

        characterHideHair.add(key(EquipmentItemsME.ORCISH_MAIL_COIF));
        characterHideHair.add(key(EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF));

        // HIDE Beards
        characterHideBeard.add(key(EquipmentItemsME.CLOSED_MAIL_COIF));
        characterHideBeard.add(key(EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF));

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if (set.coal_ore() != null) {
                builder(coal_ores)
                        .add(key(set.coal_ore().asItem()));
            }
            if (set.copper_ore() != null) {
                builder(copper_ores)
                        .add(key(set.copper_ore().asItem()));
            }
            if (set.tin_ore() != null) {
                builder(tin_ores)
                        .add(key(set.tin_ore().asItem()));
            }
            if (set.lead_ore() != null) {
                builder(lead_ores)
                        .add(key(set.lead_ore().asItem()));
            }
            if (set.silver_ore() != null) {
                builder(silver_ores)
                        .add(key(set.silver_ore().asItem()));
            }
            if (set.gold_ore() != null) {
                builder(gold_ores)
                        .add(key(set.gold_ore().asItem()));
            }
            if (set.iron_ore() != null) {
                builder(iron_ores)
                        .add(key(set.iron_ore().asItem()));
            }
            if (set.mithril_ore() != null) {
                builder(mithril_ores)
                        .add(key(set.mithril_ore().asItem()));
            }
        }

        SimpleDyeableItemModel.items.forEach(item -> dyeable.add(key(item)));

        WoodenSlabs.woodenSlabs.forEach(block -> {
            builder(wooden_slabs).add(key(block.asItem()));
        });

        WoodenVerticalSlabs.woodenVericalSlabs.forEach(block -> {
            builder(wooden_vertical_slabs).add(key(block.asItem()));
        });

        Fences.fences.forEach(block -> {
            builder(wooden_fences).add(key(block.asItem()));
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            builder(mod_stripped_logs).add(key(block.asItem()));
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            builder(stripped_logs).add(key(block.asItem()));
        });

        Shingles.shingles.forEach(block -> {
            builder(shingles).add(key(block.asItem()));
        });

        Saplings.saplings.forEach(sapling -> {
            builder(saplings).add(key(sapling.asItem()));
        });

        LogsThatBurn.logsThatBurn.forEach(log -> {
            builder(logs_that_burn).add(key(log.asItem()));
        });

        HotMetalsModel.ingots.forEach(ingot -> {
            builder(ingot_shaping).add(key(ingot));
        });

        HotMetalsModel.nuggets.forEach(nugget -> {
            builder(nugget_shaping).add(key(nugget));
        });

        StoneBlockSets.stoneSetsList.forEach(stone -> {
            if (stone.cobblestoneBlocks != null) {
                builder(stone_crafting_materials).add(key(stone.cobblestoneBlocks.base().asItem()));
                builder(stone_tool_materials).add(key(stone.cobblestoneBlocks.base().asItem()));
            }
        });
        builder(stone_crafting_materials).add(key(Blocks.BLACKSTONE.asItem()));
        builder(stone_tool_materials).add(key(Blocks.BLACKSTONE.asItem()));
    }
}
