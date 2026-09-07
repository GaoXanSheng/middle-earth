package net.sevenstars.middleearth.item.utils.armor;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.ItemTagsME;

import java.util.EnumMap;
import java.util.function.IntFunction;

public interface ArmorMaterialsME {
    ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));

    ExtendedArmorMaterial STRAW_T1 = registerArmor("straw_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_STRAW_ARMOR);
    ExtendedArmorMaterial WOOD_T1 = registerArmor("wood_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.PLANKS);
    ExtendedArmorMaterial WOOL_T1 = registerArmor("wool_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_WOOL_ARMOR);

    ExtendedArmorMaterial FUR_T0 = registerArmor("fur_t0", Tiers.CLOTHING, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FUR_ARMOR);

    ExtendedArmorMaterial FABRIC_T0 = registerArmor("fabric_t0", Tiers.CLOTHING, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FABRIC_ARMOR);
    ExtendedArmorMaterial FABRIC_T1 = registerArmor("fabric_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FABRIC_ARMOR);

    ExtendedArmorMaterial LEATHER_T1 = registerArmor("leather_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial LEATHER_T2 = registerArmor("leather_t2", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);

    ExtendedArmorMaterial BONE_T0 = registerArmor("bone_t0", Tiers.CLOTHING, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_BONE_ARMOR);
    ExtendedArmorMaterial BONE_T1 = registerArmor("bone_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_BONE_ARMOR);
    ExtendedArmorMaterial BONE_T2 = registerArmor("bone_t2", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_BONE_ARMOR);

    ExtendedArmorMaterial IRON_T2 = registerArmor("iron_t2", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);
    ExtendedArmorMaterial IRON_T3 = registerArmor("iron_t3", Tiers.MEDIUM, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);

    ExtendedArmorMaterial BRONZE_T1 = registerArmor("bronze_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_BRONZE_ARMOR);
    ExtendedArmorMaterial BRONZE_T2 = registerArmor("bronze_t2", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_BRONZE_ARMOR);

    ExtendedArmorMaterial CRUDE_T1 = registerArmor("crude_t1", Tiers.BASIC, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_CRUDE_ARMOR);
    ExtendedArmorMaterial CRUDE_T2 = registerArmor("crude_t2", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_CRUDE_ARMOR);
    ExtendedArmorMaterial CRUDE_T3 = registerArmor("crude_t3", Tiers.MEDIUM, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_CRUDE_ARMOR);

    ExtendedArmorMaterial STEEL_T4 = registerArmor("straw_t4", Tiers.STURDY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial STEEL_T5 = registerArmor("straw_t5", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemTagsME.REPAIRS_STEEL_ARMOR);

    ExtendedArmorMaterial DWARVEN_STEEL_T4 = registerArmor("dwarven_steel_t4", Tiers.STURDY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_KHAZAD_STEEL_ARMOR);
    ExtendedArmorMaterial DWARVEN_STEEL_T5 = registerArmor("dwarven_steel_t5", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemTagsME.REPAIRS_KHAZAD_STEEL_ARMOR);

    ExtendedArmorMaterial ELVEN_STEEL_T4 = registerArmor("elven_steel_t4", Tiers.STURDY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);
    ExtendedArmorMaterial ELVEN_STEEL_T5 = registerArmor("elven_steel_t5", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);

    ExtendedArmorMaterial BURZUM_STEEL_T3 = registerArmor("burzum_steel_t3", Tiers.MEDIUM, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial BURZUM_STEEL_T4 = registerArmor("burzum_steel_t4", Tiers.STURDY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial BURZUM_STEEL_T5 = registerArmor("burzum_steel_t5", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_NETHERITE, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);

    ExtendedArmorMaterial BROADHOOF_GOAT_LEATHER_ARMOR = registerArmor("broadhoof_goat_leather_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial BROADHOOF_GOAT_ORNAMENTED_LEATHER_ARMOR = registerArmor("broadhoof_goat_ornamented_leather_armor", Tiers.MEDIUM, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial BROADHOOF_GOAT_PLATE_ARMOR = registerArmor("broadhoof_goat_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_KHAZAD_STEEL_ARMOR);

    ExtendedArmorMaterial GREAT_HORN_LIGHT_ARMOR = registerArmor("great_horn_light_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FABRIC_ARMOR);
    ExtendedArmorMaterial GREAT_HORN_LIGHT_GRAY_ARMOR = registerArmor("great_horn_light_gray_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FABRIC_ARMOR);
    ExtendedArmorMaterial GREAT_HORN_LIGHT_GREEN_ARMOR = registerArmor("great_horn_light_green_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTagsME.REPAIRS_FABRIC_ARMOR);
    ExtendedArmorMaterial GREAT_HORN_PLATE_ARMOR = registerArmor("great_horn_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);
    ExtendedArmorMaterial GREAT_HORN_ORNAMENTED_PLATE_ARMOR = registerArmor("great_horn_ornamented_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);
    ExtendedArmorMaterial GREAT_HORN_GREEN_PLATE_ARMOR = registerArmor("great_horn_green_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);

    ExtendedArmorMaterial WARG_LEATHER_ARMOR = registerArmor("warg_leather_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial WARG_REINFORCED_LEATHER_ARMOR = registerArmor("warg_reinforced_leather_armor", Tiers.LIGHT, SoundEvents.ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial WARG_MORDOR_MAIL_ARMOR = registerArmor("warg_mordor_mail_armor", Tiers.MEDIUM, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);
    ExtendedArmorMaterial WARG_MORDOR_PLATE_ARMOR = registerArmor("warg_mordor_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial WARG_ISENGARD_PLATE_ARMOR = registerArmor("warg_isengard_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial WARG_GUNDABAD_PLATE_ARMOR = registerArmor("warg_gundabad_plate_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_IRON, ItemTagsME.REPAIRS_BURZUM_STEEL_ARMOR);

    ExtendedArmorMaterial GONDORIAN_HORSE_ARMOR = registerArmor("gondorian_horse_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial ROHIRRIC_HORSE_ARMOR = registerArmor("rohirric_horse_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial DALISH_HORSE_ARMOR = registerArmor("dalish_horse_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial LORIEN_HORSE_ARMOR = registerArmor("lorien_horse_armor", Tiers.HEAVY, SoundEvents.ARMOR_EQUIP_CHAIN, ItemTagsME.REPAIRS_EDHEL_STEEL_ARMOR);

    private static ExtendedArmorMaterial registerArmor(String name, Tiers tier, Holder<SoundEvent> equipSound, TagKey<Item> repairTag) {
        EnumMap<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        float toughness;
        
        float knockbackResistance;
        int enchantability;
        int durabilityMultiplier;
        switch (tier) {
            case CLOTHING -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 1);
                map.put(ArmorType.CHESTPLATE, 1);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 1);
                durabilityMultiplier = 2;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case BASIC -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 2);
                map.put(ArmorType.CHESTPLATE, 3);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 4);
                durabilityMultiplier = 6;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case LIGHT -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 3);
                map.put(ArmorType.CHESTPLATE, 4);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 5);
                durabilityMultiplier = 11;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case MEDIUM -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 4);
                map.put(ArmorType.CHESTPLATE, 5);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 6);
                durabilityMultiplier = 18;
                toughness = 0.5f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case STURDY -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 8);
                durabilityMultiplier = 23;
                toughness = 1.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case HEAVY -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 7);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 10);
                durabilityMultiplier = 37;
                toughness = 2.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            default -> {
                map.put(ArmorType.BOOTS, 1);
                map.put(ArmorType.LEGGINGS, 2);
                map.put(ArmorType.CHESTPLATE, 3);
                map.put(ArmorType.HELMET, 1);
                map.put(ArmorType.BODY, 3);
                durabilityMultiplier = 5;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 1;
            }
        }
        return register(name, map, durabilityMultiplier, enchantability, equipSound, toughness, knockbackResistance, repairTag, tier);
    }

    private static ExtendedArmorMaterial register(String name, EnumMap<ArmorType, Integer> defense,
                                                  int durabilityMultiplier, int enchantability, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance,
                                                  TagKey<Item> repairTag, Tiers tier) {

        EnumMap<ArmorType, Integer> enumMap = new EnumMap<>(ArmorType.class);
        for (ArmorType type : ArmorType.values()) {
            enumMap.put(type, defense.get(type));
        }
        ArmorMaterial material =  new ArmorMaterial(durabilityMultiplier, enumMap, enchantability, equipSound, toughness, knockbackResistance, repairTag, ResourceKey.create(REGISTRY_KEY, Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, name)));
        return new ExtendedArmorMaterial(material, durabilityMultiplier, tier);
    }

    enum Tiers implements StringRepresentable {
        CLOTHING(0, "tier_clothing",11184810),
        BASIC(1, "tier_basic", 16777215),
        LIGHT(2, "tier_light", 5635925),
        MEDIUM(3, "tier_medium", 5592575),
        STURDY(4, "tier_sturdy",11141290),
        HEAVY(5, "tier_heavy",11141120),
        ;

        private static final IntFunction<Tiers> BY_ID = ByIdMap.continuous(Tiers::getId, Tiers.values(), ByIdMap.OutOfBoundsStrategy.ZERO);;
        private final String name;
        private final int id;
        private final int color;

        public static final Codec<Tiers> CODEC = StringRepresentable.fromValues(Tiers::values);
        public static final StreamCodec<ByteBuf, Tiers> PACKET_CODEC = ByteBufCodecs.idMapper(BY_ID, Tiers::getId);

        Tiers(int id, String name, int color){
            this.name = name;
            this.id = id;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getColor() {
            return color;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
