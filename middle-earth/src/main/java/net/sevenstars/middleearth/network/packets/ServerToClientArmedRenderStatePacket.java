package net.sevenstars.middleearth.network.packets;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.contexts.RenderStatePacketContext;
import net.sevenstars.middleearth.network.packets.S2C.PacketLivingEntityData;

public abstract class ServerToClientArmedRenderStatePacket<T extends ServerToClientArmedRenderStatePacket<T>> implements CustomPacketPayload {
    @Override
    public abstract Type<T> type();
    public abstract StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
    public abstract void process(RenderStatePacketContext context);
}