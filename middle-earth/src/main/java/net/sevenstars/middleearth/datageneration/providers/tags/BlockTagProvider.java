package net.sevenstars.middleearth.datageneration.providers.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    private static ResourceKey<Block> key(Block block) {
        return block.builtInRegistryHolder().key();
    }

    private static ResourceKey<Block>[] keysOf(Block... blocks) {
        ResourceKey<Block>[] keys = new ResourceKey[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            keys[i] = key(blocks[i]);
        }
        return keys;
    }

    private static ResourceKey<Block>[] keysOf(List<? extends Block> blocks) {
        return keysOf(blocks.toArray(new Block[0]));
    }

    public BlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        var mineablePickaxe = builder(TagKey.create(Registries.BLOCK, Identifier.parse( "mineable/pickaxe")));
        var mineableAxe = builder(TagKey.create(Registries.BLOCK, Identifier.parse( "mineable/axe")));
        var mineableShovel = builder(TagKey.create(Registries.BLOCK, Identifier.parse("mineable/shovel")));
        var mineableHoe = builder(TagKey.create(Registries.BLOCK, Identifier.parse("mineable/hoe")));
        var swordEfficient = builder(TagKey.create(Registries.BLOCK, Identifier.parse("sword_efficient")));

        var needsStoneTools = builder(TagKey.create(Registries.BLOCK, Identifier.parse("needs_stone_tool")));
        var needsIronTools = builder(TagKey.create(Registries.BLOCK, Identifier.parse("needs_iron_tool")));
        var needsDiamondTools = builder(TagKey.create(Registries.BLOCK, Identifier.parse("needs_diamond_tool")));
        var needsNetheriteTools = builder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("fabric",  "needs_tool_level_4")));

        var baseStoneOverworld = builder(TagKey.create(Registries.BLOCK, Identifier.parse("base_stone_overworld")));

        var climbable = builder(TagKey.create(Registries.BLOCK, Identifier.parse("climbable")));
        var impermeable = builder(TagKey.create(Registries.BLOCK, Identifier.parse("impermeable")));

        var seat = builder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "seat")));
        var table = builder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "table")));

        var leaves = builder(TagKey.create(Registries.BLOCK, Identifier.parse("leaves")));

        var wool = builder(TagKey.create(Registries.BLOCK, Identifier.parse("wool")));

        var snapsGoatHorn = builder(TagKey.create(Registries.BLOCK, Identifier.parse("snaps_goat_horn")));

        var cobwebs = builder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "cobwebs")));

        mineableAxe.add(keysOf(MineableAxe.blocks.toArray(new Block[0])));
        mineablePickaxe.add(keysOf(MineablePickaxe.blocks.toArray(new Block[0])));
        mineableHoe.add(keysOf(MineableHoe.blocks.toArray(new Block[0])));
        mineableShovel.add(keysOf(MineableShovel.blocks.toArray(new Block[0])));

        wool.add(keysOf(Wool.blocks.toArray(new Block[0])));

        leaves.add(keysOf(LeavesSets.leaves.toArray(new Block[0])));

        swordEfficient.add(keysOf(LeavesSets.leaves.toArray(new Block[0])));

        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "saplings"))).add(keysOf(Saplings.saplings.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "doors"))).add(keysOf(Doors.doors.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "trapdoors"))).add(keysOf(Trapdoors.trapdoors.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "stone_buttons"))).add(keysOf(Buttons.stoneButtons.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "wooden_buttons"))).add(keysOf(Buttons.woodButtons.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "fences"))).add(keysOf(Fences.fences.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "wooden_fences"))).add(keysOf(Fences.fences.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "fence_gates"))).add(keysOf(FenceGates.fenceGates.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "leaves"))).add(keysOf(LeavesSets.leaves.toArray(new Block[0]))).add(keysOf(LeavesSets.grayscaleLeaves.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "logs"))).add(keysOf(Logs.logs.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "logs_that_burn"))).add(keysOf(Logs.logs.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "pressure_plates"))).add(keysOf(PressurePlates.pressurePlates.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "walls"))).add(keysOf(Walls.walls.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "planks"))).add(keysOf(Planks.planks.toArray(new Block[0])));
        builder(TagKey.create(Registries.BLOCK, Identifier.parse( "crops"))).add(keysOf(Crops.crops.toArray(new Block[0])));

        //Ores
        TagKey<Block> iron_ores = TagKey.create(Registries.BLOCK, Identifier.parse( "iron_ores"));
        TagKey<Block> gold_ores = TagKey.create(Registries.BLOCK, Identifier.parse( "gold_ores"));
        TagKey<Block> copper_ores = TagKey.create(Registries.BLOCK, Identifier.parse( "copper_ores"));
        TagKey<Block> coal_ores = TagKey.create(Registries.BLOCK, Identifier.parse( "coal_ores"));

        TagKey<Block> tin_ores = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "tin_ores"));
        TagKey<Block> lead_ores = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "lead_ores"));
        TagKey<Block> silver_ores = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "silver_ores"));
        TagKey<Block> mithril_ores = TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "mithril_ores"));

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                builder(coal_ores)
                        .add(key(set.coal_ore()));
            }
            if(set.copper_ore() != null) {
                builder(copper_ores)
                        .add(key(set.copper_ore()));
            }
            if(set.tin_ore() != null) {
                builder(tin_ores)
                        .add(key(set.tin_ore()));
            }
            if(set.lead_ore() != null) {
                builder(lead_ores)
                        .add(key(set.lead_ore()));
            }
            if(set.silver_ore() != null) {
                builder(silver_ores)
                        .add(key(set.silver_ore()));
            }
            if(set.gold_ore() != null) {
                builder(gold_ores)
                        .add(key(set.gold_ore()));
            }
            if(set.iron_ore() != null) {
                builder(iron_ores)
                        .add(key(set.iron_ore()));
            }
            if(set.mithril_ore() != null) {
                builder(mithril_ores)
                        .add(key(set.mithril_ore()));
            }
        }

        BlockRecordTypes.BaseStoneSet.getAllBlocks(StoneBlockSets.NURGON_SET.baseBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.cobblestoneBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossyCobblestoneBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.brickBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossyBrickBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.crackedBrickBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.tileBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossyTileBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.crackedTileBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.smoothBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossySmoothBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.crackedSmoothBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.polishedBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossyPolishedBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.crackedPolishedBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.pillarBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.mossyPillarBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.crackedPillarBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.chiseledBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.chiseledBricksBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.chiseledPolishedBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.chiseledSmoothBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.NURGON_SET.chiseledTilesBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.brickworkBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.NURGON_SET.oldBlocks).forEach(block -> needsIronTools.add(key(block)));
        BlockRecordTypes.CarvedWindow.getAllBlocks(StoneBlockSets.NURGON_SET.carvedWindows).forEach(block -> needsIronTools.add(key(block)));

        BlockRecordTypes.BaseStoneSet.getAllBlocks(StoneBlockSets.MEDGON_SET.baseBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.cobblestoneBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossyCobblestoneBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.brickBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossyBrickBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.crackedBrickBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.tileBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossyTileBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.crackedTileBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.smoothBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossySmoothBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.crackedSmoothBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.polishedBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossyPolishedBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.crackedPolishedBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.pillarBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.mossyPillarBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.crackedPillarBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.chiseledBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.chiseledBricksBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.chiseledPolishedBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.chiseledSmoothBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.PillarSet.getAllBlocks(StoneBlockSets.MEDGON_SET.chiseledTilesBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.brickworkBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.RegularSet.getAllBlocks(StoneBlockSets.MEDGON_SET.oldBlocks).forEach(block -> needsDiamondTools.add(key(block)));
        BlockRecordTypes.CarvedWindow.getAllBlocks(StoneBlockSets.MEDGON_SET.carvedWindows).forEach(block -> needsDiamondTools.add(key(block)));

        baseStoneOverworld.add(key(Blocks.CALCITE));
        baseStoneOverworld.add(keysOf(MineablePickaxe.baseStoneOverworld.toArray(new Block[0])));

        cobwebs.add(key(Blocks.COBWEB));
        cobwebs.add(key(ModNatureBlocks.HANGING_WEBS));
        cobwebs.add(key(ModNatureBlocks.WEBBING));
        cobwebs.add(key(ModNatureBlocks.CORNER_COBWEB));

        SimpleStoneStoolModel.stools.forEach(block -> {
            seat.add(key(block.stool()));
        });
        SimpleStoneChairModel.chairs.forEach(block -> {
            seat.add(key(block.chair()));
        });
        SimpleStoneTableModel.tables.forEach(block -> {
            table.add(key(block.table()));
        });
        SimpleWoodStoolModel.stools.forEach(block -> seat.add(key(block)));
        SimpleWoodStoolModel.vanillaStools.forEach(block -> {
            seat.add(key(block.base()));
        });
        SimpleWoodBenchModel.benchs.forEach(block -> seat.add(key(block)));
        SimpleWoodBenchModel.vanillaBenchs.forEach(block -> {
            seat.add(key(block.base()));
        });
        SimpleWoodChairModel.chairs.forEach(block -> seat.add(key(block)));
        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            seat.add(key(block.base()));
        });
        SimpleWoodTableModel.tables.forEach(block -> table.add(key(block)));
        SimpleWoodTableModel.vanillaTables.forEach(block -> {
            table.add(key(block.base()));
        });

        seat.add(key(ModDecorativeBlocks.BLUE_CUSHION));
        seat.add(key(ModDecorativeBlocks.BROWN_CUSHION));
        seat.add(key(ModDecorativeBlocks.DARK_BLUE_CUSHION));
        seat.add(key(ModDecorativeBlocks.DARK_BROWN_CUSHION));
        seat.add(key(ModDecorativeBlocks.DARK_GREEN_CUSHION));
        seat.add(key(ModDecorativeBlocks.DARK_RED_CUSHION));
        seat.add(key(ModDecorativeBlocks.GREEN_CUSHION));
        seat.add(key(ModDecorativeBlocks.RED_CUSHION));

        SimpleLadderModel.ladders.forEach(block -> {
            climbable.add(key(block.ladder()));
        });

        SimpleLadderModel.vanillaLadders.forEach(block -> {
            climbable.add(key(block.ladder()));
        });

        SimplePaneModel.panes.forEach(block -> {
            impermeable.add(key(block.glass()));
        });

        climbable.add(key(ModDecorativeBlocks.ROPE));
        climbable.add(key(ModBlocks.NET));
        climbable.add(key(ModNatureBlocks.MIRKWOOD_VINES));
        climbable.add(key(ModDecorativeBlocks.REINFORCED_SCAFFOLDING));

        needsStoneTools.add(key(OreRockSets.KHAGALABAN.copper_ore()));
        needsStoneTools.add(key(OreRockSets.KHAGALABAN.coal_ore()));
        needsStoneTools.add(key(OreRockSets.KHAGALABAN.tin_ore()));
        
        needsStoneTools.add(key(OreRockSets.ASHEN.copper_ore()));
        needsStoneTools.add(key(OreRockSets.ASHEN.coal_ore()));
        needsStoneTools.add(key(OreRockSets.ASHEN.tin_ore()));

        needsStoneTools.add(key(OreRockSets.LIMESTONE.copper_ore()));
        needsStoneTools.add(key(OreRockSets.LIMESTONE.coal_ore()));
        needsStoneTools.add(key(OreRockSets.LIMESTONE.tin_ore()));

        needsStoneTools.add(key(OreRockSets.CALCITE.copper_ore()));
        needsStoneTools.add(key(OreRockSets.CALCITE.coal_ore()));
        needsStoneTools.add(key(OreRockSets.CALCITE.tin_ore()));

        needsStoneTools.add(key(OreRockSets.SLATE.copper_ore()));
        needsStoneTools.add(key(OreRockSets.SLATE.coal_ore()));
        needsStoneTools.add(key(OreRockSets.SLATE.tin_ore()));

        needsStoneTools.add(key(OreRockSets.IRONSTONE.copper_ore()));
        needsStoneTools.add(key(OreRockSets.IRONSTONE.coal_ore()));
        needsStoneTools.add(key(OreRockSets.IRONSTONE.tin_ore()));

        needsStoneTools.add(key(OreRockSets.STONE.tin_ore()));

        needsStoneTools.add(key(OreRockSets.DEEPSLATE.tin_ore()));
        needsStoneTools.add(key(OreRockSets.DEEPSLATE.lead_ore()));

        needsIronTools.add(key(OreRockSets.NURGON.tin_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.lead_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.silver_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.gold_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.iron_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.sapphire_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.emerald_ore()));
        needsIronTools.add(key(OreRockSets.NURGON.ruby_ore()));

        needsDiamondTools.add(key(OreRockSets.MEDGON.lead_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.silver_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.gold_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.iron_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.emerald_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.ruby_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.sapphire_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.adamant_ore()));
        needsDiamondTools.add(key(OreRockSets.MEDGON.mithril_ore()));

        needsStoneTools.add(key(ModDecorativeBlocks.STONE_ANVIL));
        needsIronTools.add(key(ModDecorativeBlocks.TREATED_ANVIL));
        needsIronTools.add(key(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL));
        needsIronTools.add(key(ModDecorativeBlocks.ELVEN_TREATED_ANVIL));
        needsIronTools.add(key(ModDecorativeBlocks.ORCISH_TREATED_ANVIL));

        needsIronTools.add(key(ModDecorativeBlocks.TORCH_OF_ORTHANC));

        needsIronTools.add(key(ModDecorativeBlocks.REINFORCED_CHEST));
        mineableAxe.add(key(ModDecorativeBlocks.REINFORCED_CHEST));

        needsDiamondTools.add(key(ModDecorativeBlocks.FIRE_OF_ORTHANC));

        needsIronTools.add(key(ModDecorativeBlocks.BIG_BRAZIER));
        needsIronTools.add(key(ModDecorativeBlocks.GILDED_BIG_BRAZIER));
        needsIronTools.add(key(ModDecorativeBlocks.SMALL_BRAZIER));
        needsIronTools.add(key(ModDecorativeBlocks.GILDED_SMALL_BRAZIER));
        needsIronTools.add(key(ModDecorativeBlocks.FIRE_BOWL));

        needsStoneTools.add(key(ModDecorativeBlocks.CRUDE_ROD));
        needsStoneTools.add(key(ModDecorativeBlocks.TREATED_STEEL_ROD));

        needsStoneTools.add(key(ModBlocks.BRONZE_DOOR));
        needsStoneTools.add(key(ModBlocks.CRUDE_DOOR));
        needsIronTools.add(key(ModBlocks.TREATED_STEEL_DOOR));
        needsStoneTools.add(key(ModBlocks.BRONZE_TRAPDOOR));
        needsStoneTools.add(key(ModBlocks.CRUDE_TRAPDOOR));
        needsIronTools.add(key(ModBlocks.TREATED_STEEL_TRAPDOOR));
        needsIronTools.add(key(ModBlocks.BURZUM_SPIKES));

        needsStoneTools.add(key(ModBlocks.BRONZE_BARS));
        needsStoneTools.add(key(ModBlocks.CRUDE_BARS));
        needsIronTools.add(key(ModBlocks.TREATED_STEEL_BARS));
        needsIronTools.add(key(ModBlocks.BURZUM_BARS));
        needsIronTools.add(key(ModBlocks.GILDED_BARS));

        needsStoneTools.add(key(ModDecorativeBlocks.SPIKY_CHAIN));

        needsStoneTools.add(key(ModDecorativeBlocks.WATERING_CAN));

        mineablePickaxe.add(key(ModDecorativeBlocks.GOLDEN_CHALICE));

        mineablePickaxe.add(key(ModDecorativeBlocks.ARKENSTONE));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_ARKENSTONE));

        mineableAxe.add(key(ModDecorativeBlocks.WOODEN_BUCKET));

        needsStoneTools.add(key(ModBlocks.BRONZE_BLOCK));
        needsStoneTools.add(key(ModBlocks.CRUDE_BLOCK));
        needsIronTools.add(key(ModBlocks.STEEL_BLOCK));
        needsIronTools.add(key(ModBlocks.KHAZAD_STEEL_BLOCK));
        needsIronTools.add(key(ModBlocks.EDHEL_STEEL_BLOCK));
        needsIronTools.add(key(ModBlocks.BURZUM_STEEL_BLOCK));

        needsIronTools.add(key(ModBlocks.ADAMANT_BLOCK));
        needsIronTools.add(key(ModBlocks.RUBY_BLOCK));
        needsIronTools.add(key(ModBlocks.SAPPHIRE_BLOCK));

        mineablePickaxe.add(key(ModBlocks.STONE_MYCELIUM));

        mineablePickaxe.add(key(ModDecorativeBlocks.FIRE_OF_ORTHANC));

        mineablePickaxe.add(key(ModDecorativeBlocks.DWARVEN_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_DWARVEN_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.TREATED_STEEL_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_TREATED_STEEL_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.CRUDE_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_CRUDE_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.LEAD_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_LEAD_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.CRYSTAL_LAMP));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_CRYSTAL_LAMP));
        mineablePickaxe.add(key(ModDecorativeBlocks.SILVER_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_SILVER_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.ELVEN_LANTERN));
        mineablePickaxe.add(key(ModDecorativeBlocks.WALL_ELVEN_LANTERN));

        mineablePickaxe.add(key(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER));
        mineablePickaxe.add(key(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER));
        mineablePickaxe.add(key(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER));
        mineablePickaxe.add(key(ModDecorativeBlocks.COPPER_COIN_PILE));
        mineablePickaxe.add(key(ModDecorativeBlocks.SILVER_COIN_PILE));
        mineablePickaxe.add(key(ModDecorativeBlocks.GOLD_COIN_PILE));
        
        mineablePickaxe.add(key(ModBlocks.PEBBLED_GRASS));
        mineablePickaxe.add(key(ModBlocks.PEBBLED_GRASS_SLAB));
        mineablePickaxe.add(key(ModBlocks.PEBBLED_GRASS_STAIRS));

        mineablePickaxe.add(key(ModBlocks.SKELETAL_PILE));
        mineablePickaxe.add(key(ModBlocks.SKELETAL_PILE_LAYER));

        mineablePickaxe.add(key(ModBlocks.EMBERS));

        mineableAxe.add(key(ModDecorativeBlocks.WOOD_PILE));
        mineableAxe.add(key(ModDecorativeBlocks.ARTISAN_TABLE));
        mineableAxe.add(key(ModDecorativeBlocks.ORCISH_ARTISAN_TABLE));
        mineableAxe.add(key(ModDecorativeBlocks.INSCRIPTION_TABLE));

        mineablePickaxe.add(key(ModBlocks.BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.MUD_BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.RED_SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.PRISMARINE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.NETHER_BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.END_STONE_BRICK_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.PURPUR_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.QUARTZ_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB));

        mineablePickaxe.add(key(ModBlocks.CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.EXPOSED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.WEATHERED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.OXIDIZED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.WAXED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL));
        mineablePickaxe.add(key(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL));

        mineableShovel.add(key(ModBlocks.GRAVEL_LAYER));
        mineableShovel.add(key(ModBlocks.SAND_LAYER));
        mineableShovel.add(key(ModBlocks.BLACK_SAND_LAYER));
        mineableShovel.add(key(ModBlocks.WHITE_SAND_LAYER));

        mineableShovel.add(key(ModBlocks.DIRT_SLAB));
        mineableShovel.add(key(ModBlocks.DIRT_STAIRS));
        mineableShovel.add(key(ModBlocks.MOSS_STAIRS));
        mineableShovel.add(key(ModBlocks.ROOTED_DIRT_STAIRS));
        mineableShovel.add(key(ModBlocks.MUD_SLAB));
        mineableShovel.add(key(ModBlocks.MOSS_SLAB));
        mineableShovel.add(key(ModBlocks.MUD_STAIRS));
        mineableShovel.add(key(ModBlocks.COARSE_DIRT_SLAB));
        mineableShovel.add(key(ModBlocks.COARSE_DIRT_STAIRS));
        mineableShovel.add(key(ModBlocks.ROOTED_DIRT_SLAB));

        mineableShovel.add(key(ModBlocks.ROOTED_DIRT_SLAB));
        
        mineablePickaxe.add(key(ModBlocks.PACKED_MUD_SLAB));
        mineablePickaxe.add(key(ModBlocks.PACKED_MUD_VERTICAL_SLAB));
        mineablePickaxe.add(key(ModBlocks.PACKED_MUD_STAIRS));
        mineablePickaxe.add(key(ModBlocks.PACKED_MUD_WALL));

        mineablePickaxe.add(key(ModBlocks.QUARTZ_BLOCK));
        mineablePickaxe.add(key(ModBlocks.BUDDING_QUARTZ));
        mineablePickaxe.add(key(ModBlocks.SMALL_QUARTZ_BUD));
        mineablePickaxe.add(key(ModBlocks.MEDIUM_QUARTZ_BUD));
        mineablePickaxe.add(key(ModBlocks.LARGE_QUARTZ_BUD));
        mineablePickaxe.add(key(ModBlocks.QUARTZ_CLUSTER));
        mineablePickaxe.add(key(ModBlocks.CITRINE_BLOCK));
        mineablePickaxe.add(key(ModBlocks.BUDDING_CITRINE));
        mineablePickaxe.add(key(ModBlocks.SMALL_CITRINE_BUD));
        mineablePickaxe.add(key(ModBlocks.MEDIUM_CITRINE_BUD));
        mineablePickaxe.add(key(ModBlocks.LARGE_CITRINE_BUD));
        mineablePickaxe.add(key(ModBlocks.CITRINE_CLUSTER));
        mineablePickaxe.add(key(ModBlocks.RED_AGATE_BLOCK));
        mineablePickaxe.add(key(ModBlocks.BUDDING_RED_AGATE));
        mineablePickaxe.add(key(ModBlocks.SMALL_RED_AGATE_BUD));
        mineablePickaxe.add(key(ModBlocks.MEDIUM_RED_AGATE_BUD));
        mineablePickaxe.add(key(ModBlocks.LARGE_RED_AGATE_BUD));
        mineablePickaxe.add(key(ModBlocks.RED_AGATE_CLUSTER));
        mineablePickaxe.add(key(ModBlocks.GLOWSTONE_BLOCK));
        mineablePickaxe.add(key(ModBlocks.BUDDING_GLOWSTONE));
        mineablePickaxe.add(key(ModBlocks.SMALL_GLOWSTONE_BUD));
        mineablePickaxe.add(key(ModBlocks.MEDIUM_GLOWSTONE_BUD));
        mineablePickaxe.add(key(ModBlocks.LARGE_GLOWSTONE_BUD));
        mineablePickaxe.add(key(ModBlocks.GLOWSTONE_CLUSTER));
    }
}
