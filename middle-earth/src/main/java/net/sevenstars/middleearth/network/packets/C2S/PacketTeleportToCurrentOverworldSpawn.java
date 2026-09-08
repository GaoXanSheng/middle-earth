package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.item.items.StarlightPhialItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PacketTeleportToCurrentOverworldSpawn extends ClientToServerPacket<PacketTeleportToCurrentOverworldSpawn> {
    public static final Type<PacketTeleportToCurrentOverworldSpawn> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_teleport_to_current_overworld_spawn"));
    public static final PacketTeleportToCurrentOverworldSpawn INSTANCE = new PacketTeleportToCurrentOverworldSpawn();
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentOverworldSpawn> CODEC = StreamCodec.of((buf, packet) -> {}, buf -> INSTANCE);

    @Override
    public Type<PacketTeleportToCurrentOverworldSpawn> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToCurrentOverworldSpawn> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().level().getServer().execute(() -> {
                RaceUtil.reset(context.player());

                if(ModDimensions.isInMiddleEarth(context.player().level())){
                    ModDimensions.teleportPlayerToOverworld(context.player());
                    RaceUtil.reset(context.player());
                    if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                        RaceUtil.initializeRace(context.player());
                    } else {
                        RaceUtil.reset(context.player());
                    }

                    if(!context.player().isCreative() && context.player().getMainHandItem().getItem() instanceof StarlightPhialItem)
                        context.player().getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketTeleportToCurrentOverworldSpawn::Apply - Tried applying the return to overworld packet",e);
        }
    }
}