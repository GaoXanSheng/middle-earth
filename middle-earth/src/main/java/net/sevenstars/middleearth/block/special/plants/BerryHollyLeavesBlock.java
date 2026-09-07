package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;

public class BerryHollyLeavesBlock extends TintedParticleLeavesBlock {

    public BerryHollyLeavesBlock(float f, Properties settings) {
        super(f, settings);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (stack.is(Items.BONE_MEAL)) {
            world.setBlockAndUpdate(pos, ModNatureBlocks.BERRY_HOLLY_LEAVES.defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }
}