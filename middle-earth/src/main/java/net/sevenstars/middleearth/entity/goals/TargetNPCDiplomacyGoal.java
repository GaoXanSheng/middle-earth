package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;

public class TargetNPCDiplomacyGoal extends NearestAttackableTargetGoal<NpcEntity> {
    NpcEntity mob;
    public TargetNPCDiplomacyGoal(NpcEntity mob) {
        super(mob, NpcEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof NpcEntity npcEntity) {
                try {
                    Faction currentFaction = FactionLookup.getFactionById(mob.level(), mob.getFactionIdentifier());
                    if(!currentFaction.isHostileToward(npcEntity.getFactionIdentifier())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canUse();
        }
    }
}
