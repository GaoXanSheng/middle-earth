package net.sevenstars.middleearth.entity.ai.brain;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.middleearth.MiddleEarth;

public class ActivitiesME {
    public static final Activity TAMED = register("tamed");

    private static Activity register(String id) {
        return Registry.register(BuiltInRegistries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + MiddleEarth.MOD_ID);
    }
}
