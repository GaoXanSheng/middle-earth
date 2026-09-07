package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Pose;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class DeerEntityRenderer extends MobRenderer<DeerEntity, DeerEntityRenderState, DeerEntityModel> {
    private static final String PATH = "textures/entity/deer/";

    public DeerEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DeerEntityModel(context.bakeLayer(EntityModelLayersWT.DEER)), 0.5f);
    }

    public static final Map<DeerEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(DeerEntityVariant.class), (map) -> {
                map.put(DeerEntityVariant.SPOTS,
                        PATH + "deer_spots.png");
                map.put(DeerEntityVariant.NO_SPOTS,
                        PATH + "deer_no_spots.png");
            });

    @Override
    public Identifier getTextureLocation(DeerEntityRenderState state) {
        return OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public DeerEntityRenderState createRenderState() {
        return new DeerEntityRenderState();
    }

    public void updateRenderState(DeerEntity deerEntity, DeerEntityRenderState deerEntityRenderState, float f) {
        super.extractRenderState(deerEntity, deerEntityRenderState, f);
        deerEntityRenderState.variant = deerEntity.getVariant();
        deerEntityRenderState.isRunning = deerEntity.getPose().equals(Pose.SHOOTING);
    }
}
