package net.sevenstars.middleearth.recipe;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.sevenstars.middleearth.MiddleEarth;

public class ModRecipeSerializer {

    public static final RecipeSerializer<BackAttachmentRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT = register("custom_armor_back_attachment", BackAttachmentRecipe.SERIALIZER);
    public static final RecipeSerializer<BackAttachmentRemovalRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL = register("custom_armor_back_attachment_removal", BackAttachmentRemovalRecipe.SERIALIZER);
    public static final RecipeSerializer<HelmetAttachmentRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT = register("custom_armor_helmet_attachment", HelmetAttachmentRecipe.SERIALIZER);
    public static final RecipeSerializer<HelmetAttachmentRemovalRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT_REMOVAL = register("custom_armor_helmet_attachment_removal", HelmetAttachmentRemovalRecipe.SERIALIZER);
    public static final RecipeSerializer<MountArmorAddonRemovalRecipe> CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL = register("custom_mount_armor_addon_removal", MountArmorAddonRemovalRecipe.SERIALIZER);
    public static final RecipeSerializer<MountArmorSideSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON = register("custom_mount_armor_side_skull_addon", MountArmorSideSkullAddonRecipe.SERIALIZER);
    public static final RecipeSerializer<MountArmorTopSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON = register("custom_mount_armor_top_skull_addon", MountArmorTopSkullAddonRecipe.SERIALIZER);

    public static final RecipeSerializer<CustomItemDecorationRecipe> CUSTOM_ITEM_DECORATION = register("custom_item_decoration", CustomItemDecorationRecipe.SERIALIZER);

    static <T extends Recipe<?>> RecipeSerializer<T> register(String id, RecipeSerializer<T> serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, id), serializer);
    }

    public static void registerRecipeSerializers(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Recipe Serializers for " + MiddleEarth.MOD_ID);
    }
}
