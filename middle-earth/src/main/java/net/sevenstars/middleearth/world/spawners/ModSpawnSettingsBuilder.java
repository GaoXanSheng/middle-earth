package net.sevenstars.middleearth.world.spawners;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.entity.EntityTypes;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

public class ModSpawnSettingsBuilder {
    public static void addRiverAnimals(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.WATER_AMBIENT,12,  new MobSpawnSettings.SpawnerData(EntityTypes.SALMON, 1, 5));
    }
    public static void addOceanAnimals(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.WATER_AMBIENT, 10, new MobSpawnSettings.SpawnerData(EntityTypes.COD, 1, 5));
        builder.addSpawn(MobCategory.WATER_CREATURE,6, new MobSpawnSettings.SpawnerData(EntityTypes.SQUID, 1, 4));
        builder.addSpawn(MobCategory.WATER_CREATURE,4,  new MobSpawnSettings.SpawnerData(EntityTypes.TURTLE, 1, 2));
        builder.addSpawn(MobCategory.WATER_CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityTypes.DOLPHIN, 1, 2));
    }

    public static void addColdWaterAnimals(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.WATER_AMBIENT,5, new MobSpawnSettings.SpawnerData(EntityTypes.COD,  1, 5));
    }

    public static void addFarmAnimals(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 12, new MobSpawnSettings.SpawnerData(EntityTypes.SHEEP, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityTypes.PIG, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntityTypes.CHICKEN, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityTypes.COW, 4, 4));
    }

    public static void addRareWarg(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.CREATURE, 3,  new MobSpawnSettings.SpawnerData(EntitiesME.WARG, 1, 3));
    }
    public static void addUncommonWarg(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.CREATURE, 6,new MobSpawnSettings.SpawnerData(EntitiesME.WARG,  1, 3));
    }

    public static void addRareStoneTroll(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.MONSTER,3,  new MobSpawnSettings.SpawnerData(EntitiesME.STONE_TROLL, 1, 2));
    }
    public static void addRareCaveTroll(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.MONSTER,3,  new MobSpawnSettings.SpawnerData(EntitiesME.CAVE_TROLL, 1, 1));
    }
    public static void addMirkwoodSpider(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.CREATURE, 9, new MobSpawnSettings.SpawnerData(EntitiesME.SHELOBITE_SCUTTLER, 2, 4));
    }
    public static void addRareMirkwoodSpider(MobSpawnSettings.Builder builder){
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntitiesME.SHELOBITE_SCUTTLER, 1, 2));
    }

    public static void addUncommonBats(MobSpawnSettings.Builder builder){
    builder.addSpawn(MobCategory.CREATURE, 8, new MobSpawnSettings.SpawnerData(EntityTypes.BAT, 2, 4));
    }

    public static void addPlainsMobs(MobSpawnSettings.Builder builder) {
        addFarmAnimals(builder);
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityTypes.HORSE, 2, 6));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityTypes.DONKEY, 1, 3));
    }

    public static void addRabbits(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,3,  new MobSpawnSettings.SpawnerData(EntityTypes.RABBIT, 1, 5));
    }

    public static void addCats(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntityTypes.CAT, 1, 3));
    }
    public static void addWolves(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityTypes.WOLF, 1, 3));
    }
    public static void addCommonWolves(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,8,  new MobSpawnSettings.SpawnerData(EntityTypes.WOLF, 1, 3));
    }
    public static void addRareWolves(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityTypes.WOLF, 1, 2));
    }

    public static void addMountainsMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntityTypes.GOAT, 1, 3));
    }
    public static void addBroadhoofGoats(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 4, new MobSpawnSettings.SpawnerData(EntitiesME.BROADHOOF_GOAT, 1, 3));
    }

    public static void addNordicMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,4, new MobSpawnSettings.SpawnerData(EntityTypes.RABBIT, 2, 4));
        builder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityTypes.FOX,  1, 3));
    }

    public static void addForochelMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityTypes.FOX, 1, 3));
        builder.addSpawn(MobCategory.CREATURE, 1, new MobSpawnSettings.SpawnerData(EntityTypes.POLAR_BEAR, 1, 2));
    }

    public static void addSwampMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,8, new MobSpawnSettings.SpawnerData(EntityTypes.FROG,  1, 4));
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntitiesWT.SNAIL, 1, 4));
    }

    public static void addRareSnails(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 2,new MobSpawnSettings.SpawnerData(EntitiesWT.SNAIL,  1, 3));
    }

    public static void addEriadorMobs(MobSpawnSettings.Builder builder) {
        addPlainsMobs(builder);
        addPheasant(builder);
    }

    public static void addArmadillo(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 3, new MobSpawnSettings.SpawnerData(EntityTypes.ARMADILLO, 1, 3));
    }
    public static void addHaradMobs(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,3,  new MobSpawnSettings.SpawnerData(EntityTypes.RABBIT, 1, 4));
    }

    public static void addCamel(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE,1, new MobSpawnSettings.SpawnerData(EntityTypes.CAMEL, 1, 2));
    }

    public static void addLlama(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 2, new MobSpawnSettings.SpawnerData(EntityTypes.LLAMA, 1, 3));
    }

    public static void addPheasant(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 6,new MobSpawnSettings.SpawnerData(EntitiesWT.PHEASANT,  1, 2));
    }

    public static void addSwan(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 7, new MobSpawnSettings.SpawnerData(EntitiesWT.SWAN, 1, 3));
    }

    public static void addDeer(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 10, new MobSpawnSettings.SpawnerData(EntitiesWT.DEER, 1, 4));
    }

    public static void addNpcs(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.addSpawn(MobCategory.MONSTER, 3, new MobSpawnSettings.SpawnerData(EntitiesME.NPC, 1, 1));
    }

    public static void addGreatHorn(MobSpawnSettings.Builder builder) {
        builder.addSpawn(MobCategory.CREATURE, 5, new MobSpawnSettings.SpawnerData(EntitiesME.GREAT_HORN, 1, 2));
    }
}
