package net.sevenstars.middleearth.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleSlabModel {
    public record Slab(Block origin, Block slab) {}
    public static List<Slab> slabs = new ArrayList<>() {
        {
            add(new Slab(ModBlocks.MIRE, ModBlocks.MIRE_SLAB));
            add(new Slab(ModBlocks.DRY_DIRT, ModBlocks.DRY_DIRT_SLAB));
            add(new Slab(ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_SLAB));
            add(new Slab(ModBlocks.COARSE_CHALKSOIL, ModBlocks.COARSE_CHALKSOIL_SLAB));
            add(new Slab(ModBlocks.LOAM, ModBlocks.LOAM_SLAB));
            add(new Slab(ModBlocks.COARSE_LOAM, ModBlocks.COARSE_LOAM_SLAB));
            add(new Slab(ModBlocks.PEAT, ModBlocks.PEAT_SLAB));
            add(new Slab(ModBlocks.COARSE_PEAT, ModBlocks.COARSE_PEAT_SLAB));
            add(new Slab(ModBlocks.SILT, ModBlocks.SILT_SLAB));
            add(new Slab(ModBlocks.COARSE_SILT, ModBlocks.COARSE_SILT_SLAB));
            add(new Slab(ModBlocks.FOUL_DIRT, ModBlocks.FOUL_DIRT_SLAB));
            add(new Slab(ModBlocks.DIRTY_ROOTS, ModBlocks.DIRTY_ROOTS_SLAB));
            add(new Slab(ModBlocks.ASHEN_DIRT, ModBlocks.ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_ASHEN_DIRT, ModBlocks.COBBLY_ASHEN_DIRT_SLAB));
            add(new Slab(ModBlocks.COBBLY_DIRT, ModBlocks.COBBLY_DIRT_SLAB));
            add(new Slab(ModBlocks.SNOWY_DIRT, ModBlocks.SNOWY_DIRT_SLAB));
        }
    };

    public static List<Slab> woodSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> strippedSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Slab> vanillaWoodSlabs = new ArrayList<>() {
        {
        }
    };

    public static List<Slab> vanillaStrippedSlab = new ArrayList<>() {
        {
        }
    };

    public static List<Slab> vanillaSlabs = new ArrayList<>() {
        {
            add(new Slab(Blocks.DIRT, ModBlocks.DIRT_SLAB));
            add(new Slab(Blocks.COARSE_DIRT, ModBlocks.COARSE_DIRT_SLAB));
            add(new Slab(Blocks.ROOTED_DIRT, ModBlocks.ROOTED_DIRT_SLAB));
            add(new Slab(Blocks.MUD, ModBlocks.MUD_SLAB));
            add(new Slab(Blocks.MOSS_BLOCK, ModBlocks.MOSS_SLAB));

            add(new Slab(Blocks.PACKED_MUD, ModBlocks.PACKED_MUD_SLAB));

            add(new Slab(Blocks.WOOL.black(), ModBlocks.BLACK_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.blue(), ModBlocks.BLUE_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.brown(), ModBlocks.BROWN_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.cyan(), ModBlocks.CYAN_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.gray(), ModBlocks.GRAY_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.green(), ModBlocks.GREEN_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.lightBlue(), ModBlocks.LIGHT_BLUE_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.lightGray(), ModBlocks.LIGHT_GRAY_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.lime(), ModBlocks.LIME_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.magenta(), ModBlocks.MAGENTA_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.orange(), ModBlocks.ORANGE_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.pink(), ModBlocks.PINK_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.purple(), ModBlocks.PURPLE_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.red(), ModBlocks.RED_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.white(), ModBlocks.WHITE_WOOL_SLAB));
            add(new Slab(Blocks.WOOL.yellow(), ModBlocks.YELLOW_WOOL_SLAB));
        }
    };
}
