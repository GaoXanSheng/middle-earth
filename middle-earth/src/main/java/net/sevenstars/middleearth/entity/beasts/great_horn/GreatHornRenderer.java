package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.google.common.collect.Maps;
import net.minecraft.util.Util;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornArmorFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornNoseFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.great_horn.features.GreatHornSaddleFeatureRenderer;

import java.util.Map;

public class GreatHornRenderer extends AgeableMobRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererProvider.Context context) {
        super(context, new GreatHornModel(context.bakeLayer(EntityModelLayersME.GREAT_HORN)),
                new GreatHornModel(context.bakeLayer(EntityModelLayersME.GREAT_HORN_BABY)), 0.95f);
        this.addLayer(new GreatHornSaddleFeatureRenderer(this,  context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new GreatHornArmorFeatureRenderer(this,  context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new GreatHornNoseFeatureRenderer(this,  context.getModelSet()));
    }

    protected float getShadowRadius(GreatHornEntityRenderState greatHornEntityRenderState) {
        float f = super.getShadowRadius(greatHornEntityRenderState);
        return greatHornEntityRenderState.isBaby ? f * 0.8F : f;
    }

    @Override
    public GreatHornEntityRenderState createRenderState() {
        return new GreatHornEntityRenderState();
    }

    public static final Map<GreatHornVariantDep, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GreatHornVariantDep.class), (map) -> {
                map.put(GreatHornVariantDep.BROWN,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "brown_great_horn.png"));
                map.put(GreatHornVariantDep.TEMPERATE,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "temperate_great_horn.png"));
                map.put(GreatHornVariantDep.COLD,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "cold_great_horn.png"));
                map.put(GreatHornVariantDep.WARM,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warm_great_horn.png"));
                map.put(GreatHornVariantDep.WHITE,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "white_great_horn.png"));

            });

    @Override
    public Identifier getTextureLocation(GreatHornEntityRenderState state) {
        return state.greatHornVariant.assetInfo().id();
    }

    public void updateRenderState(GreatHornEntity greatHornEntity, GreatHornEntityRenderState state, float f) {
        super.extractRenderState(greatHornEntity, state, f);

        state.idleAnimationState.copyFrom(greatHornEntity.idleAnimationState);
        state.earWiggleAnimationState.copyFrom(greatHornEntity.earWigglingAnimationState);
        state.gallopAnimationState.copyFrom(greatHornEntity.gallopAnimationState);
        state.bowAnimationState.copyFrom(greatHornEntity.bowAnimationState);
        state.attackAnimationState.copyFrom(greatHornEntity.attackAnimationState);
        state.saddle = greatHornEntity.getItemBySlot(EquipmentSlot.SADDLE);
        state.armor = greatHornEntity.getItemBySlot(EquipmentSlot.BODY);
        state.blueSaddle = greatHornEntity.hasBlueSaddle();
        state.hasRider = greatHornEntity.hasExactlyOnePlayerPassenger();
        state.greatHornVariant = greatHornEntity.getVariant();
    }
}
