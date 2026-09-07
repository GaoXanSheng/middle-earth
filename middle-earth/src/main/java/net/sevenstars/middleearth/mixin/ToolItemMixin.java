package net.sevenstars.middleearth.mixin;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ToolMaterial.class)
public class ToolItemMixin {

    @Inject(method = "applySwordProperties", at = @At(value = "RETURN"), cancellable = true)
    private void applySwordSettings(Item.Properties settings, float attackDamage, float attackSpeed, CallbackInfoReturnable<Item.Properties> cir) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        Item.Properties result = cir.getReturnValue();

        result = result.component(
                DataComponents.TOOL,
                new Tool(
                        List.of(
                                Tool.Rule.minesAndDrops(registryEntryLookup.getOrThrow(BlockTagsME.COBWEBS), 15.0F),
                                Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                                Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                        ),
                        1.0F,
                        2,
                        false
                )
        );
        cir.setReturnValue(result);
    }
}
