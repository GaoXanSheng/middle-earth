package net.sevenstars.middleearth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ExtraModelKey;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.SimpleUnbakedExtraModel;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.coffers.*;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntityRenderer;
import net.sevenstars.middleearth.block.special.forge.ForgeEntityRenderer;
import net.sevenstars.middleearth.block.special.plate.PlateEntityRenderer;
import net.sevenstars.middleearth.block.special.pots.LootablePotBlockEntityRenderer;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestEntityRenderer;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilEntityRenderer;
import net.sevenstars.middleearth.block.special.skull.OldSkullBlockEntityRenderer;
import net.sevenstars.middleearth.client.BlockColorsME;
import net.sevenstars.middleearth.client.model.equipment.CustomBootsModel;
import net.sevenstars.middleearth.client.model.equipment.CustomChestplateModel;
import net.sevenstars.middleearth.client.model.equipment.CustomHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.CustomLeggingsModel;
import net.sevenstars.middleearth.client.model.equipment.chest.backAttachments.armored.CapeMediumModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmetAttachments.armored.HoodModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.ErynGalenWatchwardenHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.SilvanLordHelmetModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.elves.woodlandrealm.WoodlandRealmCrownModel;
import net.sevenstars.middleearth.client.model.equipment.head.helmets.humans.rohan.RohanHelmetModel;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.HeaterShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.KiteShieldEntityModel;
import net.sevenstars.middleearth.client.model.hand.shields.RoundShieldEntityModel;
import net.sevenstars.middleearth.client.renderer.armor.*;
import net.sevenstars.middleearth.client.renderer.handheld.HeaterShieldModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.HeldBannerModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.KiteShieldModelRenderer;
import net.sevenstars.middleearth.client.renderer.handheld.RoundShieldModelRenderer;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.Crops;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityModelsME;
import net.sevenstars.middleearth.entity.barrel.BarrelEntityRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollRenderer;
import net.sevenstars.middleearth.entity.beasts.warg.WargRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntityRenderer;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileRenderer;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntityRenderer;
import net.sevenstars.middleearth.entity.seat.SeatRenderer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaRenderer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerRenderer;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobRenderer;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.forge.ForgeAlloyingScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreen;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreen;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreen;
import net.sevenstars.middleearth.gui.wood_pile.WoodPileScreen;
import net.sevenstars.middleearth.item.EquipmentItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.items.weapons.HotComponentProperty;
import net.sevenstars.middleearth.item.items.weapons.SneakAttackProperty;
import net.sevenstars.middleearth.item.utils.armor.ArmorModelsME;
import net.sevenstars.middleearth.network.ModClientNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToServer;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.particles.custom.AnvilBonkParticle;
import net.sevenstars.middleearth.particles.custom.BiomeFogParticle;

public class MiddleEarthClient implements ClientModInitializer {
    
    public static final ModelLayerLocation CUSTOM_ARMOR_HELMET = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_1");
    public static final ModelLayerLocation CUSTOM_ARMOR_CHESTPLATE = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_2");
    public static final ModelLayerLocation CUSTOM_ARMOR_LEGGINGS = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_3");
    public static final ModelLayerLocation CUSTOM_ARMOR_BOOTS = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "_4");
    public static final ModelLayerLocation HELMET_ADDON_MODEL_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "helmet_addon");
    public static final ModelLayerLocation BACK_ATTACHMENT_MODEL_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "back_attachment");
    public static final ModelLayerLocation HELMET_ATTACHMENT_MODEL_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "armor"), "helmet_attachment");

    public static final ModelLayerLocation HEATER_SHIELD_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "heater_shield"), "main");
    public static final ModelLayerLocation KITE_SHIELD_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "kite_shield"), "main");
    public static final ModelLayerLocation ROUND_SHIELD_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "round_shield"), "main");

    public static final ModelLayerLocation HELD_BANNER_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "held_banner"), "main");

    @Override
    public void onInitializeClient() {
        ModClientNetworkHandler.register(new ConnectionToServer());

        KeyInputHandler.register();

        EntityModelsME.getModels();
        ConditionalItemModelProperties.ID_MAPPER.put(MiddleEarth.of("sneak_attack"), SneakAttackProperty.CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(MiddleEarth.of("hot_component"), HotComponentProperty.CODEC);

        // Entities

        EntityRendererRegistry.register(EntitiesME.SNOW_TROLL, SnowTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.CAVE_TROLL, CaveTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.STONE_TROLL, StoneTrollRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PETRIFIED_TROLL, PetrifiedTrollRenderer::new);

        EntityRendererRegistry.register(EntitiesME.BROADHOOF_GOAT, BroadhoofGoatRenderer::new);
        EntityRendererRegistry.register(EntitiesME.GREAT_HORN, GreatHornRenderer::new);
        EntityRendererRegistry.register(EntitiesME.WARG, WargRenderer::new);

        EntityRendererRegistry.register(EntitiesME.REINFORCED_BARREL, BarrelEntityRenderer::new);

        EntityRendererRegistry.register(EntitiesME.SHELOBITE_LARVA, ShelobiteLarvaRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SHELOBITE_SCUTTLER, ShelobiteScuttlerRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SPAWN_OF_SHELOB, SpawnOfShelobRenderer::new);
        //EntityRendererRegistry.register(EntitiesME.BALROG, BalrogRenderer::new);

        EntityRendererRegistry.register(EntitiesME.FIRE_OF_ORTHANC, FireOfOrthancEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PEBBLE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntitiesME.PINECONE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntitiesME.LIT_PINECONE, ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SPEAR, SpearEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.BOULDER, BoulderEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesME.SMOKE_RING_PROJECTILE, SmokeRingProjectileRenderer::new);
        EntityRendererRegistry.register(EntitiesME.WEB, ThrownItemRenderer::new);

        EntityRendererRegistry.register(EntitiesME.NPC, NpcEntityRenderer::new);

        EntityRendererRegistry.register(EntitiesME.SEAT_ENTITY, SeatRenderer::new);
        //HandledScreens.register(ModScreenHandlers.CROCKPOT_SCREEN_HANDLER, CrockpotScreen::new);
        MenuScreens.register(ModScreenHandlers.FORGE_ALLOYING_SCREEN_HANDLER, ForgeAlloyingScreen::new);
        MenuScreens.register(ModScreenHandlers.ARTISAN_SCREEN_HANDLER, ArtisanTableScreen::new);
        MenuScreens.register(ModScreenHandlers.INSCRIPTION_SCREEN_HANDLER, InscriptionTableScreen::new);
        MenuScreens.register(ModScreenHandlers.TREATED_ANVIL_SCREEN_HANDLER, ShapingAnvilScreen::new);
        MenuScreens.register(ModScreenHandlers.WOOD_PILE_SCREEN_HANDLER, WoodPileScreen::new);
        MenuScreens.register(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, StructureManagerScreen::new);
        MenuScreens.register(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, StructureNestScreen::new);

        BlockEntityRenderers.register(ModBlockEntities.STONE_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.TREATED_ANVIL, ShapingAnvilEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FORGE, ForgeEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.LARCH_COFFER, LarchCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.PINE_COFFER, PineCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SPRUCE_COFFER, SpruceCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FIR_COFFER, FirCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.BEECH_COFFER, BeechCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.CHESTNUT_COFFER, ChestnutCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OAK_COFFER, OakCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.WILLOW_COFFER, WillowCofferEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.REINFORCED_CHEST, ReinforcedChestEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.OLD_SKULL, OldSkullBlockEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.BELLOWS, BellowsBlockEntityRenderer::new);
        //BlockEntityRendererFactories.register(ModBlockEntities.CROCKPOT, CrockpotEntityRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.PLATE, PlateEntityRenderer::new);
        BlockEntityRenderers.register(net.minecraft.world.level.block.entity.BlockEntityTypes.DECORATED_POT, LootablePotBlockEntityRenderer::new);

        ModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_HELMET, CustomHelmetModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_CHESTPLATE, CustomChestplateModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_LEGGINGS, CustomLeggingsModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(CUSTOM_ARMOR_BOOTS, CustomBootsModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(HELMET_ADDON_MODEL_LAYER, RohanHelmetModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(BACK_ATTACHMENT_MODEL_LAYER, CapeMediumModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(HELMET_ATTACHMENT_MODEL_LAYER, HoodModel::getTexturedModelData);

        ModelLayerRegistry.registerModelLayer(HEATER_SHIELD_LAYER, HeaterShieldEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(KITE_SHIELD_LAYER, KiteShieldEntityModel::getTexturedModelData);
        ModelLayerRegistry.registerModelLayer(ROUND_SHIELD_LAYER, RoundShieldEntityModel::getTexturedModelData);

        ModelLayerRegistry.registerModelLayer(HELD_BANNER_LAYER, HeldBannerEntityModel::getTexturedModelData);

        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "held_banner"), HeldBannerModelRenderer.Unbaked.CODEC);

        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "heater_shield"), HeaterShieldModelRenderer.Unbaked.CODEC);
        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "kite_shield"), KiteShieldModelRenderer.Unbaked.CODEC);
        SpecialModelRenderers.ID_MAPPER.put(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "round_shield"), RoundShieldModelRenderer.Unbaked.CODEC);

        for(ArmorModelsME.ModHelmetModels model : ArmorModelsME.ModHelmetModels.values()){
            ArmorRenderer.register(new HelmetArmorRenderer(model.getModel()), model.getItem());
        }
        ArmorRenderer.register(new HelmetVariantsRenderer(new SilvanLordHelmetModel(SilvanLordHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.SILVAN_LORD_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new HelmetVariantsRenderer(new ErynGalenWatchwardenHelmetModel(ErynGalenWatchwardenHelmetModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.OXIDISED_ERYN_GALEN_WATCHWARDEN_HELMET);
        ArmorRenderer.register(new WoodlandCrownRenderer(new WoodlandRealmCrownModel(WoodlandRealmCrownModel.getTexturedModelData().bakeRoot())), EquipmentItemsME.WOODLAND_REALM_CROWN);

        for(ArmorModelsME.ModChestplateModels model : ArmorModelsME.ModChestplateModels.values()){
            ArmorRenderer.register(new ChestplateArmorRenderer(model.getModel()), model.getItem());
        }

        EquipmentItemsME.armorPiecesListHelmets.forEach(armor -> {
            ArmorRenderer.register(new HelmetArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListChestplates.forEach(armor -> {
            ArmorRenderer.register(new ChestplateArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListLeggings.forEach(armor -> {
            ArmorRenderer.register(new LeggingsArmorRenderer(), armor.asItem());
        });
        EquipmentItemsME.armorPiecesListBoots.forEach(armor -> {
            ArmorRenderer.register(new BootsArmorRenderer(), armor.asItem());
        });

        EquipmentItemsME.helmetAttachments.forEach(hood -> {
            ArmorRenderer.register(new HelmetAttachmentRenderer(), hood);
        });
        EquipmentItemsME.backAttachments.forEach(cape -> {
            ArmorRenderer.register(new BackAttachmentRenderer(), cape);
        });

        ModelLoadingPlugin.register(pluginContext -> {
            pluginContext.addModel(ExtraModelKey.create(() -> "plate_apple"), SimpleUnbakedExtraModel.blockStateModel(MiddleEarth.ofPath("item", "plate_apple")));
        });

        ParticleProviderRegistry.getInstance().register(ModParticleTypes.ANVIL_SPARK_PARTICLE, AnvilBonkParticle.Factory::new);
        ParticleProviderRegistry.getInstance().register(ModParticleTypes.BIOME_FOG_PARTICLE, BiomeFogParticle.Factory::new);

        // TODO(26.2): block render-layer assignment (fabric BlockRenderLayerMap removed) not yet re-ported.
        BlockColorsME.initializeBlockColors();
    }

}
