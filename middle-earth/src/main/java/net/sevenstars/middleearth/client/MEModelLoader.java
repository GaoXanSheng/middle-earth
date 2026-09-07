package net.sevenstars.middleearth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

@Environment(EnvType.CLIENT)
public class MEModelLoader {
    public static final SpriteId KITE_SHIELD_BASE;
    public static final SpriteId KITE_SHIELD_BASE_NO_PATTERN;

    public static final SpriteId HEATER_SHIELD_BASE;
    public static final SpriteId HEATER_SHIELD_BASE_NO_PATTERN;

    public static final SpriteId ROUND_SHIELD_BASE;
    public static final SpriteId ROUND_SHIELD_BASE_NO_PATTERN;

    static {
        KITE_SHIELD_BASE = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/kite_shield_base"));
        KITE_SHIELD_BASE_NO_PATTERN = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/kite_shield_base_nopattern"));

        HEATER_SHIELD_BASE = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/heater_shield_base"));
        HEATER_SHIELD_BASE_NO_PATTERN = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/heater_shield_base_nopattern"));

        ROUND_SHIELD_BASE = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/round_shield_base"));
        ROUND_SHIELD_BASE_NO_PATTERN = new SpriteId(Sheets.SHIELD_SHEET, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/round_shield_base_nopattern"));
    }
}
