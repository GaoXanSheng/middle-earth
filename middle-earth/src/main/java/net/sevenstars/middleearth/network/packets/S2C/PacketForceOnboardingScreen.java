package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;

import java.util.ArrayList;
import java.util.List;

public class PacketForceOnboardingScreen extends ServerToClientPacket<PacketForceOnboardingScreen> {
    public static final Type<PacketForceOnboardingScreen> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_force_onboarding_screen"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketForceOnboardingScreen> CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            ByteBufCodecs.COMPOUND_TAG, p -> p.attributeList,
            PacketForceOnboardingScreen::new
    );
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, CompoundTag attributeList) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketForceOnboardingScreen(float delayOnTeleportationConfirm, Player player) {
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    public Type<PacketForceOnboardingScreen> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketForceOnboardingScreen> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().hasInfiniteMaterials())
            delay = 0;

        Player player = context.player();
        var controller = new OnboardingFactionScreenController(player.level(), delay, AttributePoolElement.obtainAttributeList(attributeList));
        controller.open();
    }
}
