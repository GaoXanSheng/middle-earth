package net.sevenstars.middleearth.particles.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;

public class BiomeFogParticle extends SingleQuadParticle {

    private final BlockPos spawnPos;

    private BiomeFogParticle(ClientLevel clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ, SpriteSet sprites) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0, sprites.first());
        this.setSprite(sprites.get(this.random));
        this.setLifetime(Mth.nextInt(this.random, 300, 400));
        this.scale(16.0F);
        this.setAlpha(0.0F);

        this.spawnPos = new BlockPos((int) d, (int) e, (int) f);

        this.xd = velocityX + (double) (this.random.nextFloat() / 750.0F);
        this.yd = velocityY;
        this.zd = velocityZ;
    }

    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public void tick() {
        if (this.spawnPos.getY() > level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.spawnPos.getX(), this.spawnPos.getZ()) + 1) {
            this.remove();
        }

        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.alpha < 0.15F) {
            this.setAlpha(getFadeAmount(this.getLifetimeProgress((float) this.age), 0.3F, 0.5F));
        }

        if (this.age++ < this.lifetime) {
            this.xd -= 0.00005F;
            this.yd += this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.zd += this.random.nextFloat() / 5000.0F * (float) (this.random.nextBoolean() ? 1 : -1);
            this.move(this.xd, this.yd, this.zd);
        } else {
            this.remove();
        }
    }

    private float getLifetimeProgress(float age) {
        return Mth.clamp(age / this.lifetime, 0.0F, 1.0F);
    }

    private static float getFadeAmount(float lifetimeProgress, float fadeIn, float fadeOut) {
        if (lifetimeProgress >= 1.0F - fadeIn) {
            return (1.0F - lifetimeProgress) / fadeIn;
        } else {
            return lifetimeProgress <= fadeOut ? lifetimeProgress / fadeOut : 1.0F;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
            return new BiomeFogParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
