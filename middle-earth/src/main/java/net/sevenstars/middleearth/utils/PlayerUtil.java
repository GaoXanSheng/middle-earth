package net.sevenstars.middleearth.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.RaceType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerUtil {
    public static boolean isAgainstWall(Player entity) {
        AABB boundingBox = entity.getBoundingBox();
        Level world = entity.level();
        double testMovementAmount = 0.0001;

        Vec3 testMovement = new Vec3(testMovementAmount, 0.0, testMovementAmount);
        List<VoxelShape> collisions = world.getEntityCollisions(entity, boundingBox.expandTowards(testMovement));
        Vec3 result = Entity.collideBoundingBox(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return checkIfBlockIsAllowed(world, entity);
        }

        testMovement = new Vec3(-testMovementAmount, 0.0, -testMovementAmount);
        collisions = world.getEntityCollisions(entity, boundingBox.expandTowards(testMovement));
        result = Entity.collideBoundingBox(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return checkIfBlockIsAllowed(world, entity);
        }

        return false;
    }

    private static boolean checkIfBlockIsAllowed(Level world, Player player) {
        BlockState blockstate = world.getBlockState(player.blockPosition().relative(player.getDirection()));
        boolean isSolid = blockstate.isRedstoneConductor(world, player.blockPosition());
        boolean isAllowed = !blockstate.is(TagKey.create(Registries.BLOCK, MiddleEarth.of("climbing_attribute_unallowed_blocks")));
        return isSolid && isAllowed;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull RaceType type){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.level(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return raceType == type;
            }
        }
        return false;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull List<RaceType> types){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.level(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return types.contains(raceType);
            }
        }
        return false;
    }

    public static Faction fetchFaction(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null){
            try {
                return FactionLookup.getFactionById(entity.level(), data.getFaction());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
    public static SpawnData fetchSpawn(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null && data.getSpawn() != null){
            try {
                Faction faction = FactionLookup.getFactionById(entity.level(), data.getFaction());
                return faction.getSpawnData().findSpawn(data.getSpawn());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
}
