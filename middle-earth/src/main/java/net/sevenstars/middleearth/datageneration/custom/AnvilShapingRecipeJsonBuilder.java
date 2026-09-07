package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AnvilShapingRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private Ingredient input;
    private final Item output;
    private final int amount;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private final HolderGetter<Item> registryLookup;

    public AnvilShapingRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, Item output, int amount ) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.output = output;
        this.amount = amount;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public Item getResult() {
        return this.output;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(this.output));
    }

    @Override
    public void save(RecipeOutput exporter, ResourceKey<Recipe<?>> recipeKey) {
        this.validate(recipeKey);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey)).rewards(AdvancementRewards.Builder.recipe(recipeKey)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        AnvilShapingRecipe anvilShapingRecipe = new AnvilShapingRecipe(this.input, this.output, this.amount);
        exporter.accept(recipeKey, anvilShapingRecipe, builder.build(recipeKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public static AnvilShapingRecipeJsonBuilder createAnvilShapingRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, Item output, int amount) {
        return new AnvilShapingRecipeJsonBuilder(registryLookup, category, output, amount);
    }

    public AnvilShapingRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.of(this.registryLookup.getOrThrow(tag)));
    }

    public AnvilShapingRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input((ItemLike)itemProvider, 1);
    }

    public AnvilShapingRecipeJsonBuilder input(ItemLike itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.of(new ItemLike[]{itemProvider}));
        }
        return this;
    }

    public AnvilShapingRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input((Ingredient) ingredient, 1);
    }

    public AnvilShapingRecipeJsonBuilder input(Ingredient ingredient, int size) {
            this.input = ingredient;

        return this;
    }

    @Override
    public AnvilShapingRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeKey));
        }
    }
}
