package net.sevenstars.middleearth.entity.beasts.warg;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Util;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.warg.features.*;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import java.util.Map;

public class WargRenderer extends MobRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/";
    private static final float SIZE = 1f;
    private static final int LIGHT_LEVEL_EMISSIVE_EYES = 8;

    public WargRenderer(EntityRendererProvider.Context context) {
        super(context, new WargModel(context.bakeLayer(EntityModelLayersME.WARG)), 0.8f);
        this.addLayer(new WargEyesFeatureRenderer(this));
        this.addLayer( // Armor Feature
                new SimpleEquipmentLayer<>(
                        this,
                        context.getEquipmentRenderer(),
                        EquipmentClientInfo.LayerType.HORSE_BODY,
                        state -> state.armor,
                        new WargModel(context.bakeLayer(EntityModelLayersME.WARG_ARMOR)),
                        new WargModel(context.bakeLayer(EntityModelLayersME.WARG_ARMOR))
                )
        );
        this.addLayer(new WargArmorSpineFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new WargArmorSideSkullsFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new WargArmorFrontSkullFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new WargArmorBackSkullFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
        this.addLayer(new WargSaddleFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Override
    protected void scale(WargEntityRenderState state, PoseStack matrixStack) {
        float f = state.isBaby ? SIZE / 2 : SIZE;
        matrixStack.scale(f, f, f);
    }

    @Override
    public WargEntityRenderState createRenderState() {
        return new WargEntityRenderState();
    }

    public static final Map<WargVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargVariant.class), (map) -> {
                map.put(WargVariant.BROWN,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_brown.png"));
                map.put(WargVariant.BLACK,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_black.png"));
                map.put(WargVariant.GRAY,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_gray.png"));
                map.put(WargVariant.LIGHT_GRAY,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_light_gray.png"));
                map.put(WargVariant.SNOW,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_snow.png"));
                map.put(WargVariant.MOTTLED,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_mottled.png"));
                map.put(WargVariant.TAN,
                        Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, PATH + "warg_tan.png"));
            });

    @Override
    public Identifier getTextureLocation(WargEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }

    public void updateRenderState(WargEntity warg, WargEntityRenderState state, float f) {
        super.extractRenderState(warg, state, f);

        state.variant = warg.getVariant();
        state.eyeVariant = warg.getEyeVariant();

        state.isSprinting = warg.isSprinting();
        state.isRunning = warg.isRunning();
        state.isCharging = warg.isCharging();
        state.isTame = warg.isTamed();
        state.conrollingPassenger = warg.getControllingPassenger();
        state.saddle = warg.getItemBySlot(EquipmentSlot.SADDLE);
        state.armor = warg.getBodyArmorItem();
        state.haveEmissiveEyes = warg.level().isDarkOutside() || warg.level().getMaxLocalRawBrightness(warg.blockPosition()) <= LIGHT_LEVEL_EMISSIVE_EYES;

        state.chargeAnimationState = warg.chargeAnimationState;
        state.startSittingAnimationState = warg.startSittingAnimationState;
        state.stopSittingAnimationState = warg.stopSittingAnimationState;
        state.sittingAnimationState = warg.sittingAnimationState;
        state.attackAnimationState = warg.attackAnimationState;
        state.idleAnimationState = warg.idleAnimationState;
    }
}
