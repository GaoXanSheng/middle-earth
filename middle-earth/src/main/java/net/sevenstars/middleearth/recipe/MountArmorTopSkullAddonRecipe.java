package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.dataComponents.MountArmorAddonComponent;
import net.sevenstars.middleearth.utils.ItemTagsME;

public class MountArmorTopSkullAddonRecipe extends CustomRecipe {

    private static final MountArmorTopSkullAddonRecipe INSTANCE = new MountArmorTopSkullAddonRecipe();
    public static final MapCodec<MountArmorTopSkullAddonRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, MountArmorTopSkullAddonRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<MountArmorTopSkullAddonRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public MountArmorTopSkullAddonRecipe() {
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        ItemStack itemStackArmor = ItemStack.EMPTY;
        ItemStack itemStackStick = ItemStack.EMPTY;
        ItemStack itemStackSkull= ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR) || itemStack2.is(EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR)) {
                    if (!itemStackArmor.isEmpty()) {
                        return false;
                    }
                    itemStackArmor = itemStack2;
                }
                else if (itemStack2.is(Items.STICK)) {
                    if (!itemStackStick.isEmpty()) {
                        return false;
                    }
                    itemStackStick = itemStack2;
                }
                else if (itemStack2.is(Items.SKELETON_SKULL)) {
                    if (!itemStackSkull.isEmpty()) {
                        return false;
                    }
                    itemStackSkull = itemStack2;
                }
            }
        }
        return !itemStackArmor.isEmpty() && !itemStackStick.isEmpty() && !itemStackSkull.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack itemStack = ItemStack.EMPTY;

        for(int i = 0; i < input.size(); ++i) {
            ItemStack itemStack2 = input.getItem(i);
            if (!itemStack2.isEmpty()) {
                if (itemStack2.is(ItemTagsME.WARG_ARMORS)) {
                    if (!itemStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    itemStack = itemStack2.copy();
                }
            }
        }

        boolean sideArmorAddons = false;
        if(itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA) != null) {
            sideArmorAddons = itemStack.get(DataComponentTypesME.MOUNT_ARMOR_DATA).sideArmorAddon();
        }

        ItemStack output = itemStack.copyWithCount(1);

        output.set(DataComponentTypesME.MOUNT_ARMOR_DATA, new MountArmorAddonComponent(true, sideArmorAddons));

        return output;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return ModRecipeSerializer.CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON;
    }
}
