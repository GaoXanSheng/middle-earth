package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.*;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.CooldownDataComponent;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.world.biomes.BiomeTagsME;
import org.jetbrains.annotations.Nullable;

public class WoodlandRealmCrownItem extends CustomHelmetItem {
    private static final float OFFSET_XZ = 0.5f;
    private static final float OFFSET_Y = -0.3f;
    private SeasonDataComponent.Season season = SeasonDataComponent.Season.DEAD;
    private boolean initialized = false;

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings.humanoidArmor(material.material(), ArmorType.HELMET).stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        Holder<Biome> biomeEntry = world.getBiome(entity.blockPosition());

        int cooldown = 5;
        if(stack.get(DataComponentTypesME.COOLDOWN) != null) {
            cooldown = stack.get(DataComponentTypesME.COOLDOWN).cooldown();
        } else {
            stack.set(DataComponentTypesME.COOLDOWN, new CooldownDataComponent(cooldown));
        }

        if(cooldown <= 0 && biomeEntry.unwrapKey().isPresent()) {
            SeasonDataComponent.Season newSeason = SeasonDataComponent.Season.SUMMER;
            if(biomeEntry.is(BiomeTagsME.SPRING)) {
                newSeason = SeasonDataComponent.Season.SPRING;
            } else if(biomeEntry.is(BiomeTagsME.AUTUMN)) {
                newSeason = SeasonDataComponent.Season.AUTUMN;
            } else if(biomeEntry.is(BiomeTagsME.WINTER)) {
                newSeason = SeasonDataComponent.Season.WINTER;
            } else if(biomeEntry.is(BiomeTagsME.DEAD)) {
                newSeason = SeasonDataComponent.Season.DEAD;
            }

            if(season != newSeason || !initialized) {
                initialized = true;
                LivingEntity livingEntity = (LivingEntity) entity;
                stack.set(DataComponentTypesME.COOLDOWN, new CooldownDataComponent(35));
                double scale = 1.8f * livingEntity.getAttributeValue(Attributes.SCALE);
                Vec3 pos = entity.position().add(0, 1.8f * scale, 0).add(0, -1.1f, 0);

                if(season.equals(SeasonDataComponent.Season.DEAD) && !newSeason.equals(SeasonDataComponent.Season.WINTER)) {
                    world.sendParticles(ParticleTypes.COMPOSTER, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SPRING)) {
                    world.sendParticles(ParticleTypes.CHERRY_LEAVES, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SUMMER)) {
                    int leavesColor = world.getBiome(entity.blockPosition()).value().getFoliageColor();
                    ColorParticleOption tintedParticleEffect = ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, leavesColor);
                    world.sendParticles((ParticleOptions)tintedParticleEffect, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.AUTUMN)) {
                    ColorParticleOption tintedParticleEffect = ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 8930366);
                    world.sendParticles((ParticleOptions)tintedParticleEffect, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.WINTER)) {
                    world.sendParticles(ParticleTypes.SNOWFLAKE, pos.x(), pos.y(), pos.z(), 12, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 0.05f);
                }

                String itemModelName = "woodland_realm_crown";
                if(newSeason != SeasonDataComponent.Season.DEAD) {
                    itemModelName += "_" + newSeason;
                }
                Identifier newItemModel = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, itemModelName.toLowerCase());
                stack.set(DataComponents.ITEM_MODEL, newItemModel);
                stack.set(DataComponentTypesME.SEASON_DATA, new SeasonDataComponent(newSeason));
            }
            season = newSeason;
        }

        stack.set(DataComponentTypesME.COOLDOWN, new CooldownDataComponent(Math.max(0, cooldown - 1)));
    }
}
