package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.entity.EntitiesME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Unique private static final float RANDOM_FLYING_BLOCK = 0.34f;
    @Unique private static final float DISCARD_DISTANCE = 3;
    @Unique private static final float FORCE = 80;
    @Unique private static final float VERTICAL_MULTIPLIER = 10;

    @Shadow protected abstract Block asBlock();

    @Inject(at = @At("HEAD"), method = "wasExploded")
    private void onDestroyedByExplosion(ServerLevel world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        // Non-destroying explosions (e.g. wind charges) must not launch blocks.
        if(!explosion.shouldAffectBlocklikeEntities()) return;
        Block block = this.asBlock();

        if(explosion.getDirectSourceEntity() == null || explosion.getDirectSourceEntity().getType() == EntitiesME.FIRE_OF_ORTHANC) {
            if(block != Blocks.TNT && block != ModDecorativeBlocks.FIRE_OF_ORTHANC) {
                if(Math.random() < RANDOM_FLYING_BLOCK) {
                    float distance = (float) pos.distToCenterSqr(explosion.center());
                    if(distance < explosion.radius() / DISCARD_DISTANCE) return;

                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, pos, block.defaultBlockState());
                    fallingBlockEntity.dropItem = false;
                    fallingBlockEntity.disableDrop();
                    Vec3 velocity = Vec3.atCenterOf(pos).subtract(explosion.center()).normalize();
                    float factor = FORCE / distance;
                    velocity.scale(factor);
                    velocity.add(0, VERTICAL_MULTIPLIER * factor, 0);
                    fallingBlockEntity.setDeltaMovement(velocity);
                }
            }
        }
    }

    @Inject(method = "canSupportCenter", at = @At("RETURN"), cancellable = true)
    private static void sideCoversSmallSquare(LevelReader world, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if(blockState.getBlock() == ModDecorativeBlocks.ROPE) cir.setReturnValue(true);
    }
}
