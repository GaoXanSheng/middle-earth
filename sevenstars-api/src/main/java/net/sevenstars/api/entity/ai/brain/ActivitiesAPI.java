package net.sevenstars.api.entity.ai.brain;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.SevenStarsApi;

public class ActivitiesAPI {
    public static final Activity BABY_IDLE = register("baby_idle");
    public static final Activity BABY_REST = register("baby_rest");

    private static Activity register(String id) {
        return Registry.register(BuiltInRegistries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + SevenStarsApi.MOD_ID);
    }
}
