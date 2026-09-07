package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;

public class BackAttachmentRemovalRecipe extends CustomRecipe {

    private static final BackAttachmentRemovalRecipe INSTANCE = new BackAttachmentRemovalRecipe();
    public static final MapCodec<BackAttachmentRemovalRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, BackAttachmentRemovalRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<BackAttachmentRemovalRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public BackAttachmentRemovalRecipe() {
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> defaultedList = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = input.getItem(i);
            ItemStackTemplate remainderTemplate = itemStack.getItem().getCraftingRemainder();
            if (remainderTemplate != null && !remainderTemplate.create().isEmpty()) {
                defaultedList.set(i, remainderTemplate.create());
            } else if (itemStack.getItem() instanceof ShearsItem) {
                defaultedList.set(i, itemStack.copyWithCount(1));
            } else if (itemStack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null){
                ItemStack backAttachment = new ItemStack(BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, itemStack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachment().getName())));
                backAttachment.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, itemStack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA));
                backAttachment.set(DataComponents.DYED_COLOR, new DyedItemColor(itemStack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachmentColor()));
                defaultedList.set(i, backAttachment);
            }
        }

        return defaultedList;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackShears = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && itemStack2.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!itemStack2.is(Items.SHEARS)) {
                        return false;
                    }
                    itemStackShears = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackShears.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem && itemStack2.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!itemStack2.is(Items.SHEARS)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
        }

        if (!itemStack.isEmpty()) {
            itemStack.remove(DataComponentTypesME.BACK_ATTACHMENT_DATA);
            return itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL;
    }
}
