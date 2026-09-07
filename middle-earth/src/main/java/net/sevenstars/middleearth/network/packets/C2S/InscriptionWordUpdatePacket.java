package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class InscriptionWordUpdatePacket extends ClientToServerPacket<InscriptionWordUpdatePacket> {
    public static final Type<InscriptionWordUpdatePacket> ID = new Type<>(MiddleEarth.of("inscription_word_update_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionWordUpdatePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.add,
            ByteBufCodecs.STRING_UTF8, p -> p.word,
            InscriptionWordUpdatePacket::new
    );

    private final boolean add;
    private final String word;

    public InscriptionWordUpdatePacket(boolean add, String word) {
        this.add = add;
        this.word = word;
    }

    @Override
    public Type<InscriptionWordUpdatePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InscriptionWordUpdatePacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().level().getServer().execute(() -> {
                InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().containerMenu;
                screenHandler.updateWords(this.add, this.word, false);
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }
}
