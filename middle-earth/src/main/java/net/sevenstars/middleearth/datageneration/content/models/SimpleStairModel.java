package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleStairModel {
    public record Stair(Block origin, Block stairs) {}
    public static List<Stair> stairs = new ArrayList<>() {
        {
            add(new Stair(ModBlocks.MIRE, ModBlocks.MIRE_STAIRS));
            add(new Stair(ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_STAIRS));
            add(new Stair(ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_STAIRS));
            add(new Stair(ModBlocks.COARSE_CHALKSOIL, ModBlocks.COARSE_CHALKSOIL_STAIRS));
            add(new Stair(ModBlocks.LOAM, ModBlocks.LOAM_STAIRS));
            add(new Stair(ModBlocks.COARSE_LOAM, ModBlocks.COARSE_LOAM_STAIRS));
            add(new Stair(ModBlocks.PEAT, ModBlocks.PEAT_STAIRS));
            add(new Stair(ModBlocks.COARSE_PEAT, ModBlocks.COARSE_PEAT_STAIRS));
            add(new Stair(ModBlocks.SILT, ModBlocks.SILT_STAIRS));
            add(new Stair(ModBlocks.COARSE_SILT, ModBlocks.COARSE_SILT_STAIRS));
            add(new Stair(ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_STAIRS));
            add(new Stair(ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_STAIRS));
            add(new Stair(ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_STAIRS));
            add(new Stair(ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_STAIRS));
            add(new Stair(ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_STAIRS));
            add(new Stair(ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_STAIRS));
        }
    };

    public static List<Stair> strippedStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> woodStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> vanillaStairs = new ArrayList<>() {
        {
            add(new Stair(Blocks.DIRT, ModBlocks.DIRT_STAIRS));
            add(new Stair(Blocks.MOSS_BLOCK, ModBlocks.MOSS_STAIRS));
            add(new Stair(Blocks.ROOTED_DIRT, ModBlocks.ROOTED_DIRT_STAIRS));
            add(new Stair(Blocks.COARSE_DIRT, ModBlocks.COARSE_DIRT_STAIRS));
            add(new Stair(Blocks.MUD, ModBlocks.MUD_STAIRS));

            add(new Stair(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_STAIRS));

            add(new Stair(Blocks.WOOL.black(), ModBlocks.BLACK_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.blue(), ModBlocks.BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.brown(), ModBlocks.BROWN_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.cyan(), ModBlocks.CYAN_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.gray(), ModBlocks.GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.green(), ModBlocks.GREEN_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.lightBlue(), ModBlocks.LIGHT_BLUE_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.lightGray(), ModBlocks.LIGHT_GRAY_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.lime(), ModBlocks.LIME_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.magenta(), ModBlocks.MAGENTA_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.orange(), ModBlocks.ORANGE_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.pink(), ModBlocks.PINK_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.purple(), ModBlocks.PURPLE_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.red(), ModBlocks.RED_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.white(), ModBlocks.WHITE_WOOL_STAIRS));
            add(new Stair(Blocks.WOOL.yellow(), ModBlocks.YELLOW_WOOL_STAIRS));
        }
    };

    public static List<Stair> vanillaWoodStairs = new ArrayList<>() {
        {
        }
    };

    public static List<Stair> vanillaStrippedStairs = new ArrayList<>() {
        {
        }
    };
}
