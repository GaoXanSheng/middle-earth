package net.sevenstars.middleearth.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class ModParticleTypes {

    public static final SimpleParticleType ANVIL_SPARK_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType BIOME_FOG_PARTICLE = FabricParticleTypes.simple(true);

    public static void registerParticleTypes(){
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "anvil_spark_particles"), ANVIL_SPARK_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "biome_fog_particles"), BIOME_FOG_PARTICLE);
    }

}
