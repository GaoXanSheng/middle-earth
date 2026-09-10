package net.sevenstars.middleearth.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.sevenstars.middleearth.utils.DimensionTypeTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public class SpawnHelperMixin {

    @Inject(method = "isValidSpawnPostitionForType", at = @At("HEAD"), cancellable = true)
    private static void canSpawnMixin(ServerLevel world, MobCategory group, StructureManager structureAccessor, ChunkGenerator chunkGenerator, MobSpawnSettings.SpawnerData spawnEntry, BlockPos.MutableBlockPos pos, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        EntityType<?> type = spawnEntry.type();
        // NPC spawn rules apply to every dimension listed in #middle-earth:npc_spawn_rules.
        if (world.dimensionTypeRegistration().is(DimensionTypeTagsME.NPC_SPAWN_RULES) && !SpawnUtil.canCreatureSpawn(type, world, EntitySpawnReason.NATURAL, pos, world.getRandom())) {
            cir.setReturnValue(false);
        }
    }
}
