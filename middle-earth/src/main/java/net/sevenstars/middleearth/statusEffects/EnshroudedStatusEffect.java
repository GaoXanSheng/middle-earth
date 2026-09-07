package net.sevenstars.middleearth.statusEffects;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.utils.IEntityDataSaver;

import java.util.Map;

public class EnshroudedStatusEffect extends MobEffect {
    public EnshroudedStatusEffect(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if(entity instanceof Player){
            Map<Holder<MobEffect>, MobEffectInstance> map = entity.getActiveEffectsMap();
            int ticksLeft = map.get(ModStatusEffects.ENSHROUDED).getDuration();
            if(ticksLeft != -1 && ticksLeft < EnshroudedData.STOPPING_TICK)
                EnshroudedData.addEffect((IEntityDataSaver) entity, -2);
            else{
                EnshroudedData.addEffect((IEntityDataSaver) entity, 2);
            }
        }

        return true;
    }

    public void stop(LivingEntity entity){
        EnshroudedData.stopEffect((IEntityDataSaver) entity);
    }

}
