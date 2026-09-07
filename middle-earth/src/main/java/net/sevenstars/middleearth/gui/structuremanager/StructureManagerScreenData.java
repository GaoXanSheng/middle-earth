package net.sevenstars.middleearth.gui.structuremanager;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public class StructureManagerScreenData{
    private BlockPos pos;
    private Identifier structureManagerIdentifier;
    private boolean isActive;
    private boolean toInitialize;

    public static final StreamCodec<? super RegistryFriendlyByteBuf, StructureManagerScreenData> PACKET_CODEC;

    public BlockPos getPos() {
        return this.pos;
    }
    public Identifier getStructureManagerIdentifier() {
        return this.structureManagerIdentifier;
    }
    private Optional<Identifier> getStructureManagerIdentifierOptional() {
        return Optional.ofNullable(this.structureManagerIdentifier);
    }

    public boolean getIsActive() {
        return this.isActive;
    }
    public boolean getToInitialize() {
        return this.toInitialize;
    }

    public void setStructureManagerIdentifier(Identifier structureManagerIdentifier) {
        this.structureManagerIdentifier = structureManagerIdentifier;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public void setToInitialize(boolean toInitialize) {
        this.toInitialize = toInitialize;
    }

    public StructureManagerScreenData(BlockPos pos, boolean isActive, boolean toInitialize, Optional<Identifier> structureManagerId){
        this.pos = pos;
        setActive(isActive);
        setToInitialize(toInitialize);
        structureManagerId.ifPresentOrElse(this::setStructureManagerIdentifier, () -> setStructureManagerIdentifier(null));
    }

    static {
        PACKET_CODEC = StreamCodec.composite(
                BlockPos.STREAM_CODEC, StructureManagerScreenData::getPos,
                ByteBufCodecs.BOOL, StructureManagerScreenData::getIsActive,
                ByteBufCodecs.BOOL, StructureManagerScreenData::getToInitialize,
                ByteBufCodecs.optional(Identifier.STREAM_CODEC), StructureManagerScreenData::getStructureManagerIdentifierOptional,
                StructureManagerScreenData::new
        );
    }
}
