package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @WrapOperation(method = "canEntityWalkOnPowderSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z", ordinal = 0))
    private static boolean canWalkOnPowderSnowTag(ItemStack instance, Object item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "powder_snow_walk_on")));
    }

    @Inject(method = "canEntityWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnowEntity(Entity entity, final CallbackInfoReturnable<Boolean> info) {
        if(entity instanceof LivingEntity livingEntity){
            AttributeMap container = livingEntity.getAttributes();
            if(container.hasAttribute(EntityAttributesME.POWDERED_SNOW_IMMUNITY) && container.getValue(EntityAttributesME.POWDERED_SNOW_IMMUNITY) != 0.0){
                info.setReturnValue(true);
            }
        }
    }
}
