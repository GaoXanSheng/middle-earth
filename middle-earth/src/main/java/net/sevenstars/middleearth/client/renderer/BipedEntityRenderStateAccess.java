package net.sevenstars.middleearth.client.renderer;

import net.minecraft.world.phys.Vec3;

public interface BipedEntityRenderStateAccess {
    float getTickProgress();
    Vec3 getPreviousVelocity();
    Vec3 getVelocity();
    void setTickProgress(float tickProgress);
    void setPreviousVelocity(Vec3 velocity);
    void setVelocity(Vec3 velocity);
}
