package net.sevenstars.middleearth.gui.map;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.event.KeyInputHandler;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToDynamicWorldCoordinate;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import org.joml.Vector2d;

public class MapScreenController {
    private Level world;
    private Player player;
    private MapScreen screen;
    private boolean isInDimension;
    private boolean isFullscreen;
    private boolean hasTeleportPermission;

    public MapScreenController(Level world, Player player) {
        this.world = world;
        this.player = player;
    }

    public boolean open(boolean canTeleport) {
        Minecraft mc = Minecraft.getInstance();

        if(world.isClientSide()) {
            if (mc.gui.screen() == null) {
                screen = new MapScreen();
                isInDimension = ModDimensions.isInMiddleEarth(world);

                hasTeleportPermission = canTeleport;
                screen.playerIsInDimension = isInDimension;
                screen.hasTeleportPermission = hasTeleportPermission;
                screen.isFullscreen = false;

                screen.playerBlockPos = player.blockPosition();
                screen.controller  = this;
                mc.gui.setScreen(screen);
                return true;
            }
        }
        return false;
    }

    private void teleportToCursor(double mouseX, double mouseY) {
        if(!hasTeleportPermission) return;
        Vector2d mapRatio = screen.mapWidget.getCurrentMapRatio(mouseX, mouseY);
        if(mapRatio != null){
            double x = mapRatio.x * MiddleEarthMapConfigs.FULL_MAP_SIZE;
            double y = mapRatio.y * MiddleEarthMapConfigs.FULL_MAP_SIZE;

            ClientPlayNetworking.send(new PacketTeleportToDynamicWorldCoordinate(x, y));
            screen.onClose();
        }
    }

    public boolean mouseClicked(MouseButtonEvent event) {
        screen.mapWidget.mouseClicked(event.x(), event.y(), event.button());
        if(KeyInputHandler.mapTeleportKey.matchesMouse(event)){
            teleportToCursor(event.x(), event.y());
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matchesMouse(event)){
            screen.isFullscreen = !screen.isFullscreen;
        }
        return true;
    }

    public boolean keyPressed(KeyEvent event, int mouseX, int mouseY) {
        if(KeyInputHandler.mapTeleportKey.matches(event)){
            teleportToCursor(mouseX, mouseY);
            return true;
        }
        if(KeyInputHandler.mapFullscreenToggle.matches(event)){
            screen.isFullscreen = !screen.isFullscreen;
        }
        if(event.key() == 258 && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }
        return true;
    }
}
