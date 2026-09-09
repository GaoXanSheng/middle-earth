package net.sevenstars.middleearth.entity.npcs.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.RenderUtil;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;

/**
 * Draws the layered NPC body textures (skin, head, eyes, clothes, hair, ...) as sprites of the
 * character atlas. The base model is deliberately not submitted by {@code NpcEntityRenderer}
 * (its getRenderType returns null), otherwise the humanoid model would be drawn once more with
 * the raw atlas page bound, smearing every sprite of the atlas across the body.
 */
@Environment(EnvType.CLIENT)
public class NpcBodyTextureLayer extends RenderLayer<NpcEntityRenderState, NpcEntityModel> {
    private TextureAtlas characterTextureAtlas;
    /**
     * Independent model instance dedicated to this layer. Submitting the renderer's shared
     * context model via submitModelPart would freeze whatever pose another NPC's submit had
     * last written into the shared parts, desynchronizing body/head from the armor (which is
     * rendered by independent armor models). This instance is re-posed per NPC at replay time
     * via submitModel(model, state, ...).
     */
    private final NpcEntityModel bodyModel;

    public NpcBodyTextureLayer(RenderLayerParent<NpcEntityRenderState, NpcEntityModel> context, EntityModelSet modelSet) {
        super(context);
        this.bodyModel = new NpcEntityModel(modelSet.bakeLayer(EntityModelLayersME.NPC));
    }

    @Override
    public void submit(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        boolean simplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        if (!simplified && (state.skinId == null || state.headId == null || state.eyesId == null))
            return;

        if (characterTextureAtlas == null) {
            characterTextureAtlas = AtlasesME.getAtlasFromPath(AtlasesME.CHARACTER_TEXTURES);
        }

        int overlay = OverlayTexture.pack(0.0f, state.hasRedOverlay);

        if (simplified) {
            renderTexture(matrices, submitNodeCollector, state, state.simplifiedSkinId, light, overlay, false);
        } else {
            renderComplexVersion(matrices, submitNodeCollector, light, overlay, state);
        }
    }

    private void renderComplexVersion(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, int overlay, NpcEntityRenderState state) {
        // Will always be shown
        renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.skinId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.headId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(!state.blinking){
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.eyesId, AtlasesME.EYE_PREFIX), light, overlay, false);
        }
        // Optionally shown, only if the value is present
        if(state.eyebrowId != null)
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.eyebrowId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.scarId != null)
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.scarId, AtlasesME.SKIN_PREFIX), light, overlay, false);

        if(state.beardId != null)
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.beardId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(state.clothingBase == null && state.clothingOver == null && state.clothingExtra == null){
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(CharacterClothesRegistryME.Base.THONG_BROWN, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);
        }
        else {
            if(state.clothingBase != null)
                renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.clothingBase, AtlasesME.CLOTHES_BASE_PREFIX), light, overlay, false);

            if(state.clothingOver != null)
                renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.clothingOver, AtlasesME.CLOTHES_OVER_PREFIX), light, overlay, false);

            if(state.clothingExtra != null)
                renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.clothingExtra, AtlasesME.CLOTHES_EXTRA_PREFIX), light, overlay, false);
        }

        if(state.hairId != null)
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.hairId, AtlasesME.HAIR_PREFIX), light, overlay, false);

        if(!state.blinking && state.haveEmissiveEyes){
            renderTexture(matrices, submitNodeCollector, state, MiddleEarth.ofPrefix(state.eyesEmissiveId, AtlasesME.EYE_PREFIX), light, overlay, true);
        }
    }

    private void renderTexture(PoseStack matrices, SubmitNodeCollector submitNodeCollector, NpcEntityRenderState state, Identifier textureId, int light, int overlay, boolean isEmissive){
        if (textureId == null)
            return;
        if (isEmissive) {
            RenderUtil.renderAtlasEmissiveTexture(characterTextureAtlas, this.bodyModel, state, matrices, submitNodeCollector, textureId, light, overlay);
        } else {
            RenderUtil.renderAtlasTexture(characterTextureAtlas, this.bodyModel, state, matrices, submitNodeCollector, textureId, light, overlay);
        }
    }
}
