package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.Blocks;
import net.sevenstars.api.entity.ai.brain.task.EatBerriesTask;
import net.sevenstars.api.entity.ai.brain.task.FleeFromEntityTask;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsBlockTask;

import java.util.List;

public class DeerBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super DeerEntity>>> SENSORS = ImmutableList.of();
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS);

    public static final Brain.Provider<DeerEntity> BRAIN_PROVIDER = Brain.provider(MEMORY_MODULES, SENSORS, DeerBrain::createActivities);

    public static Brain<DeerEntity> create(DeerEntity deerEntity, Brain.Packed packed) {
        Brain<DeerEntity> brain = BRAIN_PROVIDER.makeBrain(deerEntity, packed);
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static List<ActivityData<DeerEntity>> createActivities(DeerEntity deerEntity) {
        return ImmutableList.of(coreActivity(), idleActivity());
    }

    private static ActivityData<DeerEntity> coreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(new Swim<>(0.8F), new MoveToTargetSink()));
    }

    private static ActivityData<DeerEntity> idleActivity() {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new FleeFromEntityTask<DeerEntity>(ImmutableList.of(Player.class), 5, 1.5F)),
                Pair.of(1, new RunOne<DeerEntity>(ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(MoveTowardsBlockTask.create(1.0F, Blocks.SWEET_BERRY_BUSH), 5),
                        Pair.of(new EatBerriesTask(), 5),
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                ))),
                Pair.of(2, new RunOne<DeerEntity>(ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                )))
        ));
    }

    public static void updateActivities(DeerEntity deer) {
        deer.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }
}
