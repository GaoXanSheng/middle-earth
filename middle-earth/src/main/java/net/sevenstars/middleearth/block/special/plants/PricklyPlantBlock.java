package net.sevenstars.middleearth.block.special.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.utils.DamageablePlantsUtil;

public class PricklyPlantBlock extends CustomPlantBlock {
    public PricklyPlantBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            Vec3 vec3d = entity.isClientAuthoritative() ? entity.getKnownMovement() : entity.oldPosition().subtract(entity.position());
            if (vec3d.horizontalDistanceSqr() > 0.0) {
                double d = Math.abs(vec3d.x());
                double e = Math.abs(vec3d.z());
                if (d >= 0.003 || e >= 0.003) {
                    DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.damageSources().cactus());
                }
            }
        }
    }
}
