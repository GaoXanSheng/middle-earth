package net.sevenstars.middleearth.gui.render.states;

import net.minecraft.client.renderer.state.gui.pip.PictureInPictureRenderState;
import net.sevenstars.middleearth.gui.render.InstancedGuiElementRenderer;

public interface InstancedGuiElementRenderState extends PictureInPictureRenderState {
    InstancedGuiElementRenderer<? extends InstancedGuiElementRenderState> newRenderer();
}
