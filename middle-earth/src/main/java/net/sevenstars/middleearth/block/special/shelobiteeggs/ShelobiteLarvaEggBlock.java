
package net.sevenstars.middleearth.block.special.shelobiteeggs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ShelobiteLarvaEggBlock extends AbstractShelobiteLarvaEgg {
    private static final EnumProperty<Direction> FACING;

    public ShelobiteLarvaEggBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.EAST));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection());
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        return world.getBlockState(blockPos).isRedstoneConductor(world, blockPos);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.RandomSource random) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.minorHorizontalCollision && !entity.getType().builtInRegistryHolder().is(EntityTypeTags.ARTHROPOD)){
            breakEgg(world, pos, state);
            super.stepOn(world, pos, state, entity);
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if(!entity.minorHorizontalCollision && !entity.getType().builtInRegistryHolder().is(EntityTypeTags.ARTHROPOD)) {
            super.fallOn(world, state, pos, entity, fallDistance);
            breakEgg(world, pos, state);
        }

    }

    public static void breakEgg(Level world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.getRandom().nextFloat() * 0.2F);
        world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        world.levelEvent(2001, pos, Block.getId(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    public static void SpawnSpider(BlockPos pos, Level world){
        ShelobiteLarvaEntity entity = new ShelobiteLarvaEntity(EntitiesME.SHELOBITE_LARVA, world);
        entity.tickCount = 0;
        entity.snapTo(pos, 0, 0);
        if(world instanceof ServerLevelAccessor serverWorldAccess) {
            entity.finalizeSpawn(serverWorldAccess, serverWorldAccess.getCurrentDifficultyAt(pos), EntitySpawnReason.NATURAL, null);
        }
        world.addFreshEntity(entity);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.or(
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
