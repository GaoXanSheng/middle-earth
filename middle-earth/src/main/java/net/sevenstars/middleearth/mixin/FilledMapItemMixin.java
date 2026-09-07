package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.sevenstars.middleearth.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MapItem.class)
public class FilledMapItemMixin {
    @Redirect(
            method = "update",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;getMapColor(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/MapColor;"
            )
    )
    private MapColor preventNullMapColor(BlockState state, BlockGetter world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);
        //debug(state, world, pos);
        return color == null ? MapColor.NONE : color;
    }

    private void debug(BlockState state, BlockGetter world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);

        if (color == null) {
            MiddleEarth.LOGGER.logError("Null map color at " +  pos+ " block " + state.toString());
        }
    }
}
