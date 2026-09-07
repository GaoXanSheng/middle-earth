package net.sevenstars.middleearth.world.features.columns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class CaveColumnFeatureConfig implements FeatureConfiguration {
    public static final Codec<CaveColumnFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProviders.codec(0, 3).fieldOf("reach").forGetter(config -> config.reach),
                    IntProviders.codec(1, 10).fieldOf("height").forGetter(config -> config.height),
                    BlockState.CODEC.fieldOf("blockstate").forGetter(config -> config.blockState)
            ).apply(instance, CaveColumnFeatureConfig::new));

    private final IntProvider reach;
    private final IntProvider height;
    private final BlockState blockState;

    public CaveColumnFeatureConfig(IntProvider reach, IntProvider height, BlockState blockState) {
        this.reach = reach;
        this.height = height;
        this.blockState = blockState;
    }

    public IntProvider getReach() {
        return this.reach;
    }

    public IntProvider getHeight() {
        return this.height;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }
}

