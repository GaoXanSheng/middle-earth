package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.dataComponents.FactionDataComponent;
import net.sevenstars.middleearth.item.dataComponents.RaceDataComponent;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcType, wrapper);
    }

    public static ItemStack getSpawnEgg(Level world, Identifier identifier) {
        NpcType npcType = world.registryAccess().lookupOrThrow(DynamicRegistriesME.NPC_TYPE).getValue(identifier);
        HolderLookup.Provider wrapperLookup = world.registryAccess();
        return buildItemStackBasedOnNpcData(npcType, wrapperLookup);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;

        Identifier itemId = MiddleEarth.append(npcType.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcType.getId(), false);

        RegistryOps<Tag> ops = RegistryOps.create(
                NbtOps.INSTANCE,
                wrapper
        );

        Tag element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        itemStack.set(DataComponents.ENTITY_DATA, TypedEntityData.of(EntitiesME.NPC, compoundData));
        itemStack.set(DataComponentTypesME.FACTION_DATA, new FactionDataComponent(npcType.getFactionIdentifier()));
        itemStack.set(DataComponentTypesME.RACE_DATA, new RaceDataComponent(npcType.getRace()));
        itemStack.set(DataComponents.ITEM_NAME, Component.translatable(itemId.toLanguageKey("item")));
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(
                List.of(),
                List.of(),
                List.of(npcType.getId().getPath().replaceAll("\\.", "_") + "_spawn_egg"),
                List.of()));

        return itemStack;
    }
}
