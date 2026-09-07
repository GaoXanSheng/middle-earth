package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.network.packets.C2S.PacketStructureManagerRespawnEntities;
import net.sevenstars.middleearth.network.packets.C2S.PacketStructureManagerShowAllEntities;
import net.sevenstars.middleearth.network.packets.C2S.PacketStructureManagerUpdateBlockEntityRequest;

public class StructureManagerScreenHandler extends AbstractContainerMenu {

    private final Level world;
    private StructureManagerScreenData data;
    StructureManagerBlockEntity blockEntity;

    // Client side Constructor
    public StructureManagerScreenHandler(int syncId, Inventory playerInventory, StructureManagerScreenData structureManagerScreenData) {
        super(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.level();
        this.data = structureManagerScreenData;
        this.blockEntity = (StructureManagerBlockEntity) this.world.getBlockEntity(data.getPos());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void selectIdentifier(Player player, Identifier identifier) {
        this.data.setStructureManagerIdentifier(identifier);
        ClientPlayNetworking.send(new PacketStructureManagerUpdateBlockEntityRequest(
                data.getPos(),
                data.getStructureManagerIdentifier(),
                data.getToInitialize(),
                data.getIsActive()));
    }

    public BlockPos getPos() {
        return this.data.getPos();
    }

    public Identifier getDataIdentifier() {
        return this.data.getStructureManagerIdentifier();
    }

    public boolean getToInitialize() {
        return this.data.getToInitialize();
    }
    public boolean getIsEnabled() {
        return this.data.getIsActive();
    }

    public void toggleToInitialize() {
        this.data.setToInitialize(!this.data.getToInitialize());
        updateServer();
    }

    private void updateServer() {
        ClientPlayNetworking.send(new PacketStructureManagerUpdateBlockEntityRequest(this.data.getPos(), this.data.getStructureManagerIdentifier(), this.data.getToInitialize(), this.data.getIsActive()));
    }

    public void toggleToActivate() {
        this.data.setActive(!this.data.getIsActive());
        updateServer();
    }
    public void triggerGlowOnAllEntities() {
        ClientPlayNetworking.send(new PacketStructureManagerShowAllEntities(this.data.getPos()));
    }

    public void triggerRespawnAllEntities() {
        ClientPlayNetworking.send(new PacketStructureManagerRespawnEntities(this.data.getPos()));
    }
}

