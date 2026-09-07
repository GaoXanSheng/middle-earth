package net.sevenstars.middleearth.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin extends Player {
    @Shadow public MinecraftServer server;
    @Shadow public ServerPlayerGameMode gameMode;

    public ServerPlayerEntityMixin(Level world, GameProfile profile) {
        super(world, profile);
    }

    @Nullable
    @Override
    public GameType gameMode() {
        return this.gameMode.getGameModeForPlayer();
    }

    @Inject(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void getRespawnTargetWithBrokenBed(boolean alive, TeleportTransition.PostTeleportTransition postDimensionTransition, CallbackInfoReturnable<TeleportTransition> cir) {
        if(ModDimensions.isInMiddleEarth(this.level())){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(toOverworldSpawn(postDimensionTransition));
    }

    @Inject(method = "findRespawnPositionAndUseSpawnBlock", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    public void getRespawnTargetNatural(boolean alive, TeleportTransition.PostTeleportTransition postDimensionTransition, CallbackInfoReturnable<TeleportTransition> cir) {
        if(ModDimensions.isInMiddleEarth(this.level())){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(toOverworldSpawn(postDimensionTransition));
    }

    @Unique
    private TeleportTransition toOverworldSpawn(TeleportTransition.PostTeleportTransition postDimensionTransition) {
        ServerLevel overworld = this.server.overworld();
        LevelData.RespawnData respawnData = overworld.getRespawnData();
        return new TeleportTransition(overworld, Vec3.atBottomCenterOf(respawnData.pos()), Vec3.ZERO, respawnData.yaw(), respawnData.pitch(), postDimensionTransition);
    }

    @Unique
    private void resetSpawn(TeleportTransition.PostTeleportTransition postDimensionTransition, CallbackInfoReturnable<TeleportTransition> cir){
        if(this.server == null) return;

        PlayerList manager = this.server.getPlayerList();
        ServerPlayer foundPlayer = manager.getPlayer(this.getUUID());
        if(this.server == null) return;
        ServerLevel overworld = this.server.overworld();
        LevelData.RespawnData respawnData = overworld.getRespawnData();
        foundPlayer.setRespawnPosition(new ServerPlayer.RespawnConfig(respawnData, true), true);

        cir.setReturnValue(new TeleportTransition(overworld, Vec3.atBottomCenterOf(respawnData.pos()), Vec3.ZERO, respawnData.yaw(), respawnData.pitch(), postDimensionTransition));
    }

    @Unique
    private boolean tryToOverrideSpawn(TeleportTransition.PostTeleportTransition postDimensionTransition, CallbackInfoReturnable<TeleportTransition> cir) {
        MinecraftServer server = this.server;

        if(server == null) return false;
        PlayerList manager = server.getPlayerList();
        ServerPlayer foundPlayer = manager.getPlayer(this.getUUID());

        if(foundPlayer == null) return false;
        if(ModDimensions.isInMiddleEarth(this.level()) && PlayerDataService.getPlayerSpawnData(foundPlayer, level()) instanceof SpawnData data && data.getIdentifier() != null) {
            BlockPos spawnCoordinates = data.getWorldCoordinateBlockPos();
            if(spawnCoordinates != null){
                ServerLevel MEWorld = this.server.getLevel(ModDimensions.ME_WORLD_KEY);
                if(MEWorld != null){
                    Vec3 coordinates = new Vec3(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ());

                    foundPlayer.setRespawnPosition(new ServerPlayer.RespawnConfig(LevelData.RespawnData.of(ModDimensions.ME_WORLD_KEY, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), 0f, 0f), true), true);

                    cir.setReturnValue(new TeleportTransition(MEWorld, Vec3.atCenterOf(spawnCoordinates), Vec3.ZERO, 0, 0, postDimensionTransition));
                    return true;
                }
            }
        }
        ServerLevel overworld = server.overworld();
        LevelData.RespawnData respawnData = overworld.getRespawnData();
        foundPlayer.setRespawnPosition(new ServerPlayer.RespawnConfig(respawnData, true), true);

        cir.setReturnValue(new TeleportTransition(overworld, Vec3.atCenterOf(respawnData.pos()), Vec3.ZERO, 0, 0, postDimensionTransition));
        return false;
    }

    @Override
    public boolean isSpectator() {
        return this.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
        if(isCreative() || isSpectator()) {
            if(hasEffect(ModStatusEffects.ENSHROUDED) && getEffect(ModStatusEffects.ENSHROUDED).isInfiniteDuration()){
                forceAddEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);
                forceAddEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 40), this);
            }
            return;
        }

        long currentTick = level().nextSubTickCount();
        if(currentTick % 5 == 0){
            if(level() == null) return;
            PlayerData data = StateSaverAndLoader.getPlayerState(level().getPlayerByUUID(getUUID()));
            if(data == null) return;

            int currentLightLevel = level().getMaxLocalRawBrightness(blockPosition());

            double delversFearStrenght = getAttributeValue(EntityAttributesME.DELVERS_FEAR_STRENGTH);

            if(delversFearStrenght > 0.0 && currentLightLevel < 3 && !level().canSeeSky(blockPosition())) {
                data.addToDelversFearCountInSeconds();

                if(data.getDelversFearCountInSeconds() > delversFearStrenght){
                    addEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, -1));
                    addEffect(new MobEffectInstance(MobEffects.DARKNESS, -1));
                    addEffect(new MobEffectInstance(MobEffects.WEAKNESS, -1));
                }
            } else {
                if(hasEffect(ModStatusEffects.ENSHROUDED) && getEffect(ModStatusEffects.ENSHROUDED).isInfiniteDuration()){
                    forceAddEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                    forceAddEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                    forceAddEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);

                }
                data.resetDelversFearCount();
            }
        }
    }
}
