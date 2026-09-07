package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.utils.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract ItemCooldowns getCooldowns();

    @Shadow public abstract Inventory getInventory();

    @Shadow protected float hurtDir;
    int climbDistance = 0;
    //TODO Shield stuff broken, most likely because of new data comps
    //@Shadow protected abstract void takeShieldHit(LivingEntity attacker);

    //@Shadow public abstract boolean canUseSlot(EquipmentSlot slot);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;onAttack()V",
    shift = At.Shift.AFTER), ordinal = 0)
    public float attack(float damage, Entity target) {
        float newDamage = damage;
        ItemStack mainStack = getItemInHand(getUsedItemHand());
        Holder<Enchantment> enchantmentRegistryEntry = level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.FIRST_STRIKE).orElseThrow();
        boolean hasEnchant = mainStack.getEnchantments().keySet().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            if(target instanceof LivingEntity livingEntity) {
                float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
                if(healthRatio > 0.9f) {
                    newDamage *= 1.5f;
                }
            }
        }
        if(mainStack.getItem() instanceof CustomDaggerWeaponItem) { // TODO config
            if(CustomDaggerWeaponItem.canBackStab(target, this)) {
                newDamage *= 1.75f;
            }
            if(CustomDaggerWeaponItem.canSneakAttack(mainStack)) {
                newDamage *= 1.5f;
            }
        }
        return newDamage;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
    }

    @Inject(method = "travel", at = @At("HEAD"))
    public void travel(CallbackInfo ci, @Local Vec3 movementInput) {
        if(movementInput.length() > 0.01f) {
            PlayerMovementData.resetAFK((IEntityDataSaver) this);
        }
    }

/*
    @Inject(method = "attack", at = @At("HEAD"))
    public void attack(Entity target, CallbackInfo ci) {
        PlayerMovementData.resetAFK((IEntityDataSaver) this);
    }

    @ModifyVariable(method = "attack", ordinal = 3, at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0))
    public float attackBackStab(float value, Entity target) {

        return value;
    }*/

    @Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"))
    public void resetLastAttackedTicks(CallbackInfo ci) {
        PlayerMovementData.resetAFK((IEntityDataSaver) this);
    }

    @Inject(method = "createAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void createPlayerAttributesInject(final CallbackInfoReturnable<AttributeSupplier.Builder> info){
        info.getReturnValue().add(EntityAttributesME.POWDERED_SNOW_IMMUNITY);
        info.getReturnValue().add(EntityAttributesME.DELVERS_FEAR_STRENGTH);
        info.getReturnValue().add(EntityAttributesME.CLIMBING_STRENGTH);
        info.getReturnValue().add(EntityAttributesME.DETECTION_RANGE);

    }

    @Inject(method = "onClimbable", at = @At("HEAD"), cancellable = true)
    private void isClimbingInject(CallbackInfoReturnable<Boolean> cir) {
        if ((LivingEntity)this instanceof Player entity){
            if(!entity.isInWater() && !entity.onGround() && PlayerUtil.isAgainstWall(entity)){
                climbDistance += 1;
                if(climbDistance < entity.getAttributeValue(EntityAttributesME.CLIMBING_STRENGTH)){
                    cir.setReturnValue(true);
                }
            } else if(entity.onGround()){
                climbDistance = 0;
            }
        }
    }
}
