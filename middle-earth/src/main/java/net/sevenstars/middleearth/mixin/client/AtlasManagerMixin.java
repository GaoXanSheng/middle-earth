package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.ModTexturedRenderLayers;
import net.sevenstars.middleearth.registries.AtlasesME;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(AtlasManager.class)
public class AtlasManagerMixin {

    @Mutable
    @Shadow
    @Final
    private static List<AtlasManager.AtlasConfig> KNOWN_ATLASES;

    @Inject(method = "<init>", at = @At("HEAD"))
    private static void addNewAtlas(TextureManager textureManager, int maxMipmapLevels, CallbackInfo ci) {
        boolean exists = KNOWN_ATLASES.stream().anyMatch(c -> c.textureId().equals(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES));
        if (!exists) {
            List<AtlasManager.AtlasConfig> list = new ArrayList<>(KNOWN_ATLASES);
            list.add(new AtlasManager.AtlasConfig(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES, AtlasesME.CHARACTER_TEXTURES, false));
            list.add(new AtlasManager.AtlasConfig(MiddleEarth.of("sprites"), MiddleEarth.of("sprites"), false));
            KNOWN_ATLASES = list;
        }
    }
}
