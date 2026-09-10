package net.sevenstars.middleearth.world.biomes.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.phys.Vec2;
import net.sevenstars.middleearth.utils.noises.BlendedNoise;
import net.sevenstars.middleearth.utils.noises.SimplexNoise;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.caves.CaveType;
import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.chunkgen.ProceduralStructures;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.middleearth.world.map.MiddleEarthMapRuntime;
import java.util.List;
import java.util.stream.Stream;

public class ModBiomeSource extends BiomeSource {

    public static final MapCodec<ModBiomeSource> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Biome.CODEC).fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomes)).apply(instance, ModBiomeSource::new));

    private final List<Holder<Biome>> biomes;
    private static final int CAVE_NOISE = 360;
    private static final int CAVE_OFFSET = 7220;
    public static final int SUB_BIOME_NOISE = 256;
    public static final int SUB_BIOME_OFFSET = 8240;
    private MiddleEarthMapRuntime middleEarthMapRuntime;
    public ModBiomeSource(List<Holder<Biome>> biomes) {
        this.biomes = biomes;
        middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return biomes.stream();
    }

    private ResourceKey<Biome> getCaveBiome(int x, int z, BiomeData surfaceBiome) {
        x += MiddleEarthHeightMap.getSeed();
        z += MiddleEarthHeightMap.getSeed();
        float temperature = (float) SimplexNoise.noise((double) x / CAVE_NOISE,  (double) z / CAVE_NOISE);
        float humidity = (float) SimplexNoise.noise((double) (x + CAVE_OFFSET) / CAVE_NOISE, (double)(z + CAVE_OFFSET) / CAVE_NOISE);
        return ModCaveBiomes.getBiome(new Vec2(temperature, humidity), surfaceBiome);
    }

    public static double getSubBiomeNoise(int x, int z, float frequency) {
        x += MiddleEarthHeightMap.getSeed();
        z += MiddleEarthHeightMap.getSeed();
        float noiseFrequency = (SUB_BIOME_NOISE * frequency);
        double perlin = 1 * BlendedNoise.noise((double) x / noiseFrequency, (double) z / noiseFrequency);
        perlin += 0.5f * BlendedNoise.noise((double) x * 2 / noiseFrequency, (double) z * 2 / noiseFrequency);
        perlin = perlin / (1 + 0.5f); // 2 octaves
        return perlin;
    }

    private ResourceKey<Biome> getSubBiome(int x, int z, BiomeData surfaceBiome) {
        SubBiome subBiome = SubBiomes.getSubBiome(surfaceBiome.getBiomeRegistryKey());
        if(subBiome != null) {
            double perlin = getSubBiomeNoise(x, z, subBiome.getFrequency());
            SubBiome.SubBiomeData biomeData = SubBiomes.subBiomesMap.get(surfaceBiome.getBiomeRegistryKey()).getBiomeAtNoise((float) perlin);
            if (biomeData != null) return biomeData.biome;
        }
        return surfaceBiome.getBiomeRegistryKey();
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler noise) {
        int i = QuartPos.toBlock(x);
        int j = QuartPos.toBlock(y);
        int k = QuartPos.toBlock(z);

        MapBasedCustomBiome biomeHeightData = middleEarthMapRuntime.getBiome(i, k);
        
        if (biomeHeightData == null) {
            return biomes.get(0);
        }

        BiomeData biome = biomeHeightData.getBiome();
        ResourceKey<Biome> processedBiome;

        float height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(i, k);
        if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
            processedBiome = MEBiomeKeys.MITHRIL_CAVE;
        } else if(j < (height - 16)) {
            processedBiome = getCaveBiome(i, k, biome);
        }
        else if(!MapBasedBiomePool.waterBiomes.contains(biome.getBiomeRegistryKey())) {
            SubBiome subBiome = SubBiomes.getSubBiome(biomeHeightData.getBiomeKey());
            if(subBiome != null) {
                double perlin = ModBiomeSource.getSubBiomeNoise(i, k, subBiome.getFrequency());
                double additionalHeight = subBiome.getAdditionalHeight((float) perlin);
                additionalHeight *= MiddleEarthMapRuntime.getInstance().getEdge(i, k);
                height += (float) additionalHeight;
            }
            ResourceKey<Biome> biomeRegistryKey = biome.getBiomeRegistryKey();
            if(j <= CavesPlacedFeatures.MAX_MITHRIL_HEIGHT && biome.getCaveType() == CaveType.MISTIES) {
                processedBiome = MEBiomeKeys.MITHRIL_CAVE;
            } else if(biomeRegistryKey == MapBasedBiomePool.deadMarshes.getBiomeKey() || biomeRegistryKey == MapBasedBiomePool.deadMarshesWater.getBiomeKey()) {
                height = MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthChunkGenerator.getMarshesHeight(i, k, height);
                if(j < (height - 20)) processedBiome = getCaveBiome(i, k, biome);
                else if(height < MiddleEarthChunkGenerator.WATER_HEIGHT) processedBiome = MapBasedBiomePool.deadMarshesWater.getBiomeKey();
                else processedBiome = MapBasedBiomePool.deadMarshes.getBiomeKey();
            } else if(height <= biomeHeightData.getWaterHeight() + 1.25f) {
                // Shoreline swap threshold: the 1.25 margin is a fixed tuning value; a
                // noise-driven margin (e.g. from the subBiome perlin above) would make shore
                // widths vary, but needs in-game comparison against the reference map first.
                if(MapBasedBiomePool.coastalBiomes.contains(biomeRegistryKey)){
                    processedBiome = MapBasedBiomePool.oceanCoast.getBiomeKey();
                } else if(MapBasedBiomePool.wastePondBiomes.contains(biomeRegistryKey)) {
                    processedBiome = MapBasedBiomePool.wastePond.getBiomeKey();
                } else if(MapBasedBiomePool.mirkwoodSwampBiomes.contains(biomeRegistryKey)) {
                    processedBiome = MapBasedBiomePool.mirkwoodSwamp.getBiomeKey();
                } else if(MapBasedBiomePool.oasisBiomes.contains(biomeRegistryKey)) {
                    processedBiome = MapBasedBiomePool.oasis.getBiomeKey();
                } else if(MapBasedBiomePool.frozenBiomes.contains(biomeRegistryKey)) {
                    processedBiome = MapBasedBiomePool.frozenPond.getBiomeKey();
                } else if(MapBasedBiomePool.anduinWaterBiomes.contains(biomeRegistryKey)){
                    processedBiome = MapBasedBiomePool.greatRiver.getBiomeKey();
                } else if(MapBasedBiomePool.mangrovePondBiomes.contains(biomeRegistryKey)){
                    processedBiome = MapBasedBiomePool.mangrovePond.getBiomeKey();
                } else {
                    processedBiome = MapBasedBiomePool.pond.getBiomeKey();
                }
            } else if(biome.getBiomeRegistryKey().isFor(MEBiomeKeys.NAN_CURUNIR.registryKey()) && ProceduralStructures.isInsideIsengard(i, k)) {
                processedBiome = MEBiomeKeys.ISENGARD;
            } else {
                processedBiome = getSubBiome(i, k, biome);
            }
        } else processedBiome = biome.getBiomeRegistryKey();

        return biomes.stream().filter(
                        b -> b.unwrapKey().get().toString().equalsIgnoreCase(processedBiome.toString()))
                .findFirst().get();
    }
}
