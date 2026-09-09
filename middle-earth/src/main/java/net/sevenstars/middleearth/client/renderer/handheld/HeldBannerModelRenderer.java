package net.sevenstars.middleearth.client.renderer.handheld;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.MiddleEarthClient;
import net.sevenstars.middleearth.client.model.hand.HeldBannerEntityModel;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.Objects;
import java.util.function.Consumer;

public class HeldBannerModelRenderer implements SpecialModelRenderer<DataComponentMap> {

    private final HeldBannerEntityModel model;
    private final SpriteGetter sprites;

    public HeldBannerModelRenderer(SpriteGetter sprites, HeldBannerEntityModel model) {
        this.model = model;
        this.sprites = sprites;
    }

    @Override
    public void submit(@Nullable DataComponentMap data, PoseStack matrices, SubmitNodeCollector collector, int light, int overlay, boolean glint, int seed) {
        BannerPatternLayers bannerPatternsComponent = data != null ? data.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) : BannerPatternLayers.EMPTY;
        DyeColor dyeColor = data != null ? data.get(DataComponents.BASE_COLOR) : null;
        boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor != null;
        matrices.pushPose();
        matrices.scale(1.0F, -1.0F, -1.0F);
        SpriteId sprite = Sheets.BANNER_BASE;

        collector.submitModelPart(this.model.getPole(), matrices, sprite.renderType(this.model.renderType()), light, overlay, this.sprites.get(sprite));
        collector.submitModelPart(this.model.getBanner(), matrices, sprite.renderType(this.model.renderType()), light, overlay, this.sprites.get(sprite));
        if (bl) {
            DyeColor canvasColor = Objects.requireNonNullElse(dyeColor, DyeColor.WHITE);
            collector.submitModelPart(this.model.getBanner(), matrices,
                    ShieldPatternLayers.bannerCanvas().renderType(RenderTypes::bannerPattern),
                    light, overlay, this.sprites.get(ShieldPatternLayers.bannerCanvas()),
                    canvasColor.getTextureDiffuseColor(), null, 0);
            ShieldPatternLayers.submitCanvas(collector, matrices, light, overlay, this.sprites, this.model.getBanner(),
                    ShieldPatternLayers.bannerCanvas(), ShieldPatternLayers.banner(), canvasColor, bannerPatternsComponent);
        }
        if (glint) {
            collector.order(bannerPatternsComponent.layers().size() + 1).submitModel(this.model, Unit.INSTANCE, matrices,
                    RenderTypes.entityGlint(), light, overlay, -1, this.sprites.get(sprite), 0, null);
        }
        matrices.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> extents) {
        PoseStack matrixStack = new PoseStack();
        matrixStack.scale(1.0F, -1.0F, -1.0F);
        this.model.root().getExtentsForGui(matrixStack, extents);
    }

    @Nullable
    @Override
    public DataComponentMap extractArgument(ItemStack stack) {
        return stack.immutableComponents();
    }

    @Environment(EnvType.CLIENT)
    public static record Unbaked() implements SpecialModelRenderer.Unbaked<DataComponentMap> {
        public static final HeldBannerModelRenderer.Unbaked INSTANCE = new HeldBannerModelRenderer.Unbaked();
        public static final MapCodec<HeldBannerModelRenderer.Unbaked> CODEC;

        public Unbaked() {
        }

        public MapCodec<HeldBannerModelRenderer.Unbaked> type() {
            return CODEC;
        }

        public SpecialModelRenderer<DataComponentMap> bake(BakingContext context) {
            return new HeldBannerModelRenderer(context.sprites(), new HeldBannerEntityModel(context.entityModelSet().bakeLayer(MiddleEarthClient.HELD_BANNER_LAYER)));
        }

        static {
            CODEC = MapCodec.unit(INSTANCE);
        }
    }
}
