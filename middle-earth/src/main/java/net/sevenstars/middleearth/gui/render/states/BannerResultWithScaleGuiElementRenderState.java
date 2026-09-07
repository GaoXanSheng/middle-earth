package net.sevenstars.middleearth.gui.render.states;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.gui.render.BannerResultWithScaleGuiElementRenderer;
import net.sevenstars.middleearth.gui.render.InstancedGuiElementRenderer;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public record BannerResultWithScaleGuiElementRenderState(ModelPart flag, DyeColor baseColor, BannerPatternLayers resultBannerPatterns, int x1, int y1, int x2, int y2, float scale, @Nullable ScreenRectangle scissorArea, @Nullable ScreenRectangle bounds) implements InstancedGuiElementRenderState {
    public BannerResultWithScaleGuiElementRenderState(ModelPart flag, DyeColor color, BannerPatternLayers bannerPatterns, int x1, int y1, int x2, int y2, float scale, @Nullable ScreenRectangle scissorArea) {
        this(flag, color, bannerPatterns, x1, y1, x2, y2, scale, scissorArea, PictureInPictureRenderState.getBounds(x1, y1, x2, y2, scissorArea));
    }

    @Override
    public int x0() {
        return x1;
    }

    @Override
    public int x1() {
        return x2;
    }

    @Override
    public int y0() {
        return y1;
    }

    @Override
    public int y1() {
        return y2;
    }

    @Override
    public InstancedGuiElementRenderer<? extends InstancedGuiElementRenderState> newRenderer() {
        return new BannerResultWithScaleGuiElementRenderer();
    }
}
