package net.sevenstars.middleearth.compat;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableCategory;
import net.sevenstars.middleearth.compat.artisantable.ArtisanTableDisplay;
import net.sevenstars.middleearth.compat.forge.AlloyingCategory;
import net.sevenstars.middleearth.compat.forge.AlloyingDisplay;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

// TODO (REI compat, 26.2): registerScreens (click areas) needs me.shedaniel.math.Rectangle which is
// not on the compile classpath; re-enabled once the dependency is declared (main agent owns build.gradle).
@Environment(EnvType.CLIENT)
public class REIClientPluginME implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new ArtisanTableCategory());
        registry.add(new AlloyingCategory());
        // TODO (REI compat, 26.2): addWorkstations resolution requires dev.architectury.fluid.FluidStack
        // (not on compile classpath) - restore once architectury is declared (main agent owns build.gradle).
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        REIClientPlugin.super.registerDisplays(registry);
        registry.beginFiller(ArtisanRecipe.class)
                .fill(ArtisanTableDisplay::new);
        registry.beginFiller(AlloyingRecipe.class)
                .fill(AlloyingDisplay::new);
    }
}
