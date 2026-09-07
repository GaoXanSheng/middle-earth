package net.sevenstars.api.entity.ai.brain;

import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.SevenStarsApi;

public class SchedulesAPI {
    public static final EnvironmentAttribute<Activity> DEFAULT_SLEEP = EnvironmentAttributes.VILLAGER_ACTIVITY;
    public static final EnvironmentAttribute<Activity> DEFAULT_BABY = EnvironmentAttributes.BABY_VILLAGER_ACTIVITY;

    public static void registerModSchedules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Schedules for " + SevenStarsApi.MOD_ID);
    }
}
