package net.sevenstars.middleearth.compat;

import dev.architectury.utils.GameInstance;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.recipe.RecipesME;

import java.util.Collection;
import java.util.List;

public class REICommonPluginME implements REICommonPlugin {
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
        registry.register(MiddleEarth.of("artisan_table"), ArtisanTableDisplay.SERIALIZER);
        registry.register(MiddleEarth.of("forge"), AlloyingDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        REICommonPlugin.super.registerDisplays(registry);

        MinecraftServer server = GameInstance.getServer();
        if (server == null) {
            return;
        }

        Collection<RecipeHolder<ArtisanRecipe>> artisanRecipes = server.getRecipeManager().getAllOfType(RecipesME.ARTISAN_TABLE_SUPPLIER.get());
        for(RecipeHolder<ArtisanRecipe> recipeHolder : artisanRecipes) {
            ArtisanRecipe artisanRecipe = recipeHolder.value();
            List<EntryIngredient> inputs = ArtisanTableDisplay.getInputs(artisanRecipe);
            registry.add(new ArtisanTableDisplay(inputs, List.of(EntryIngredients.of(artisanRecipe.getOutput())), artisanRecipe.category));
        }

        Collection<RecipeHolder<AlloyingRecipe>> alloyRecipes = server.getRecipeManager().getAllOfType(RecipesME.FORGE);
        for(RecipeHolder<AlloyingRecipe> recipeHolder : alloyRecipes) {
            AlloyingRecipe alloyRecipe = recipeHolder.value();
            if(!alloyRecipe.output.contains("nugget")) {
                List<EntryIngredient> inputs = AlloyingDisplay.getInputs(alloyRecipe);
                registry.add(new AlloyingDisplay(inputs, alloyRecipe.output, alloyRecipe.amount));
            }
        }
    }
}
