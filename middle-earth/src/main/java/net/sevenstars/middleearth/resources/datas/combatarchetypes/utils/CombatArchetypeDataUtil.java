package net.sevenstars.middleearth.resources.datas.combatarchetypes.utils;

import net.minecraft.nbt.CompoundTag;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

import java.util.Optional;

public class CombatArchetypeDataUtil {
    public static CombatArchetypeData create(CompoundTag combatArchetypeData) {
        Optional<String> type = combatArchetypeData.getString("type");
        if(type.isEmpty())
            return null;
        CombatArchetype combatArchetype = CombatArchetype.valueOf(type.get());

        CompoundTag data = combatArchetypeData.getCompoundOrEmpty("data");
        return switch (combatArchetype) {
            case MELEE -> new MeleeCombatArchetypeData(data);
            case RANGED -> new RangedCombatArchetypeData(data);
            default -> null;
        };
    }
}
