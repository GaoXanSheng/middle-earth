package net.sevenstars.middleearth.entity.seat;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SeatRenderer extends EntityRenderer<SeatEntity, SeatEntityRenderState> {

    public SeatRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public SeatEntityRenderState createRenderState() {
        return new SeatEntityRenderState();
    }
}
