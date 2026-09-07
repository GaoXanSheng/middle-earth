package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.Optional;

public class StructureManagerDataLookup {
    public static Optional<StructureManagerData> getStructureManagerData(Level world, Identifier id) {
        return world.registryAccess().lookupOrThrow(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).getOptional(id);
    }
}
