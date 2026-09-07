package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.BackAttachmentItem;
import net.sevenstars.middleearth.item.items.armor.CustomChestplateItem;

public class BackAttachmentRecipe extends CustomRecipe {

    private static final BackAttachmentRecipe INSTANCE = new BackAttachmentRecipe();
    public static final MapCodec<BackAttachmentRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, BackAttachmentRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<BackAttachmentRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public BackAttachmentRecipe() {
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStackChest = ItemStack.EMPTY;
        ItemStack itemStackBackAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStackChest.isEmpty()) {
                        return false;
                    }
                    if (itemStack2.get(DataComponentTypesME.BACK_ATTACHMENT_DATA) != null){
                        return false;
                    }
                    itemStackChest = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return false;
                    }
                    itemStackBackAttachment = itemStack2;
                }
            }
        }
        return !itemStackChest.isEmpty() && !itemStackBackAttachment.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack backAttachment = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomChestplateItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof BackAttachmentItem)) {
                        return ItemStack.EMPTY;
                    }
                    backAttachment = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (backAttachment.get(DataComponents.DYED_COLOR) != null){
                color = backAttachment.get(DataComponents.DYED_COLOR).rgb();
            } else {
                color = 0;
            }
            return BackAttachmentDataComponent.setBackAttachmentWithColor(itemStack,
                    backAttachment.get(DataComponentTypesME.BACK_ATTACHMENT_DATA).backAttachment(),
                    color);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_BACK_ATTACHMENT;
    }
}
