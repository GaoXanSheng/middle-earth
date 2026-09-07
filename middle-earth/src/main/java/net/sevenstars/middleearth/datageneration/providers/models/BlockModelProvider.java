package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;

public class BlockModelProvider extends FabricModelProvider {

    public BlockModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "BlockModelProvider";
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        // Ported baseline: generate a default full-cube block-state + block model + item model for every
        // block registered under the middle-earth namespace. Individual block families (stairs, slabs,
        // doors, cross/plant shapes, coffers, ...) that previously had bespoke BlockStateModelGenerator
        // entries should be layered back on top of this (see the legacy content in git history) once
        // their 26.2 data.models counterparts are wired in.
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !MiddleEarth.MOD_ID.equals(id.getNamespace())) {
                continue;
            }
            blockStateModelGenerator.createTrivialCube(block);
            blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
