package net.sevenstars.api.dtos;

import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

public class WeightedIdentifier extends WeightedItem<Identifier> {
    public WeightedIdentifier(Tag element){
        super(element);

        if(element.asString().isPresent()){
            this.item = Identifier.parse(element.asString().get());
        } else if(element.asCompound().isPresent()) {
            var potentialId = element.asCompound().get().getString("id");
            potentialId.ifPresent(integer -> this.item = Identifier.parse(potentialId.get()));
        }
    }
    public WeightedIdentifier(Identifier value) {
        super(value);
    }
    public WeightedIdentifier(Identifier value, int i) {
        super(value, i);
    }
    public static WeightedIdentifier fromIdentifier(Identifier id){
        return new WeightedIdentifier(id, 1);
    }
    public static WeightedIdentifier fromKey(ResourceKey key){
        return new WeightedIdentifier(key.identifier(), 1);
    }

    @Override
    public WeightedIdentifier withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public Tag getNbt(){
        Tag newNbt = super.getNbt();
        if(newNbt == null)
            return StringTag.valueOf(item.toString());

        newNbt.asCompound().get().putString("id", this.item.toString());
        return newNbt;
    }
}
