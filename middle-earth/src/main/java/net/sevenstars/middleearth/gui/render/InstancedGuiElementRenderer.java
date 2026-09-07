package net.sevenstars.middleearth.gui.render;

import net.minecraft.client.gui.render.pip.PictureInPictureRenderer;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.sevenstars.middleearth.gui.render.states.InstancedGuiElementRenderState;

public abstract class InstancedGuiElementRenderer<T extends InstancedGuiElementRenderState> extends PictureInPictureRenderer<T> {
    private boolean usedThisFrame;

    protected InstancedGuiElementRenderer() {
        super();
    }

    public final boolean usedThisFrame() {
        return this.usedThisFrame;
    }

    public final void resetUsedThisFrame() {
        this.usedThisFrame = false;
    }

    @Override
    public void prepare(T specialGuiElementRenderState, GuiRenderState guiRenderState, FeatureRenderDispatcher featureRenderDispatcher, int windowScaleFactor) {
        this.usedThisFrame = true;
        super.prepare(specialGuiElementRenderState, guiRenderState, featureRenderDispatcher, windowScaleFactor);
    }
}
