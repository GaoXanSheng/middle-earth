package net.sevenstars.middleearth.registries;

import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.registry.*;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteractions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.pathfinder.PathType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.datageneration.content.models.HotMetalsModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleDyeableItemModel;
import net.sevenstars.middleearth.datageneration.content.tags.LeavesSets;
import net.sevenstars.middleearth.datageneration.content.tags.Saplings;
import net.sevenstars.middleearth.item.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistriesME {

    public static final HashMap<String, String> specialAliases = new HashMap<>();

    public static void registerRegistryAliases() {
        specialAliases.put("khagalaban", "gonluin");

        specialAliases.put("chiseled_blackstone_bricks", "chiseled_polished_blackstone_bricks");

        specialAliases.put("carved_window_vertical_slab", "carved_window_pane");

        specialAliases.put("chiseled_gilded", "gilded_chiseled");
        specialAliases.put("chiseled_polished_gilded", "gilded_chiseled_polished");
        specialAliases.put("chiseled_smooth_gilded", "gilded_chiseled_smooth");

        specialAliases.put("ashenstone_", "ashen_");
        specialAliases.put("cobbled_ashenstone", "ashen_cobblestone");

        specialAliases.put("polished_ashenstone", "polished_ashen_stone");
        specialAliases.put("chiseled_polished_ashenstone", "chiseled_polished_ashen_stone");

        specialAliases.put("brick_", "bricks_");
        specialAliases.put("tile_", "tiles_");

        //ashen_stone

        if (MiddleEarth.IS_DEBUG){
            try {
                File aliases = new File("aliases.txt");

                if (aliases.createNewFile()) {
                    MiddleEarth.LOGGER.logInfoMsg("File created: " + aliases.getName());
                } else {
                    MiddleEarth.LOGGER.logWarn("File already exists.");
                }
            } catch (IOException e) {
                MiddleEarth.LOGGER.logError("An error occurred.", e);
            }

            try {
                FileWriter myWriter = new FileWriter("aliases.txt");
                for (RegistryAliasesME.Alias alias: RegistryAliasesME.aliases) {
                    String name = alias.name();
                    for (Map.Entry<String, String> map : specialAliases.entrySet()) {
                        name = name.replaceAll(map.getKey(), map.getValue());
                    }
                    alias.registry().addAlias(Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, name), Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.name()));
                    myWriter.write(alias.registry().key().identifier().getPath() + ": " + Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, name) + " -> " + Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.name()) + "\r\n");
                }

                for (RegistryAliasesME.ManualAlias alias: RegistryAliasesME.manualAliases) {
                    alias.registry().addAlias(Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, alias.oldName()), Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.newName()));
                    myWriter.write(alias.registry().key().identifier().getPath() + ": " + Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, alias.oldName()) + " -> " + Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.newName()) + "\r\n");
                }

                myWriter.close();
                MiddleEarth.LOGGER.logTrace("Successfully wrote to the file.");
            } catch (IOException e) {
                MiddleEarth.LOGGER.logError("RegistriesME :: An error occurred.", e);
            }
        } else {
            for (RegistryAliasesME.Alias alias: RegistryAliasesME.aliases) {
                String name = alias.name();
                for (Map.Entry<String, String> map : specialAliases.entrySet()) {
                    name = name.replaceAll(map.getKey(), map.getValue());
                }
                alias.registry().addAlias(Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, name), Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.name()));
            }

            for (RegistryAliasesME.ManualAlias alias: RegistryAliasesME.manualAliases) {
                alias.registry().addAlias(Identifier.fromNamespaceAndPath(MiddleEarth.OLD_MOD_ID, alias.oldName()), Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, alias.newName()));
            }
        }
    }

    public static void registerToolTipAppenders() {
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.ARTISAN_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.TEMPERATURE_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.ARMOR_TIER_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.WEAPON_TYPE_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.FACTION_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.RACE_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.HELMET_ATTACHMENT_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.BACK_ATTACHMENT_DATA);
        ItemComponentTooltipProviderRegistry.addAfter(DataComponents.TRIM, DataComponentTypesME.BLOCK_AUTHOR_DATA);
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_VERTICAL_SLAB, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.WHITE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.ORANGE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.MAGENTA_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.YELLOW_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIME_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PINK_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.CYAN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.PURPLE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLUE_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BROWN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.GREEN_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.RED_WOOL_STAIRS, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.BLACK_WOOL_STAIRS, 30, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.REINFORCED_SCAFFOLDING, 60, 60);

        FlammableBlockRegistry.getDefaultInstance().add(ModDecorativeBlocks.WOOD_PILE, 5, 5);

        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.LEBETHRON_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.BERRY_HOLLY_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.DRY_LARCH_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.ORANGE_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.RED_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.YELLOW_MAPLE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.DRY_PINE_LEAVES, 30, 60);
        FlammableBlockRegistry.getDefaultInstance().add(ModNatureBlocks.PINE_BRANCHES, 30, 60);

        LeavesSets.leaves.forEach(block -> {
            FlammableBlockRegistry.getDefaultInstance().add(block, 30, 60);
        });
    }

    public static void registerTillableBlocks() {
        TillableBlockRegistry.register(ModBlocks.DRY_DIRT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.CHALKSOIL_GRASS_BLOCK, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.CHALKSOIL, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.GRASSY_CHALKSOIL, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.COARSE_CHALKSOIL, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.CHALKSOIL_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.LOAM_GRASS_BLOCK, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.LOAM_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.LOAM, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.LOAM_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.GRASSY_LOAM, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.LOAM_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.COARSE_LOAM, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.LOAM_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.PEAT_GRASS_BLOCK, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.PEAT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.PEAT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.PEAT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.GRASSY_PEAT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.PEAT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.COARSE_PEAT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.PEAT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.SILT_GRASS_BLOCK, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.SILT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.SILT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.SILT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.GRASSY_SILT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.SILT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.COARSE_SILT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.SILT_FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.DIRTY_ROOTS, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.GRASSY_DIRT, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState()));
        TillableBlockRegistry.register(ModBlocks.TURF, HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState()));
    }

    public static void registerFlattenableBlocks() {
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.DRY_DIRT, Blocks.DIRT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.CHALKSOIL_GRASS_BLOCK, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.GRASSY_CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.COARSE_CHALKSOIL, ModBlocks.CHALKSOIL_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.GRASSY_LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.COARSE_LOAM, ModBlocks.LOAM_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.GRASSY_PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.COARSE_PEAT, ModBlocks.PEAT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.SILT, ModBlocks.SILT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.GRASSY_SILT, ModBlocks.SILT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.COARSE_SILT, ModBlocks.SILT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.DIRTY_ROOTS, Blocks.DIRT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.GRASSY_DIRT, Blocks.DIRT_PATH.defaultBlockState());
        net.minecraft.world.item.ShovelItem.FLATTENABLES.put(ModBlocks.TURF, Blocks.DIRT_PATH.defaultBlockState());
    }

    public static void registerAgingCopperBlocks() {
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerWaxable(ModBlocks.CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        OxidizableBlocksRegistry.registerNextStage(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerWaxable(ModBlocks.COPPER_BARS, ModBlocks.WAXED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WAXED_EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.WAXED_WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.OXIDIZED_COPPER_BARS, ModBlocks.WAXED_OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerNextStage(ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS);

        OxidizableBlocksRegistry.registerNextStage(ModBlocks.CUT_COPPER_WALL, ModBlocks.EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerNextStage(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.OXIDIZED_CUT_COPPER_WALL);

        OxidizableBlocksRegistry.registerWaxable(ModBlocks.CUT_COPPER_WALL, ModBlocks.WAXED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.EXPOSED_CUT_COPPER_WALL, ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.WEATHERED_CUT_COPPER_WALL, ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL);
        OxidizableBlocksRegistry.registerWaxable(ModBlocks.OXIDIZED_CUT_COPPER_WALL, ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL);
        
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.THATCH.blockSet.base(), GenericBlockSets.WEATHERED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.THATCH.blockSet.slab(), GenericBlockSets.WEATHERED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.THATCH.blockSet.verticalSlab(), GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.THATCH.blockSet.stairs(), GenericBlockSets.WEATHERED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.THATCH.blockSet.wall(), GenericBlockSets.WEATHERED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_THATCH.blockSet.base(), GenericBlockSets.AGED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_THATCH.blockSet.slab(), GenericBlockSets.AGED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSets.AGED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSets.AGED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_THATCH.blockSet.wall(), GenericBlockSets.AGED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_THATCH.blockSet.base(), GenericBlockSets.OLD_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_THATCH.blockSet.slab(), GenericBlockSets.OLD_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSets.OLD_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_THATCH.blockSet.stairs(), GenericBlockSets.OLD_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_THATCH.blockSet.wall(), GenericBlockSets.OLD_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_THATCH.blockSet.base(), GenericBlockSets.ROTTEN_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_THATCH.blockSet.slab(), GenericBlockSets.ROTTEN_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSets.ROTTEN_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_THATCH.blockSet.stairs(), GenericBlockSets.ROTTEN_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_THATCH.blockSet.wall(), GenericBlockSets.ROTTEN_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.THATCH.blockSet.base(), GenericBlockSets.WAXED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.THATCH.blockSet.slab(), GenericBlockSets.WAXED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.THATCH.blockSet.stairs(), GenericBlockSets.WAXED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.THATCH.blockSet.wall(), GenericBlockSets.WAXED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.base(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_WEATHERED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.base(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_AGED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.base(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.slab(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_THATCH.blockSet.wall(), GenericBlockSets.WAXED_OLD_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.base(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.slab(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_THATCH.blockSet.wall(), GenericBlockSets.WAXED_ROTTEN_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.REED_THATCH.blockSet.base(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.REED_THATCH.blockSet.slab(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.REED_THATCH.blockSet.stairs(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.REED_THATCH.blockSet.wall(), GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSets.AGED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSets.AGED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSets.AGED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSets.AGED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_REED_THATCH.blockSet.base(), GenericBlockSets.OLD_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_REED_THATCH.blockSet.slab(), GenericBlockSets.OLD_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSets.OLD_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.AGED_REED_THATCH.blockSet.wall(), GenericBlockSets.OLD_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_REED_THATCH.blockSet.base(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_REED_THATCH.blockSet.slab(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerNextStage(GenericBlockSets.OLD_REED_THATCH.blockSet.wall(), GenericBlockSets.ROTTEN_REED_THATCH.blockSet.wall());

        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.WEATHERED_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_WEATHERED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.AGED_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_AGED_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.OLD_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_OLD_REED_THATCH.blockSet.wall());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.base(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.base());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.slab(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.slab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.verticalSlab(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.verticalSlab());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.stairs(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.stairs());
        OxidizableBlocksRegistry.registerWaxable(GenericBlockSets.ROTTEN_REED_THATCH.blockSet.wall(), GenericBlockSets.WAXED_ROTTEN_REED_THATCH.blockSet.wall());
    }

    public static void registerFuels() {
        FuelValueEvents.BUILD.register(((builder, context) -> {
            builder.add(ModNatureBlocks.MIRKWOOD_ROOTS, 300);
            builder.add(ModNatureBlocks.GREEN_SHRUB, 100);
            builder.add(ModNatureBlocks.SMALL_DRY_SHRUB, 100);
            builder.add(ModNatureBlocks.TAN_SHRUB, 100);

            builder.add(ModDecorativeBlocks.ROPE, 150);
            builder.add(ModDecorativeBlocks.WOOD_PILE, 200);

            builder.add(ModBlocks.WHITE_WOOL_SLAB, 50);
            builder.add(ModBlocks.ORANGE_WOOL_SLAB, 50);
            builder.add(ModBlocks.MAGENTA_WOOL_SLAB, 50);
            builder.add(ModBlocks.LIGHT_BLUE_WOOL_SLAB, 50);
            builder.add(ModBlocks.YELLOW_WOOL_SLAB, 50);
            builder.add(ModBlocks.LIME_WOOL_SLAB, 50);
            builder.add(ModBlocks.PINK_WOOL_SLAB, 50);
            builder.add(ModBlocks.GRAY_WOOL_SLAB, 50);
            builder.add(ModBlocks.LIGHT_GRAY_WOOL_SLAB, 50);
            builder.add(ModBlocks.CYAN_WOOL_SLAB, 50);
            builder.add(ModBlocks.PURPLE_WOOL_SLAB, 50);
            builder.add(ModBlocks.BLUE_WOOL_SLAB, 50);
            builder.add(ModBlocks.BROWN_WOOL_SLAB, 50);
            builder.add(ModBlocks.GREEN_WOOL_SLAB, 50);
            builder.add(ModBlocks.RED_WOOL_SLAB, 50);
            builder.add(ModBlocks.BLACK_WOOL_SLAB, 50);

            builder.add(ModBlocks.WHITE_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.ORANGE_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.MAGENTA_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.LIGHT_BLUE_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.YELLOW_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.LIME_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.PINK_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.GRAY_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.LIGHT_GRAY_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.CYAN_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.PURPLE_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.BLUE_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.BROWN_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.GREEN_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.RED_WOOL_VERTICAL_SLAB, 50);
            builder.add(ModBlocks.BLACK_WOOL_VERTICAL_SLAB, 50);

            builder.add(ModBlocks.WHITE_WOOL_STAIRS, 100);
            builder.add(ModBlocks.ORANGE_WOOL_STAIRS, 100);
            builder.add(ModBlocks.MAGENTA_WOOL_STAIRS, 100);
            builder.add(ModBlocks.LIGHT_BLUE_WOOL_STAIRS, 100);
            builder.add(ModBlocks.YELLOW_WOOL_STAIRS, 100);
            builder.add(ModBlocks.LIME_WOOL_STAIRS, 100);
            builder.add(ModBlocks.PINK_WOOL_STAIRS, 100);
            builder.add(ModBlocks.GRAY_WOOL_STAIRS, 100);
            builder.add(ModBlocks.LIGHT_GRAY_WOOL_STAIRS, 100);
            builder.add(ModBlocks.CYAN_WOOL_STAIRS, 100);
            builder.add(ModBlocks.PURPLE_WOOL_STAIRS, 100);
            builder.add(ModBlocks.BLUE_WOOL_STAIRS, 100);
            builder.add(ModBlocks.BROWN_WOOL_STAIRS, 100);
            builder.add(ModBlocks.GREEN_WOOL_STAIRS, 100);
            builder.add(ModBlocks.RED_WOOL_STAIRS, 100);
            builder.add(ModBlocks.BLACK_WOOL_STAIRS, 100);

            builder.add(WeaponItemsME.WOODEN_SPEAR, 300);
            builder.add(WeaponItemsME.WOODEN_DAGGER, 150);

            builder.add(WeaponItemsME.GONDORIAN_BOW, 300);
            builder.add(WeaponItemsME.GONDORIAN_LONGBOW, 400);
            builder.add(WeaponItemsME.GONDORIAN_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.ROHIRRIC_BOW, 300);
            builder.add(WeaponItemsME.ROHIRRIC_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.ROHIRRIC_LONGBOW, 400);

            builder.add(WeaponItemsME.LORIEN_BOW, 300);
            builder.add(WeaponItemsME.LORIEN_LONGBOW, 400);
            builder.add(WeaponItemsME.LORIEN_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.WOODLAND_REALM_BOW, 300);
            builder.add(WeaponItemsME.WOODLAND_REALM_LONGBOW, 400);
            builder.add(WeaponItemsME.WOODLAND_REALM_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW, 400);

            builder.add(WeaponItemsME.EREBOR_BOW, 300);
            builder.add(WeaponItemsME.EREBOR_NOBLE_BOW, 300);
            builder.add(WeaponItemsME.EREBOR_CROSSBOW, 400);
            builder.add(WeaponItemsME.EREBOR_NOBLE_CROSSBOW, 400);

            builder.add(WeaponItemsME.ORCISH_BOW, 300);

            builder.add(WeaponItemsME.MORDOR_BOW, 300);
            builder.add(WeaponItemsME.MORDOR_ELITE_LONGBOW, 400);

            builder.add(WeaponItemsME.URUK_HAI_BOW, 300);
            builder.add(WeaponItemsME.URUK_HAI_CROSSBOW, 400);

            builder.add(WeaponItemsME.GUNDABAD_BOW, 300);
            builder.add(WeaponItemsME.GUNDABAD_LONGBOW, 400);
            builder.add(WeaponItemsME.GOBLIN_CROSSBOW, 400);

            builder.add(WeaponItemsME.MORIA_GOBLIN_BOW, 300);
            builder.add(WeaponItemsME.GOBLIN_TOWN_BOW, 300);
        }));
    }

    public static void registerComposterBlocks() {
        CompostableRegistry registry = CompostableRegistry.INSTANCE;

        registry.add(ModNatureBlocks.TAN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.GREEN_SHRUB, 0.50f);
        registry.add(ModNatureBlocks.SMALL_DRY_SHRUB, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_SHRUB, 0.10f);
        registry.add(ModNatureBlocks.MORGUL_IVY, 0.40f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS_BLOCK, 0.65f);
        registry.add(ModNatureBlocks.CORRUPTED_MOSS, 0.35f);
        registry.add(ModNatureBlocks.MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS, 0.35f);
        registry.add(ModNatureBlocks.FOREST_MOSS_CARPET, 0.30f);
        registry.add(ModNatureBlocks.FOREST_MOSS_BLOCK, 0.65f);

        registry.add(ModNatureBlocks.AZALEA_FLOWER_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.DRY_GROWTH, 0.35f);
        registry.add(ModNatureBlocks.GREEN_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.IVY_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.LILAC_FLOWER_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.PINK_FLOWER_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.RED_FLOWER_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.THORNY_GROWTH, 0.35f);
        registry.add(ModNatureBlocks.WHITE_FLOWER_GROWTH, 0.50f);
        registry.add(ModNatureBlocks.YELLOW_FLOWER_GROWTH, 0.50f);

        registry.add(ModNatureBlocks.ELANOR, 0.65f);
        registry.add(ModNatureBlocks.MALLOS, 0.65f);
        registry.add(ModNatureBlocks.NIPHREDIL, 0.65f);
        registry.add(ModNatureBlocks.SIMBELMYNE, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_FLOWER, 0.65f);
        registry.add(ModNatureBlocks.BLUE_GENTIAN, 0.65f);
        registry.add(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER, 0.65f);
        registry.add(ModNatureBlocks.NOBLEWHITE, 0.65f);

        registry.add(ModNatureBlocks.LIGHT_BLUE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.MAGENTA_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.ORANGE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.PINK_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.PURPLE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.RED_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.WHITE_FLOWERS, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_FLOWERS, 0.65f);

        registry.add(ModNatureBlocks.AZALEA_FLOWER_GROWTH, 0.65f);

        registry.add(ModNatureBlocks.BLUE_LAVENDER, 0.65f);
        registry.add(ModNatureBlocks.LAVENDER, 0.65f);
        registry.add(ModNatureBlocks.WHITE_LAVENDER, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_TROLLIUS, 0.65f);
        registry.add(ModNatureBlocks.HOBBIT_SUNFLOWERS, 0.65f);

        registry.add(ModNatureBlocks.ATHELAS, 0.30f);

        registry.add(ModNatureBlocks.BROWN_GRASS, 0.30f);
        registry.add(ModNatureBlocks.DYING_GRASS, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_GRASS, 0.10f);
        registry.add(ModNatureBlocks.GRIM_GRASS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_HOGWEED, 0.30f);
        registry.add(ModNatureBlocks.HOGWEED, 0.30f);
        registry.add(ModNatureBlocks.MEADOWGRASS, 0.30f);
        registry.add(ModNatureBlocks.SPARSE_GRASS, 0.30f);
        registry.add(ModNatureBlocks.NETTLES, 0.30f);
        registry.add(ModNatureBlocks.THISTLE, 0.30f);
        registry.add(ModNatureBlocks.TEMPERATE_GRASS, 0.30f);
        registry.add(ModNatureBlocks.BLUE_FESCUE, 0.30f);
        registry.add(ModNatureBlocks.GRASS_TUFT, 0.30f);
        registry.add(ModNatureBlocks.FROZEN_TUFT, 0.10f);
        registry.add(ModNatureBlocks.WHEATGRASS, 0.30f);
        registry.add(ModNatureBlocks.WILD_GRASS, 0.30f);
        registry.add(ModNatureBlocks.WILDERGRASS, 0.30f);
        registry.add(ModNatureBlocks.BEACH_GRASS, 0.30f);
        registry.add(ModNatureBlocks.COASTAL_PANIC_GRASS, 0.30f);
        registry.add(ModNatureBlocks.MISTWEED, 0.30f);
        registry.add(ModNatureBlocks.SEDUM, 0.30f);
        registry.add(ModNatureBlocks.ORANGE_SEDUM, 0.30f);
        registry.add(ModNatureBlocks.RED_SEDUM, 0.30f);
        registry.add(ModNatureBlocks.YELLOW_SEDUM, 0.30f);
        registry.add(ModNatureBlocks.BRACKEN, 0.30f);
        registry.add(ModNatureBlocks.GIANT_BUTTERBUR, 0.30f);
        registry.add(ModNatureBlocks.CAMPION, 0.30f);
        registry.add(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA, 0.30f);
        registry.add(ModNatureBlocks.DEAD_HEATHER_BUSH, 0.30f);
        registry.add(ModNatureBlocks.DRY_HEATHER_BUSH, 0.30f);
        registry.add(ModNatureBlocks.DEAD_RUSHES, 0.30f);
        registry.add(ModNatureBlocks.FALSE_OATGRASS, 0.30f);
        registry.add(ModNatureBlocks.HEATHER_BUSH, 0.30f);
        registry.add(ModNatureBlocks.LARGE_BLUE_FESCUE, 0.30f);
        registry.add(ModNatureBlocks.LARGE_BUSH, 0.30f);
        registry.add(ModNatureBlocks.LARGE_SHRIVELED_SHRUB, 0.10f);
        registry.add(ModNatureBlocks.LILY_PADS, 0.65f);
        registry.add(ModNatureBlocks.FLOWERING_LILY_PADS, 0.65f);
        registry.add(ModNatureBlocks.SMALL_LILY_PADS, 0.65f);
        registry.add(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, 0.65f);
        registry.add(ModNatureBlocks.RED_HEATHER_BUSH, 0.30f);
        registry.add(ModNatureBlocks.RUSHES, 0.30f);
        registry.add(ModNatureBlocks.BRAMBLES_OF_MORDOR, 0.10f);
        registry.add(ModNatureBlocks.CLOVERS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_DEAD_RUSHES, 0.30f);
        registry.add(ModNatureBlocks.SHORT_RUSHES, 0.30f);
        registry.add(ModNatureBlocks.SHORT_REEDS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_CATTAILS, 0.30f);
        registry.add(ModNatureBlocks.SHORT_BULRUSH, 0.30f);
        registry.add(ModNatureBlocks.TALL_CATTAILS, 0.30f);
        registry.add(ModNatureBlocks.HEATHER, 0.50f);
        registry.add(ModNatureBlocks.RED_HEATHER, 0.50f);
        registry.add(ModNatureBlocks.DEAD_HEATHER, 0.30f);
        registry.add(ModNatureBlocks.DRY_HEATHER, 0.30f);
        registry.add(ModNatureBlocks.HEATH, 0.30f);
        registry.add(ModNatureBlocks.TALL_BULRUSH, 0.30f);

        registry.add(ModNatureBlocks.SHRIVELED_SHRUB, 0.30f);

        registry.add(ModNatureBlocks.SCORCHED_GRASS, 0.10f);
        registry.add(ModNatureBlocks.SCORCHED_TUFT, 0.10f);
        registry.add(ModNatureBlocks.SCORCHED_SHRUB, 0.10f);

        registry.add(ModNatureBlocks.BROWN_BOLETE, 0.65f);
        registry.add(ModNatureBlocks.CAVE_AMANITA, 0.65f);
        registry.add(ModNatureBlocks.DEEP_FIRECAP, 0.65f);
        registry.add(ModNatureBlocks.GHOSTSHROOM, 0.65f);
        registry.add(ModNatureBlocks.MORSEL, 0.65f);
        registry.add(ModNatureBlocks.SKY_FIRECAP, 0.65f);
        registry.add(ModNatureBlocks.TRUMPET_SHROOM, 0.65f);
        registry.add(ModNatureBlocks.TALL_TRUMPET_SHROOM, 0.85f);
        registry.add(ModNatureBlocks.TUBESHRROM, 0.65f);
        registry.add(ModNatureBlocks.TALL_TUBESHROOM, 0.85f);
        registry.add(ModNatureBlocks.VIOLET_CAPS, 0.65f);
        registry.add(ModNatureBlocks.WHITE_MUSHROOM, 0.65f);
        registry.add(ModNatureBlocks.YELLOW_AMANITA, 0.65f);

        registry.add(ModNatureBlocks.BROWN_BOLETE_TILLER, 0.40f);
        registry.add(ModNatureBlocks.CAVE_AMANITA_TILLER, 0.40f);
        registry.add(ModNatureBlocks.DEEP_FIRECAP_TILLER, 0.40f);
        registry.add(ModNatureBlocks.GHOSTSHROOM_TILLER, 0.40f);
        registry.add(ModNatureBlocks.MORSEL_TILLER, 0.40f);
        registry.add(ModNatureBlocks.SKY_FIRECAP_TILLER, 0.40f);
        registry.add(ModNatureBlocks.VIOLET_CAPS_TILLER, 0.40f);
        registry.add(ModNatureBlocks.WHITE_MUSHROOM_TILLER, 0.40f);
        registry.add(ModNatureBlocks.YELLOW_AMANITA_TILLER, 0.40f);

        registry.add(ModNatureBlocks.BROWN_BOLETE_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.CAVE_AMANITA_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.DEEP_FIRECAP_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.SKY_FIRECAP_BLOCK, 0.85F);
        registry.add(ModNatureBlocks.YELLOW_AMANITA_BLOCK, 0.85F);

        Saplings.saplings.forEach(sapling -> {
            registry.add(sapling, 0.3F);
        });

        registry.add(ModNatureBlocks.ASPEN_SAPLING, 0.3F);
        registry.add(ModNatureBlocks.BEECH_SAPLING, 0.3F);

        LeavesSets.leaves.forEach(block -> {
            registry.add(block, 0.3F);
        });

        registry.add(ModNatureBlocks.LEBETHRON_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.BERRY_HOLLY_LEAVES, 0.4F);
        registry.add(ModNatureBlocks.DRY_LARCH_LEAVES, 0.2F);

        registry.add(ModNatureBlocks.FLOWERING_MALLORN_LEAVES, 0.4F);

        registry.add(ModNatureBlocks.FALLEN_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.FALLEN_MALLORN_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES, 0.3F);

        registry.add(ModNatureBlocks.DRY_PINE_LEAVES, 0.2F);
        registry.add(ModNatureBlocks.PINE_BRANCHES, 0.2F);

        registry.add(ModNatureBlocks.ORANGE_MAPLE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.RED_MAPLE_LEAVES, 0.3F);
        registry.add(ModNatureBlocks.YELLOW_MAPLE_LEAVES, 0.3F);

        registry.add(ModNatureBlocks.WILD_PIPEWEED, 0.5F);
        registry.add(ModNatureBlocks.WILD_FLAX, 0.5F);
        registry.add(ModNatureBlocks.WILD_WHEAT, 0.5F);
        registry.add(ModNatureBlocks.TALL_WILD_WHEAT, 0.5F);
        registry.add(ModNatureBlocks.WILD_TOMATO, 0.5F);
        registry.add(ModNatureBlocks.WILD_BELL_PEPPER, 0.5F);
        registry.add(ModNatureBlocks.WILD_CUCUMBER, 0.5F);
        registry.add(ModNatureBlocks.WILD_GARLIC, 0.5F);
        registry.add(ModNatureBlocks.WILD_ONION, 0.5F);
        registry.add(ModNatureBlocks.WILD_LETTUCE, 0.5F);
        registry.add(ModNatureBlocks.WILD_LEEK, 0.5F);
        registry.add(ModNatureBlocks.WILD_POTATO, 0.5F);
        registry.add(ModNatureBlocks.WILD_CARROT, 0.5F);
        registry.add(ModNatureBlocks.WILD_BEETROOT, 0.5F);

        registry.add(ModNatureBlocks.MIRKWOOD_HANGING_ROOTS, 0.3F);
        registry.add(ModNatureBlocks.MIRKWOOD_ROOTS, 0.3F);
        registry.add(ModNatureBlocks.SHELOBITE_LARVA_EGG, 0.8F);
        registry.add(ModNatureBlocks.HANGING_SHELOBITE_LARVA_EGG, 0.8F);

        registry.add(FoodItemsME.LEMBAS, 1.0F);
        registry.add(FoodItemsME.CRAM, 0.7F);
        registry.add(FoodItemsME.MAGGOTY_BREAD, 0.8F);
        registry.add(FoodItemsME.TOUGH_BERRIES, 0.3F);
        registry.add(FoodItemsME.STRAWBERRIES, 0.5F);
        registry.add(FoodItemsME.TOMATO, 0.5F);
        registry.add(FoodItemsME.BELL_PEPPER, 0.5F);
        registry.add(FoodItemsME.CUCUMBER, 0.5F);
        registry.add(FoodItemsME.GARLIC, 0.5F);
        registry.add(FoodItemsME.LEEK, 0.5F);
        registry.add(FoodItemsME.LETTUCE, 0.5F);
        registry.add(FoodItemsME.ONION, 0.5F);

        registry.add(FoodItemsME.LAYERED_CAKE, 1.0F);
        registry.add(FoodItemsME.BERRY_PIE, 1.0F);
        registry.add(FoodItemsME.VEGETABLE_SKEWER, 1.0F);
        registry.add(FoodItemsME.VEGETABLE_SOUP, 1.0F);
        registry.add(FoodItemsME.SACK_OF_HORSEFEED, 1.0F);

        registry.add(ResourceItemsME.STRAW, 0.3F);
        registry.add(ResourceItemsME.REEDS, 0.3F);

        registry.add(ResourceItemsME.FLAX, 0.3F);
        registry.add(ResourceItemsME.PIPEWEED, 0.3F);
        registry.add(ResourceItemsME.DRIED_PIPEWEED, 0.3F);
        registry.add(ResourceItemsME.PINECONE, 0.3F);

        registry.add(ResourceItemsME.BELL_PEPPER_SEEDS, 0.3F);
        registry.add(ResourceItemsME.CUCUMBER_SEEDS, 0.3F);
        registry.add(ResourceItemsME.FLAX_SEEDS, 0.3F);
        registry.add(ResourceItemsME.LETTUCE_SEEDS, 0.3F);
        registry.add(ResourceItemsME.TOMATO_SEEDS, 0.3F);
        registry.add(ResourceItemsME.PIPEWEED_SEEDS, 0.3F);
    }

    public static void registerLandPathNodeTypesBlocks() {
        LandPathTypeRegistry.register(ModNatureBlocks.TOUGH_BERRY_BUSH, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModNatureBlocks.NETTLES, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModNatureBlocks.THISTLE, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModNatureBlocks.BRAMBLES_OF_MORDOR, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.SMALL_BRAZIER, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.BIG_BRAZIER, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.GILDED_SMALL_BRAZIER, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.GILDED_BIG_BRAZIER, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.BONFIRE, PathType.DAMAGING, PathType.DAMAGING);
        LandPathTypeRegistry.register(ModDecorativeBlocks.FIRE_BOWL, PathType.DAMAGING, PathType.DAMAGING);
    }

    public static final CauldronInteraction CLEAN_EQUIPMENT = (state, world, pos, player, hand, stack) -> {
        // 26.2 dropped the shared "dyeable" tag this used to gate on; a DYED_COLOR component
        // is what marks an item (modded or vanilla) as dyed, and dyed items are what's washable.
        if (!stack.has(DataComponents.DYED_COLOR)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        } else {
            if (!world.isClientSide()) {
                stack.remove(DataComponents.DYED_COLOR);
                player.awardStat(Stats.CLEAN_ARMOR);
                LayeredCauldronBlock.lowerFillLevel(state, world, pos);
            }

            return InteractionResult.SUCCESS;
        }
    };

    //This not good but will do for now until more cases appear
    public static final CauldronInteraction CLEAN_ITEM = (state, world, pos, player, hand, stack) -> {
        if (!world.isClientSide()) {
            player.addItem(new ItemStack(Items.BONE));
            stack.shrink(1);
        }

        //TODO Make sure this works well on server/client, ActionResult.SERVER_SUCCESS if not
        return InteractionResult.SUCCESS;
    };

    public static final CauldronInteraction COOL_DOWN_METAL = (state, world, pos, player, hand, stack) -> {
        RandomSource random = world.getRandom();
        int smokeAmount = random.nextInt(9) + 4;
        int bigSmokeAmount = random.nextInt(3) + 2;

        if (!stack.has(DataComponentTypesME.TEMPERATURE_DATA)) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }
        if (!world.isClientSide()) {
            ItemStack originalStack = stack.copy();
            originalStack.setCount(1);
            originalStack.remove(DataComponentTypesME.TEMPERATURE_DATA);
            stack.shrink(1);
            player.getInventory().placeItemBackInInventory(originalStack);

            LayeredCauldronBlock.lowerFillLevel(state, world, pos);

            world.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            for (int i = 0; i < bigSmokeAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.9f,
                        pos.getZ()+ random.nextDouble(),
                        0.0f,
                        0.03f + random.nextDouble() * 0.08,
                        0.0f);
            }
            for (int i = 0; i < smokeAmount; i++) {
                world.addParticle(ParticleTypes.SMOKE,
                        pos.getX() + random.nextDouble(),
                        pos.getY() + 0.8f,
                        pos.getZ() + random.nextDouble(),
                        0.0f,
                        0.00f + random.nextDouble() * 0.08,
                        0.0f);
            }
        }
        return InteractionResult.SUCCESS;
    };

    public static void registerCauldronBehaviour() {
        // The per-fluid Dispatcher instances are the 26.2 replacement for the old
        // CauldronInteraction.WATER.map(); Dispatcher.put is opened up in the access widener.
        HotMetalsModel.items.forEach(item -> {
            CauldronInteractions.WATER.put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.ingots.forEach(item -> {
            CauldronInteractions.WATER.put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggets.forEach(item -> {
            CauldronInteractions.WATER.put(item, COOL_DOWN_METAL);
        });

        HotMetalsModel.nuggies.forEach(item -> {
            CauldronInteractions.WATER.put(item, COOL_DOWN_METAL);
        });

        SimpleDyeableItemModel.items.forEach(item -> {
            CauldronInteractions.WATER.put(item, CLEAN_EQUIPMENT);
        });

        CauldronInteractions.WATER.put(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteractions.WATER.put(EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteractions.WATER.put(EquipmentItemsME.WARG_LEATHER_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteractions.WATER.put(EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteractions.WATER.put(EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteractions.WATER.put(EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR, CLEAN_EQUIPMENT);
        CauldronInteractions.WATER.put(EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR, CLEAN_EQUIPMENT);

        CauldronInteractions.WATER.put(ResourceItemsME.DIRTY_BONE, CLEAN_ITEM);
    }
}
