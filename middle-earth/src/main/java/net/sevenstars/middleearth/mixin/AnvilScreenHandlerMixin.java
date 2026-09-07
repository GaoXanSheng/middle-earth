package net.sevenstars.middleearth.mixin;

import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.sevenstars.middleearth.config.ModServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public class AnvilScreenHandlerMixin {
    @Redirect(method = "createResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V"))
    private void interceptAnvilOutput(ResultContainer outputInventory, int slot, ItemStack stack) {
        if (!stack.isEmpty()) {
            var enchants = EnchantmentHelper.getEnchantmentsForCrafting(stack);
            for (var entry : enchants.entrySet()) {
                var enchant = entry.getKey();
                if ((enchant.is(Enchantments.SHARPNESS) && entry.getIntValue() >= ModServerConfigs.SHARPNESS_MAX_LEVEL)
                        || (enchant.is(Enchantments.POWER) && entry.getIntValue() >= ModServerConfigs.POWER_MAX_LEVEL)) {
                    outputInventory.setItem(slot, ItemStack.EMPTY);
                    return;
                }
            }
        }
        outputInventory.setItem(slot, stack);
    }
}
