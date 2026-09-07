package net.sevenstars.middleearth.compat.forge;

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
public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return REICommonPluginME.FORGE_CATEGORY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + ".forge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModDecorativeBlocks.FORGE.asItem().getDefaultInstance());
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
