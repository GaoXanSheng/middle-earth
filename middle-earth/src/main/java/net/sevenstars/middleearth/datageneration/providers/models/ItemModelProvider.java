package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.conditional.Broken;
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem;
import net.minecraft.client.renderer.item.properties.numeric.CrossbowPull;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.client.renderer.item.properties.select.CustomModelDataProperty;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.item.equipment.trim.TrimMaterials;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.CustomItemModels;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.CustomLongswordWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.HotComponentProperty;
import net.sevenstars.middleearth.item.items.weapons.SneakAttackProperty;
import net.sevenstars.middleearth.item.utils.SmithingTrimMaterialsME;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static net.minecraft.client.data.models.ItemModelGenerators.createFlatModelDispatch;

public class ItemModelProvider extends FabricModelProvider {

    public ItemModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "ItemModelProvider";
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    private static final List<ItemModelGenerators.TrimMaterialData> TRIM_MATERIALS = List.of(
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.QUARTZ, TrimMaterials.QUARTZ),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.IRON, TrimMaterials.IRON),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.NETHERITE, TrimMaterials.NETHERITE),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.REDSTONE, TrimMaterials.REDSTONE),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.COPPER, TrimMaterials.COPPER),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.GOLD, TrimMaterials.GOLD),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.EMERALD, TrimMaterials.EMERALD),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.DIAMOND, TrimMaterials.DIAMOND),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.LAPIS, TrimMaterials.LAPIS),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.AMETHYST, TrimMaterials.AMETHYST),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.RESIN, TrimMaterials.RESIN),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("jade"), SmithingTrimMaterialsME.JADE),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("lead"), SmithingTrimMaterialsME.LEAD),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("tin"), SmithingTrimMaterialsME.TIN),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("bronze"), SmithingTrimMaterialsME.BRONZE),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("crude"), SmithingTrimMaterialsME.CRUDE),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("silver"), SmithingTrimMaterialsME.SILVER),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("steel"), SmithingTrimMaterialsME.STEEL),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("khazad_steel"), SmithingTrimMaterialsME.KHAZAD_STEEL),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("edhel_steel"), SmithingTrimMaterialsME.EDHEL_STEEL),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("burzum_steel"), SmithingTrimMaterialsME.BURZUM_STEEL),
            new ItemModelGenerators.TrimMaterialData(MaterialAssetGroup.create("mithril"), SmithingTrimMaterialsME.MITHRIL)
    );

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {

        for (Item item : SimpleItemModel.items) {
            itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }

        for (Item item : SimpleHandheldItemModel.items) {
            itemModelGenerator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        }

        for (Item item : SimpleHandheldItemModel.daggers) {
            registerDaggerItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.items) {
            registerWeaponBigItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.bigBows) {
            registerBigBowItemModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBowItemModel.items) {
            registerBow(itemModelGenerator, item);
        }

        for (Item item : SimpleCrossbowItemModel.items) {
            registerCrossbow(itemModelGenerator, item);
        }

        for (Item item : SimpleSpearModel.items) {
            registerSpearModels(itemModelGenerator, item);
        }

        for (Item item : SimpleBigItemModel.genericItems) {
            registerGenericBigModels(itemModelGenerator, item);
        }

        for (Item item : WeaponItemsME.shields) {
            registerShield(itemModelGenerator, item);
        }

        for (SimpleArtefactModels.Artefact artefact : SimpleArtefactModels.artefacts) {
            registerArtefact(itemModelGenerator, artefact.artefact(), artefact.dualModel());
        }

        for (Item item : HotMetalsModel.ingots) {
            registerHotIngotsItem(item, itemModelGenerator);
        }

        for (Item item : HotMetalsModel.nuggets) {
            registerHotNuggetItem(item, itemModelGenerator);
        }

        for (Item item : HotMetalsModel.nuggies) {
            registerHotItem(item, itemModelGenerator);
        }

        // Dyeables needs to be done manually (because of layers)

        SimpleDyeableItemModel.items.forEach(item -> {
            registerDyeableArmor(item, itemModelGenerator);
        });

        registerPalettedItem(ResourceItemsME.ROD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.LARGE_ROD, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.PICKAXE_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.AXE_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHOVEL_HEAD, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.HOE_HEAD, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHORT_BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.LONG_BLADE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SWORD_HILT, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.MAIL_RING, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.MAIL, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.SCALE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SCALE_MAIL, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.ARMOR_PLATE, itemModelGenerator);

        registerPalettedItem(ResourceItemsME.HELMET_PLATE, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHIELD_BORDER, itemModelGenerator);
        registerPalettedItem(ResourceItemsME.SHIELD_PLATE, itemModelGenerator);

        List<SelectItemModel.SwitchCase> models = new ArrayList<>(List.of());

        NpcRegistry.allNpcTypes.forEach(npcDataRegistryKey -> {
            String id = npcDataRegistryKey.identifier().getPath().replaceAll("npc_data.middle-earth.", "").replaceAll("\\.", "_") + "_spawn_egg";

            var item = ItemModelUtils.when(id,
                    ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(MiddleEarth.of("item/" + id),
                            TextureMapping.layer0(new Material(MiddleEarth.of("item/" + id))),
                            itemModelGenerator.modelOutput
                    )));

            if(!models.contains(item))
                models.add(item);
        });
        ItemModel.Unbaked fallbackModel = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(EggItemsME.NPC_SPAWN_EGG, ModelTemplates.FLAT_ITEM));

        String randomNpcEggId = "npc_random_spawn_egg";
        var randomNpcEgg = ItemModelUtils.when(randomNpcEggId,
            ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(MiddleEarth.ofPath( "item", randomNpcEggId),
                    TextureMapping.layer0(new Material(MiddleEarth.ofPath("item", randomNpcEggId))),
                    itemModelGenerator.modelOutput
            )));

        if(!models.contains(randomNpcEgg))
            models.add(randomNpcEgg);

        itemModelGenerator.itemModelOutput.accept(EggItemsME.NPC_SPAWN_EGG,
                new SelectItemModel.Unbaked(Optional.empty(), new SelectItemModel.UnbakedSwitch(new CustomModelDataProperty(0), models), Optional.of(fallbackModel)));
    }

    public final void registerDaggerItemModels(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
        ItemModel.Unbaked unbakedHandStrike = ItemModelUtils.plainModel(CustomItemModels.DAGGER_STRIKE.create(ModelLocationUtils.getModelLocation(item, "_strike"),
                TextureMapping.layer0(TextureMapping.getItemTexture(item)), itemModelGenerator.modelOutput));
        //ItemModels.basic(itemModelGenerator.registerSubModel(item, "_strike", CustomItemModels.DAGGER_STRIKE));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new SneakAttackProperty(), unbakedHandStrike, unbakedHand));
    }

    public final void registerWeaponBigItemModels(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand;
        if (BuiltInRegistries.ITEM.getKey(item).getPath().contains("staff")){
            unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.BIG_WEAPON_STAFF));
        } else {
            unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.BIG_WEAPON));
        }
        ItemModel.Unbaked unbakedInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));

        if (item instanceof CustomLongswordWeaponItem longswordWeaponItem){
            ItemModel.Unbaked unbakedHandBlocking = ItemModelUtils.plainModel(CustomItemModels.BIG_WEAPON_BLOCKING.create(ModelLocationUtils.getModelLocation(item, "_blocking"), TextureMapping.layer0(TextureMapping.getItemTexture(item)), itemModelGenerator.modelOutput));
            itemModelGenerator.itemModelOutput.accept(longswordWeaponItem, ItemModelUtils.conditional(new IsUsingItem(),
                    ItemModelUtils.select(new DisplayContext(), unbakedHandBlocking,
                            ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)),
                    ItemModelUtils.select(new DisplayContext(), unbakedHand,
                            ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory))));
        } else {
            itemModelGenerator.itemModelOutput.accept(item, createFlatModelDispatch(unbakedInventory, unbakedHand));
        }
    }

    public final void registerGenericBigModels(ItemModelGenerators itemModelGenerator, Item item) {
        if (item instanceof PipeItem) {
            registerPipeItemModels(itemModelGenerator, item);
            return;
        }

        ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbakedInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));

        itemModelGenerator.itemModelOutput.accept(item, createFlatModelDispatch(unbakedInventory, unbakedHand));
    }

    public final void registerPipeItemModels(ItemModelGenerators itemModelGenerator, Item item) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbakedSmokingHand = ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "item/smoking_" + path));
        ItemModel.Unbaked unbakedInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.select(new DisplayContext(),
                ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), unbakedSmokingHand, unbakedHand),
                ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)));
    }

    public final void registerArtefact(ItemModelGenerators itemModelGenerator, Item item, Boolean dualModel) {
        if(item instanceof CustomDaggerWeaponItem) {
            ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
            ItemModel.Unbaked unbakedBroken = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_broken", ModelTemplates.FLAT_HANDHELD_ITEM));
            ItemModel.Unbaked unbakedHandStrike = ItemModelUtils.plainModel(CustomItemModels.DAGGER_STRIKE.create(ModelLocationUtils.getModelLocation(item, "_strike"),
                    TextureMapping.layer0(TextureMapping.getItemTexture(item)), itemModelGenerator.modelOutput));

            itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new SneakAttackProperty(), unbakedHandStrike,
                    ItemModelUtils.conditional(new Broken(), unbakedBroken, unbakedHand)));
        } else if (dualModel) {
            ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.BIG_WEAPON));
            ItemModel.Unbaked unbakedInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));
            ItemModel.Unbaked unbakedHandBlocking = ItemModelUtils.plainModel(CustomItemModels.BIG_WEAPON_BLOCKING.create(ModelLocationUtils.getModelLocation(item, "_blocking"), TextureMapping.layer0(TextureMapping.getItemTexture(item)), itemModelGenerator.modelOutput));

            ItemModel.Unbaked unbakedBrokenHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_broken", CustomItemModels.BIG_WEAPON));
            ItemModel.Unbaked unbakedBrokenInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_broken_inventory", ModelTemplates.FLAT_ITEM));
            ItemModel.Unbaked unbakedBrokenHandBlocking = ItemModelUtils.plainModel(CustomItemModels.BIG_WEAPON_BLOCKING.create(
                    ModelLocationUtils.getModelLocation(item, "_broken_blocking"), TextureMapping.layer0(TextureMapping.getItemTexture(item)), itemModelGenerator.modelOutput));

            itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new Broken(),
                    ItemModelUtils.select(new DisplayContext(), ItemModelUtils.conditional(new IsUsingItem(), unbakedBrokenHandBlocking, unbakedBrokenHand),
                            ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedBrokenInventory)),
                    ItemModelUtils.select(new DisplayContext(), ItemModelUtils.conditional(new IsUsingItem(), unbakedHandBlocking, unbakedHand),
                            ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory))));
        } else {
            ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM));
            ItemModel.Unbaked unbakedBroken = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_broken", ModelTemplates.FLAT_HANDHELD_ITEM));

            itemModelGenerator.generateBooleanDispatch(item, new Broken(), unbakedBroken, unbaked);
        }

    }

    public final void registerSpearModels(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbakedInventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedHolding = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item).withSuffix("_holding"));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.select(new DisplayContext(), unbakedHand,
                ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), unbakedInventory)));
    }

    public final void registerShield(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(item, "_blocking"));
        itemModelGenerator.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), unbaked2, unbaked);
    }

    public final void registerBigBowItemModels(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbakedHand = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand2 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_0", CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand3 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_1", CustomItemModels.LONGBOW));
        ItemModel.Unbaked unbakedHand4 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_2", CustomItemModels.LONGBOW));

        ItemModel.Unbaked unbakedIventory = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_inventory", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedIventory2 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_0_inventory", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedIventory3 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_1_inventory", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedIventory4 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_2_inventory", ModelTemplates.FLAT_ITEM));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.select(new DisplayContext(), ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(
                        new UseDuration(false), 0.05F, unbakedHand2,
                        ItemModelUtils.override(unbakedHand3, 0.65F),
                        ItemModelUtils.override(unbakedHand4, 0.9F)), unbakedHand), ItemModelUtils.when(List.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED), ItemModelUtils.conditional(ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(
                        new UseDuration(false), 0.05F, unbakedIventory2,
                        ItemModelUtils.override(unbakedIventory3, 0.65F),
                        ItemModelUtils.override(unbakedIventory4, 0.9F)), unbakedIventory))));

    }

    public final void registerBow(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.BOW));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_0", ModelTemplates.BOW));
        ItemModel.Unbaked unbaked3 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_1", ModelTemplates.BOW));
        ItemModel.Unbaked unbaked4 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_2", ModelTemplates.BOW));
        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(new UseDuration(false), 0.05F, unbaked2, new RangeSelectItemModel.Entry[]{ItemModelUtils.override(unbaked3, 0.65F), ItemModelUtils.override(unbaked4, 0.9F)}), unbaked));
    }

    public final void registerCrossbow(ItemModelGenerators itemModelGenerator, Item item) {
        ItemModel.Unbaked unbaked = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, CustomItemModels.CROSSBOW));
        ItemModel.Unbaked unbaked2 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_0", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked unbaked3 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_1", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked unbaked4 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_pulling_2", ModelTemplates.CROSSBOW));
        ItemModel.Unbaked unbaked5 = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_arrow", ModelTemplates.CROSSBOW));
        // No per-faction firework loading textures exist; reuse the vanilla crossbow_firework model.
        ItemModel.Unbaked unbaked6 = ItemModelUtils.plainModel(Identifier.withDefaultNamespace("item/crossbow_firework"));
        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.select(new Charge(), ItemModelUtils.conditional(ItemModelUtils.isUsingItem(), ItemModelUtils.rangeSelect(new CrossbowPull(), unbaked2, new RangeSelectItemModel.Entry[]{ItemModelUtils.override(unbaked3, 0.58F), ItemModelUtils.override(unbaked4, 1.0F)}), unbaked), new SelectItemModel.SwitchCase[]{ItemModelUtils.when(CrossbowItem.ChargeType.ARROW, unbaked5), ItemModelUtils.when(CrossbowItem.ChargeType.ROCKET, unbaked6)}));
    }

    public final Identifier registerSubModelWithSingletonTexture(Item item, String suffix, ModelTemplate model, BiConsumer<Identifier, ModelInstance> modelCollector) {
        return model.create(ModelLocationUtils.getModelLocation(item, suffix), TextureMapping.layer0(TextureMapping.getItemTexture(item)), modelCollector);
    }

    // Mirrors vanilla ItemModelGenerators.generateTwoLayerDyedItem: plain base model when
    // undyed, base+overlay with the overlay tinted by DYED_COLOR when dyed.
    public final void registerDyeableArmor(Item armor, ItemModelGenerators itemModelGenerator) {
        Material base = TextureMapping.getItemTexture(armor);
        Material overlay = TextureMapping.getItemTexture(armor, "_overlay");

        Identifier plainId = ModelTemplates.FLAT_ITEM.create(armor, TextureMapping.layer0(base), itemModelGenerator.modelOutput);
        Identifier dyedId = ModelLocationUtils.getModelLocation(armor, "_dyed");
        ModelTemplates.TWO_LAYERED_ITEM.create(dyedId, TextureMapping.layered(base, overlay), itemModelGenerator.modelOutput);

        itemModelGenerator.itemModelOutput.accept(armor, ItemModelUtils.conditional(
                ItemModelUtils.hasComponent(DataComponents.DYED_COLOR),
                ItemModelUtils.tintedModel(dyedId, ItemModelUtils.constantTint(-1), new Dye(0)),
                ItemModelUtils.plainModel(plainId)));
    }

    public final void registerPalettedItem(Item item, ItemModelGenerators itemModelGenerator) {
        Identifier identifierItem = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "item/" + BuiltInRegistries.ITEM.getKey(item).getPath());

        Material identifier2 = TextureMapping.getItemTexture(item);

        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> list = new ArrayList<>(TRIM_MATERIALS.size());
        ItemModelGenerators.TrimMaterialData trimMaterial;
        ItemModel.Unbaked unbaked;

        for (Iterator<ItemModelGenerators.TrimMaterialData> var9 = TRIM_MATERIALS.iterator(); var9.hasNext(); list.add(ItemModelUtils.when(trimMaterial.materialKey(), unbaked))) {
            trimMaterial = var9.next();
            Identifier identifier4 = identifierItem.withSuffix("_" + trimMaterial.assets().base().suffix() + "_trim");

            itemModelGenerator.generateLayeredItem(identifier4, identifier2,
                    new Material(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "trims/" + identifierItem.getPath().replaceAll("item", "items") + "_trim" + "_" + trimMaterial.assets().base().suffix())));
            unbaked = ItemModelUtils.plainModel(identifier4);
        }

        ItemModel.Unbaked unbaked2;
        ModelTemplates.FLAT_ITEM.create(identifierItem, TextureMapping.layer0(identifier2), itemModelGenerator.modelOutput);
        unbaked2 = ItemModelUtils.plainModel(identifierItem);

        // Only emit the heated-state model when its texture exists; otherwise the heated state just reuses the plain model.
        ItemModel.Unbaked unbakedHotItem = itemTextureExists(BuiltInRegistries.ITEM.getKey(item).getPath() + "_hot")
                ? ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_hot", ModelTemplates.FLAT_ITEM))
                : unbaked2;

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new HotComponentProperty(), unbakedHotItem, ItemModelUtils.select(new TrimMaterialProperty(), unbaked2, list)));
    }

    public final void registerHotIngotsItem(Item item, ItemModelGenerators itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        String idPath = "ingot_hot";
        if(item == ResourceItemsME.BRONZE_INGOT) {
            idPath = "medium_ingot_hot";
        } else if(item == ResourceItemsME.TIN_INGOT) {
            idPath = "cube_ingot_hot";
        } else if(item == ResourceItemsME.LEAD_INGOT) {
            idPath = "tall_small_ingot_hot";
        } else if(item == ResourceItemsME.EDHEL_STEEL_INGOT || item == ResourceItemsME.MITHRIL_INGOT) {
            idPath = "small_ingot_hot";
        } else if(item == ResourceItemsME.KHAZAD_STEEL_INGOT) {
            idPath = "tall_ingot_hot";
        } else if(item == ResourceItemsME.BURZUM_STEEL_INGOT) {
            idPath = "thick_ingot_hot";
        }

        Identifier textureId = MiddleEarth.ofPath( "item", idPath);
        ItemModel.Unbaked unbakedHotItem = ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, "_hot"),
                TextureMapping.layer0(new Material(textureId)), itemModelGenerator.modelOutput));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

    public final void registerHotNuggetItem(Item item, ItemModelGenerators itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked unbakedHotItem = ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item, "_hot"),
                TextureMapping.layer0(new Material(MiddleEarth.ofPath( "item", "nugget_hot"))), itemModelGenerator.modelOutput));

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

    public final void registerHotItem(Item item, ItemModelGenerators itemModelGenerator) {
        ItemModel.Unbaked unbakedItem = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
        // Fall back to the plain model when the heated texture does not exist.
        ItemModel.Unbaked unbakedHotItem = itemTextureExists(BuiltInRegistries.ITEM.getKey(item).getPath() + "_hot")
                ? ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(item, "_hot", ModelTemplates.FLAT_ITEM))
                : unbakedItem;

        itemModelGenerator.itemModelOutput.accept(item, ItemModelUtils.conditional(new HotComponentProperty(), unbakedHotItem, unbakedItem));
    }

    private boolean itemTextureExists(String texturePath) {
        return FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).stream().flatMap(container -> container.getRootPaths().stream())
                .anyMatch(root -> java.nio.file.Files.exists(root.resolve("assets/" + MiddleEarth.MOD_ID + "/textures/item/" + texturePath + ".png")));
    }

}
