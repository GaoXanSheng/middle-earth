package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SwanBabyModel extends SwanEntityModel{
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation swimmingAnimation;
    private final KeyframeAnimation sleepingAnimation;
    private final KeyframeAnimation flapAnimation;
    protected SwanBabyModel(ModelPart root) {
        super(root);

        this.walkingAnimation = SwanEntityAnimations.BABY_WALK.bake(root);
        this.swimmingAnimation = SwanEntityAnimations.BABY_SWIM.bake(root);
        this.sleepingAnimation = SwanEntityAnimations.BABY_SLEEP.bake(root);
        this.flapAnimation = SwanEntityAnimations.BABY_FLAP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5F, 21.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -2.0F, -1.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.0F, 0.0F));

        PartDefinition backfeathers_r1 = body.addOrReplaceChild("backfeathers_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-1.5F, -1.2F, -0.8F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.5F, -1.0F, 3.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition cube_r1 = left_wing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-3.0F, -2.0F, 0.0F));

        PartDefinition cube_r2 = right_wing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition left_leg = body.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(10, 8).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 1).addBox(-1.0F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 7).addBox(-3.0F, -5.0F, -2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(-2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = body.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 8).mirror().addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(1, 1).mirror().addBox(0.0F, 1.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, 1.5F));

        return LayerDefinition.create(modelData, 32, 32);
    }

    @Override
    public void setupAnim(SwanEntityRenderState state) {
        super.setupAnim(state);

        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2.0f, 1.0f);
        this.swimmingAnimation.apply(state.swimmingAnimationState, state.ageInTicks);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.ageInTicks);
        this.flapAnimation.apply(state.flapAnimationState, state.ageInTicks, 3f);
    }
}
