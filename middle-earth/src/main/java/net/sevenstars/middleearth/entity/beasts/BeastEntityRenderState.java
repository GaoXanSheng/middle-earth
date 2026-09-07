package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class BeastEntityRenderState extends LivingEntityRenderState {
    public AnimationState startSittingAnimationState = new AnimationState();
    public AnimationState stopSittingAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();

    public int tameness = 0;

    public boolean isSprinting = false;
    public boolean isCharging = false;
    public boolean isTame = false;
    public LivingEntity conrollingPassenger = null;

    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
}
