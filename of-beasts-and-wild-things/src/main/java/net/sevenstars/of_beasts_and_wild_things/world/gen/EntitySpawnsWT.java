package net.sevenstars.of_beasts_and_wild_things.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.utils.BiomeTagsWT;

public class EntitySpawnsWT {
    public static void addSpawns() {
        // Snail
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTagsWT.SNAIL_SPAWNS),
                MobCategory.CREATURE,
                EntitiesWT.SNAIL,
                40, 2, 5);
        SpawnPlacements.register(EntitiesWT.SNAIL, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

        // Pheasant
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTagsWT.PHEASANT_SPAWNS),
                MobCategory.CREATURE,
                EntitiesWT.PHEASANT,
                50, 1, 3);
        SpawnPlacements.register(EntitiesWT.PHEASANT, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

        // DEER
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTagsWT.DEER_SPAWNS),
                MobCategory.CREATURE,
                EntitiesWT.DEER,
                50, 2, 6);
        SpawnPlacements.register(EntitiesWT.DEER, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
    }
}
