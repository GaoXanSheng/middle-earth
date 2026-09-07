package net.sevenstars.middleearth.item.items.weapons.artefacts;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;

public class WeaverStingItem extends CustomDaggerWeaponItem {
    public WeaverStingItem(ToolMaterial toolMaterial, Properties settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(MobEffects.POISON, 50));
    }
}
