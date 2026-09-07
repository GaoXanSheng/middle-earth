package net.sevenstars.middleearth.block.special.candles;

import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChandelierBlock extends Block {
    private static final int MAX_VARIANT = 2;
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 1, MAX_VARIANT);
    public static final BooleanProperty LIT;

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape SHAPE;

    public ChandelierBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(VARIANT);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = this.defaultBlockState().setValue(VARIANT, 1);
        if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return !state.canSurvive(world, pos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = player.getUsedItemHand();
        if (!world.isClientSide() && player.getAbilities().mayBuild) {
            ItemStack itemStack = player.getItemInHand(hand);
            if(player.hasInfiniteMaterials() && itemStack == ItemStack.EMPTY){
                world.setBlockAndUpdate(pos, state.cycle(VARIANT));
                return InteractionResult.SUCCESS;
            }

            if (state.getValue(LIT) && itemStack.is(ItemTags.SHOVELS)) {
                extinguish(null, state, world, pos);
            } else if (!state.getValue(LIT) && itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.TORCH)) {
                setLit(world, state, pos, true);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            if(state.getValue(VARIANT) == 1) {
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(0.5, 12.5 * 0.0625, -0.375), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(0.5, 12.5 * 0.0625, 1.375), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(0.375, 12.5 * 0.0625, 0.5), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.375, 12.5 * 0.0625, 0.5), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(-0.125, 12.5 * 0.0625, -0.125), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(-0.125, 12.5 * 0.0625, 1.125), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.125, 12.5 * 0.0625, -0.125), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.125, 12.5 * 0.0625, 1.125), random);
            } else if(state.getValue(VARIANT) == 2) {
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(-0.25, 12.5 * 0.0625, -0.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(-0.25, 12.5 * 0.0625, 1.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.25, 12.5 * 0.0625, -0.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.25, 12.5 * 0.0625, 1.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(-0.25, 12.5 * 0.0625, 0.5), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(0.5, 12.5 * 0.0625, 1.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(0.5, 12.5 * 0.0625, -0.25), random);
                spawnCandleParticles(world, Vec3.atCenterOf(pos).add(1.25, 12.5 * 0.0625, 0.5), random);
            }
        }
    }

    protected static void spawnCandleParticles(Level world, Vec3 vec3d, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x - 0.5f, vec3d.y - 0.5f, vec3d.z - 0.5f, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playLocalSound(vec3d.x, vec3d.y, vec3d.z, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x - 0.5f, vec3d.y - 0.5f, vec3d.z - 0.5f, 0.0, 0.0, 0.0);
    }

    protected static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, LevelAccessor world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.5F, 1.0F);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide() && projectile.isOnFire() && !state.getValue(LIT)) {
            world.setBlock(hit.getBlockPos(), state.setValue(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    static {
        LIT = BlockStateProperties.LIT;
        STATE_TO_LUMINANCE = (state) -> state.getValue(LIT) ? 15 : 0;
        SHAPE = Block.box(0, 2, 0, 16, 16.0, 16);
    }
}
