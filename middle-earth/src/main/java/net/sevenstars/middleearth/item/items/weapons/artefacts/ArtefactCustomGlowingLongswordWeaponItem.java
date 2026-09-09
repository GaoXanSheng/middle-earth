package net.sevenstars.middleearth.item.items.weapons.artefacts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.items.weapons.CustomLongswordWeaponItem;
import net.sevenstars.middleearth.item.items.weapons.utils.ArtefactUtils;
import org.jetbrains.annotations.Nullable;

public class ArtefactCustomGlowingLongswordWeaponItem extends CustomLongswordWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity_interaction_range");

    public boolean glowing;
    public int counter = 0;

    public ArtefactCustomGlowingLongswordWeaponItem(ToolMaterial toolMaterial, Item.Properties settings) {
        super(toolMaterial,  settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        ArtefactCustomGlowingLongswordWeaponItem item = (ArtefactCustomGlowingLongswordWeaponItem) stack.getItem();
        item.glowing = shouldBeGlowing(world, entity);
    }

    public static boolean shouldBeGlowing(Level world, Entity entity){
        int range = 50;
        if (entity != null){
            return ArtefactUtils.isEvilNpcNearby(world, entity, range);
        }
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        if(stack.getDamageValue() == stack.getMaxDamage() - 1) {
            return false;
        } else if(stack.getDamageValue() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamageValue() == stack.getMaxDamage() - 1){
            stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                    .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                            0.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID,
                            -3.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                            0.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                    .build());
            stack.remove(DataComponents.WEAPON);
        }

    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        Tool toolComponent = (Tool)stack.get(DataComponents.TOOL);
        if (toolComponent == null) {
            return false;
        } else {
            if (!world.isClientSide() && state.getDestroySpeed(world, pos) != 0.0F && toolComponent.damagePerBlock() > 0) {
                if (stack.getDamageValue() == stack.getMaxDamage() - 1){
                    stack.set(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,
                                    0.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID,
                                    -3.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                                    0.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                            .build());
                    stack.remove(DataComponents.WEAPON);
                }
            }
            return true;
        }
    }
}
