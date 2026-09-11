package net.sevenstars.middleearth.world.roads;

import net.minecraft.util.Mth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Deterministic sampling of the {@link RoadNetworkData} bezier graph into world space.
 * Heights are relative units (surface top = DIRT_HEIGHT + height), matching MiddleEarthHeightMap.getHeight.
 * Built lazily exactly once and read-only afterwards: safe to query from parallel chunk worker threads.
 */
public final class RoadNetwork {
    public static final int BUCKET_SIZE = 512;
    public static final float INFLUENCE_MULTIPLIER = 2.5f;
    private static final int SAMPLE_SPACING = 4;
    private static final int SMOOTHING_WINDOW = 5;
    private static final float WATER_LEVEL = MapBasedCustomBiome.DEFAULT_WATER_HEIGHT;
    private static final float BRIDGE_REL_HEIGHT = 1.0f;
    private static final float DEEP_WATER_REL_HEIGHT = -25f;
    private static volatile RoadNetwork instance;
    private final ConcurrentHashMap<Long, List<Sample>> buckets;

    private RoadNetwork(ConcurrentHashMap<Long, List<Sample>> buckets) {
        this.buckets = buckets;
    }

    public static RoadHit query(int worldX, int worldZ) {
        return getInstance().find(worldX, worldZ);
    }

    private static RoadNetwork getInstance() {
        RoadNetwork network = instance;
        if(network == null) {
            synchronized (RoadNetwork.class) {
                network = instance;
                if(network == null) {
                    instance = network = build();
                }
            }
        }
        return network;
    }

    private static RoadNetwork build() {
        Map<Long, List<Sample>> buckets = new HashMap<>();
        for(RoadNetworkData.Edge edge : RoadNetworkData.edges()) {
            sampleEdge(edge, buckets);
        }
        return new RoadNetwork(new ConcurrentHashMap<>(buckets));
    }

    private static void sampleEdge(RoadNetworkData.Edge edge, Map<Long, List<Sample>> buckets) {
        float p0x = edge.from().x();
        float p0z = edge.from().z();
        float cx = edge.controlX();
        float cz = edge.controlZ();
        float p1x = edge.to().x();
        float p1z = edge.to().z();

        float chord = (float) Math.sqrt(sq(p1x - p0x) + sq(p1z - p0z));
        float polygon = (float) Math.sqrt(sq(cx - p0x) + sq(cz - p0z)) + (float) Math.sqrt(sq(p1x - cx) + sq(p1z - cz));
        float pixelLength = (chord + polygon) / 2;
        int steps = Math.max(8, (int) (pixelLength * RoadNetworkData.MAP_TO_WORLD / SAMPLE_SPACING));
        int sampleCount = steps + 1;

        int[] xs = new int[sampleCount];
        int[] zs = new int[sampleCount];
        float[] heights = new float[sampleCount];
        boolean[] kept = new boolean[sampleCount];
        for(int i = 0; i <= steps; i++) {
            float t = (float) i / steps;
            float mt = 1 - t;
            xs[i] = Math.round((mt * mt * p0x + 2 * mt * t * cx + t * t * p1x) * RoadNetworkData.MAP_TO_WORLD);
            zs[i] = Math.round((mt * mt * p0z + 2 * mt * t * cz + t * t * p1z) * RoadNetworkData.MAP_TO_WORLD);
            heights[i] = MiddleEarthHeightMap.getHeight(xs[i], zs[i]);
        }

        // Windowed average over tangential neighbours to smooth grades
        for(int i = 0; i < sampleCount; i++) {
            float total = 0;
            int count = 0;
            for(int j = Math.max(0, i - SMOOTHING_WINDOW); j <= Math.min(sampleCount - 1, i + SMOOTHING_WINDOW); j++) {
                total += heights[j];
                count++;
            }
            heights[i] = total / count;
        }

        for(int i = 0; i < sampleCount; i++) {
            // Deep water: drop the samples (gap in the road) instead of absurd floating bridges
            if(heights[i] < DEEP_WATER_REL_HEIGHT) continue;
            kept[i] = true;
        }

        for(int i = 0; i < sampleCount; i++) {
            if(!kept[i]) continue;
            int end = i + 1 < sampleCount && kept[i + 1] ? i + 1 : i > 0 && kept[i - 1] ? i - 1 : i;
            // Relatively shallow water: bridge with the deck just above the water level
            boolean bridge = heights[i] < BRIDGE_REL_HEIGHT;
            boolean endBridge = heights[end] < BRIDGE_REL_HEIGHT;
            Sample sample = new Sample(xs[i], zs[i], bridge ? BRIDGE_REL_HEIGHT : heights[i], bridge, edge.style(), edge.width(),
                    xs[end], zs[end], endBridge ? BRIDGE_REL_HEIGHT : heights[end], endBridge);
            buckets.computeIfAbsent(bucketKey(Math.floorDiv(xs[i], BUCKET_SIZE), Math.floorDiv(zs[i], BUCKET_SIZE)),
                    key -> new ArrayList<>()).add(sample);
        }
    }

    private static float distanceSqToSegment(int px, int pz, Sample sample) {
        float abx = sample.endX - sample.x;
        float abz = sample.endZ - sample.z;
        float apx = px - sample.x;
        float apz = pz - sample.z;
        float lengthSq = abx * abx + abz * abz;
        float t = lengthSq <= 0 ? 0 : Mth.clamp((apx * abx + apz * abz) / lengthSq, 0, 1);
        float dx = apx - abx * t;
        float dz = apz - abz * t;
        return dx * dx + dz * dz;
    }

    private static RoadHit toHit(int px, int pz, Sample sample, float distanceSq, float width) {
        float abx = sample.endX - sample.x;
        float abz = sample.endZ - sample.z;
        float lengthSq = abx * abx + abz * abz;
        float t = lengthSq <= 0 ? 0 : Mth.clamp(((px - sample.x) * abx + (pz - sample.z) * abz) / lengthSq, 0, 1);
        float relHeight = Mth.lerp(t, sample.relHeight, sample.endRelHeight);
        boolean bridge = t < 0.5f ? sample.bridge : sample.endBridge;
        return new RoadHit((float) Math.sqrt(distanceSq), width, relHeight, bridge, sample.style);
    }

    private static long bucketKey(int bucketX, int bucketZ) {
        return (bucketX & 0xFFFFL) | ((bucketZ & 0xFFFFL) << 16);
    }

    private static float sq(float value) {
        return value * value;
    }

    private RoadHit find(int worldX, int worldZ) {
        float widthScale = ModServerConfigs.ROADS_WIDTH_SCALE;
        if(widthScale <= 0) widthScale = 1;

        int bucketX = Math.floorDiv(worldX, BUCKET_SIZE);
        int bucketZ = Math.floorDiv(worldZ, BUCKET_SIZE);
        RoadHit best = null;
        float bestDistanceSq = Float.MAX_VALUE;
        for(int dx = -1; dx <= 1; dx++) {
            for(int dz = -1; dz <= 1; dz++) {
                List<Sample> bucket = buckets.get(bucketKey(bucketX + dx, bucketZ + dz));
                if(bucket == null) continue;
                for(Sample sample : bucket) {
                    float width = sample.width * widthScale;
                    float influence = width * INFLUENCE_MULTIPLIER;
                    float limitSq = influence * influence;
                    float distanceSq = distanceSqToSegment(worldX, worldZ, sample);
                    if(distanceSq <= limitSq && distanceSq < bestDistanceSq) {
                        bestDistanceSq = distanceSq;
                        best = toHit(worldX, worldZ, sample, distanceSq, width);
                    }
                }
            }
        }
        return best;
    }

    /**
     * @param distance distance to the road centerline in blocks
     * @param relHeight smoothed road grade at the queried position (relative units)
     */
    public record RoadHit(float distance, float width, float relHeight, boolean bridge, RoadNetworkData.RoadStyle style) {}

    /**
     * end* fields store the adjacent sample on the same edge so queries can measure
     * point-to-segment distance instead of the raw ~{@value SAMPLE_SPACING}-block sample spacing.
     */
    private record Sample(int x, int z, float relHeight, boolean bridge, RoadNetworkData.RoadStyle style, float width,
                          int endX, int endZ, float endRelHeight, boolean endBridge) {}
}
