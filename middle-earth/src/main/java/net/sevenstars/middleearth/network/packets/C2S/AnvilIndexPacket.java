package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class AnvilIndexPacket extends ClientToServerPacket<AnvilIndexPacket> {
    public static final Type<AnvilIndexPacket> ID = new Type<>(MiddleEarth.of("anvil_index_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, AnvilIndexPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.index,
            ByteBufCodecs.DOUBLE, p -> p.x,
            ByteBufCodecs.DOUBLE, p -> p.y,
            ByteBufCodecs.DOUBLE, p -> p.z,
            AnvilIndexPacket::new
    );

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private final int index;
    private final double x;
    private final double y;
    private final double z;

    public AnvilIndexPacket(int index, double x, double y, double z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Type<AnvilIndexPacket> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, AnvilIndexPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            if(index >= 0) {
                context.player().level().getServer().execute(() -> {
                    Vec3 coordinates = new Vec3(x, y, z);
                    ShapingAnvilBlockEntity.updateIndex(index, coordinates, context.player());
                });
            } else {
                ServerPlayer player = context.player();
                AbstractContainerMenu screenHandler = player.containerMenu;
                if (screenHandler instanceof ShapingAnvilScreenHandler anvilScreenHandler) {
                    anvilScreenHandler.updateScreen();
                }
            }
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketAnvilIndex error: ", e);
        }
    }
}
