package net.sevenstars.middleearth.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class ServerWorldMixin {
    @Shadow @Final
    private MinecraftServer server;

    // Reason of addition
    // MC-188578 - Sleeping in a bed in a custom dimension doesn't set time to day
    // Link : https://bugs.mojang.com/browse/MC-188578
    // TODO 26.2: the world clock/day-time API has been reworked (Level#getOverworldClockTime /
    // ServerClockManager / WORLD_CLOCK dynamic registry). Restore advancing to morning when
    // GameRules daylight cycle is on once that rework is mirrored here.
    @Inject(method = "wakeUpAllPlayers", at = @At("TAIL"))
    private void wakeSleepingPlayers(CallbackInfo ci) {
    }

    @Inject(method = "addFreshEntity", at = @At("TAIL"))
    private void onSpawn(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() || !(entity instanceof LivingEntity living))
            return;
        BiomeEventDataLookup.addEntity(living);
    }
}
