package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.races.Race;

import java.util.function.Consumer;

public record RaceDataComponent(Identifier raceId) implements TooltipProvider {
    private static final Codec<RaceDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Identifier.CODEC.fieldOf("faction").forGetter(RaceDataComponent::raceId))
                .apply(instance, RaceDataComponent::new);
    });
    public static final Codec<RaceDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, Identifier.CODEC, RaceDataComponent::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, RaceDataComponent> PACKET_CODEC =
            StreamCodec.composite(Identifier.STREAM_CODEC, RaceDataComponent::raceId, RaceDataComponent::new);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        Race race = context.registries().lookupOrThrow(DynamicRegistriesME.RACE).getOrThrow(ResourceKey.create(DynamicRegistriesME.RACE, this.raceId)).value();
        textConsumer.accept(Component.translatable("tooltip.%s.race".formatted(MiddleEarth.MOD_ID)).withStyle(ChatFormatting.DARK_RED)
            .append(Component.translatable(race.getId().toLanguageKey("race")).withStyle(ChatFormatting.WHITE)));
    }

    @Override
    public Identifier raceId() {
        return raceId;
    }
}