package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.datageneration.content.loot_tables.BlockDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.CropDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.LeavesDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.PotDrops;
import net.sevenstars.middleearth.datageneration.content.models.SimplePaneModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleRocksModel;
import net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootSubProvider {
    protected static final float[] SAPLING_COMMON_DROP_CHANCE = new float[]{0.1F, 0.1625F, 0.183333336F, 0.2F};

    public BlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        BlockDrops.ensureCatalogPopulated();

        for (Block block : BlockDrops.blocks) {
            if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("nurgon")) {
                cobbleDrops(block, StoneBlockSets.NURGON_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("medgon")) {
                cobbleDrops(block, StoneBlockSets.MEDGON_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("khagalaban")) {
                cobbleDrops(block, StoneBlockSets.KHAGALABAN_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("limestone")) {
                cobbleDrops(block, StoneBlockSets.LIMESTONE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("dolomite")) {
                cobbleDrops(block, StoneBlockSets.DOLOMITE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("quartzite")) {
                cobbleDrops(block, StoneBlockSets.QUARTZITE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("jadeite")) {
                cobbleDrops(block, StoneBlockSets.JADEITE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("ashen_stone")) {
                cobbleDrops(block, StoneBlockSets.ASHENSTONE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("ironstone")) {
                cobbleDrops(block, StoneBlockSets.IRONSTONE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("hematite")) {
                cobbleDrops(block, StoneBlockSets.HEMATITE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("gneiss")) {
                cobbleDrops(block, StoneBlockSets.GNEISS_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("izheraban")) {
                cobbleDrops(block, StoneBlockSets.IZHERABAN_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("schist")) {
                cobbleDrops(block, StoneBlockSets.SCHIST_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("galonn")) {
                cobbleDrops(block, StoneBlockSets.GALONN_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("slate")) {
                cobbleDrops(block, StoneBlockSets.SLATE_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("blue_tuff")) {
                cobbleDrops(block, StoneBlockSets.BLUE_TUFF_SET.cobblestoneBlocks.base());
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().contains("_door")) {
                add(block, createDoorTable(block));
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().contains("vertical_slab")) {
                add(block, verticalSlabDrops(block));
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().contains("slab")) {
                add(block, createSlabItemTable(block));
            } else if (BuiltInRegistries.BLOCK.getKey(block).getPath().equals("reinforced_scaffolding")) {
                add(block, createSingleItemTable(DecorativeItemsME.REINFORCED_SCAFFOLDING));
            } else {
                // TODO : crashes during Datagen
                if (block == null) continue;
                if(block == Blocks.STONE || block == Blocks.DEEPSLATE) continue;
                dropSelf(block);
            }
        }

        for (LeavesDrops.LeavesDrop drop : LeavesDrops.blocks) {
            HolderLookup.RegistryLookup<Enchantment> impl = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
            if (drop.toString().contains("pine")) {
                add(drop.block(), this.createLeavesDrops(drop.block(), drop.drop(), SAPLING_COMMON_DROP_CHANCE).withPool(
                        LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(this.doesNotHaveShearsOrSilkTouch())
                                .add(((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(drop.block(), LootItem.lootTableItem(ResourceItemsME.PINECONE)))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(impl.getOrThrow(Enchantments.FORTUNE),
                                                0.025F, 0.03F, 0.035F, 0.04F, 0.045F)))));
            } else {
                add(drop.block(), this.createLeavesDrops(drop.block(), drop.drop(), NORMAL_LEAVES_SAPLING_CHANCES));
            }
        }
        for (Block sapling : Saplings.saplings) {
            dropSelf(sapling);
        }
        for (CropDrops.CropDrop cd : CropDrops.crops) {
            add(cd.crop_block, createCropDrops(cd.crop_block, cd.fruit, cd.seeds, cd.builder));
        }
        for (CropDrops.CropDrop cd : CropDrops.wild_crops) {
            add(cd.crop_block,
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(cd.seeds)
                                    .when(LootItemRandomChanceCondition.randomChance(0.125f)))
                            .add(LootItem.lootTableItem(cd.fruit))));
        }

        for (Block pot : PotDrops.pots) {
            dropPottedContents(pot);
        }

        add(ModNatureBlocks.CAMPION, createGrassDrops(ModNatureBlocks.CAMPION));
        add(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA, createGrassDrops(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA));
        add(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA, createGrassDrops(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA));
        add(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA, createGrassDrops(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA));
        add(ModNatureBlocks.DEAD_HEATHER_BUSH, createGrassDrops(ModNatureBlocks.DEAD_HEATHER_BUSH));
        add(ModNatureBlocks.DRY_HEATHER_BUSH, createGrassDrops(ModNatureBlocks.DRY_HEATHER_BUSH));
        add(ModNatureBlocks.DEAD_RUSHES, createGrassDrops(ModNatureBlocks.DEAD_RUSHES));
        add(ModNatureBlocks.FALSE_OATGRASS, createGrassDrops(ModNatureBlocks.FALSE_OATGRASS));
        add(ModNatureBlocks.HEATHER_BUSH, createGrassDrops(ModNatureBlocks.HEATHER_BUSH));
        add(ModNatureBlocks.LARGE_BLUE_FESCUE, createGrassDrops(ModNatureBlocks.LARGE_BLUE_FESCUE));
        add(ModNatureBlocks.LARGE_BUSH, createGrassDrops(ModNatureBlocks.LARGE_BUSH));
        add(ModNatureBlocks.LARGE_SHRIVELED_SHRUB, createGrassDrops(ModNatureBlocks.LARGE_SHRIVELED_SHRUB));
        add(ModNatureBlocks.RED_HEATHER_BUSH, createGrassDrops(ModNatureBlocks.RED_HEATHER_BUSH));
        add(ModNatureBlocks.RUSHES, createGrassDrops(ModNatureBlocks.RUSHES));

        add(ModNatureBlocks.BRACKEN, createGrassDrops(ModNatureBlocks.BRACKEN));
        add(ModNatureBlocks.GIANT_BUTTERBUR, createGrassDrops(ModNatureBlocks.GIANT_BUTTERBUR));

        for (Block block : TintableCrossModel.grassLikeBlocks) {
            add(block, createGrassDrops(block));
        }
        for (Block block : TintableCrossModel.tintedBlocks) {
            dropWhenSilkTouch(block);
        }

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if (set.coal_ore() != null) {
                add(set.coal_ore(), createOreDrop(set.coal_ore(), Items.COAL));
            }
            if (set.copper_ore() != null) {
                add(set.copper_ore(), createCopperOreDrops(set.copper_ore()));
            }
            if (set.tin_ore() != null) {
                add(set.tin_ore(), createOreDrop(set.tin_ore(), ResourceItemsME.RAW_TIN));
            }
            if (set.lead_ore() != null) {
                add(set.lead_ore(), createOreDrop(set.lead_ore(), ResourceItemsME.RAW_LEAD));
            }
            if (set.silver_ore() != null) {
                add(set.silver_ore(), createOreDrop(set.silver_ore(), ResourceItemsME.RAW_SILVER));
            }
            if (set.gold_ore() != null) {
                add(set.gold_ore(), createOreDrop(set.gold_ore(), Items.RAW_GOLD));
            }
            if (set.iron_ore() != null) {
                add(set.iron_ore(), createOreDrop(set.iron_ore(), Items.RAW_IRON));
            }
            if (set.mithril_ore() != null) {
                add(set.mithril_ore(), createOreDrop(set.mithril_ore(), ResourceItemsME.RAW_MITHRIL));
            }
            if (set.adamant_ore() != null) {
                add(set.adamant_ore(), createOreDrop(set.adamant_ore(), ResourceItemsME.ADAMANT));
            }
            if (set.emerald_ore() != null) {
                add(set.emerald_ore(), createOreDrop(set.emerald_ore(), Items.EMERALD));
            }
            if (set.ruby_ore() != null) {
                add(set.ruby_ore(), createOreDrop(set.ruby_ore(), ResourceItemsME.RUBY));
            }
            if (set.sapphire_ore() != null) {
                add(set.sapphire_ore(), createOreDrop(set.sapphire_ore(), ResourceItemsME.SAPPHIRE));
            }
        }

        for (SimplePaneModel.Pane pane : SimplePaneModel.panes){
            dropWhenSilkTouch(pane.pane());
            dropWhenSilkTouch(pane.glass());
        }

        cobbleDrops(ModBlocks.STONE_MYCELIUM, Blocks.COBBLESTONE);

        largeDoorDrop(ModDecorativeBlocks.LARCH_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.BLUE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.GREEN_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.RED_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.YELLOW_HOBBIT_DOOR);
        largeDoorDrop(ModDecorativeBlocks.TALL_BLACK_PINE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.TALL_FIR_DOOR);
        largeDoorDrop(ModDecorativeBlocks.OAK_STABLE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SIMPLE_LARCH_GATE);
        largeDoorDrop(ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR);
        largeDoorDrop(ModDecorativeBlocks.SPRUCE_STABLE_DOOR);
        largeDoorDrop(ModDecorativeBlocks.LARGE_STURDY_DOOR);
        largeDoorDrop(ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE);
        largeDoorDrop(ModDecorativeBlocks.GREAT_GONDORIAN_GATE);
        largeDoorDrop(ModDecorativeBlocks.GREAT_DWARVEN_GATE);
        largeDoorDrop(ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.RUINED_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR);
        largeDoorDrop(ModDecorativeBlocks.GREAT_ELVEN_GATE);
        largeDoorDrop(ModDecorativeBlocks.GREAT_ORCISH_GATE);

        cobbleDrops(ModBlocks.SNOWY_GRASS_BLOCK, ModBlocks.DRY_DIRT);

        cobbleDrops(ModNatureBlocks.OLD_PODZOL, Blocks.DIRT);
        cobbleDrops(ModNatureBlocks.LORIEN_PODZOL, Blocks.DIRT);

        for (SimpleRocksModel.Rocks rock : SimpleRocksModel.rocks) {
            rocksDrop(rock.rocks());
        }

        for (SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks) {
            rocksDrop(rock.rocks());
        }
    }

    public void rocksDrop(Block rocksDrop) {
        add(rocksDrop, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(rocksDrop).setProperties(
                                StatePropertiesPredicate.Builder.properties().hasProperty(RocksBlock.STAGE, 0)))
                        .setRolls(ConstantValue.exactly(1.0f))
                        .add(LootItem.lootTableItem(rocksDrop)))
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(rocksDrop).setProperties(
                                StatePropertiesPredicate.Builder.properties().hasProperty(RocksBlock.STAGE, 1)))
                        .setRolls(ConstantValue.exactly(2.0f))
                        .add(LootItem.lootTableItem(rocksDrop)))
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(rocksDrop).setProperties(
                                StatePropertiesPredicate.Builder.properties().hasProperty(RocksBlock.STAGE, 2)))
                        .setRolls(ConstantValue.exactly(3.0f))
                        .add(LootItem.lootTableItem(rocksDrop)))
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(rocksDrop).setProperties(
                                StatePropertiesPredicate.Builder.properties().hasProperty(RocksBlock.STAGE, 3)))
                        .setRolls(ConstantValue.exactly(4.0f))
                        .add(LootItem.lootTableItem(rocksDrop))));
    }

    public LootTable.Builder createSlabItemTable(Block drop) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(drop, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(drop).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }
    public LootTable.Builder verticalSlabDrops(Block drop) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(drop, LootItem.lootTableItem(drop).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(drop).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(VerticalSlabBlock.DOUBLE, true)))))));
    }

    public void cobbleDrops(Block stoneBlock, Block cobbledBlock) {
        add(stoneBlock, this.createSilkTouchDispatchTable(stoneBlock, this.applyExplosionDecay(cobbledBlock, ((LootPoolSingletonContainer.Builder<?>)
                LootItem.lootTableItem(cobbledBlock)))));
    }

    public void largeDoorDrop(Block doorblock) {
        add(doorblock, LootTable.lootTable().withPool(this.applyExplosionCondition(doorblock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(doorblock).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(doorblock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LargeDoorBlock.PART, 0)))))));
    }
}
