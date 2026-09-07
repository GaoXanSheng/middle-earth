package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class CustomAxeWeaponItem extends AxeItem {

    public CustomAxeWeaponItem(ToolMaterial toolMaterial, Item.Properties settings) {
        super(toolMaterial, WeaponTypesME.AXE.attack, WeaponTypesME.AXE.attackSpeed, settings.axe(toolMaterial, WeaponTypesME.AXE.attack, WeaponTypesME.AXE.attackSpeed)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.AXE.name)));
    }

    @Override
    public Component getName(ItemStack stack) {
        if(BuiltInRegistries.ITEM.getKey(this).getPath().contains("_noble")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("_elite")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("uruk_hai")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("heyday")
                || BuiltInRegistries.ITEM.getKey(this).getPath().contains("numenorean")){
            return Component.translatable(this.getDescriptionId()).withStyle(ChatFormatting.GOLD);
        }
        return super.getName(stack);
    }
}
