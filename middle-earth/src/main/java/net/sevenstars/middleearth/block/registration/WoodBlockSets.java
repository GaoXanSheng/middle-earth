package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.plants.BerryHollyLeavesBlock;
import net.sevenstars.middleearth.block.special.plants.ModLeavesBlock;
import net.sevenstars.middleearth.block.utils.BlockSetRegistration;
import net.sevenstars.middleearth.block.utils.WoodBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.tags.ModdedStrippedLogs;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.block.utils.BlockSetRegistration.getVanillaOrCreateNew;

public class WoodBlockSets {
    public static final float WOOD_STRENGTH = 2.0f;
    public static final float WOOD_BLAST_RESISTANCE = 3.0f;
    public static final float LEAVES_STRENGTH = 0.1f;

    public static List<WoodBlockSetBuilder> woodSetsList = new ArrayList<>();

    public static WoodBlockSetBuilder OAK_SET = registerWoodSet(new WoodBlockSetBuilder("oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WOOD, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder SPRUCE_SET = registerWoodSet(new WoodBlockSetBuilder("spruce",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.SPRUCE_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BIRCH_SET = registerWoodSet(new WoodBlockSetBuilder("birch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SAND, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.BIRCH_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder JUNGLE_SET = registerWoodSet(new WoodBlockSetBuilder("jungle",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.JUNGLE_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder ACACIA_SET = registerWoodSet(new WoodBlockSetBuilder("acacia",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_ORANGE, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.ACACIA_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder DAK_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("dark_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.DARK_OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MANGROVE_SET = registerWoodSet(new WoodBlockSetBuilder("mangrove",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_RED, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.MANGROVE_PROPAGULE)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder CHERRY_SET = registerWoodSet(new WoodBlockSetBuilder("cherry",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_WHITE, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.CHERRY_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder PALE_OAK_SET = registerWoodSet(new WoodBlockSetBuilder("pale_oak",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.PALE_OAK_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BAMBOO = registerWoodSet(new WoodBlockSetBuilder("bamboo",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.BAMBOO_WOOD, Blocks.BAMBOO_SAPLING)
            .vanilla(true)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder CRIMSON_SET = registerWoodSet(new WoodBlockSetBuilder("crimson",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.CRIMSON_STEM, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.CRIMSON_FUNGUS)
            .vanilla(true)
            .addToSet(WoodBlockTypes.NETHER_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder WARPED_SET = registerWoodSet(new WoodBlockSetBuilder("warped",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WARPED_STEM, NoteBlockInstrument.BASS, SoundType.WOOD, Blocks.WARPED_FUNGUS)
            .vanilla(true)
            .addToSet(WoodBlockTypes.NETHER_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS));

    public static WoodBlockSetBuilder ASPEN_SET = registerWoodSet(new WoodBlockSetBuilder("aspen",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.ASPEN_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.COLOR_YELLOW));

    public static WoodBlockSetBuilder BEECH_SET = registerWoodSet(new WoodBlockSetBuilder("beech",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_LIGHT_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.BEECH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.PLANT));

    public static WoodBlockSetBuilder DEADWOOD_SET = registerWoodSet(new WoodBlockSetBuilder("deadwood",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_LIGHT_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS));

    public static WoodBlockSetBuilder LARCH_SET = registerWoodSet(new WoodBlockSetBuilder("larch",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.LARCH_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("black_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder WHITE_LEBETHRON_SET = registerWoodSet(new WoodBlockSetBuilder("white_lebethron",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SNOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_LEBETHRON_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder CHESTNUT_SET = registerWoodSet(new WoodBlockSetBuilder("chestnut",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_YELLOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.CHESTNUT_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.GRASS));

    public static WoodBlockSetBuilder FIR_SET = registerWoodSet(new WoodBlockSetBuilder("fir",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.FIR_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder HOLLY_SET = registerWoodSet(new WoodBlockSetBuilder("holly",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.WOOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.HOLLY_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MALLORN_SET = registerWoodSet(new WoodBlockSetBuilder("mallorn",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.SNOW, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MALLORN_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.TERRACOTTA_LIGHT_GREEN));

    public static WoodBlockSetBuilder SILVER_MAPLE_SET = registerWoodSet(new WoodBlockSetBuilder("silver_maple",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.SILVER_MAPLE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder MIRKWOOD_SET = registerWoodSet(new WoodBlockSetBuilder("mirkwood",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.MIRKWOOD_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.PLANT));

    public static WoodBlockSetBuilder PALM_SET = registerWoodSet(new WoodBlockSetBuilder("palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.DIRT, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WHITE_PALM_SET = registerWoodSet(new WoodBlockSetBuilder("white_palm",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.QUARTZ, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_PALM_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder PINE_SET = registerWoodSet(new WoodBlockSetBuilder("pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder BLACK_PINE_SET = registerWoodSet(new WoodBlockSetBuilder("black_pine",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.TERRACOTTA_ORANGE, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.BLACK_PINE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WHITE_SPRUCE_SET = registerWoodSet(new WoodBlockSetBuilder("white_spruce",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.PODZOL, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WHITE_SPRUCE_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES));

    public static WoodBlockSetBuilder WILLOW_SET = registerWoodSet(new WoodBlockSetBuilder("willow",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BROWN, NoteBlockInstrument.BASS, SoundType.WOOD, ModNatureBlocks.WILLOW_SAPLING)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS)
            .addToSet(WoodBlockTypes.LEAVES)
            .leavesColor(MapColor.TERRACOTTA_GREEN));

    public static WoodBlockSetBuilder ROTTEN_SET = registerWoodSet(new WoodBlockSetBuilder("rotten",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.GRASS, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder SCORCHED_SET = registerWoodSet(new WoodBlockSetBuilder("scorched",
            WOOD_STRENGTH, WOOD_BLAST_RESISTANCE, MapColor.COLOR_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.LOG_BLOCKS)
            .addToSet(WoodBlockTypes.STRIPPED_LOG_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.ROOFING_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("mushroom",
            2f, 0f, MapColor.WOOL, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder DARK_MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("dark_mushroom",
            2f, 0f, MapColor.TERRACOTTA_BLACK, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    public static WoodBlockSetBuilder GRAY_MUSHROOM_SET = registerWoodSet(new WoodBlockSetBuilder("gray_mushroom",
            2f, 0f, MapColor.COLOR_GRAY, NoteBlockInstrument.BASS, SoundType.WOOD, null)
            .addToSet(WoodBlockTypes.MUSHROOM_STEM_BLOCKS)
            .addToSet(WoodBlockTypes.PLANK_BLOCKS)
            .addToSet(WoodBlockTypes.REDSTONE_BLOCKS)
            .addToSet(WoodBlockTypes.FURNITURE_BLOCKS)
            .addToSet(WoodBlockTypes.SHINGLE_BLOCKS));

    private static WoodBlockSetBuilder registerWoodSet(WoodBlockSetBuilder set) {
        List<ItemLike> itemGroup = ItemGroupsME.WOOD_BLOCKS_CONTENTS;

        set.existingList.forEach((woodStoneTypes) -> {
            switch (woodStoneTypes) {
                case LOG_BLOCKS -> {
                    set.logBlocks = BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log());
                }
                case MUSHROOM_STEM_BLOCKS -> {
                    set.mushroomStemBlocks = BlockSetRegistration.createMushroomStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.mushroomStemBlocks.stem());
                }
                case NETHER_STEM_BLOCKS -> {
                    set.logBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(set.logBlocks.log());
                }
                case STRIPPED_LOG_BLOCKS -> {
                    set.strippedLogBlocks = BlockSetRegistration.createWoodSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    StrippableBlockRegistry.register(set.logBlocks.log(), set.strippedLogBlocks.log());
                    StrippableBlockRegistry.register(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                    ModdedStrippedLogs.strippedLogs.add(set.strippedLogBlocks.log());
                }
                case STRIPPED_STEM_BLOCKS -> {
                    set.strippedLogBlocks = BlockSetRegistration.createStemSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    StrippableBlockRegistry.register(set.logBlocks.log(), set.strippedLogBlocks.log());
                    StrippableBlockRegistry.register(set.logBlocks.wood(), set.strippedLogBlocks.wood());
                    ModdedStrippedLogs.strippedLogs.add(set.strippedLogBlocks.log());
                }
                case PLANK_BLOCKS ->
                        set.planksBlocks = BlockSetRegistration.createPlanksSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                case REDSTONE_BLOCKS ->
                        set.redstoneBlocks = BlockSetRegistration.createWoodRedstoneSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                case FURNITURE_BLOCKS -> {
                    set.furnitureBlocks = BlockSetRegistration.createWoodFurnitureSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.soundGroup, set.planksBlocks.base(), itemGroup);
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.stool());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.bench());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.table());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.chair());
                    ItemGroupsME.DECORATIVES_BLOCKS_CONTENT.add(set.furnitureBlocks.ladder());
                }
                case ROOFING_BLOCKS ->
                        set.roofingBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, false);
                case SHINGLE_BLOCKS ->
                        set.shinglesBlocks = BlockSetRegistration.createRegularSet(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, false);
                case LEAVES -> {
                    if(set.setName.equals("mallorn")){
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                (settings) -> new ModLeavesBlock(0.01F, settings, false), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    } else if(set.setName.equals("holly")){
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                (settings) -> new BerryHollyLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    } else {
                        set.leaves = getVanillaOrCreateNew(woodStoneTypes.getPrefix() + set.setName + woodStoneTypes.getSuffix(),
                                (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                        .strength(LEAVES_STRENGTH).mapColor(set.leavesMapColor).sound(SoundType.GRASS).ignitedByLava(), itemGroup);
                    }
                    FlammableBlockRegistry.getDefaultInstance().add(set.leaves, 5, 60);
                }
            }
        });

        woodSetsList.add(set);

        return set;
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Wood Block Sets for " + MiddleEarth.MOD_ID);
    }
}
