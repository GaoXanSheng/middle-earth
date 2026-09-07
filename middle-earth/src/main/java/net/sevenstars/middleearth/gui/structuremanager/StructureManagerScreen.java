package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.SearchBarWidget;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResult;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResultType;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(value= EnvType.CLIENT)
public class StructureManagerScreen extends AbstractContainerScreen<StructureManagerScreenHandler> {
    private static final Identifier TEXTURE = MiddleEarth.ofPath("textures", "gui", "structure_manager.png");

    public SearchBarWidget searchBarWidget;
    public Component runtimeDataText;
    public Identifier dataIdentifier;
    public Button toInitializeToggleButton;
    public Button isEnabledToggleButton;
    public Button showAllButton;
    public Button respawnAllButton;

    public ArrayList<Identifier> identifiers;

    private static final int TEXT_COLOR = Color.WHITE.getRGB();

    public StructureManagerScreen(StructureManagerScreenHandler handler, Inventory playerInventory, Component title) {
        super(handler, playerInventory, title);

        Level world = playerInventory.player.level();

        this.identifiers = new ArrayList<>();
        for(ResourceKey<StructureManagerData> data : world.registryAccess().lookup(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).get().registryKeySet()){
            this.identifiers.add(data.identifier());
        }

        this.dataIdentifier = handler.getDataIdentifier();
    }

    @Override
    protected void init() {
        super.init();

        List<SearchBarResult> results = new ArrayList<>();
        for(Identifier identifier : this.identifiers){
            results.add(new SearchBarResult(Component.translatable(identifier.toLanguageKey("structure_manager_data")), identifier, SearchBarResultType.NORMAL, button -> selectIdentifier(identifier)));
        }

        this.searchBarWidget = new SearchBarWidget(9, results, x -> updateScreenInformation(), 170);
        addRenderableWidget(this.searchBarWidget.getSearchBarToggleButton());
        this.searchBarWidget.getAllButtons().forEach(this::addRenderableWidget);
        addRenderableWidget(this.searchBarWidget.getScreenClickButton());

        toInitializeToggleButton = Button.builder(Component.nullToEmpty("toInitializeToggleButton"),x -> toggleToInitialize()).build();
        toInitializeToggleButton.setSize(15, 15);
        addRenderableWidget(toInitializeToggleButton);

        isEnabledToggleButton = Button.builder(Component.nullToEmpty("isEnabledToggleButton"),x -> toggleEnable()).build();
        isEnabledToggleButton.setSize(15, 15);
        addRenderableWidget(isEnabledToggleButton);

        showAllButton = Button.builder(Component.nullToEmpty("showAll"),x -> menu.triggerGlowOnAllEntities()).build();
        showAllButton.setSize(104, 20);
        addRenderableWidget(showAllButton);

        respawnAllButton = Button.builder(Component.nullToEmpty("showAll"),x -> menu.triggerRespawnAllEntities()).build();
        respawnAllButton.setSize(104, 20);
        addRenderableWidget(respawnAllButton);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float deltaTicks) {
        super.extractRenderState(context, mouseX, mouseY, deltaTicks);

        ModWidget.updateMouse(mouseX, mouseY);

        int centerX = (int) (minecraft.gui.screen().width / 2f);
        int startY = 70;

        int managerSearchBarWidgetStartY = startY;
        managerSearchBarWidgetStartY += this.searchBarWidget.drawSearchBar(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY, font);
        this.searchBarWidget.setEndY(startY + 500);

        if(this.searchBarWidget.searchIsToggled()) {
            this.searchBarWidget.drawSearchResults(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY - 20);
        }

        Component selectedIdText = (dataIdentifier == null)
                ? Component.translatable("N/A")
                : Component.translatable(dataIdentifier.toLanguageKey("structure_manager_data"));
        this.runtimeDataText = Component.translatable("ui.middle-earth.structure_manager.label_selected_id", selectedIdText).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.WHITE);

        context.text(this.font, this.runtimeDataText, centerX + 5, startY + 5, TEXT_COLOR, false);

        startY += 20;
        toInitializeToggleButton.setPosition(centerX + 5, startY);

        boolean toInitializeToggleButtonFocused = toInitializeToggleButton.isMouseOver(mouseX, mouseY) || toInitializeToggleButton.isFocused();
        int toInitializeToggleButtonUvY = 1;
        if(menu.getToInitialize())
            toInitializeToggleButtonUvY = toInitializeToggleButtonFocused ? 52 : 35;
        else if(toInitializeToggleButtonFocused)
            toInitializeToggleButtonUvY = 18;

        if(menu.getDataIdentifier() == null){
            toInitializeToggleButton.active = false;
            isEnabledToggleButton.active = false;
            showAllButton.active = false;
            respawnAllButton.active = false;
            return;
        } else {
            toInitializeToggleButton.active = true;
            isEnabledToggleButton.active = true;
            showAllButton.active = true;
            respawnAllButton.active = true;
        }

        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                toInitializeToggleButton.getX(), toInitializeToggleButton.getY(),
                1, toInitializeToggleButtonUvY,
                toInitializeToggleButton.getWidth(), toInitializeToggleButton.getHeight(), 256, 256);
        if(toInitializeToggleButton.isMouseOver(mouseX, mouseY))
            context.setTooltipForNextFrame(Component.nullToEmpty("[SET TO TRUE] Before saving a structure."), toInitializeToggleButton.getX(), toInitializeToggleButton.getY());

        isEnabledToggleButton.setPosition(centerX + 25, startY);
        boolean isEnabledToggleButtonFocused = isEnabledToggleButton.isMouseOver(mouseX, mouseY) || isEnabledToggleButton.isFocused();
        int isEnabledToggleButtonUvY = 1;
        if(menu.getIsEnabled())
            isEnabledToggleButtonUvY = isEnabledToggleButtonFocused ? 52 : 35;
        else if(isEnabledToggleButtonFocused)
            isEnabledToggleButtonUvY = 18;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                isEnabledToggleButton.getX(), isEnabledToggleButton.getY(),
                18, isEnabledToggleButtonUvY,
                isEnabledToggleButton.getWidth(), isEnabledToggleButton.getHeight(), 256, 256);
        if(isEnabledToggleButton.isMouseOver(mouseX, mouseY))
            context.setTooltipForNextFrame(Component.nullToEmpty("[SET TO FALSE] Before saving a structure."), isEnabledToggleButton.getX(), isEnabledToggleButton.getY());

        startY += 15;
        showAllButton.setPosition(centerX + 5, startY);
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                showAllButton.getX(), showAllButton.getY(),
                35, showAllButton.isMouseOver(mouseX, mouseY) ? 23 : 1,
                showAllButton.getWidth(), showAllButton.getHeight(), 256, 256);
        Component showAllText = Component.translatable("Show all");
        int showAllStartX = showAllButton.getX() + (showAllButton.getWidth() / 2) - (font.width(showAllText) / 2);
        context.text(font, showAllText, showAllStartX, showAllButton.getY() + 6, Color.BLACK.getRGB(), false);

        startY += showAllButton.getHeight() + 4;
        respawnAllButton.setPosition(centerX + 5, startY);
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                respawnAllButton.getX(), respawnAllButton.getY(),
                35, respawnAllButton.isMouseOver(mouseX, mouseY) ? 23 : 1,
                respawnAllButton.getWidth(), respawnAllButton.getHeight(), 256, 256);
        Component respawnAllText = Component.translatable("Respawn all");
        int respawnAllStartX = respawnAllButton.getX() + (respawnAllButton.getWidth() / 2) - (font.width(respawnAllText) / 2);
        context.text(font, respawnAllText, respawnAllStartX, respawnAllButton.getY() + 6, Color.BLACK.getRGB(), false);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        int keyCode = event.key();
        int scanCode = event.scancode();
        int modifiers = event.modifiers();
        this.searchBarWidget.keyPressed(keyCode, scanCode, modifiers);
        if(keyCode <= 90 && keyCode >= 65)
            return true;
        return super.keyPressed(event);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        this.searchBarWidget.mouseReleased(event.x(), event.y(), event.button());
        return super.mouseReleased(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        this.searchBarWidget.keyReleased(event.key(), event.scancode(), event.modifiers());
        return super.keyReleased(event);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        this.searchBarWidget.mouseDragged(event.x(), event.y(), event.button(), deltaX, deltaY);
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        String text = event.codepointAsString();
        if(!text.isEmpty()){
            this.searchBarWidget.charTyped(text.charAt(0), 0);
        }
        return super.charTyped(event);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }

    private void selectIdentifier(Identifier identifier) {
        this.menu.selectIdentifier(minecraft.player, identifier);
        this.dataIdentifier = identifier;
    }

    private void toggleToInitialize() {
        this.menu.toggleToInitialize();
    }

    private void toggleEnable() {
        this.menu.toggleToActivate();
    }

    private void updateScreenInformation() {

    }
}
