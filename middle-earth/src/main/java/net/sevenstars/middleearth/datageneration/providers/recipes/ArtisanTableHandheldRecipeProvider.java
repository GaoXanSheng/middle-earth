package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.utils.SmithingTrimPatternsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.RecipeProvider.getHasName;
import static net.minecraft.data.recipes.RecipeProvider.inventoryTrigger;

public class ArtisanTableHandheldRecipeProvider extends net.sevenstars.middleearth.datageneration.providers.recipes.RecipeProvider {
    private final int XP_MEDIUM_SHIELD = 2;
    private final int XP_HEAVY_SHIELD = 4;
    private final CompletableFuture<HolderLookup.Provider> registryLookup;

    public ArtisanTableHandheldRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);

        this.registryLookup = registriesFuture;
    }

    @Override
    public String getName() {
        return "ArtisanTableHandheldRecipes";
    }

    public HolderLookup.RegistryLookup<TrimMaterial> getArmorTrimMaterialsRegistry(){
        HolderLookup.RegistryLookup<TrimMaterial> armorTrimMaterialsRegistry;

        try {
            armorTrimMaterialsRegistry = registryLookup.get().lookupOrThrow(Registries.TRIM_MATERIAL);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimMaterialsRegistry;
    }

    public HolderLookup.RegistryLookup<TrimPattern> getArmorTrimPatternsRegistry(){
        HolderLookup.RegistryLookup<TrimPattern> armorTrimPatternsRegistry;

        try {
            armorTrimPatternsRegistry = registryLookup.get().lookupOrThrow(Registries.TRIM_PATTERN);
        } catch (Exception ignored) {
            throw new IllegalStateException("Data generation without registries failed!");
        }
        return armorTrimPatternsRegistry;
    }

    public Holder<TrimPattern> getPattern(){
        return getArmorTrimPatternsRegistry().getOrThrow(SmithingTrimPatternsME.SMITHING_PART);
    }
    
    public Identifier getMetalIdentifier(MetalTypes metal){
        if (metal.isVanilla()){
            return Identifier.parse(metal.getName());
        } else {
            return Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, metal.getName());
        }
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
        net.sevenstars.middleearth.datageneration.DatagenComponentBinder.bindItemComponents();
        return new RecipeProvider(wrapperLookup, recipeExporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                //region WEAPONS
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.IRON, Items.IRON_SWORD.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.IRON, WeaponItemsME.IRON_DAGGER.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.IRON, WeaponItemsME.IRON_SPEAR.asItem(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_DAGGER.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_SPEAR.asItem(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, Items.NETHERITE_SWORD.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_DAGGER.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_SPEAR.asItem(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SWORD.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_DAGGER.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SPEAR.asItem(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_FALCHION.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_DAGGER.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_LONGBLADE.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_FALCHION.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SHANK.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SCIMITAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SWORD.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SWORD.asItem(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_DAGGER.asItem(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_DAGGER.asItem(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_LONGSWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_LONGSWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_LONGSWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_LONGSWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_LONGSWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SCIMITAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_LONGSWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_LONGSWORD.asItem(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_AXE.asItem(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_FOUNTAIN_GUARD_SPEAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SPEAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SPEAR.asItem(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SPEAR.asItem(), true, DispositionType.EVIL);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SWORD.asItem(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_LONGSWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_LONGSWORD.asItem(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SWORD.asItem(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_GLAIVE.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_GLAIVE.asItem(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD.asItem(), true, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_DAGGER.asItem(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER.asItem(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_LONGSWORD.asItem(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGSWORD.asItem(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_AXE.asItem(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_AXE.asItem(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SPEAR.asItem(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR.asItem(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SWORD.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SWORD.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_FALCHION.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_CLEAVER.asItem(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_MACHETE.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_CLEAVER.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_FALCHION.asItem(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_FALCHION.asItem(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_CLEAVER.asItem(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_FALCHION.asItem(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_KNIFE.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_DAGGER.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_DAGGER.asItem(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_DAGGER.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_KNIFE.asItem(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SHANK.asItem(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_DAGGER.asItem(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SHANK.asItem(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_BROADBLADE.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SCIMITAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_WARBLADE.asItem(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_WARBLADE.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_WARBLADE.asItem(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_WARBLADE.asItem(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SCIMITAR.asItem(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKBLADE.asItem(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_AXE.asItem(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_AXE.asItem(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_AXE.asItem(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE.asItem(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKAXE.asItem(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_SPEAR.asItem(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_SPEAR.asItem(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SPEAR.asItem(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SPEAR.asItem(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(itemLookup, recipeExporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SPEAR.asItem(), true, DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_LONGBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GONDORIAN_NOBLE_LONGBOW.asItem(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_NOBLE_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.ROHIRRIC_LONGBOW.asItem(), DispositionType.GOOD);

                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_LONGBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_HEYDAY_LONGBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.DALISH_NOBLE_LONGBOW.asItem(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_LONGBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.LORIEN_NOBLE_LONGBOW.asItem(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.WOODLAND_REALM_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.WOODLAND_REALM_LONGBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.WOODLAND_REALM_NOBLE_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW.asItem(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_NOBLE_BOW.asItem(), DispositionType.GOOD);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_CROSSBOW.asItem(), DispositionType.GOOD);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.EREBOR_NOBLE_CROSSBOW.asItem(), DispositionType.GOOD);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.ORCISH_BOW.asItem(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.MORDOR_BOW.asItem(), DispositionType.EVIL);
                createArtisanTableNobleLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.MORDOR_ELITE_LONGBOW.asItem(), DispositionType.EVIL);

                createArtisanTableNobleBowRecipe(itemLookup, recipeExporter, WeaponItemsME.URUK_HAI_BOW.asItem(), DispositionType.EVIL);
                createArtisanTableNobleCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.URUK_HAI_CROSSBOW.asItem(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.GUNDABAD_BOW.asItem(), DispositionType.EVIL);
                createArtisanTableLongbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GUNDABAD_LONGBOW.asItem(), DispositionType.EVIL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, WeaponItemsME.GOBLIN_CROSSBOW.asItem(), DispositionType.EVIL);
                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.GOBLIN_TOWN_BOW.asItem(), DispositionType.EVIL);
                createArtisanTableBowRecipe(itemLookup, recipeExporter, WeaponItemsME.MORIA_GOBLIN_BOW.asItem(), DispositionType.EVIL);

                createArtisanTableBowRecipe(itemLookup, recipeExporter, Items.BOW.asItem(), DispositionType.NEUTRAL);
                createArtisanTableCrossbowRecipe(itemLookup, recipeExporter, Items.CROSSBOW.asItem(), DispositionType.NEUTRAL);
                //endregion

                //region TOOLS
                createToolSet(itemLookup, output, MetalTypes.BRONZE, ToolItemsME.BRONZE_PICKAXE.asItem(),
                        ToolItemsME.BRONZE_AXE.asItem(),
                        ToolItemsME.BRONZE_SHOVEL.asItem(),
                        ToolItemsME.BRONZE_HOE.asItem(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.CRUDE, ToolItemsME.CRUDE_PICKAXE.asItem(),
                        ToolItemsME.CRUDE_AXE.asItem(),
                        ToolItemsME.CRUDE_SHOVEL.asItem(),
                        ToolItemsME.CRUDE_HOE.asItem(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(itemLookup, output, MetalTypes.IRON, Items.IRON_PICKAXE.asItem(),
                        Items.IRON_AXE.asItem(),
                        Items.IRON_SHOVEL.asItem(),
                        Items.IRON_HOE.asItem(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.GOLD, Items.GOLDEN_PICKAXE.asItem(),
                        Items.GOLDEN_AXE.asItem(),
                        Items.GOLDEN_SHOVEL.asItem(),
                        Items.GOLDEN_HOE.asItem(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.STEEL, ToolItemsME.STEEL_PICKAXE.asItem(),
                        ToolItemsME.STEEL_AXE.asItem(),
                        ToolItemsME.STEEL_SHOVEL.asItem(),
                        ToolItemsME.STEEL_HOE.asItem(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.KHAZAD_STEEL, ToolItemsME.KHAZAD_STEEL_PICKAXE.asItem(),
                        ToolItemsME.KHAZAD_STEEL_AXE.asItem(),
                        ToolItemsME.KHAZAD_STEEL_SHOVEL.asItem(),
                        ToolItemsME.KHAZAD_STEEL_HOE.asItem(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(itemLookup, output, MetalTypes.EDHEL_STEEL, ToolItemsME.EDHEL_STEEL_PICKAXE.asItem(),
                        ToolItemsME.EDHEL_STEEL_AXE.asItem(),
                        ToolItemsME.EDHEL_STEEL_SHOVEL.asItem(),
                        ToolItemsME.EDHEL_STEEL_HOE.asItem(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(itemLookup, output, MetalTypes.BURZUM_STEEL, ToolItemsME.BURZUM_STEEL_PICKAXE.asItem(),
                        ToolItemsME.BURZUM_STEEL_AXE.asItem(),
                        ToolItemsME.BURZUM_STEEL_SHOVEL.asItem(),
                        ToolItemsME.BURZUM_STEEL_HOE.asItem(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.asItem(),
                        ToolItemsME.MITHRIL_AXE.asItem(),
                        ToolItemsME.MITHRIL_SHOVEL.asItem(),
                        ToolItemsME.MITHRIL_HOE.asItem(),
                        Optional.of(MetalTypes.STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.asItem(),
                        ToolItemsME.MITHRIL_AXE.asItem(),
                        ToolItemsME.MITHRIL_SHOVEL.asItem(),
                        ToolItemsME.MITHRIL_HOE.asItem(),
                        Optional.of(MetalTypes.KHAZAD_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.asItem(),
                        ToolItemsME.MITHRIL_AXE.asItem(),
                        ToolItemsME.MITHRIL_SHOVEL.asItem(),
                        ToolItemsME.MITHRIL_HOE.asItem(),
                        Optional.of(MetalTypes.EDHEL_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.asItem(),
                        ToolItemsME.MITHRIL_AXE.asItem(),
                        ToolItemsME.MITHRIL_SHOVEL.asItem(),
                        ToolItemsME.MITHRIL_HOE.asItem(),
                        Optional.of(MetalTypes.BURZUM_STEEL), DispositionType.NEUTRAL);

                createToolSet(itemLookup, output, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.asItem(),
                        Items.NETHERITE_AXE.asItem(),
                        Items.NETHERITE_SHOVEL.asItem(),
                        Items.NETHERITE_HOE.asItem(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.IRON, Items.IRON_NUGGET, ToolItemsME.IRON_CHISEL.asItem());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.asItem());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.KHAZAD_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.asItem());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.EDHEL_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.asItem());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.BURZUM_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.asItem());
                createArtisanTableChiselRecipe(itemLookup, output, MetalTypes.MITHRIL, ResourceItemsME.MITHRIL_NUGGET, ToolItemsME.MITHRIL_CHISEL.asItem());
                        
                //endregion

                //region SHIELDS
                ItemStack ironShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                ironShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.parse(MetalTypes.IRON.getName()))), getPattern()));

                ItemStack bronzeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                bronzeShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName()))), getPattern()));

                ItemStack crudeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                crudeShieldBorder.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName()))), getPattern()));

                ItemStack steelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                steelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName()))), getPattern()));

                ItemStack edhelSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                edhelSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName()))), getPattern()));

                ItemStack khazadSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                khazadSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName()))), getPattern()));

                ItemStack burzumSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                burzumSteelShieldPlate.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName()))), getPattern()));

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, Items.SHIELD.asItem(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(Items.SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROUND_SHIELD.asItem(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.HEATER_SHIELD.asItem(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.KITE_SHIELD.asItem(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.KITE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.black())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.black())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.black())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.black())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.black())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.black())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.black())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.black())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_HERO_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_HERO_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KNIGHT_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.DYE.green())
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.white())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.yellow())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.green())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.green())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_EORLING_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.DYE.yellow())
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_EORLING_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.DYE.yellow())
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_OVAL_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.blue())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.blue())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.blue())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_OVAL_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.blue())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_BRACED_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.blue())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.blue())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_BRACED_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.DYE.white())
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.DYE.blue())
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.DYE.lightBlue())
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.DYE.lightBlue())
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.DYE.orange())
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(steelShieldPlate.getItem()), steelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_LAURELS_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.yellow())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_LAURELS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.LORIEN_MALLORN_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.yellow())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.LORIEN_MALLORN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.yellow())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GALADHRIM_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_LORD_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.yellow())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GALADHRIM_LORD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.COPPER_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.brown())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.green())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.blue())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.brown())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.green())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.green())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.black())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.DYE.black())
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(edhelSteelShieldPlate.getItem()), edhelSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_CROSS_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_CROSS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_PLATED_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_PLATED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_SHIELD.asItem(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(bronzeShieldBorder.getItem()), bronzeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_BUCKLER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD.asItem(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(khazadSteelShieldPlate.getItem()), khazadSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(WeaponItemsME.MORDOR_WOODEN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_WOODEN_SHIELD),
                                has(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_ROUND_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.MORDOR_BANNER_PATTERN),
                                has(ResourceItemsME.MORDOR_BANNER_PATTERN))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD)
                        .input(Items.DYE.black())
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BRACED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_BRACED_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_BRACED_SHIELD),
                                has(WeaponItemsME.MORDOR_BRACED_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD)
                        .input(Items.DYE.black())
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_LARGE_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_LARGE_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_LARGE_SHIELD),
                                has(WeaponItemsME.MORDOR_LARGE_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD)
                        .input(Items.DYE.black())
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD),
                                has(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_PAVISE.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_PAVISE).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_ARMRUST_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_ARMRUST_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_HEAVY_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_HEAVY_SKULL_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ModDecorativeBlocks.OLD_SKULL.asItem())
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.DOL_GULDUR_HEAVY_SKULL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_CONVERTED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.GONDORIAN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.GONDORIAN_SHIELD),
                                has(WeaponItemsME.GONDORIAN_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.MORDOR_HEAVY_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.MORDOR_HEAVY_SHIELD),
                                has(WeaponItemsME.MORDOR_HEAVY_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.red())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.DYE.black())
                        .componentInput(new ComponentsIngredient(Ingredient.of(ironShieldBorder.getItem()), ironShieldBorder.getComponentsPatch()))
                        .input(Items.DYE.black())
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_HEATER_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_S_RUNE_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .unlockedBy(getHasName(WeaponItemsME.URUK_HAI_SHIELD),
                                has(WeaponItemsME.URUK_HAI_SHIELD))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SIEGE_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.URUK_HAI_SIEGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_REINFORCED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GUNDABAD_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .componentInput(new ComponentsIngredient(Ingredient.of(crudeShieldBorder.getItem()), crudeShieldBorder.getComponentsPatch()))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD.asItem(), "light_shield", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .unlockedBy(getHasName(ResourceItemsME.CRUDE_INGOT),
                                has(ResourceItemsME.CRUDE_INGOT))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.BONE)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.BONE)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD.asItem(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(Items.LEATHER)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_BORDER),
                                has(ResourceItemsME.SHIELD_BORDER))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD.asItem(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .componentInput(new ComponentsIngredient(Ingredient.of(burzumSteelShieldPlate.getItem()), burzumSteelShieldPlate.getComponentsPatch()))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .unlockedBy(getHasName(ResourceItemsME.SHIELD_PLATE),
                                has(ResourceItemsME.SHIELD_PLATE))
                        .save(output, BuiltInRegistries.ITEM.getKey(WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD).getPath() + "_artisan");
                //endregion

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.PIPE.asItem(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.CLAY_PIPE.asItem(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(Items.TERRACOTTA)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(Items.TERRACOTTA)
                        .input(Items.TERRACOTTA)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.CLAY_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.RIVERBEND_PIPE.asItem(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.RIVERBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.BRIMMINGBEND_PIPE.asItem(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.BRIMMINGBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, ToolItemsME.LONGBOTTOM_PIPE.asItem(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_slabs")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .input(TagKey.create(Registries.ITEM, Identifier.parse("planks")))
                        .unlockedBy(getHasName(Items.STICK),
                                has(Items.STICK))
                        .save(output, BuiltInRegistries.ITEM.getKey(ToolItemsME.LONGBOTTOM_PIPE).getPath() + "_artisan");

            }
        };
    }

    private int getXpFor(MetalTypes metalTypes) {
        return switch (metalTypes) {
            default -> 0;
            case EMPTY -> 0;
            case COPPER, TIN -> 2;
            case BRONZE, CRUDE -> 3;
            case IRON, SILVER, LEAD -> 4;
            case STEEL, EDHEL_STEEL, KHAZAD_STEEL, BURZUM_STEEL -> 5;
            case GOLD, NETHERITE -> 7;
            case MITHRIL -> 12;
        };
    }

    private void createToolSet(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike outputPickaxe, ItemLike outputAxe, ItemLike outputShovel, ItemLike outputHoe, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTablePickaxeRecipe(itemLookup, exporter, metal, outputPickaxe, rodMetal, dispositionType);
        createArtisanTableAxeRecipe(itemLookup, exporter, metal, outputAxe, false, rodMetal, dispositionType);
        createArtisanTableShovelRecipe(itemLookup, exporter, metal, outputShovel, rodMetal, dispositionType);
        createArtisanTableHoeRecipe(itemLookup, exporter, metal, outputHoe, rodMetal, dispositionType);
    }

    private void createArtisanTableSwordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSwordRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSwordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemLike output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.BLADE);
        blade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = getXpFor(metal);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(new ComponentsIngredient(Ingredient.of(blade.getItem()), blade.getComponentsPatch()))
                .componentInput(new ComponentsIngredient(Ingredient.of(swordHilt.getItem()), swordHilt.getComponentsPatch()))
                .input(stick)
                .unlockedBy(getHasName(blade.getItem()),
                        conditionsFromItem(blade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableLongswordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, boolean noble, DispositionType dispositionType) {
        createArtisanTableLongswordRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableLongswordRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemLike output, boolean noble, DispositionType dispositionType) {
        ItemStack longBlade = new ItemStack(ResourceItemsME.LONG_BLADE);
        longBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(new ComponentsIngredient(Ingredient.of(longBlade.getItem()), longBlade.getComponentsPatch()))
                .componentInput(new ComponentsIngredient(Ingredient.of(swordHilt.getItem()), swordHilt.getComponentsPatch()))
                .input(stick)
                .unlockedBy(getHasName(longBlade.getItem()),
                        conditionsFromItem(longBlade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableDaggerRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, boolean noble, DispositionType dispositionType) {
        createArtisanTableDaggerRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableDaggerRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemLike output, boolean noble, DispositionType dispositionType) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(metal))), getPattern()));
        } else {
            swordHilt.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(new ComponentsIngredient(Ingredient.of(shortBlade.getItem()), shortBlade.getComponentsPatch()))
                .componentInput(new ComponentsIngredient(Ingredient.of(swordHilt.getItem()), swordHilt.getComponentsPatch()))
                .input(stick)
                .unlockedBy(getHasName(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem(), itemLookup))
                .save(exporter);
    }

    private void createArtisanTableSpearRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSpearRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSpearRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemLike output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        blade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(blade.getItem()), blade.getComponentsPatch()))
                    .input(stick)
                    .input(stick)
                    .unlockedBy(getHasName(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .save(exporter);
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            xp++;
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(blade.getItem()), blade.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .input(stick)
                    .unlockedBy(getHasName(blade.getItem()),
                            conditionsFromItem(blade.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableBowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleBowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType, XP_MEDIUM_SHIELD)
                .input(Items.STICK)
                .input(Items.STRING)
                .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableLongbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences")))
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleLongbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "bow", dispositionType, 2)
                .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences")))
                .input(Items.STRING)
                .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                .input(Items.STRING)
                .input(TagKey.create(Registries.ITEM, Identifier.parse("wooden_fences")))
                .input(Items.STRING)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableCrossbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                Identifier.parse(MetalTypes.IRON.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTableNobleCrossbowRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, ItemLike output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .unlockedBy(getHasName(Items.STRING),
                        conditionsFromItem(Items.STRING, itemLookup))
                .save(exporter);
    }

    private void createArtisanTablePickaxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack pickaxeHead = new ItemStack(ResourceItemsME.PICKAXE_HEAD);
        pickaxeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(pickaxeHead.getItem()), pickaxeHead.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .unlockedBy(getHasName(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(pickaxeHead.getItem()), pickaxeHead.getComponentsPatch()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_artisan");
        }
    }

    private void createArtisanTableAxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTableAxeRecipe(itemLookup, exporter, metal, Items.STICK, output, noble, rodMetal, dispositionType);
    }

    private void createArtisanTableAxeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item stick, ItemLike output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack axeHead = new ItemStack(ResourceItemsME.AXE_HEAD);
        axeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ResourceItemsME.ROD);
                rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                        getMetalIdentifier(rodMetal.get()))), getPattern()));
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(new ComponentsIngredient(Ingredient.of(axeHead.getItem()), axeHead.getComponentsPatch()))
                        .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                        .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                        .unlockedBy(getHasName(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
            } else {
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(new ComponentsIngredient(Ingredient.of(axeHead.getItem()), axeHead.getComponentsPatch()))
                        .input(stick)
                        .input(stick)
                        .unlockedBy(getHasName(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem(), itemLookup))
                        .save(exporter);
            }
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    Identifier.parse(MetalTypes.GOLD.getName()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "axe", dispositionType, xp + 1)
                    .componentInput(new ComponentsIngredient(Ingredient.of(axeHead.getItem()), axeHead.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .input(stick)
                    .unlockedBy(getHasName(axeHead.getItem()),
                            conditionsFromItem(axeHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableShovelRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack shovelHead = new ItemStack(ResourceItemsME.SHOVEL_HEAD);
        shovelHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(shovelHead.getItem()), shovelHead.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .unlockedBy(getHasName(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(shovelHead.getItem()), shovelHead.getComponentsPatch()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableHoeRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, ItemLike output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack hoeHead = new ItemStack(ResourceItemsME.HOE_HEAD);
        hoeHead.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                getMetalIdentifier(metal))), getPattern()));
        int xp = getXpFor(metal);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
                    getMetalIdentifier(rodMetal.get()))), getPattern()));
            
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(hoeHead.getItem()), hoeHead.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .componentInput(new ComponentsIngredient(Ingredient.of(rod.getItem()), rod.getComponentsPatch()))
                    .unlockedBy(getHasName(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(new ComponentsIngredient(Ingredient.of(hoeHead.getItem()), hoeHead.getComponentsPatch()))
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .unlockedBy(getHasName(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem(), itemLookup))
                    .save(exporter);
        }
    }

    private void createArtisanTableChiselRecipe(HolderGetter<Item> itemLookup, RecipeOutput exporter, MetalTypes metal, Item nugget, ItemLike output) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.set(DataComponents.TRIM, new ArmorTrim(getArmorTrimMaterialsRegistry().getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL,
            getMetalIdentifier(metal))), getPattern()));
        int xp = (int) (getXpFor(metal) * 0.5f);

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(itemLookup, RecipeCategory.COMBAT, output, "chisel", DispositionType.NEUTRAL, xp)
                .componentInput(new ComponentsIngredient(Ingredient.of(shortBlade.getItem()), shortBlade.getComponentsPatch()))
                .input(nugget)
                .input(Items.STICK)
                .unlockedBy(getHasName(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem(), itemLookup))
                    .save(exporter, BuiltInRegistries.ITEM.getKey(output.asItem()).getPath() + "_" + metal.getName() + "_artisan");
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> conditionsFromItem(ItemLike item, HolderGetter<Item> itemLookup) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLookup, new ItemLike[]{item}));
    }
}