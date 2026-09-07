package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelLayersWT;

import java.util.Map;

public class SwanEntityRenderer  extends MobRenderer<SwanEntity, SwanEntityRenderState, SwanEntityModel> {
    private static final String PATH = "textures/entity/swan/";
    AdultAndBabyModelPair<SwanEntityModel> babyModelPair;

    public SwanEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new SwanAdultModel(context.bakeLayer(EntityModelLayersWT.SWAN)), 0.5f);
        babyModelPair = new AdultAndBabyModelPair<>(new SwanAdultModel(context.bakeLayer(EntityModelLayersWT.SWAN)), new SwanBabyModel(context.bakeLayer(EntityModelLayersWT.SWAN_BABY)));
    }

    public static final Map<SwanEntityVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SwanEntityVariant.class), (map) -> {
                map.put(SwanEntityVariant.WHITE,
                        PATH + "swan_white.png");
                map.put(SwanEntityVariant.BLACK,
                        PATH + "swan_black.png");
                map.put(SwanEntityVariant.TRUMPETER,
                        PATH + "swan_trumpeter.png");
                map.put(SwanEntityVariant.WHOOPER,
                        PATH + "swan_whooper.png");
            });

    @Override
    public Identifier getTextureLocation(SwanEntityRenderState state) {
        return state.isBaby ? Identifier.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, PATH + "swan_baby.png") : Identifier.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, LOCATION_BY_VARIANT.get(state.variant));
    }

    @Override
    public void submit(SwanEntityRenderState state, PoseStack matrixStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        this.model = babyModelPair.getModel(state.isBaby);
        super.submit(state, matrixStack, submitNodeCollector, cameraRenderState);
    }

    @Override
    public SwanEntityRenderState createRenderState() {
        return new SwanEntityRenderState();
    }

    public void updateRenderState(SwanEntity swan, SwanEntityRenderState swanEntityRenderState, float f) {
        super.extractRenderState(swan, swanEntityRenderState, f);
        swanEntityRenderState.variant = swan.getVariant();
        swanEntityRenderState.sleepingAnimationState = swan.sleepingAnimationState;
        swanEntityRenderState.swimmingAnimationState = swan.swimmingAnimationState;
        swanEntityRenderState.intimidateAnimationState = swan.intimidateAnimationState;
        swanEntityRenderState.eatAnimationState = swan.eatAnimationState;
        swanEntityRenderState.swimIdleAnimationState = swan.swimIdleAnimationState;
        swanEntityRenderState.flapAnimationState = swan.flapAnimationState;
    }
}
