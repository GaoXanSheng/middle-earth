package net.sevenstars.middleearth.mixin;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ShearsItem.class)
public class ShearItemMixin {

    @Inject(method = "createToolProperties", at = @At(value = "HEAD"), cancellable = true)
    private static void createToolComponent(CallbackInfoReturnable<Tool> cir) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        cir.setReturnValue(new Tool(List.of(
                Tool.Rule.minesAndDrops(
                        registryEntryLookup.getOrThrow(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "cobwebs"))), 15.0F),
                Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.LEAVES), 15.0F),
                Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.WOOL), 5.0F),
                Tool.Rule.overrideSpeed(HolderSet.direct(Blocks.VINE.builtInRegistryHolder(), Blocks.GLOW_LICHEN.builtInRegistryHolder()), 2.0F)), 1.0F, 1, false));

    }
}
