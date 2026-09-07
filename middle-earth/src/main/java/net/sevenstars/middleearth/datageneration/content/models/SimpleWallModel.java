package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleWallModel {
    public record Wall(Block block, Block wall) {}
    public static List<Wall> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> columnWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> strippedWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> vanillaWalls = new ArrayList<>() {
        {
            add(new Wall(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_WALL));

            add(new Wall(Blocks.CUT_COPPER.weathering().unaffected(), ModBlocks.CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.weathering().exposed(), ModBlocks.EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.weathering().weathered(), ModBlocks.WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.weathering().oxidized(), ModBlocks.OXIDIZED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.waxed().unaffected(), ModBlocks.WAXED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.waxed().exposed(), ModBlocks.WAXED_EXPOSED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.waxed().weathered(), ModBlocks.WAXED_WEATHERED_CUT_COPPER_WALL));
            add(new Wall(Blocks.CUT_COPPER.waxed().oxidized(), ModBlocks.WAXED_OXIDIZED_CUT_COPPER_WALL));

        }
    };

    public static List<Wall> vanillaStrippedWalls = new ArrayList<>() {
        {
        }
    };

    public static List<Wall> vanillaWoodWalls = new ArrayList<>() {
        {
        }
    };
}
