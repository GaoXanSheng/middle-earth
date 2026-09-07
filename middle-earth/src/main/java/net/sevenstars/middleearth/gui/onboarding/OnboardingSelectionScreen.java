package net.sevenstars.middleearth.gui.onboarding;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;
import net.minecraft.world.entity.Entity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.sevenstars.middleearth.network.packets.C2S.PacketTeleportToCurrentSpawn;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePool;
import net.sevenstars.middleearth.resources.datas.attributes.AttributePoolElement;

import java.util.List;

@Environment(EnvType.CLIENT)
public class OnboardingSelectionScreen extends Screen {
private static final Component ONBOARDING_SELECTION_TITLE = Component.translatable("ui.%s.onboarding_selection.title".formatted(MiddleEarth.MOD_ID));
    private static final Identifier BUTTON_WIDGET = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    private boolean focusEnabled;
    public Button continueAsCharacterButton;
    public Button resetCharacterButton;

    private int mouseX;
    private int mouseY;
    private final boolean canResetCharacter;
    private LocalPlayer player;
    float currentDelay;

    List<AttributePoolElement> playerAttributes;

    public OnboardingSelectionScreen(float delay, boolean canResetCharacter, List<AttributePoolElement> playerAttributes) {
        super(ONBOARDING_SELECTION_TITLE);
        this.canResetCharacter = canResetCharacter;
        this.playerAttributes = playerAttributes;
        focusEnabled = false;
        currentDelay = delay;
    }

    @Override
    protected void init() {
        Button.OnPress continueAsFaction = button -> {
            teleportPlayerToMiddleEarth();
        };
        continueAsCharacterButton = Button.builder(Component.nullToEmpty("continue_character"), continueAsFaction).build();
        addRenderableWidget(continueAsCharacterButton);
        if(currentDelay > 0)
            continueAsCharacterButton.active = false;

        if(canResetCharacter){
            Button.OnPress resetCharacterAction = button -> {
                var controller = new OnboardingFactionScreenController(this.player.level(), currentDelay, playerAttributes);
                controller.open();
            };
            resetCharacterButton = Button.builder(Component.nullToEmpty("reset_character"), resetCharacterAction).build();
            addRenderableWidget(resetCharacterButton);
        }
    }

    private void teleportPlayerToMiddleEarth() {
        ClientPlayNetworking.send(new PacketTeleportToCurrentSpawn(false));
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.minecraft.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof LocalPlayer clientPlayerEntity) {
                this.player = clientPlayerEntity;
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.drawContent(context);
            } else {
                this.player = null;
            }
        }
    }

    @Override
    public void tick() {
        if(currentDelay > 0){
            currentDelay = Math.max(0, currentDelay - (1f / 20));
            if(currentDelay == 0) {
                continueAsCharacterButton.active = true;
            }
        }
        super.tick();
    }

    private void drawContent(GuiGraphicsExtractor context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        if(continueAsCharacterButton.active){
            context.blit(RenderPipelines.GUI_TEXTURED, BUTTON_WIDGET,
                    startX, startY, 0, continueAsCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX, panelSizeY, 256, 256);

            Component continueText = Component.translatable("ui.%s.continue_character".formatted(MiddleEarth.MOD_ID));
            context.text(font, continueText,
                    startX + (int)((panelSizeX - font.width(continueText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (font.lineHeight / 2f)) + 1,
                    CommonColors.BLACK, false);

            continueAsCharacterButton.setRectangle(panelSizeX, panelSizeY, startX, startY);
            if(focusEnabled && continueAsCharacterButton.isFocused()){
                context.blit(RenderPipelines.GUI_TEXTURED, BUTTON_WIDGET,
                        startX, startY, 103, 0,
                        panelSizeX, panelSizeY, 256, 256);
            }
        } else {
            context.blit(RenderPipelines.GUI_TEXTURED, BUTTON_WIDGET,
                    startX, startY, 0, 38,
                    panelSizeX, panelSizeY, 256, 256);
            Component delayText = Component.literal(String.valueOf((Math.round(this.currentDelay * 10f) /10f)));
            context.text(font, delayText,
                    startX + (panelSizeX / 2) - (font.width(delayText) / 2),
                    startY + 5, CommonColors.SOFT_RED, true);
        }

        if(canResetCharacter) {

            startY += panelSizeY + margin;

            context.blit(RenderPipelines.GUI_TEXTURED, BUTTON_WIDGET,
                    startX, startY,0, resetCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                    panelSizeX, panelSizeY, 256, 256);

            Component resetText = Component.translatable("ui.%s.reset_character".formatted(MiddleEarth.MOD_ID));
            context.text(font, resetText,
                    startX + (int) ((panelSizeX - font.width(resetText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (font.lineHeight / 2f)) + 1,
                    CommonColors.BLACK, false);
            resetCharacterButton.setRectangle(panelSizeX, panelSizeY, startX, startY);
            if(focusEnabled && resetCharacterButton.isFocused()){
                context.blit(RenderPipelines.GUI_TEXTURED, BUTTON_WIDGET,
                        startX, startY,0, 103, 0,
                        panelSizeX, panelSizeY, 256, 256);
            }
        }
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        // Keybind : Tabulation
        if(event.key() == 258 && !focusEnabled){
            focusEnabled = true;
            return true;
        }

        return super.keyPressed(event);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }
}
