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

public class SwanAdultModel extends SwanEntityModel {
    private final KeyframeAnimation walkingAnimation;
    private final KeyframeAnimation swimmingAnimation;
    private final KeyframeAnimation sleepingAnimation;
    private final KeyframeAnimation intimidateAnimation;
    private final KeyframeAnimation eatAnimation;
    private final KeyframeAnimation swimIdleAnimation;
    private final KeyframeAnimation flapAnimation;

    protected SwanAdultModel(ModelPart root) {
        super(root);

        this.walkingAnimation = SwanEntityAnimations.WALK.bake(root);
        this.swimmingAnimation = SwanEntityAnimations.SWIM.bake(root);
        this.sleepingAnimation = SwanEntityAnimations.SLEEP.bake(root);
        this.intimidateAnimation = SwanEntityAnimations.INTIMIDATE.bake(root);
        this.eatAnimation = SwanEntityAnimations.EATING.bake(root);
        this.swimIdleAnimation = SwanEntityAnimations.EATING_IN_WATER.bake(root);
        this.flapAnimation = SwanEntityAnimations.FLAP.bake(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -5.0F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.5F, 0.0F));

        PartDefinition wing_left = body.addOrReplaceChild("wing_left", CubeListBuilder.create(), PartPose.offset(4.0F, -2.75F, -4.5F));

        PartDefinition wing_left_inner = wing_left.addOrReplaceChild("wing_left_inner", CubeListBuilder.create().texOffs(30, 11).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_left_middle = wing_left_inner.addOrReplaceChild("wing_left_middle", CubeListBuilder.create().texOffs(48, 3).addBox(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.25F, 5.5F));

        PartDefinition wing_left_outer = wing_left_middle.addOrReplaceChild("wing_left_outer", CubeListBuilder.create().texOffs(37, -6).addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.5F, 6.0F));

        PartDefinition wing_right = body.addOrReplaceChild("wing_right", CubeListBuilder.create(), PartPose.offset(-4.0F, -2.75F, -4.5F));

        PartDefinition wing_right_inner = wing_right.addOrReplaceChild("wing_right_inner", CubeListBuilder.create().texOffs(30, 11).mirror().addBox(-0.5F, -0.75F, -0.5F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_right_middle = wing_right_inner.addOrReplaceChild("wing_right_middle", CubeListBuilder.create().texOffs(48, 3).mirror().addBox(-0.5F, -3.0F, 0.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.25F, 5.5F));

        PartDefinition wing_right_outer = wing_right_middle.addOrReplaceChild("wing_right_outer", CubeListBuilder.create().texOffs(37, -6).mirror().addBox(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, -0.5F, 6.0F));

        PartDefinition head_and_neck = body.addOrReplaceChild("head_and_neck", CubeListBuilder.create(), PartPose.offset(0.5F, -2.5F, -4.0F));

        PartDefinition neck = head_and_neck.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(12, 17).addBox(-1.5F, -1.75F, -4.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-1.5F, -4.75F, -1.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -1.25F, 1.0F));

        PartDefinition head = head_and_neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -6.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55).addBox(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.01F)), PartPose.offset(-0.5F, -6.0F, 1.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 23).addBox(-3.5F, -2.25F, -0.25F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(7, 26).addBox(-3.5F, -0.25F, -0.25F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 5.25F));

        PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 23).addBox(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 5.0F, 0.5F));

        PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(0, 3).mirror().addBox(-1.0F, -2.0F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 23).mirror().addBox(-2.0F, 1.0F, -2.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 5.0F, 0.5F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void setupAnim(SwanEntityRenderState state) {
        super.setupAnim(state);

        this.walkingAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 6.0f, 1.5f);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.ageInTicks);
        this.swimmingAnimation.apply(state.swimmingAnimationState, state.ageInTicks);
        this.intimidateAnimation.apply(state.intimidateAnimationState, state.ageInTicks);
        this.eatAnimation.apply(state.eatAnimationState, state.ageInTicks, 2.2f);
        this.swimIdleAnimation.apply(state.swimIdleAnimationState, state.ageInTicks);
        this.flapAnimation.apply(state.flapAnimationState, state.ageInTicks, 3f);
    }
}
