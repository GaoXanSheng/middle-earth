package net.sevenstars.middleearth.entity.npcs.renderer.features.feet;

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
public class FeetFeatureRenderer extends RenderLayer<NpcEntityRenderState, NpcEntityModel> {
    private final EntityModel<NpcEntityRenderState> feetModel;
    private TextureAtlas characterTexturesAtlas;

    public FeetFeatureRenderer(RenderLayerParent<NpcEntityRenderState, NpcEntityModel> context, EntityModelSet loader) {
        super(context);
        this.feetModel = new FeetModel(loader.bakeLayer(EntityModelLayersME.NPC_ENTITY_FEET));
    }

    @Override
    public void submit(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        if (characterTexturesAtlas == null) {
            characterTexturesAtlas = AtlasesME.getAtlasFromPath(AtlasesME.CHARACTER_TEXTURES);
        }
        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier feetId =  (isSimplified) ? state.simplifiedFeetId : MiddleEarth.ofPrefix(state.feetId, AtlasesME.SKIN_PREFIX);

        feetModel.setupAnim(state);

        if(!state.canShowFeet)
            return;

        int overlay = OverlayTexture.pack(0.0f, state.hasRedOverlay);

        if(feetId != null){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(feetId);
            if(sprite != null){
                submitNodeCollector.submitModelPart(feetModel.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, sprite);
            }
        }
    }
}
