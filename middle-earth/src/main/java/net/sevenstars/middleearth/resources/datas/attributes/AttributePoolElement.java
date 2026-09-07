package net.sevenstars.middleearth.resources.datas.attributes;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.*;

public class AttributePoolElement {
    private Identifier identifier;
    private Double value;
    private Double valueMax;
    private List<AttributeModifierElement> modifiers;

    public static AttributePoolElement create(Holder<Attribute> attributeEntry, double defineValue){
        return new AttributePoolElement()
                .withIdentifier(Identifier.parse(attributeEntry.getRegisteredName()))
                .withDefineValue(defineValue);
    }
    public static AttributePoolElement create(Holder<Attribute> attributeEntry, double min, double max){
        return new AttributePoolElement()
                .withIdentifier(Identifier.parse(attributeEntry.getRegisteredName()))
                .withMinMaxValue(min, max);
    }

    public static AttributePoolElement createFromNbt(CompoundTag nbtCompound){
        var newElement = new AttributePoolElement();
        newElement.withIdentifier(Identifier.parse(nbtCompound.getString("id").get()));

        if(nbtCompound.contains("value"))
            newElement.withDefineValue(nbtCompound.getDouble("value").get());
        else if(nbtCompound.contains("min") && nbtCompound.contains("max"))
            newElement.withMinMaxValue(nbtCompound.getDouble("min").get(), nbtCompound.getDouble("max").get());

        if(nbtCompound.contains("modifiers")){
            newElement.withModifiers(nbtCompound.getList("modifiers").get());
        }

        return newElement;
    }

    private void withModifiers(ListTag modifierList) {
        this.modifiers = new ArrayList<>();
        modifierList.forEach(modifierNbt -> {
            modifiers.add(new AttributeModifierElement(modifierNbt.asCompound().get()));
        });
    }

    public CompoundTag createNbt(){
        var nbtCompound = new CompoundTag();

        nbtCompound.putString("id", this.identifier.toString());
        if(this.valueMax == null)
            nbtCompound.putDouble("value", this.value);
        else{
            nbtCompound.putDouble("min", this.value);
            nbtCompound.putDouble("max", this.valueMax);
        }

        if(this.modifiers != null && !this.modifiers.isEmpty()){
            ListTag modifiersList = new ListTag();
            for(AttributeModifierElement modifier : this.modifiers){
                modifiersList.add(modifier.toNbt());
            }
            nbtCompound.put("modifiers", modifiersList);
        }
        return nbtCompound;
    }

    public AttributePoolElement withIdentifier(Identifier newIdentifier) {
        this.identifier = newIdentifier;
        return this;
    }

    public AttributePoolElement withDefineValue(Double newDefineValue) {
        this.value = newDefineValue;
        return this;
    }

    public AttributePoolElement withMinMaxValue(Double newMinValue, Double newMaxValue) {
        this.value = newMinValue;
        this.valueMax = newMaxValue;
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value) {
        withModifier(identifier, value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return this;
    }

    public AttributePoolElement withModifier(Identifier identifier, double value, AttributeModifier.Operation operation) {
        if(modifiers == null)
            modifiers = new ArrayList<>();

        modifiers.add(new AttributeModifierElement(identifier, value, operation));
        return this;
    }

    public Identifier getIdentifier(){
        return this.identifier;
    }

    public double getValue(){
        if(this.valueMax == null){
            return this.value;
        }
        Random random = new Random();
        return random.nextDouble(value, valueMax);
    }

    public boolean hasModifiers(){
        return this.modifiers != null && !this.modifiers.isEmpty();
    }

    public List<AttributeModifierElement> getModifiers() {
        if(modifiers == null)
            return new ArrayList<>();
        return modifiers;
    }

    public static CompoundTag createAttributeNbtListFromPlayer(Player player) {
        ListTag attributeList = new ListTag();
        var registry = player.level().registryAccess().lookup(Registries.ATTRIBUTE).get();
        Collection<AttributeInstance> attributes = new ArrayList<>();
        var entries = registry.asHolderIdMap();
        for(var entry : entries){
            AttributeInstance instance = player.getAttribute(entry);
            if(instance == null)
                continue;
            attributes.add(instance);
        }
        attributes.forEach(attribute -> {
            AttributePoolElement attributePoolElement = new AttributePoolElement();
            attributePoolElement.withIdentifier(MiddleEarth.fetchId(attribute.getAttribute().getRegisteredName()));
            attributePoolElement.withDefineValue(attribute.getBaseValue());
            for(var modifier : attribute.getModifiers())
                attributePoolElement.withModifier(modifier.id(), modifier.amount(), modifier.operation());
            attributeList.add(attributePoolElement.createNbt());
        });
        CompoundTag compound = new CompoundTag();
        compound.put("attributes", attributeList);
        return compound;
    }

    public static List<AttributePoolElement> obtainAttributeList(CompoundTag nbtCompound) {
        List<AttributePoolElement> attributePoolElementList = new ArrayList<>();

        ListTag nbtList = nbtCompound.getListOrEmpty("attributes");

        nbtList.forEach(attribute -> {
            attributePoolElementList.add(AttributePoolElement.createFromNbt(attribute.asCompound().get()));
        });
        return attributePoolElementList;
    }
}
