package net.sevenstars.middleearth.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

import java.util.function.Supplier;

public class RecipesME {
    public static final Supplier<RecipeType<ArtisanRecipe>> ARTISAN_TABLE_SUPPLIER = () -> ArtisanRecipe.Type.INSTANCE;

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier, RecipeType<T> recipeType) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(identifier),
                recipeType);
    }

    public static RecipeType<InscriptionRecipe> INSCRIPTION_TABLE;
    public static RecipeType<CrockpotRecipe> CROCKPOT;
    public static RecipeType<AnvilShapingRecipe> ANVIL_SHAPING;
    public static RecipeType<AlloyingRecipe> FORGE;

    public static void registerRecipes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                MiddleEarth.of(AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        FORGE = Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                MiddleEarth.of(AnvilShapingRecipe.Serializer.ID),
                AnvilShapingRecipe.Serializer.INSTANCE);
        ANVIL_SHAPING = Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(AnvilShapingRecipe.Type.ID),
                AnvilShapingRecipe.Type.INSTANCE);

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                MiddleEarth.of(ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);
        Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                MiddleEarth.of(InscriptionRecipe.Serializer.ID),
                InscriptionRecipe.Serializer.INSTANCE);
        INSCRIPTION_TABLE = Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(InscriptionRecipe.Type.ID),
                InscriptionRecipe.Type.INSTANCE);

        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                MiddleEarth.of(CrockpotRecipe.Serializer.ID),
                CrockpotRecipe.Serializer.INSTANCE);
        CROCKPOT = Registry.register(BuiltInRegistries.RECIPE_TYPE,
                MiddleEarth.of(CrockpotRecipe.Type.ID),
                CrockpotRecipe.Type.INSTANCE);
    }
}
