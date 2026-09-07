package net.sevenstars.of_beasts_and_wild_things.entity.pheasant;

import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class PheasantEntityRenderer extends MobRenderer<PheasantEntity, PheasantEntityRenderState, PheasantEntityModel> {
    private static final String PATH = "textures/entity/pheasant/";

    public PheasantEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new PheasantEntityModel(context.bakeLayer(EntityModelLayersWT.PHEASANT)), 0.2f);
    }

    public static final Map<PheasantEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PheasantEntityVariant.class), (map) -> {
                map.put(PheasantEntityVariant.MALE,
                        PATH + "pheasant_male.png");
                map.put(PheasantEntityVariant.FEMALE,
                        PATH + "pheasant_female.png");
            });

    @Override
    public Identifier getTextureLocation(PheasantEntityRenderState state) {
        return OfBeastsAndWildThings.of(LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public PheasantEntityRenderState createRenderState() {
        return new PheasantEntityRenderState();
    }

    public void updateRenderState(PheasantEntity pheasantEntity, PheasantEntityRenderState pheasantEntityRenderState, float f) {
        super.extractRenderState(pheasantEntity, pheasantEntityRenderState, f);
        pheasantEntityRenderState.variant = pheasantEntity.getVariant();
        pheasantEntityRenderState.idleAnimationState.copyFrom(pheasantEntity.idleAnimationState);
        pheasantEntityRenderState.diggingAnimationState.copyFrom(pheasantEntity.diggingAnimationState);
        pheasantEntityRenderState.flapAnimationState.copyFrom(pheasantEntity.flapAnimationState);
    }
}