package net.sevenstars.middleearth.item.items.weapons;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SneakAttackProperty implements ConditionalItemModelProperty {
    public static final MapCodec<SneakAttackProperty> CODEC = MapCodec.unit(new SneakAttackProperty());

    public MapCodec<SneakAttackProperty> type() {
        return CODEC;
    }

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if(stack.getItem() instanceof CustomDaggerWeaponItem) {
            return CustomDaggerWeaponItem.canSneakAttack(stack);
        }
        return false;
    }
}
