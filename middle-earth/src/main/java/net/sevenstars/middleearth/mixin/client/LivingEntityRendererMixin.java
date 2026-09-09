package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {
    @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V")
    private <T extends LivingEntity, S extends LivingEntityRenderState>
    void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = livingEntity.getMainHandItem();
        ItemStack offHandStack = livingEntity.getOffhandItem();

        boolean restrained = false;
        if(livingEntity.hasEffect(ModStatusEffects.RESTRAINED)) {
            if(livingEntity.getEffect(ModStatusEffects.RESTRAINED).getDuration() > 0) {
                restrained = true;
            }
        }

        boolean spearIdle = false;
        if(!livingEntity.isUsingItem()) {
            if(livingEntity instanceof Player player && PlayerMovementData.readAFK((IEntityDataSaver) player) > 60) {
                spearIdle = true;
            } else if(livingEntity instanceof Mob mob && mob.isNoAi()) {
                spearIdle = true;
            }
        }

        ArmedEntityRenderStateAccess state = (ArmedEntityRenderStateAccess)livingEntityRenderState;
        state.setMainHandStack(mainHandStack);
        state.setOffHandStack(offHandStack);
        state.setRestrained(restrained);
        state.setSpearIdle(spearIdle);
    }
}
