package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class PacketSetSpawnData extends ClientToServerPacket<PacketSetSpawnData>
{
    public static final Type<PacketSetSpawnData> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_spawn_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetSpawnData> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.overworldX,
            ByteBufCodecs.INT, p -> p.overworldY,
            ByteBufCodecs.INT, p -> p.overworldZ,
            PacketSetSpawnData::new
    );

    private final int overworldX;
    private final int overworldY;
    private final int overworldZ;
    public PacketSetSpawnData(int overworldX, int overworldY, int overworldZ){
        this.overworldX = overworldX;
        this.overworldY = overworldY;
        this.overworldZ = overworldZ;
    }

    @Override
    public Type<PacketSetSpawnData> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketSetSpawnData> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().level().getServer().execute(() -> {

                MinecraftServer server = context.player().level().getServer();
                ServerPlayer player = server.getPlayerList().getPlayer(context.player().getUUID());

                BlockPos overworldSpawnBlockpos = new BlockPos(overworldX, overworldY, overworldZ);
                PlayerDataService.setOrigin(player, player.level(), BuiltinDimensionTypes.OVERWORLD.identifier(), overworldSpawnBlockpos);
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("SpawnDataPacket::Apply - Tried applying the spawn data packet",e);
        }
    }

    @Override
    public String toString() {
        return "Overworld="+overworldX+","+overworldY+","+overworldZ+";";
    }
}