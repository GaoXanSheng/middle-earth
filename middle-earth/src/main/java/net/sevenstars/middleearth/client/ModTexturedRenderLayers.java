package net.sevenstars.middleearth.client;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.AtlasesME;

import java.util.HashMap;
import java.util.Map;

public class ModTexturedRenderLayers extends Sheets {
    public static final SpriteId HEATER_SHIELD_BASE;
    public static final SpriteId KITE_SHIELD_BASE;
    public static final SpriteId ROUND_SHIELD_BASE;

    private static final Map<Identifier, SpriteId> HEATER_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteId> KITE_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteId> ROUND_SHIELD_PATTERN_TEXTURES;

    public static final Identifier CHARACTER_ATLAS_TEXTURES = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_TEXTURES);
    public static final RenderType CHARACTER_TEXTURES_RENDER_LAYER;
    public static final RenderType CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;

    static {
        CHARACTER_TEXTURES_RENDER_LAYER = RenderTypes.entityCutout(CHARACTER_ATLAS_TEXTURES);
        CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER = RenderTypes.entityTranslucentEmissive(CHARACTER_ATLAS_TEXTURES);

        HEATER_SHIELD_BASE = new SpriteId(SHIELD_SHEET, MiddleEarth.ofVanillaPath("entity", "heater_shield", "base"));
        KITE_SHIELD_BASE = new SpriteId(SHIELD_SHEET, MiddleEarth.ofVanillaPath("entity", "kite_shield", "base"));
        ROUND_SHIELD_BASE = new SpriteId(SHIELD_SHEET, MiddleEarth.ofVanillaPath("entity", "round_shield", "base"));

        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        KITE_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        ROUND_SHIELD_PATTERN_TEXTURES = new HashMap<>();
    }

    public static RenderType getCharacterTexturesRenderLayer() {
        return CHARACTER_TEXTURES_RENDER_LAYER;
    }

    public static RenderType getCharacterTexturesEmissiveRenderLayer() {
        return CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;
    }

    public static SpriteId getHeaterShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return HEATER_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefix("entity/heater_shield/");
            return new SpriteId(SHIELD_SHEET, identifier);
        });
    }

    public static SpriteId getKiteShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return KITE_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefix("entity/kite_shield/");
            return new SpriteId(SHIELD_SHEET, identifier);
        });
    }

    public static SpriteId getRoundShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return ROUND_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefix("entity/round_shield/");
            return new SpriteId(SHIELD_SHEET, identifier);
        });
    }
}
