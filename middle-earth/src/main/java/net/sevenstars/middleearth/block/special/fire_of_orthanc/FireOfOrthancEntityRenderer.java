package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class FireOfOrthancEntityRenderer extends EntityRenderer<FireOfOrthancEntity, FireOfOrthancEntityRenderState> {

    public FireOfOrthancEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.5F;
    }

    @Override
    public FireOfOrthancEntityRenderState createRenderState() {
        return new FireOfOrthancEntityRenderState();
    }

    // TODO: re-port to 26.2 render pipeline (used to render the FIRE_OF_ORTHANC block like a falling block entity)
}
