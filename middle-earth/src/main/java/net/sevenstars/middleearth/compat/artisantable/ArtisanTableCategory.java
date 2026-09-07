package net.sevenstars.middleearth.compat.artisantable;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.compat.REICommonPluginME;

// TODO (REI compat, 26.2): setupDisplay layout was removed because it requires me.shedaniel.math
// (Point/Rectangle), not on the compile classpath. Restore once declared (main agent owns build.gradle).
@Environment(EnvType.CLIENT)
public class ArtisanTableCategory implements DisplayCategory<ArtisanTableDisplay> {

    @Override
    public CategoryIdentifier<? extends ArtisanTableDisplay> getCategoryIdentifier() {
        return REICommonPluginME.ARTISAN_TABLE_CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + MiddleEarth.MOD_ID + ".artisan_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.ARTISAN_TABLE.asItem().getDefaultInstance());
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
