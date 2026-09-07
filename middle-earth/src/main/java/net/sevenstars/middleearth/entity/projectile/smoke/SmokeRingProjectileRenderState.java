package net.sevenstars.middleearth.entity.projectile.smoke;

import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import org.joml.Quaternionf;

public class SmokeRingProjectileRenderState extends ArrowRenderState {
    public Quaternionf orientationQuat;
    public int maxLifespan;
    public boolean failed;
}
