package net.sevenstars.middleearth.permissions;

import net.minecraft.server.level.ServerPlayer;

public class PermissionsME {
    public static boolean checkMapTeleport(ServerPlayer player) {
        if (player.level().getServer() != null) {
            return player.level().getServer().getPlayerList().isOp(player.nameAndId());
        }
        return false;
    }
}
