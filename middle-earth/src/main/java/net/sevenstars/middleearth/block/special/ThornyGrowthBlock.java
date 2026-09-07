package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.utils.DamageablePlantsUtil;

public class ThornyGrowthBlock extends GlowLichenBlock {
    public ThornyGrowthBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean bl) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            Vec3 movement = livingEntity.isClientAuthoritative() ? livingEntity.getKnownMovement() : livingEntity.oldPosition().subtract(livingEntity.position());
            if (movement.horizontalDistanceSqr() > 0.0) {
                double d = Math.abs(movement.x());
                double e = Math.abs(movement.z());
                if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                    DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.damageSources().sweetBerryBush());
                }
            }
        }
    }
}
