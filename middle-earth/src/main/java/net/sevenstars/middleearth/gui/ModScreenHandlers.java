package net.sevenstars.middleearth.gui;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.crockpot.CrockpotScreenHandler;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreenHandler;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreenHandler;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenData;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;

public class ModScreenHandlers {
    public static MenuType<WoodPileScreenHandler> WOOD_PILE_SCREEN_HANDLER
            = new MenuType<>(WoodPileScreenHandler::new, FeatureFlags.VANILLA_SET);
    
    public static final MenuType<CrockpotScreenHandler> CROCKPOT_SCREEN_HANDLER
            = new ExtendedMenuType<>(CrockpotScreenHandler::new, BlockPos.STREAM_CODEC.cast());

    public static MenuType<ArtisanTableScreenHandler> ARTISAN_SCREEN_HANDLER
            = new ExtendedMenuType<>(ArtisanTableScreenHandler::new, ByteBufCodecs.STRING_UTF8.cast());

    public static MenuType<InscriptionTableScreenHandler> INSCRIPTION_SCREEN_HANDLER
            = new MenuType<>(InscriptionTableScreenHandler::new, FeatureFlags.VANILLA_SET);

    public static MenuType<ShapingAnvilScreenHandler> TREATED_ANVIL_SCREEN_HANDLER
            = new ExtendedMenuType<>(ShapingAnvilScreenHandler::new, BlockPos.STREAM_CODEC.cast());

    public static final MenuType<ForgeAlloyingScreenHandler> FORGE_ALLOYING_SCREEN_HANDLER
            = new ExtendedMenuType<>(ForgeAlloyingScreenHandler::new, BlockPos.STREAM_CODEC.cast());

    public static final MenuType<StructureManagerScreenHandler> STRUCTURE_MANAGER_SCREEN_HANDLER
            = new ExtendedMenuType<>(StructureManagerScreenHandler::new, StructureManagerScreenData.PACKET_CODEC.cast());

    public static final MenuType<StructureNestScreenHandler> STRUCTURE_NEST_SCREEN_HANDLER
            = new ExtendedMenuType<>(StructureNestScreenHandler::new, StructureNestScreenData.PACKET_CODEC.cast());

    public static void registerAllScreenHandlers() {
        register("wood_pile", WOOD_PILE_SCREEN_HANDLER);
        register("forge_alloying", FORGE_ALLOYING_SCREEN_HANDLER);
        register("artisan_table", ARTISAN_SCREEN_HANDLER);
        register("inscription_table", INSCRIPTION_SCREEN_HANDLER);
        register("treated_anvil", TREATED_ANVIL_SCREEN_HANDLER);
        register("structure_manager", STRUCTURE_MANAGER_SCREEN_HANDLER);
        register("structure_nest", STRUCTURE_NEST_SCREEN_HANDLER);
        register("crockpot",CROCKPOT_SCREEN_HANDLER );
    }

    private static void register(String name, MenuType handlerType) {
        Registry.register(BuiltInRegistries.MENU, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), handlerType);
    }
}
