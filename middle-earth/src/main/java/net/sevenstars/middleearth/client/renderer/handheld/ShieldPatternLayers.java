package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Dye canvas + banner pattern overlay layers for the custom shields, drawn onto a single
 * ModelPart (the shield plate) with per-layer tints, mirroring the vanilla banner canvas flow.
 */
@Environment(EnvType.CLIENT)
public final class ShieldPatternLayers {
    public static final SpriteId HEATER_CANVAS = canvas("heater_shield");
    public static final SpriteId KITE_CANVAS = canvas("kite_shield");
    public static final SpriteId ROUND_CANVAS = canvas("round_shield");
    private static final Map<Holder<BannerPattern>, SpriteId> HEATER_PATTERNS = new HashMap<>();
    private static final Map<Holder<BannerPattern>, SpriteId> KITE_PATTERNS = new HashMap<>();
    private static final Map<Holder<BannerPattern>, SpriteId> ROUND_PATTERNS = new HashMap<>();

    private ShieldPatternLayers() {
    }

    private static SpriteId canvas(String shield) {
        return new SpriteId(Sheets.SHIELD_SHEET, MiddleEarth.ofPath("entity", shield, "base"));
    }

    private static Function<Holder<BannerPattern>, SpriteId> patterns(String shield, Map<Holder<BannerPattern>, SpriteId> cache) {
        return pattern -> cache.computeIfAbsent(pattern,
                p -> new SpriteId(Sheets.SHIELD_SHEET, p.value().assetId().withPrefix("entity/" + shield + "/")));
    }

    public static Function<Holder<BannerPattern>, SpriteId> heater() {
        return patterns("heater_shield", HEATER_PATTERNS);
    }

    public static Function<Holder<BannerPattern>, SpriteId> kite() {
        return patterns("kite_shield", KITE_PATTERNS);
    }

    public static Function<Holder<BannerPattern>, SpriteId> round() {
        return patterns("round_shield", ROUND_PATTERNS);
    }

    public static Function<Holder<BannerPattern>, SpriteId> banner() {
        return Sheets::getBannerSprite;
    }

    public static SpriteId bannerCanvas() {
        return Sheets.BANNER_BASE;
    }

    public static void submitCanvas(SubmitNodeCollector collector, PoseStack poseStack, int light, int overlay,
                                    SpriteGetter sprites, ModelPart canvas, SpriteId canvasSprite,
                                    Function<Holder<BannerPattern>, SpriteId> patternSprites,
                                    DyeColor baseColor, BannerPatternLayers patterns) {
        submitLayer(collector, poseStack, light, overlay, sprites, canvas, canvasSprite, baseColor);
        for (int i = 0; i < 16 && i < patterns.layers().size(); i++) {
            BannerPatternLayers.Layer layer = patterns.layers().get(i);
            submitLayer(collector, poseStack, light, overlay, sprites, canvas, patternSprites.apply(layer.pattern()), layer.color());
        }
    }

    private static void submitLayer(SubmitNodeCollector collector, PoseStack poseStack, int light, int overlay,
                                    SpriteGetter sprites, ModelPart canvas, SpriteId sprite, DyeColor color) {
        collector.submitModelPart(canvas, poseStack, sprite.renderType(RenderTypes::bannerPattern),
                light, overlay, sprites.get(sprite), color.getTextureDiffuseColor(), null, 0);
    }
}
