package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.model.EntityModelsWT;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityRenderer;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntityRenderer;

import java.util.List;

public class OfBeastsAndWildThingsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityModelsWT.getModels();

        EntityRendererRegistry.register(EntitiesWT.SNAIL, SnailEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesWT.PHEASANT, PheasantEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesWT.SWAN, SwanEntityRenderer::new);
        EntityRendererRegistry.register(EntitiesWT.DEER, DeerEntityRenderer::new);

        EntityRendererRegistry.register(EntitiesWT.SWAN_EGG, ThrownItemRenderer::new);

        BlockColorRegistry.register(List.of(BlockTintSources.dryFoliage()), BlocksWT.BIRD_NEST);
    }
}
