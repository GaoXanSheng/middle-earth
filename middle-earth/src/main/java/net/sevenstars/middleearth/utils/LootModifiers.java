package net.sevenstars.middleearth.utils;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.FoodItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class LootModifiers {

    private static final Identifier HORSE_LOOT_TABLE_IDENTIFIER = Identifier.fromNamespaceAndPath("minecraft", "entities/horse");
    private static final Identifier GOAT_LOOT_TABLE_IDENTIFIER = Identifier.fromNamespaceAndPath("minecraft", "entities/goat");

    public static final Identifier FISHING_LOOT_TABLE_IDENTIFIER = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "gameplay/fishing");
    public static final ResourceKey<LootTable> FISHING_LOOT_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, FISHING_LOOT_TABLE_IDENTIFIER);

    public static void modifyLootTables(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if(HORSE_LOOT_TABLE_IDENTIFIER.equals(key.identifier()) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .add(LootItem.lootTableItem(FoodItemsME.RAW_HORSE))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
                tableBuilder.withPool(pool);
            }

            if(GOAT_LOOT_TABLE_IDENTIFIER.equals(key.identifier()) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemRandomChanceCondition.randomChance(1f))
                        .add(LootItem.lootTableItem(ResourceItemsME.FUR))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
                tableBuilder.withPool(pool);
            }
        });
    }
}
