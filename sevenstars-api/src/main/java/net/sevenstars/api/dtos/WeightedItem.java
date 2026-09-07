package net.sevenstars.api.dtos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public abstract class WeightedItem<T> {
    protected int weight;
    protected T item;

    public WeightedItem(){
        this.item = null;
        this.weight = 1;
    }

    public WeightedItem(T item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public WeightedItem(T item){
        this(item, 1);
    }

    public WeightedItem(Tag element) {
        this.weight = 1;
        if(element.asCompound().isPresent()){
            var potentialWeight = element.asCompound().get().getInt("weight");
            potentialWeight.ifPresent(integer -> this.weight = integer);
        }
    }

    public T getItem() {
        return item;
    }

    public int getWeight(){
        return weight;
    }

    public abstract WeightedItem<T> withWeight(int newWeight);

    public Tag getNbt(){
        if(weight != 1){
            CompoundTag compound = new CompoundTag();
            if(weight != 1)
                compound.putInt("weight", weight);
            return compound;
        }
        return null;
    }

    public boolean isSame(T differentItem) {
        return this.item == differentItem;
    }
}
