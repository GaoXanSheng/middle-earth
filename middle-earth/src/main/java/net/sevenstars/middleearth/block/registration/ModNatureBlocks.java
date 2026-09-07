package net.sevenstars.middleearth.block.registration;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.crop.*;
import net.sevenstars.middleearth.block.special.hangingstuff.CustomHangingBlock;
import net.sevenstars.middleearth.block.special.hangingstuff.HangingCobwebBlock;
import net.sevenstars.middleearth.block.special.plants.*;
import net.sevenstars.middleearth.block.special.saplings.VariantSaplingBlock;
import net.sevenstars.middleearth.block.special.saplings.WeightedSaplingBlock;
import net.sevenstars.middleearth.block.special.shelobiteeggs.ShelobiteLarvaEggBlock;
import net.sevenstars.middleearth.block.special.shelobiteeggs.ShelobiteLarvaEggHangingBlock;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;
import net.sevenstars.middleearth.world.features.tree.ModTreeConfiguredFeatures;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.HangingRootsBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static net.sevenstars.middleearth.block.registration.WoodBlockSets.LEAVES_STRENGTH;

public class ModNatureBlocks {

    public static final Block MORGUL_IVY = registerBlock("morgul_ivy",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).lightLevel(GlowLichenBlock.emission(5)).ignitedByLava(), true);

    public static final Block CORRUPTED_MOSS_CARPET = registerBlock("corrupted_moss_carpet",
            CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f).sound(SoundType.MOSS_CARPET), true);

    public static final Block CORRUPTED_MOSS_BLOCK = registerBlock("corrupted_moss_block",
            Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f).sound(SoundType.MOSS), true);

    public static final Block CORRUPTED_MOSS = registerBlock("corrupted_moss",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);

    public static final Block MOSS = registerBlock("moss",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);

    public static final Block FOREST_MOSS = registerBlock("forest_moss",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);

    public static final Block FOREST_MOSS_CARPET = registerBlock("forest_moss_carpet",
            CarpetBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f).sound(SoundType.MOSS_CARPET), true);

    public static final Block FOREST_MOSS_BLOCK = registerBlock("forest_moss_block",
            Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f).sound(SoundType.MOSS), true);

    public static final Block OLD_PODZOL = registerBlock("old_podzol",
            Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL), true);

    public static final Block LORIEN_PODZOL = registerBlock("lorien_podzol",
            Block::new, BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).strength(0.5F).sound(SoundType.GRAVEL), true);

    public static final Block BLUE_FESCUE = registerBlock("blue_fescue",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block BROWN_GRASS = registerBlock("brown_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DYING_GRASS = registerBlock("dying_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GRIM_GRASS = registerBlock("grim_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block MEADOWGRASS = registerBlock("meadowgrass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SPARSE_GRASS = registerBlock("sparse_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block TEMPERATE_GRASS = registerBlock("temperate_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GRASS_TUFT = registerBlock("grass_tuft",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block HEATHER = registerBlock("heather",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block RED_HEATHER = registerBlock("red_heather",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DEAD_HEATHER = registerBlock("dead_heather",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block DRY_HEATHER = registerBlock("dry_heather",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block HEATH = registerBlock("heath",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block WHEATGRASS = registerBlock("wheatgrass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block WILDERGRASS = registerBlock("wildergrass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block MISTWEED = registerBlock("mistweed",
            MistweedPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block SEDUM = registerBlock("sedum",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block ORANGE_SEDUM = registerBlock("orange_sedum",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block RED_SEDUM = registerBlock("red_sedum",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block YELLOW_SEDUM = registerBlock("yellow_sedum",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block GREEN_SHRUB = registerBlock("green_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SMALL_DRY_SHRUB = registerBlock("small_dry_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BEACH_GRASS = registerBlock("beach_grass",
            DesertPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block COASTAL_PANIC_GRASS = registerBlock("coastal_panic_grass",
            DesertPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block SHRIVELED_SHRUB = registerBlock("shriveled_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DEAD_BUSH).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);

    public static final Block CAMPION = registerBlock("campion",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block BLUE_BIGLEAF_HYDRANGEA = registerBlock("blue_bigleaf_hydrangea",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block PINK_BIGLEAF_HYDRANGEA = registerBlock("pink_bigleaf_hydrangea",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block WHITE_BIGLEAF_HYDRANGEA = registerBlock("white_bigleaf_hydrangea",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block DEAD_HEATHER_BUSH = registerBlock("dead_heather_bush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block DRY_HEATHER_BUSH = registerBlock("dry_heather_bush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block DEAD_RUSHES = registerBlock("dead_rushes",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block FALSE_OATGRASS = registerBlock("false_oatgrass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block HEATHER_BUSH = registerBlock("heather_bush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block LARGE_BLUE_FESCUE = registerBlock("large_blue_fescue",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block LARGE_BUSH = registerBlock("large_bush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block LARGE_SHRIVELED_SHRUB = registerBlock("large_shriveled_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block RED_HEATHER_BUSH = registerBlock("red_heather_bush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block RUSHES = registerBlock("rushes",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.BROWN), true);
    public static final Block WILD_GRASS = registerBlock("wild_grass",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BRACKEN = registerBlock("bracken",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block GIANT_BUTTERBUR = registerBlock("giant_butterbur",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block NETTLES = registerBlock("nettles",
            PricklyPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block THISTLE = registerBlock("thistle",
            PricklyPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block ATHELAS = registerBlock("athelas",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block BRAMBLES_OF_MORDOR = registerBlock("brambles_of_mordor",
            MordorPricklyPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block ELANOR = registerBlock("elanor",
            (settings) -> new FlowerBlock(MobEffects.GLOWING, 3, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak()
                    .sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);
    public static final Block MALLOS = registerBlock("mallos",
            (settings) -> new FlowerBlock(MobEffects.GLOWING, 3, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak()
                    .sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);
    public static final Block NIPHREDIL = registerBlock("niphredil",
            (settings) -> new FlowerBlock(MobEffects.GLOWING, 3, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak()
                    .sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);
    public static final Block SIMBELMYNE = registerBlock("simbelmyne",
            (settings) -> new FlowerBlock(MobEffects.GLOWING, 3, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak()
                    .sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).mapColor(DyeColor.GREEN), true);

    public static final Block TAN_SHRUB = registerBlock("tan_shrub",
            DesertPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BLUE_GENTIAN = registerBlock("blue_gentian",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block GREEN_JEWEL_CORNFLOWER = registerBlock("green_jewel_cornflower",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block NOBLEWHITE = registerBlock("noblewhite",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block YELLOW_FLOWER = registerBlock("yellow_flower",
            (settings) -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);

    public static final Block LIGHT_BLUE_FLOWERS = registerBlock("light_blue_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block MAGENTA_FLOWERS = registerBlock("magenta_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block ORANGE_FLOWERS = registerBlock("orange_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block PINK_FLOWERS = registerBlock("pink_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block PURPLE_FLOWERS = registerBlock("purple_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block RED_FLOWERS = registerBlock("red_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block WHITE_FLOWERS = registerBlock("white_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block YELLOW_FLOWERS = registerBlock("yellow_flowers",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);

    public static final Block BLUE_LAVENDER = registerBlock("blue_lavender",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block LAVENDER = registerBlock("lavender",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block WHITE_LAVENDER = registerBlock("white_lavender",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);
    public static final Block YELLOW_TROLLIUS = registerBlock("yellow_trollius",
            (settings) -> new FlowerBlock(MobEffects.SATURATION, 0, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION), true);

    public static final Block HOBBIT_SUNFLOWERS = registerBlock("hobbit_sunflowers",
            RotatableTallFlowerBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SUNFLOWER), true);

    public static final Block HOGWEED = registerBlock("hogweed",
            DoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).instabreak(), true);
    public static final Block SHORT_HOGWEED = registerBlock("short_hogweed",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS), true);

    public static final Block AZALEA_FLOWER_GROWTH = registerBlock("azalea_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block DRY_GROWTH = registerBlock("dry_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block GREEN_GROWTH = registerBlock("green_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block IVY_GROWTH = registerBlock("ivy_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block LILAC_FLOWER_GROWTH = registerBlock("lilac_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block PINK_FLOWER_GROWTH = registerBlock("pink_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block RED_FLOWER_GROWTH = registerBlock("red_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block THORNY_GROWTH = registerBlock("thorny_growth",
            ThornyGrowthBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block WHITE_FLOWER_GROWTH = registerBlock("white_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);
    public static final Block YELLOW_FLOWER_GROWTH = registerBlock("yellow_flower_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);

    public static final Block CLOVERS = registerBlock("clovers",
            FlowerBedBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS), true);

    public static final Block SHORT_DEAD_RUSHES = registerBlock("short_dead_rushes",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SHORT_RUSHES = registerBlock("short_rushes",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SHORT_REEDS = registerBlock("short_reeds",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block SHORT_CATTAILS = registerBlock("short_cattails",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(MapColor.TERRACOTTA_BROWN), true);
    public static final Block SHORT_BULRUSH = registerBlock("short_bulrush",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(MapColor.TERRACOTTA_BROWN), true);

    public static final Block TALL_CATTAILS = registerBlock("tall_cattails",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).instabreak(), true);
    public static final Block TALL_BULRUSH = registerBlock("tall_bulrush",
            (settings) -> new CustomWaterloggableTallPlantBlock(settings, false), BlockBehaviour.Properties.ofFullCopy(Blocks.TALL_GRASS).instabreak(), true);

    public static final Block SMALL_LILY_PADS = registerBlock("small_lily_pads",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block SMALL_FLOWERING_LILY_PADS = registerBlock("small_flowering_lily_pads",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block LILY_PADS = registerBlock("lily_pads",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block FLOWERING_LILY_PADS = registerBlock("flowering_lily_pads",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollision(), false);
    public static final Block LARGE_LILY_PAD = registerBlock("large_lily_pad",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD), false);
    public static final Block LARGE_FLOWERING_LILY_PAD = registerBlock("large_flowering_lily_pad",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD), false);

    public static final Block DUCKWEED = registerBlock("duckweed",
            LilyPadBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).noCollision(), false);

    public static final Block FROZEN_GRASS = registerBlock("frozen_grass",
            DesertPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block FROZEN_TUFT = registerBlock("frozen_tuft",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block FROZEN_SHRUB = registerBlock("frozen_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block FROZEN_GROWTH = registerBlock("frozen_growth",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN).replaceable().noCollision().strength(0.2f).sound(SoundType.GLOW_LICHEN)
                    .mapColor(DyeColor.GREEN).ignitedByLava(), true);

    public static final Block STICKY_SNOW = registerBlock("sticky_snow",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollision()
                    .strength(0.2f).sound(SoundType.SNOW), true);
    public static final Block STICKY_ICE = registerBlock("sticky_ice",
            GlowLichenBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).replaceable().noCollision()
                    .strength(0.2f).sound(SoundType.GLASS), true);

    public static final Block SHORT_ICICLES = registerBlock("short_icicles",
            ShortIciclesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollision()
                    .strength(0.2f).sound(SoundType.GLASS), true);
    public static final Block DROOPING_ICICLES = registerBlock("drooping_icicles",
            DroopingIciclesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollision()
                    .strength(0.2f).sound(SoundType.GLASS), true);

    public static final Block FLOATING_ICE = registerBlock("floating_ice",
            FloatingIceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.ICE), false);

    public static final Block SCORCHED_GRASS = registerBlock("scorched_grass",
            DesertPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SCORCHED_TUFT = registerBlock("scorched_tuft",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);
    public static final Block SCORCHED_SHRUB = registerBlock("scorched_shrub",
            CustomPlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ)
                    .mapColor(DyeColor.GREEN), true);

    public static final Block BROWN_BOLETE = registerBlock("brown_bolete",
            (settings) -> new MushroomBlock(MushroomTreeConfiguredFeatures.BROWN_BOLETTE_TREE_KEY, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block CAVE_AMANITA = registerBlock("cave_amanita",
            (settings) -> new MushroomBlock(MushroomTreeConfiguredFeatures.CAVE_AMANITA_TREE_KEY, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block DEEP_FIRECAP = registerBlock("deep_firecap",
            (settings) -> new MushroomBlock(MushroomTreeConfiguredFeatures.DEEP_FIRECAP_TREE_KEY, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block GHOSTSHROOM = registerBlock("ghostshroom",
            (settings) -> new MushroomBlock(null, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel(state -> 4), true);
    public static final Block MORSEL = registerBlock("morsel",
            (settings) -> new MushroomBlock(null, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block SKY_FIRECAP = registerBlock("sky_firecap",
            (settings) -> new MushroomBlock(MushroomTreeConfiguredFeatures.SKY_FIRECAP_TREE_KEY, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TRUMPET_SHROOM = registerBlock("trumpet_shroom",
            (settings) -> new MushroomBlock(null, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TALL_TRUMPET_SHROOM = registerBlock("tall_trumpet_shroom",
            TallMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block TUBESHRROM = registerBlock("tubeshroom",
            (settings) -> new MushroomBlock(null, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel(state -> 4), true);
    public static final Block TALL_TUBESHROOM = registerBlock("tall_tubeshroom",
            TallMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel(state -> 5), true);
    public static final Block VIOLET_CAPS = registerBlock("violet_caps",
            (settings) -> new MushroomBlock(null, settings),BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            (settings) -> new MushroomBlock(null, settings),BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block YELLOW_AMANITA = registerBlock("yellow_amanita",
            (settings) -> new MushroomBlock(MushroomTreeConfiguredFeatures.YELLOW_AMANITA_TREE_KEY,settings), BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);

    public static final Block BROWN_BOLETE_TILLER = registerBlock("brown_bolete_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block CAVE_AMANITA_TILLER = registerBlock("cave_amanita_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block DEEP_FIRECAP_TILLER = registerBlock("deep_firecap_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block GHOSTSHROOM_TILLER = registerBlock("ghostshroom_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM).lightLevel(state -> 4), true);
    public static final Block MORSEL_TILLER = registerBlock("morsel_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block SKY_FIRECAP_TILLER = registerBlock("sky_firecap_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block VIOLET_CAPS_TILLER = registerBlock("violet_caps_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block WHITE_MUSHROOM_TILLER = registerBlock("white_mushroom_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);
    public static final Block YELLOW_AMANITA_TILLER = registerBlock("yellow_amanita_tiller",
            FlowerbedMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM), true);

    public static final Block BROWN_BOLETE_BLOCK = registerBlock("brown_bolete_block",
            HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block CAVE_AMANITA_BLOCK = registerBlock("cave_amanita_block",
            HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block DEEP_FIRECAP_BLOCK = registerBlock("deep_firecap_block",
            HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block SKY_FIRECAP_BLOCK = registerBlock("sky_firecap_block",
            HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), true);
    public static final Block YELLOW_AMANITA_BLOCK = registerBlock("yellow_amanita_block",
            HugeMushroomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_MUSHROOM_BLOCK), true);

    public static final Block ASPEN_SAPLING = registerSimpleSapling("aspen_sapling", ModTreeConfiguredFeatures.ASPEN_TREE_KEY);
    public static final Block BEECH_SAPLING = registerSimpleSapling("beech_sapling", ModTreeConfiguredFeatures.BEECH_TREE_KEY);
    public static final Block CHESTNUT_SAPLING = registerSimpleSapling("chestnut_sapling", ModTreeConfiguredFeatures.CHESTNUT_TREE_KEY);
    public static final Block HOLLY_SAPLING = registerSimpleSapling("holly_sapling", ModTreeConfiguredFeatures.HOLLY_TREE_KEY);
    public static final Block FIR_SAPLING = registerSimpleSapling("fir_sapling", ModTreeConfiguredFeatures.FIR_TREE_KEY);
    public static final Block LARCH_SAPLING = registerSimpleSapling("larch_sapling", ModTreeConfiguredFeatures.LARCH_TREE_KEY);
    public static final Block LEBETHRON_SAPLING = registerWeightedSapling("lebethron_sapling", List.of(
            new WeightedSaplingBlock.WeightedTree(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY, 1, "white_lebethron_sapling"),
            new WeightedSaplingBlock.WeightedTree(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY, 39, "black_lebethron_sapling")));
    public static final Block WHITE_LEBETHRON_SAPLING = registerSimpleSapling("white_lebethron_sapling", ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY);
    public static final Block MALLORN_SAPLING = registerSimpleSapling("mallorn_sapling", ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY);
    public static final Block MAPLE_SAPLING = registerVariantSapling("maple_sapling",
            List.of(ModTreeConfiguredFeatures.MAPLE_TREE_KEY, ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY));
    public static final Block SILVER_MAPLE_SAPLING = registerVariantSapling("silver_maple_sapling",
            List.of(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY, ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY));
    public static final Block MIRKWOOD_SAPLING = registerSimpleSapling("mirkwood_sapling", ModTreeConfiguredFeatures.SMALL_MIRKWOOD_TREE_KEY);
    public static final Block PALM_SAPLING = registerWeightedSapling("palm_sapling",
            List.of(new WeightedSaplingBlock.WeightedTree(ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY, 1, "white_palm_sapling"),
                    new WeightedSaplingBlock.WeightedTree(ModTreeConfiguredFeatures.PALM_TREE_KEY, 14, "palm_sapling")));
    public static final Block WHITE_PALM_SAPLING = registerSimpleSapling("white_palm_sapling", ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY);
    public static final Block PINE_SAPLING = registerSimpleSapling("pine_sapling", ModTreeConfiguredFeatures.PINE_TREE_KEY);
    public static final Block BLACK_PINE_SAPLING = registerSimpleSapling("black_pine_sapling", ModTreeConfiguredFeatures.BLACK_PINE_TREE_KEY);
    public static final Block WHITE_SPRUCE_SAPLING = registerSimpleSapling("white_spruce_sapling", ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY);
    public static final Block WILLOW_SAPLING = registerSimpleSapling("willow_sapling", ModTreeConfiguredFeatures.WILLOW_TREE_KEY);

    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sound(SoundType.GRASS).ignitedByLava(), true);

    public static final Block BERRY_HOLLY_LEAVES = registerBlock("berry_holly_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sound(SoundType.GRASS).ignitedByLava(), true);

    public static final Block DRY_LARCH_LEAVES = registerBlock("dry_larch_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.SAND).sound(SoundType.GRASS).ignitedByLava(), true);

    public static final Block FLOWERING_MALLORN_LEAVES = registerBlock("flowering_mallorn_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.SAND).sound(SoundType.GRASS), true);

    public static final Block ORANGE_MAPLE_LEAVES = registerBlock("orange_maple_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.GRASS).ignitedByLava(), true);
    public static final Block RED_MAPLE_LEAVES = registerBlock("red_maple_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_RED).sound(SoundType.GRASS).ignitedByLava(), true);
    public static final Block YELLOW_MAPLE_LEAVES = registerBlock("yellow_maple_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.GRASS).ignitedByLava(), true);

    public static final Block DRY_PINE_LEAVES = registerBlock("dry_pine_leaves",
            (settings) -> new TintedParticleLeavesBlock(0.01F, settings), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                    .strength(LEAVES_STRENGTH).mapColor(MapColor.GRASS).sound(SoundType.GRASS).ignitedByLava(), true);
    public static final Block PINE_BRANCHES = registerBlock("pine_branches",
            Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sound(SoundType.GRASS).ignitedByLava(), true);

    public static final Block FALLEN_LEAVES = registerBlock("fallen_leaves",
            FallenLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f).sound(SoundType.MOSS_CARPET).noOcclusion().replaceable().noCollision(), true);
    public static final Block FALLEN_MALLORN_LEAVES = registerBlock("fallen_mallorn_leaves",
            FallenLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.1f)
                    .sound(SoundType.MOSS_CARPET).mapColor(MapColor.COLOR_YELLOW).noOcclusion().replaceable().noCollision(), true);
    public static final Block FALLEN_MIRKWOOD_LEAVES = registerBlock("fallen_mirkwood_leaves",
            FallenLeavesBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.1f)
                    .sound(SoundType.MOSS_CARPET).noOcclusion().replaceable().noCollision(), true);

    public static final Block WILLOW_VINES = registerBlock("willow_vines",
            CustomHangingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_HANGING_MOSS), true);

    public static final Block TOUGH_BERRY_BUSH = registerBlock("tough_berry_bush",
            ToughBerryBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).randomTicks().noCollision().instabreak()
                    .sound(SoundType.SWEET_BERRY_BUSH), false);
    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            StrawBerryBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).randomTicks().noCollision().instabreak().sound(SoundType.SWEET_BERRY_BUSH), false);

    public static final Block WILD_WHEAT = registerCrossBlock("wild_wheat",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block TALL_WILD_WHEAT = registerBlock("tall_wild_wheat",
            (settings) -> new CustomTallPlantBlock(settings, true), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_PIPEWEED = registerBlock("wild_pipeweed",
            (settings) -> new CustomTallPlantBlock(settings, true), BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP),true);
    public static final Block WILD_FLAX = registerCrossBlock("wild_flax",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_TOMATO = registerCrossBlock("wild_tomato",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_BELL_PEPPER = registerCrossBlock("wild_bell_pepper",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_CUCUMBER = registerCrossBlock("wild_cucumber",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_GARLIC = registerCrossBlock("wild_garlic",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_ONION = registerCrossBlock("wild_onion",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_LETTUCE = registerCrossBlock("wild_lettuce",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_LEEK = registerCrossBlock("wild_leek",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_POTATO = registerBlock("wild_potato",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_CARROT = registerBlock("wild_carrot",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);
    public static final Block WILD_BEETROOT = registerBlock("wild_beetroot",
            WildCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CROP), true);

    public static final Block TOMATO_CROP = registerBlock("tomato_crop",
            TomatoCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block BELL_PEPPER_CROP = registerBlock("bell_pepper_crop",
            BellpepperCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block CUCUMBER_CROP = registerBlock("cucumber_crop",
            CucumberCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block FLAX_CROP = registerBlock("flax_crop",
            FlaxCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block GARLIC_CROP = registerBlock("garlic_crop",
            GarlicCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block LEEK_CROP = registerBlock("leek_crop",
            LeekCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block LETTUCE_CROP = registerBlock("lettuce_crop",
            LettuceCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block ONION_CROP = registerBlock("onion_crop",
            OnionCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);
    public static final Block PIPEWEED_CROP = registerBlock("pipeweed_crop",
            PipeweedCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POTATOES), false);

    public static final Block MIRKWOOD_ROOTS = registerBlock("mirkwood_roots",
            MangroveRootsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MANGROVE_ROOTS), true);
    public static final Block MIRKWOOD_HANGING_ROOTS = registerBlock("mirkwood_hanging_roots",
            HangingRootsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.HANGING_ROOTS), true);

    public static final Block MIRKWOOD_VINES = registerBlock("mirkwood_vines",
            CustomHangingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.PALE_HANGING_MOSS), true);

    public static final Block HANGING_WEBS = registerBlock("hanging_webs",
            HangingCobwebBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBWEB), true);

    public static final Block CORNER_COBWEB = registerBlock("corner_cobweb",
            CornerCobwebBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBWEB).noCollision().pushReaction(PushReaction.DESTROY), true);

    public static final Block WEBBING = registerBlock("webbing",
            WebbingBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().noCollision().strength(0.2f)
                    .pushReaction(PushReaction.DESTROY).sound(SoundType.COBWEB).ignitedByLava().randomTicks(), true);

    public static final Block SHELOBITE_LARVA_EGG = registerBlock("shelobite_larva_egg",
            ShelobiteLarvaEggBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG), true);
    public static final Block HANGING_SHELOBITE_LARVA_EGG = registerBlock("hanging_shelobite_larva_egg",
            ShelobiteLarvaEggHangingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG), true);

    public static final Block GLOWWORM_WEBBING = registerBlock("glowworm_webbing",
            GlowWormBottomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES_PLANT).lightLevel(state -> 6), true);
    public static final Block GLOWWORM_MAIN = registerBlock("glowworm_main",
            GlowWormBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES).lightLevel(state -> 6), false);

    public static Block registerCrossBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean present) {
        Block resultBlock = registerBlock(name, factory, settings, present);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        return resultBlock;
    }

    public static Block registerSimpleSapling(String name, ResourceKey<ConfiguredFeature<?, ?>> treeFeature) {
        SaplingBlock saplingBlock = new SaplingBlock(new TreeGrower(name, Optional.empty(), Optional.ofNullable(treeFeature), Optional.empty()),
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).setId(ModBlocks.keyOfBlock(name)));
        Block resultBlock = Registry.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name), saplingBlock);
        registerBlockItem(name, resultBlock);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        Saplings.saplings.add(resultBlock);

        TranslationEntries.blockEntries.add(resultBlock);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));

        return resultBlock;
    }

    public static Block registerVariantSapling(String name, List<ResourceKey<ConfiguredFeature<?, ?>>> treeFeatures) {
        List<TreeGrower> saplingGenerators = new ArrayList<>();
        for(ResourceKey<ConfiguredFeature<?,?>> treeFeature : treeFeatures) {
            saplingGenerators.add(new TreeGrower(name, Optional.empty(), Optional.ofNullable(treeFeature),
                            Optional.empty()));
        }
        SaplingBlock saplingBlock = new VariantSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).setId(ModBlocks.keyOfBlock(name)), saplingGenerators);
        Block resultBlock = Registry.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name), saplingBlock);

        TranslationEntries.blockEntries.add(resultBlock);

        registerBlockItem(name, resultBlock);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        Saplings.saplings.add(resultBlock);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));

        return resultBlock;
    }

    public static Block registerWeightedSapling(String name, List<WeightedSaplingBlock.WeightedTree> weightedTrees) {
        SaplingBlock saplingBlock = new WeightedSaplingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).setId(ModBlocks.keyOfBlock(name)), weightedTrees);

        Block resultBlock = Registry.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name), saplingBlock);

        TranslationEntries.blockEntries.add(resultBlock);

        registerBlockItem(name, resultBlock);
        TintableCrossModel.notTintedBlocks.add(resultBlock);
        Saplings.saplings.add(resultBlock);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));

        return resultBlock;
    }

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean present) {
        Block block = (Block)factory.apply(settings.setId(ModBlocks.keyOfBlock(name)));
        if(present){
            ModNatureBlocks.registerBlockItem(name, block);
            TranslationEntries.blockEntries.add(block);
        }

        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.BLOCK, name));

        return Registry.register(BuiltInRegistries.BLOCK, ModBlocks.keyOfBlock(name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name),
               new BlockItem(block, new Item.Properties().setId(ModBlocks.keyOfItem(name))));
        Item.BY_BLOCK.put(block, item);

        ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
