package net.sevenstars.middleearth.entity.seat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.SeatBlock;

public class SeatEntity extends VehicleEntity {

    public SeatEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    @Override
    public void tick() {
        if(!this.level().isClientSide()){
            Level world = this.level();
            BlockPos pos = this.blockPosition();
            if (!this.isVehicle() && this.level().getBlockState(pos).is(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "seat")))){
                world.setBlockAndUpdate(this.blockPosition(), world.getBlockState(pos).setValue(SeatBlock.OCCUPIED, false));
                this.remove(RemovalReason.DISCARDED);
            } else if(!this.isVehicle() || !this.level().getBlockState(pos).is(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "seat")))){
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput view) {

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {

    }

    @Override
    protected void removePassenger(Entity passenger) {
        Level world = passenger.level();
        if (!world.isClientSide()){
            BlockPos pos = this.blockPosition();
            passenger.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            super.removePassenger(passenger);
        }
    }

}
