package net.sevenstars.middleearth.entity.ai.brain;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.sensor.CaveTrollAttackablesSensor;
import net.sevenstars.middleearth.entity.ai.brain.sensor.NpcAttackablesSensor;

import java.util.function.Supplier;

public class SensorsME {
    public static final SensorType<CaveTrollAttackablesSensor> CAVE_TROLL_ATTACKABLES = register("cave_troll_attackables", CaveTrollAttackablesSensor::new);
    public static final SensorType<NpcAttackablesSensor> NPC_ATTACKABLES = register("npc_attackables", NpcAttackablesSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Sensors for " + MiddleEarth.MOD_ID);
    }
}
