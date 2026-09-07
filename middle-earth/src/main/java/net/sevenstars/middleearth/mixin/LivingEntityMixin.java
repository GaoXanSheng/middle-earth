package net.sevenstars.middleearth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.items.shields.CustomSiegeShieldItem;
import net.sevenstars.middleearth.item.items.weapons.ReachWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.network.packets.S2C.PacketLivingEntityData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    private static boolean stackTraceLock = false;

    @Shadow @Final protected EntityEquipment equipment;

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    @Shadow public abstract ItemStack getItemInHand(InteractionHand hand);

    @Shadow public abstract boolean hasEffect(Holder<MobEffect> effect);

    @Shadow public abstract @NotNull ItemStack getWeaponItem();

    @Shadow
    protected abstract float getFrictionInfluencedSpeed(float slipperiness);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "getVisibilityPercent", at = @At("RETURN"), cancellable = true)
    public void getAttackDistanceScalingFactor(Entity entity, CallbackInfoReturnable<Double> cir) {
        if (entity != null) {
            ItemStack chestplate = this.getItemBySlot(EquipmentSlot.CHEST);
            if(!chestplate.isEmpty()) {
                Holder<Enchantment> enchantmentRegistryEntry = entity.level().registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.STEALTHY_TRAIL).orElseThrow();
                boolean hasEnchant = chestplate.getEnchantments().keySet().contains(enchantmentRegistryEntry);
                int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistryEntry, chestplate);
                if(hasEnchant) {
                    double original = cir.getReturnValue();
                    double viewDistance = original + Math.max(-0.9f, -0.2f * level);
                    cir.setReturnValue(viewDistance);
                }
            }
        }
    }

    @Inject(method = "getItemBySlot", at = @At("HEAD"), cancellable = true)
    public void getEquippedStack(EquipmentSlot slot, CallbackInfoReturnable<ItemStack> cir) {
        if(stackTraceLock) return;
        stackTraceLock = true;
        boolean twoHanded = false;
        ItemStack stackMainHand = this.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack stackOffHand = this.getItemInHand(InteractionHand.OFF_HAND);

        if(stackMainHand != null){
            if ((stackMainHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackMainHand.getItem()).type.twoHanded))
                    || (stackMainHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackMainHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }
        if(stackOffHand != null){
            if ((stackOffHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackOffHand.getItem()).type.twoHanded))
                    || (stackOffHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackOffHand.getItem() instanceof CustomLongbowWeaponItem)) {
                twoHanded = true;
            }
        }

        if (slot == EquipmentSlot.OFFHAND) {
            if (twoHanded) {
                cir.setReturnValue(ItemStack.EMPTY);
                cir.cancel();
            }
        }
        stackTraceLock = false;
    }

    @ModifyVariable(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"), index = 1, argsOnly = true)
    public final MobEffectInstance addStatusEffect(MobEffectInstance effect) {
        if(effect.getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL)) {
            float ailmentLevel = 0;
            Level world = this.level();
            ailmentLevel += getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.HEAD), world);
            ailmentLevel += getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.CHEST), world);
            ailmentLevel += getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.LEGS), world);
            ailmentLevel += getAilmentProtectionLevel(this.getItemBySlot(EquipmentSlot.FEET), world);
            if(ailmentLevel > 0) {
                float scale = 0.08f * ailmentLevel;
                scale = 1 - Math.min(0.8f, scale);
                effect = effect.withScaledDuration(scale);
            }
        }
        return effect;
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("TAIL"))
    public final void addStatusEffect(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if(!this.level().isClientSide()) {
            for (ServerPlayer player : ((ServerLevel) this.level()).players()) {
                ServerPlayNetworking.send(player, new PacketLivingEntityData(this.getId(), effect));
            }
        }
    }

    private static int getAilmentProtectionLevel(ItemStack itemStack, Level world) {
        int level = 0;
        Holder<Enchantment> enchantmentRegistryEntry = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .get(EnchantmentsME.AILMENT_PROTECTION).orElseThrow();
        boolean hasEnchant = itemStack.getEnchantments().keySet().contains(enchantmentRegistryEntry);
        if(hasEnchant) {
            level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistryEntry, itemStack);
        }
        return level;
    }

    @Inject(at = @At("HEAD"), method = "dropAllDeathLoot")
    protected void drop(ServerLevel world, DamageSource damageSource, CallbackInfo callbackInfo) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            this.equipment.clear();
        }
    }

    @Inject(method = "getSpeed()F", at = @At("RETURN"), cancellable = true)
    private void getMovementSpeed(CallbackInfoReturnable<Float> cir) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity npcEntity){
            float currentValue = cir.getReturnValue();
            float modifier = 1f;
            float fightingModifier = 1f;
            if(getControllingPassenger().getVehicle() instanceof Horse){
                currentValue = 0.5f;
                fightingModifier = 2f;
            }
            if(npcEntity.isFighting()){
                cir.setReturnValue(Math.max(currentValue * fightingModifier, 0.5f));
            }
            cir.setReturnValue(Math.max(currentValue * modifier, 0.25f));
        }
    }
    @WrapOperation(method = "handleOnClimbable", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean ScaffoldingDescendLogic(BlockState state, Object block, Operation<Boolean> original) {
        return original.call(state, block)
                || block == Blocks.SCAFFOLDING && state.is(ModDecorativeBlocks.REINFORCED_SCAFFOLDING);
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void onRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        if (!(((Entity)this) instanceof LivingEntity livingEntity)) {
            return;
        }
        BiomeEventDataLookup.removeEntity(livingEntity.getType(), livingEntity.getUUID());
    }
}
