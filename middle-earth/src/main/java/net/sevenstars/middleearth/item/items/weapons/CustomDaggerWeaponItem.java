package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.SneakAttackDataComponent;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity_interaction_range");
    private static final int SNEAK_ATTACK_TIME = 40;

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, Item.Properties settings) {
        super(toolMaterial, WeaponTypesME.DAGGER, settings);
        this.type = WeaponTypesME.DAGGER;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        if(entity instanceof Player playerEntity) {
            int timer = 0;
            SneakAttackDataComponent sneakAttackDataComponent = stack.get(DataComponentTypesME.SNEAK_ATTACK_DATA);
            if(sneakAttackDataComponent != null) timer = sneakAttackDataComponent.timer();

            if(playerEntity.isShiftKeyDown()) {
                stack.set(DataComponentTypesME.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(Math.min(timer + 1, SNEAK_ATTACK_TIME)));
            } else {
                stack.set(DataComponentTypesME.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(0));
            }
        }
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        SneakAttackDataComponent sneakAttackDataComponent = stack.get(DataComponentTypesME.SNEAK_ATTACK_DATA);
        if(sneakAttackDataComponent != null) stack.set(DataComponentTypesME.SNEAK_ATTACK_DATA, new SneakAttackDataComponent(0));
    }

    public static boolean canSneakAttack(ItemStack stack) {
        SneakAttackDataComponent sneakAttackDataComponent = stack.get(DataComponentTypesME.SNEAK_ATTACK_DATA);
        if(sneakAttackDataComponent != null) {
            int timer = sneakAttackDataComponent.timer();
            return (timer >= SNEAK_ATTACK_TIME);
        } else return false;
    }

    public static boolean canBackStab(Entity target, Entity attacker) {
        Vector2f direction = new Vector2f((float) target.getLookAngle().x, (float) target.getLookAngle().z).normalize();
        Vector3f attackerPosDifference = target.position().add(attacker.position().scale(-1)).toVector3f();
        Vector2f attackerDirection = new Vector2f(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.1f;
    }
}
