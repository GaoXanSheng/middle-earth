package net.sevenstars.middleearth.resources.datas.npc_types;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;

public class NpcTypeLookup {
    public static List<NpcType> getAllNpcTypes(Level world, List<Identifier> ids) {
        Registry<NpcType> registry = world.registryAccess().lookupOrThrow(DynamicRegistriesME.NPC_TYPE);
        List<NpcType> list = new ArrayList<>();
        for(Identifier id : ids){
            list.add(registry.getValue(id));
        }
        return list;
    }

    public static List<NpcType> getAllNpcTypesFromRace(Level world, List<Identifier> ids, Identifier race){
        List<NpcType> unsortedList = getAllNpcTypes(world, ids);
        List<NpcType> list = new ArrayList<>();
        for(NpcType npcType : unsortedList){
            if(npcType.getRace().equals(race))
                list.add(npcType);
        }
        return list;
    }
    public static NpcType getNpcType(Level world, Identifier id) {
        return world.registryAccess().lookupOrThrow(DynamicRegistriesME.NPC_TYPE).getValue(id);
    }
}
