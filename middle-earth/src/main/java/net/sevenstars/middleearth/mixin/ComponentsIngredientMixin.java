package net.sevenstars.middleearth.mixin;

import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.core.component.DataComponentPatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ComponentsIngredient.class)
public interface ComponentsIngredientMixin {

    @Accessor("components")
    DataComponentPatch getComponentChanges();
}
