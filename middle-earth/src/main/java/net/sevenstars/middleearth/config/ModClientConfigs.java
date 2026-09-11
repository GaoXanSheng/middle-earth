package net.sevenstars.middleearth.config;

import com.mojang.datafixers.util.Pair;
import net.sevenstars.middleearth.MiddleEarth;

public class ModClientConfigs {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.MOD_ID + "/config-client";

    /**Should you see the map overlay button in the middle-earth map gui**/
    public static boolean ENABLE_MAP_OVERLAY;
    /**Should roads be drawn on the middle-earth map gui**/
    public static boolean SHOW_MAP_ROADS;
    /**Should points of interest markers be drawn on the middle-earth map gui**/
    public static boolean SHOW_MAP_MARKERS;
    /**Should you see the experimental npc visuals, or use the simplified version**/
    public static boolean ENABLE_SIMPLIFIED_CHARACTER_RENDERING;
    /**Distance of rendering armor on NPCs**/
    public static int LOD_NPC_ARMOR_DISTANCE;
    /**Distance of rendering NPCs features (long hair, ears, etc.)**/
    public static int LOD_NPC_FEATURES_DISTANCE;
    /**Should glint be disabled in Middle-earth**/
    public static boolean DISABLE_GLINT;

    private static ModConfigProvider configs;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createClientConfigs();
        CONFIG = SimpleConfig.of(PATH).provider(configs).request();
        assignClientConfigs();
    }

    private static void createClientConfigs() {
        configs.addSection("Client Configs");
        configs.addComment("This file stores client configuration options for the Middle-earth mod.");
        configs.addLineJump();
        configs.addComment("GUI configurations");
        configs.addDescription("Should you see the map overlay button in the middle-earth map gui?");
        configs.addKeyValuePair(new Pair<>("enableMapOverlay", false), "boolean");
        configs.addDescription("Should roads be drawn on the middle-earth map gui?");
        configs.addKeyValuePair(new Pair<>("showMapRoads", true), "boolean");
        configs.addDescription("Should points of interest markers be drawn on the middle-earth map gui?");
        configs.addKeyValuePair(new Pair<>("showMapMarkers", true), "boolean");
        configs.addLineJump();
        configs.addComment("NPC simplified model");
        configs.addDescription("Should you see the experimental npc visuals, or use the simplified version.");
        configs.addKeyValuePair(new Pair<>("enableSimplifiedCharacterRendering", false), "boolean");
        configs.addLineJump();
        configs.addComment("LOD distance for NPCs");
        configs.addDescription("Distance of rendering armor on NPCs");
        configs.addKeyValuePair(new Pair<>("npcsArmorDistanceLOD", 48), "int");
        configs.addDescription("Distance of rendering NPCs features (long hair, ears, etc.)");
        configs.addKeyValuePair(new Pair<>("npcsFeaturesDistanceLOD", 24), "int");
        configs.addLineJump();
        configs.addComment("Glint config");
        configs.addDescription("Should glint be disabled in middle-earth");
        configs.addKeyValuePair(new Pair<>("disableGlint", true), "boolean");
    }

    private static void assignClientConfigs() {
        ENABLE_MAP_OVERLAY = CONFIG.getOrDefault("enableMapOverlay", false);
        SHOW_MAP_ROADS = CONFIG.getOrDefault("showMapRoads", true);
        SHOW_MAP_MARKERS = CONFIG.getOrDefault("showMapMarkers", true);
        ENABLE_SIMPLIFIED_CHARACTER_RENDERING = CONFIG.getOrDefault("enableSimplifiedCharacterRendering", false);
        LOD_NPC_ARMOR_DISTANCE = CONFIG.getOrDefault("npcsArmorDistanceLOD", 48);
        LOD_NPC_FEATURES_DISTANCE = CONFIG.getOrDefault("npcsArmorDistanceLOD", 24);
        DISABLE_GLINT = CONFIG.getOrDefault("disableGlint", true);
        MiddleEarth.LOGGER.logDebugMsg("All client configs (" + configs.getConfigsList().size() + ") have been set properly");
    }
}