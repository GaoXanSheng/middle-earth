package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.Util;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SnailEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(SnailEntity.class, EntityDataSerializers.BOOLEAN);
    public static final int CLIMBING_TIME_TRANSITION = 12;
    private int climbingTicks = 0;

    public SnailEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createSnailAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.05f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ARMOR, 0.5f);
    }

    protected void customServerAiStep(ServerLevel world) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("snailBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("snailActivityUpdate");
        SnailBrain.updateActivities(this);
        profiler.pop();
        super.customServerAiStep(world);
    }

    protected Brain<?> makeBrain(Brain.Packed packed) {
        return SnailBrain.create(this, packed);
    }

    public Brain<SnailEntity> getBrain() {
        return (Brain<SnailEntity>)super.getBrain();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.setClimbingWall(this.horizontalCollision);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(isClimbingWall()) {
            this.setDeltaMovement(this.getDeltaMovement().x(),this.getSpeed() / 5,this.getDeltaMovement().z());
            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        }
        else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    public void setClimbingWall(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    public boolean isClimbingWall() {
        return entityData.get(CLIMBING);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    @Override
    public void jumpFromGround() {
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return EntitiesWT.SNAIL.create(world, EntitySpawnReason.BREEDING);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        SnailEntityVariant variant = Util.getRandom(SnailEntityVariant.values(), this.random);
        setVariant(variant);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(CLIMBING, false);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {
        super.addAdditionalSaveData(view);
        view.putInt("Variant", this.getTypeVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput view) {
        super.readAdditionalSaveData(view);
        this.entityData.set(VARIANT, view.getIntOr("Variant", 0));
    }

    public SnailEntityVariant getVariant() {
        return SnailEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private void setVariant(SnailEntityVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }
}
