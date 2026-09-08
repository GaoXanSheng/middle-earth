package net.sevenstars.middleearth.datageneration.providers.models;

import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.block.special.RocksBlock;
import net.sevenstars.middleearth.block.special.crop.*;
import net.sevenstars.middleearth.block.special.doors.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabShape;
import net.sevenstars.middleearth.datageneration.content.MEModels;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.LeavesSets;

import java.util.Set;

/**
 * Port of the legacy (1.21.8 yarn) bespoke model families to the 26.2 datagen API.
 * Sources: git commit 745312fc9 BlockModelProvider + datageneration/content/models/*.
 */
final class LegacyFamilies {
    private static BlockModelGenerators G;
    private static java.util.function.Function<String, Identifier> textureResolver;

    private LegacyFamilies() {
    }

    static void setTextureResolver(java.util.function.Function<String, Identifier> resolver) {
        textureResolver = resolver;
    }

    static Identifier resolve(String path) {
        return textureResolver != null ? textureResolver.apply(path) : Identifier.parse(MiddleEarth.MOD_ID + ":block/" + path);
    }

    static Identifier tex(Block block) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath());
    }

    static String woodSub(String path) {
        return path.replaceAll("_wood", "_log").replaceAll("_hyphae", "_stem")
                .replaceAll("treated_log", "treated_wood").replaceAll("aged_log", "aged_wood");
    }

    static Identifier texResolved(Block block) {
        return resolve(BuiltInRegistries.BLOCK.getKey(block).getPath());
    }

    static Identifier woodTexResolved(Block origin) {
        return resolve(woodSub(BuiltInRegistries.BLOCK.getKey(origin).getPath()));
    }

    static Identifier vanillaWoodTexResolved(Block origin) {
        return resolve(woodSub(BuiltInRegistries.BLOCK.getKey(origin).getPath()));
    }

    static TextureMapping mapAll(Identifier texture) {
        return new TextureMapping().put(TextureSlot.ALL, new Material(texture)).put(TextureSlot.PARTICLE, new Material(texture));
    }

    static TextureMapping mapAll(Block block) {
        return mapAll(tex(block));
    }

    static MultiVariant yRot(MultiVariant variant, Quadrant quadrant) {
        return variant.with(net.minecraft.client.renderer.block.dispatch.VariantMutator.Y_ROT.withValue(quadrant));
    }

    static MultiVariant uvLock(MultiVariant variant) {
        return variant.with(net.minecraft.client.renderer.block.dispatch.VariantMutator.UV_LOCK.withValue(true));
    }

    static MultiVariant model(BlockModelGenerators g, Identifier id) {
        return g.plainVariant(id);
    }

    static MultiVariant model(Block block, Identifier id) {
        return G.plainVariant(id);
    }

    static void simpleItem(BlockModelGenerators g, Block block, Identifier model) {
        g.registerSimpleItemModel(block, model);
    }

    static void portAll(BlockModelGenerators g, Set<Block> handled) {
        G = g;
        cubes(g, handled);
        grayscaleLeaves(g, handled);
        axisRotated(g, handled);
        woodColumns(g, handled);
        carvedWindows(g, handled);
        slabs(g, handled);
        stairs(g, handled);
        walls(g, handled);
        columnWalls(g, handled);
        fences(g, handled);
        fenceGates(g, handled);
        buttons(g, handled);
        pressurePlates(g, handled);
        doors(g, handled);
        trapdoors(g, handled);
        ladders(g, handled);
        glowworm(g, handled);
        largePlants(g, handled);
        flowerBeds(g, handled);
        flowerPots(g, handled);
        doubleBlocks(g, handled);
        verticalSlabs(g, handled);
        rocks(g, handled);
        panes(g, handled);
        furniture(g, handled);
        largeDoors(g, handled);
        pointedBlocks(g, handled);
        crops(g, handled);
        multiface(g, handled);
        farmlandAndPaths(g, handled);
        hangingMoss(g, handled);
        topWater(g, handled);
        stoneLectern(g, handled);
        decorativeRods(g, handled);
    }

    private static void decorativeRods(BlockModelGenerators g, Set<Block> handled) {
        for (Block rod : new Block[]{ModDecorativeBlocks.CRUDE_ROD, ModDecorativeBlocks.TREATED_STEEL_ROD}) {
            if (!handled.add(rod)) {
                continue;
            }
            simpleItem(g, rod, ModelLocationUtils.getModelLocation(rod));
        }
    }

    private static void stoneLectern(BlockModelGenerators g, Set<Block> handled) {
        if (!handled.add(ModDecorativeBlocks.STONE_LECTERN)) {
            return;
        }
        simpleItem(g, ModDecorativeBlocks.STONE_LECTERN, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "block/stone_lectern"));
    }

    // region simple cubes / leaves / axis rotated

    private static void cubes(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : SimpleBlockModel.blocks) {
            if (handled.add(block)) {
                g.createTrivialCube(block);
            }
        }
        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
            if (handled.add(block.base())) {
                g.createTrivialCube(block.base());
            }
        }
    }

    private static void grayscaleLeaves(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : LeavesSets.grayscaleLeaves) {
            if (handled.add(block)) {
                g.createTintedLeaves(block, net.minecraft.client.data.models.model.TexturedModel.LEAVES, -12012264);
            }
        }
    }

    private static void axisRotated(BlockModelGenerators g, Set<Block> handled) {
        // chiseled/pillar blocks with distinct top texture: vertical = column with end on top,
        // horizontal = the same model rotated (vanilla createRotatedPillar dispatch on AXIS).
        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimplePillarModel.Pillar block : SimplePillarModel.blocks) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
        for (SimplePillarModel.StonePillar block : SimplePillarModel.stonePillars) {
            axisRotatedWithEndTop(g, handled, block.base());
        }
    }

    private static void axisRotatedWithEndTop(BlockModelGenerators g, Set<Block> handled, Block base) {
        if (!handled.add(base)) {
            return;
        }
        Identifier side = tex(base);
        Identifier end = Identifier.fromNamespaceAndPath(side.getNamespace(), side.getPath() + "_top");
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.END, new Material(end))
                .put(TextureSlot.SIDE, new Material(side))
                .put(TextureSlot.PARTICLE, new Material(side));
        Identifier model = ModelTemplates.CUBE_COLUMN.create(base, mapping, g.modelOutput);
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(base, model(base, model)).with(g.createRotatedPillar()));
    }

    private static void woodColumns(BlockModelGenerators g, Set<Block> handled) {
        for (Block wood : SimpleBlockModel.woodBlocks) {
            if (!handled.add(wood)) {
                continue;
            }
            Identifier id = BuiltInRegistries.BLOCK.getKey(wood);
            Identifier texture = Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + woodSub(id.getPath()));
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.END, new Material(texture))
                    .put(TextureSlot.SIDE, new Material(texture))
                    .put(TextureSlot.PARTICLE, new Material(texture));
            Identifier model = ModelTemplates.CUBE_COLUMN.create(wood, mapping, g.modelOutput);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(wood, model(wood, model)).with(g.createRotatedPillar()));
        }
    }

    private static void carvedWindows(BlockModelGenerators g, Set<Block> handled) {
        for (SimplePillarModel.StonePillar block : SimplePillarModel.carvedWindows) {
            if (!handled.add(block.base())) {
                continue;
            }
            Identifier side = texResolved(block.base());
            Identifier top = resolve(BuiltInRegistries.BLOCK.getKey(block.origin()).getPath() + "_top");
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.END, new Material(top))
                    .put(TextureSlot.SIDE, new Material(side))
                    .put(TextureSlot.PARTICLE, new Material(side));
            Identifier model = ModelTemplates.CUBE_COLUMN.create(block.base(), mapping, g.modelOutput);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block.base(), model(block.base(), model)));
        }
    }

    // endregion

    // region slabs / stairs

    private static void slabs(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.slabs) {
            slab(g, handled, slab.origin(), slab.slab(), texResolved(slab.origin()));
        }
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.woodSlabs) {
            slab(g, handled, slab.origin(), slab.slab(), woodTexResolved(slab.origin()));
        }
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.strippedSlabs) {
            slab(g, handled, slab.origin(), slab.slab(), woodTexResolved(slab.origin()));
        }
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaWoodSlabs) {
            slab(g, handled, slab.origin(), slab.slab(), vanillaWoodTexResolved(slab.origin()));
        }
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaStrippedSlab) {
            slab(g, handled, slab.origin(), slab.slab(), vanillaWoodTexResolved(slab.origin()));
        }
        for (SimpleSlabModel.Slab slab : SimpleSlabModel.vanillaSlabs) {
            slab(g, handled, slab.origin(), slab.slab(), vanillaWoodTexResolved(slab.origin()));
        }
    }

    private static Identifier woodTexture(Block origin) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(origin);
        return Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + woodSub(id.getPath()));
    }

    private static Identifier vanillaWoodTexture(Block origin) {
        return Identifier.withDefaultNamespace("block/" + woodSub(BuiltInRegistries.BLOCK.getKey(origin).getPath()));
    }

    private static void slab(BlockModelGenerators g, Set<Block> handled, Block origin, Block slab, Identifier texture) {
        if (!handled.add(slab)) {
            return;
        }
        TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture));
        Identifier bottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, g.modelOutput);
        Identifier top = ModelTemplates.SLAB_TOP.create(slab, mapping, g.modelOutput);
        Identifier full = ModelLocationUtils.getModelLocation(origin);
        g.blockStateOutput.accept(g.createSlab(slab, model(slab, bottom), model(slab, top), model(slab, full)));
        simpleItem(g, slab, bottom);
    }

    private static void stairs(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleStairModel.Stair stair : SimpleStairModel.stairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), texResolved(stair.origin()));
        }
        for (SimpleStairModel.Stair stair : SimpleStairModel.woodStairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), woodTexResolved(stair.origin()));
        }
        for (SimpleStairModel.Stair stair : SimpleStairModel.strippedStairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), woodTexResolved(stair.origin()));
        }
        for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaWoodStairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), vanillaWoodTexResolved(stair.origin()));
        }
        for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStrippedStairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), vanillaWoodTexResolved(stair.origin()));
        }
        for (SimpleStairModel.Stair stair : SimpleStairModel.vanillaStairs) {
            stairs(g, handled, stair.origin(), stair.stairs(), vanillaWoodTexResolved(stair.origin()));
        }
    }

    private static void stairs(BlockModelGenerators g, Set<Block> handled, Block origin, Block stairs, Identifier texture) {
        if (!handled.add(stairs)) {
            return;
        }
        TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture));
        Identifier inner = ModelTemplates.STAIRS_INNER.create(stairs, mapping, g.modelOutput);
        Identifier regular = ModelTemplates.STAIRS_STRAIGHT.create(stairs, mapping, g.modelOutput);
        Identifier outer = ModelTemplates.STAIRS_OUTER.create(stairs, mapping, g.modelOutput);
        g.blockStateOutput.accept(g.createStairs(stairs, model(stairs, inner), model(stairs, regular), model(stairs, outer)));
        simpleItem(g, stairs, regular);
    }

    // endregion

    // region walls / fences / gates / buttons / plates

    private static void walls(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            wall(g, handled, wall.block(), wall.wall(), woodSub(BuiltInRegistries.BLOCK.getKey(wall.block()).getPath()), BuiltInRegistries.BLOCK.getKey(wall.block()).getNamespace());
        }
        for (SimpleWallModel.Wall wall : SimpleWallModel.strippedWalls) {
            wall(g, handled, wall.block(), wall.wall(), woodSub(BuiltInRegistries.BLOCK.getKey(wall.block()).getPath()), BuiltInRegistries.BLOCK.getKey(wall.block()).getNamespace());
        }
        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
            wall(g, handled, wall.block(), wall.wall(), BuiltInRegistries.BLOCK.getKey(wall.block()).getPath(), "minecraft");
        }
        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaStrippedWalls) {
            wall(g, handled, wall.block(), wall.wall(), woodSub(BuiltInRegistries.BLOCK.getKey(wall.block()).getPath()), "minecraft");
        }
        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWoodWalls) {
            wall(g, handled, wall.block(), wall.wall(), woodSub(BuiltInRegistries.BLOCK.getKey(wall.block()).getPath()), "minecraft");
        }
    }

    private static void wall(BlockModelGenerators g, Set<Block> handled, Block origin, Block wall, String texturePath, String namespace) {
        if (!handled.add(wall)) {
            return;
        }
        Identifier texture = resolve(texturePath);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture)).put(TextureSlot.PARTICLE, new Material(texture));
        Identifier inventory = ModelTemplates.WALL_INVENTORY.create(wall, mapping, g.modelOutput);
        Identifier post = ModelTemplates.WALL_POST.create(wall, mapping, g.modelOutput);
        Identifier low = ModelTemplates.WALL_LOW_SIDE.create(wall, mapping, g.modelOutput);
        Identifier tall = ModelTemplates.WALL_TALL_SIDE.create(wall, mapping, g.modelOutput);
        simpleItem(g, wall, inventory);
        g.blockStateOutput.accept(g.createWall(wall, model(wall, post), model(wall, low), model(wall, tall)));
    }

    private static void columnWalls(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleWallModel.Wall wall : SimpleWallModel.columnWalls) {
            if (!handled.add(wall.wall())) {
                continue;
            }
            Identifier id = BuiltInRegistries.BLOCK.getKey(wall.wall());
            String sidePath = id.getPath().replaceAll("_wall", "");
            String topBottomPath = sidePath + "_top";
            Identifier side = resolve(sidePath);
            Identifier topBottom = resolve(topBottomPath);
            TextureMapping mapping = new TextureMapping()
                    .put(TextureSlot.TOP, new Material(topBottom))
                    .put(TextureSlot.BOTTOM, new Material(topBottom))
                    .put(TextureSlot.WALL, new Material(side))
                    .put(TextureSlot.PARTICLE, new Material(side));
            Identifier inventory = MEModels.COLUMN_WALL_INVENTORY.create(wall.wall(), mapping, g.modelOutput);
            Identifier post = MEModels.COLUMN_WALL_POST.create(wall.wall(), mapping, g.modelOutput);
            Identifier low = MEModels.COLUMN_WALL_SIDE.create(wall.wall(), mapping, g.modelOutput);
            Identifier tall = MEModels.COLUMN_WALL_SIDE_TALL.create(wall.wall(), mapping, g.modelOutput);
            simpleItem(g, wall.wall(), inventory);
            g.blockStateOutput.accept(g.createWall(wall.wall(), model(wall.wall(), post), model(wall.wall(), low), model(wall.wall(), tall)));
        }
    }

    private static void fences(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleFenceModel.Fence fence : SimpleFenceModel.blocks) {
            fence(g, handled, fence.block(), fence.fence(), woodSub(BuiltInRegistries.BLOCK.getKey(fence.block()).getPath()), BuiltInRegistries.BLOCK.getKey(fence.block()).getNamespace());
        }
        for (SimpleFenceModel.Fence fence : SimpleFenceModel.strippedFences) {
            fence(g, handled, fence.block(), fence.fence(), woodSub(BuiltInRegistries.BLOCK.getKey(fence.block()).getPath()), BuiltInRegistries.BLOCK.getKey(fence.block()).getNamespace());
        }
        for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaStrippedFences) {
            fence(g, handled, fence.block(), fence.fence(), woodSub(BuiltInRegistries.BLOCK.getKey(fence.block()).getPath()), "minecraft");
        }
        for (SimpleFenceModel.Fence fence : SimpleFenceModel.vanillaWoodFences) {
            fence(g, handled, fence.block(), fence.fence(), woodSub(BuiltInRegistries.BLOCK.getKey(fence.block()).getPath()), "minecraft");
        }
    }

    private static void fence(BlockModelGenerators g, Set<Block> handled, Block origin, Block fence, String texturePath, String namespace) {
        if (!handled.add(fence)) {
            return;
        }
        Identifier texture = resolve(texturePath);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture)).put(TextureSlot.PARTICLE, new Material(texture));
        Identifier post = ModelTemplates.FENCE_POST.create(fence, mapping, g.modelOutput);
        Identifier side = ModelTemplates.FENCE_SIDE.create(fence, mapping, g.modelOutput);
        Identifier inventory = ModelTemplates.FENCE_INVENTORY.create(fence, mapping, g.modelOutput);
        simpleItem(g, fence, inventory);
        g.blockStateOutput.accept(g.createFence(fence, model(fence, post), model(fence, side)));
    }

    private static void fenceGates(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleFenceGateModel.FenceGate gate : SimpleFenceGateModel.blocks) {
            if (!handled.add(gate.fenceGate())) {
                continue;
            }
            Identifier texture = tex(gate.block());
            TextureMapping mapping = mapAll(texture);
            Identifier open = ModelTemplates.FENCE_GATE_OPEN.create(gate.fenceGate(), mapping, g.modelOutput);
            Identifier closed = ModelTemplates.FENCE_GATE_CLOSED.create(gate.fenceGate(), mapping, g.modelOutput);
            Identifier openWall = ModelTemplates.FENCE_GATE_WALL_OPEN.create(gate.fenceGate(), mapping, g.modelOutput);
            Identifier closedWall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(gate.fenceGate(), mapping, g.modelOutput);
            simpleItem(g, gate.fenceGate(), closed);
            g.blockStateOutput.accept(g.createFenceGate(gate.fenceGate(),
                    model(gate.fenceGate(), open), model(gate.fenceGate(), closed),
                    model(gate.fenceGate(), openWall), model(gate.fenceGate(), closedWall), true));
        }
    }

    private static void buttons(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleButtonModel.Button button : SimpleButtonModel.buttons) {
            if (!handled.add(button.button())) {
                continue;
            }
            Identifier texture = texResolved(button.block());
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture));
            Identifier unpressed = ModelTemplates.BUTTON.create(button.button(), mapping, g.modelOutput);
            Identifier pressed = ModelTemplates.BUTTON_PRESSED.create(button.button(), mapping, g.modelOutput);
            Identifier inventory = ModelTemplates.BUTTON_INVENTORY.create(button.button(), mapping, g.modelOutput);
            simpleItem(g, button.button(), inventory);
            g.blockStateOutput.accept(g.createButton(button.button(), model(button.button(), unpressed), model(button.button(), pressed)));
        }
    }

    private static void pressurePlates(BlockModelGenerators g, Set<Block> handled) {
        for (SimplePressurePlateModel.PressurePlate plate : SimplePressurePlateModel.pressurePlates) {
            if (!handled.add(plate.pressurePlate())) {
                continue;
            }
            Identifier texture = texResolved(plate.block());
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture));
            Identifier up = ModelTemplates.PRESSURE_PLATE_UP.create(plate.pressurePlate(), mapping, g.modelOutput);
            Identifier down = ModelTemplates.PRESSURE_PLATE_DOWN.create(plate.pressurePlate(), mapping, g.modelOutput);
            g.blockStateOutput.accept(g.createPressurePlate(plate.pressurePlate(), model(plate.pressurePlate(), up), model(plate.pressurePlate(), down)));
        }
    }

    // endregion

    // region doors / trapdoors / ladders

    private static void doors(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleDoorModel.Door door : SimpleDoorModel.doors) {
            if (handled.add(door.door())) {
                g.createDoor(door.door());
            }
        }
    }

    private static void trapdoors(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.trapdoors) {
            if (!handled.add(trapdoor.trapdoor())) {
                continue;
            }
            if (trapdoor.orientable()) {
                Identifier texture = tex(trapdoor.trapdoor());
                TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, new Material(texture));
                Identifier top = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                Identifier bottom = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                Identifier open = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                simpleItem(g, trapdoor.trapdoor(), bottom);
                g.blockStateOutput.accept(g.createOrientableTrapdoor(trapdoor.trapdoor(), model(trapdoor.trapdoor(), top), model(trapdoor.trapdoor(), bottom), model(trapdoor.trapdoor(), open)));
            } else {
                Identifier texture;
                if (trapdoor.block() == net.minecraft.world.level.block.Blocks.BASALT) {
                    texture = Identifier.parse("minecraft:block/basalt_side");
                } else {
                    texture = texResolved(trapdoor.block());
                }
                TextureMapping mapping = new TextureMapping().put(TextureSlot.TEXTURE, new Material(texture));
                Identifier top = ModelTemplates.TRAPDOOR_TOP.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                Identifier bottom = ModelTemplates.TRAPDOOR_BOTTOM.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                Identifier open = ModelTemplates.TRAPDOOR_OPEN.create(trapdoor.trapdoor(), mapping, g.modelOutput);
                simpleItem(g, trapdoor.trapdoor(), bottom);
                g.blockStateOutput.accept(g.createTrapdoor(trapdoor.trapdoor(), model(trapdoor.trapdoor(), top), model(trapdoor.trapdoor(), bottom), model(trapdoor.trapdoor(), open)));
            }
        }
    }

    private static void ladders(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.ladders) {
            orientableThickLadder(g, handled, ladder.ladder());
        }
        for (SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
            orientableThickLadder(g, handled, ladder.ladder());
        }
    }

    private static void orientableThickLadder(BlockModelGenerators g, Set<Block> handled, Block ladder) {
        if (!handled.add(ladder)) {
            return;
        }
        Identifier texture = tex(ladder);
        Identifier model = MEModels.THICK_LADDER.create(ladder, mapAll(texture), g.modelOutput);
        MultiVariant base = model(ladder, model);
        MultiVariant x90 = base.with(net.minecraft.client.renderer.block.dispatch.VariantMutator.X_ROT.withValue(Quadrant.R90));
        MultiVariant x180 = base.with(net.minecraft.client.renderer.block.dispatch.VariantMutator.X_ROT.withValue(Quadrant.R180));
        var attach = net.minecraft.world.level.block.state.properties.BlockStateProperties.ATTACH_FACE;
        var facingP = net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
        if (!ladder.defaultBlockState().hasProperty(attach) || !ladder.defaultBlockState().hasProperty(facingP)) {
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(ladder, base));
            simpleItem(g, ladder, model);
            return;
        }
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(ladder)
                .with(PropertyDispatch.initial(net.minecraft.world.level.block.state.properties.BlockStateProperties.ATTACH_FACE,
                                net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING)
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.FLOOR, Direction.NORTH, base)
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.FLOOR, Direction.EAST, yRot(base, Quadrant.R90))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.FLOOR, Direction.SOUTH, yRot(base, Quadrant.R180))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.FLOOR, Direction.WEST, yRot(base, Quadrant.R270))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.WALL, Direction.NORTH, x90)
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.WALL, Direction.EAST, yRot(x90, Quadrant.R90))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.WALL, Direction.SOUTH, yRot(x90, Quadrant.R180))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.WALL, Direction.WEST, yRot(x90, Quadrant.R270))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.CEILING, Direction.SOUTH, x180)
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.CEILING, Direction.WEST, yRot(x180, Quadrant.R90))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.CEILING, Direction.NORTH, yRot(x180, Quadrant.R180))
                        .select(net.minecraft.world.level.block.state.properties.AttachFace.CEILING, Direction.EAST, yRot(x180, Quadrant.R270))));
        simpleItem(g, ladder, model);
    }

    // endregion

    // region plants

    private static void glowworm(BlockModelGenerators g, Set<Block> handled) {
        Block webbing = ModNatureBlocks.GLOWWORM_WEBBING;
        if (handled.add(webbing)) {
            Identifier model = ModelTemplates.CROSS.create(webbing,
                    new TextureMapping().put(TextureSlot.CROSS, new Material(tex(ModNatureBlocks.GLOWWORM_MAIN))), g.modelOutput);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(webbing, model(webbing, model)));
            simpleItem(g, webbing, model);
        }
    }

    private static void largePlants(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : TintableCrossModel.largePlants) {
            if (!handled.add(block)) {
                continue;
            }
            Identifier model = MEModels.LARGE_PLANT.create(block, mapAll(tex(block)), g.modelOutput);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model(block, model)));
            simpleItem(g, block, model);
        }
    }

    private static void flowerBeds(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : SimpleFlowerBedModel.flowerBeds) {
            if (handled.add(block)) {
                g.createFlowerBed(block);
            }
        }
    }

    private static void flowerPots(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleFlowerPotModel.FlowerPot pot : SimpleFlowerPotModel.pots) {
            if (!handled.add(pot.pottedPlant())) {
                continue;
            }
            Identifier model = ModelTemplates.FLOWER_POT_CROSS.create(pot.pottedPlant(),
                    new TextureMapping().put(TextureSlot.PLANT, new Material(tex(pot.plant()))), g.modelOutput);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(pot.pottedPlant(), model(pot.pottedPlant(), model)));
        }
    }

    private static void doubleBlocks(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : SimpleDoubleBlockModel.doubleBlocks) {
            doubleBlock(g, handled, block);
        }
        for (Block block : SimpleDoubleBlockModel.doubleBlocksItems) {
            doubleBlock(g, handled, block);
        }
    }

    private static void doubleBlock(BlockModelGenerators g, Set<Block> handled, Block block) {
        if (!handled.add(block)) {
            return;
        }
        String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
        Identifier bottom = ModelTemplates.CROSS.createWithSuffix(block, "_bottom",
                new TextureMapping().put(TextureSlot.CROSS, new Material(resolve(path + "_bottom"))), g.modelOutput);
        Identifier top = ModelTemplates.CROSS.createWithSuffix(block, "_top",
                new TextureMapping().put(TextureSlot.CROSS, new Material(resolve(path + "_top"))), g.modelOutput);
        simpleItem(g, block, bottom);
        // 26.2 createDoubleBlock binds the FIRST variant to half=upper
        g.createDoubleBlock(block, model(block, top), model(block, bottom));
    }

    // endregion

    // region vertical slabs / rocks / furniture / large doors

    private static void verticalSlabs(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.verticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), texResolved(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.columnVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), texResolved(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.woodVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), woodTexture(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.strippedVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), woodTexture(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.plansVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), tex(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), texResolved(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), vanillaWoodTexResolved(vs.block()));
        }
        for (SimpleVerticalSlabModel.VerticalSlab vs : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            verticalSlab(g, handled, vs.verticalSlab(), vs.block(), vanillaWoodTexResolved(vs.block()));
        }
    }

    private static void verticalSlab(BlockModelGenerators g, Set<Block> handled, Block verticalSlab, Block origin, Identifier texture) {
        if (!handled.add(verticalSlab)) {
            return;
        }
        Identifier full = ModelLocationUtils.getModelLocation(origin);
        TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(texture)).put(TextureSlot.PARTICLE, new Material(texture));
        Identifier regular = MEModels.VERTICAL_SLAB.create(verticalSlab, mapping, g.modelOutput);
        Identifier inner = MEModels.VERTICAL_SLAB_INNER.create(verticalSlab, mapping, g.modelOutput);
        Identifier outer = MEModels.VERTICAL_SLAB_OUTER.create(verticalSlab, mapping, g.modelOutput);
        simpleItem(g, verticalSlab, regular);

        MultiVariant regularMv = uvLock(model(verticalSlab, regular));
        MultiVariant innerMv = uvLock(model(verticalSlab, inner));
        MultiVariant outerMv = uvLock(model(verticalSlab, outer));
        MultiVariant fullMv = uvLock(model(verticalSlab, full));

        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(verticalSlab).with(PropertyDispatch.initial(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING,
                        VerticalSlabBlock.DOUBLE, VerticalSlabBlock.SHAPE)
                .select(Direction.EAST, false, VerticalSlabShape.STRAIGHT, regularMv)
                .select(Direction.WEST, false, VerticalSlabShape.STRAIGHT, yRot(regularMv, Quadrant.R270))
                .select(Direction.SOUTH, false, VerticalSlabShape.STRAIGHT, yRot(regularMv, Quadrant.R180))
                .select(Direction.NORTH, false, VerticalSlabShape.STRAIGHT, regularMv)
                .select(Direction.EAST, false, VerticalSlabShape.OUTER_RIGHT, yRot(outerMv, Quadrant.R180))
                .select(Direction.WEST, false, VerticalSlabShape.OUTER_RIGHT, outerMv)
                .select(Direction.SOUTH, false, VerticalSlabShape.OUTER_RIGHT, yRot(outerMv, Quadrant.R270))
                .select(Direction.NORTH, false, VerticalSlabShape.OUTER_RIGHT, yRot(outerMv, Quadrant.R90))
                .select(Direction.EAST, false, VerticalSlabShape.OUTER_LEFT, yRot(outerMv, Quadrant.R90))
                .select(Direction.WEST, false, VerticalSlabShape.OUTER_LEFT, yRot(outerMv, Quadrant.R270))
                .select(Direction.SOUTH, false, VerticalSlabShape.OUTER_LEFT, yRot(outerMv, Quadrant.R180))
                .select(Direction.NORTH, false, VerticalSlabShape.OUTER_LEFT, outerMv)
                .select(Direction.EAST, false, VerticalSlabShape.INNER_RIGHT, yRot(innerMv, Quadrant.R180))
                .select(Direction.WEST, false, VerticalSlabShape.INNER_RIGHT, innerMv)
                .select(Direction.SOUTH, false, VerticalSlabShape.INNER_RIGHT, yRot(innerMv, Quadrant.R270))
                .select(Direction.NORTH, false, VerticalSlabShape.INNER_RIGHT, yRot(innerMv, Quadrant.R90))
                .select(Direction.EAST, false, VerticalSlabShape.INNER_LEFT, yRot(innerMv, Quadrant.R90))
                .select(Direction.WEST, false, VerticalSlabShape.INNER_LEFT, yRot(innerMv, Quadrant.R270))
                .select(Direction.SOUTH, false, VerticalSlabShape.INNER_LEFT, yRot(innerMv, Quadrant.R180))
                .select(Direction.NORTH, false, VerticalSlabShape.INNER_LEFT, innerMv)
                .select(Direction.EAST, true, VerticalSlabShape.STRAIGHT, fullMv)
                .select(Direction.WEST, true, VerticalSlabShape.STRAIGHT, fullMv)
                .select(Direction.SOUTH, true, VerticalSlabShape.STRAIGHT, fullMv)
                .select(Direction.NORTH, true, VerticalSlabShape.STRAIGHT, fullMv)
                .select(Direction.EAST, true, VerticalSlabShape.OUTER_RIGHT, fullMv)
                .select(Direction.WEST, true, VerticalSlabShape.OUTER_RIGHT, fullMv)
                .select(Direction.SOUTH, true, VerticalSlabShape.OUTER_RIGHT, fullMv)
                .select(Direction.NORTH, true, VerticalSlabShape.OUTER_RIGHT, fullMv)
                .select(Direction.EAST, true, VerticalSlabShape.OUTER_LEFT, fullMv)
                .select(Direction.WEST, true, VerticalSlabShape.OUTER_LEFT, fullMv)
                .select(Direction.SOUTH, true, VerticalSlabShape.OUTER_LEFT, fullMv)
                .select(Direction.NORTH, true, VerticalSlabShape.OUTER_LEFT, fullMv)
                .select(Direction.EAST, true, VerticalSlabShape.INNER_RIGHT, fullMv)
                .select(Direction.WEST, true, VerticalSlabShape.INNER_RIGHT, fullMv)
                .select(Direction.SOUTH, true, VerticalSlabShape.INNER_RIGHT, fullMv)
                .select(Direction.NORTH, true, VerticalSlabShape.INNER_RIGHT, fullMv)
                .select(Direction.EAST, true, VerticalSlabShape.INNER_LEFT, fullMv)
                .select(Direction.WEST, true, VerticalSlabShape.INNER_LEFT, fullMv)
                .select(Direction.SOUTH, true, VerticalSlabShape.INNER_LEFT, fullMv)
                .select(Direction.NORTH, true, VerticalSlabShape.INNER_LEFT, fullMv)));
    }

    private static void rocks(BlockModelGenerators g, Set<Block> handled) {
        for (SimpleRocksModel.Rocks rocks : SimpleRocksModel.rocks) {
            rocks(g, handled, rocks.rocks(), rocks.block());
        }
        for (SimpleRocksModel.Rocks rocks : SimpleRocksModel.vanillaRocks) {
            rocks(g, handled, rocks.rocks(), rocks.block());
        }
    }

    private static void rocks(BlockModelGenerators g, Set<Block> handled, Block rocksBlock, Block origin) {
        if (!handled.add(rocksBlock)) {
            return;
        }
        Identifier texture = texResolved(origin);
        ModelTemplate[] stages = {MEModels.ROCKS_STAGE_0, MEModels.ROCKS_STAGE_1, MEModels.ROCKS_STAGE_2, MEModels.ROCKS_STAGE_3};
        PropertyDispatch.C2<MultiVariant, Direction, Integer> dispatch = PropertyDispatch.initial(
                net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, RocksBlock.STAGE);
        Identifier stage0Model = null;
        for (int stage = 0; stage < stages.length; stage++) {
            Identifier model = stages[stage].create(rocksBlock, mapAll(texture), g.modelOutput);
            if (stage == 0) {
                stage0Model = model;
            }
            dispatch.select(Direction.EAST, stage, uvLock(yRot(model(rocksBlock, model), Quadrant.R90)));
            dispatch.select(Direction.WEST, stage, uvLock(yRot(model(rocksBlock, model), Quadrant.R270)));
            dispatch.select(Direction.SOUTH, stage, uvLock(yRot(model(rocksBlock, model), Quadrant.R180)));
            dispatch.select(Direction.NORTH, stage, uvLock(model(rocksBlock, model)));
        }
        simpleItem(g, rocksBlock, stage0Model);
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(rocksBlock).with(dispatch));
    }

    private static void furniture(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : SimpleWoodStoolModel.stools) {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            facingModel(g, handled, block, MEModels.WOOD_STOOL,
                    Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath().replaceAll("stool", "chair")));
        }
        for (Block block : SimpleWoodBenchModel.benchs) {
            facingModel(g, handled, block, MEModels.WOOD_BENCH, tex(block));
        }
        for (Block block : SimpleWoodTableModel.tables) {
            facingModel(g, handled, block, MEModels.WOOD_TABLE, tex(block));
        }
        for (Block block : SimpleWoodChairModel.chairs) {
            facingModel(g, handled, block, MEModels.WOOD_CHAIR, tex(block));
        }
        for (SimpleStoneStoolModel.Stool stool : SimpleStoneStoolModel.stools) {
            facingModel(g, handled, stool.stool(), MEModels.STONE_STOOL, texResolved(stool.base()));
        }
        for (SimpleStoneTableModel.Table table : SimpleStoneTableModel.tables) {
            facingModel(g, handled, table.table(), MEModels.STONE_TABLE, texResolved(table.base()));
        }
        for (SimpleStoneChairModel.Chair chair : SimpleStoneChairModel.chairs) {
            facingModel(g, handled, chair.chair(), MEModels.STONE_CHAIR, texResolved(chair.base()));
        }
    }

    private static void facingModel(BlockModelGenerators g, Set<Block> handled, Block block,
                                    net.minecraft.client.data.models.model.ModelTemplate template, Identifier texture) {
        if (!handled.add(block)) {
            return;
        }
        Identifier model = template.create(block, mapAll(texture), g.modelOutput);
        MultiVariant base = model(block, model);
        var facing = net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;
        if (block.defaultBlockState().hasProperty(facing)) {
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch
                    .initial(facing)
                    .select(Direction.NORTH, base)
                    .select(Direction.EAST, yRot(base, Quadrant.R90))
                    .select(Direction.SOUTH, yRot(base, Quadrant.R180))
                    .select(Direction.WEST, yRot(base, Quadrant.R270))));
        } else {
            // 26.2 migration dropped FACING from some furniture blocks
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, base));
        }
        simpleItem(g, block, model);
    }

    private static void largeDoors(BlockModelGenerators g, Set<Block> handled) {
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, LargeDoor2x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, LargeDoor3x1.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.TALL_FIR_DOOR, LargeDoor3x1.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.OAK_STABLE_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.SIMPLE_LARCH_GATE, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_STABLE_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.LARGE_STURDY_DOOR, LargeDoor5x3.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, LargeDoor1x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, LargeDoor10x5.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, LargeDoor5x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, LargeDoor4x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, LargeThickDoor3x2.PART, true);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, LargeDoor6x2.PART, false);
        largeDoor(g, handled, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, LargeDoor10x4.PART, false);
    }

    private static void largeDoor(BlockModelGenerators g, Set<Block> handled, LargeDoorBlock door, IntegerProperty part, boolean thick) {
        if (!handled.add(door)) {
            return;
        }
        Identifier id = BuiltInRegistries.BLOCK.getKey(door);
        net.minecraft.client.data.models.model.ModelTemplate left = thick ? MEModels.LARGE_THICK_DOOR_LEFT : MEModels.LARGE_DOOR_LEFT;
        net.minecraft.client.data.models.model.ModelTemplate leftOpen = thick ? MEModels.LARGE_THICK_DOOR_LEFT_OPEN : MEModels.LARGE_DOOR_LEFT_OPEN;
        net.minecraft.client.data.models.model.ModelTemplate right = thick ? MEModels.LARGE_THICK_DOOR_RIGHT : MEModels.LARGE_DOOR_RIGHT;
        net.minecraft.client.data.models.model.ModelTemplate rightOpen = thick ? MEModels.LARGE_THICK_DOOR_RIGHT_OPEN : MEModels.LARGE_DOOR_RIGHT_OPEN;

        PropertyDispatch.C4<MultiVariant, Direction, Boolean, net.minecraft.world.level.block.state.properties.DoorHingeSide, Integer> dispatch = PropertyDispatch.initial(
                net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING,
                net.minecraft.world.level.block.state.properties.BlockStateProperties.OPEN,
                net.minecraft.world.level.block.state.properties.BlockStateProperties.DOOR_HINGE,
                part);
        int pieces = door.getDoorWidth() * door.getDoorHeight();
        Quadrant[] rots = {Quadrant.R0, null, Quadrant.R0, Quadrant.R180, Quadrant.R270, Quadrant.R90};
        Direction[] dirs = {null, null, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        for (int i = 0; i < pieces; i++) {
            Identifier tex = Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath() + "_" + i);
            TextureMapping mapping = new TextureMapping().put(TextureSlot.ALL, new Material(tex)).put(TextureSlot.PARTICLE, new Material(tex));
            Identifier leftId = left.createWithSuffix(door, "_left_" + i, mapping, g.modelOutput);
            Identifier leftOpenId = leftOpen.createWithSuffix(door, "_left_open_" + i, mapping, g.modelOutput);
            Identifier rightId = right.createWithSuffix(door, "_right_" + i, mapping, g.modelOutput);
            Identifier rightOpenId = rightOpen.createWithSuffix(door, "_right_open_" + i, mapping, g.modelOutput);
            for (int k = 2; k < 6; k++) {
                Quadrant rot = rots[k];
                Direction dir = dirs[k];
                MultiVariant l = yRot(model(door, leftId), rot);
                MultiVariant lo = yRot(model(door, leftOpenId), rot);
                MultiVariant r = yRot(model(door, rightId), rot);
                MultiVariant ro = yRot(model(door, rightOpenId), rot);
                dispatch.select(dir, false, net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT, i, l);
                dispatch.select(dir, true, net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT, i, lo);
                dispatch.select(dir, false, net.minecraft.world.level.block.state.properties.DoorHingeSide.RIGHT, i, r);
                dispatch.select(dir, true, net.minecraft.world.level.block.state.properties.DoorHingeSide.RIGHT, i, ro);
            }
        }
        MultiVariantGenerator generator = MultiVariantGenerator.dispatch(door).with(dispatch);
        g.blockStateOutput.accept(generator);
        simpleItem(g, door, Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath() + "_left_0"));
    }

    // endregion

    // region panes

    private static void panes(BlockModelGenerators g, Set<Block> handled) {
        for (SimplePaneModel.Pane pane : SimplePaneModel.panes) {
            Identifier paneTex = texResolved(pane.glass());
            Identifier edgeTex = texResolved(pane.pane());
            paneModel(g, handled, pane.pane(), paneTex, edgeTex);
        }
        paneModel(g, handled, ModBlocks.NET, texResolved(ModBlocks.NET), texResolved(ModBlocks.NET));
        paneModel(g, handled, ModBlocks.GILDED_BARS, texResolved(ModBlocks.GILDED_BARS), texResolved(ModBlocks.GILDED_BARS));
        for (Block bars : new Block[]{ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS,
                ModBlocks.OXIDIZED_COPPER_BARS, ModBlocks.WAXED_COPPER_BARS, ModBlocks.WAXED_EXPOSED_COPPER_BARS,
                ModBlocks.WAXED_WEATHERED_COPPER_BARS, ModBlocks.WAXED_OXIDIZED_COPPER_BARS, ModBlocks.BRONZE_BARS,
                ModBlocks.CRUDE_BARS, ModBlocks.TREATED_STEEL_BARS, ModBlocks.BURZUM_BARS, ModBlocks.SILVER_BARS}) {
            paneModel(g, handled, bars, texResolved(bars), texResolved(bars));
        }
    }

    private static void paneModel(BlockModelGenerators g, Set<Block> handled, Block pane, Identifier paneTexture, Identifier edgeTexture) {
        if (!handled.add(pane)) {
            return;
        }
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.PANE, new Material(paneTexture))
                .put(TextureSlot.EDGE, new Material(edgeTexture));
        Identifier post = ModelTemplates.STAINED_GLASS_PANE_POST.create(pane, mapping, g.modelOutput);
        Identifier side = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(pane, mapping, g.modelOutput);
        Identifier sideAlt = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(pane, mapping, g.modelOutput);
        Identifier noside = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(pane, mapping, g.modelOutput);
        Identifier nosideAlt = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(pane, mapping, g.modelOutput);
        simpleItem(g, pane, post);
        var mvPost = model(pane, post);
        var mvSide = model(pane, side);
        var mvSideY90 = yRot(model(pane, side), Quadrant.R90);
        var mvSideAlt = model(pane, sideAlt);
        var mvSideAltY90 = yRot(model(pane, sideAlt), Quadrant.R90);
        var mvNoside = model(pane, noside);
        var mvNosideAlt = model(pane, nosideAlt);
        var mvNosideAltY90 = yRot(model(pane, nosideAlt), Quadrant.R90);
        var mvNosideAltY270 = yRot(model(pane, nosideAlt), Quadrant.R270);
        MultiPartGenerator multipart = MultiPartGenerator.multiPart(pane)
                .with(mvPost)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.NORTH, true), mvSide)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.EAST, true), mvSideY90)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.SOUTH, true), mvSideAlt)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.WEST, true), mvSideAltY90)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.NORTH, false), mvNoside)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.EAST, false), mvNosideAlt)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.SOUTH, false), mvNosideAltY90)
                .with(g.condition().term(net.minecraft.world.level.block.state.properties.BlockStateProperties.WEST, false), mvNosideAltY270);
        g.blockStateOutput.accept(multipart);
    }

    // endregion

    // region pointed / crops / multiface / farmland / hanging moss / top water

    private static void pointedBlocks(BlockModelGenerators g, Set<Block> handled) {
        pointed(g, handled, ModBlocks.POINTED_DOLOMITE);
        pointed(g, handled, ModBlocks.POINTED_GALONN);
        pointed(g, handled, ModBlocks.POINTED_LIMESTONE);
        pointed(g, handled, ModBlocks.POINTED_IZHERABAN);
    }

    private static void pointed(BlockModelGenerators g, Set<Block> handled, Block block) {
        if (!handled.add(block)) {
            return;
        }
        Identifier itemId = Identifier.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(), "item/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
        ModelTemplates.FLAT_ITEM.create(itemId, TextureMapping.layer0(new Material(itemId)), g.modelOutput);
        simpleItem(g, block, itemId);
        PropertyDispatch.C2<MultiVariant, Direction, net.minecraft.world.level.block.state.properties.SpeleothemThickness> dispatch = PropertyDispatch.initial(
                net.minecraft.world.level.block.state.properties.BlockStateProperties.VERTICAL_DIRECTION,
                net.sevenstars.middleearth.block.special.pointedBlocks.PointedIzherabanBlock.THICKNESS);
        for (Direction direction : new Direction[]{Direction.UP, Direction.DOWN}) {
            for (net.minecraft.world.level.block.state.properties.SpeleothemThickness thickness :
                    net.minecraft.world.level.block.state.properties.SpeleothemThickness.values()) {
                String suffix = "_" + direction.getSerializedName() + "_" + thickness.getSerializedName();
                Identifier model = ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(block, suffix,
                        new TextureMapping().put(TextureSlot.CROSS, new Material(
                                Identifier.fromNamespaceAndPath(BuiltInRegistries.BLOCK.getKey(block).getNamespace(),
                                        "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath() + suffix))), g.modelOutput);
                dispatch.select(direction, thickness, model(block, model));
            }
        }
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
    }

    private static void crops(BlockModelGenerators g, Set<Block> handled) {
        crop(g, handled, ModNatureBlocks.BELL_PEPPER_CROP, BellpepperCropBlock.AGE, 0, 1, 2, 3, 4);
        crop(g, handled, ModNatureBlocks.CUCUMBER_CROP, CucumberCropBlock.AGE, 0, 1, 2, 3);
        crop(g, handled, ModNatureBlocks.FLAX_CROP, FlaxCropBlock.AGE, 0, 1, 2, 3);
        crop(g, handled, ModNatureBlocks.GARLIC_CROP, GarlicCropBlock.AGE, 0, 1, 2, 3);
        crop(g, handled, ModNatureBlocks.LEEK_CROP, LeekCropBlock.AGE, 0, 1, 2, 3);
        crop(g, handled, ModNatureBlocks.LETTUCE_CROP, LettuceCropBlock.AGE, 0, 1, 2, 3);
        crop(g, handled, ModNatureBlocks.ONION_CROP, OnionCropBlock.AGE, 0, 1, 2, 3);
    }

    private static void crop(BlockModelGenerators g, Set<Block> handled, Block block, IntegerProperty age, int... stages) {
        if (!handled.add(block)) {
            return;
        }
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
        PropertyDispatch.C1<MultiVariant, Integer> dispatch = PropertyDispatch.initial(age);
        for (int stage : stages) {
            Identifier texture = resolve(blockId.getPath() + "stage" + stage);
            Identifier model = ModelTemplates.CROP.create(
                    Identifier.fromNamespaceAndPath(blockId.getNamespace(), "block/" + blockId.getPath() + stage),
                    new TextureMapping().put(TextureSlot.CROP, new Material(texture)), g.modelOutput);
            dispatch.select(stage, g.plainVariant(model));
        }
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
    }

    private static void multiface(BlockModelGenerators g, Set<Block> handled) {
        Block[] blocks = {
                ModNatureBlocks.AZALEA_FLOWER_GROWTH, ModNatureBlocks.DRY_GROWTH, ModNatureBlocks.FROZEN_GROWTH,
                ModNatureBlocks.GREEN_GROWTH, ModNatureBlocks.IVY_GROWTH, ModNatureBlocks.LILAC_FLOWER_GROWTH,
                ModNatureBlocks.PINK_FLOWER_GROWTH, ModNatureBlocks.RED_FLOWER_GROWTH, ModNatureBlocks.THORNY_GROWTH,
                ModNatureBlocks.WHITE_FLOWER_GROWTH, ModNatureBlocks.YELLOW_FLOWER_GROWTH, ModNatureBlocks.WEBBING,
                ModNatureBlocks.MOSS, ModNatureBlocks.FOREST_MOSS, ModNatureBlocks.CORRUPTED_MOSS,
                ModNatureBlocks.MORGUL_IVY, ModNatureBlocks.STICKY_SNOW, ModNatureBlocks.STICKY_ICE
        };
        for (Block block : blocks) {
            if (handled.add(block)) {
                g.createMultiface(block);
            }
        }
    }

    private static void farmlandAndPaths(BlockModelGenerators g, Set<Block> handled) {
        farmland(g, handled, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND);
        farmland(g, handled, ModBlocks.LOAM, ModBlocks.LOAM_FARMLAND);
        farmland(g, handled, ModBlocks.PEAT, ModBlocks.PEAT_FARMLAND);
        farmland(g, handled, ModBlocks.SILT, ModBlocks.SILT_FARMLAND);
        dirtPath(g, handled, ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_PATH);
        dirtPath(g, handled, ModBlocks.LOAM, ModBlocks.LOAM_PATH);
        dirtPath(g, handled, ModBlocks.PEAT, ModBlocks.PEAT_PATH);
        dirtPath(g, handled, ModBlocks.SILT, ModBlocks.SILT_PATH);
    }

    private static void farmland(BlockModelGenerators g, Set<Block> handled, Block dirt, Block farmland) {
        if (!handled.add(farmland)) {
            return;
        }
        Identifier dry = ModelTemplates.FARMLAND.create(farmland,
                new TextureMapping().put(TextureSlot.DIRT, new Material(tex(dirt)))
                        .put(TextureSlot.TOP, new Material(tex(farmland))), g.modelOutput);
        Identifier moist = ModelTemplates.FARMLAND.createWithSuffix(farmland, "_moist",
                new TextureMapping().put(TextureSlot.DIRT, new Material(tex(dirt)))
                        .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(
                                BuiltInRegistries.BLOCK.getKey(farmland).getNamespace(),
                                "block/" + BuiltInRegistries.BLOCK.getKey(farmland).getPath() + "_moist"))), g.modelOutput);
        IntegerProperty moisture = net.minecraft.world.level.block.state.properties.BlockStateProperties.MOISTURE;
        MultiVariant dryMv = model(farmland, dry);
        MultiVariant moistMv = model(farmland, moist);
        PropertyDispatch.C1<MultiVariant, Integer> dispatch = PropertyDispatch.initial(moisture);
        for (int i = 0; i <= 7; i++) {
            dispatch.select(i, i == 7 ? moistMv : dryMv);
        }
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(farmland).with(dispatch));
    }

    private static void dirtPath(BlockModelGenerators g, Set<Block> handled, Block dirt, Block path) {
        if (!handled.add(path)) {
            return;
        }
        Identifier pathId = BuiltInRegistries.BLOCK.getKey(path);
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, new Material(tex(dirt)))
                .put(TextureSlot.TOP, new Material(Identifier.fromNamespaceAndPath(pathId.getNamespace(), "block/" + pathId.getPath() + "_top")))
                .put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(pathId.getNamespace(), "block/" + pathId.getPath() + "_side")))
                .put(TextureSlot.BOTTOM, new Material(tex(dirt)));
        Identifier model = MEModels.PATH_BLOCK.create(path, mapping, g.modelOutput);
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(path, model(path, model)));
    }

    private static void hangingMoss(BlockModelGenerators g, Set<Block> handled) {
        hangingVine(g, handled, ModNatureBlocks.WILLOW_VINES);
        hangingVine(g, handled, ModNatureBlocks.MIRKWOOD_VINES);
        hangingVine(g, handled, ModNatureBlocks.HANGING_WEBS);
    }

    private static void hangingVine(BlockModelGenerators g, Set<Block> handled, Block block) {
        if (!handled.add(block)) {
            return;
        }
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        Identifier tip = MEModels.CROP_VINE.createWithSuffix(block, "_tip",
                new TextureMapping().put(TextureSlot.CROP, new Material(Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath() + "_tip"))), g.modelOutput);
        Identifier body = MEModels.CROP_VINE.create(block,
                new TextureMapping().put(TextureSlot.CROP, new Material(Identifier.fromNamespaceAndPath(id.getNamespace(), "block/" + id.getPath()))), g.modelOutput);
        simpleItem(g, block, body);
        g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(HangingMossBlock.TIP)
                .select(true, model(block, tip))
                .select(false, model(block, body))));
    }

    private static void topWater(BlockModelGenerators g, Set<Block> handled) {
        for (Block block : SimpleTopWaterModel.topWaterBlocks) {
            if (!handled.add(block)) {
                continue;
            }
            Identifier model = ModelLocationUtils.getModelLocation(block);
            simpleItem(g, block, model);
            g.blockStateOutput.accept(MultiVariantGenerator.dispatch(block,
                    yRot(model(block, model), Quadrant.R90)));
        }
    }

    // endregion
}
