package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MountData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_TYPE = "npc_type";
        public static final String ARMOR = "armor_id";
        public static final String PASSENGER_SLOTS = "passenger_slots";
    }

    public static final Codec<MountData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(Fields.ENTITY_TYPE).forGetter(MountData::getEntityType),
            Identifier.CODEC.optionalFieldOf(Fields.NPC_TYPE).forGetter(MountData::getOptionalNpcType),
            ItemStack.CODEC.optionalFieldOf(Fields.ARMOR).forGetter(MountData::getOptionalArmor),
            MountPassengerSlotData.CODEC.listOf().fieldOf(Fields.PASSENGER_SLOTS).forGetter(MountData::getPassengerSlots)
    ).apply(instance, MountData::new));

    private Identifier entityType;
    private Identifier npcType;
    private ItemStack armor;
    private List<MountPassengerSlotData> passengerSlots;

    private MountData(
            Identifier entityType,
            Optional<Identifier> npcType,
            Optional<ItemStack> armor,
            List<MountPassengerSlotData> passengerSlots
    ) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.armor = armor.orElse(null);
        this.passengerSlots = passengerSlots;
    }

    public MountData(EntityType<?> entity) {
        this.entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entity);
        this.armor = null;
    }

    public MountData(ResourceKey<NpcType> npcType){
        this.entityType = BuiltInRegistries.ENTITY_TYPE.getKey(EntitiesME.NPC);
        this.npcType = npcType.identifier();
    }

    public MountData withArmor(ItemStack armorItem){
        this.armor = armorItem;
        return this;
    }

    public MountData withArmor(Item armorItem){
        this.armor = armorItem.getDefaultInstance();
        return this;
    }

    public MountData withColor(DyedItemColor color){
        if(this.armor == null)
            return this;
        this.armor.set(DataComponents.DYED_COLOR, color);
        return this;
    }

    private Identifier getEntityType() {
        return entityType;
    }
    private Optional<Identifier> getOptionalNpcType() {
        return Optional.ofNullable(npcType);
    }
    private Optional<ItemStack> getOptionalArmor() {
        return Optional.ofNullable(armor);
    }

    private List<MountPassengerSlotData> getPassengerSlots() {
        if(passengerSlots == null)
            return new ArrayList<>();
        return passengerSlots;
    }

    public MountData withPassengerSlots(MountPassengerSlotData... passengerSlots) {
        this.passengerSlots = Arrays.asList(passengerSlots);
        return this;
    }

    public void createEntity(ServerLevel world, LivingEntity owner) {
        if(this.entityType == null || owner.isPassenger() || owner.isVehicle())
            return;
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(this.entityType);
        var notLiving = type.create(world, EntitySpawnReason.JOCKEY);
        if(notLiving == null)
            return;
        if(notLiving instanceof LivingEntity entity){
            entity.setPos(owner.position());
            entity.setItemSlot(EquipmentSlot.SADDLE, Items.SADDLE.asItem().getDefaultInstance());
            if(armor != null)
                entity.setItemSlot(EquipmentSlot.BODY, this.armor);

            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(
                        world,
                        world.getCurrentDifficultyAt(owner.blockPosition()),
                        EntitySpawnReason.EVENT,
                        null
                );
            }
            if(entity instanceof AbstractHorse horse){
                horse.setTamed(true);
                horse.setOwner(owner);
                if(horse instanceof AbstractBeastEntity beast){
                    beast.tameBeast(owner);
                }

            }

            owner.startRiding(entity, true, false);
            world.addFreshEntity(entity);

            if(entity instanceof NpcEntity npc && npcType != null){
                npc.prepareNpcIdentifier(npcType);
                npc.prepare();
            }

            // Set other passengers
            passengerSlots.forEach(slot -> {
               LivingEntity passengerEntity = slot.createRandom(world, owner);
               if(passengerEntity != null)
                   passengerEntity.startRiding(entity, true, false);
            });

        }
    }
}
