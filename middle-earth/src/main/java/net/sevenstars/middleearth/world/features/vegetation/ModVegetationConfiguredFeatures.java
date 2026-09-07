package net.sevenstars.middleearth.world.features.vegetation;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.world.features.columns.CaveColumnFeatureConfig;
import net.sevenstars.middleearth.world.features.growth.MultifaceStateFeatureConfig;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatures;
import net.sevenstars.middleearth.world.gen.ModFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.data.worldgen.features.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.material.Fluids;
import java.util.List;

public class ModVegetationConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_DELTA = registerKey("water_delta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ALLIUM = registerKey("flower_allium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ATHELAS = registerKey("flower_athelas");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_AZURE_BLUET = registerKey("flower_azure_bluet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLUE_GENTIAN = registerKey("flower_blue_gentian");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CORNFLOWER = registerKey("flower_cornflower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DORWINION = registerKey("flower_dorwinion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_GREEN_JEWEL = registerKey("flower_green_jewel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LILAC = registerKey("flower_lilac");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LEBENNIN = registerKey("flower_lebennin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LOSSARNACH = registerKey("flower_lossarnach");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MALLOS = registerKey("flower_mallos");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_NOBLEWHITE = registerKey("flower_noblewhite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_POPPY = registerKey("flower_poppy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ELANOR = registerKey("flower_elanor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_NIPHREDIL = registerKey("flower_niphredil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SIMBELMYNE = registerKey("flower_simbelmyne");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ROSE_BUSH = registerKey("flower_rose_bush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_LIGHT_BLUE = registerKey("flowers_light_blue");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_MAGENTA = registerKey("flowers_magenta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_ORANGE = registerKey("flowers_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_PINK = registerKey("flowers_pink");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_PURPLE = registerKey("flowers_purple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_RED = registerKey("flowers_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_WHITE = registerKey("flowers_white");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_YELLOW = registerKey("flowers_yellow");

    // region FIELDS
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_HEATHER = registerKey("field_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_DRY_HEATHER = registerKey("field_dry_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_LAVENDER = registerKey("field_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_WILD_WHEAT = registerKey("field_wild_wheat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_BLUE_FESCUE = registerKey("field_blue_fescue");

    // endregion
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRKWOOD_VINES = registerKey("mirkwood_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_VINES = registerKey("willow_vines");

    // region GROWTH
    public static final ResourceKey<ConfiguredFeature<?, ?>> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_GROWTH = registerKey("dry_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IVY_GROWTH = registerKey("ivy_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GROWTH = registerKey("patch_frozen_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GREEN_GROWTH = registerKey("patch_green_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_THORNY_GROWTH = registerKey("patch_thorny_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LILAC_FLOWER_GROWTH = registerKey("lilac_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_FLOWER_GROWTH = registerKey("red_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_FLOWER_GROWTH = registerKey("yellow_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_FLOWER_GROWTH = registerKey("pink_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_FLOWER_GROWTH = registerKey("white_flower_growth");
    public static final HolderSet.Direct<Block> BLOCKS_GROWTH = HolderSet.direct(Block::builtInRegistryHolder,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE,
            Blocks.MOSS_BLOCK, Blocks.CLAY, Blocks.COBBLESTONE, Blocks.DEEPSLATE, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.SPRUCE_LOG,
            WoodBlockSets.BEECH_SET.logBlocks.log(), WoodBlockSets.MAPLE_SET.logBlocks.log(), WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log(), WoodBlockSets.HOLLY_SET.logBlocks.log());
    // endregion

    // region FOLIAGE
    public static final  HolderSet<Block> BLOCKS_MOSS = HolderSet.direct(Block::builtInRegistryHolder,
            Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MOSS_BLOCK, Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
            WoodBlockSets.PINE_SET.logBlocks.log(), WoodBlockSets.LARCH_SET.logBlocks.log(), WoodBlockSets.BEECH_SET.logBlocks.log(), WoodBlockSets.MAPLE_SET.logBlocks.log(),
            WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log(), WoodBlockSets.BLACK_LEBETHRON_SET.logBlocks.log(), WoodBlockSets.WHITE_LEBETHRON_SET.logBlocks.log());

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BASALT = registerKey("patch_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMICE = registerKey("patch_pumice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_COBWEB = registerKey("patch_cobweb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PERSISTENT_WEBBING = registerKey("patch_persistent_webbing");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WEBBING = registerKey("patch_webbing");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SPIDER_EGGS = registerKey("patch_spider_eggs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN = registerKey("pumice_column");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BEACH_GRASS = registerKey("patch_beach_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BUSHES = registerKey("patch_bushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CLOVERS = registerKey("patch_clovers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BRACKEN = registerKey("patch_bracken");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GIANT_BUTTERBUR = registerKey("patch_giant_butterbur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_BULRUSH = registerKey("patch_short_bulrush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_BULRUSH = registerKey("patch_tall_bulrush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_CATTAIL = registerKey("patch_short_cattail");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_CATTAIL = registerKey("patch_tall_cattail");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_COASTAL_PANIC_GRASS = registerKey("patch_coastal_panic_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEAD_RUSHES = registerKey("patch_dead_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_DRY_SHRUB = registerKey("patch_small_dry_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_LEAVES = registerKey("patch_fallen_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MALLORN_LEAVES = registerKey("patch_fallen_mallorn_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MIRKWOOD_LEAVES = registerKey("patch_fallen_mirkwood_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOATING_ICE = registerKey("patch_floating_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GRASS = registerKey("patch_frozen_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_SHRUB = registerKey("patch_frozen_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_TUFT = registerKey("patch_frozen_tuft");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRIM_GRASS = registerKey("patch_grim_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HEATH = registerKey("patch_heath");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEAD_HEATHER = registerKey("patch_dead_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_HEATHER = registerKey("patch_dry_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_DEAD_NORMAL_HEATHER = registerKey("patch_dead_normal_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUE_LAVENDER = registerKey("patch_blue_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LAVENDER = registerKey("patch_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_LAVENDER = registerKey("patch_white_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HOGWEED = registerKey("patch_hogweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HOBBIT_SUNFLOWERS = registerKey("patch_hobbit_sunflowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUE_BIGLEAF_HYDRANGEA = registerKey("patch_blue_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PINK_BIGLEAF_HYDRANGEA = registerKey("patch_pink_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_BIGLEAF_HYDRANGEA = registerKey("patch_white_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAMPION = registerKey("patch_campion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_REEDS = registerKey("patch_reeds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_REEDS = registerKey("patch_short_reeds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_LILY_PAD = registerKey("patch_large_lily_pad");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_FLOWERING_LILY_PAD = registerKey("patch_large_flowering_lily_pad");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERING_LILY_PADS = registerKey("patch_flowering_lily_pads");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MISTWEED = registerKey("patch_mistweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOSS = registerKey("patch_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOSS_CARPET = registerKey("patch_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_SHRUB = registerKey("patch_scorched_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_TUFT = registerKey("patch_scorched_tuft");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM = registerKey("patch_sedum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_ORANGE = registerKey("patch_sedum_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_RED = registerKey("patch_sedum_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_YELLOW = registerKey("patch_sedum_yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUMS = registerKey("patch_sedums");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_SHRIVELED_SHRUB = registerKey("patch_large_shriveled_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RUSHES = registerKey("patch_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_RUSHES = registerKey("patch_short_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_TROLLIUS = registerKey("patch_yellow_trollius");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TEMPERATE_GRASS = registerKey("patch_temperate_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SWEET_BERRY_BUSH = registerKey("patch_sweet_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SPARSE_GRASS = registerKey("patch_sparse_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MEADOW_GRASS = registerKey("patch_meadow_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIXED_WILD_WHEAT = registerKey("patch_mixed_wild_wheat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_FLOWER = registerKey("patch_yellow_flower");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NETTLES = registerKey("patch_nettles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_THISTLE = registerKey("patch_thistle");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORDOR_BRAMBLES = registerKey("patch_mordor_brambles");
    // endregion

    // region MUSHROOMS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE = registerKey("patch_brown_bolete");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE_TILLER = registerKey("patch_brown_bolete_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORSEL = registerKey("patch_morsel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORSEL_TILLER = registerKey("patch_morsel_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");

    // endregion

    // region WILD CROPS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        FeatureUtils.register(featureRegisterable, WATER_DELTA, ModFeatures.DELTA_FEATURE,
                new DeltaFeatureConfiguration(Blocks.WATER.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), UniformInt.of(3, 7), UniformInt.of(0, 2)));

        FeatureUtils.register(featureRegisterable, FIELD_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.defaultBlockState(), 2))
                                )));
        FeatureUtils.register(featureRegisterable, FIELD_DRY_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.DRY_HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.DRY_HEATHER_BUSH.defaultBlockState(), 2))
                                )));

        FeatureUtils.register(featureRegisterable, FIELD_LAVENDER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LAVENDER))));

        FeatureUtils.register(featureRegisterable, FIELD_WILD_WHEAT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.TALL_WILD_WHEAT.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.WILD_WHEAT.defaultBlockState(), 3))
                                )));

        FeatureUtils.register(featureRegisterable, MIRKWOOD_VINES, ModFeatures.MIRKWOOD_VINE, new NoneFeatureConfiguration());
        FeatureUtils.register(featureRegisterable, WILLOW_VINES, ModFeatures.WILLOW_VINE, new NoneFeatureConfiguration());

        FeatureUtils.register(featureRegisterable, FLOWER_ALLIUM, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM))));
        FeatureUtils.register(featureRegisterable, FLOWER_ATHELAS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.ATHELAS))));
        FeatureUtils.register(featureRegisterable, FLOWER_AZURE_BLUET, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.AZURE_BLUET))));
        FeatureUtils.register(featureRegisterable, FLOWER_BLUE_GENTIAN, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BLUE_GENTIAN))));
        FeatureUtils.register(featureRegisterable, FLOWER_CORNFLOWER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CORNFLOWER))));
        FeatureUtils.register(featureRegisterable, FLOWER_GREEN_JEWEL, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER))));
        FeatureUtils.register(featureRegisterable, FLOWER_LILAC, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC))));
        FeatureUtils.register(featureRegisterable, FLOWER_LEBENNIN, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2143L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(),
                                        ModNatureBlocks.LIGHT_BLUE_FLOWERS.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.SHORT_GRASS.defaultBlockState()))));
        FeatureUtils.register(featureRegisterable, FLOWER_LOSSARNACH, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2241L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(),
                                        Blocks.ROSE_BUSH.defaultBlockState(), Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(),
                                        Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), Blocks.ALLIUM.defaultBlockState(), Blocks.SHORT_GRASS.defaultBlockState()))));
        FeatureUtils.register(featureRegisterable, FLOWER_DORWINION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2345L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.defaultBlockState(), Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(),
                                        Blocks.ALLIUM.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState(), Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), Blocks.SHORT_GRASS.defaultBlockState()))));
        FeatureUtils.register(featureRegisterable, FLOWER_MALLOS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.MALLOS))));
        FeatureUtils.register(featureRegisterable, FLOWER_NOBLEWHITE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.NOBLEWHITE))));
        FeatureUtils.register(featureRegisterable, FLOWER_POPPY, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.POPPY))));
        FeatureUtils.register(featureRegisterable, FLOWER_ELANOR, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.ELANOR))));
        FeatureUtils.register(featureRegisterable, FLOWER_NIPHREDIL, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.NIPHREDIL))));
        FeatureUtils.register(featureRegisterable, FLOWER_SIMBELMYNE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SIMBELMYNE))));
        FeatureUtils.register(featureRegisterable, FLOWER_ROSE_BUSH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH))));

        FeatureUtils.register(featureRegisterable, FLOWERS_LIGHT_BLUE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LIGHT_BLUE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_MAGENTA, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.MAGENTA_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_ORANGE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.ORANGE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_PINK, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.PINK_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_PURPLE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.PURPLE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_RED, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.RED_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_WHITE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WHITE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_YELLOW, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.YELLOW_FLOWERS))));

        FeatureUtils.register(featureRegisterable, AZALEA_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.AZALEA_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, DRY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.DRY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, IVY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.IVY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.FROZEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_GREEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.GREEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_THORNY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.THORNY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, LILAC_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.LILAC_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, RED_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.RED_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, YELLOW_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.YELLOW_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PINK_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.PINK_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, WHITE_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.WHITE_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));

        FeatureUtils.register(featureRegisterable, PATCH_BASALT, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(Blocks.BASALT)));
        FeatureUtils.register(featureRegisterable, PATCH_BLACKSTONE, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(Blocks.BLACKSTONE)));
        FeatureUtils.register(featureRegisterable, PATCH_PUMICE, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(StoneBlockSets.PUMICE_SET.baseBlocks.base())));

        FeatureUtils.register(featureRegisterable, PATCH_COBWEB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.COBWEB))));

        FeatureUtils.register(featureRegisterable, PATCH_PERSISTENT_WEBBING, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.WEBBING,
                        20, true, true, true, 0.95f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_WEBBING, ModFeatures.MULTIFACE_PERSISTENT,
                new MultifaceStateFeatureConfig(ModNatureBlocks.WEBBING,
                        20, true, true, true, false, 0.95f, BLOCKS_GROWTH));

        FeatureUtils.register(featureRegisterable, PATCH_SPIDER_EGGS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHELOBITE_LARVA_EGG))));

        FeatureUtils.register(featureRegisterable, PUMICE_COLUMN, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(ConstantInt.of(1), UniformInt.of(1, 4), StoneBlockSets.PUMICE_SET.baseBlocks.base().defaultBlockState()));
        FeatureUtils.register(featureRegisterable, PUMICE_COLUMN_LARGE, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(UniformInt.of(2, 3), UniformInt.of(5, 10), StoneBlockSets.PUMICE_SET.baseBlocks.base().defaultBlockState()));

        FeatureUtils.register(featureRegisterable, PATCH_BRACKEN, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BRACKEN))));

        FeatureUtils.register(featureRegisterable, PATCH_GIANT_BUTTERBUR, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GIANT_BUTTERBUR))));

        FeatureUtils.register(featureRegisterable, FIELD_BLUE_FESCUE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.BLUE_FESCUE.defaultBlockState(), 2)
                                                .add(ModNatureBlocks.LARGE_BLUE_FESCUE.defaultBlockState(), 1))
                                )));

        FeatureUtils.register(featureRegisterable, PATCH_BROWN_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BROWN_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_SHORT_BULRUSH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHORT_BULRUSH))));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_CATTAIL, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHORT_CATTAILS))));

        FeatureUtils.register(featureRegisterable, PATCH_TALL_BULRUSH, Feature.SIMPLE_BLOCK,
                createShallowWaterPatchFeatureConfig(ModNatureBlocks.TALL_BULRUSH, 96, 7, 3));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_CATTAIL, Feature.SIMPLE_BLOCK,
                createShallowWaterPatchFeatureConfig(ModNatureBlocks.TALL_CATTAILS, 96, 7, 3));

        FeatureUtils.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.COASTAL_PANIC_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.CORRUPTED_MOSS,
                        20, true, true, true, 0.5f,
                        HolderSet.direct(Block::builtInRegistryHolder,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT,  Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
                                WoodBlockSets.MIRKWOOD_SET.logBlocks.log(), ModNatureBlocks.OLD_PODZOL)));

        FeatureUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(ModNatureBlocks.CORRUPTED_MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_DEAD_RUSHES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.DEAD_RUSHES))));

        FeatureUtils.register(featureRegisterable, PATCH_BEACH_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BEACH_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_BUSHES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(Blocks.BUSH.defaultBlockState(), 8)
                                .add(ModNatureBlocks.LARGE_BUSH.defaultBlockState(), 4))), List.of(), 15));
        FeatureUtils.register(featureRegisterable, PATCH_CLOVERS, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.CLOVERS))));
        FeatureUtils.register(featureRegisterable, PATCH_DRY_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(Blocks.TALL_DRY_GRASS.defaultBlockState(), 8)
                                .add(Blocks.SHORT_DRY_GRASS.defaultBlockState(), 4))), List.of(), 15));
        FeatureUtils.register(featureRegisterable, PATCH_SMALL_DRY_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SMALL_DRY_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_DUCKWEED, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.DUCKWEED)));

        FeatureUtils.register(featureRegisterable, PATCH_DYING_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.DYING_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(ModNatureBlocks.FALLEN_LEAVES)));
        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_MALLORN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(ModNatureBlocks.FALLEN_MALLORN_LEAVES)));
        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_MIRKWOOD_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES)));

        FeatureUtils.register(featureRegisterable, PATCH_FALSE_OATGRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FALSE_OATGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.FOREST_MOSS,
                    20, true, true, true, 0.5f, BLOCKS_MOSS));

        FeatureUtils.register(featureRegisterable, PATCH_FLOATING_ICE, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FLOATING_ICE)));

        FeatureUtils.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(ModNatureBlocks.FOREST_MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FROZEN_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FROZEN_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_TUFT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FROZEN_TUFT))));

        FeatureUtils.register(featureRegisterable, PATCH_GREEN_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GREEN_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_GRIM_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GRIM_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_HEATH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.HEATH))));
        FeatureUtils.register(featureRegisterable, PATCH_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.defaultBlockState(), 1))
                                )));
        FeatureUtils.register(featureRegisterable, PATCH_DEAD_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.DEAD_HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER_BUSH.defaultBlockState(), 1))
                                )));
        FeatureUtils.register(featureRegisterable, PATCH_DRY_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.DRY_HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.DRY_HEATHER_BUSH.defaultBlockState(), 1))
                                )));

        FeatureUtils.register(featureRegisterable, PATCH_RED_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.RED_HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.RED_HEATHER_BUSH.defaultBlockState(), 1))
                                )));
        FeatureUtils.register(featureRegisterable, FIELD_DEAD_NORMAL_HEATHER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER_BUSH.defaultBlockState(), 1))
                                )));

        FeatureUtils.register(featureRegisterable, PATCH_BLUE_LAVENDER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BLUE_LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_LAVENDER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_WHITE_LAVENDER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WHITE_LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_HOGWEED, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.HOGWEED.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.SHORT_HOGWEED.defaultBlockState(), 3))
                                        )));

        FeatureUtils.register(featureRegisterable, PATCH_HOBBIT_SUNFLOWERS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.HOBBIT_SUNFLOWERS))));

        FeatureUtils.register(featureRegisterable, PATCH_BLUE_BIGLEAF_HYDRANGEA, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA))));
        FeatureUtils.register(featureRegisterable, PATCH_PINK_BIGLEAF_HYDRANGEA, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA))));
        FeatureUtils.register(featureRegisterable, PATCH_WHITE_BIGLEAF_HYDRANGEA, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA))));

        FeatureUtils.register(featureRegisterable, PATCH_CAMPION, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.CAMPION))));

        FeatureUtils.register(featureRegisterable, PATCH_REEDS, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ResourceItemsME.REEDS)));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_REEDS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHORT_REEDS))));

        FeatureUtils.register(featureRegisterable, PATCH_MISTWEED, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.MISTWEED))));

        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SCORCHED_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SCORCHED_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_TUFT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SCORCHED_TUFT))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_ORANGE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.ORANGE_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_RED, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.RED_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_YELLOW, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.YELLOW_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUMS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                        new WeightedStateProvider(WeightedList.<BlockState>builder()
                                                .add(ModNatureBlocks.ORANGE_SEDUM.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.RED_SEDUM.defaultBlockState(), 1)
                                                .add(ModNatureBlocks.YELLOW_SEDUM.defaultBlockState(), 1))
                                )));

        FeatureUtils.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHRIVELED_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_LARGE_SHRIVELED_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LARGE_SHRIVELED_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_RUSHES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.RUSHES))));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_RUSHES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SHORT_RUSHES))));

        FeatureUtils.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.STRAWBERRY_BUSH.defaultBlockState()
                                .setValue(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_TROLLIUS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.YELLOW_TROLLIUS))));

        FeatureUtils.register(featureRegisterable, PATCH_TAN_SHRUB, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TAN_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_TEMPERATE_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TEMPERATE_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_SWEET_BERRY_BUSH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SWEET_BERRY_BUSH.defaultBlockState()
                                .setValue(SweetBerryBushBlock.AGE, 0))), List.of(
                                        Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.GRASSY_DIRT,
                                ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM, ModBlocks.GRASSY_LOAM,
                                ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT, ModBlocks.GRASSY_PEAT,
                                ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT, ModBlocks.GRASSY_SILT)));

        FeatureUtils.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TOUGH_BERRY_BUSH.defaultBlockState()
                                .setValue(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.ASHEN_DIRT)));

        FeatureUtils.register(featureRegisterable, PATCH_TUFT_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GRASS_TUFT))));

        FeatureUtils.register(featureRegisterable, PATCH_SPARSE_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SPARSE_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_LARGE_LILY_PAD, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LARGE_LILY_PAD)));
        FeatureUtils.register(featureRegisterable, PATCH_LARGE_FLOWERING_LILY_PAD, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LARGE_FLOWERING_LILY_PAD)));

        FeatureUtils.register(featureRegisterable, PATCH_LILY_PADS, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.LILY_PADS)));
        FeatureUtils.register(featureRegisterable, PATCH_FLOWERING_LILY_PADS, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.FLOWERING_LILY_PADS)));

        FeatureUtils.register(featureRegisterable, PATCH_SMALL_LILY_PADS, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SMALL_LILY_PADS)));
        FeatureUtils.register(featureRegisterable, PATCH_SMALL_FLOWERING_LILY_PADS, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS)));

        FeatureUtils.register(featureRegisterable, PATCH_MIRKWOOD, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                                .add(Blocks.SHORT_GRASS.defaultBlockState(), 8)
                                .add(Blocks.FERN.defaultBlockState(), 8)
                                .add(Blocks.TALL_GRASS.defaultBlockState(), 15)
                                .add(Blocks.LARGE_FERN.defaultBlockState(), 10)
                                .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1))), List.of(), 15));

        FeatureUtils.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, Feature.BLOCK_PILE,
                new BlockPileConfiguration(new WeightedStateProvider(WeightedList.<BlockState>builder()
                        .add(ModNatureBlocks.MIRKWOOD_ROOTS.defaultBlockState(), 3))));

        FeatureUtils.register(featureRegisterable, PATCH_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.MOSS,
                        20, true, true, true, 0.5f, BLOCKS_MOSS));
        FeatureUtils.register(featureRegisterable, PATCH_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.simple(Blocks.MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceSpreadeableBlock)ModNatureBlocks.STICKY_SNOW,
                        32, true, true, true, 0.75f,
                        HolderSet.direct(Block::builtInRegistryHolder,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.BASALT)));

        FeatureUtils.register(featureRegisterable, PATCH_MIXED_WILD_WHEAT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider((WeightedList.<BlockState>builder()
                                .add(ModNatureBlocks.TALL_WILD_WHEAT.defaultBlockState(), 1)
                                .add(ModNatureBlocks.WILD_WHEAT.defaultBlockState(), 3))
                        ))));

        FeatureUtils.register(featureRegisterable, PATCH_WHEAT_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WHEATGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_MEADOW_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.MEADOWGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_WILD_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_WILDER_GRASS, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILDERGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_FLOWER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.YELLOW_FLOWER))));

        FeatureUtils.register(featureRegisterable, PATCH_NETTLES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.NETTLES))));

        FeatureUtils.register(featureRegisterable, PATCH_THISTLE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.THISTLE))));

        FeatureUtils.register(featureRegisterable, PATCH_MORDOR_BRAMBLES, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BRAMBLES_OF_MORDOR))));

        // region MUSHROOMS
        FeatureUtils.register(featureRegisterable, PATCH_BROWN_BOLETE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.BROWN_BOLETE))));
        FeatureUtils.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.BROWN_BOLETE_TILLER))));

        FeatureUtils.register(featureRegisterable, PATCH_MORSEL, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.MORSEL))));
        FeatureUtils.register(featureRegisterable, PATCH_MORSEL_TILLER, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.MORSEL_TILLER))));

        FeatureUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WHITE_MUSHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.WHITE_MUSHROOM_TILLER))));
        // endregion

        // region WILD CROPS
        FeatureUtils.register(featureRegisterable, PATCH_WILD_BEETROOT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_BEETROOT))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_BELL_PEPPER))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_CARROT, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_CARROT))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_CUCUMBER, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_CUCUMBER))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_FLAX, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_FLAX))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_GARLIC, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_GARLIC))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_LEEK, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_LEEK))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_LETTUCE, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_LETTUCE))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_ONION, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_ONION))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_PIPEWEED, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_PIPEWEED))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_POTATO, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_POTATO))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_TOMATO, Feature.SIMPLE_BLOCK,
                simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.WILD_TOMATO))));
        // endregion
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    private static SimpleBlockConfiguration createShallowWaterPatchFeatureConfig(Block block, int tries, int xzSpread, int ySpread) {
        BlockState state = block.defaultBlockState();
        return new SimpleBlockConfiguration(BlockStateProvider.simple(state));
    }

    private static SimpleBlockConfiguration simplePatchConfiguration(Feature<SimpleBlockConfiguration> feature, SimpleBlockConfiguration config) {
        return config;
    }

    private static SimpleBlockConfiguration simplePatchConfiguration(Feature<SimpleBlockConfiguration> feature, SimpleBlockConfiguration config, List<Block> ignoredBlocks) {
        return config;
    }

    private static SimpleBlockConfiguration simplePatchConfiguration(Feature<SimpleBlockConfiguration> feature, SimpleBlockConfiguration config, List<Block> ignoredBlocks, int tries) {
        return config;
    }

    private static BlockPredicate hasAdjacentBank() {
        return BlockPredicate.anyOf(
                BlockPredicate.hasSturdyFace(new BlockPos(1, 0, 0), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(-1, 0, 0), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(0, 0, 1), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(0, 0, -1), Direction.UP));
    }
}
