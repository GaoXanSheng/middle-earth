package net.sevenstars.middleearth.network.packets.S2C;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class PacketLivingEntityData extends ServerToClientPacket<PacketLivingEntityData> {
    public static final Type<PacketLivingEntityData> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_living_entity_data"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketLivingEntityData> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.entityId,
            MobEffectInstance.STREAM_CODEC, p -> p.statusEffectInstance,
            PacketLivingEntityData::new
    );

    private final int entityId;
    private final MobEffectInstance statusEffectInstance;

    public PacketLivingEntityData(int entityId, MobEffectInstance statusEffectInstance) {
        this.entityId = entityId;
        this.statusEffectInstance = statusEffectInstance;
    }

    @Override
    public Type<PacketLivingEntityData> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketLivingEntityData> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ClientPacketContext context) {
        Entity entity = context.player().level().getEntity(entityId);
        if(entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(statusEffectInstance);
        }
    }
}
