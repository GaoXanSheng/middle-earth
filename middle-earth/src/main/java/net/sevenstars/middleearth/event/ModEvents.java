package net.sevenstars.middleearth.event;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.shapingAnvil.AbstractShapingAnvilBlock;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.Objects;

public class ModEvents {
    private static final String GOT_STARTER_ITEM = MiddleEarth.MOD_ID + ".received_starter_item";

    public static void register(){
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            ServerPlayer player = serverPlayNetworkHandler.getPlayer();
            MiddleEarthHeightMap.setSeed(player.level().getSeed());

            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                return;
            if(data != null && data.getRace() != null){
                RaceUtil.reset(player);
                boolean isInMiddleEarth = ModDimensions.isInMiddleEarth(player.level());
                if(isInMiddleEarth){
                    RaceUtil.initializeRace(player);
                } else if(ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP){
                    RaceUtil.initializeRace(player);
                }
            }

            if(!player.entityTags().contains(GOT_STARTER_ITEM)) {
                ItemStack starterItem = new ItemStack(ResourceItemsME.PLAYER_BOOK);
                player.getInventory().placeItemBackInInventory(starterItem);
                player.addTag(GOT_STARTER_ITEM);
            }
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((world, entity, killedEntity, damageSource) -> {
            if(entity instanceof Player playerEntity) {
                ItemStack stack = Objects.requireNonNull(playerEntity.getItemInHand(playerEntity.getUsedItemHand()));
                Holder<Enchantment> enchantmentRegistryEntry = world.registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.BEHEADING).orElseThrow();
                boolean hasEnchant = stack.getEnchantments().keySet().contains(enchantmentRegistryEntry);

                if (hasEnchant) {
                    ItemStack drop = ItemStack.EMPTY;
                    if(killedEntity instanceof Player killedPlayer) {
                        drop = new ItemStack(Items.PLAYER_HEAD);
                        drop.set(DataComponents.PROFILE, ResolvableProfile.createResolved(killedPlayer.getGameProfile()));
                    }
                    if(!drop.isEmpty()) {
                        killedEntity.spawnAtLocation(world, drop);
                    }
                }
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getItemInHand(playerEntity.getUsedItemHand()));
            Tool toolComponent = stack.get(DataComponents.TOOL);
            Holder<Enchantment> enchantmentRegistryEntry = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.HEWING).orElseThrow();
            boolean hasEnchant = stack.getEnchantments().keySet().contains(enchantmentRegistryEntry);
            int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistryEntry, stack);
            float hardness = blockState.getBlock().defaultDestroyTime();

            if (hasEnchant) {
                if (!playerEntity.isCreative() && !playerEntity.isShiftKeyDown()) {
                    assert toolComponent != null;
                    if (toolComponent.isCorrectForDrops(blockState)){
                        if (playerEntity.getNearestViewDirection() == Direction.DOWN || playerEntity.getNearestViewDirection() == Direction.UP){
                            if (level == 1){
                                level1BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3BreakVertical(world, playerEntity, blockPos, stack, hardness);
                            }
                        } else {
                            if (level == 1){
                                level1Break(world, playerEntity, blockPos, stack, hardness);
                            } else if (level == 2){
                                level2Break(world, playerEntity, blockPos, stack, hardness);
                            } else {
                                level3Break(world, playerEntity, blockPos, stack, hardness);
                            }
                        }
                    }
                }
            }
        });

        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            ItemStack stack = Objects.requireNonNull(playerEntity.getItemInHand(playerEntity.getUsedItemHand()));
            Tool toolComponent = stack.get(DataComponents.TOOL);
            Holder<Enchantment> enchantmentRegistryEntry = world.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentsME.TREE_FELLER).orElseThrow();
            boolean hasEnchant = stack.getEnchantments().keySet().contains(enchantmentRegistryEntry);
            int level = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistryEntry, stack);
            float hardness = blockState.getBlock().defaultDestroyTime();

            if (hasEnchant) {
                if (!playerEntity.isCreative() && !playerEntity.isShiftKeyDown()) {
                    assert toolComponent != null;
                    if (toolComponent.isCorrectForDrops(blockState)){
                        int[] blockCount = new int[]{16};
                        if(level == 2) blockCount[0] = 64;
                        else if(level == 3) blockCount[0] = 256;
                        breakTopLogs(world, playerEntity, blockPos, stack, hardness, blockCount);
                    }
                }
            }
        });

        // Vanilla skips Block#attack for creative players, so hammer bonks on shaping anvils
        // need to be routed through this callback, which fires before that skip.
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (player.getAbilities().instabuild
                    && world.getBlockState(pos).getBlock() instanceof AbstractShapingAnvilBlock) {
                return AbstractShapingAnvilBlock.tryBonk(world, pos, player);
            }
            return InteractionResult.PASS;
        });
    }

    private static void breakTopLogs(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness, int[] attempts) {
        BlockPos offsetY = blockPos.relative(Direction.Axis.Y, 1);
        for(int z = -2; z < 2; z++) {
            BlockPos offsetZ = offsetY.relative(Direction.Axis.Z, z);
            for(int x = -2; x < 2; x++) {
                BlockPos offset = offsetZ.relative(Direction.Axis.X, x);
                if(world.getBlockState(offset).is(BlockTags.LOGS)) {
                    if(attempts[0]-- <= 0) return;
                    breakTopLogs(world, player, new BlockPos(offset), stack, hardness, attempts);
                }
            }
        }
        breakAndDamage(world, player, blockPos, stack, hardness);
    }

    private static void level1BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        float yaw = player.getYRot();
        if((yaw >= -45 && yaw <= 45) || yaw <= -135 || yaw >= 135){
            breakAndDamage(world, player, blockPos.relative(Direction.NORTH), stack, hardness);
            breakAndDamage(world, player, blockPos.relative(Direction.SOUTH), stack, hardness);
        } else {
            breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
            breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);
        }
    }

    private static void level2BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        breakAndDamage(world, player, blockPos.relative(Direction.NORTH), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.SOUTH), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
    }

    private static void level3BreakVertical(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosNorth = blockPos.relative(Direction.NORTH);
        BlockPos blockPosSouth = blockPos.relative(Direction.SOUTH);

        breakAndDamage(world, player, blockPosNorth, stack, hardness);
        breakAndDamage(world, player, blockPosNorth.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosNorth.relative(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(Direction.EAST), stack, hardness);

        breakAndDamage(world, player, blockPosSouth, stack, hardness);
        breakAndDamage(world, player, blockPosSouth.relative(Direction.EAST), stack, hardness);
        breakAndDamage(world, player, blockPosSouth.relative(Direction.WEST), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(Direction.WEST), stack, hardness);
    }

    private static void level1Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level2Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);

        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
    }

    private static void level3Break(Level world, Player player, BlockPos blockPos, ItemStack stack, float hardness){
        BlockPos blockPosUp = blockPos.above();
        BlockPos blockPosDown = blockPos.below();

        breakAndDamage(world, player, blockPosUp, stack, hardness);
        breakAndDamage(world, player, blockPosUp.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPosUp.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPos.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);

        breakAndDamage(world, player, blockPosDown, stack, hardness);
        breakAndDamage(world, player, blockPosDown.relative(player.getNearestViewDirection().getClockWise()), stack, hardness);
        breakAndDamage(world, player, blockPosDown.relative(player.getNearestViewDirection().getCounterClockWise()), stack, hardness);
    }

    private static void breakAndDamage(Level world, Player player, BlockPos blockpos, ItemStack stack, float hardness){
        Tool toolComponent = stack.get(DataComponents.TOOL);
        BlockState blockState = world.getBlockState(blockpos);

        if (!blockState.isAir() && toolComponent != null) {
            if (toolComponent.isCorrectForDrops(blockState)) {
                if (blockState.getBlock().defaultDestroyTime() <= hardness) {
                    BlockEntity blockEntity = blockState.hasBlockEntity()
                            ? world.getBlockEntity(blockpos)
                            : null;

                    Block.dropResources(blockState, world, blockpos, blockEntity, player, stack);
                    world.destroyBlock(blockpos, false, player);
                    stack.mineBlock(world, blockState, blockpos, player);
                }
            }
        }
    }
}
