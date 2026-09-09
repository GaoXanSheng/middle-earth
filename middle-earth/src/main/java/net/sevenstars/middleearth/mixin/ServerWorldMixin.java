package net.sevenstars.middleearth.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {
    // MC-188578 (sleeping in a custom dimension doesn't set time to day) is fixed natively in
    // 26.2: ServerLevel.tick() moves the dimension's default_clock to the WAKE_UP_FROM_SLEEP
    // time marker, and middle_earth_type.json now declares "default_clock": "minecraft:overworld".
    // No wakeUpAllPlayers injection needed anymore.

    @Inject(method = "addFreshEntity", at = @At("TAIL"))
    private void onSpawn(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || !(entity instanceof LivingEntity living))
            return;
        BiomeEventDataLookup.addEntity(living);
    }
}
