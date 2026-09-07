package net.sevenstars.middleearth.block.special;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Map;

public class StoneLecternBlock extends LecternBlock {
    private static final VoxelShape BASE_SHAPE;
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION;

    public StoneLecternBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape)OUTLINE_SHAPES_BY_DIRECTION.get(state.getValue(FACING));
    }

    static {
        BASE_SHAPE = Shapes.or(Block.column(16.0, 0.0, 2.0), Block.column(12.0, 2.0, 14.0));
        OUTLINE_SHAPES_BY_DIRECTION = Shapes.rotateHorizontal(Shapes.or(Block.boxZ(16.0, 10.0, 14.0, 1.0, 5.333333), new VoxelShape[]{Block.boxZ(16.0, 12.0, 16.0, 5.333333, 9.666667), Block.boxZ(16.0, 14.0, 18.0, 9.666667, 14.0), BASE_SHAPE}));
    }
}
