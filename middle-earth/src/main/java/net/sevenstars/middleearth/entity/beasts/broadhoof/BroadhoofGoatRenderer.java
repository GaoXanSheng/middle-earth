package net.sevenstars.middleearth.entity.beasts.broadhoof;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatBeadsFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatPatternFeatureRenderer;
import net.sevenstars.middleearth.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;

import java.util.Map;

public class BroadhoofGoatRenderer extends MobRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";
    private static final float SIZE = 1f;

    public BroadhoofGoatRenderer(EntityRendererProvider.Context context) {
        super(context, new BroadhoofGoatModel(context.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT)), 0.8f);
        this.addLayer(new BroadhoofGoatPatternFeatureRenderer(this));
        this.addLayer(new BroadhoofGoatBeadsFeatureRenderer(this));
        this.addLayer(
                new SimpleEquipmentLayer<>(
                        this,
                        context.getEquipmentRenderer(),
                        EquipmentClientInfo.LayerType.HORSE_BODY,
                        broadhoofGoatEntityRenderState -> broadhoofGoatEntityRenderState.armor,
                        new BroadhoofGoatModel(context.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_ARMOR)),
                        new BroadhoofGoatModel(context.bakeLayer(EntityModelLayersME.BROADHOOF_GOAT_ARMOR))
                )
        );
        this.addLayer(new BroadhoofGoatSaddleFeatureRenderer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Override
    protected void scale(BroadhoofGoatEntityRenderState state, PoseStack matrixStack) {
        float f = state.isBaby ? SIZE / 2 : SIZE;
        matrixStack.scale(f, f, f);
    }

    @Override
    public BroadhoofGoatEntityRenderState createRenderState() {
        return new BroadhoofGoatEntityRenderState();
    }

    private static final Map<BroadhoofGoatColor, Identifier> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatColor.WHITE,
                    MiddleEarth.of(PATH + "broadhoof_goat_white.png"),
                    BroadhoofGoatColor.LIGHT_GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_light_gray.png"),
                    BroadhoofGoatColor.PALE,
                    MiddleEarth.of(PATH + "broadhoof_goat_pale.png"),
                    BroadhoofGoatColor.RED,
                    MiddleEarth.of(PATH + "broadhoof_goat_red.png"),
                    BroadhoofGoatColor.BROWN,
                    MiddleEarth.of(PATH + "broadhoof_goat_brown.png"),
                    BroadhoofGoatColor.GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_gray.png"),
                    BroadhoofGoatColor.BLACK,
                    MiddleEarth.of(PATH + "broadhoof_goat_black.png")
            )
    );

    public void updateRenderState(BroadhoofGoatEntity goat, BroadhoofGoatEntityRenderState state, float f) {
        super.extractRenderState(goat, state, f);

        state.color = goat.getGoatColor();
        state.pattern = goat.getPattern();
        state.armor = goat.getBodyArmorItem().copy();

        state.beads = goat.getGoatBeads();
        state.horns = goat.getHorns();
        state.hair = goat.hasHair();
        state.hasLeftHorn = goat.hasLeftHorn();
        state.hasRightHorn = goat.hasRightHorn();
        state.beardBrushed = goat.hasBrushedBeard();

        state.isSprinting = goat.isSprinting();
        state.isCharging = goat.isCharging();
        state.isTame = goat.isTamed();
        state.conrollingPassenger = goat.getControllingPassenger();
        state.saddle = goat.getItemBySlot(EquipmentSlot.SADDLE);
        state.armor = goat.getBodyArmorItem();

        state.chargeAnimationState = goat.chargeAnimationState;
        state.startSittingAnimationState = goat.startSittingAnimationState;
        state.stopSittingAnimationState = goat.stopSittingAnimationState;
        state.sittingAnimationState = goat.sittingAnimationState;
        state.attackAnimationState = goat.attackAnimationState;
        state.idleAnimationState = goat.idleAnimationState;
        state.jumpAnimationState = goat.jumpAnimationState;

    }

    @Override
    public Identifier getTextureLocation(BroadhoofGoatEntityRenderState state) {
        return TEXTURES.get(state.color);
    }
}
