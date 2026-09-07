package net.sevenstars.middleearth.item.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.gui.map.MapScreenController;
import net.sevenstars.middleearth.gui.playerbook.PlayerBookScreen;
import net.sevenstars.middleearth.gui.return_confirmation.ReturnConfirmationScreen;

public class PlayerBookItem extends Item {
    public PlayerBookItem(Properties settings) {
        super(settings);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if(world.isClientSide()) {
            Minecraft client = Minecraft.getInstance();
            client.setScreenAndShow(new PlayerBookScreen(Component.nullToEmpty("Player's book")));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
