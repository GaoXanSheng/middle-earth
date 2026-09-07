package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.api.utils.IdentifierUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;

public class PacketSetRace extends ClientToServerPacket<PacketSetRace>
{
    public static final Type<PacketSetRace> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_set_race"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetRace> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, p -> p.race,
            PacketSetRace::new
    );

    private final String race;

    public PacketSetRace(String race){
        this.race = race;
    }

    @Override
    public Type<PacketSetRace> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketSetRace> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        MinecraftServer server = context.player().level().getServer();
        server.execute(() -> {
            try{
                RaceUtil.updateRace(context.player(), RaceLookup.getRace(context.player().level(), MiddleEarth.fetchId(race)), true);
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("PacketSetRace::Tried setting race for player.", e);
            }
        });
    }
}