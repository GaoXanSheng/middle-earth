package net.sevenstars.middleearth.network.connections;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ConnectionToClient implements IConnectionToClient{
    @Override
    public <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayer player) {
        ServerPlayNetworking.send(player, packet);
    }
}
