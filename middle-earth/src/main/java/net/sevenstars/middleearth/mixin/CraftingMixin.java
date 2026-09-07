package net.sevenstars.middleearth.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CraftingMenu.class)
public class CraftingMixin {

    @Inject(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 0),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void updateResultCancel(AbstractContainerMenu handler, ServerLevel world, Player player,
                                           CraftingContainer craftingInventory, ResultContainer resultInventory,
                                           @Nullable RecipeHolder<CraftingRecipe> recipe, CallbackInfo ci, CraftingInput craftingRecipeInput,
                                           ServerPlayer serverPlayerEntity, ItemStack itemStack){
        if (world.isClientSide()){
            return;
        }
        if (!ModServerConfigs.ENABLE_GOLDEN_FOOD_RECIPES && ModDimensions.isInMiddleEarth(world)
                && (itemStack.is(Items.GOLDEN_APPLE) || itemStack.is(Items.GOLDEN_CARROT))){
            itemStack = new ItemStack(Items.AIR);
        }
        resultInventory.setItem(0, itemStack);
        ci.cancel();
    }
}