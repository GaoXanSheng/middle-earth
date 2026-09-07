package net.sevenstars.middleearth.item.items;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.network.packets.S2C.PacketOpenMapScreen;
import net.sevenstars.middleearth.permissions.PermissionsME;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if(user instanceof ServerPlayer serverPlayerEntity) {
            boolean canTeleport = PermissionsME.checkMapTeleport(serverPlayerEntity);
            PacketOpenMapScreen packet = new PacketOpenMapScreen(canTeleport);
            ServerPlayNetworking.send(serverPlayerEntity, packet);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
