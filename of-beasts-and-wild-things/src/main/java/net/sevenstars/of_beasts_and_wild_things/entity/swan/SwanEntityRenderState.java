package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class SwanEntityRenderState extends LivingEntityRenderState {
    public SwanEntityVariant variant;
    public AnimationState swimmingAnimationState = new AnimationState();
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState intimidateAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
    public AnimationState swimIdleAnimationState = new AnimationState();
    public AnimationState flapAnimationState = new AnimationState();

    public SwanEntityRenderState() {
        variant = SwanEntityVariant.WHITE;
    }
}
