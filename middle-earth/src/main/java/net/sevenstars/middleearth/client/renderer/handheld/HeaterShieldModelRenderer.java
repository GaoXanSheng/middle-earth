package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.MEModelLoader;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class HeaterShieldModelRenderer implements SpecialModelRenderer<DataComponentMap> {

    private final HeaterShieldEntityModel model;
    private final SpriteGetter sprites;

    public HeaterShieldModelRenderer(SpriteGetter sprites, HeaterShieldEntityModel model) {
        this.model = model;
        this.sprites = sprites;
    }

    @Override
    public void submit(@Nullable DataComponentMap data, PoseStack matrices, SubmitNodeCollector collector, int light, int overlay, boolean glint, int seed) {
        BannerPatternLayers bannerPatternsComponent = data != null ? data.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) : BannerPatternLayers.EMPTY;
        DyeColor dyeColor = data != null ? data.get(DataComponents.BASE_COLOR) : null;
        boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrices.pushPose();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteId sprite = bl ? MEModelLoader.HEATER_SHIELD_BASE : MEModelLoader.HEATER_SHIELD_BASE_NO_PATTERN;
        collector.submitModel(this.model, Unit.INSTANCE, matrices, light, overlay, -1, sprite, this.sprites, seed, null);
        // TODO: re-port banner pattern overlay & foil layers to the 26.2 submit pipeline.
        matrices.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> extents) {
        PoseStack matrixStack = new PoseStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.root().getExtentsForGui(matrixStack, extents);
    }

    @Nullable
    @Override
    public DataComponentMap extractArgument(ItemStack stack) {
        return stack.immutableComponents();
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked<DataComponentMap> {
        public static final HeaterShieldModelRenderer.Unbaked INSTANCE = new HeaterShieldModelRenderer.Unbaked();
        public static final MapCodec<HeaterShieldModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }

        public MapCodec<HeaterShieldModelRenderer.Unbaked> type() {
            return CODEC;
        }

        public SpecialModelRenderer<DataComponentMap> bake(BakingContext context) {
            return new HeaterShieldModelRenderer(context.sprites(), new HeaterShieldEntityModel(context.entityModelSet().bakeLayer(MiddleEarthClient.HEATER_SHIELD_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
