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
import net.sevenstars.middleearth.item.dataComponents.HelmetAttachmentDataComponent;
import net.sevenstars.middleearth.item.items.armor.CustomHelmetItem;
import net.sevenstars.middleearth.item.items.armor.HelmetAttachmentItem;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;

public class HelmetAttachmentRecipe extends CustomRecipe {

    private static final HelmetAttachmentRecipe INSTANCE = new HelmetAttachmentRecipe();
    public static final MapCodec<HelmetAttachmentRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, HelmetAttachmentRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<HelmetAttachmentRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public HelmetAttachmentRecipe() {
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStackHelmet = ItemStack.EMPTY;
        ItemStack itemStackHood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStackHelmet.isEmpty()) {
                        return false;
                    }
                    if (itemStack2.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA) != null){
                        return false;
                    }
                    itemStackHelmet = itemStack2;
                } else {
                    if (!(itemStack2.getItem() instanceof HelmetAttachmentItem)) {
                        return false;
                    }
                    itemStackHood = itemStack2;
                }
            }
        }
        return !itemStackHelmet.isEmpty() && !itemStackHood.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack hood = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.getItem() instanceof CustomHelmetItem) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    itemStack = itemStack2.copy();
                } else {
                    if (!(itemStack2.getItem() instanceof HelmetAttachmentItem)) {
                        return ItemStack.EMPTY;
                    }
                    hood = itemStack2;
                }
            }
        }

        if (!itemStack.isEmpty()) {
            int color;
            if (hood.get(DataComponents.DYED_COLOR) != null){
                color = hood.get(DataComponents.DYED_COLOR).rgb();
            } else {
                color = 0;
            }
            if (hood.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachment().getConstantState() == HelmetAttachmentsStatesME.DOWN){
                return HelmetAttachmentDataComponent.setHelmetAttachmentWithcolor(itemStack, true, hood.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachment(), color);
            } else {
                return HelmetAttachmentDataComponent.setHelmetAttachmentWithcolor(itemStack, false, hood.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA).helmetAttachment(), color);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_ARMOR_HELMET_ATTACHMENT;
    }
}
