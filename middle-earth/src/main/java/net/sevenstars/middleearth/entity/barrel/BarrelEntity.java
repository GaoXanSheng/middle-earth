package net.sevenstars.middleearth.entity.barrel;

import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BarrelEntity extends AbstractChestBoat {
    public BarrelEntity(EntityType<? extends AbstractChestBoat> type, Level world, Supplier<Item> itemSupplier) {
        super(type, world, itemSupplier);
    }

    @Override
    protected double rideHeight(EntityDimensions dimensions) {
        return dimensions.height() / 1.75F;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().size() < this.getMaxPassengers();
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return (new Vec3(0.0, this.rideHeight(dimensions), -0.2f)).yRot(-this.getYRot() * 0.017453292F);
    }
}
