package net.sevenstars.middleearth.resources.datas.combatarchetypes;
import net.minecraft.world.phys.Vec3;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public abstract class CombatArchetypeData {
    protected CombatArchetype combatArchetype;

    private final float fleeMovementSpeedModifier;
    private final float seekTargetMovementSpeedModifier;
    private final float optimalBlockRangeMinimum; // If the target is too close, they will try to fall back to that distance
    private final float optimalBlockRangeMaximum; // If the target is too far, they will try to fall in to that distance

    public CombatArchetypeData(float fleeMovementSpeedModifier, float seekTargetMovementSpeedModifier, float optimalBlockRangeMinimum, float optimalBlockRangeMaximum) {
        setArchetype(getCombatArchetype());
        this.fleeMovementSpeedModifier = fleeMovementSpeedModifier;
        this.seekTargetMovementSpeedModifier = seekTargetMovementSpeedModifier;
        this.optimalBlockRangeMinimum = optimalBlockRangeMinimum;
        this.optimalBlockRangeMaximum = optimalBlockRangeMaximum;
    }

    public CombatArchetypeData(CompoundTag data) {
        setArchetype(getCombatArchetype());

        this.fleeMovementSpeedModifier = data.getFloatOr("flee_movement_speed_modifier", 1f);
        this.seekTargetMovementSpeedModifier = data.getFloatOr("seek_target_movement_speed_modifier", 1f);
        this.optimalBlockRangeMinimum = data.getIntOr("optimal_block_range_minimum", 1);
        this.optimalBlockRangeMaximum = data.getIntOr("optimal_block_range_maximum", 3);
    }

    public CompoundTag getNbt(){
        CompoundTag nbt = new CompoundTag();
        if(this.combatArchetype == null)
            this.combatArchetype = CombatArchetype.MELEE;

        nbt.putString("type", combatArchetype.name());

        CompoundTag dataNbt =  getDataNbt();

        nbt.put("data", dataNbt);

        return nbt;
    }

    protected CompoundTag getDataNbt() {
        CompoundTag nbtCompound = new CompoundTag();

        nbtCompound.putFloat("flee_movement_speed_modifier", fleeMovementSpeedModifier);
        nbtCompound.putFloat("seek_target_movement_speed_modifier", seekTargetMovementSpeedModifier);
        nbtCompound.putFloat("optimal_block_range_minimum", optimalBlockRangeMinimum);
        nbtCompound.putFloat("optimal_block_range_maximum", optimalBlockRangeMaximum);

        return nbtCompound;
    }

    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };

    public CombatArchetype getArchetype() {
        return this.combatArchetype;
    }
    protected void setArchetype(CombatArchetype combatArchetype) {
        this.combatArchetype = combatArchetype;
    }

    public float getFleeSpeedModifier() {
        return this.fleeMovementSpeedModifier;
    }

    public float getSeekSpeedModifier() {
        return this.seekTargetMovementSpeedModifier;
    }

    public boolean isInOptimalRange(LivingEntity source, BlockPos target) {
        double distance = Vec3.atCenterOf(source.blockPosition()).distanceTo(Vec3.atCenterOf(target));
        return distance > optimalBlockRangeMinimum && distance < optimalBlockRangeMaximum;
    }

}
