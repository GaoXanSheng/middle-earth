package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreen;
import net.sevenstars.middleearth.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.sevenstars.middleearth.gui.return_confirmation.ReturnConfirmationScreen;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingScreenHandler;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.List;

public class InscriptionEnchantInfoPacket extends ServerToClientPacket<InscriptionEnchantInfoPacket> {
    public static final Type<InscriptionEnchantInfoPacket> ID = new Type<>(MiddleEarth.of("inscription_enchant_info_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionEnchantInfoPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, p -> p.enchant,
            ByteBufCodecs.INT, p -> p.level,
            ByteBufCodecs.INT, p -> p.maxLevel,
            ByteBufCodecs.BYTE_ARRAY, p -> p.words,
            InscriptionEnchantInfoPacket::new
    );

    private final String enchant;
    private final int level;
    private final int maxLevel;
    private final byte[] words;

    public InscriptionEnchantInfoPacket(String enchant, int level, int maxLevel, byte[] words) {
        this.enchant = enchant;
        this.level = level;
        this.maxLevel = maxLevel;
        this.words = words;
    }

    @Override
    public Type<InscriptionEnchantInfoPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, InscriptionEnchantInfoPacket> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        Minecraft client = Minecraft.getInstance();
        InscriptionTableScreen screen = (InscriptionTableScreen)client.gui.screen();
        if (screen != null) screen.updateInfo(this.enchant, this.level, this.maxLevel);

        try{
            InscriptionTableScreenHandler screenHandler = (InscriptionTableScreenHandler) context.player().containerMenu;
            screenHandler.updateAvailableWords(words);
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("InscriptionWordUpdate error: ", e);
        }
    }
}
