package net.sevenstars.middleearth.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypeIds;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Arrays;

@Mixin(BlockEntityTypes.class)
public class BlockEntityTypeMixin {
    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/entity/BlockEntityTypes;register(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;"))
    private static void modifyLecternBlocks(Args args) {
        ResourceKey<?> key = args.get(0);
        if (key.equals(BlockEntityTypeIds.LECTERN)) {
            Block[] original = args.get(2);
            Block[] modified = Arrays.copyOf(original, original.length + 1);
            modified[modified.length - 1] = ModDecorativeBlocks.STONE_LECTERN;
            args.set(2, modified);
        } else if(key.equals(BlockEntityTypeIds.CHISELED_BOOKSHELF)) {
            Block[] original = args.get(2);
            Block[] modified = Arrays.copyOf(original, original.length + 1);
            modified[modified.length - 1] = ModDecorativeBlocks.CHISELED_DOLOMITE_BOOKSHELF;
            args.set(2, modified);
        }
    }
}
