package net.sevenstars.middleearth.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.ai.memory.*;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import java.util.List;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.behavior.StrollAroundPoi;
import net.minecraft.world.entity.ai.behavior.StrollToPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.EntityTypes;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.tasks.SpiderPounceTask;

import java.util.Optional;

public class ShelobiteScuttlerBrain {
	private static final UniformInt POUNCE_COOLDOWN_RANGE = UniformInt.of(50, 80);
	public static final int POUNCE_VERTICAL_RANGE = 1;
	public static final int POUNCE_HORIZONTAL_RANGE = 3;

	public static List<ActivityData<ShelobiteScuttlerEntity>> getActivities(ShelobiteScuttlerEntity entity) {
		return List.of(
				initCoreActivity(),
				initIdleActivity(),
				initFightActivity(entity),
				initPounceActivity(entity)
		);
	}

	protected static Brain<ShelobiteScuttlerEntity> create(ShelobiteScuttlerEntity shelobiteScuttlerEntity, Brain.Packed packed) {
		Brain.Provider<ShelobiteScuttlerEntity> profile = Brain.provider(ShelobiteScuttlerEntity.MEMORY_MODULE_TYPES, ShelobiteScuttlerEntity.SENSOR_TYPES, ShelobiteScuttlerBrain::getActivities);
		Brain<ShelobiteScuttlerEntity> brain = profile.makeBrain(shelobiteScuttlerEntity, packed);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.useDefaultActivity();
		return brain;
	}

	protected static void setCurrentPosAsHome(ShelobiteScuttlerEntity shelobiteScuttler) {
		GlobalPos globalPos = GlobalPos.of(shelobiteScuttler.level().dimension(), shelobiteScuttler.blockPosition());
		shelobiteScuttler.getBrain().setMemory(MemoryModuleType.HOME, globalPos);
	}

	private static ActivityData<ShelobiteScuttlerEntity> initCoreActivity() {
		return ActivityData.create(
				Activity.CORE, 0, ImmutableList.of(
						new LookAtTargetSink(45, 90),
						new MoveToTargetSink(),
						StopBeingAngryIfTargetDead.create(),
						new CountDownCooldownTicks(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)
				)
		);
	}

	private static ActivityData<ShelobiteScuttlerEntity> initIdleActivity() {
		return ActivityData.create(
				Activity.IDLE,
				10,
				ImmutableList.of(
						StartAttacking.<ShelobiteScuttlerEntity>create(ShelobiteScuttlerBrain::getTarget),
						getFollowTasks(),
						getIdleTasks(),
						SetLookAndInteract.create(EntityTypes.PLAYER, 4)
				)
		);
	}

	private static ActivityData<ShelobiteScuttlerEntity> initFightActivity(ShelobiteScuttlerEntity shelobiteScuttler) {
		return ActivityData.create(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						StopAttackingIfTargetInvalid.create((world, target) -> !isTarget(world, shelobiteScuttler, target)),
						SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
						MeleeAttack.create(20)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	private static ActivityData<ShelobiteScuttlerEntity> initPounceActivity(ShelobiteScuttlerEntity shelobiteScuttler) {
		return ActivityData.create(
				Activity.LONG_JUMP,
				ImmutableList.of(
						Pair.of(0, StopAttackingIfTargetInvalid.create(
								(world, target) -> !isTarget(world, shelobiteScuttler, target))
						),
						//new LeapingChargeTask(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP),
						Pair.of(1, new SpiderPounceTask<>(
								POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE,
								3.5714288F, spider -> SoundEvents.SPIDER_STEP
						))
				),
				ImmutableSet.of(
						Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
						Pair.of(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT)
				),
				ImmutableSet.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getFollowTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Pair.of(SetEntityLookTarget.create(EntityTypes.PLAYER, 8.0F), 1),
						Pair.of(SetEntityLookTarget.create(EntitiesME.SHELOBITE_SCUTTLER, 8.0F), 1),
						Pair.of(SetEntityLookTarget.create(8.0F), 1),
						Pair.of(new DoNothing(30, 60), 1)
				)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getIdleTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Pair.of(RandomStroll.stroll(0.6F), 2),
						Pair.of(InteractWith.of(EntitiesME.SHELOBITE_SCUTTLER, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Pair.of(StrollToPoi.create(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
						Pair.of(StrollAroundPoi.create(MemoryModuleType.HOME, 0.6F, 5), 2),
						Pair.of(new DoNothing(30, 60), 1)
				)
		);
	}
/*
	protected static void tick(MirkwoodSpiderEntity shelobiteScuttler) {
		Random random = shelobiteScuttler.getWorld().getRandom();
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		List<Task<? super MirkwoodSpiderEntity>> runningTasks = brain.getRunningTasks();
		brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP, Activity.FIGHT, Activity.IDLE));
		System.out.println(shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN));
		shelobiteScuttler.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
	}

	protected static void updateActivities(MirkwoodSpiderEntity shelobiteScuttler) {
		//Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));

		Optional<LivingEntity> isAttacking = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
		if(isAttacking != null && isAttacking.isPresent()) {
			Optional<Integer> canJump = brain.getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
			if(canJump != null && canJump.isPresent()) {
				int cooldown =  canJump.get();
				if(cooldown == 0) {
					System.out.println("JUMP!!");
					brain.forget(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
					brain.doExclusively(Activity.LONG_JUMP);
				} else {
					brain.doExclusively(Activity.FIGHT);
				}
			} else {
				brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP));
				brain.doExclusively(Activity.FIGHT);
				brain.remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, POUNCE_COOLDOWN_RANGE.get(shelobiteScuttler.getRandom()));
			}
		}
		else {
			brain.doExclusively(Activity.IDLE);
		}
		Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
		System.out.println(activity);
	}

	protected static void updateActivities(MirkwoodSpiderEntity shelobiteScuttler) {
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		Optional<LivingEntity> isAttacking = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
		if(isAttacking != null && isAttacking.isPresent()) {
			Optional<Integer> canJump = brain.getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
			if(canJump != null && canJump.isPresent()) {
				int cooldown =  canJump.get();
				if(cooldown == 0) {
					System.out.println("JUMP!!");
					brain.forget(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
				}
				else {
					brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT));
				}
			} else {
				brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP));
				brain.remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, POUNCE_COOLDOWN_RANGE.get(shelobiteScuttler.getRandom()));
			}
		}
		else {
			brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));
		}
		brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));
		shelobiteScuttler.getBrain().refreshActivities(shelobiteScuttler.getWorld().getTimeOfDay(), shelobiteScuttler.getWorld().getTime());

		Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
		System.out.println(activity);
	}

	public static void updateActivities(AxolotlEntity axolotl) {
		Brain<AxolotlEntity> brain = axolotl.getBrain();
		Activity activity = (Activity)brain.getFirstPossibleNonCoreActivity().orElse(null);
		if (activity != Activity.PLAY_DEAD) {
			brain.resetPossibleActivities(ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
			if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
				brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
			}
		}
	}*/

	private static boolean isTarget(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		return getTarget(world, shelobiteScuttler).filter(targetx -> targetx == target).isPresent();
	}

	private static Optional<? extends LivingEntity> getTarget(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler) {
		Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(shelobiteScuttler, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight(world, shelobiteScuttler, (LivingEntity)optional.get())) {
			return optional;
		} else {
			Optional<? extends LivingEntity> optional2 = shelobiteScuttler.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
			return optional2.isPresent() ? optional2 : shelobiteScuttler.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
		}
	}

	protected static void tryRevenge(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		if (!(target instanceof AbstractPiglin)) {
			tryRevenge(world, shelobiteScuttler, target);
		}
	}

	protected static void setTarget(ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		shelobiteScuttler.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		shelobiteScuttler.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, target.getUUID(), 600L);
	}

	protected static void playSoundRandomly(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		if (shelobiteScuttlerEntity.level().getRandom().nextFloat() < 0.0125) {
			playSoundIfAngry(shelobiteScuttlerEntity);
		}
	}

	private static void playSoundIfAngry(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		shelobiteScuttlerEntity.getBrain().getActiveNonCoreActivity().ifPresent(activity -> {
			if (activity == Activity.FIGHT) {
				//mirkwoodSpiderEntity.playAngrySound();
			}
		});
	}
}
