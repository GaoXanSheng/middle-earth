package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.sevenstars.middleearth.item.items.shields.CustomShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @WrapOperation(
            method = "playerHasBlockingItemUseIntent(Lnet/minecraft/world/item/context/UseOnContext;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"
            )
    )
    private static boolean shield_api$shouldCancelStripAttempt(ItemStack instance, Object item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.getItem() instanceof CustomShieldItem;
    }
}
