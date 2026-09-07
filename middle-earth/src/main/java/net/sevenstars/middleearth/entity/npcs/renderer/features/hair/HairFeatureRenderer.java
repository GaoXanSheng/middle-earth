package net.sevenstars.middleearth.entity.npcs.renderer.features.hair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
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
public class HairFeatureRenderer extends RenderLayer<NpcEntityRenderState, NpcEntityModel> {
    private EntityModel<NpcEntityRenderState> hairModel;
    private TextureAtlas characterTexturesAtlas;

    public HairFeatureRenderer(RenderLayerParent<NpcEntityRenderState, NpcEntityModel> context, EntityModelSet loader) {
        super(context);
        this.hairModel = new HairModel(loader.bakeLayer(EntityModelLayersME.NPC_ENTITY_HAIR));
    }

    @Override
    public void submit(PoseStack matrices, SubmitNodeCollector submitNodeCollector, int light, NpcEntityRenderState state, float limbAngle, float limbDistance) {
        if (characterTexturesAtlas == null) {
            characterTexturesAtlas = AtlasesME.getAtlasFromPath(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES);
        }
        EntityModel<NpcEntityRenderState> entityModel = hairModel;

        boolean isSimplified = ModClientConfigs.ENABLE_SIMPLIFIED_CHARACTER_RENDERING && state.simplifiedSkinId != null;
        Identifier hairAddonTextureId = (isSimplified) ? state.simplifiedHairAddonId : MiddleEarth.ofPrefix(state.hairAddonId, AtlasesME.HAIR_PREFIX);
        Identifier beardAddonTextureId =  (isSimplified) ? null : MiddleEarth.ofPrefix(state.beardAddonId, AtlasesME.HAIR_PREFIX);

        if(hairAddonTextureId == null && beardAddonTextureId == null){
            entityModel.root().visible = false;
            return;
        } else if (!entityModel.root().visible){
            entityModel.root().visible = true;
        }
        entityModel.setupAnim(state);

        int overlay = OverlayTexture.pack(0.0f, state.hasRedOverlay);

        if(hairAddonTextureId != null && state.canShowHair){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(hairAddonTextureId);
            if(sprite != null){
                submitNodeCollector.submitModelPart(entityModel.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, sprite);
            }
        }
        if(beardAddonTextureId != null && state.canShowBeard){
            TextureAtlasSprite sprite = characterTexturesAtlas.getSprite(beardAddonTextureId);
            if(sprite != null){
                submitNodeCollector.submitModelPart(entityModel.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, sprite);
            }
        }
    }
}
