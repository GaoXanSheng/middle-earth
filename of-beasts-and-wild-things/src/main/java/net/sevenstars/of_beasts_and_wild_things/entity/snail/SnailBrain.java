package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsBlockTask;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.EatCropTask;

import java.util.List;

public class SnailBrain {
    private static final ImmutableList<SensorType<? extends Sensor<? super SnailEntity>>> SENSORS = ImmutableList.of();
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS);

    public static final Brain.Provider<SnailEntity> BRAIN_PROVIDER = Brain.provider(MEMORY_MODULES, SENSORS, SnailBrain::createActivities);

    public static Brain<SnailEntity> create(SnailEntity snailEntity, Brain.Packed packed) {
        Brain<SnailEntity> brain = BRAIN_PROVIDER.makeBrain(snailEntity, packed);
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static List<ActivityData<SnailEntity>> createActivities(SnailEntity snailEntity) {
        return ImmutableList.of(coreActivity(), idleActivity());
    }

    private static ActivityData<SnailEntity> coreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(new MoveToTargetSink(), new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)));
    }

    private static ActivityData<SnailEntity> idleActivity() {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunOne<SnailEntity>(ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(MoveTowardsBlockTask.create(1.0F, BlockTags.CROPS), 5),
                        Pair.of(new EatCropTask(), 5),
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                ))),
                Pair.of(1, new RunOne<SnailEntity>(ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(60, 100), 1)
                )))
        ));
    }

    public static void updateActivities(SnailEntity snail) {
        snail.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }
}
