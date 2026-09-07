package net.sevenstars.middleearth.network.contexts;

import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.network.connections.IConnectionToServer;

public record RenderStatePacketContext(ArmedEntityRenderState renderState, ServerPlayer playerEntity, IConnectionToServer connection) {

}