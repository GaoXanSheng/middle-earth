package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreen;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ArtisanRecipePacket extends ServerToClientPacket<ArtisanRecipePacket> {
    public static final Type<ArtisanRecipePacket> ID = new Type<>(MiddleEarth.of("artisan_recipe_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.index,
            ItemStack.STREAM_CODEC, p -> p.output,
            ArtisanRecipePacket::new
    );

    private final int index;
    private final ItemStack output;

    public ArtisanRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    public Type<ArtisanRecipePacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipePacket> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        Minecraft client = Minecraft.getInstance();
        ArtisanTableScreen screen = (ArtisanTableScreen)client.gui.screen();
        if (screen != null) {
            ArtisanTableScreenHandler screenHandler = screen.getMenu();
            if(screenHandler != null) {
                screenHandler.addRecipeOutput(this.index, this.output);
            }
        }
    }
}
