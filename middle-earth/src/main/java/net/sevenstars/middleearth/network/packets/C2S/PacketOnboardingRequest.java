package net.sevenstars.middleearth.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketOnboardingResult;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class PacketOnboardingRequest extends ClientToServerPacket<PacketOnboardingRequest>
{
    public static final Type<PacketOnboardingRequest> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_onboarding_request"));
    public static final PacketOnboardingRequest INSTANCE = new PacketOnboardingRequest();
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingRequest> CODEC = StreamCodec.of((buf, packet) -> {}, buf -> INSTANCE);

    @Override
    public Type<PacketOnboardingRequest> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().level().getServer().execute(() -> {
                ServerPlayer player = context.player();

                PacketOnboardingResult newPacket = new PacketOnboardingResult(
                        PlayerDataService.playerPassedOnboarding(context.player()),
                        ModServerConfigs.ENABLE_FACTION_RESET,
                        ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD,
                        ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION,
                        AttributePoolElement.createAttributeNbtListFromPlayer(player)
                );
                ServerPlayNetworking.send(player, newPacket);
            });
        } catch(Exception e){
            MiddleEarth.LOGGER.logError("OnboardingDetailFetchingPacket::Apply - Tried sending packet with data", e);
        }
    }
}
