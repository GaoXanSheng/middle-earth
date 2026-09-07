package net.sevenstars.middleearth.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;

import java.util.HashMap;
import java.util.UUID;
/**
 * <a href="https://fabricmc.net/wiki/tutorial:persistent_states">Documentation</a><br/>
 * <a href="https://github.com/TerraformersMC/Biolith/blob/main/common/src/main/java/com/terraformersmc/biolith/impl/config/BiolithState.java">Other Source</a>
 */
public class StateSaverAndLoader extends SavedData {
    private static final SavedDataType<StateSaverAndLoader> TYPE;

    private HashMap<UUID, PlayerData> players;

    public static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CompoundTag.CODEC.fieldOf("player_datas").forGetter(StateSaverAndLoader::getPlayerDataNbt)
    ).apply(instance, StateSaverAndLoader::new));

    public StateSaverAndLoader() {
        this.players = new HashMap<>();
    }
    public StateSaverAndLoader(CompoundTag nbt){
        this.players = new HashMap<>();
        var listOpt = nbt.getList("list");
        if(listOpt.isPresent()){
            var list = listOpt.get();
            for(int i = 0; i < list.size(); i++){
                var playerDataNbt = list.getCompoundOrEmpty(i);
                var uuidOpt = playerDataNbt.getString("uuid");
                var newPlayerData = new PlayerData(playerDataNbt.getCompoundOrEmpty("data"));
                uuidOpt.ifPresent(s -> this.players.put(UUID.fromString(s), newPlayerData));
            }
        }
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>();
        return state;
    }

    private CompoundTag getPlayerDataNbt() {
        CompoundTag nbt = new CompoundTag();
        ListTag list = new ListTag();
        for(UUID uuid : players.keySet()){
            CompoundTag specificNbt = new CompoundTag();
            specificNbt.putString("uuid", uuid.toString());
            specificNbt.put("data", this.players.get(uuid).createNbt());
            list.add(specificNbt);
        }
        nbt.put("list", list);
        return nbt;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        SavedDataStorage persistentStateManager = server.getLevel(Level.OVERWORLD).getDataStorage();
        StateSaverAndLoader state = persistentStateManager.computeIfAbsent(TYPE);
        state.setDirty();
        return state;
    }
    public static PlayerData getPlayerState(Player player) {
        if(player == null || player.level().getServer() == null) return null;
        StateSaverAndLoader serverState = getServerState(player.level().getServer());
        UUID playerUUID = player.getUUID();
        serverState.players.computeIfAbsent(playerUUID, k -> new PlayerData());
        return serverState.players.get(playerUUID);
    }

    static {
        TYPE = new SavedDataType<>(MiddleEarth.of("middle_earth_player_datas"), StateSaverAndLoader::createNew, CODEC, (DataFixTypes) null);
    }
}

