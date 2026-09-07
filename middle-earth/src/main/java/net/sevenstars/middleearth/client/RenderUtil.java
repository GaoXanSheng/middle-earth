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

    public static void renderCutoutTexture(Model<?> model, PoseStack matrices, SubmitNodeCollector collector, int light, int overlay, Identifier texture) {
        if (texture != null) {
            collector.submitModelPart(model.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, null);
        }
    }

    public static void renderEmissiveTexture(Model<?> model, PoseStack matrices, SubmitNodeCollector collector, int light, int overlay, Identifier texture) {
        if (texture != null) {
            collector.submitModelPart(model.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer(), light, overlay, null);
        }
    }

    public static void renderAtlasTexture(TextureAtlas atlas, Model<?> model, PoseStack matrices, SubmitNodeCollector collector, Identifier textureId, int light, int overlay) {
        if (textureId != null) {
            var sprite = atlas.getSprite(textureId);
            if (sprite != null) {
                collector.submitModelPart(model.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesRenderLayer(), light, overlay, sprite);
            }
        }
    }

    public static void renderAtlasEmissiveTexture(TextureAtlas atlas, Model<?> model, PoseStack matrices, SubmitNodeCollector collector, Identifier textureId, int light, int overlay) {
        if (textureId != null) {
            var sprite = atlas.getSprite(textureId);
            if (sprite != null) {
                collector.submitModelPart(model.root(), matrices, ModTexturedRenderLayers.getCharacterTexturesEmissiveRenderLayer(), light, overlay, sprite);
            }
        }
    }
}
