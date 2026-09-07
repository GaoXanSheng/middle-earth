package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.NonNullList;
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
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AlloyRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final NonNullList<Ingredient> inputs = NonNullList.create();
    private final String metalOutput;
    private final int metalAmount;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;
    private final int xp;

    private final HolderGetter<Item> registryLookup;

    public AlloyRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, String metalOutput, int metalAmount, int xp) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.metalOutput = metalOutput;
        this.metalAmount = metalAmount;
        this.xp = xp;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public Item getResult() {
        return ResourceItemsME.ROD;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(this.getResult()));
    }

    @Override
    public void save(RecipeOutput exporter, ResourceKey<Recipe<?>> recipeKey) {
        this.validate(recipeKey);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeKey)).rewards(AdvancementRewards.Builder.recipe(recipeKey)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        AlloyingRecipe alloyRecipeJsonBuilder = new AlloyingRecipe((String)Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineCraftingBookCategory(this.category), this.metalOutput, this.inputs, this.metalAmount, this.xp);
        exporter.accept(recipeKey, alloyRecipeJsonBuilder, builder.build(recipeKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public String getOutputMetal() {
        return this.metalOutput;
    }

    public int getMetalAmount() {
        return this.metalAmount;
    }

    public static AlloyRecipeJsonBuilder createAlloyRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, String output, int amount, int xp) {
        return new AlloyRecipeJsonBuilder(registryLookup, category, output, amount, xp);
    }

    public AlloyRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.of(this.registryLookup.getOrThrow(tag)));
    }

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input((ItemLike)itemProvider, 1);
    }

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.of(new ItemLike[]{itemProvider}));
        }
        return this;
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input((Ingredient)ingredient, 1);
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    @Override
    public AlloyRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeKey));
        }
    }
}
