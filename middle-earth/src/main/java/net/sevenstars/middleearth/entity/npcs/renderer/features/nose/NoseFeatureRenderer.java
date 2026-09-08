package net.sevenstars.middleearth.entity.npcs.renderer.features.nose;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityModel;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityRenderState;
import net.sevenstars.middleearth.registries.AtlasesME;

@Environment(EnvType.CLIENT)
public class NoseFeatureRenderer extends RenderLayer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> noseModel;
    private TextureAtlas characterTexturesAtlas;

    public NoseFeatureRenderer(RenderLayerParent<NpcEntityRenderState, NpcEntityModel> context, EntityModelSet loader) {
        super(context);
        this.noseModel = new NoseModel(loader.bakeLayer(EntityModelLayersME.NPC_ENTITY_NOSE));
    }

    @Override
    public void submit(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        if (characterTexturesAtlas == null) {
            characterTexturesAtlas = AtlasesME.getAtlasFromPath(AtlasesME.CHARACTER_TEXTURES);
        }
        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier noseId =  (isSimplified) ? state.simplifiedNoseId : MiddleEarth.ofPrefix(state.noseId, AtlasesME.SKIN_PREFIX);

        noseModel.setupAnim(state);

        int overlay = OverlayTexture.pack(0.0f, state.hasRedOverlay);

        if(noseId != null) {
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(noseId);
            if(sprite != null){
                submitNodeCollector.submitModelPart(noseModel.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, sprite);
            }
        }
    }
}
