package net.sevenstars.middleearth.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.return_confirmation.ReturnConfirmationScreen;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.handlers.OnboardingScreenHandler;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PacketOnboardingResult extends ServerToClientPacket<PacketOnboardingResult> {
    public static final Type<PacketOnboardingResult> ID = new Type<>(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_onboarding_result"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingResult> CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, p -> p.havePlayerData,
            ByteBufCodecs.BOOL, p -> p.canChangeFaction,
            ByteBufCodecs.BOOL, p -> p.canReturnToOverworld,
            ByteBufCodecs.FLOAT, p -> p.delayOnTeleportationConfirm,
            ByteBufCodecs.COMPOUND_TAG, p -> p.attributeList,
            PacketOnboardingResult::new
    );

    private final boolean havePlayerData;
    private final boolean canChangeFaction;
    private final boolean canReturnToOverworld;
    private final float delayOnTeleportationConfirm;
    private final CompoundTag attributeList;

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm, CompoundTag attributeList) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = attributeList;
    }

    public PacketOnboardingResult(boolean havePlayerData, boolean canChangeFaction, boolean canReturnToOverworld, float delayOnTeleportationConfirm,  Player player) {
        this.havePlayerData = havePlayerData;
        this.canChangeFaction = canChangeFaction;
        this.canReturnToOverworld = canReturnToOverworld;
        this.delayOnTeleportationConfirm = delayOnTeleportationConfirm;
        this.attributeList = AttributePoolElement.createAttributeNbtListFromPlayer(player);
    }

    @Override
    public Type<PacketOnboardingResult> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketOnboardingResult> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        float delay = delayOnTeleportationConfirm;
        if(context.player().hasInfiniteMaterials())
            delay = 0;
        if(ModDimensions.isInMiddleEarth(context.player().level())){
            if(!canReturnToOverworld){
                return;
            }
            Minecraft client = Minecraft.getInstance();
            client.setScreenAndShow(new ReturnConfirmationScreen(delay));
        } else if(ModDimensions.isInOverworld(context.player().level())){
            OnboardingScreenHandler.handle(context, havePlayerData, delay, AttributePoolElement.obtainAttributeList(attributeList));
        }
    }
}
