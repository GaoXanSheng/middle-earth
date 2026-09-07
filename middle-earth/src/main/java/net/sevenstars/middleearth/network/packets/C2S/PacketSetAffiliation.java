package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.StarlightPhialItem;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;

public class PacketSetAffiliation extends ClientToServerPacket<PacketSetAffiliation>
{
    public static final Type<PacketSetAffiliation> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_set_affiliation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSetAffiliation> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, p -> p.dispositionName,
            ByteBufCodecs.STRING_UTF8, p -> p.factionName,
            ByteBufCodecs.STRING_UTF8, p -> p.spawnName,
            PacketSetAffiliation::new
    );

    private final String dispositionName;
    private final String factionName;
    private final String spawnName;

    public PacketSetAffiliation(String dispositionName, String factionName, String spawnName){
        this.dispositionName = dispositionName;
        this.factionName = factionName;
        this.spawnName = spawnName;
    }

    @Override
    public Type<PacketSetAffiliation> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketSetAffiliation> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        MinecraftServer server = context.player().level().getServer();
        server.execute(() -> {
            try{
                Identifier factionId = Identifier.parse(factionName);
                Faction faction = FactionLookup.getFactionById(context.player().level(), factionId);
                Identifier spawnId = Identifier.parse(spawnName);
                FactionUtil.updateFaction(context.player(), faction, spawnId);
                if(!context.player().isCreative() && context.player().getMainHandItem().getItem() instanceof StarlightPhialItem)
                    context.player().getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("AffiliationPacket::Tried getting affiliation packet and couldn't fetch any.", e);
            }
        });
    }
}