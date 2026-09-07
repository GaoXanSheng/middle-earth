package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
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

        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setOffHandStack(offHandStack);
        ((ArmedEntityRenderStateAccess)livingEntityRenderState).setRestrained(restrained);
    }
}
