package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.BipedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class BipedEntityRenderStateMixin extends ArmedEntityRenderState implements BipedEntityRenderStateAccess {
    @Unique private float tickProgress;
    @Unique private Vec3 previousVelocity;
    @Unique private Vec3 velocity;

    @Override
    public float getTickProgress() {
        return tickProgress;
    }

    @Override
    public Vec3 getPreviousVelocity() {
        return previousVelocity;
    }

    @Override
    public Vec3 getVelocity() {
        return velocity;
    }

    @Override
    public void setTickProgress(float tickProgress) {
        this.tickProgress = tickProgress;
    }

    @Override
    public void setPreviousVelocity(Vec3 previousVelocity) {
        this.previousVelocity = previousVelocity;
    }

    @Override
    public void setVelocity(Vec3 velocity) {
        this.velocity = velocity;
    }
}
