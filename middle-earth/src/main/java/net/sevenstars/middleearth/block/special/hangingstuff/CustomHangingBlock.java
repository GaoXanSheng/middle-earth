package net.sevenstars.middleearth.block.special.hangingstuff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CustomHangingBlock extends HangingMossBlock {

    public CustomHangingBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.canStayAtPosition(world, pos);
    }

    private boolean canStayAtPosition(BlockGetter world, BlockPos pos) {
        BlockPos blockPos = pos.relative(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceBlock.canAttachTo(world, Direction.UP, blockPos, blockState) || blockState.is(this);
    }

    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (!this.canStayAtPosition(world, pos)) {
            tickView.scheduleTick(pos, this, 1);
        }

        return (BlockState)state.setValue(TIP, !world.getBlockState(pos.below()).is(this));
    }

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canStayAtPosition(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {

    }
}
