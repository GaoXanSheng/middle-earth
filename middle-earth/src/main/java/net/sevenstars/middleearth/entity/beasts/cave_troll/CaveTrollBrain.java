package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.entity.ai.brain.task.*;

import java.util.List;
import java.util.Optional;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<CaveTrollEntity> create(CaveTrollEntity troll, Brain.Packed packed) {
        Brain.Provider<CaveTrollEntity> provider = Brain.provider(MEMORY_MODULES, SENSORS, CaveTrollBrain::getActivities);
        Brain<CaveTrollEntity> brain = provider.makeBrain(troll, packed);

        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);
        brain.useDefaultActivity();
        return brain;
    }

    public static List<ActivityData<CaveTrollEntity>> getActivities(CaveTrollEntity troll) {
        return List.of(
                coreActivity(),
                idleActivity(),
                tamedActivity(),
                fightActivity(troll)
        );
    }

    private static ActivityData<CaveTrollEntity> coreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetSink(),
                new LookAtTargetSink(45, 90),
                new CountDownCooldownTicks(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.ROAR_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.SMASH_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.ACTION_TIMEOUT)
        ));
    }

    private static ActivityData<CaveTrollEntity> idleActivity() {
        ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super CaveTrollEntity>>> behaviors = ImmutableList.of(
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getHurtBy)),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(RandomStroll.stroll(1.0F), 5),
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new GateBehavior<>(
                                ImmutableMap.of(MemoryModulesME.ACTION_TIMEOUT, MemoryStatus.VALUE_ABSENT),
                                ImmutableSet.of(),
                                GateBehavior.OrderPolicy.ORDERED,
                                GateBehavior.RunningPolicy.TRY_ALL,
                                ImmutableList.of(
                                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new CaveTrollDigForFoodTask(), 1),
                                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new CaveTrollEatFoodTask(), 1),
                                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new CaveTrollSleepTask(), 1)
                                )), 1)
                )))
        );
        return ActivityData.create(Activity.IDLE, behaviors,
                ImmutableSet.of(Pair.of(MemoryModulesME.TAME, MemoryStatus.VALUE_ABSENT)));
    }

    private static ActivityData<CaveTrollEntity> tamedActivity() {
        ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super CaveTrollEntity>>> behaviors = ImmutableList.of(
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(1, new GateBehavior<>(
                        ImmutableMap.of(MemoryModulesME.SITTING, MemoryStatus.VALUE_ABSENT),
                        ImmutableSet.of(),
                        GateBehavior.OrderPolicy.SHUFFLED,
                        GateBehavior.RunningPolicy.RUN_ONE,
                        ImmutableList.of(
                                Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(RandomStroll.stroll(1.0F), 1)
                        ))
                )
        );
        return ActivityData.create(ActivitiesME.TAMED, behaviors,
                ImmutableSet.of(Pair.of(MemoryModulesME.TAME, MemoryStatus.VALUE_PRESENT)));
    }

    private static ActivityData<CaveTrollEntity> fightActivity(CaveTrollEntity troll) {
        ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super CaveTrollEntity>>> behaviors = ImmutableList.of(
                Pair.of(0, StopAttackingIfTargetInvalid.create()),
                Pair.of(2, new RunOne<>(ImmutableList.of(
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(MeleeAttack.create(30), 4),
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(2.5F), 3),
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new BeastChargeTask(troll.chargeDuration(), troll.maxChargeCooldown()), 1),
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new CaveTrollRoarTask(), 2),
                        Pair.<BehaviorControl<? super CaveTrollEntity>, Integer>of(new CaveTrollSmashTask(), 2)
                )))
        );
        return ActivityData.create(Activity.FIGHT, behaviors,
                ImmutableSet.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)));
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
        troll.getBrain().updateActivityFromSchedule(troll.level().environmentAttributes(), troll.level().getGameTime(), troll.position());
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
