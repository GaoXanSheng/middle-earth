package net.sevenstars.middleearth.network;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.network.connections.IConnectionToClient;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.C2S.*;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.*;

import java.util.function.BiConsumer;

public class ModServerNetworkHandler {
    public static void register(IConnectionToClient connection) {
        // REGISTRY : Server to client
        PayloadTypeRegistry.clientboundPlay().register(PacketOnboardingResult.ID, PacketOnboardingResult.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PacketForceOnboardingScreen.ID, PacketForceOnboardingScreen.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PacketLivingEntityData.ID, PacketLivingEntityData.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(InscriptionEnchantInfoPacket.ID, InscriptionEnchantInfoPacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ShapingAnvilRecipePacket.ID, ShapingAnvilRecipePacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(ArtisanRecipePacket.ID, ArtisanRecipePacket.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PacketOpenMapScreen.ID, PacketOpenMapScreen.CODEC);

        // REGISTRY : Client to server
        PayloadTypeRegistry.serverboundPlay().register(PacketStructureManagerRespawnEntities.ID, PacketStructureManagerRespawnEntities.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketStructureManagerShowAllEntities.ID, PacketStructureManagerShowAllEntities.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketStructureNestUpdateBlockEntityRequest.ID, PacketStructureNestUpdateBlockEntityRequest.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketStructureManagerUpdateBlockEntityRequest.ID, PacketStructureManagerUpdateBlockEntityRequest.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketSetAffiliation.ID, PacketSetAffiliation.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketSetRace.ID, PacketSetRace.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketTeleportToDynamicWorldCoordinate.ID, PacketTeleportToDynamicWorldCoordinate.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketTeleportToCustomCoordinate.ID, PacketTeleportToCustomCoordinate.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketTeleportToDynamicCoordinate.ID, PacketTeleportToDynamicCoordinate.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketTeleportToCurrentSpawn.ID, PacketTeleportToCurrentSpawn.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketTeleportToCurrentOverworldSpawn.ID, StreamCodec.unit(new PacketTeleportToCurrentOverworldSpawn()));
        PayloadTypeRegistry.serverboundPlay().register(PacketSetSpawnData.ID, PacketSetSpawnData.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(PacketOnboardingRequest.ID, StreamCodec.unit(new PacketOnboardingRequest()));
        PayloadTypeRegistry.serverboundPlay().register(ForgeOutputPacket.ID, ForgeOutputPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ForgeModeSwitchPacket.ID, ForgeModeSwitchPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(InscriptionWordUpdatePacket.ID, InscriptionWordUpdatePacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(InscriptionConfirmationPacket.ID, StreamCodec.unit(new InscriptionConfirmationPacket()));
        PayloadTypeRegistry.serverboundPlay().register(AnvilIndexPacket.ID, AnvilIndexPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ArtisanIndexPacket.ID, ArtisanIndexPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(ArtisanTableTabPacket.ID, ArtisanTableTabPacket.CODEC);
        PayloadTypeRegistry.serverboundPlay().register(HoodStateTogglePacket.ID, StreamCodec.unit(new HoodStateTogglePacket()));

        // Application [SERVER SIDE]
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerRespawnEntities.ID, wrapServerHandler(connection, PacketStructureManagerRespawnEntities::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerShowAllEntities.ID, wrapServerHandler(connection, PacketStructureManagerShowAllEntities::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureNestUpdateBlockEntityRequest.ID, wrapServerHandler(connection, PacketStructureNestUpdateBlockEntityRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketStructureManagerUpdateBlockEntityRequest.ID, wrapServerHandler(connection, PacketStructureManagerUpdateBlockEntityRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetAffiliation.ID, wrapServerHandler(connection, PacketSetAffiliation::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetRace.ID, wrapServerHandler(connection, PacketSetRace::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentOverworldSpawn.ID, wrapServerHandler(connection, PacketTeleportToCurrentOverworldSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicWorldCoordinate.ID, wrapServerHandler(connection, PacketTeleportToDynamicWorldCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCustomCoordinate.ID, wrapServerHandler(connection, PacketTeleportToCustomCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToDynamicCoordinate.ID, wrapServerHandler(connection, PacketTeleportToDynamicCoordinate::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketTeleportToCurrentSpawn.ID, wrapServerHandler(connection, PacketTeleportToCurrentSpawn::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketSetSpawnData.ID, wrapServerHandler(connection, PacketSetSpawnData::process));
        ServerPlayNetworking.registerGlobalReceiver(PacketOnboardingRequest.ID, wrapServerHandler(connection, PacketOnboardingRequest::process));
        ServerPlayNetworking.registerGlobalReceiver(ForgeOutputPacket.ID, wrapServerHandler(connection, ForgeOutputPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ForgeModeSwitchPacket.ID, wrapServerHandler(connection, ForgeModeSwitchPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(InscriptionWordUpdatePacket.ID, wrapServerHandler(connection, InscriptionWordUpdatePacket::process));
        ServerPlayNetworking.registerGlobalReceiver(InscriptionConfirmationPacket.ID, wrapServerHandler(connection, InscriptionConfirmationPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(AnvilIndexPacket.ID, wrapServerHandler(connection, AnvilIndexPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ArtisanIndexPacket.ID, wrapServerHandler(connection, ArtisanIndexPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(ArtisanTableTabPacket.ID, wrapServerHandler(connection, ArtisanTableTabPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(HoodStateTogglePacket.ID, wrapServerHandler(connection, HoodStateTogglePacket::process));
    }

    private static <T extends ClientToServerPacket<T>> ServerPlayNetworking.PlayPayloadHandler<T> wrapServerHandler(
            IConnectionToClient connection,
            BiConsumer<T, ServerPacketContext> consumer
    ) {
        return (t, payloadContext) -> {
            ServerPlayer player = payloadContext.player();
            var serverPacketContext = new ServerPacketContext(player, connection);
            consumer.accept(t, serverPacketContext);
        };
    }
}
