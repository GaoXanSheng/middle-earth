package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.BipedEntityRenderStateAccess;
import net.sevenstars.middleearth.entity.spider.EnwebbedFeatureRenderer;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidMobRenderer.class)
public abstract class BipedEntityRendererMixin<T extends Mob, S extends HumanoidRenderState, M extends HumanoidModel<S>>
        extends AgeableMobRenderer<T, S, M> {

    public BipedEntityRendererMixin(EntityRendererProvider.Context context, M model, M babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Lnet/minecraft/client/model/HumanoidModel;Lnet/minecraft/client/model/HumanoidModel;FLnet/minecraft/client/renderer/entity/layers/CustomHeadLayer$Transforms;)V")
    public <S extends HumanoidRenderState, M extends HumanoidModel<S>>
    void BipedEntityRenderer(EntityRendererProvider.Context context, HumanoidModel model, HumanoidModel babyModel,
                             float scale, CustomHeadLayer.Transforms headTransformation, CallbackInfo ci) {

        this.addLayer(new EnwebbedFeatureRenderer<>(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/Mob;Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;F)V")
    private <T extends Mob, S extends HumanoidRenderState>
    void positionRightArm(T mobEntity, S bipedEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = mobEntity.getMainHandItem();
        ItemStack offHandStack = mobEntity.getOffhandItem();
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setOffHandStack(offHandStack);
    }

    @Inject(at = @At("TAIL"), method = "extractHumanoidRenderState")
    private static <T extends LivingEntity, S extends LivingEntityRenderState>
    void updateRenderState(LivingEntity entity, HumanoidRenderState state, float tickProgress, ItemModelResolver itemModelResolver, CallbackInfo ci) {
        Vec3 velocity = entity.getDeltaMovement();
        BipedEntityRenderStateAccess stateAccess = ((BipedEntityRenderStateAccess)state);
        stateAccess.setTickProgress(tickProgress);
        stateAccess.setPreviousVelocity(stateAccess.getVelocity());
        stateAccess.setVelocity(velocity);
    }

    @Override
    public Identifier getTextureLocation(S state) {
        return null;
    }

    @Override
    public S createRenderState() {
        return null;
    }
}
