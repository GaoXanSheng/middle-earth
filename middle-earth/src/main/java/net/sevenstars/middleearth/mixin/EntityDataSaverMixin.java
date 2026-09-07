package net.sevenstars.middleearth.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class EntityDataSaverMixin implements IEntityDataSaver {
    private final String KEY = MiddleEarth.MOD_ID;
    private CompoundTag persistentData;

    @Override
    public CompoundTag getPersistentData() {
        if(this.persistentData == null) {
            this.persistentData = new CompoundTag();
        }
        return persistentData;
    }

    /*@Inject(method = "writeCustomData", at = @At("HEAD"))
    protected void writeCustomDataToNbt(WriteView view, CallbackInfo ci) {
        if(persistentData != null) {
            view.putString(KEY, persistentData);
        }
    }

    @Inject(method = "readCustomData", at = @At("HEAD"))
    protected void readCustomDataToNbt(ReadView view, CallbackInfo ci) {
        if(view.contains(KEY)) {
            persistentData = view.getString(KEY).get();
        }
    }*/

}
