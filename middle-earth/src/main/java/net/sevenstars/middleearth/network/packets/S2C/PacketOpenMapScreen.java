package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.map.MapScreenController;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketOpenMapScreen extends ServerToClientPacket<PacketOpenMapScreen> {
    public static final Type<PacketOpenMapScreen> ID = new Type<>(MiddleEarth.of("packet_open_map_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOpenMapScreen> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.canTeleport,
            PacketOpenMapScreen::new
    );
    private final boolean canTeleport;

    public PacketOpenMapScreen(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }
    @Override
    public Type<PacketOpenMapScreen> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketOpenMapScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        Player player = context.player();
        MapScreenController controller = new MapScreenController(player.level(), player);
        controller.open(canTeleport);
    }
}
