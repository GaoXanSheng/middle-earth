package net.sevenstars.middleearth.item.items.shields;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.component.BlocksAttacks;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.WeaponTypeDataComponent;
import net.sevenstars.middleearth.item.utils.ShieldTypesME;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class CustomShieldItem extends ShieldItem {
    public final ShieldTypesME type;
    public final static HashSet<CustomShieldItem> instances = new HashSet<>();

    public CustomShieldItem(ShieldTypesME type, Item.Properties settings) {
        super(settings.stacksTo(1).durability(type.durability).equippableUnswappable(EquipmentSlot.OFFHAND)
                .delayedComponent(DataComponents.BLOCKS_ATTACKS,
                        lookup -> new BlocksAttacks(0.25F, 1.0F, List.of(
                                new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F),
                                Optional.of(lookup.getOrThrow(DamageTypeTags.BYPASSES_SHIELD)),
                                Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                .component(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK)
                .component(DataComponentTypesME.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
        instances.add(this);
    }
}
