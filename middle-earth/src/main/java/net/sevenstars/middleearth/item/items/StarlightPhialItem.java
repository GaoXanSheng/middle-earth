package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.network.packets.C2S.PacketOnboardingRequest;

public class StarlightPhialItem extends Item {
    public StarlightPhialItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {
        if(world.isClientSide()){
            ClientPlayNetworking.send(new PacketOnboardingRequest());
        }
        return InteractionResult.SUCCESS;
    }
}
