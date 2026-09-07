package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(Level world, Identifier typeId){
        if(typeId == null)
            return false;
        RegistryAccess registryManager = world.registryAccess();
        NpcType type =  registryManager.lookupOrThrow(DynamicRegistriesME.NPC_TYPE).getValue(typeId);
        return type != null;
    }
}
