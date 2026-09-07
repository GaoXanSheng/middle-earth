package net.sevenstars.middleearth.entity.ai.brain.sensor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean isMatchingEntity(ServerLevel world, LivingEntity entity, LivingEntity target) {
        return CaveTrollEntity.shouldTarget(target) && !((CaveTrollEntity)entity).isTamed() && !isPassenger(entity, target);
    }

    private boolean isPassenger(LivingEntity troll, LivingEntity target) {
        return ((CaveTrollEntity)troll).getPassengers().contains(target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemoryToSet() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
