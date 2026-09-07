package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.SimpleBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.custom.AlloyRecipeJsonBuilder;
import net.sevenstars.middleearth.datageneration.custom.AnvilShapingRecipeJsonBuilder;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.recipe.*;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipeProvider extends FabricRecipeProvider {

    private static final int INGOT_LIQUID_VALUE = 144;

    public RecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
        return new net.minecraft.data.recipes.RecipeProvider(wrapperLookup, recipeExporter) {

            final HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
            private final RecipeOutput exporter = output;

            @Override
            public void buildRecipes() {
                net.sevenstars.middleearth.datageneration.DatagenComponentBinder.bindItemComponents();
                generate();
            }

            public void generate() {
                //region STONE RECIPES
                for (StoneBlockSetBuilder record : StoneBlockSets.stoneSetsList) {
                    if(record.hasMossy) {
                        createStoneSetRecipes(record.mossyCobblestoneBlocks);
                        createStoneSetRecipes(record.mossyBrickBlocks);
                        createStoneSetRecipes(record.mossyPolishedBlocks);
                        createStoneSetRecipes(record.mossyPillarBlocks);
                        createStoneSetRecipes(record.mossyTileBlocks);
                        createStoneSetRecipes(record.mossySmoothBlocks);
                        if(record.mossyCobblestoneBlocks != null && record.cobblestoneBlocks != null) {
                            createMossyRecipe(exporter, record.cobblestoneBlocks.base(), record.mossyCobblestoneBlocks.base());
                        }
                        if(record.mossyBrickBlocks != null && record.brickBlocks != null) {
                            createMossyRecipe(exporter, record.brickBlocks.base(), record.mossyBrickBlocks.base());
                        }
                        if(record.mossyPillarBlocks != null && record.pillarBlocks != null) {
                            createMossyRecipe(exporter, record.pillarBlocks.base(), record.mossyPillarBlocks.base());
                        }
                        if(record.mossyPolishedBlocks != null && record.polishedBlocks != null) {
                            createMossyRecipe(exporter, record.polishedBlocks.base(), record.mossyPolishedBlocks.base());
                        }
                        if(record.mossyTileBlocks != null && record.tileBlocks != null) {
                            createMossyRecipe(exporter, record.tileBlocks.base(), record.mossyTileBlocks.base());
                        }
                        if(record.mossySmoothBlocks != null && record.smoothBlocks != null) {
                            createMossyRecipe(exporter, record.smoothBlocks.base(), record.mossySmoothBlocks.base());
                        }
                    }
                    if(record.hasCracked) {
                        if(record.crackedBrickBlocks != null && record.brickBlocks != null) {
                            createStoneSetRecipes(record.crackedBrickBlocks);
                            offerSmelting(List.of(record.brickBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedBrickBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPillarBlocks != null && record.pillarBlocks != null) {
                            createStoneSetRecipes(record.crackedPillarBlocks);
                            offerSmelting(List.of(record.pillarBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPillarBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedPolishedBlocks != null && record.polishedBlocks != null) {
                            createStoneSetRecipes(record.crackedPolishedBlocks);
                            offerSmelting(List.of(record.polishedBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedPolishedBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedTileBlocks != null && record.tileBlocks != null) {
                            createStoneSetRecipes(record.crackedTileBlocks);
                            offerSmelting(List.of(record.tileBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedTileBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                        if(record.crackedSmoothBlocks != null && record.smoothBlocks != null) {
                            createStoneSetRecipes(record.crackedSmoothBlocks);
                            offerSmelting(List.of(record.smoothBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.crackedSmoothBlocks.base(), 0.1f, 200, "cracked_bricks");
                        }
                    }

                    if(record.cobblestoneBlocks != null && record.baseBlocks != null) {
                        if(!record.hasVanillaCobble) {
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.cobblestoneBlocks.base(), record.baseBlocks.base(), 1);
                            offerSmelting(List.of(record.cobblestoneBlocks.base()), RecipeCategory.BUILDING_BLOCKS,
                                    record.baseBlocks.base(), 0.1f, 200, "blocks");
                        }

                        if(record.brickworkBlocks != null) {
                            createBrickworkBlockRecipe(exporter, record.cobblestoneBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), record.brickworkBlocks.base());
                        }
                    }

                    if(record.smoothBlocks != null && record.tileBlocks != null) {
                        createBrickRecipe(exporter, record.smoothBlocks.base().asItem(), record.tileBlocks.base(), 4);
                    }

                    if (record.baseBlocks != null) {
                        if(record.brickBlocks != null) {
                            createBrickRecipe(exporter, record.baseBlocks.base().asItem(), record.brickBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.brickBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.pillarBlocks != null) {
                            createPillarRecipe(exporter, record.baseBlocks.base(), record.pillarBlocks.base(), 3);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.pillarBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.polishedBlocks != null && !record.hasVanillaPolished) {
                            createBrickRecipe(exporter, record.baseBlocks.base().asItem(), record.polishedBlocks.base(), 4);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.polishedBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.smoothBlocks != null) {
                            createSmeltingRecipe(exporter, record.baseBlocks.base().asItem(), record.smoothBlocks.base().asItem());
                        }
                        if(record.chiseledBlocks != null) {
                            createChiseledRecipe(exporter, record.baseBlocks.base(), record.chiseledBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledBlocks.base(), record.baseBlocks.base(), 1);
                        }
                        if(record.chiseledBricksBlocks != null && record.brickBlocks != null) {
                            createChiseledRecipe(exporter, record.brickBlocks.base(), record.chiseledBricksBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledBricksBlocks.base(), record.brickBlocks.base(), 1);
                        }
                        if(record.chiseledPolishedBlocks != null && record.polishedBlocks != null) {
                            createChiseledRecipe(exporter, record.polishedBlocks.base(), record.chiseledPolishedBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledPolishedBlocks.base(), record.polishedBlocks.base(), 1);
                        }
                        if(record.chiseledSmoothBlocks != null && record.smoothBlocks != null) {
                            createChiseledRecipe(exporter, record.smoothBlocks.base(), record.chiseledSmoothBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledSmoothBlocks.base(), record.smoothBlocks.base(), 1);
                        }
                        if(record.chiseledTilesBlocks != null && record.tileBlocks != null) {
                            createChiseledRecipe(exporter, record.tileBlocks.base(), record.chiseledTilesBlocks.base(), 2);
                            offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.chiseledTilesBlocks.base(), record.tileBlocks.base(), 1);
                        }
                        if(record.oldBlocks != null) {
                            createCenterSurroundRecipe(exporter, record.baseBlocks.base().asItem(), ResourceItemsME.ASH, record.oldBlocks.base().asItem(), 8);
                        }

                        createFilledRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.trapdoor(), 3);
                        createPressurePlateRecipe(exporter, record.baseBlocks.base(), record.baseBlocks.pressurePlate());
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.trapdoor(), record.baseBlocks.base());
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, record.baseBlocks.rocks(), record.baseBlocks.base(), 4);
                        createButtonRecipe(exporter, record.baseBlocks.base(), record.baseBlocks.button());

                        createStoneStoolRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.stool());
                        createStoneTableRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.table());
                        createStoneChairRecipe(exporter, record.baseBlocks.base().asItem(), record.baseBlocks.chair());
                    }

                    if(!record.isVanilla) createStoneSetRecipes(record.baseBlocks);
                    if(!record.hasVanillaCobble) createStoneSetRecipes(record.cobblestoneBlocks);
                    createStoneSetRecipes(record.brickBlocks);
                    createStoneSetRecipes(record.tileBlocks);
                    createStoneSetRecipes(record.smoothBlocks);
                    if(!record.hasVanillaPolished) createStoneSetRecipes(record.polishedBlocks);
                    createStoneSetRecipes(record.chiseledBlocks);
                    createStoneSetRecipes(record.chiseledBricksBlocks);
                    createStoneSetRecipes(record.chiseledTilesBlocks);
                    createStoneSetRecipes(record.chiseledPolishedBlocks);
                    createStoneSetRecipes(record.chiseledSmoothBlocks);
                    createStoneSetRecipes(record.brickworkBlocks);
                    createStoneSetRecipes(record.pillarBlocks);
                    createStoneSetRecipes(record.oldBlocks);

                    if(record.carvedWindows != null && record.baseBlocks != null) {
                        ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.carvedWindows.block(), 4)
                                .pattern("EEE")
                                .pattern("EGE")
                                .pattern("EEE")
                                .define('E', record.baseBlocks.base())
                                .define('G', Items.GLASS)
                                .unlockedBy(hasItem(record.baseBlocks.base()),
                                        conditionsFromItem(record.baseBlocks.base()))
                                .save(exporter);
                        createPaneRecipe(exporter, record.carvedWindows.block().asItem(), record.carvedWindows.verticalSlab(), 12);
                    }
                }
                //endregion

                //region WOOD RECIPES
                for (WoodBlockSetBuilder record : WoodBlockSets.woodSetsList) {
                    if(record.logBlocks != null) {
                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.wall(), record.logBlocks.wood());
                        createFenceRecipe(exporter, record.logBlocks.wood().asItem(), record.logBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.logBlocks.slab(), record.logBlocks.wood());
                        createStairsRecipe(exporter, record.logBlocks.wood(), record.logBlocks.stairs());

                        createSlabsFromVerticalRecipe(exporter, record.logBlocks.verticalSlab(), record.logBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.logBlocks.slab(), record.logBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(exporter, record.planksBlocks.verticalSlab(), record.planksBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.planksBlocks.slab(), record.planksBlocks.verticalSlab());

                        if(!record.vanilla) {
                            createBrickRecipe(exporter, record.logBlocks.log().asItem(), record.logBlocks.wood(), 3);
                            ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                    .requires(record.logBlocks.log())
                                    .unlockedBy(hasItem(record.logBlocks.log()),
                                            conditionsFromItem(record.planksBlocks.base()))
                                    .save(exporter);

                            ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                    .requires(record.logBlocks.wood())
                                    .unlockedBy(hasItem(record.logBlocks.wood()),
                                            conditionsFromItem(record.planksBlocks.base()))
                                    .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_wood")));
                        }

                    } else if(record.mushroomStemBlocks != null) {
                        ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.mushroomStemBlocks.stem())
                                .unlockedBy(hasItem(record.mushroomStemBlocks.stem()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .save(exporter);

                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.wall(), record.mushroomStemBlocks.stem());
                        createFenceRecipe(exporter, record.mushroomStemBlocks.stem().asItem(), record.mushroomStemBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.stem());
                        createVerticalSlabsRecipe(exporter, record.mushroomStemBlocks.slab(), record.mushroomStemBlocks.verticalSlab());
                        createSlabsFromVerticalRecipe(exporter, record.mushroomStemBlocks.verticalSlab(), record.mushroomStemBlocks.slab());
                        createStairsRecipe(exporter, record.mushroomStemBlocks.stem(), record.mushroomStemBlocks.stairs());
                    }

                    if(record.strippedLogBlocks != null) {
                        createBrickRecipe(exporter, record.strippedLogBlocks.log().asItem(), record.strippedLogBlocks.wood(),  3);
                        offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.wall(), record.strippedLogBlocks.wood());
                        createFenceRecipe(exporter, record.strippedLogBlocks.wood().asItem(), record.strippedLogBlocks.fence());
                        offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.strippedLogBlocks.slab(), record.strippedLogBlocks.wood());
                        createStairsRecipe(exporter, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());

                        createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        createVerticalSlabsRecipe(exporter, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        // if(!record.vanilla)
                        //createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        //createVerticalSlabsRecipe(exporter, record.strippedLogBlocks.slab(), record.strippedLogBlocks.verticalSlab());
                        //createSlabsFromVerticalRecipe(exporter, record.strippedLogBlocks.verticalSlab(), record.strippedLogBlocks.slab());
                        //createStairsRecipe(exporter, record.strippedLogBlocks.wood(), record.strippedLogBlocks.stairs());

                        ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.strippedLogBlocks.log())
                                .unlockedBy(hasItem(record.strippedLogBlocks.log()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_stripped_log")));

                        ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.base(), 4)
                                .requires(record.strippedLogBlocks.wood())
                                .unlockedBy(hasItem(record.strippedLogBlocks.wood()),
                                        conditionsFromItem(record.planksBlocks.base()))
                                .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(record.planksBlocks.base()).getPath() + "_from_stripped_wood")));
                    }

                    createFenceRecipe(exporter, record.planksBlocks.base().asItem(), record.planksBlocks.fence());
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.slab(), record.planksBlocks.base());

                    if(record.shinglesBlocks != null) {
                        createShinglesRecipe(exporter, record.planksBlocks.base(), record.shinglesBlocks.base());
                        createRegularSetRecipes(record.shinglesBlocks);
                    }
                    if(record.roofingBlocks != null) {
                        createRoofingRecipe(exporter, record.planksBlocks.slab(), record.roofingBlocks.base());
                        createRegularSetRecipes(record.roofingBlocks);
                    }

                    createStairsRecipe(exporter, record.planksBlocks.base(), record.planksBlocks.stairs());

                    if(record.redstoneBlocks != null) {
                        createDoorRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.door());
                        createTrapdoorRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.trapdoor());
                        createButtonRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.button());
                        createPressurePlateRecipe(exporter, record.planksBlocks.base(), record.redstoneBlocks.pressurePlate());
                    }

                    if(record.furnitureBlocks != null) {
                        createWoodStoolRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.stool());
                        createWoodBenchRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.bench());
                        createWoodTableRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.table());
                        createWoodChairRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.chair());
                        createWoodLadderRecipe(exporter, record.planksBlocks.base().asItem(), record.furnitureBlocks.ladder());
                    }

                    ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, record.planksBlocks.gate(), 1)
                            .pattern("sls")
                            .pattern("sls")
                            .define('l', record.planksBlocks.base())
                            .define('s', Items.STICK)
                            .unlockedBy(hasItem(record.planksBlocks.base()),
                                    conditionsFromItem(record.planksBlocks.base()))
                            .unlockedBy(hasItem(Items.STICK),
                                    conditionsFromItem(Items.STICK))
                            .save(exporter);

                }
                //endregion

                for(GenericBlockSetBuilder set : GenericBlockSets.genericSetsList) {
                    if(!set.setName.contains("wood") && !set.setName.contains("thatch") && !set.setName.contains("reed")) {
                        createStoneSetRecipes(set.blockSet);
                    } else if (set.setName.contains("thatch") || set.setName.contains("reed")) {
                        createRegularSetRecipes(set.blockSet);
                    }
                }
                for(SimpleBlockSetBuilder set : GenericBlockSets.simpleSetsList) {
                    createGenericRecipes(set);
                }

                //region BLOCK LIST SPECIFIC RECIPES
                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
                    createVerticalSlabsRecipe(exporter, verticalSlab.slab(), verticalSlab.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, verticalSlab.verticalSlab(), verticalSlab.slab());
                }

                for (SimplePillarModel.StonePillar pillar : SimplePillarModel.stonePillars) {
                    if (pillar.toString().contains("mossy_")) {
                        createMossyRecipe(exporter, pillar.origin(), pillar.base());
                    } else if (pillar.toString().contains("cracked_")) {
                        createSmeltingRecipe(exporter, pillar.origin().asItem(), pillar.base().asItem());
                    } else {
                        createPillarRecipe(exporter, pillar.origin(), pillar.base(), 3);
                        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, pillar.base().asItem(), pillar.origin());
                    }
                }

                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
                    createChiseledRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }
                for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
                    createCutPolishedRecipe(exporter, block.origin(), block.base(), 1);
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaSlabs) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaWoodSlabs) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaStrippedSlab) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab.slab(), slab.origin());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaWoodStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStrippedStairs) {
                    createStairsRecipe(exporter, stair.origin(), stair.stairs());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaStrippedWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWoodWalls) {
                    offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, wall.wall(), wall.block());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaStrippedFences) {
                    createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
                }

                for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaWoodFences) {
                    createFenceRecipe(exporter, fence.block().asItem(), fence.fence());
                }

                for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
                    createPaneRecipe(exporter, pane.glass().asItem(), pane.pane(), 16);
                }

                for (SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
                    createWoodStoolRecipe(exporter, stool.planks().asItem(), stool.base());
                }

                for (SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs) {
                    createWoodBenchRecipe(exporter, bench.planks().asItem(), bench.base());
                }

                for (SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
                    createWoodTableRecipe(exporter, table.planks().asItem(), table.base());
                }

                for (SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
                    createWoodChairRecipe(exporter, chair.planks().asItem(), chair.base());
                }

                for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
                    createWoodLadderRecipe(exporter, ladder.block().asItem(), ladder.ladder());
                }

                for (SimpleRocksModel.Rocks rock : SimpleRocksModel.vanillaRocks) {
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, rock.rocks(), rock.block(), 4);
                }

                //endregion

                //region MANUAL BLOCK RECIPES
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.black(), ModDecorativeBlocks.BLACK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.blue(), ModDecorativeBlocks.BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.brown(), ModDecorativeBlocks.BROWN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.cyan(), ModDecorativeBlocks.CYAN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.gray(), ModDecorativeBlocks.GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.green(), ModDecorativeBlocks.GREEN_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.lightBlue(), ModDecorativeBlocks.LIGHT_BLUE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.lightGray(), ModDecorativeBlocks.LIGHT_GRAY_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.lime(), ModDecorativeBlocks.LIME_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.magenta(), ModDecorativeBlocks.MAGENTA_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.orange(), ModDecorativeBlocks.ORANGE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.pink(), ModDecorativeBlocks.PINK_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.purple(), ModDecorativeBlocks.PURPLE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.red(), ModDecorativeBlocks.RED_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.white(), ModDecorativeBlocks.WHITE_STAINED_LEAD_GLASS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.LEAD_GLASS.asItem(), Items.DYE.yellow(), ModDecorativeBlocks.YELLOW_STAINED_LEAD_GLASS.asItem(), 8);

                createLayerRecipe(exporter, Blocks.GRAVEL.asItem(), ModBlocks.GRAVEL_LAYER);
                createLayerRecipe(exporter, Blocks.SAND.asItem(), ModBlocks.SAND_LAYER);
                createLayerRecipe(exporter, ModBlocks.BLACK_SAND.asItem(), ModBlocks.BLACK_SAND_LAYER);
                createLayerRecipe(exporter, ModBlocks.WHITE_SAND.asItem(), ModBlocks.WHITE_SAND_LAYER);
                createLayerRecipe(exporter, ModBlocks.ASHEN_SAND.asItem(), ModBlocks.ASHEN_SAND_LAYER);
                createLayerRecipe(exporter, ModBlocks.ASHEN_GRAVEL.asItem(), ModBlocks.ASHEN_GRAVEL_LAYER);
                createLayerRecipe(exporter, ModBlocks.SKELETAL_PILE.asItem(), ModBlocks.SKELETAL_PILE_LAYER);
                createLayerRecipe(exporter, ModBlocks.WASTE_PILE.asItem(), ModBlocks.WASTE_PILE_LAYER);


                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TRAVERTINE_SET.baseBlocks.base(), 4)
                        .pattern("CS")
                        .pattern("SC")
                        .define('C', Blocks.CALCITE)
                        .define('S', Blocks.SANDSTONE)
                        .unlockedBy(hasItem(Blocks.CALCITE),
                                conditionsFromItem(Blocks.CALCITE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.REED_THATCH.blockSet.base(), 1)
                        .pattern("RR")
                        .pattern("RR")
                        .define('R', ResourceItemsME.REEDS)
                        .unlockedBy(hasItem(ResourceItemsME.REEDS),
                                conditionsFromItem(ResourceItemsME.REEDS))
                        .save(exporter);

                createStairsRecipe(exporter, ModBlocks.GRASSY_DIRT, ModBlocks.GRASSY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT_SLAB, ModBlocks.GRASSY_DIRT);

                createStairsRecipe(exporter, ModBlocks.PEBBLED_GRASS, ModBlocks.PEBBLED_GRASS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS_SLAB, ModBlocks.PEBBLED_GRASS);

                createStairsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF_SLAB, ModBlocks.TURF);
                createVerticalSlabsRecipe(exporter, ModBlocks.TURF, ModBlocks.TURF_VERTICAL_SLAB);
                createSlabsFromVerticalRecipe(exporter, ModBlocks.TURF_VERTICAL_SLAB, ModBlocks.TURF_SLAB);

                createStairsRecipe(exporter, ModBlocks.MIRE, ModBlocks.MIRE_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MIRE_SLAB, ModBlocks.MIRE);

                createStairsRecipe(exporter, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHALKSOIL_SLAB, ModBlocks.CHALKSOIL);
                createStairsRecipe(exporter, ModBlocks.GRASSY_CHALKSOIL, ModBlocks.GRASSY_CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_CHALKSOIL_SLAB, ModBlocks.GRASSY_CHALKSOIL);
                createStairsRecipe(exporter, ModBlocks.COARSE_CHALKSOIL, ModBlocks.COARSE_CHALKSOIL_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_CHALKSOIL_SLAB, ModBlocks.COARSE_CHALKSOIL);

                createStairsRecipe(exporter, ModBlocks.LOAM, ModBlocks.LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LOAM_SLAB, ModBlocks.LOAM);
                createStairsRecipe(exporter, ModBlocks.GRASSY_LOAM, ModBlocks.GRASSY_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM_SLAB, ModBlocks.GRASSY_LOAM);
                createStairsRecipe(exporter, ModBlocks.COARSE_LOAM, ModBlocks.COARSE_LOAM_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_LOAM_SLAB, ModBlocks.COARSE_LOAM);

                createStairsRecipe(exporter, ModBlocks.PEAT, ModBlocks.PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEAT_SLAB, ModBlocks.PEAT);
                createStairsRecipe(exporter, ModBlocks.GRASSY_PEAT, ModBlocks.GRASSY_PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_PEAT_SLAB, ModBlocks.GRASSY_PEAT);
                createStairsRecipe(exporter, ModBlocks.COARSE_PEAT, ModBlocks.COARSE_PEAT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_PEAT_SLAB, ModBlocks.COARSE_PEAT);

                createStairsRecipe(exporter, ModBlocks.SILT, ModBlocks.SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILT_SLAB, ModBlocks.SILT);
                createStairsRecipe(exporter, ModBlocks.GRASSY_SILT, ModBlocks.GRASSY_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT_SLAB, ModBlocks.GRASSY_SILT);
                createStairsRecipe(exporter, ModBlocks.COARSE_SILT, ModBlocks.COARSE_SILT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COARSE_SILT_SLAB, ModBlocks.COARSE_SILT);

                createStairsRecipe(exporter, ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRY_DIRT_SLAB, ModBlocks.DRY_DIRT);

                createStairsRecipe(exporter, ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FOUL_DIRT_SLAB, ModBlocks.FOUL_DIRT);

                createStairsRecipe(exporter, ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS_SLAB, ModBlocks.DIRTY_ROOTS);

                createStairsRecipe(exporter, ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ASHEN_DIRT_SLAB, ModBlocks.ASHEN_DIRT);

                createStairsRecipe(exporter, ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT_SLAB, ModBlocks.COBBLY_ASHEN_DIRT);

                createStairsRecipe(exporter, ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT_SLAB, ModBlocks.COBBLY_DIRT);

                createStairsRecipe(exporter, ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_STAIRS);
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT_SLAB, ModBlocks.SNOWY_DIRT);

                createPaneRecipe(exporter, Blocks.WOOL.white().asItem(), ModBlocks.NET, 16);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COPPER_BARS, 16)
                        .pattern("IBI")
                        .pattern("IBI")
                        .define('I', Items.COPPER_INGOT)
                        .define('B', Items.CUT_COPPER.weathering().unaffected())
                        .unlockedBy(hasItem(Items.CUT_COPPER.weathering().unaffected()),
                                conditionsFromItem(Items.CUT_COPPER.weathering().unaffected()))
                        .save(exporter);

                createBrickRecipe(exporter, ResourceItemsME.CITRINE_SHARD, ModBlocks.CITRINE_BLOCK, 1);
                createFilledRecipe(exporter, Items.GLOWSTONE, ModBlocks.GLOWSTONE_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.QUARTZ_SHARD, ModBlocks.QUARTZ_BLOCK, 1);
                createBrickRecipe(exporter, ResourceItemsME.RED_AGATE_SHARD, ModBlocks.RED_AGATE_BLOCK, 1);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.BRICKS, GenericBlockSets.OLD_BRICKS.blockSet.base());

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.WHITE_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(GenericBlockSets.WHITE_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.WHITE_DAUB.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.YELLOW_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_HOBBIT_WINDOW, 4)
                        .pattern("WBW")
                        .pattern("BGB")
                        .pattern("WBW")
                        .define('W', GenericBlockSets.PLASTER.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(GenericBlockSets.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SIMPLE_OAK_WINDOW, 8)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .define('E', Blocks.OAK_LOG)
                        .define('G', ResourceItemsME.LEAD_NUGGET)
                        .unlockedBy(hasItem(Blocks.OAK_LOG),
                                conditionsFromItem(Blocks.OAK_LOG))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.DRYSTONE_SET.carvedWindows.block(), 2)
                        .pattern("EEE")
                        .pattern("EGE")
                        .pattern("EEE")
                        .define('E', StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base())
                        .define('G', Items.GLASS)
                        .unlockedBy(hasItem(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()),
                                conditionsFromItem(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .save(exporter);
                createPaneRecipe(exporter, StoneBlockSets.DRYSTONE_SET.carvedWindows.block().asItem(), StoneBlockSets.DRYSTONE_SET.carvedWindows.verticalSlab(), 12);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LEAD_GLASS, 4)
                        .pattern("LGL")
                        .pattern("GLG")
                        .pattern("LGL")
                        .define('L', ResourceItemsME.LEAD_NUGGET)
                        .define('G', Items.GLASS)
                        .unlockedBy(hasItem(ResourceItemsME.ROD),
                                conditionsFromItem(ResourceItemsME.ROD))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', Items.STRING)
                        .unlockedBy(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .save(exporter);

                createBrickRecipe(exporter, ResourceItemsME.ASH, ModBlocks.ASH_BLOCK, 1);
                createBrickRecipe(exporter, ModBlocks.ASH_BLOCK.asItem(), Blocks.TUFF, 1, "tuff_from_ash");

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.ASHENSTONE_SET.baseBlocks.base(), 4)
                        .pattern("AS")
                        .pattern("SA")
                        .define('A', ModBlocks.ASH_BLOCK)
                        .define('S', Blocks.STONE)
                        .unlockedBy(hasItem(ModBlocks.ASH_BLOCK),
                                conditionsFromItem(ModBlocks.ASH_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledBricksBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.chiseledBricksBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GREEN_TUFF_SET.chiseledBricksBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF_SET.chiseledBricksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledTilesBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.chiseledTilesBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GREEN_TUFF_SET.chiseledTilesBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF_SET.chiseledTilesBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledSmoothBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.chiseledSmoothBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GREEN_TUFF_SET.chiseledSmoothBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF_SET.chiseledSmoothBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.GILDED_GREEN_TUFF_SET.chiseledPolishedBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GREEN_TUFF_SET.chiseledPolishedBlocks.base())
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GREEN_TUFF_SET.chiseledPolishedBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GREEN_TUFF_SET.chiseledPolishedBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.baseBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GABBRO_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GABBRO_SET.baseBlocks.base()))
                        .save(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks);

                createBrickRecipe(exporter, StoneBlockSets.BURZUM_GABBRO_SET.chiseledBlocks.base().asItem(), StoneBlockSets.BURZUM_GABBRO_SET.chiseledBricksBlocks.base(), 4);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledBricksBlocks);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledSmoothBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.smoothBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GABBRO_SET.smoothBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GABBRO_SET.smoothBlocks.base()))
                        .save(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledSmoothBlocks);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledPolishedBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.polishedBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GABBRO_SET.polishedBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GABBRO_SET.polishedBlocks.base()))
                        .save(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledPolishedBlocks);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BURZUM_GABBRO_SET.chiseledTilesBlocks.base(), 5)
                        .pattern("TNT")
                        .pattern("NTN")
                        .pattern("TNT")
                        .define('T', StoneBlockSets.GABBRO_SET.tileBlocks.base())
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(hasItem(StoneBlockSets.GABBRO_SET.tileBlocks.base()),
                                conditionsFromItem(StoneBlockSets.GABBRO_SET.tileBlocks.base()))
                        .save(exporter);
                //createStoneSetRecipes(StoneBlockSets.BURZUM_GABBRO_SET.chiseledTilesBlocks);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WATTLE_TRAPDOOR, 2)
                        .pattern("PLP")
                        .pattern("PLP")
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .define('L', ResourceItemsME.LEAD_NUGGET)
                        .unlockedBy(hasItem(ResourceItemsME.LEAD_NUGGET),
                                conditionsFromItem(ResourceItemsME.LEAD_NUGGET))
                        .save(exporter);

                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.DYE.red(), ModBlocks.RED_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.DYE.green(), ModBlocks.GREEN_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.DYE.brown(), ModBlocks.DARK_WATTLE_TRAPDOOR);
                createDyeableItemRecipe(exporter, ModBlocks.WATTLE_TRAPDOOR, Items.DYE.black(), ModBlocks.BLACK_WATTLE_TRAPDOOR);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.STONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.STONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.CALCITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.CALCITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, Blocks.DEEPSLATE_TILES, GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DEEPSLATE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BASALT_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BASALT_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.ANDESITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(),StoneBlockSets.ANDESITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DIORITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DIORITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GRANITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GRANITE_SET.brickworkBlocks.base());

                createBrickRecipe(exporter, StoneBlockSets.QUARTZITE_SET.brickBlocks.base().asItem(), StoneBlockSets.QUARTZITE_SET.tileBlocks.base(), 4);
                createBrickRecipe(exporter, GenericBlockSets.PACKED_MIRE.blockSet.base().asItem(), GenericBlockSets.MIRE_BRICKS.blockSet.base(), 4);

                createMossyRecipe(exporter, GenericBlockSets.MIXED_STONES.blockSet.base(), GenericBlockSets.MOSSY_MIXED_STONES.blockSet.base());
                offerSmelting(List.of(GenericBlockSets.MIXED_STONES.blockSet.base()), RecipeCategory.BUILDING_BLOCKS,
                        GenericBlockSets.CRACKED_MIXED_STONES.blockSet.base(), 0.1f, 200, "cracked_bricks");

                createBrickRecipe(exporter, Blocks.BRICKS.asItem(), GenericBlockSets.CLAY_TILING.blockSet.base(), 4);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.BLACK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.blue(), GenericBlockSets.BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.brown(), GenericBlockSets.BROWN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.cyan(), GenericBlockSets.CYAN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.green(), GenericBlockSets.GREEN_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.lightBlue(), GenericBlockSets.LIGHT_BLUE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.LIGHT_GRAY_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.lime(), GenericBlockSets.LIME_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.magenta(), GenericBlockSets.MAGENTA_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.orange(), GenericBlockSets.ORANGE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.pink(), GenericBlockSets.PINK_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.purple(), GenericBlockSets.PURPLE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.red(), GenericBlockSets.RED_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.WHITE_CLAY_TILING.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CLAY_TILING.blockSet.base().asItem(), Items.DYE.yellow(), GenericBlockSets.YELLOW_CLAY_TILING.blockSet.base().asItem(), 8);

                //createBrickworkBlockRecipe(exporter, StoneBlockSets.DOLOMITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.DOLOMITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.HEMATITE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.HEMATITE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GNEISS_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GNEISS_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.IZHERABAN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.IZHERABAN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.LIMESTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.LIMESTONE_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GALONN_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GALONN_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.GABBRO_SET.brickBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.GABBRO_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.TUFF_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.TUFF_SET.brickworkBlocks.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.BLACKSTONE_SET.tileBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.BLACKSTONE_SET.brickworkBlocks.base());
                createBrickworkBlockRecipe(exporter, StoneBlockSets.TAN_CLAY.brickBlocks.base(), GenericBlockSets.PLASTER.blockSet.base(), StoneBlockSets.TAN_CLAY.brickworkBlocks.base());
                createBrickworkBlockRecipe(exporter, GenericBlockSets.MIXED_STONES.blockSet.base(), GenericBlockSets.STUCCO.blockSet.base(), GenericBlockSets.MIXED_STONES_BRICKWORK.blockSet.base());
                //createBrickworkBlockRecipe(exporter, StoneBlockSets.MEDGON_SET.baseBlocks.base(), GenericBlockSets.STUCCO.blockSet.base(), StoneBlockSets.MEDGON_SET.brickworkBlocks.base());

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.blue(), GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.BRIGHT_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BLUE_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_BLUE_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.brown(), GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BROWN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_BROWN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.cyan(), GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.BRIGHT_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CYAN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_CYAN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.gray(), GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GRAY_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_GRAY_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.green(), GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.BRIGHT_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GREEN_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_GREEN_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.red(), GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.BRIGHT_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_RED_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.RED_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_RED_ROOF_TILES.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, Items.BRICK, Items.DYE.yellow(), GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), 2);
                createCenterSurroundRecipe(exporter, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.LIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.BRIGHT_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.OFF_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.YELLOW_ROOF_TILES.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_YELLOW_ROOF_TILES.blockSet.base().asItem(), 8);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.TAN_CLAY.brickBlocks.base(), 5)
                        .pattern(" B ")
                        .pattern("BPB")
                        .pattern(" B ")
                        .define('P', GenericBlockSets.PLASTER.blockSet.base())
                        .define('B', Items.BRICKS)
                        .unlockedBy(hasItem(GenericBlockSets.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(exporter);
                //endregion

                //region SMITHING
                createDaggerRecipeTag(exporter, Items.STICK, TagKey.create(Registries.ITEM, Identifier.parse("planks")), WeaponItemsME.WOODEN_DAGGER);
                createDaggerRecipeTag(exporter, Items.STICK, TagKey.create(Registries.ITEM, Identifier.parse("stone_tool_materials")), WeaponItemsME.STONE_DAGGER);
                createDaggerRecipe(exporter, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_DAGGER);

                createSpearRecipeTag(exporter, Items.STICK, TagKey.create(Registries.ITEM, Identifier.parse("planks")), WeaponItemsME.WOODEN_SPEAR);
                createSpearRecipeTag(exporter, Items.STICK, TagKey.create(Registries.ITEM, Identifier.parse("stone_tool_materials")), WeaponItemsME.STONE_SPEAR);
                createSpearRecipe(exporter, Items.STICK, Items.DIAMOND, WeaponItemsME.DIAMOND_SPEAR);

                createToolSetRecipes(exporter, Items.STICK, ResourceItemsME.BRONZE_INGOT, ToolItemsME.BRONZE_PICKAXE, ToolItemsME.BRONZE_AXE, ToolItemsME.BRONZE_SHOVEL, ToolItemsME.BRONZE_HOE);

                createToolSetRecipes(exporter, Items.STICK, ResourceItemsME.CRUDE_INGOT, ToolItemsME.CRUDE_PICKAXE, ToolItemsME.CRUDE_AXE, ToolItemsME.CRUDE_SHOVEL, ToolItemsME.CRUDE_HOE);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WEAVER_STING, 1)
                        .pattern("  S")
                        .pattern(" S ")
                        .pattern("W  ")
                        .define('S', ResourceItemsME.SPIDER_STINGER)
                        .define('W', Items.STICK)
                        .unlockedBy(hasItem(ResourceItemsME.SPIDER_STINGER),
                                conditionsFromItem(ResourceItemsME.SPIDER_STINGER))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.MISC, ResourceItemsME.FABRIC, 2)
                        .pattern("sss")
                        .pattern("sss")
                        .define('s', Items.STRING)
                        .unlockedBy(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.REINFORCED_SCAFFOLDING, 6)
                        .pattern("LCL")
                        .pattern("S S")
                        .pattern("T T")
                        .define('L', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "stripped_logs")))
                        .define('C', GenericBlockSets.CANVAS.blockSet.base())
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .save(exporter, "reinforced_scaffolding");

                //region CANVAS
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CANVAS.blockSet.base(), 3)
                        .pattern("FF")
                        .pattern("FF")
                        .define('F', ResourceItemsME.FABRIC)
                        .unlockedBy(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .save(exporter);

                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.white(), GenericBlockSets.WHITE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.BLACK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.blue(), GenericBlockSets.BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.brown(), GenericBlockSets.BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.cyan(), GenericBlockSets.CYAN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.green(), GenericBlockSets.GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.lightBlue(), GenericBlockSets.LIGHT_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.lightGray(), GenericBlockSets.LIGHT_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.magenta(), GenericBlockSets.MAGENTA_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.orange(), GenericBlockSets.ORANGE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.pink(), GenericBlockSets.PINK_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.purple(), GenericBlockSets.PURPLE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.red(), GenericBlockSets.RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.CANVAS.blockSet.base().asItem(), Items.DYE.yellow(), GenericBlockSets.YELLOW_CANVAS.blockSet.base().asItem(), 8);

                createCenterSurroundRecipe(exporter, GenericBlockSets.BLUE_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_BLUE_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.BROWN_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_BROWN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GRAY_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_GRAY_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.GREEN_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_GREEN_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.RED_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_RED_CANVAS.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.YELLOW_CANVAS.blockSet.base().asItem(), Items.DYE.gray(), GenericBlockSets.DARK_YELLOW_CANVAS.blockSet.base().asItem(), 8);
                //endregion

                createBucketRecipe(exporter, Items.IRON_INGOT, Items.BUCKET);

                createMetalsRecipe(exporter, ResourceItemsME.TIN_NUGGET, ResourceItemsME.TIN_INGOT, ModBlocks.TIN_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.LEAD_NUGGET, ResourceItemsME.LEAD_INGOT, ModBlocks.LEAD_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.SILVER_NUGGET, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.MITHRIL_NUGGET, ResourceItemsME.MITHRIL_INGOT, ModBlocks.MITHRIL_BLOCK);

                createMetalsRecipe(exporter, ResourceItemsME.BRONZE_NUGGET, ResourceItemsME.BRONZE_INGOT, ModBlocks.BRONZE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.CRUDE_NUGGET, ResourceItemsME.CRUDE_INGOT, ModBlocks.CRUDE_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.BURZUM_STEEL_NUGGET, ResourceItemsME.BURZUM_STEEL_INGOT, ModBlocks.BURZUM_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.STEEL_NUGGET, ResourceItemsME.STEEL_INGOT, ModBlocks.STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.EDHEL_STEEL_NUGGET, ResourceItemsME.EDHEL_STEEL_INGOT, ModBlocks.EDHEL_STEEL_BLOCK);
                createMetalsRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_NUGGET, ResourceItemsME.KHAZAD_STEEL_INGOT, ModBlocks.KHAZAD_STEEL_BLOCK);

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.ADAMANT, 9)
                        .requires(ModBlocks.ADAMANT_BLOCK)
                        .unlockedBy(hasItem(ModBlocks.ADAMANT_BLOCK),
                                conditionsFromItem(ModBlocks.ADAMANT_BLOCK))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.RUBY, 9)
                        .requires(ModBlocks.RUBY_BLOCK)
                        .unlockedBy(hasItem(ModBlocks.RUBY_BLOCK),
                                conditionsFromItem(ModBlocks.RUBY_BLOCK))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ResourceItemsME.SAPPHIRE, 9)
                        .requires(ModBlocks.SAPPHIRE_BLOCK)
                        .unlockedBy(hasItem(ModBlocks.SAPPHIRE_BLOCK),
                                conditionsFromItem(ModBlocks.SAPPHIRE_BLOCK))
                        .save(exporter);
                //endregion

                //region SEEDS
                createSeedsRecipe(exporter, FoodItemsME.TOMATO, ResourceItemsME.TOMATO_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.BELL_PEPPER, ResourceItemsME.BELL_PEPPER_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.CUCUMBER, ResourceItemsME.CUCUMBER_SEEDS);
                createSeedsRecipe(exporter, FoodItemsME.LETTUCE, ResourceItemsME.LETTUCE_SEEDS);
                createSeedsRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_SEEDS);
                createSeedsRecipe(exporter, ResourceItemsME.FLAX, ResourceItemsME.FLAX_SEEDS);
                //endregion

                //region FOOD
                createCookedFoodRecipes(exporter, FoodItemsME.RAW_HORSE, FoodItemsME.COOKED_HORSE);
                createCookedFoodRecipes(exporter, FoodItemsME.MEAT_SKEWER, FoodItemsME.COOKED_MEAT_SKEWER);
                createCookedFoodRecipes(exporter, FoodItemsME.VEGETABLE_SKEWER, FoodItemsME.COOKED_VEGETABLE_SKEWER);
                createCookedFoodRecipes(exporter, Items.EGG, FoodItemsME.BOILED_EGG);
                //endregion


                SpecialRecipeBuilder.special(HelmetAttachmentRecipe::new).save(exporter, "custom_armor_hood");
                SpecialRecipeBuilder.special(HelmetAttachmentRemovalRecipe::new).save(exporter, "custom_armor_hood_removal");
                SpecialRecipeBuilder.special(BackAttachmentRecipe::new).save(exporter, "custom_armor_cape");
                SpecialRecipeBuilder.special(BackAttachmentRemovalRecipe::new).save(exporter, "custom_armor_cape_removal");
                SpecialRecipeBuilder.special(MountArmorAddonRemovalRecipe::new).save(exporter, "custom_mount_armor_addon_removal");
                SpecialRecipeBuilder.special(MountArmorSideSkullAddonRecipe::new).save(exporter, "custom_mount_armor_side_skull_addon");
                SpecialRecipeBuilder.special(MountArmorTopSkullAddonRecipe::new).save(exporter, "custom_mount_armor_top_skull_addon");

                //region Alloying
                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4,  4)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "tin")))
                        .unlockedBy(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "bronze" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "tin")))
                        .input(ResourceItemsME.ASH)
                        .unlockedBy(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "crude" + "_from_alloying")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(Items.COAL)
                        .unlockedBy(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "lead")))
                        .input(Items.COAL)
                        .unlockedBy(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "khazad_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .unlockedBy(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "edhel_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3, 3)
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")))
                        .input(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "lead")))
                        .input(ResourceItemsME.ASH)
                        .unlockedBy(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "burzum_steel" + "_from_alloying_tags")));

                AlloyRecipeJsonBuilder.createAlloyRecipe(itemLookup, RecipeCategory.MISC, "chicken_nugget", INGOT_LIQUID_VALUE, 1)
                        .input(Items.CHICKEN)
                        .input(Items.WHEAT)
                        .input(Items.EGG)
                        .input(FoodItemsME.GARLIC)
                        .unlockedBy(hasItem(ResourceItemsME.PTEROSAUR_NUGGET),
                                conditionsFromItem(ResourceItemsME.PTEROSAUR_NUGGET))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "chicken_nugget" + "_from_alloying")));


                HotMetalsModel.nuggets.forEach(nugget -> {
                    //createMeltRecipe(exporter, nugget, BuiltInRegistries.ITEM.getKey(nugget).getPath().replace("_nugget", ""), INGOT_LIQUID_VALUE / 9);
                });
                HotMetalsModel.shapesTag.forEach(shape -> {
                    createAnvilShapingRecipeTag(exporter, shape.tagKey(), shape.output(), shape.amount());
                });
                HotMetalsModel.shapesItem.forEach(shape -> {
                    createAnvilShapingRecipeItem(exporter, shape.item(), shape.output(), shape.amount());
                });

                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper")), "copper");
                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "tin")), "tin");

                createMeltBulkRecipe(exporter, ResourceItemsME.BRONZE_INGOT, "bronze");
                createMeltBulkRecipe(exporter, ResourceItemsME.CRUDE_INGOT, "crude");

                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "lead")), "lead");
                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "silver")), "silver");
                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "iron")), "iron");
                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "gold")), "gold");

                createMeltBulkRecipe(exporter, ResourceItemsME.STEEL_INGOT, "steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_INGOT, "khazad_steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.EDHEL_STEEL_INGOT, "edhel_steel");
                createMeltBulkRecipe(exporter, ResourceItemsME.BURZUM_STEEL_INGOT, "burzum_steel");

                createMeltBulkRecipeTag(exporter, TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "mithril")), "mithril");

                createMeltBulkRecipe(exporter, Items.NETHERITE_INGOT, "netherite");

                createAnvilRecipe(exporter, ModBlocks.STEEL_BLOCK.asItem(), ResourceItemsME.STEEL_INGOT, DecorativeItemsME.TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.KHAZAD_STEEL_BLOCK.asItem(), ResourceItemsME.KHAZAD_STEEL_INGOT, DecorativeItemsME.DWARVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.EDHEL_STEEL_BLOCK.asItem(), ResourceItemsME.EDHEL_STEEL_INGOT, DecorativeItemsME.ELVEN_TREATED_ANVIL);
                createAnvilRecipe(exporter, ModBlocks.BURZUM_STEEL_BLOCK.asItem(), ResourceItemsME.BURZUM_STEEL_INGOT, DecorativeItemsME.ORCISH_TREATED_ANVIL);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.BELLOWS, 1)
                        .pattern(" PS")
                        .pattern("PFF")
                        .pattern("TPS")
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .define('S', Items.STICK)
                        .define('F', Items.LEATHER)
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .save(exporter);

                createWattleRecipes(exporter, Items.BRICKS,
                        ModBlocks.WATTLE_AND_BRICK, ModBlocks.WATTLE_AND_BRICK_CROSS, ModBlocks.WATTLE_AND_BRICK_RIGHT,
                        ModBlocks.WATTLE_AND_BRICK_LEFT, ModBlocks.WATTLE_AND_BRICK_PILLAR, ModBlocks.WATTLE_AND_BRICK_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(),
                        ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT,
                        ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.DARK_DAUB.blockSet.base().asItem(),
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_CROSS, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_RIGHT,
                        ModBlocks.DARK_WATTLE_AND_DARK_DAUB_LEFT, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_PILLAR, ModBlocks.DARK_WATTLE_AND_DARK_DAUB_DIAMOND);

                createWattleRecipes(exporter, GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(),
                        ModBlocks.WATTLE_AND_YELLOW_DAUB, ModBlocks.WATTLE_AND_YELLOW_DAUB_CROSS, ModBlocks.WATTLE_AND_YELLOW_DAUB_RIGHT,
                        ModBlocks.WATTLE_AND_YELLOW_DAUB_LEFT, ModBlocks.WATTLE_AND_YELLOW_DAUB_PILLAR, ModBlocks.WATTLE_AND_YELLOW_DAUB_DIAMOND);

                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.DYE.black(), ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.DYE.green(), ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_CROSS.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB_CROSS.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB_RIGHT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_LEFT.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB_LEFT.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB_PILLAR.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModBlocks.WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), Items.DYE.red(), ModBlocks.RED_WATTLE_AND_WHITE_DAUB_DIAMOND.asItem(), 8);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .save(exporter);
                
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BURZUM_BARS, 16)
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(exporter);


                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.AGED_WOOD_WINDOW, 4)
                        .pattern("AAA")
                        .pattern("AGA")
                        .pattern("AAA")
                        .define('A', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .define('G', Items.GLASS)
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_TRAPDOOR, 2)
                        .pattern("WWW")
                        .pattern("WWW")
                        .define('W', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_TRAPDOOR, 2)
                        .pattern("NSN")
                        .pattern("NSN")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.AGED_WOOD_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BRONZE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.CRUDE_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_STEEL_DOOR, 3)
                        .pattern("SS")
                        .pattern("SS")
                        .pattern("SS")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                createPaneRecipe(exporter, ResourceItemsME.SILVER_INGOT, ModBlocks.SILVER_BARS, 16);
                createPaneRecipe(exporter, Items.GOLD_INGOT, ModBlocks.GILDED_BARS, 16);

                createCenterSurroundRecipe(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.DYE.black(), GenericBlockSets.DARK_DAUB.blockSet.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, GenericBlockSets.WHITE_DAUB.blockSet.base().asItem(), Items.DYE.yellow(), GenericBlockSets.YELLOW_DAUB.blockSet.base().asItem(), 8);

                //region TREATED_WOOD
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PHP")
                        .pattern("PPP")
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("logs")))
                        .define('H', Items.HONEYCOMB)
                        .unlockedBy(hasItem(Items.HONEYCOMB),
                                conditionsFromItem(Items.HONEYCOMB))
                        .save(exporter);

                createBrickRecipe(exporter, GenericBlockSets.TREATED_WOOD.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_BEAM.blockSet.base(), 3);
                createBrickRecipe(exporter, GenericBlockSets.TREATED_WOOD_BEAM.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_TILING.blockSet.base(), 4);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base(), 4)
                        .requires(GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(exporter);

                createBrickRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.TREATED_WOOD_PANELS.blockSet.base(), 4);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_TILING);
                createGenericRecipes(GenericBlockSets.TREATED_WOOD_CARVED_BEAM);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.TREATED_WOOD_CARVED_BEAM.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab())
                        .unlockedBy(hasItem(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD_BEAM.blockSet.slab()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TREATED_WOOD_ROPE_FENCE, 3)
                        .pattern("WRW")
                        .pattern("WRW")
                        .define('W', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('R', ModDecorativeBlocks.ROPE)
                        .unlockedBy(hasItem(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(exporter);
                //endregion

                //region AGED_WOOD
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD.blockSet.base(), 6)
                        .pattern("PPP")
                        .pattern("PAP")
                        .pattern("PPP")
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("logs")))
                        .define('A', ResourceItemsME.ASH)
                        .unlockedBy(hasItem(ResourceItemsME.ASH),
                                conditionsFromItem(ResourceItemsME.ASH))
                        .save(exporter);

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BEAM.blockSet.base(), 3);
                createGenericRecipes(GenericBlockSets.AGED_WOOD);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PLANKS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BEAM);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_PANELS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_BOARDS);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_FISH_CARVING);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_CARVED_BEAM);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_KNOTTED_BEAM);
                createShinglesRecipe(exporter, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), GenericBlockSets.AGED_WOOD_SHINGLES.blockSet.base());
                createGenericRecipes(GenericBlockSets.AGED_WOOD_SHINGLES);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base(), 4)
                        .requires(GenericBlockSets.AGED_WOOD.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD.blockSet.base()))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base()).getPath() + "_from_wood")));

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD_PLANKS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_PANELS.blockSet.base(), 4);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.slab()))
                        .save(exporter);
                createGenericRecipes(GenericBlockSets.AGED_WOOD_CARVING);

                createBrickRecipe(exporter, GenericBlockSets.AGED_WOOD_PANELS.blockSet.base().asItem(), GenericBlockSets.AGED_WOOD_BOARDS.blockSet.base(), 4);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base(), 1)
                        .pattern("S")
                        .pattern("S")
                        .define('S', GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.slab()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_CARVED_BEAM.blockSet.base(), 3)
                        .pattern("P")
                        .pattern("P")
                        .pattern("P")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_KNOTTED_BEAM.blockSet.base(), 6)
                        .pattern("PW")
                        .pattern("WP")
                        .pattern("PW")
                        .define('W', GenericBlockSets.AGED_WOOD.blockSet.base())
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(exporter);
                //endregion

                //region AGED_WOOD_REDDISH
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_REDDISH_BEAM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PRP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .define('R', Items.DYE.red())
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(exporter);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_REDDISH_BEAM);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_CARVED_PILLAR.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_BEAM.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_BEAM.blockSet.base()))
                        .save(exporter);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_CARVED_PILLAR);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_CARVING.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_CARVING.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_CARVING.blockSet.base()))
                        .save(exporter);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_CARVING);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_HORSES.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_FISH_CARVING.blockSet.base()))
                        .save(exporter);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_HORSES);
                 //endregion

                //region AGED_WOOD_GILDED_CARVED_PILLAR
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.AGED_WOOD_GILDED_TRIM.blockSet.base(), 8)
                        .pattern("PPP")
                        .pattern("PGP")
                        .pattern("PPP")
                        .define('P', GenericBlockSets.AGED_WOOD_PANELS.blockSet.base())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(GenericBlockSets.AGED_WOOD_PANELS.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.AGED_WOOD_PANELS.blockSet.base()))
                        .save(exporter);

                createGenericRecipes(GenericBlockSets.AGED_WOOD_GILDED_TRIM);
                 //endregion

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OLD_SKULL, 1)
                        .requires(Items.SKELETON_SKULL)
                        .requires(ResourceItemsME.ASH)
                        .unlockedBy(hasItem(Items.SKELETON_SKULL),
                                conditionsFromItem(Items.SKELETON_SKULL))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SKELETON, 1)
                        .pattern("BSB")
                        .pattern(" B ")
                        .pattern("B B")
                        .define('B', TagKey.create(Registries.ITEM, MiddleEarth.of("bones")))
                        .define('S', ModDecorativeBlocks.OLD_SKULL.asItem())
                        .unlockedBy(hasItem(ModDecorativeBlocks.OLD_SKULL.asItem()),
                                conditionsFromItem(ModDecorativeBlocks.OLD_SKULL.asItem()))
                        .save(exporter);

                createCombinedItemRecipe(exporter, Blocks.SKELETON_SKULL, ItemTags.CANDLES, ModDecorativeBlocks.SKULL_CANDLE);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLESTICK, 1)
                        .pattern("C")
                        .pattern("S")
                        .pattern("S")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HOLDER, 1)
                        .pattern("C ")
                        .pattern("SS")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_LAMP, 1)
                        .pattern("T ")
                        .pattern("BB")
                        .define('T', Items.TORCH)
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CANDLE_HEAP, 1)
                        .pattern("CCC")
                        .pattern("CCC")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('H', ModDecorativeBlocks.SMALL_CHANDELIER)
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRONZE_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CNC")
                        .pattern("N N")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHANDELIER, 1)
                        .pattern(" N ")
                        .pattern("CHC")
                        .pattern("N N")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('H', ModDecorativeBlocks.SMALL_BRONZE_CHANDELIER)
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(hasItem(Items.CANDLE),
                                conditionsFromItem(Items.CANDLE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STONE_LECTERN.asItem(), 1)
                        .pattern("SSS")
                        .pattern(" B ")
                        .pattern(" S ")
                        .define('S', Items.STONE)
                        .define('B', Items.BOOKSHELF)
                        .unlockedBy(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF, 1)
                        .pattern("DDD")
                        .pattern("SSS")
                        .pattern("DDD")
                        .define('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .define('S', StoneBlockSets.DOLOMITE_SET.baseBlocks.slab())
                        .unlockedBy(hasItem(Items.BOOKSHELF),
                                conditionsFromItem(Items.BOOKSHELF))
                        .save(exporter);

                createStatueRecipe(exporter, Blocks.POLISHED_BASALT, Blocks.BASALT, StoneBlockSets.BASALT_SET.baseBlocks.wall(), ModDecorativeBlocks.BASALT_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.CALCITE_SET.polishedBlocks.base(), Blocks.CALCITE, StoneBlockSets.CALCITE_SET.baseBlocks.wall(), ModDecorativeBlocks.CALCITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.DEEPSLATE_SET.polishedBlocks.base(), Blocks.DEEPSLATE, StoneBlockSets.DEEPSLATE_SET.baseBlocks.wall(), ModDecorativeBlocks.DEEPSLATE_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.DIORITE_SET.polishedBlocks.base(), Blocks.DIORITE, Blocks.DIORITE_WALL, ModDecorativeBlocks.DIORITE_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.GABBRO_SET.polishedBlocks.base(), StoneBlockSets.GABBRO_SET.baseBlocks.base(), StoneBlockSets.GABBRO_SET.baseBlocks.wall(), ModDecorativeBlocks.GABBRO_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.GALONN_SET.polishedBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.base(), StoneBlockSets.GALONN_SET.baseBlocks.wall(), ModDecorativeBlocks.GALONN_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.KHAGALABAN_SET.polishedBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.base(), StoneBlockSets.KHAGALABAN_SET.baseBlocks.wall(), ModDecorativeBlocks.KHAGALABAN_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.base(), StoneBlockSets.PUMICE_SET.baseBlocks.wall(), ModDecorativeBlocks.PUMICE_STATUE);
                createStatueRecipe(exporter, Blocks.POLISHED_TUFF, Blocks.TUFF, Blocks.TUFF_WALL, ModDecorativeBlocks.TUFF_STATUE);
                createStatueRecipe(exporter, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSets.ZIGILABAN_SET.baseBlocks.base(), StoneBlockSets.ZIGILABAN_SET.baseBlocks.wall(), ModDecorativeBlocks.ZIGILABAN_STATUE);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CERAMIC_PLATE, 1)
                        .pattern("BB")
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(Items.BRICK),
                                conditionsFromItem(Items.BRICK))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROTTEN_PLATE, 4)
                        .pattern("RR")
                        .define('R', WoodBlockSets.ROTTEN_SET.logBlocks.log())
                        .unlockedBy(hasItem(WoodBlockSets.ROTTEN_SET.logBlocks.log()),
                                conditionsFromItem(WoodBlockSets.ROTTEN_SET.logBlocks.log()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_PLATE, 1)
                        .pattern("SS")
                        .define('S', ResourceItemsME.SILVER_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.SILVER_INGOT),
                                conditionsFromItem(ResourceItemsME.SILVER_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MEDGON_SPIKE, 1)
                        .pattern("M  ")
                        .pattern("MM ")
                        .pattern("PMP")
                        .define('M', StoneBlockSets.MEDGON_SET.baseBlocks.base())
                        .define('P', StoneBlockSets.MEDGON_SET.polishedBlocks.base())
                        .unlockedBy(hasItem(StoneBlockSets.MEDGON_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.MEDGON_SET.baseBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BURZUM_SPIKES, 4)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern("BBB")
                        .define('B', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .define('N', ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TAPPER, 1)
                        .pattern(" S ")
                        .pattern("LBL")
                        .pattern(" L ")
                        .define('S', ResourceItemsME.STEEL_NUGGET)
                        .define('L', ItemTags.LOGS)
                        .define('B', Items.BUCKET)
                        .unlockedBy(hasItem(Items.BUCKET),
                                conditionsFromItem(Items.BUCKET));

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ORCISH_DRUM, 1)
                        .pattern("SLS")
                        .pattern("W W")
                        .pattern(" W ")
                        .define('S', Items.STICK)
                        .define('W', ItemTags.LOGS)
                        .define('L', Items.LEATHER)
                        .unlockedBy(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_AND_BRICK_WINDOW, 4)
                        .pattern("BSB")
                        .pattern("SGS")
                        .pattern("BSB")
                        .define('B', Items.BRICKS)
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW, 2)
                        .pattern("SSS")
                        .pattern("SGS")
                        .pattern("SSS")
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .save(exporter);

                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.DYE.gray(), ModDecorativeBlocks.DARK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.DYE.black(), ModDecorativeBlocks.BLACK_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.DYE.green(), ModDecorativeBlocks.GREEN_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.DYE.red(), ModDecorativeBlocks.RED_WATTLE_FRAMED_WINDOW.asItem(), 8);
                createCenterSurroundRecipe(exporter, ModDecorativeBlocks.WATTLE_FRAMED_WINDOW.asItem(), Items.DYE.white(), ModDecorativeBlocks.WHITE_WATTLE_FRAMED_WINDOW.asItem(), 8);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.MUD_BRICK_ROUND_WINDOW, 4)
                        .pattern("MBM")
                        .pattern("BGB")
                        .pattern("MBM")
                        .define('M', Items.MUD_BRICKS)
                        .define('G', Items.GLASS)
                        .define('B', Items.BRICK)
                        .unlockedBy(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WHITE_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.WHITE_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(GenericBlockSets.WHITE_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.WHITE_DAUB.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_DAUB_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.YELLOW_DAUB.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.YELLOW_DAUB.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PLASTER_ROUND_WINDOW, 4)
                        .pattern("WSW")
                        .pattern("SGS")
                        .pattern("WSW")
                        .define('W', GenericBlockSets.PLASTER.blockSet.base())
                        .define('G', Items.GLASS)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(GenericBlockSets.PLASTER.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.PLASTER.blockSet.base()))
                        .save(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_BRONZE.blockSet.base(), ModBlocks.BRONZE_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_CRUDE_PLATES.blockSet.base(), ModBlocks.CRUDE_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_LEAD.blockSet.base(), ModBlocks.LEAD_BLOCK, 4);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.CUT_SILVER.blockSet.base(), ModBlocks.SILVER_BLOCK, 4);

                createCushionRecipe(exporter, Blocks.WOOL.blue(), ModDecorativeBlocks.BLUE_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BLUE_CUSHION, Items.DYE.gray(), ModDecorativeBlocks.DARK_BLUE_CUSHION);
                createCushionRecipe(exporter, Blocks.WOOL.brown(), ModDecorativeBlocks.BROWN_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BROWN_CUSHION, Items.DYE.gray(), ModDecorativeBlocks.DARK_BROWN_CUSHION);
                createCushionRecipe(exporter, Blocks.WOOL.green(), ModDecorativeBlocks.GREEN_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.GREEN_CUSHION, Items.DYE.gray(), ModDecorativeBlocks.DARK_GREEN_CUSHION);
                createCushionRecipe(exporter, Blocks.WOOL.red(), ModDecorativeBlocks.RED_CUSHION);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.RED_CUSHION, Items.DYE.gray(), ModDecorativeBlocks.DARK_RED_CUSHION);

                createSmallCurtainRecipe(exporter, Blocks.WOOL.black(), ModDecorativeBlocks.SMALL_BLACK_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.blue(), ModDecorativeBlocks.SMALL_BLUE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.brown(), ModDecorativeBlocks.SMALL_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_BLUE_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.SMALL_DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_BROWN_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.SMALL_DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_GREEN_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.SMALL_DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.SMALL_RED_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.SMALL_DARK_RED_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.WOOL.blue(), ModDecorativeBlocks.SMALL_FANCY_BLUE_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.WOOL.green(), ModDecorativeBlocks.SMALL_FANCY_GREEN_CURTAIN);
                createSmallFancyCurtainRecipe(exporter, Blocks.WOOL.red(), ModDecorativeBlocks.SMALL_FANCY_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.gray(), ModDecorativeBlocks.SMALL_GRAY_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.green(), ModDecorativeBlocks.SMALL_GREEN_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.purple(), ModDecorativeBlocks.SMALL_PURPLE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.red(), ModDecorativeBlocks.SMALL_RED_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.white(), ModDecorativeBlocks.SMALL_WHITE_CURTAIN);
                createSmallCurtainRecipe(exporter, Blocks.WOOL.yellow(), ModDecorativeBlocks.SMALL_YELLOW_CURTAIN);

                createCurtainRecipe(exporter, Blocks.WOOL.black(), ModDecorativeBlocks.BLACK_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.blue(), ModDecorativeBlocks.BLUE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.brown(), ModDecorativeBlocks.BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BLUE_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.DARK_BLUE_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.BROWN_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.DARK_BROWN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.GREEN_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.DARK_GREEN_CURTAIN);
                createDyeableItemRecipe(exporter, ModDecorativeBlocks.RED_CURTAIN, Items.DYE.gray(), ModDecorativeBlocks.DARK_RED_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.WOOL.blue(), ModDecorativeBlocks.FANCY_BLUE_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.WOOL.green(), ModDecorativeBlocks.FANCY_GREEN_CURTAIN);
                createFancyCurtainRecipe(exporter, Blocks.WOOL.red(), ModDecorativeBlocks.FANCY_RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.gray(), ModDecorativeBlocks.GRAY_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.green(), ModDecorativeBlocks.GREEN_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.purple(), ModDecorativeBlocks.PURPLE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.red(), ModDecorativeBlocks.RED_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.white(), ModDecorativeBlocks.WHITE_CURTAIN);
                createCurtainRecipe(exporter, Blocks.WOOL.yellow(), ModDecorativeBlocks.YELLOW_CURTAIN);
                
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.ROPE_LADDER, 3)
                        .pattern("R R")
                        .pattern("RSR")
                        .pattern("R R")
                        .define('R', ModDecorativeBlocks.ROPE)
                        .define('S', Items.STRING)
                        .unlockedBy(hasItem(ModDecorativeBlocks.ROPE),
                                conditionsFromItem(ModDecorativeBlocks.ROPE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FANCY_BED, 1)
                        .pattern("FFW")
                        .pattern("FFW")
                        .pattern("PPP")
                        .define('W', TagKey.create(Registries.ITEM, Identifier.parse("wool")))
                        .define('F', ResourceItemsME.FABRIC)
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.STRAW_BED, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .define('S', ResourceItemsME.STRAW)
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(ResourceItemsME.STRAW),
                                conditionsFromItem(ResourceItemsME.STRAW))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FUR_BED, 1)
                        .pattern("FFF")
                        .pattern("PPP")
                        .define('F', ResourceItemsME.FUR)
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .save(exporter);

                createCenterSurroundRecipe(exporter, ResourceItemsME.SILVER_NUGGET, Items.TORCH, DecorativeItemsME.SILVER_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.DWARVEN_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.EDHEL_STEEL_NUGGET, Items.TORCH, DecorativeItemsME.ELVEN_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.STEEL_NUGGET, Items.TORCH, DecorativeItemsME.TREATED_STEEL_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.CRUDE_NUGGET, Items.TORCH, DecorativeItemsME.CRUDE_LANTERN, 1);
                createCenterSurroundRecipe(exporter, ResourceItemsME.LEAD_NUGGET, Items.TORCH, DecorativeItemsME.LEAD_LANTERN, 1);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.CRYSTAL_LAMP, 1)
                        .pattern("NGN")
                        .pattern("GLG")
                        .pattern("NIN")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .define('L', TagKey.create(Registries.ITEM, Identifier.parse("candles")))
                        .define('G', ResourceItemsME.QUARTZ_SHARD)
                        .unlockedBy(hasItem(ResourceItemsME.QUARTZ_SHARD),
                                conditionsFromItem(ResourceItemsME.QUARTZ_SHARD))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('I', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('T', Items.TORCH)
                        .unlockedBy(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.GILDED_SCONCE, 4)
                        .pattern("NTN")
                        .pattern(" I ")
                        .define('N', Items.GOLD_NUGGET)
                        .define('I', Items.GOLD_INGOT)
                        .define('T', Items.TORCH)
                        .unlockedBy(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, DecorativeItemsME.ORCISH_SCONCE, 2)
                        .pattern("NTN")
                        .pattern(" S ")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('S', Items.STICK)
                        .define('T', Items.TORCH)
                        .unlockedBy(hasItem(Items.TORCH),
                                conditionsFromItem(Items.TORCH))
                        .save(exporter);

                createWoodStoolRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_STOOL);
                createWoodBenchRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_BENCH);
                createWoodTableRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_TABLE);
                createWoodChairRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_CHAIR);
                createWoodLadderRecipe(exporter, GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base().asItem(), ModDecorativeBlocks.TREATED_WOOD_LADDER);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_HOBBIT_DOOR, 1)
                        .pattern("LLL")
                        .pattern("LSL")
                        .pattern("LLL")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, 1)
                        .pattern("LSL")
                        .pattern("SLL")
                        .pattern("LSL")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('L', Items.SPRUCE_PLANKS)
                        .unlockedBy(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BLUE_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.DYE.blue())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREEN_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.DYE.green())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, 1)
                        .pattern(" B ")
                        .pattern("BDB")
                        .pattern(" B ")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.DYE.lightBlue())
                        .unlockedBy(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RED_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.DYE.red())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, 1)
                        .pattern(" BG")
                        .pattern("BDG")
                        .pattern(" BG")
                        .define('D', ModDecorativeBlocks.LARCH_HOBBIT_DOOR)
                        .define('B', Items.DYE.yellow())
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR),
                                conditionsFromItem(ModDecorativeBlocks.LARCH_HOBBIT_DOOR))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TALL_FIR_DOOR, 1)
                        .pattern("SP")
                        .pattern("PP")
                        .pattern("SP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('P', WoodBlockSets.FIR_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.FIR_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.FIR_SET.planksBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OAK_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('P', Items.OAK_PLANKS)
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('P', WoodBlockSets.BLACK_PINE_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.BLACK_PINE_SET.planksBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("SPS")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('P', Items.SPRUCE_PLANKS)
                        .unlockedBy(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SIMPLE_LARCH_GATE, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('P', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, ModDecorativeBlocks.SIMPLE_LARCH_GATE);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_STABLE_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .define('P', Items.SPRUCE_PLANKS)
                        .unlockedBy(hasItem(Items.SPRUCE_PLANKS),
                                conditionsFromItem(Items.SPRUCE_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_STURDY_DOOR, 1)
                        .pattern("SPP")
                        .pattern("PPP")
                        .pattern("SPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, 1)
                        .pattern("FF")
                        .define('F', WoodBlockSets.BEECH_SET.planksBlocks.gate())
                        .unlockedBy(hasItem(WoodBlockSets.BEECH_SET.planksBlocks.gate()),
                                conditionsFromItem(WoodBlockSets.BEECH_SET.planksBlocks.gate()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_GONDORIAN_GATE, 1)
                        .pattern("LCL")
                        .pattern("CCS")
                        .pattern("LCL")
                        .define('L', WoodBlockSets.BLACK_LEBETHRON_SET.planksBlocks.base())
                        .define('C', Items.COPPER_BLOCK.weathering().oxidized())
                        .define('S', ResourceItemsME.STEEL_INGOT)
                        .unlockedBy(hasItem(Items.COPPER_BLOCK.weathering().oxidized()),
                                conditionsFromItem(Items.COPPER_BLOCK.weathering().oxidized()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_DWARVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .define('B', ResourceItemsME.BRONZE_INGOT)
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, 1)
                        .pattern("TNT")
                        .pattern("TTS")
                        .pattern("TNT")
                        .define('N', ResourceItemsME.STEEL_NUGGET)
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .save(exporter);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.RUINED_DWARVEN_DOOR, ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, 1)
                        .pattern("SSG")
                        .pattern("GDL")
                        .pattern("DSS")
                        .define('L', Items.LEVER)
                        .define('G', StoneBlockSets.DOLOMITE_SET.smoothBlocks.base())
                        .define('D', StoneBlockSets.DOLOMITE_SET.baseBlocks.base())
                        .define('S', Items.STONE)
                        .unlockedBy(hasItem(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()),
                                conditionsFromItem(StoneBlockSets.DOLOMITE_SET.smoothBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ELVEN_GATE, 1)
                        .pattern("BTB")
                        .pattern("BTS")
                        .pattern("BTB")
                        .define('B', Items.DYE.cyan())
                        .define('T', GenericBlockSets.TREATED_WOOD.blockSet.base())
                        .define('S', ResourceItemsME.EDHEL_STEEL_INGOT)
                        .unlockedBy(hasItem(GenericBlockSets.TREATED_WOOD.blockSet.base()),
                                conditionsFromItem(GenericBlockSets.TREATED_WOOD.blockSet.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GREAT_ORCISH_GATE, 1)
                        .pattern("SSS")
                        .pattern("SNS")
                        .pattern("NNN")
                        .define('N', ModBlocks.BURZUM_STEEL_BLOCK)
                        .define('S', ResourceItemsME.BURZUM_STEEL_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BURZUM_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.BURZUM_STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.TURF, 4)
                        .pattern("MM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', Items.DIRT)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.CORRUPTED_MOSS_CARPET, 3)
                        .pattern("MM")
                        .define('M', ModNatureBlocks.CORRUPTED_MOSS_BLOCK)
                        .unlockedBy(hasItem(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                                conditionsFromItem(ModNatureBlocks.CORRUPTED_MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.FOREST_MOSS_CARPET, 3)
                        .pattern("MM")
                        .define('M', ModNatureBlocks.FOREST_MOSS_BLOCK)
                        .unlockedBy(hasItem(ModNatureBlocks.FOREST_MOSS_BLOCK),
                                conditionsFromItem(ModNatureBlocks.FOREST_MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_DIRT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', Items.DIRT)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_CHALKSOIL, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.CHALKSOIL)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_SILT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.SILT)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_LOAM, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.LOAM)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRASSY_PEAT, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', ModBlocks.LOAM)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.PEBBLED_GRASS, 4)
                        .pattern("DM")
                        .pattern("MD")
                        .define('M', Items.MOSS_BLOCK)
                        .define('D', TagKey.create(Registries.ITEM, Identifier.parse("stone_crafting_materials")))
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WASTE_PILE, 8)
                        .pattern("DDD")
                        .pattern("DWD")
                        .pattern("DDD")
                        .define('W', Items.ROTTEN_FLESH)
                        .define('D', ItemTagsME.DIRT)
                        .unlockedBy(hasItem(Items.MOSS_BLOCK),
                                conditionsFromItem(Items.MOSS_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SKELETAL_PILE, 8)
                        .pattern("DDD")
                        .pattern("DBD")
                        .pattern("DDD")
                        .define('B', ItemTagsME.BONES)
                        .define('D', ModBlocks.WASTE_PILE)
                        .unlockedBy(hasItem(ModBlocks.WASTE_PILE),
                                conditionsFromItem(ModBlocks.WASTE_PILE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.FOUL_DIRT, 8)
                        .pattern("DDD")
                        .pattern("DAD")
                        .pattern("DDD")
                        .define('A', ResourceItemsME.ASH)
                        .define('D', ModBlocks.WASTE_PILE)
                        .unlockedBy(hasItem(ModBlocks.WASTE_PILE),
                                conditionsFromItem(ModBlocks.WASTE_PILE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.SNOWY_DIRT, 4)
                        .pattern("DS")
                        .pattern("SD")
                        .define('D', Items.DIRT)
                        .define('S', Items.SNOW_BLOCK)
                        .unlockedBy(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base(), 2)
                        .pattern("CC")
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("stone_crafting_materials")))
                        .unlockedBy(hasItem(Items.COBBLESTONE),
                                conditionsFromItem(Items.COBBLESTONE))
                        .save(exporter);
                //createMossyRecipe(exporter, StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base(), StoneBlockSets.DRYSTONE_SET.mossyCobblestoneBlocks.base());
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, GenericBlockSets.FRAMED_DRYSTONE.blockSet.base(), 1)
                        .pattern(" S ")
                        .pattern("SCS")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("stone_crafting_materials")))
                        .unlockedBy(hasItem(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()),
                                conditionsFromItem(StoneBlockSets.DRYSTONE_SET.cobblestoneBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .define('D', Items.DIRT)
                        .define('C', TagKey.create(Registries.ITEM, Identifier.parse("stone_crafting_materials")))
                        .unlockedBy(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBBLY_ASHEN_DIRT, 4)
                        .pattern("DC")
                        .pattern("CD")
                        .define('D', ModBlocks.ASHEN_DIRT)
                        .define('C', StoneBlockSets.ASHENSTONE_SET.cobblestoneBlocks.base())
                        .unlockedBy(hasItem(Items.DIRT),
                                conditionsFromItem(Items.DIRT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DIRTY_ROOTS, 2)
                        .pattern(" R ")
                        .pattern("RDR")
                        .pattern(" R ")
                        .define('D', Items.ROOTED_DIRT)
                        .define('R', Items.HANGING_ROOTS)
                        .unlockedBy(hasItem(Items.ROOTED_DIRT),
                                conditionsFromItem(Items.ROOTED_DIRT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WATERING_CAN, 1)
                        .pattern(" N ")
                        .pattern("NII")
                        .pattern(" II")
                        .define('N', ResourceItemsME.TIN_NUGGET)
                        .define('I', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WOODEN_BUCKET, 1)
                        .pattern(" R ")
                        .pattern("P P")
                        .pattern(" P ")
                        .define('R', ModDecorativeBlocks.ROPE)
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(ModDecorativeBlocks.ROPE),
                                conditionsFromItem(ModDecorativeBlocks.ROPE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .define('S', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.TREATED_STEEL_ROD, 1)
                        .pattern("S")
                        .pattern("S")
                        .pattern("S")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.IRON_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('I', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(Items.IRON_CHAIN).getPath() + "_alt")));

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BRONZE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .define('N', ResourceItemsME.BRONZE_NUGGET)
                        .define('I', ResourceItemsME.BRONZE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_CHAIN, 4)
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('I', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CRUDE_BROAD_CHAIN, 8)
                        .pattern("NN")
                        .pattern("II")
                        .pattern("NN")
                        .define('N', ResourceItemsME.CRUDE_NUGGET)
                        .define('I', ResourceItemsME.CRUDE_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPIKY_CHAIN, 4)
                        .pattern(" N ")
                        .pattern("NIN")
                        .pattern(" N ")
                        .define('I', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .define('N', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_nuggets")))
                        .unlockedBy(hasItem(ResourceItemsME.STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.STEEL_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.DWARVEN_KEY, 1)
                        .pattern("IN")
                        .define('N', ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .define('I', ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.KHAZAD_STEEL_INGOT),
                                conditionsFromItem(ResourceItemsME.KHAZAD_STEEL_INGOT))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EMBERS, 1)
                        .requires(Items.MAGMA_BLOCK, 1)
                        .requires(ResourceItemsME.ASH, 1)
                        .unlockedBy(hasItem(Items.MAGMA_BLOCK),
                                conditionsFromItem(Items.MAGMA_BLOCK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHIMNEY, 2)
                        .pattern(" B ")
                        .pattern(" B ")
                        .pattern("PPP")
                        .define('B', Items.BRICKS)
                        .define('P', StoneBlockSets.DOLOMITE_SET.polishedBlocks.base())
                        .unlockedBy(hasItem(Items.BRICKS),
                                conditionsFromItem(Items.BRICKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.TREATED_STEEL_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_BIG_BRAZIER, 2)
                        .pattern("B B")
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.GILDED_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', Items.GOLD_INGOT)
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.TREATED_STEEL_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GILDED_SMALL_BRAZIER, 2)
                        .pattern("BCB")
                        .pattern("SSS")
                        .define('B', ModBlocks.GILDED_BARS)
                        .define('C', Items.CAMPFIRE)
                        .define('S', Items.GOLD_INGOT)
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIRE_BOWL, 2)
                        .pattern("SCS")
                        .pattern("SSS")
                        .define('C', Items.CAMPFIRE)
                        .define('S', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "steel_ingots")))
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BONFIRE, 1)
                        .pattern(" L ")
                        .pattern("LCL")
                        .define('C', Items.CAMPFIRE)
                        .define('L', TagKey.create(Registries.ITEM, Identifier.parse("logs")))
                        .unlockedBy(hasItem(Items.CAMPFIRE),
                                conditionsFromItem(Items.CAMPFIRE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GROUND_BOOK, 1)
                        .pattern("BSR")
                        .define('B', Items.BOOK)
                        .define('S', Items.STRING)
                        .define('R', Items.DYE.red())
                        .unlockedBy(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.DWARVEN_GROUND_BOOK, 1)
                        .pattern("BG")
                        .define('B', Items.BOOK)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(Items.BOOK),
                                conditionsFromItem(Items.BOOK))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SMALL_CRATE, 1)
                        .pattern("SSS")
                        .pattern("PPP")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.THIN_BARREL, 1)
                        .pattern("VSV")
                        .pattern("V V")
                        .pattern("VSV")
                        .define('S', TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .define('V', TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "wooden_vertical_slabs")))
                        .unlockedBy(hasItem(Items.OAK_SLAB),
                                conditionsFromItem(Items.OAK_SLAB))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARCH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.LARCH_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.LARCH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.LARCH_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.PINE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.PINE_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.PINE_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.PINE_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SPRUCE_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', Blocks.SPRUCE_PLANKS)
                        .unlockedBy(hasItem(Blocks.SPRUCE_PLANKS),
                                conditionsFromItem(Blocks.SPRUCE_PLANKS))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FIR_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.FIR_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.FIR_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.FIR_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BEECH_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.BEECH_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.BEECH_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.BEECH_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CHESTNUT_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.CHESTNUT_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.CHESTNUT_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.CHESTNUT_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.OAK_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.OAK_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.OAK_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.OAK_SET.planksBlocks.base()))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.WILLOW_COFFER, 1)
                        .pattern("TLT")
                        .pattern("L L")
                        .pattern("LLL")
                        .define('T', ResourceItemsME.TIN_NUGGET)
                        .define('L', WoodBlockSets.WILLOW_SET.planksBlocks.base())
                        .unlockedBy(hasItem(WoodBlockSets.WILLOW_SET.planksBlocks.base()),
                                conditionsFromItem(WoodBlockSets.WILLOW_SET.planksBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SACK, 1)
                        .pattern("C C")
                        .pattern("CRC")
                        .pattern("CCC")
                        .define('C', GenericBlockSets.CANVAS.blockSet.base())
                        .define('R', Items.RESIN_CLUMP)
                        .unlockedBy(hasItem(Items.RESIN_CLUMP),
                                conditionsFromItem(Items.RESIN_CLUMP))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.BELL, 1)
                        .pattern("VSV")
                        .pattern("VGV")
                        .define('S', Items.STICK)
                        .define('V', StoneBlockSets.STONE_SET.baseBlocks.verticalSlab())
                        .define('G', Items.GOLD_INGOT)
                        .unlockedBy(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_SNOW, 8)
                        .requires(Items.SNOWBALL, 8)
                        .requires(Items.WATER_BUCKET, 1)
                        .unlockedBy(hasItem(Items.SNOWBALL),
                                conditionsFromItem(Items.SNOWBALL))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.STICKY_ICE, 4)
                        .pattern("II")
                        .pattern("II")
                        .define('I', Items.ICE)
                        .unlockedBy(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .save(exporter);

                createBannerPatternRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.PIPEWEED_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ModNatureBlocks.LEBETHRON_SAPLING.asItem(), ResourceItemsME.GONDOR_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ModNatureBlocks.MALLORN_SAPLING.asItem(), ResourceItemsME.LOTHLORIEN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.MAGMA_BLOCK, ResourceItemsME.MORDOR_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.HAY_BLOCK, ResourceItemsME.ROHAN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BONE, ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BONE_BLOCK, ResourceItemsME.GOBLIN_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.SKELETON_SKULL, ResourceItemsME.SCREECHING_SKULL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.DYE.white(), ResourceItemsME.ISENGARD_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ToolItemsME.DWARVEN_SMITHING_HAMMER, ResourceItemsME.ANVIL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, ResourceItemsME.BRONZE_INGOT, ResourceItemsME.BELL_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.GOLD_NUGGET, ResourceItemsME.DWARF_CROWN_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.SPIDER_EYE, ResourceItemsME.SPIDER_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.BOW, ResourceItemsME.BOW_BANNER_PATTERN);
                createBannerPatternRecipe(exporter, Items.OAK_LEAVES, ResourceItemsME.OAK_LEAF_BANNER_PATTERN);

                createBrickRecipe(exporter, ModBlocks.POINTED_DOLOMITE.asItem(), StoneBlockSets.DOLOMITE_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_GALONN.asItem(), StoneBlockSets.GALONN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_IZHERABAN.asItem(), StoneBlockSets.IZHERABAN_SET.baseBlocks.base(), 1);
                createBrickRecipe(exporter, ModBlocks.POINTED_LIMESTONE.asItem(), StoneBlockSets.LIMESTONE_SET.baseBlocks.base(), 1);

                SimpleCookingRecipeBuilder.smoking(Ingredient.of(itemLookup.getOrThrow(TagKey.create(Registries.ITEM, Identifier.parse("planks")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.planksBlocks.base(), 0.0f, 100)
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS)).save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(WoodBlockSets.SCORCHED_SET.planksBlocks.base()).getPath() + "_from_smoking")));
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(itemLookup.getOrThrow(TagKey.create(Registries.ITEM, Identifier.parse("logs")))), RecipeCategory.BUILDING_BLOCKS, WoodBlockSets.SCORCHED_SET.logBlocks.log(), 0.0f, 100)
                        .unlockedBy(hasItem(Items.OAK_LOG),
                                conditionsFromItem(Items.OAK_LOG)).save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(WoodBlockSets.SCORCHED_SET.logBlocks.log()).getPath() + "_from_smoking")));

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_ICICLES, 4)
                        .pattern("III")
                        .pattern(" I ")
                        .define('I', Items.ICE)
                        .unlockedBy(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DROOPING_ICICLES, 4)
                        .pattern("III")
                        .pattern("III")
                        .pattern(" I ")
                        .define('I', Items.ICE)
                        .unlockedBy(hasItem(Items.ICE),
                                conditionsFromItem(Items.ICE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.BUCKET, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern(" T ")
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .unlockedBy(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(Items.BUCKET).getPath() + "_alt")));

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, Items.CAULDRON, 1)
                        .pattern("T T")
                        .pattern("T T")
                        .pattern("TBT")
                        .define('T', ResourceItemsME.TIN_INGOT)
                        .define('B', ModBlocks.TIN_BLOCK)
                        .unlockedBy(hasItem(ResourceItemsME.TIN_INGOT),
                                conditionsFromItem(ResourceItemsME.TIN_INGOT))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(Items.CAULDRON).getPath() + "_alt")));

                createCenterSurroundRecipe(exporter, Blocks.TUFF.asItem(), Items.RAW_COPPER, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, StoneBlockSets.SLATE_SET.baseBlocks.base().asItem(), Items.RAW_COPPER, StoneBlockSets.KHAGALABAN_SET.baseBlocks.base().asItem(), 8);
                createCenterSurroundRecipe(exporter, Blocks.TUFF.asItem(), Items.IRON_NUGGET, StoneBlockSets.IRONSTONE_SET.baseBlocks.base().asItem(), 8);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.LARGE_JUG, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_POT, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.CLAY_JAR, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_JAR, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_AMPHORA, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_VASE, Items.CLAY);

                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.BROWN_FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.FAT_POT, Items.CLAY);
                offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GRAY_FAT_POT, Items.CLAY);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.POT_OF_GOLD, 1)
                        .pattern(" G ")
                        .pattern("GGG")
                        .pattern(" P ")
                        .define('P', ModDecorativeBlocks.FAT_POT)
                        .define('G', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.AZALEA_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', Items.FLOWERING_AZALEA_LEAVES)
                        .unlockedBy(hasItem(Items.FLOWERING_AZALEA_LEAVES),
                                conditionsFromItem(Items.FLOWERING_AZALEA_LEAVES))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.DRY_GROWTH.asItem(), 4)
                        .pattern("sss")
                        .pattern("sss")
                        .define('s', Items.STICK)
                        .unlockedBy(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.THORNY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .define('s', Items.STICK)
                        .define('l', FoodItemsME.TOUGH_BERRIES)
                        .unlockedBy(hasItem(FoodItemsME.TOUGH_BERRIES),
                                conditionsFromItem(FoodItemsME.TOUGH_BERRIES))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.GREEN_GROWTH.asItem(), 8)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.IVY_GROWTH.asItem(), 6)
                        .pattern("sls")
                        .pattern("sls")
                        .define('s', Items.STICK)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.LILAC_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', Items.LILAC)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(Items.OAK_LEAVES),
                                conditionsFromItem(Items.OAK_LEAVES))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.PINK_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.PINK_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(ModNatureBlocks.PINK_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.PINK_FLOWERS))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.RED_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.RED_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(ModNatureBlocks.RED_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.RED_FLOWERS))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.WHITE_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.WHITE_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(ModNatureBlocks.WHITE_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.WHITE_FLOWERS))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.YELLOW_FLOWER_GROWTH.asItem(), 8)
                        .pattern("lfl")
                        .pattern("lfl")
                        .define('f', ModNatureBlocks.YELLOW_FLOWERS)
                        .define('l', TagKey.create(Registries.ITEM, Identifier.parse("leaves")))
                        .unlockedBy(hasItem(ModNatureBlocks.YELLOW_FLOWERS),
                                conditionsFromItem(ModNatureBlocks.YELLOW_FLOWERS))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.FROZEN_GROWTH.asItem(), 8)
                        .pattern("sis")
                        .pattern("sis")
                        .define('i', ModNatureBlocks.STICKY_SNOW)
                        .define('s', ModNatureBlocks.DRY_GROWTH)
                        .unlockedBy(hasItem(ModNatureBlocks.DRY_GROWTH),
                                conditionsFromItem(ModNatureBlocks.DRY_GROWTH))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLDEN_CHALICE, 1)
                        .pattern("I")
                        .pattern("N")
                        .pattern("N")
                        .define('I', Items.GOLD_INGOT)
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(Items.GOLD_INGOT),
                                conditionsFromItem(Items.GOLD_INGOT))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.COPPER_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.SILVER_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .save(exporter);
                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER, 1)
                        .pattern("NNN")
                        .define('N', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.COPPER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.COPPER_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.COPPER_COIN),
                                conditionsFromItem(ResourceItemsME.COPPER_COIN))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.SILVER_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.SILVER_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.SILVER_COIN),
                                conditionsFromItem(ResourceItemsME.SILVER_COIN))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks.GOLD_COIN_PILE, 1)
                        .pattern("NN")
                        .pattern("NN")
                        .define('N', ResourceItemsME.GOLD_COIN)
                        .unlockedBy(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 3)
                        .requires(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER)
                        .unlockedBy(hasItem(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.COPPER_TREASURE_HEAP_LAYER))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper_coin_from_treasure")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 3)
                        .requires(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER)
                        .unlockedBy(hasItem(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.SILVER_TREASURE_HEAP_LAYER))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "silver_coin_from_treasure")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 3)
                        .requires(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER)
                        .unlockedBy(hasItem(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER),
                                conditionsFromItem(ModDecorativeBlocks.GOLD_TREASURE_HEAP_LAYER))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "gold_nugget_from_treasure")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.COPPER_COIN, 4)
                        .requires(ModDecorativeBlocks.COPPER_COIN_PILE)
                        .unlockedBy(hasItem(ModDecorativeBlocks.COPPER_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.COPPER_COIN_PILE))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "copper_coin_from_pile")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.SILVER_COIN, 4)
                        .requires(ModDecorativeBlocks.SILVER_COIN_PILE)
                        .unlockedBy(hasItem(ModDecorativeBlocks.SILVER_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.SILVER_COIN_PILE))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "silver_coin_from_pile")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .requires(ModDecorativeBlocks.GOLD_COIN_PILE)
                        .unlockedBy(hasItem(ModDecorativeBlocks.GOLD_COIN_PILE),
                                conditionsFromItem(ModDecorativeBlocks.GOLD_COIN_PILE))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "gold_nugget_from_pile")));

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_BULRUSH, 2)
                        .requires(ModNatureBlocks.TALL_BULRUSH)
                        .unlockedBy(hasItem(ModNatureBlocks.TALL_BULRUSH),
                                conditionsFromItem(ModNatureBlocks.TALL_BULRUSH))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_REEDS, 2)
                        .requires(ResourceItemsME.REEDS)
                        .unlockedBy(hasItem(ResourceItemsME.REEDS),
                                conditionsFromItem(ResourceItemsME.REEDS))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_DEAD_RUSHES, 2)
                        .requires(ModNatureBlocks.DEAD_RUSHES)
                        .unlockedBy(hasItem(ModNatureBlocks.DEAD_RUSHES),
                                conditionsFromItem(ModNatureBlocks.DEAD_RUSHES))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_RUSHES, 2)
                        .requires(ModNatureBlocks.RUSHES)
                        .unlockedBy(hasItem(ModNatureBlocks.RUSHES),
                                conditionsFromItem(ModNatureBlocks.RUSHES))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ModNatureBlocks.SHORT_CATTAILS, 2)
                        .requires(ModNatureBlocks.TALL_CATTAILS)
                        .unlockedBy(hasItem(ModNatureBlocks.TALL_CATTAILS),
                                conditionsFromItem(ModNatureBlocks.TALL_CATTAILS))
                        .save(exporter);

                ShapelessRecipeBuilder.shapeless(itemLookup, RecipeCategory.BUILDING_BLOCKS, ResourceItemsME.GOLD_COIN, 4)
                        .requires(ModDecorativeBlocks.POT_OF_GOLD)
                        .unlockedBy(hasItem(ModDecorativeBlocks.POT_OF_GOLD),
                                conditionsFromItem(ModDecorativeBlocks.POT_OF_GOLD))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  "gold_from_pot_of_gold")));

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, WeaponItemsME.HELD_BANNER, 1)
                        .pattern("WWW")
                        .pattern("WWW")
                        .pattern("WSW")
                        .define('W', TagKey.create(Registries.ITEM, Identifier.parse("wool")))
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(ResourceItemsME.GOLD_COIN),
                                conditionsFromItem(ResourceItemsME.GOLD_COIN))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.SLATE_SET.baseBlocks.base(), 4)
                        .pattern("DS")
                        .pattern("SD")
                        .define('D', Items.DEEPSLATE)
                        .define('S', Items.STONE)
                        .unlockedBy(hasItem(Items.DEEPSLATE),
                                conditionsFromItem(Items.DEEPSLATE))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.BLUE_TUFF_SET.baseBlocks.base(), 4)
                        .pattern("TG")
                        .pattern("GT")
                        .define('T', Items.TUFF)
                        .define('G', StoneBlockSets.KHAGALABAN_SET.baseBlocks.base())
                        .unlockedBy(hasItem(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base()))
                        .save(exporter);

                ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, StoneBlockSets.HEMATITE_SET.baseBlocks.base(), 4)
                        .pattern("SI")
                        .pattern("IS")
                        .define('S', Items.STONE)
                        .define('I', StoneBlockSets.IRONSTONE_SET.baseBlocks.base())
                        .unlockedBy(hasItem(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()),
                                conditionsFromItem(StoneBlockSets.IRONSTONE_SET.baseBlocks.base()))
                        .save(exporter);

                createSmokingRecipe(exporter, Items.SHORT_GRASS, ModNatureBlocks.SCORCHED_GRASS.asItem());
                createSmokingRecipe(exporter, ModNatureBlocks.GRASS_TUFT.asItem(), ModNatureBlocks.SCORCHED_TUFT.asItem());
                createSmokingRecipe(exporter, ModNatureBlocks.GREEN_SHRUB.asItem(), ModNatureBlocks.SCORCHED_SHRUB.asItem());
                //endregion

                //region SMOKING-ONLY
                createSmokingRecipe(exporter, ResourceItemsME.PIPEWEED, ResourceItemsME.DRIED_PIPEWEED);
                //endregion

                SpecialRecipeBuilder.special(CustomItemDecorationRecipe::new).save(exporter, "custom_shield_decoration");
            }

            //region Refactored Methods            
            private void createStoneSetRecipes(BlockRecordTypes.RegularSet base) {
                if (base != null) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    createVerticalSlabsRecipe(exporter, base.slab(), base.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, base.verticalSlab(), base.slab());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    createStairsRecipe(exporter, base.base(), base.stairs());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .define('l', base.base())
                            .unlockedBy(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .save(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.BaseStoneSet base) {
                if (base != null) {
                    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.slab(), base.base(), 2);
                    createVerticalSlabsRecipe(exporter, base.slab(), base.verticalSlab());
                    createSlabsFromVerticalRecipe(exporter, base.verticalSlab(), base.slab());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    createStairsRecipe(exporter, base.base(), base.stairs());
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.stairs(), base.base());
                    ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .define('l', base.base())
                            .unlockedBy(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .save(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            private void createStoneSetRecipes(BlockRecordTypes.PillarSet base) {
                if (base != null) {
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .define('l', base.base())
                            .unlockedBy(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .save(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }
            private void createChiseledStoneSetRecipes(BlockRecordTypes.PillarSet base) {
                if (base != null) {
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.verticalSlab(), base.base(), 2);
                    ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, base.wall(), 6)
                            .pattern("lll")
                            .pattern("lll")
                            .define('l', base.base())
                            .unlockedBy(hasItem(base.base()),
                                    conditionsFromItem(base.base()))
                            .save(exporter);
                    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, base.wall(), base.base());
                }
            }

            //endregion

            //region BLOCK RECIPE METHODS
            private void createBrickRecipe(RecipeOutput exporter, Item input, Block output, int count) {
                createBrickRecipe(exporter, input, output, count, null);
            }

            private void createBrickRecipe(RecipeOutput exporter, Item input, Block output, int count, String recipeName) {
                ShapedRecipeBuilder shapedRecipeBuilder = ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("ll")
                        .pattern("ll")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input));
                if (recipeName != null) {
                    shapedRecipeBuilder.save(exporter, recipeName);
                } else {
                    shapedRecipeBuilder.save(exporter);
                }
            }

            private void createPillarRecipe(RecipeOutput exporter, Block input, Block output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createChiseledRecipe(RecipeOutput exporter, Block input, Block output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createCutPolishedRecipe(RecipeOutput exporter, Block input, Block output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("l")
                        .pattern("l")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createMossyRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(input)
                        .requires(Items.VINE)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(output).getPath() + "_vine")));

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(input)
                        .requires(Blocks.MOSS_BLOCK)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(output).getPath() + "_moss")));
            }

            private void createSmeltingRecipe(RecipeOutput exporter, Item input, Item output) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, output, 0.1f, 200)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createSmeltingRecipeIdentifier(RecipeOutput exporter, Item input, Item output) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, output, 0.1f, 200)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(output).getPath() + "_from_smelting")));
            }

            private void createMeltBulkRecipe(RecipeOutput exporter, Item input, String output) {
                createMeltRecipe(exporter, input, output, 1, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 2, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 3, INGOT_LIQUID_VALUE);
                createMeltRecipe(exporter, input, output, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipe(RecipeOutput exporter, Item input, String output, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount, 0)
                            .input(input)
                            .unlockedBy(hasItem(input),
                                    conditionsFromItem(input))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_1_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(input),
                                    conditionsFromItem(input))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_2_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3,0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(input),
                                    conditionsFromItem(input))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_3_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(input),
                                    conditionsFromItem(input))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_4_" + BuiltInRegistries.ITEM.getKey(input).getPath())));
                }
            }

            private void createMeltBulkRecipeTag(RecipeOutput exporter, TagKey input, String output) {
                createMeltRecipeTag(exporter, input, output, 1, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 2, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 3, INGOT_LIQUID_VALUE);
                createMeltRecipeTag(exporter, input, output, 4, INGOT_LIQUID_VALUE);
            }

            private void createMeltRecipeTag(RecipeOutput exporter, TagKey input, String output, int ingots, int amount) {
                switch (ingots) {
                    case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount, 0)
                            .input(input)
                            .unlockedBy(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_1_" + input.location().getPath())));
                    case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 2, 0)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_2_" + input.location().getPath())));
                    case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 3, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_3_" + input.location().getPath())));
                    case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(this.itemLookup, RecipeCategory.MISC, output, amount * 4, 0)
                            .input(input)
                            .input(input)
                            .input(input)
                            .input(input)
                            .unlockedBy(hasItem(DecorativeItemsME.FORGE),
                                    conditionsFromItem(DecorativeItemsME.FORGE))
                            .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  output + "_from_melting_4_" + input.location().getPath())));
                }
            }

            private void createAnvilShapingRecipeTag(RecipeOutput exporter, TagKey input, Item output, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                        .input(input)
                        .unlockedBy(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .save(exporter);
            }

            private void createAnvilShapingRecipeItem(RecipeOutput exporter, Item input, Item output, int amount) {
                AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(this.itemLookup, RecipeCategory.MISC, output, amount)
                        .input(input)
                        .unlockedBy(hasItem(Items.COPPER_INGOT),
                                conditionsFromItem(Items.COPPER_INGOT))
                        .save(exporter);
            }

            private void createAnvilRecipe(RecipeOutput exporter, Item inputBlock, Item inputIngot, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("IBI")
                        .pattern(" I ")
                        .pattern("LLL")
                        .define('I', inputIngot)
                        .define('B', inputBlock)
                        .define('L', TagKey.create(Registries.ITEM, Identifier.parse("logs")))
                        .unlockedBy(hasItem(inputIngot),
                                conditionsFromItem(inputIngot))
                        .save(exporter);
            }

            private void createGenericRecipes(GenericBlockSetBuilder set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(exporter, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(exporter, set.blockSet.base(), set.blockSet.stairs());
                offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.wall(), set.blockSet.base());
            }
            private void createGenericRecipes(SimpleBlockSetBuilder set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.blockSet.slab().asItem(), set.blockSet.base().asItem());
                createVerticalSlabsRecipe(exporter, set.blockSet.slab(), set.blockSet.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.blockSet.verticalSlab(), set.blockSet.slab());
                createStairsRecipe(exporter, set.blockSet.base(), set.blockSet.stairs());
            }

            private void createRegularSetRecipes(BlockRecordTypes.RegularSet set) {
                offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, set.slab().asItem(), set.base().asItem());
                createVerticalSlabsRecipe(exporter, set.slab(), set.verticalSlab());
                createSlabsFromVerticalRecipe(exporter, set.verticalSlab(), set.slab());
                createStairsRecipe(exporter, set.base(), set.stairs());
                offerWallRecipe(RecipeCategory.BUILDING_BLOCKS, set.wall(), set.base());
            }

            private void createStairsRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("l  ")
                        .pattern("ll ")
                        .pattern("lll")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createSlabsFromVerticalRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.BLOCK.getKey(input).getPath() + "_from_vertical")));
            }

            private void createVerticalSlabsRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createShinglesRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 7)
                        .pattern(" w ")
                        .pattern("www")
                        .pattern("www")
                        .define('w', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createRoofingRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern(" w ")
                        .pattern("www")
                        .define('w', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createDoorRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("ll")
                        .pattern("ll")
                        .pattern("ll")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createTrapdoorRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createCenterSurroundRecipe(RecipeOutput exporter, Item surroundInput, Item centerItem, Item output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("BBB")
                        .pattern("BDB")
                        .pattern("BBB")
                        .define('B', surroundInput)
                        .define('D', centerItem)
                        .unlockedBy(hasItem(surroundInput),
                                conditionsFromItem(surroundInput))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(output).getPath() + "_alt")));
            }

            private void createDyeableItemRecipe(RecipeOutput exporter, Block blockInput, Item dyeItem, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(blockInput)
                        .requires(dyeItem)
                        .unlockedBy(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .save(exporter);
            }

            private void createCombinedItemRecipe(RecipeOutput exporter, Block blockInput, TagKey<Item> addition, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(blockInput)
                        .requires(addition)
                        .unlockedBy(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .save(exporter);
            }

            private void createPaneRecipe(RecipeOutput exporter, Item blockInput, Block output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("BBB")
                        .pattern("BBB")
                        .define('B', blockInput)
                        .unlockedBy(hasItem(blockInput),
                                conditionsFromItem(blockInput))
                        .save(exporter);
            }

            private void createWoodStoolRecipe(RecipeOutput exporter, Item inputPlanks, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PP")
                        .pattern("SS")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .save(exporter);
            }

            private void createWoodBenchRecipe(RecipeOutput exporter, Item inputPlanks, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .save(exporter);
            }

            private void createWoodTableRecipe(RecipeOutput exporter, Item inputPlanks, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("PPP")
                        .pattern("S S")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .save(exporter);
            }

            private void createWoodChairRecipe(RecipeOutput exporter, Item inputPlanks, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("P  ")
                        .pattern("PPP")
                        .pattern("S S")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .save(exporter);
            }

            private void createWoodLadderRecipe(RecipeOutput exporter, Item inputPlanks, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("P P")
                        .pattern("PSP")
                        .pattern("P P")
                        .define('P', inputPlanks)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(inputPlanks),
                                conditionsFromItem(inputPlanks))
                        .save(exporter);
            }

            private void createStoneStoolRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("SSS")
                        .pattern("S S")
                        .define('S', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createStoneTableRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("SSS")
                        .pattern(" S ")
                        .pattern(" S ")
                        .define('S', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createStoneChairRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("S  ")
                        .pattern("SSS")
                        .pattern("SSS")
                        .define('S', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createLayerRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("BBB")
                        .define('B', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createButtonRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .requires(input, 1)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createPressurePlateRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("BB")
                        .define('B', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createFenceRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 3)
                        .pattern("lsl")
                        .pattern("lsl")
                        .define('l', input)
                        .define('s', Items.STICK)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .unlockedBy(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .save(exporter);
            }

            private void createGildedBlockRecipe(RecipeOutput exporter, Block input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern(" N ")
                        .pattern("NBN")
                        .pattern(" N ")
                        .define('B', input)
                        .define('N', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createBrickworkBlockRecipe(RecipeOutput exporter, Block input, Block inputBinder, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SB")
                        .define('S', inputBinder)
                        .define('B', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createWattleRecipes(RecipeOutput exporter, Item input, Block outputBase,
                                             Block outputCross, Block outputRight, Block outputLeft, Block outputPillar, Block outputDiamond) {
                createBaseWattleRecipe(exporter, input, outputBase);
                createCrossWattleRecipe(exporter, input, outputCross);
                createRightWattleRecipe(exporter, input, outputRight);
                createLeftWattleRecipe(exporter, input, outputLeft);
                createPillarWattleRecipe(exporter, input, outputPillar);
                createDiamondWattleRecipe(exporter, input, outputDiamond);
            }

            private void createBaseWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern(" S ")
                        .pattern("SDS")
                        .pattern(" S ")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createCrossWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SDS")
                        .pattern("DSD")
                        .pattern("SDS")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createRightWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("DDS")
                        .pattern("DSD")
                        .pattern("SDD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createLeftWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("SDD")
                        .pattern("DSD")
                        .pattern("DDS")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createPillarWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 6)
                        .pattern("DSD")
                        .pattern("DSD")
                        .pattern("DSD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createDiamondWattleRecipe(RecipeOutput exporter, Item input, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 5)
                        .pattern("DSD")
                        .pattern("SDS")
                        .pattern("DSD")
                        .define('S', Items.STICK)
                        .define('D', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createStatueRecipe(RecipeOutput exporter, Block polishedInput, Block stoneInput, Block wallInput, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("WSW")
                        .pattern("WSW")
                        .pattern("WPW")
                        .define('W', wallInput)
                        .define('S', stoneInput)
                        .define('P', polishedInput)
                        .unlockedBy(hasItem(polishedInput),
                                conditionsFromItem(polishedInput))
                        .save(exporter);
            }

            private void createCushionRecipe(RecipeOutput exporter, Block woolBlock, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("WW")
                        .pattern("PP")
                        .define('W', woolBlock)
                        .define('P', TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .save(exporter);
            }

            private void createSmallCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SSS")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .save(exporter);
            }
            private void createSmallFancyCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 2)
                        .pattern("SGS")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .define('G', Items.GOLD_NUGGET)
                        .unlockedBy(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .save(exporter);
            }

            private void createCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SSS")
                        .pattern("W W")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .save(exporter);
            }
            private void createFancyCurtainRecipe(RecipeOutput exporter, Block woolBlock, Block output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 4)
                        .pattern("SGS")
                        .pattern("W W")
                        .pattern("W W")
                        .define('W', woolBlock)
                        .define('G', Items.GOLD_NUGGET)
                        .define('S', Items.STICK)
                        .unlockedBy(hasItem(woolBlock),
                                conditionsFromItem(woolBlock))
                        .save(exporter);
            }

            private void createBannerPatternRecipe(RecipeOutput exporter, Item input, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.MISC, output, 1)
                        .pattern("PF")
                        .pattern("BI")
                        .define('I', input)
                        .define('B', Items.DYE.black())
                        .define('F', Items.FEATHER)
                        .define('P', Items.PAPER)
                        .unlockedBy(hasItem(Items.PAPER),
                                conditionsFromItem(Items.PAPER))
                        .save(exporter);
            }
            //endregion

            //region ITEM RECIPE METHODS
            private void createSeedsRecipe(RecipeOutput exporter, Item input, Item output) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, output, 1)
                        .requires(input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void createPickaxeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MMM")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createAxeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MM ")
                        .pattern("MR ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createShovelRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createHoeRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.TOOLS, output, 1)
                        .pattern("MM ")
                        .pattern(" R ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createSwordRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createDaggerRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createDaggerRecipeTag(RecipeOutput exporter, Item inputRod, TagKey inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern(" M ")
                        .pattern(" R ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .save(exporter);
            }

            private void createSpearRecipe(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createSpearRecipeTag(RecipeOutput exporter, Item inputRod, TagKey inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.COMBAT, output, 1)
                        .pattern("  M")
                        .pattern(" R ")
                        .pattern("R  ")
                        .define('M', inputMaterial)
                        .define('R', inputRod)
                        .unlockedBy(hasItem(Items.OAK_PLANKS),
                                conditionsFromItem(Items.OAK_PLANKS))
                        .save(exporter);
            }

            private void createBucketRecipe(RecipeOutput exporter, Item inputMaterial, Item output) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, 1)
                        .pattern("M M")
                        .pattern(" M ")
                        .define('M', inputMaterial)
                        .unlockedBy(hasItem(inputMaterial),
                                conditionsFromItem(inputMaterial))
                        .save(exporter);
            }

            private void createToolSetRecipes(RecipeOutput exporter, Item inputRod, Item inputMaterial, Item outputPickaxe, Item outputAxe, Item outputShovel, Item outputHoe) {
                createPickaxeRecipe(exporter, inputRod, inputMaterial, outputPickaxe);
                createAxeRecipe(exporter, inputRod, inputMaterial, outputAxe);
                createShovelRecipe(exporter, inputRod, inputMaterial, outputShovel);
                createHoeRecipe(exporter, inputRod, inputMaterial, outputHoe);
            }

            private void createCookedFoodRecipes(RecipeOutput exporter, Item rawFood, Item cookedFood) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawFood), RecipeCategory.FOOD, CookingBookCategory.FOOD, cookedFood, 0.35f, 200)
                        .unlockedBy(hasItem(rawFood), conditionsFromItem(rawFood))
                        .save(exporter, getItemName(cookedFood) + "_from_smelting");
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(rawFood), RecipeCategory.FOOD, cookedFood, 0.35f, 100)
                        .unlockedBy(hasItem(rawFood), conditionsFromItem(rawFood))
                        .save(exporter, getItemName(cookedFood) + "_from_smoking");
                SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(rawFood), RecipeCategory.FOOD, cookedFood, 0.35f, 600)
                        .unlockedBy(hasItem(rawFood), conditionsFromItem(rawFood))
                        .save(exporter, getItemName(cookedFood) + "_from_campfire_cooking");
            }

            private void createSmokingRecipe(RecipeOutput exporter, Item rawFood, Item cookedFood) {
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(rawFood), RecipeCategory.FOOD, cookedFood, 0.35f, 100)
                        .unlockedBy(hasItem(rawFood), conditionsFromItem(rawFood))
                        .save(exporter);
            }

            private void createMetalsRecipe(RecipeOutput exporter, Item nugget, Item ingot, Block block) {
                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ingot, 1)
                        .requires(nugget, 9)
                        .unlockedBy(hasItem(nugget),
                                conditionsFromItem(nugget))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(ingot).getPath() + "_from_nuggets")));

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, nugget, 9)
                        .requires(ingot)
                        .unlockedBy(hasItem(ingot),
                                conditionsFromItem(ingot))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(nugget).getPath() + "_from_ingot")));

                createFilledRecipe(exporter, ingot, block, 1);

                ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ingot, 9)
                        .requires(block)
                        .unlockedBy(hasItem(block),
                                conditionsFromItem(block))
                        .save(exporter, String.valueOf(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,  BuiltInRegistries.ITEM.getKey(ingot).getPath() + "_from_block")));
            }
            //endregion

            private void createFilledRecipe(RecipeOutput exporter, Item input, Block output, int count) {
                ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, output, count)
                        .pattern("lll")
                        .pattern("lll")
                        .pattern("lll")
                        .define('l', input)
                        .unlockedBy(hasItem(input),
                                conditionsFromItem(input))
                        .save(exporter);
            }

            private void offerSlabRecipe(RecipeCategory category, ItemLike output, ItemLike input) {
                ShapedRecipeBuilder.shaped(this.itemLookup, category, output, 6)
                        .pattern("###")
                        .define('#', input)
                        .unlockedBy(hasItem(input), conditionsFromItem(input))
                        .save(exporter);
            }

            private void offerWallRecipe(RecipeCategory category, ItemLike output, ItemLike input) {
                ShapedRecipeBuilder.shaped(this.itemLookup, category, output, 6)
                        .pattern("###")
                        .pattern("###")
                        .define('#', input)
                        .unlockedBy(hasItem(input), conditionsFromItem(input))
                        .save(exporter);
            }

            private void offerStonecuttingRecipe(RecipeCategory category, ItemLike output, ItemLike input) {
                offerStonecuttingRecipe(category, output, input, 1);
            }

            private void offerStonecuttingRecipe(RecipeCategory category, ItemLike output, ItemLike input, int count) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), category, output, count)
                        .unlockedBy(hasItem(input), conditionsFromItem(input))
                        .save(exporter, getItemName(output) + "_stonecutting");
            }

            private void offerSmelting(List<ItemLike> ingredients, RecipeCategory category, ItemLike output, float experience, int time, String group) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredients.stream()), category, CookingBookCategory.BLOCKS, output, experience, time)
                        .group(group)
                        .unlockedBy(getHasName(ingredients.get(0)), has(ingredients.get(0)))
                        .save(exporter, getItemName(output) + "_from_smelting_" + getItemName(ingredients.get(0)));
            }

            private static String hasItem(ItemLike item) {
                return getHasName(item);
            }

            private Criterion<?> conditionsFromItem(ItemLike item) {
                return has(item);
            }
        };

    }

    @Override
    public String getName() {
        return "RecipeProvider";
    }
}