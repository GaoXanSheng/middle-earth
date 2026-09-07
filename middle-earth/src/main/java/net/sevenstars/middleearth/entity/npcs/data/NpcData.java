package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Optional;

public class NpcData {
    public static final Codec<NpcData> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, NpcData> PACKET_CODEC;

    private Holder<NpcType> type;
    private EntityCategories category;
    private final EntitySpawnReason spawnReason;
    private final Optional<BlockPos> structureManagerPos;

    static {
        CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                DynamicRegistriesME.NPC_TYPE_CODEC.optionalFieldOf("Type").forGetter(NpcData::getOptionalType),
                Codec.STRING.optionalFieldOf("Category").forGetter(NpcData::getOptionalCategory),
                Codec.STRING.optionalFieldOf("SpawnReason").forGetter(NpcData::getOptionalSpawnReason),
                BlockPos.CODEC.optionalFieldOf("StructureManagerPos").forGetter((data) -> data.structureManagerPos)
        ).apply(instance, NpcData::new));

        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(DynamicRegistriesME.NPC_TYPE)), NpcData::getOptionalType,
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), NpcData::getOptionalCategory,
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8), NpcData::getOptionalSpawnReason,
                ByteBufCodecs.optional(BlockPos.STREAM_CODEC), NpcData::getOptionalStructureManagerPos,
                NpcData::new);
    }

    public NpcData(Optional<Holder<NpcType>> type, Optional<String> category, Optional<String> spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type.orElse(null);
        this.category = category.map(value -> parseEnum(EntityCategories.class, value)).orElse(null);
        this.spawnReason = spawnReason.map(value -> parseEnum(EntitySpawnReason.class, value)).orElse(null);
        this.structureManagerPos = structureManagerPos == null ||  structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData(Holder<NpcType> type, EntityCategories category, EntitySpawnReason spawnReason, Optional<BlockPos> structureManagerPos) {
        this.type = type;
        this.category = category;
        this.spawnReason = spawnReason;
        this.structureManagerPos = structureManagerPos == null || structureManagerPos.isEmpty() ? Optional.empty() : structureManagerPos;
    }

    public NpcData() {
        this.type = null;
        this.category = null;
        this.spawnReason = null;
        this.structureManagerPos = Optional.empty();
    }

    // [BUILDERS]
    public NpcData withType(Holder<NpcType> type) {
        return new NpcData(type, category, spawnReason, structureManagerPos);
    }

    public NpcData withCategory(EntityCategories category) {
        return new NpcData(this.type, category, this.spawnReason, this.structureManagerPos);
    }

    public NpcData withSpawnReason(EntitySpawnReason spawnReason) {
        return new NpcData(this.type, this.category, spawnReason, this.structureManagerPos);
    }

    public NpcData withStructureManagerPos(BlockPos structureManagerPos) {
        return new NpcData(this.type, this.category, this.spawnReason, Optional.of(structureManagerPos));
    }
    public NpcData withType(Level world, Identifier npcDataIdentifier) {
        if(npcDataIdentifier == null || world == null)
            return this;

        Registry<NpcType> registry = world.registryAccess().lookupOrThrow(DynamicRegistriesME.NPC_TYPE);
        NpcType npcType = registry.getValue(npcDataIdentifier);
        if(npcType == null)
            return this;
        return this.withType(registry.wrapAsHolder(npcType));
    }

    public Identifier getNpcTypeId() {
        if(type == null){
            return null;
        }
        return type.unwrapKey()
                .map(ResourceKey::identifier)
                .orElse(null);
    }

    public Identifier getFaction() {
        if(type == null)
            return null;
        return type.value().getFactionIdentifier();
    }

    public EntityCategories getCategory() {
        return category;
    }

    public EntitySpawnReason getSpawnReason() {
        return spawnReason;
    }

    public NpcType getNpcType() {
        if(type == null)
            return null;
        return type.value();
    }

    private Optional<Holder<NpcType>> getOptionalType() {
        return type != null ? Optional.of(type) : Optional.empty();
    }

    private Optional<String> getOptionalCategory() {
        return category != null ? Optional.of(category.name()) : Optional.empty();
    }
    private Optional<String> getOptionalSpawnReason() {
        return spawnReason != null ? Optional.of(spawnReason.name()) : Optional.empty();
    }

    private Optional<BlockPos> getOptionalStructureManagerPos() {
        return structureManagerPos;
    }

    public BlockPos getStructureManagerPos() {
        return structureManagerPos.orElse(null);
    }

    private static <E extends Enum<E>> E parseEnum(Class<E> type, String value) {
        try {
            return Enum.valueOf(type, value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
