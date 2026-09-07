package net.sevenstars.middleearth.entity.npcs.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.resources.datas.texture_presets.SimplifiedTexturePreset;

public class NpcTextureData {
    private Identifier simplifiedSkin;
    private Identifier simplifiedEar;
    private Identifier simplifiedFeet;
    private Identifier simplifiedHair;
    private Identifier simplifiedNose;

    private Identifier bodyTexture;
    private Identifier feetTexture;
    private Identifier headTexture;
    private Identifier earTexture;
    private Identifier noseTexture;
    private Identifier eyeTexture;
    private Identifier eyeEmissiveTexture;
    private Identifier eyebrowTexture;
    private Identifier scarTexture;
    private Identifier beardTexture;
    private Identifier beardAddonTexture;
    private Identifier hairTexture;
    private Identifier hairAddonTexture;
    private Identifier clothingBaseTexture;
    private Identifier clothingOverTexture;
    private Identifier clothingExtraTexture;

    private Boolean eyeIsEmissive;

    public static final Codec<NpcTextureData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
        CompoundTag.CODEC.fieldOf("dynamic").forGetter(NpcTextureData::writeDynamic),
        CompoundTag.CODEC.fieldOf("simplified").forGetter(NpcTextureData::writeSimplified)
    ).apply(instance, NpcTextureData::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, NpcTextureData> PACKET_CODEC;

    public NpcTextureData(CompoundTag dynamic, CompoundTag simplified)
    {
        this.simplifiedSkin = MiddleEarth.fetchId(simplified.getStringOr("skin", null));
        this.simplifiedEar = MiddleEarth.fetchId(simplified.getStringOr("ear", null));
        this.simplifiedFeet = MiddleEarth.fetchId(simplified.getStringOr("feet", null));
        this.simplifiedHair = MiddleEarth.fetchId(simplified.getStringOr("hair", null));
        this.simplifiedNose = MiddleEarth.fetchId(simplified.getStringOr("nose", null));

        this.bodyTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.BODY.getField(), null));
        this.headTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.HEAD.getField(), null));
        this.feetTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.FEET.getField(), null));

        this.earTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.EAR.getField(), null));
        this.noseTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.NOSE.getField(), null));

        this.scarTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.SCAR.getField(), null));
        this.eyeTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.EYE.getField(), null));
        this.eyeEmissiveTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.EYE_EMISSIVE.getField(), null));
        this.eyeIsEmissive = dynamic.getBooleanOr(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField(), false);

        this.hairTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.HAIR.getField(), null));
        this.hairAddonTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.HAIR_ADDON.getField(), null));

        this.eyebrowTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.EYEBROW.getField(), null));

        this.beardTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.BEARD.getField(), null));
        this.beardAddonTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.BEARD_ADDON.getField(), null));

        this.clothingBaseTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.CLOTHING_BASE.getField(), null));
        this.clothingOverTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.CLOTHING_OVER.getField(), null));
        this.clothingExtraTexture = MiddleEarth.fetchId(dynamic.getStringOr(NpcRenderedPart.CLOTHING_EXTRA.getField(), null));
    }

    private CompoundTag writeSimplified() {
        CompoundTag nbt = new CompoundTag();
        if(simplifiedSkin != null)
            nbt.putString("skin", simplifiedSkin.toString());
        if(simplifiedEar != null)
            nbt.putString("ear", simplifiedEar.toString());
        if(simplifiedFeet != null)
            nbt.putString("feet", simplifiedFeet.toString());
        if(simplifiedHair != null)
            nbt.putString("hair", simplifiedHair.toString());
        if(simplifiedNose != null)
            nbt.putString("nose", simplifiedNose.toString());
        return nbt;
    }

    private CompoundTag writeDynamic() {
        CompoundTag nbt = new CompoundTag();
        if(bodyTexture != null)
            nbt.putString(NpcRenderedPart.BODY.getField(), bodyTexture.toString());
        if(headTexture != null)
            nbt.putString(NpcRenderedPart.HEAD.getField(), headTexture.toString());
        if(scarTexture != null)
            nbt.putString(NpcRenderedPart.SCAR.getField(), scarTexture.toString());
        if(earTexture != null)
            nbt.putString(NpcRenderedPart.EAR.getField(), earTexture.toString());
        if(noseTexture != null)
            nbt.putString(NpcRenderedPart.NOSE.getField(), noseTexture.toString());
        if(feetTexture != null)
            nbt.putString(NpcRenderedPart.FEET.getField(), feetTexture.toString());

        if(eyeTexture != null)
            nbt.putString(NpcRenderedPart.EYE.getField(), eyeTexture.toString());
        if(eyeEmissiveTexture != null)
            nbt.putString(NpcRenderedPart.EYE_EMISSIVE.getField(), eyeEmissiveTexture.toString());
        if(eyeIsEmissive != null)
            nbt.putBoolean(NpcRenderedPart.EYE_EMISSIVE_TOGGLE.getField(), eyeIsEmissive);

        if(hairTexture != null){
            nbt.putString(NpcRenderedPart.HAIR.getField(), hairTexture.toString());
            if(hairAddonTexture != null)
                nbt.putString(NpcRenderedPart.HAIR_ADDON.getField(), hairAddonTexture.toString());
        }

        if(eyebrowTexture != null)
            nbt.putString(NpcRenderedPart.EYEBROW.getField(), eyebrowTexture.toString());

        if(beardTexture != null){
            nbt.putString(NpcRenderedPart.BEARD.getField(), beardTexture.toString());
            if(beardAddonTexture != null)
                nbt.putString(NpcRenderedPart.BEARD_ADDON.getField(), beardAddonTexture.toString());
        }

        if(clothingBaseTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_BASE.getField(), clothingBaseTexture.toString());
        if(clothingOverTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_OVER.getField(), clothingOverTexture.toString());
        if(clothingExtraTexture != null)
            nbt.putString(NpcRenderedPart.CLOTHING_EXTRA.getField(), clothingExtraTexture.toString());

        return nbt;
    }

    public NpcTextureData() {
        this.eyeIsEmissive = false;
    }

    public void withSimplifiedPreset(SimplifiedTexturePreset preset) {
        if(preset == null)
            return;

        this.simplifiedSkin = preset.base;
        this.simplifiedEar = preset.ear;
        this.simplifiedFeet = preset.feet;
        this.simplifiedHair = preset.hair;
        this.simplifiedNose = preset.nose;
    }

    public NpcTextureData withSkinTexture(Identifier texture){
        this.bodyTexture = texture;
        return this;
    }
    public NpcTextureData withHeadTexture(Identifier texture){
        this.headTexture = texture;
        return this;
    }
    public NpcTextureData withFeetTexture(Identifier texture){
        this.feetTexture = texture;
        return this;
    }
    public NpcTextureData withScarTexture(Identifier texture){
        this.scarTexture = texture;
        return this;
    }
    public NpcTextureData withEarTexture(Identifier texture){
        this.earTexture = texture;
        return this;
    }
    public NpcTextureData withNoseTexture(Identifier texture){
        this.noseTexture = texture;
        return this;
    }

    public NpcTextureData withEyeTexture(Identifier eyeTexture, Identifier eyeEmissiveTexture, Boolean isEmissive){
        this.eyeTexture = eyeTexture;
        this.eyeEmissiveTexture = eyeEmissiveTexture;
        this.eyeIsEmissive = isEmissive;
        return this;
    }

    public NpcTextureData withHairTexture(Identifier texture){
        this.hairTexture = texture;
        return this;
    }

    public NpcTextureData withHairAddonTexture(Identifier texture){
        this.hairAddonTexture = texture;
        return this;
    }

    public NpcTextureData withEyebrowTexture(Identifier texture){
        this.eyebrowTexture = texture;
        return this;
    }

    public NpcTextureData withBeardTexture(Identifier texture){
        this.beardTexture = texture;
        return this;
    }

    public NpcTextureData withBeardAddonTexture(Identifier texture){
        this.beardAddonTexture = texture;
        return this;
    }
    public NpcTextureData withClothingTexture(Identifier textureBase, Identifier textureOver, Identifier textureExtra){
        this.clothingBaseTexture = textureBase;
        this.clothingOverTexture = textureOver;
        this.clothingExtraTexture = textureExtra;
        return this;
    }

    public Identifier get(NpcRenderedPart part){
        return switch (part){
            case BODY -> bodyTexture;
            case HEAD -> headTexture;
            case FEET -> feetTexture;
            case EAR -> earTexture;
            case NOSE -> noseTexture;
            case SCAR -> scarTexture;
            case EYE -> eyeTexture;
            case EYE_EMISSIVE -> eyeEmissiveTexture;
            case HAIR -> hairTexture;
            case HAIR_ADDON -> hairAddonTexture;
            case EYEBROW -> eyebrowTexture;
            case BEARD -> beardTexture;
            case BEARD_ADDON -> beardAddonTexture;
            case CLOTHING_BASE -> clothingBaseTexture;
            case CLOTHING_OVER -> clothingOverTexture;
            case CLOTHING_EXTRA -> clothingExtraTexture;
            default -> null;
        };
    }

    public Boolean isEyeEmissive() {
        return this.eyeIsEmissive;
    }
    static {
        PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.COMPOUND_TAG, NpcTextureData::writeDynamic,
                ByteBufCodecs.COMPOUND_TAG, NpcTextureData::writeSimplified,
                NpcTextureData::new);
    }

    public boolean needToBeRefreshed() {
        return get(NpcRenderedPart.BODY) == null;
    }

    public Identifier getSimplifiedSkin() {
        return this.simplifiedSkin;
    }

    public Identifier getSimplifiedEar() {
        return this.simplifiedEar;
    }

    public Identifier getSimplifiedFeet() {
        return this.simplifiedFeet;
    }

    public Identifier getSimplifiedHair() {
        return this.simplifiedHair;
    }

    public Identifier getSimplifiedNose() {
        return this.simplifiedNose;
    }
}
