package net.sevenstars.middleearth.item.dataComponents;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public record ArtisanDataComponent(UUID uuid) implements TooltipProvider {
    private static final Codec<ArtisanDataComponent> BASE_CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(UUIDUtil.AUTHLIB_CODEC.fieldOf("uuid").forGetter(ArtisanDataComponent::uuid))
                .apply(instance, ArtisanDataComponent::new);
    });
    public static final Codec<ArtisanDataComponent> CODEC = Codec.withAlternative(BASE_CODEC, UUIDUtil.AUTHLIB_CODEC, ArtisanDataComponent::new);

    public static final StreamCodec<ByteBuf, ArtisanDataComponent> PACKET_CODEC  = StreamCodec.composite(UUIDUtil.STREAM_CODEC, ArtisanDataComponent::uuid,
            ArtisanDataComponent::new);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> textConsumer, TooltipFlag type, DataComponentGetter components) {
        net.minecraft.world.item.component.ResolvableProfile profile = net.minecraft.world.item.component.ResolvableProfile.createUnresolved(uuid);
        String name = profile.name().orElse(null);
        if (name == null) {
            var client = net.minecraft.client.Minecraft.getInstance();
            if (client != null && client.getConnection() != null) {
                var info = client.getConnection().getPlayerInfo(uuid);
                if (info != null) {
                    name = info.getProfile().name();
                }
            }
        }
        if (name != null) {
            textConsumer.accept(Component.translatable("tooltip.%s.artisan".formatted(MiddleEarth.MOD_ID)).append(name).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public UUID uuid() {
        return uuid;
    }
}
