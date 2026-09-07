package net.sevenstars.middleearth.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.HoodStateTogglePacket;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final KeyMapping.Category ME_KEY_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "main"));
    public static final String ME_KEY_HOOD_STATE_TOGGLE = "key.%s.hood_state_toggle".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_TELEPORT = "key.%s.map_teleport".formatted(MiddleEarth.MOD_ID);
    public static final String ME_KEY_MAP_FULLSCREEN_TOGGLE = "key.%s.map_fullscreen_toggle".formatted(MiddleEarth.MOD_ID);

    public static KeyMapping hoodStateToggleKey;
    // Used in MapScreen
    public static KeyMapping mapTeleportKey;
    public static KeyMapping mapFullscreenToggle;

    public static void registerKeyInputs(){
        var ref = new Object() {
            int counter = 0;
        };

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(hoodStateToggleKey.isDown()) {
                if (ref.counter == 0) {
                    ref.counter = 1;
                    assert client.player != null;
                    ClientPlayNetworking.send(new HoodStateTogglePacket());
                }
            } else {
                ref.counter = 0;
            }
        });
    }

    public static void register(){
        hoodStateToggleKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                ME_KEY_HOOD_STATE_TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                ME_KEY_CATEGORY
        ));

        mapTeleportKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                ME_KEY_MAP_TELEPORT,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                ME_KEY_CATEGORY
        ));

        mapFullscreenToggle = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                ME_KEY_MAP_FULLSCREEN_TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                ME_KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
