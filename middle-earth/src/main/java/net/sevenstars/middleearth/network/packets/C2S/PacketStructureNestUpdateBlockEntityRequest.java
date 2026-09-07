package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

import java.util.Optional;

public class PacketStructureNestUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureNestUpdateBlockEntityRequest>
{
    public static final Type<PacketStructureNestUpdateBlockEntityRequest> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "structure_nest_update_block_entity_request"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureNestUpdateBlockEntityRequest> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, p -> p.pos,
            ByteBufCodecs.optional(Identifier.STREAM_CODEC), p -> p.getStructureManagerId(),
            ByteBufCodecs.optional(Identifier.STREAM_CODEC), p -> p.getStructureNestId(),
            ByteBufCodecs.INT, p -> p.spawnRadius,
            ByteBufCodecs.BOOL, p -> p.isEnabled,
            PacketStructureNestUpdateBlockEntityRequest::new
    );

    private Optional<Identifier> getStructureManagerId() {
        return Optional.ofNullable(structureManagerId);
    }

    private Optional<Identifier> getStructureNestId() {
        return Optional.ofNullable(structureNestId);
    }

    private final BlockPos pos;
    private final Identifier structureManagerId;
    private final Identifier structureNestId;
    private final int spawnRadius;
    private final boolean isEnabled;

    public PacketStructureNestUpdateBlockEntityRequest(BlockPos pos, Optional<Identifier> structureManagerId, Optional<Identifier> structureNestId, int spawnRadius, boolean isEnabled) {
        this.pos = pos;
        this.spawnRadius = spawnRadius;
        this.isEnabled = isEnabled;

        if(structureManagerId.isPresent())
            this.structureManagerId = structureManagerId.get();
        else
            this.structureManagerId = null;

        if(structureNestId.isPresent())
            this.structureNestId = structureNestId.get();
        else
            this.structureNestId = null;
    }

    @Override
    public Type<PacketStructureNestUpdateBlockEntityRequest> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketStructureNestUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().level().getServer();
            server.execute(() -> {
                if(context.player().level().getBlockEntity(pos) instanceof StructureNestBlockEntity blockEntity){
                    blockEntity.setStructureManagerId(structureManagerId);
                    blockEntity.setStructureNestId(structureNestId);
                    blockEntity.setSpawnRadius(spawnRadius);
                    blockEntity.setIsEnabled(isEnabled);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureNestUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}