package net.sevenstars.middleearth.entity.beasts.great_horn;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.List;

public record GreatHornVariant(GreatHornAssetInfo assetInfo, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<GreatHornVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							GreatHornAssetInfo.CODEC.fieldOf("assets").forGetter(GreatHornVariant::assetInfo),
							SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(GreatHornVariant::spawnConditions)
					)
					.apply(instance, GreatHornVariant::new)
	);

	public static final Codec<Holder<GreatHornVariant>> ENTRY_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<GreatHornVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(DynamicRegistriesME.GREAT_HORN_VARIANTS);

	private GreatHornVariant(GreatHornAssetInfo assetInfo) {
		this(assetInfo, SpawnPrioritySelectors.EMPTY);
	}

	@Override
	public List<Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}

	public record GreatHornAssetInfo(Identifier id) {
		public static final Codec<GreatHornAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(Identifier.CODEC.fieldOf("id").forGetter(GreatHornAssetInfo::id)).apply(instance, GreatHornAssetInfo::new)
		);
	}
}