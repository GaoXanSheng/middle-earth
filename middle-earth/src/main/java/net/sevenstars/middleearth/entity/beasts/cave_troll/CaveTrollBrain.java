package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;

import java.util.Optional;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(CaveTrollEntity troll) {
        // TODO 26.2: brain activities were reworked to the new Brain.Provider/ActivityData API
        //  (Brain.provider(memories, sensors, entity -> List<ActivityData<E>>).makeBrain(entity, Brain.Packed)).
        //  Re-register the core / idle / tamed / fight behaviours (CaveTrollDigForFoodTask, CaveTrollSleepTask,
        //  BeastChargeTask, CaveTrollRoarTask, CaveTrollSmashTask, RunOne/GateBehavior entries) against that API
        //  once the surrounding AI rework lands. For now the troll falls back to an empty brain.
        Brain<CaveTrollEntity> brain = new Brain<>();
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static Optional<? extends LivingEntity> getAttackTarget(ServerLevel world, CaveTrollEntity troll) {
        return (troll.isSleeping() || troll.isSitting()) ? getMemoryIfRegistered(troll, MemoryModuleType.HURT_BY_ENTITY) : getMemoryIfRegistered(troll, MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static Optional<? extends LivingEntity> getHurtBy (ServerLevel world, CaveTrollEntity troll) {
        return getMemoryIfRegistered(troll, MemoryModuleType.HURT_BY_ENTITY);
    }

    private static Optional<? extends LivingEntity> getMemoryIfRegistered(CaveTrollEntity troll, MemoryModuleType<LivingEntity> type) {
        return troll.getBrain().hasMemoryValue(type) ? troll.getBrain().getMemory(type) : Optional.empty();
    }

    public static void updateActivities(CaveTrollEntity troll) {
        troll.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, ActivitiesME.TAMED, Activity.IDLE));
        // TODO 26.2: Brain.updateActivityFromSchedule now needs (EnvironmentAttributeSystem, long, Vec3); schedule
        //  driven behaviour was part of the brain rework above.
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                SensorsME.CAVE_TROLL_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                MemoryModulesME.FOOD_EATEN_COUNT,
                MemoryModulesME.TAME,
                MemoryModulesME.SITTING,
                MemoryModulesME.ROAR_COOLDOWN,
                MemoryModulesME.SMASH_COOLDOWN,
                MemoryModulesME.ACTION_TIMEOUT
        );
    }
}
