package net.sevenstars.middleearth.entity.beasts.great_horn.features;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntityModelLayersME;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornModel;

import java.time.LocalDate;
import java.time.Month;

@Environment(EnvType.CLIENT)
public class GreatHornNoseFeatureRenderer extends RenderLayer<GreatHornEntityRenderState, GreatHornModel> {
	private final GreatHornModel model;
	private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_red_nose.png");

	public GreatHornNoseFeatureRenderer(RenderLayerParent<GreatHornEntityRenderState, GreatHornModel> context, EntityModelSet loader) {
		super(context);
		this.model = new GreatHornModel(loader.bakeLayer(EntityModelLayersME.GREAT_HORN));
	}

	private boolean isChristmas() {
		LocalDate date = LocalDate.now();
		return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 24;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
		if(state.hasRedNose() || isChristmas()) {
			// TODO 26.2: was entityTranslucent overlay on parent model; now generic cutout overlay
			RenderLayer.renderColoredCutoutModel(this.getParentModel(), TEXTURE, poseStack, submitNodeCollector, light, state, -1, 0);
		}
	}
}
