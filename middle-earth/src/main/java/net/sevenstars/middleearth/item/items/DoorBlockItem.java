package net.sevenstars.middleearth.item.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import java.util.List;
import java.util.function.Consumer;

public class DoorBlockItem extends BlockItem {
    private final LargeDoorBlock block;

    public DoorBlockItem(LargeDoorBlock block, Properties settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".door_size").append(block.getDoorWidth() + "x" + block.getDoorHeight()));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
