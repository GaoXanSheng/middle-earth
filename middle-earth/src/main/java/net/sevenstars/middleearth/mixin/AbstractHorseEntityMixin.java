package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.FoodItemsME;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseEntityMixin {

    @Shadow
    @Nullable
    public abstract LivingEntity getControllingPassenger();
    @Shadow
    @Nullable
    protected SimpleContainer inventory;

    @Shadow
    protected abstract void dropEquipment(ServerLevel world);

    @WrapOperation(method = "handleEating", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z", ordinal = 3))
    private boolean receiveFoodLettuce(ItemStack instance, Object item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.LETTUCE);
    }

    @WrapOperation(method = "handleEating", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z", ordinal = 5))
    private boolean receiveFoodHorseFeed(ItemStack instance, Object item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.SACK_OF_HORSEFEED);
    }

    @Inject(at = @At("HEAD"), method = "dropEquipment", cancellable = true)
    protected void dropInventory(ServerLevel world, CallbackInfo ci) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            this.inventory.clearContent();
            ci.cancel();
        }
    }
}
