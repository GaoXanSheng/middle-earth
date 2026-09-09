package net.sevenstars.middleearth.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public final class RenderUtil {
    private RenderUtil() {
    }

    /**
     * Submits the model via {@code submitModel(model, state, ...)} so the pose is re-computed from
     * the render state when the submit node is replayed. Submitting a shared {@code ModelPart}
     * with {@code submitModelPart} instead would freeze/reuse whatever pose the shared model had
     * last, which desynchronizes the layer from armor rendered by independent models.
     */
    public static <S> void renderAtlasTexture(TextureAtlas atlas, Model<S> model, S state, PoseStack matrices, SubmitNodeCollector collector, Identifier textureId, int light, int overlay) {
        if (textureId != null) {
            var sprite = atlas.getSprite(textureId);
            if (sprite != null) {
                collector.submitModel(model, state, matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, -1, sprite, 0, null);
            }
        }
    }

    public static <S> void renderAtlasEmissiveTexture(TextureAtlas atlas, Model<S> model, S state, PoseStack matrices, SubmitNodeCollector collector, Identifier textureId, int light, int overlay) {
        if (textureId != null) {
            var sprite = atlas.getSprite(textureId);
            if (sprite != null) {
                collector.submitModel(model, state, matrices, ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer(), light, overlay, -1, sprite, 0, null);
            }
        }
    }
}
