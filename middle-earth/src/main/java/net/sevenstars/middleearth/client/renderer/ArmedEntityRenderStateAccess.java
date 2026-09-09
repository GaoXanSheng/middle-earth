package net.sevenstars.middleearth.client.renderer;

import net.minecraft.world.item.ItemStack;

public interface ArmedEntityRenderStateAccess {
    ItemStack getMainHandStack();
    ItemStack getOffHandStack();
    boolean isRestrained();
    boolean isSpearIdle();
    void setMainHandStack(ItemStack mainHandStack);
    void setOffHandStack(ItemStack mainHandStack);
    void setRestrained(boolean restrained);
    void setSpearIdle(boolean spearIdle);
}
