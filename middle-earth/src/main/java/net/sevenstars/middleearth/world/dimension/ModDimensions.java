package net.sevenstars.middleearth.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.registries.RegistryAliasesME;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import org.joml.Vector3i;

public class ModDimensions {
    public static Identifier ME_DIMENSION_ID = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "middle_earth");
    public static Identifier OW_DIMENSION_ID = Identifier.parse("overworld");

    public static final ResourceKey<LevelStem> ME_DIMENSION_KEY =
            ResourceKey.create(Registries.LEVEL_STEM, ME_DIMENSION_ID);

    public static ResourceKey<Level> ME_WORLD_KEY =
            ResourceKey.create(Registries.DIMENSION, ME_DIMENSION_ID);

    public static final ResourceKey<LevelStem> OW_DIMENSION_KEY =
            ResourceKey.create(Registries.LEVEL_STEM, Identifier.parse("overworld"));

    public static ResourceKey<Level> OW_WORLD_KEY =
            ResourceKey.create(Registries.DIMENSION, OW_DIMENSION_ID);

    public static void register() {
        Registry.register(BuiltInRegistries.CHUNK_GENERATOR, ME_DIMENSION_ID, MiddleEarthChunkGenerator.CODEC);
        ME_WORLD_KEY = ResourceKey.create(Registries.DIMENSION, ME_DIMENSION_ID);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.CHUNK_GENERATOR, ME_DIMENSION_ID.getPath()));

        MiddleEarth.LOGGER.logDebugMsg("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }

    public static Vector3i getDimensionHeight(int x, int z) {
        MiddleEarthHeightMap.getHeight(x, z);
        int height = (int) (1 + MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z));
        return new Vector3i(x, height, z);
    }

    public static void teleportPlayerToMe(Player player, Vec3 coordinates, boolean setSpawnPoint, boolean welcomeNeeded){
        if(!player.level().isClientSide()) {
            ResourceKey<Level> registryKey = ME_WORLD_KEY;
            ServerLevel serverWorld = (ServerLevel) player.level();
            if (serverWorld != null) {

                player.teleport(new TeleportTransition(serverWorld.getServer().getLevel(ME_WORLD_KEY), coordinates, Vec3.ZERO, 0, 0, new TeleportTransition.PostTeleportTransition() {
                    @Override
                    public void onTransition(Entity entity) {
                        // idk
                    }
                }));
                if(setSpawnPoint){
                    ServerPlayer.RespawnConfig respawn = new ServerPlayer.RespawnConfig(LevelData.RespawnData.of(registryKey, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), player.getYRot(), player.getXRot()), true);
                    ((ServerPlayer) player).setRespawnPosition(respawn, true);
                }
                if(welcomeNeeded)
                    FactionUtil.sendOnFactionJoinMessage(player);
                Race race =  PlayerDataService.getPlayerRace(player, player.level());
                if(race != null){
                    RaceUtil.updateRace(player, race, false);
                }

            }
        }
    }

    public static boolean isInMiddleEarth(Level world){
        return world.dimension().identifier().equals(ME_DIMENSION_ID);
    }

    public static boolean isInOverworld(Level world){
        return world.dimension().identifier().equals(OW_DIMENSION_ID);
    }

    public static boolean teleportPlayerToOverworld(Player player) {
        if(!player.level().isClientSide()) {
            ResourceKey<Level> registryKey = OW_WORLD_KEY;
            ServerLevel serverWorld = (ServerLevel) player.level();
            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(player, player.level());
            BlockPos coordinate;
            if(origin == null) {
                coordinate = serverWorld.getServer().overworld().getLevelData().getRespawnData().pos();
            } else {
                coordinate = origin.origin();
            }

            if (serverWorld != null) {
                Vec3 coordinates = new Vec3(coordinate.getX(), coordinate.getY(), coordinate.getZ());
                player.teleport(new TeleportTransition(serverWorld.getServer().overworld(), coordinates, Vec3.ZERO, 0, 0, entity -> {
                    // idk
                }));
                
                if(!ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    AttributePool.reverse(player);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * For future usage only, not necessary for now
     * @return world coordinate for current map coordinate selected based on map iteration/pixel weight
     */
    public static Vector3i getSpawnCoordinate(){
        Vector3i spawnCoordinate = new Vector3i(939, 90, 915);;
        double worldIteration = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION);
        int x = (int)((spawnCoordinate.x * worldIteration));
        int z = (int)((spawnCoordinate.z * worldIteration));

        return new Vector3i(x, spawnCoordinate.y, z);
    }
}
