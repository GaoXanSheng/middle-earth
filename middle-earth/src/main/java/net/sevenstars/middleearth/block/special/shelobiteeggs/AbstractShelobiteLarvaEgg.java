package net.sevenstars.middleearth.block.special.shelobiteeggs;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;

import java.util.Random;

public abstract class AbstractShelobiteLarvaEgg extends Block {
    public AbstractShelobiteLarvaEgg(Properties settings) {
        super(settings);
    }

    public static void breakEgg(Level world, BlockPos pos, BlockState state) {
        world.playSound((Player)null, pos, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.getRandom().nextFloat() * 0.2F);
        world.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        world.levelEvent(2001, pos, Block.getId(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    private static void SpawnSpider(BlockPos pos, Level world){
        ShelobiteLarvaEntity entity = new ShelobiteLarvaEntity(EntitiesME.SHELOBITE_LARVA, world);
        entity.tickCount = 0;
        entity.snapTo(pos, 0, 0);
        if(world instanceof ServerLevelAccessor serverWorldAccess) {
            entity.finalizeSpawn(serverWorldAccess, serverWorldAccess.getCurrentDifficultyAt(pos), EntitySpawnReason.NATURAL, null);
        }
        world.addFreshEntity(entity);
    }
}
