package net.sevenstars.middleearth.item.utils;

import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.equipment.trim.MaterialAssetGroup;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.Map;

public class SmithingTrimMaterialsME {
    public static final ResourceKey<TrimMaterial> BRONZE = of("bronze");
    public static final ResourceKey<TrimMaterial> BURZUM_STEEL = of("burzum_steel");
    public static final ResourceKey<TrimMaterial> CRUDE = of("crude");
    public static final ResourceKey<TrimMaterial> EDHEL_STEEL = of("edhel_steel");
    public static final ResourceKey<TrimMaterial> JADE = of("jade");
    public static final ResourceKey<TrimMaterial> KHAZAD_STEEL = of("khazad_steel");
    public static final ResourceKey<TrimMaterial> LEAD = of("lead");
    public static final ResourceKey<TrimMaterial> MITHRIL = of("mithril");
    public static final ResourceKey<TrimMaterial> SILVER = of("silver");
    public static final ResourceKey<TrimMaterial> STEEL = of("steel");
    public static final ResourceKey<TrimMaterial> TIN = of("tin");

    public static void bootstrap(BootstrapContext<TrimMaterial> registry) {
        register(registry, BRONZE, Style.EMPTY.withColor(13151627), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("bronze"), Map.of()));
        register(registry, BURZUM_STEEL, Style.EMPTY.withColor(5985355), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("burzum_steel"), Map.of()));
        register(registry, CRUDE, Style.EMPTY.withColor(7560021), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("crude"), Map.of()));
        register(registry, EDHEL_STEEL, Style.EMPTY.withColor(15921385), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("edhel_steel"), Map.of()));
        register(registry, JADE, Style.EMPTY.withColor(5869927), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("jade"), Map.of()));
        register(registry, KHAZAD_STEEL, Style.EMPTY.withColor(6778743), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("khazad_steel"), Map.of()));
        register(registry, LEAD, Style.EMPTY.withColor(6384761), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("lead"), Map.of()));
        register(registry, MITHRIL, Style.EMPTY.withColor(14278631), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("mithril"), Map.of()));
        register(registry, SILVER, Style.EMPTY.withColor(15397618), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("silver"), Map.of()));
        register(registry, STEEL, Style.EMPTY.withColor(0xECECEC), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("steel"), Map.of()));
        register(registry, TIN, Style.EMPTY.withColor(13026492), new MaterialAssetGroup(new MaterialAssetGroup.AssetInfo("tin"), Map.of()));
    }

    private static void register(BootstrapContext<TrimMaterial> registry, ResourceKey<TrimMaterial> key, Style style, MaterialAssetGroup assets) {
        Component text = Component.translatable(Util.makeDescriptionId("trim_material", key.identifier())).withStyle(style);
        registry.register(key, new TrimMaterial(assets, text));
    }

    private static ResourceKey<TrimMaterial> of(String id) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, id));
    }
}
