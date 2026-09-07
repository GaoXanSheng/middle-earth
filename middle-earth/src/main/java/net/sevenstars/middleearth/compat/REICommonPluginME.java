package net.sevenstars.middleearth.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;

// TODO (REI compat, 26.2): display population in registerDisplays requires
// dev.architectury.utils.GameInstance which is not on the compile classpath.
// Re-enabled once a 26.2-compatible architectury dependency is declared
// (main agent owns build.gradle). Serializers/categories still registered.
public class REICommonPluginME implements REICommonPlugin {
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
        registry.register(MiddleEarth.of("artisan_table"), ArtisanTableDisplay.SERIALIZER);
        registry.register(MiddleEarth.of("forge"), AlloyingDisplay.SERIALIZER);
    }
}
