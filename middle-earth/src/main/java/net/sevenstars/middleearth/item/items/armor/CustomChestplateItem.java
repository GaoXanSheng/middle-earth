package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import org.jetbrains.annotations.Nullable;

public class CustomChestplateItem extends ArmorItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings.humanoidArmor(material.material(), ArmorType.CHESTPLATE).stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        BackAttachmentDataComponent backAttachmentDataComponent = stack.get(DataComponentTypesME.BACK_ATTACHMENT_DATA);
        if(backAttachmentDataComponent != null) {
            int id = backAttachmentDataComponent.getBackAttachment().getId();
            if(id == BackAttachmentsME.MANTLE_OF_YAVANNA.getId()) {
                MantleOfYavannaItem.applyBackAttachmentColor(stack, world, entity, backAttachmentDataComponent);
            }
        }
    }
}
