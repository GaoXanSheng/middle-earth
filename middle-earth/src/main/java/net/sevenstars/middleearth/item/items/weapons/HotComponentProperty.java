package net.sevenstars.middleearth.item.items.weapons;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.TemperatureDataComponent;
import org.jetbrains.annotations.Nullable;

public class HotComponentProperty implements ConditionalItemModelProperty {
    public static final MapCodec<HotComponentProperty> CODEC = MapCodec.unit(new HotComponentProperty());

    public MapCodec<HotComponentProperty> type() {
        return CODEC;
    }

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        TemperatureDataComponent temperatureDataComponent = stack.get(DataComponentTypesME.TEMPERATURE_DATA);
        return temperatureDataComponent != null && temperatureDataComponent.temperature() > 0;
    }
}
