package net.sevenstars.middleearth.item.items;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CustomSpawnEggItem extends Item {
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    private static final Map<EntityType<? extends Mob>, CustomSpawnEggItem> SPAWN_EGGS = Maps.newIdentityHashMap();
    private final EntityType<?> type;
    public CustomSpawnEggItem(EntityType<? extends Mob> type, Properties settings) {
        super(settings);
        this.type = type;
        SPAWN_EGGS.put(type, this);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack itemStack = context.getItemInHand();
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.relative(direction);
        EntityType<?> entityType2 = this.getEntityType(itemStack);
        if (entityType2.spawn((ServerLevel)world, itemStack, context.getPlayer(), blockPos2, EntitySpawnReason.SPAWNER, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
            itemStack.shrink(1);
            world.gameEvent((Entity)context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult blockHitResult = SpawnEggItem.getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResult.PASS;
        }
        if (!(world instanceof ServerLevel)) {
            return InteractionResult.SUCCESS.heldItemTransformedTo(itemStack);
        }
        BlockHitResult blockHitResult2 = blockHitResult;
        BlockPos blockPos = blockHitResult2.getBlockPos();
        if (!(world.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
            return InteractionResult.PASS;
        }
        if (!world.mayInteract(user, blockPos) || !user.mayUseItemAt(blockPos, blockHitResult2.getDirection(), itemStack)) {
            return InteractionResult.FAIL;
        }
        EntityType<?> entityType = this.getEntityType(itemStack);
        Object entity = entityType.spawn((ServerLevel)world, itemStack, user, blockPos, EntitySpawnReason.SPAWNER, false, false);
        if (entity == null) {
            return InteractionResult.PASS;
        }
        if (!user.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        world.gameEvent((Entity)user, GameEvent.ENTITY_PLACE, ((Entity)entity).position());
        return InteractionResult.CONSUME;
    }

    public boolean isOfSameEntityType(ItemStack stack, EntityType<?> type) {
        return Objects.equals(this.getEntityType(stack), type);
    }

    @Nullable
    public static CustomSpawnEggItem forEntity(@Nullable EntityType<?> type) {
        return SPAWN_EGGS.get(type);
    }

    public static Iterable<CustomSpawnEggItem> getAll() {
        return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
    }

    public EntityType<?> getEntityType(ItemStack stack) {
        net.minecraft.world.item.component.TypedEntityData<EntityType<?>> entityData = stack.get(DataComponents.ENTITY_DATA);
        if (entityData != null) {
            return entityData.type();
        }
        return this.type;
    }
}
