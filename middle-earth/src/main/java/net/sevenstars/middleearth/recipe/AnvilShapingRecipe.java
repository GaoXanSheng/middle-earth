package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import java.util.List;

public class AnvilShapingRecipe implements Recipe<SingleRecipeInput> {
    protected final Ingredient input;
    protected final Item outputItem;
    protected final int amount;

    private PlacementInfo ingredientPlacement;
    private ItemStack output;
    public static final StreamCodec<ByteBuf, List<String>> STRING_LIST_CODEC =
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list());

    public AnvilShapingRecipe(Ingredient input, Item outputItem, int amount) {
        this.outputItem = outputItem;
        this.input = input;
        this.amount = amount;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.TREATED_ANVIL);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level world) {
        if(input.item().isEmpty()) return false;
        return this.input.test(input.item());
    }

    public ItemStack getOutput() {
        if (output == null) {
            output = new ItemStack(outputItem);
        }
        return output;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        return getOutput().copy();
    }

    public ItemStack craft(SingleRecipeInput input, HolderLookup.Provider lookup) {
        return getOutput().copy();
    }

    public Ingredient getIngredient() {
        return this.input;
    }

    public int getAmount() {
        return this.amount;
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
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = PlacementInfo.create(this.input);
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Type implements RecipeType<AnvilShapingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_shaping";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer {
        public static final String ID = "anvil_shaping";

        private static final MapCodec<AnvilShapingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("output").forGetter(recipe -> recipe.outputItem),
                Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.amount)
        ).apply(instance, AnvilShapingRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, AnvilShapingRecipe> STREAM_CODEC = StreamCodec.of(Serializer::write, Serializer::read);

        public static final RecipeSerializer<AnvilShapingRecipe> INSTANCE = new RecipeSerializer<>(CODEC, STREAM_CODEC);

        private static AnvilShapingRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Item outputItem = BuiltInRegistries.ITEM.getValue(Identifier.parse(buf.readUtf()));
            int amount = ByteBufCodecs.INT.decode(buf);
            return new AnvilShapingRecipe(input, outputItem, amount);
        }

        private static void write(RegistryFriendlyByteBuf buf, AnvilShapingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input);
            buf.writeUtf(BuiltInRegistries.ITEM.getKey(recipe.outputItem).toString());
            ByteBufCodecs.INT.encode(buf, recipe.amount);
        }
    }
}
