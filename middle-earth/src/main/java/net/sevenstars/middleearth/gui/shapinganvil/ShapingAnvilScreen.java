package net.sevenstars.middleearth.gui.shapinganvil;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.packets.C2S.AnvilIndexPacket;

import java.util.ArrayList;
import java.util.List;

public class ShapingAnvilScreen extends AbstractContainerScreen<ShapingAnvilScreenHandler> {
    private static final Identifier TEXTURE = MiddleEarth.ofPath( "textures", "gui", "shaping_anvil.png");

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    private List<ItemStack> outputs;

    public ShapingAnvilScreen(ShapingAnvilScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        outputs = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        BlockPos pos = this.menu.getPos();
        AnvilIndexPacket newPacket = new AnvilIndexPacket(-1, pos.getX(), pos.getY(), pos.getZ());
        ClientPlayNetworking.send(newPacket);
    }

    public void addRecipe(int index, ItemStack output) {
        boolean exists = outputs.stream().anyMatch(item -> output.getItem().equals(item.getItem()));
        if(exists) return;

        while (outputs.size() < index) {
            outputs.add(ItemStack.EMPTY);
        }
        outputs.add(output);
    }

    public void clearOutputs() {
        outputs.clear();
    }

    private boolean shouldScroll() {
        return this.outputs.size() > 12;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean blursPreviousSelection) {
        double mouseX = event.x();
        double mouseY = event.y();
        int i = this.leftPos + 20;
        int j = this.topPos + 14;
        int k = this.scrollOffset + 12;

        for(int l = this.scrollOffset; l < k; ++l) {
            int m = l - this.scrollOffset;
            double d = mouseX - (double)(i + m % 4 * 16);
            double e = mouseY - (double)(j + m / 4 * 18);
            if (d >= 0.0 && e >= 0.0 && d < 16.0 && e < 18.0 && (this.menu).clickMenuButton(this.minecraft.player, l)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.menu.setSelectedRecipe(l);
                return true;
            }
        }

        i = this.leftPos + 119;
        j = this.topPos + 9;
        if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
            this.mouseClicked = true;
        }

        return super.mouseClicked(event, blursPreviousSelection);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        context.text(this.font, this.title, this.titleLabelX, this.titleLabelY - 1, -12566464, false);
        context.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, -12566464, false);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        int k = (int)(41.0F * this.scrollAmount);
        context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 87, y + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 20, 12, 15, 256, 256);

        int l = this.leftPos + 19;
        int m = this.topPos + 14;
        int n = this.scrollOffset + 12;
        this.renderRecipeBackground(context, mouseX, mouseY, l + 1, m, n);
        this.renderRecipeIcons(context, l, m, n);

        this.renderOutputTooltip(context, mouseX, mouseY);
        this.renderHammerTooltip(context, mouseX, mouseY);
    }

    private void renderOutputTooltip(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 16 && mouseY <= y + 33){
            context.setTooltipForNextFrame(this.minecraft.font, menu.getOutputStack().getItem().getName(menu.getOutputStack()), mouseX, mouseY);
        }
    }

    private void renderHammerTooltip(GuiGraphicsExtractor context, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 34 && mouseY <= y + 51){
            context.setTooltipForNextFrame(this.minecraft.font,
                    Lists.transform(List.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer"),
                            Component.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer_2")),
                            Component::getVisualOrderText), mouseX, mouseY);
        }
    }

    private void renderRecipeBackground(GuiGraphicsExtractor context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for(int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = 0;
            if (i == (this.menu).getSelectedRecipe()) {
                n += 16;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 32;
            }

            context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, k, m - 1, 176 + n, 1, 16, 18, 256,256);
        }
    }

    private void renderRecipeIcons(GuiGraphicsExtractor context, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + 1 + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.item(outputs.get(i), k, m);
        }

    }
}
