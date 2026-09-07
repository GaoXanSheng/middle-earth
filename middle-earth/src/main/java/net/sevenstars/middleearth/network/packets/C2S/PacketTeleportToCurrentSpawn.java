package net.sevenstars.middleearth.network.packets.C2S;
import net.minecraft.world.phys.Vec3;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PacketTeleportToCurrentSpawn extends ClientToServerPacket<PacketTeleportToCurrentSpawn> {
    public static final Type<PacketTeleportToCurrentSpawn> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_teleport_current_spawn"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentSpawn> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.welcomeNeeded,
            PacketTeleportToCurrentSpawn::new
    );
    private Boolean welcomeNeeded;

    public PacketTeleportToCurrentSpawn(boolean welcomeNeeded){
        this.welcomeNeeded = welcomeNeeded;
    }
    @Override
    public Type<PacketTeleportToCurrentSpawn> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentSpawn> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().level().getServer().execute(() -> {
                if(PlayerDataService.playerPassedOnboarding(context.player())){
                    SpawnData spawnData = PlayerDataService.getPlayerSpawnData(context.player(), context.player().level());
                    if(spawnData == null)
                        return;
                    BlockPos spawnCoordinates = spawnData.getBlockPos();
                    if(spawnCoordinates != null)
                        ModDimensions.teleportPlayerToMe(context.player(), Vec3.atCenterOf(spawnCoordinates), true, welcomeNeeded);

                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}