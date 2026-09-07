package net.sevenstars.middleearth.item.utils;

import net.minecraft.world.item.component.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Weapon;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.BlockTagsME;
import net.sevenstars.middleearth.utils.EntityTypeTagsME;

import java.util.List;

public interface ItemSettingsME {

    /**
     * Middle-earth mod custom Settings for weapons
     */

    Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity_interaction_range");

    static Item.Properties createWeaponSettings(ToolMaterial material, Item.Properties settings, WeaponTypesME type){
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);

        return settings.durability(material.durability())
                .repairable(material.repairItems())
                .enchantable(material.enchantmentValue())
                .attributes(createWeaponAttibutes(material, type.attack, type.attackSpeed, type.attackRange))
                .component(DataComponents.TOOL, new Tool(List.of(
                        Tool.Rule.minesAndDrops(registryEntryLookup.getOrThrow(BlockTagsME.COBWEBS), 15.0F),
                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE),
                        Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false))
                .component(DataComponents.WEAPON, new Weapon(1));
    }

    static ItemAttributeModifiers createWeaponAttibutes(ToolMaterial material, float attackDamage, float attackSpeed, float attackRange){
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(attackDamage + material.attackDamageBonus()), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID, (double)attackRange, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    static Item.Properties goatArmor(ArmorMaterial material) {
        HolderGetter<EntityType<?>> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE);
        return new Item.Properties().attributes(material.createAttributes(ArmorType.BODY))
                .component(
                        DataComponents.EQUIPPABLE,
                        Equippable.builder(EquipmentSlot.BODY)
                                .setEquipSound(SoundEvents.HORSE_ARMOR)
                                .setAsset(material.assetId())
                                .setAllowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_GOAT_ARMOR))
                                .setDamageOnHurt(false)
                                .setCanBeSheared(true)
                                .setShearingSound(SoundEvents.HORSE_ARMOR_UNEQUIP)
                                .build()
                )
                .stacksTo(1);
    }

    static Item.Properties wargArmor(ArmorMaterial material) {
        HolderGetter<EntityType<?>> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE);
        return new Item.Properties().attributes(material.createAttributes(ArmorType.BODY))
                .component(
                        DataComponents.EQUIPPABLE,
                        Equippable.builder(EquipmentSlot.BODY)
                                .setEquipSound(SoundEvents.HORSE_ARMOR)
                                .setAsset(material.assetId())
                                .setAllowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_WARG_ARMOR))
                                .setDamageOnHurt(false)
                                .setCanBeSheared(true)
                                .setShearingSound(SoundEvents.HORSE_ARMOR_UNEQUIP)
                                .build()
                )
                .stacksTo(1);
    }

    static Item.Properties greatHornArmor(ArmorMaterial material) {
        HolderGetter<EntityType<?>> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ENTITY_TYPE);
        return new Item.Properties().attributes(material.createAttributes(ArmorType.BODY))
                .component(
                        DataComponents.EQUIPPABLE,
                        Equippable.builder(EquipmentSlot.BODY)
                                .setEquipSound(SoundEvents.HORSE_ARMOR)
                                .setAsset(material.assetId())
                                .setAllowedEntities(registryEntryLookup.getOrThrow(EntityTypeTagsME.CAN_WEAR_GREAT_HORN_ARMOR))
                                .setDamageOnHurt(false)
                                .build()
                )
                .stacksTo(1);
    }
}
