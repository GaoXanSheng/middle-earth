package net.sevenstars.middleearth.entity.npcs.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.npcs.renderer.features.ear.EarFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.feet.FeetFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.hair.HairFeatureRenderer;
import net.sevenstars.middleearth.entity.npcs.renderer.features.nose.NoseFeatureRenderer;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.utils.ItemTagsME;
import org.jetbrains.annotations.Nullable;

public class NpcEntityRenderer extends HumanoidMobRenderer<NpcEntity, NpcEntityRenderState, NpcEntityModel> {
    public final static int HURT_COLOR = 0xff7e75;

    public final static int LIGHT_LEVEL_EMISSIVE_EYES = 8;
    public final static int BLINKING_INTERVAL = 80;
    public final static int BLINKING_DURATION = 3;

    public NpcEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new NpcEntityModel(context.bakeLayer(EntityModelLayersME.NPC)), 0.7f);

        this.layers.removeIf(x -> x.getClass() == WingsLayer.class);
        this.layers.removeIf(x -> x.getClass() == CustomHeadLayer.class);

        this.addLayer(new NpcBodyTextureLayer(this, context.getModelSet()));
        this.addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer()));
        this.addLayer(new HairFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new EarFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new NoseFeatureRenderer(this, context.getModelSet()));
        this.addLayer(new FeetFeatureRenderer(this, context.getModelSet()));


        this.shadowRadius = 0.5f;
    }

    // region [RenderState]
    @Override
    public NpcEntityRenderState createRenderState() {
        return new NpcEntityRenderState();
    }

    public static float getLOD(Vec3 entityPos) {
        Minecraft client = Minecraft.getInstance();
        Camera camera = client.gameRenderer.mainCamera();
        return (float)camera.position().distanceTo(entityPos);
        // Keep in case it's needed
        /*int currentFov = client.options.getFov().getValue();
        double fovRatio = currentFov / 90.0;
        return (float) (distance * fovRatio);*/
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void extractRenderState(NpcEntity npcEntity, NpcEntityRenderState npcEntityRenderState, float tickDelta) {
        if(!npcEntity.level().isClientSide())
            return;

        npcEntityRenderState.aimingState = npcEntity.aimingState;

        super.extractRenderState(npcEntity, npcEntityRenderState, tickDelta);
        NpcTextureData npcTextureData = npcEntity.retrieveNpcTextureData();
        float currentLightLevel = npcEntity.level().getMaxLocalRawBrightness(npcEntity.blockPosition());

        npcEntityRenderState.pose = npcEntity.getPose();

        npcEntityRenderState.leftArmPose = getArmPose(npcEntity, npcEntity.getItemInHand(InteractionHand.OFF_HAND), InteractionHand.OFF_HAND);
        npcEntityRenderState.rightArmPose = getArmPose(npcEntity, npcEntity.getItemInHand(InteractionHand.MAIN_HAND), InteractionHand.MAIN_HAND);

        npcEntityRenderState.widthScale = npcEntity.getWidthScale();

        npcEntityRenderState.simplifiedSkinId = npcTextureData.getSimplifiedSkin();
        npcEntityRenderState.simplifiedEarId = npcTextureData.getSimplifiedEar();
        npcEntityRenderState.simplifiedFeetId = npcTextureData.getSimplifiedFeet();
        npcEntityRenderState.simplifiedHairAddonId = npcTextureData.getSimplifiedHair();
        npcEntityRenderState.simplifiedNoseId = npcTextureData.getSimplifiedNose();

        if(!ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING || npcEntityRenderState.simplifiedSkinId == null){
            npcEntityRenderState.skinId = npcTextureData.get(NpcRenderedPart.BODY);
            npcEntityRenderState.feetId = npcTextureData.get(NpcRenderedPart.FEET);
            npcEntityRenderState.headId = npcTextureData.get(NpcRenderedPart.HEAD);
            npcEntityRenderState.earId = npcTextureData.get(NpcRenderedPart.EAR);
            npcEntityRenderState.noseId = npcTextureData.get(NpcRenderedPart.NOSE);
            npcEntityRenderState.eyesId = npcTextureData.get(NpcRenderedPart.EYE);
            npcEntityRenderState.eyesEmissiveId = npcTextureData.get(NpcRenderedPart.EYE_EMISSIVE);
            npcEntityRenderState.haveEmissiveEyes = npcTextureData.isEyeEmissive() && currentLightLevel <= LIGHT_LEVEL_EMISSIVE_EYES;
            npcEntityRenderState.eyebrowId = npcTextureData.get(NpcRenderedPart.EYEBROW);
            npcEntityRenderState.scarId = npcTextureData.get(NpcRenderedPart.SCAR);
            npcEntityRenderState.beardId = npcTextureData.get(NpcRenderedPart.BEARD);
            npcEntityRenderState.beardAddonId = npcTextureData.get(NpcRenderedPart.BEARD_ADDON);
            npcEntityRenderState.hairId = npcTextureData.get(NpcRenderedPart.HAIR);
            npcEntityRenderState.hairAddonId = npcTextureData.get(NpcRenderedPart.HAIR_ADDON);

            npcEntityRenderState.clothingBase = npcTextureData.get(NpcRenderedPart.CLOTHING_BASE);
            npcEntityRenderState.clothingOver = npcTextureData.get(NpcRenderedPart.CLOTHING_OVER);
            npcEntityRenderState.clothingExtra = npcTextureData.get(NpcRenderedPart.CLOTHING_EXTRA);

            long age = npcEntity.tickCount;
            npcEntityRenderState.blinking = (0 + age) % BLINKING_INTERVAL >= BLINKING_INTERVAL - BLINKING_DURATION;
        }
        ItemStack currentHelmet = npcEntity.getItemBySlot(EquipmentSlot.HEAD);
        if(currentHelmet == null || currentHelmet.isEmpty()){
            npcEntityRenderState.canShowBeard = true;
            npcEntityRenderState.canShowHair = true;
            npcEntityRenderState.canShowEars = true;
        } else {
            var hasAttachment = currentHelmet.get(DataComponentTypesME.HELMET_ATTACHMENT_DATA);
            boolean hasHoodDown = hasAttachment == null || hasAttachment.down();

            npcEntityRenderState.canShowEars = currentHelmet.is(ItemTagsME.CHARACTER_HELMET_SHOW_EARS) && hasHoodDown;
            npcEntityRenderState.canShowBeard = !currentHelmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_BEARD);
            npcEntityRenderState.canShowHair = !currentHelmet.is(ItemTagsME.CHARACTER_HELMET_HIDE_HAIR) && hasHoodDown;
        }
        ItemStack currentShoes = npcEntity.getItemBySlot(EquipmentSlot.FEET);
        npcEntityRenderState.canShowFeet = currentShoes == null || currentShoes.isEmpty();
        npcEntityRenderState.LOD = getLOD(npcEntity.position());
    }
    // endregion

    // region [Layered Texture Renderer]
    @Nullable
    @Override
    protected RenderType getRenderType(NpcEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
        // The body is fully drawn by NpcBodyTextureLayer with atlas sprites; submitting the base
        // model as well would bind the whole atlas page with unmapped UVs (texture-sheet garbage).
        // Only the glowing outline still needs a texture-bound render type.
        return showOutline ? RenderTypes.outline(this.getTextureLocation(state)) : null;
    }

    @Override
    public void submit(NpcEntityRenderState state, PoseStack matrices, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (state.isPassenger) {
            matrices.pushPose();
            matrices.translate(0, -0.5F, 0);
            super.submit(state, matrices, submitNodeCollector, cameraRenderState);
            matrices.popPose();
            return;
        }
        super.submit(state, matrices, submitNodeCollector, cameraRenderState);
    }

    @Override
    protected void scale(NpcEntityRenderState state, PoseStack matrices) {
        float widthScale = state.widthScale;
        matrices.scale(widthScale * widthScale, 1.0f, widthScale * widthScale);
    }

    @Override
    protected int getModelTint(NpcEntityRenderState state) {
        if(state.hasRedOverlay)
            return HURT_COLOR;
        return super.getModelTint(state);
    }

    @Override
    public Identifier getTextureLocation(NpcEntityRenderState state) {
        // The body layers bind atlas sprites directly in submit(); this texture is only used by
        // the glowing outline render type.
        return AtlasesME.getAtlasPath(AtlasesME.CHARACTER_TEXTURES);
    }

    private HumanoidModel.ArmPose getArmPose(NpcEntity npc, ItemStack stack, InteractionHand hand) {
        if(npc.isAiming()){
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }

        if (stack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        if (!npc.swinging && (stack.is(Items.CROSSBOW) || stack.is(ItemTagsME.CROSSBOW))) {
            if(CrossbowItem.isCharged(stack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            else if(npc.isCharging()) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        }
        if (npc.getUsedItemHand() == hand && npc.getUseItemRemainingTicks() > 0) {
            ItemUseAnimation useAction = stack.getUseAnimation();
            if (useAction == ItemUseAnimation.BLOCK || npc.isBlocking()) {
                return HumanoidModel.ArmPose.BLOCK;
            }
            if (useAction == ItemUseAnimation.BOW) {
                return HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
            if (useAction == ItemUseAnimation.SPEAR) {
                return HumanoidModel.ArmPose.SPEAR;
            }
            if (useAction == ItemUseAnimation.CROSSBOW) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
            if (useAction == ItemUseAnimation.SPYGLASS) {
                return HumanoidModel.ArmPose.SPYGLASS;
            }
            if (useAction == ItemUseAnimation.TOOT_HORN) {
                return HumanoidModel.ArmPose.TOOT_HORN;
            }
            if (useAction == ItemUseAnimation.BRUSH) {
                return HumanoidModel.ArmPose.BRUSH;
            }
        }
        return HumanoidModel.ArmPose.ITEM;
    }
    // endregion
}
