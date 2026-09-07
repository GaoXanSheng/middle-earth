package net.sevenstars.middleearth.resources.persistent_datas;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelData;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PlayerDataService {
    private static PlayerData getPlayerData(Player player){
        return StateSaverAndLoader.getPlayerState(player);
    }

    public static boolean clearPlayerData(Player player){
        PlayerData data = getPlayerData(player);
        data.assignNewRace(null);
        data.assignNewFactionInformation(null, null);
        data.assignNewOrigin(null, null);
        return true;
    }
    public static boolean playerPassedOnboarding(Player player){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return false;
        return !(playerData.getFaction() == null || playerData.getSpawn() == null);
    }
    public static Faction getPlayerFaction(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier factionId = playerData.getFaction();
        if(factionId == null) return null;
        try{
            return FactionLookup.getFactionById(world, factionId);
        } catch (FactionIdentifierException exception){
            return null;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, Identifier factionId){
        PlayerData playerData = getPlayerData(player);
        try{
            Faction faction = FactionLookup.getFactionById(world, factionId);
            setNewFactionInformation(player, world, factionId, faction.getSpawnData().getDefaultSpawn());
            return true;
        } catch (FactionIdentifierException exception){
            return false;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, Identifier factionId, Identifier spawnId){
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewFactionInformation(factionId, spawnId);
        return true;
    }
    public static DispositionType getPlayerDisposition(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return DispositionType.NEUTRAL;
        return faction.getDisposition();
    }
    public static Race getPlayerRace(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier raceId = playerData.getRace();
        if(raceId == null) return null;
        try{
            return RaceLookup.getRace(world, raceId);
        } catch (Exception exception){
            return null;
        }
    }
    public static boolean setRace(Player player, Level world, Identifier raceId){
        Race newRace = RaceLookup.getRace(world, raceId);
        if(newRace == null) return false;
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewRace(raceId);
        newRace.applyPlayerAttributes(player);
        return true;
    }
    public static SpawnData getPlayerSpawnData(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return null;
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        Identifier spawnId = playerData.getSpawn();
        if(spawnId == null) return null;
        return faction.getSpawnData().findSpawn(spawnId);
    }
    public static boolean setSpawn(ServerPlayer player, Level world, Identifier spawnId) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        if(faction.getSpawnData().findSpawn(spawnId) != null){
            PlayerData data = getPlayerData(player);
            data.assignNewFactionInformation(faction.getId(), spawnId);

            if(ModDimensions.isInMiddleEarth(player.level())){
                ServerPlayer.RespawnConfig respawn = new ServerPlayer.RespawnConfig(LevelData.RespawnData.of(ModDimensions.ME_WORLD_KEY, getPlayerSpawnData(player, world).getBlockPos(), 0f, 0f), true);
                player.setRespawnPosition(respawn, true);
                return true;
            }

            return true;
        }
        return false;
    }
    public static boolean resetSpawn(ServerPlayer player, Level world) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        SpawnDataHandler spawnDataHandler= faction.getSpawnData();
        if(spawnDataHandler == null) return false;
        setSpawn(player, world, spawnDataHandler.getDefaultSpawn());
        return true;
    }
    public static OriginAggregate getOriginAggregateOrDefault(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return getDefaultOriginAggregate(world);
        BlockPos originPos = playerData.getOriginPos();
        Identifier dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return getDefaultOriginAggregate(world);
        }
        if(dimensionId == null){
            dimensionId = BuiltinDimensionTypes.OVERWORLD.identifier();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static OriginAggregate getOriginAggregate(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        BlockPos originPos = playerData.getOriginPos();
        Identifier dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return null;
        }
        if(dimensionId == null){
            dimensionId = BuiltinDimensionTypes.OVERWORLD.identifier();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static boolean setOrigin(ServerPlayer player, Level world, Identifier dimensionId, BlockPos originPos) {
        if(world.registryAccess().lookup(Registries.DIMENSION_TYPE).get() instanceof Registry<DimensionType> registry){
            if(registry.getValue(dimensionId) == null){
                PlayerData data = getPlayerData(player);
                data.assignNewOrigin(dimensionId, originPos);
                return true;
            }
        }
        return false;
    }
    public static boolean resetOrigin(ServerPlayer player, Level world) {
        OriginAggregate newOrigin = getDefaultOriginAggregate(world);
        PlayerData data = getPlayerData(player);
        data.assignNewOrigin(newOrigin.dimensionId, newOrigin.origin);
        return true;
    }
    private static OriginAggregate getDefaultOriginAggregate(Level world){
        return new OriginAggregate(
                Level.OVERWORLD.identifier(),
                world.getServer().overworld().getRespawnData().pos()
        );
    }

    public record OriginAggregate(Identifier dimensionId, BlockPos origin){

    }
}
