package net.sevenstars.middleearth.resources.datas.races;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceLookup {
    public static List<Race> getAllRaces(Level world) {
        return world.registryAccess().lookupOrThrow(DynamicRegistriesME.RACE).stream().toList();
    }

    public static List<Race> getAllRaces(Level world, List<Identifier> ids) {
        Registry<Race> registry = world.registryAccess().lookupOrThrow(DynamicRegistriesME.RACE);
        List<Race> list = new ArrayList<>();
        for(Identifier id : ids){
            Race race = registry.getValue(id);
            if(!list.contains(race))
                list.add(race);
        }
        return list;
    }

    public static Race getRace(Level world, Identifier identifier) {
        Optional<Race> race = world.registryAccess().lookupOrThrow(DynamicRegistriesME.RACE).getOptional(identifier);
        return race.orElse(null);
    }
}
