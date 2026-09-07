package net.sevenstars.middleearth.entity.beasts.trolls.petrified;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.item.items.CustomSpawnEggItem;
import org.jetbrains.annotations.Nullable;

public class PetrifiedTrollEntity extends Mob {

    public PetrifiedTrollEntity(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
        this.setNoAi(true);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 30.0);
    }

    @Override
    public void tick() {
        super.tick();
        this.setYBodyRot(this.getYRot());
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (this.level().isClientSide()) return false;
        Entity entity = source.getEntity();
        if(entity instanceof Player playerEntity) {
            if(playerEntity.isCreative()) {
                super.setHealth(0);
                return true;
            }
            ItemStack itemStack = playerEntity.getMainHandItem();
            if(itemStack.getComponents().has(DataComponents.TOOL) && !this.level().isClientSide()) {
                TagKey tagKey = TagKey.create(Registries.ITEM, Identifier.parse("pickaxes"));
                if(itemStack.is(tagKey)) {
                    return super.hurtServer(world, source, 10.0f);
                }
            }
        }
        return false;
    }

    @Override
    public void handleDamageEvent(DamageSource damageSource) {
    }

    @Override
    @Nullable
    public ItemStack getPickResult() {
        CustomSpawnEggItem statue = CustomSpawnEggItem.forEntity(this.getType());
        if (statue == null) {
            return null;
        }
        return new ItemStack(statue);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.STONE_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }
}
