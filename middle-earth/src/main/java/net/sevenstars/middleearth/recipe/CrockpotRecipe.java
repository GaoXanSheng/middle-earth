package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class CrockpotRecipe implements Recipe<MultipleStackRecipeInput> {
    public final int ingredientsAmount;
    public final List<Ingredient> inputs;
    public final ItemStack output;

    private PlacementInfo ingredientPlacement;

    public CrockpotRecipe(int ingredientsAmount, List<Ingredient> inputs, ItemStack output) {
        this.ingredientsAmount = ingredientsAmount;
        this.inputs = inputs;
        this.output = output;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, Level world) {
        if(world.isClientSide()) return false;
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            ItemStack itemStack = input.getItem(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(input.getItem(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input) {
        return this.output.copy();
    }

    public ItemStack craft(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(this.inputs);
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    public static class Type implements RecipeType<CrockpotRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "crockpot";
    }

    public static class Serializer {
        public static final String ID = "crockpot";

        private static final MapCodec<CrockpotRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.INT.fieldOf("ingredients_amount").forGetter(recipe -> recipe.ingredientsAmount),
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                ).apply(instance, CrockpotRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, CrockpotRecipe> STREAM_CODEC = StreamCodec.of(Serializer::write, Serializer::read);

        public static final RecipeSerializer<CrockpotRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

        private static CrockpotRecipe read(RegistryFriendlyByteBuf buf) {
            int ingredientsAmount = ByteBufCodecs.INT.decode(buf);
            int i = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.createWithCapacity(i);
            ingredients.replaceAll(empty -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new CrockpotRecipe(ingredientsAmount, ingredients, output);
        }

        private static void write(RegistryFriendlyByteBuf buf, CrockpotRecipe recipe) {
            ByteBufCodecs.INT.encode(buf, recipe.ingredientsAmount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }
    }
}
