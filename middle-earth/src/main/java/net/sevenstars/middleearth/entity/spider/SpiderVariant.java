package net.sevenstars.middleearth.entity.spider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.List;

public record SpiderVariant(SpiderAssetInfo assetInfo, SpawnPrioritySelectors spawnConditions) implements PriorityProvider<SpawnContext, SpawnCondition> {
	public static final Codec<SpiderVariant> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
							SpiderAssetInfo.CODEC.fieldOf("assets").forGetter(SpiderVariant::assetInfo),
							SpawnPrioritySelectors.CODEC.fieldOf("spawn_conditions").forGetter(SpiderVariant::spawnConditions)
					)
					.apply(instance, SpiderVariant::new)
	);

	public static final Codec<Holder<SpiderVariant>> ENTRY_CODEC = RegistryFixedCodec.create(DynamicRegistriesME.SPIDER_VARIANTS);

	public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SpiderVariant>> PACKET_CODEC = ByteBufCodecs.holderRegistry(DynamicRegistriesME.SPIDER_VARIANTS);

	private SpiderVariant(SpiderAssetInfo assetInfo) {
		this(assetInfo, SpawnPrioritySelectors.EMPTY);
	}

	@Override
	public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
		return this.spawnConditions.selectors();
	}

	public record SpiderAssetInfo(net.minecraft.resources.Identifier larva, net.minecraft.resources.Identifier scuttler, net.minecraft.resources.Identifier spawnOfShelob) {
		public static final Codec<SpiderAssetInfo> CODEC = RecordCodecBuilder.create(
				instance -> instance.group(
								net.minecraft.resources.Identifier.CODEC.fieldOf("larva").forGetter(SpiderAssetInfo::larva),
								net.minecraft.resources.Identifier.CODEC.fieldOf("scuttler").forGetter(SpiderAssetInfo::scuttler),
								net.minecraft.resources.Identifier.CODEC.fieldOf("spawn").forGetter(SpiderAssetInfo::spawnOfShelob)
						)
						.apply(instance, SpiderAssetInfo::new)
		);
	}
}