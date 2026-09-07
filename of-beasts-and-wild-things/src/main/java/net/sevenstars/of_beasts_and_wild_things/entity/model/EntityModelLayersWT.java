package net.sevenstars.of_beasts_and_wild_things.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntityModel;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanAdultModel;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBabyModel;

@Environment(value= EnvType.CLIENT)
public class EntityModelLayersWT {
    public static final ModelLayerLocation SNAIL = EntityModelLayersWT.registerEntityModelLayer("snail", SnailEntityModel.getTexturedModelData());
    public static final ModelLayerLocation PHEASANT = EntityModelLayersWT.registerEntityModelLayer("pheasant", PheasantEntityModel.getTexturedModelData());
    public static final ModelLayerLocation SWAN = EntityModelLayersWT.registerEntityModelLayer("swan", SwanAdultModel.getTexturedModelData());
    public static final ModelLayerLocation SWAN_BABY = EntityModelLayersWT.registerEntityModelLayer("swan_baby", SwanBabyModel.getTexturedModelData());
    public static final ModelLayerLocation DEER = EntityModelLayersWT.registerEntityModelLayer("deer", DeerEntityModel.getTexturedModelData());

    private static ModelLayerLocation registerEntityModelLayer(String registryName, LayerDefinition modelData) {
        ModelLayerLocation entityModelLayer = new ModelLayerLocation(OfBeastsAndWildThings.of(registryName), "main");
        ModelLayerRegistry.registerModelLayer(entityModelLayer, () -> modelData);
        return entityModelLayer;
    }
}
