package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.Maps;
import net.minecraft.util.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollDroolFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollHeldItemFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.cave_troll.feature.CaveTrollSaddleFeatureRenderer;

import java.util.Map;

public class CaveTrollRenderer extends MobRenderer<CaveTrollEntity, CaveTrollEntityRenderState, CaveTrollEntityModel> {
    private static final String PATH = "textures/entities/trolls/cave/cave_troll_";
    private static final String TEXTURE_ANG = "textures/entities/trolls/cave/cave_troll_ang.png";
    private static final String TEXTURE_ANGRY_ANG = "textures/entities/trolls/cave/cave_troll_angry_ang.png";
    public CaveTrollRenderer(EntityRendererProvider.Context context) {
        super(context, new CaveTrollEntityModel(context.bakeLayer(EntityModelLayersME.CAVE_TROLL)), 1.1f);
        this.addLayer(new CaveTrollDroolFeatureRenderer(this));
        this.addLayer(new CaveTrollSaddleFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new CaveTrollHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTextureLocation(CaveTrollEntityRenderState state) {
        boolean isCalm = (state.tameness > 25 || !state.isTame) && !state.isEnraged;
        if(state.nameTag != null && state.nameTag.getString().equals("Angmarzku")) {
            return isCalm ?
                    MiddleEarth.of(TEXTURE_ANG) :
                    MiddleEarth.of(TEXTURE_ANGRY_ANG);
        }
        return isCalm ?
                MiddleEarth.of(PATH + LOCATION_BY_VARIANT.get(state.variant) + ".png") :
                MiddleEarth.of(PATH +  LOCATION_BY_VARIANT.get(state.variant) + "_angry.png");
    }

    public static final Map<CaveTrollVariant, String> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CaveTrollVariant.class), (map) -> {
                map.put(CaveTrollVariant.GREEN, "green");
                map.put(CaveTrollVariant.YELLOW, "yellow");
                map.put(CaveTrollVariant.BROWN, "brown");
                map.put(CaveTrollVariant.BLUE, "blue");
                map.put(CaveTrollVariant.GRAY, "gray");
                map.put(CaveTrollVariant.STONE, "stone");
                map.put(CaveTrollVariant.GREY_BLUE, "grey_blue");
            });

    @Override
    public CaveTrollEntityRenderState createRenderState() {
        return new CaveTrollEntityRenderState();
    }

    public void updateRenderState(CaveTrollEntity troll, CaveTrollEntityRenderState state, float f) {
        super.extractRenderState(troll, state, f);
        CaveTrollEntityRenderState.updateRenderState(troll, state, this.itemModelResolver);

        state.chaseAnimationState = troll.chaseAnimationState;
        state.scavengingAnimationState = troll.scavengingAnimationState;
        state.startSittingAnimationState = troll.startSittingAnimationState;
        state.stopSittingAnimationState = troll.stopSittingAnimationState;
        state.startSleepingAnimationState = troll.startSleepingAnimationState;
        state.sleepingAnimationState = troll.sleepingAnimationState;
        state.stopSleepingAnimationState = troll.stopSleepingAnimationState;
        state.roaringAnimationState = troll.roaringAnimationState;
        state.smashingAnimationState = troll.smashingAnimationState;
        state.isSprinting = troll.isSprinting();
        state.isCharging = troll.isCharging();
        state.isEnraged = troll.isEnraged();
        state.variant = troll.getVariant();
        state.isTame = troll.isTamed();
        state.conrollingPassenger = troll.getControllingPassenger();
        state.saddle = troll.getItemBySlot(EquipmentSlot.SADDLE);
        state.tameness = troll.getTameness();
    }
}
