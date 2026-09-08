package net.sevenstars.middleearth.datageneration.providers.models;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class BlockModelProvider extends FabricModelProvider {

    // Suffixes stripped from longest to shortest when looking up a stand-in texture for
    // placeholder models of variant blocks (slabs, stairs, furniture, ...) whose own
    // texture file does not exist yet.
    private static final String[] SUFFIXES = {
            "_vertical_slab", "_pressure_plate", "_brickwork", "_chandelier", "_shingles", "_roofing",
            "_fence_gate", "_hanging_sign", "_stripped_log", "_stripped_wood", "_trapdoor", "_chiseled",
            "_polished", "_carved", "_smooth", "_stairs", "_slab", "_wall", "_fence", "_button",
            "_plate", "_door", "_stool", "_chair", "_table", "_bench", "_ladder", "_bricks", "_brick",
            "_tiles", "_tile", "_pillar", "_carving", "_carpet", "_bars", "_rocks", "_beam", "_boards",
            "_window", "_pane", "_trim", "_base", "_sign", "_leaves", "_layer", "_planks", "_wood",
            "_log", "_stem", "_block"
    };
    private Set<String> modBlockTextures;

    public BlockModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "BlockModelProvider";
    }
    private Set<String> vanillaBlockTextures;

    private static void collectPngNames(Path dir, Set<String> target) {
        if (!Files.isDirectory(dir)) {
            return;
        }
        try (Stream<Path> files = Files.list(dir)) {
            files.map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".png"))
                    .forEach(n -> target.add(n.substring(0, n.length() - 4)));
        } catch (IOException e) {
            System.err.println("[middle-earth] Failed to list textures in " + dir + ": " + e);
        }
    }

    private void loadTextureIndexes() {
        if (modBlockTextures != null) {
            return;
        }
        modBlockTextures = new HashSet<>();
        vanillaBlockTextures = new HashSet<>();
        FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).ifPresent(container -> {
            for (Path root : container.getRootPaths()) {
                collectPngNames(root.resolve("assets/" + MiddleEarth.MOD_ID + "/textures/block"), modBlockTextures);
            }
        });
        FabricLoader.getInstance().getModContainer("minecraft").ifPresent(container -> {
            for (Path root : container.getRootPaths()) {
                collectPngNames(root.resolve("assets/minecraft/textures/block"), vanillaBlockTextures);
            }
        });
    }

    private String resolveTexture(String path, int depth) {
        if (modBlockTextures.contains(path)) {
            return MiddleEarth.MOD_ID + ":block/" + path;
        }
        if (vanillaBlockTextures.contains(path)) {
            return "minecraft:block/" + path;
        }
        if (depth >= 3) {
            return null;
        }
        for (String suffix : SUFFIXES) {
            if (!path.endsWith(suffix)) {
                continue;
            }
            String base = path.substring(0, path.length() - suffix.length());
            for (String candidate : new String[]{base, base + "_planks", base + "s", base + "_block", base + "wood", base + "_side"}) {
                if (modBlockTextures.contains(candidate)) {
                    return MiddleEarth.MOD_ID + ":block/" + candidate;
                }
                if (vanillaBlockTextures.contains(candidate)) {
                    return "minecraft:block/" + candidate;
                }
            }
            String resolved = resolveTexture(base, depth + 1);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    private Identifier textureFor(Block block) {
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        String resolved = resolveTexture(path, 0);
        return Identifier.parse(resolved != null ? resolved : MiddleEarth.MOD_ID + ":block/" + path);
    }

    private Identifier textureForPath(String path) {
        String resolved = resolveTexture(path, 0);
        return Identifier.parse(resolved != null ? resolved : MiddleEarth.MOD_ID + ":block/" + path);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        // Ported baseline: generate a default full-cube block-state + block model + item model for every
        // block registered under the middle-earth namespace. Individual block families (stairs, slabs,
        // doors, cross/plant shapes, coffers, ...) that previously had bespoke BlockStateModelGenerator
        // entries should be layered back on top of this (see the legacy content in git history) once
        // their 26.2 data.models counterparts are wired in.
        loadTextureIndexes();

        Set<Block> handled = new HashSet<>();
        LegacyFamilies.setTextureResolver(this::textureForPath);
        generateCrossPlants(blockStateModelGenerator, handled);
        LegacyFamilies.portAll(blockStateModelGenerator, handled);

        Set<Item> seenItems = new HashSet<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !MiddleEarth.MOD_ID.equals(id.getNamespace())) {
                continue;
            }
            if (handled.contains(block)) {
                continue;
            }
            if (block.asItem() == Items.AIR || !seenItems.add(block.asItem())) {
                continue;
            }
            Identifier texture = textureFor(block);
            blockStateModelGenerator.createTrivialBlock(block, TexturedModel.CUBE.updateTexture(mapping -> mapping.put(TextureSlot.ALL, new Material(texture))));
            blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
        }
    }

    /**
     * Ported from the legacy BlockStateModelGenerator-based provider: cross-shaped plants.
     * The 26.2 createCrossBlock/createDoublePlant helpers are exposed by fabric's datagen
     * classtweaker; the PlantType constants are opened in middle-earth.accesswidener.
     */
    private void generateCrossPlants(BlockModelGenerators generators, Set<Block> handled) {
        var plantTypeClass = BlockModelGenerators.PlantType.class;
        var notTinted = Enum.valueOf(plantTypeClass, "NOT_TINTED");
        var tinted = Enum.valueOf(plantTypeClass, "TINTED");

        for (Block block : net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel.notTintedBlocks) {
            if (block == null || !handled.add(block)) {
                continue;
            }
            generators.createCrossBlock(block, notTinted);
        }
        for (Block block : net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel.grassLikeBlocks) {
            if (block == null || !handled.add(block)) {
                continue;
            }
            generators.createCrossBlock(block, notTinted);
        }
        for (Block block : net.sevenstars.middleearth.datageneration.content.models.TintableCrossModel.tintedBlocks) {
            if (block == null || !handled.add(block)) {
                continue;
            }
            generators.createCrossBlock(block, tinted);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
