package net.sevenstars.middleearth.client.model.equipment.chest.backAttachments;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.client.model.equipment.chest.ChestplateAddonModel;
import net.sevenstars.middleearth.client.renderer.ArmedEntityRenderStateAccess;
import net.sevenstars.middleearth.client.renderer.BipedEntityRenderStateAccess;
import net.sevenstars.middleearth.utils.ToRad;

public class CloakCapeModel extends ChestplateAddonModel {
    private static final float MAX_ANGLE_CLOAK = 80f;
    private static final float SPEED_MULTIPLIER_CLOAK = 1.8f;
    private final ModelPart cape;

    public CloakCapeModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("body").getChild("cape");
    }

    @Override
    public void setupAnim(HumanoidRenderState bipedEntityRenderState) {
        BipedEntityRenderStateAccess renderStateAccess = ((BipedEntityRenderStateAccess)bipedEntityRenderState);
        if(renderStateAccess != null) {
            this.cape.getAllParts().forEach(ModelPart::resetPose);
            Vec3 currentVelocity = renderStateAccess.getVelocity();
            if(currentVelocity == null) currentVelocity = new Vec3(0, 0, 0);

            double sqrVel = currentVelocity.length();
            double speed = (sqrVel * 0.65f) + Math.sqrt(Math.abs(bipedEntityRenderState.walkAnimationSpeed)) * 0.35f;
            double degree;

            if (bipedEntityRenderState.isCrouching) {
                degree = 5f + (speed * (MAX_ANGLE_CLOAK / 2));
            } else {
                degree = (MAX_ANGLE_CLOAK * speed);
            }
            degree = Math.max(0.0F, degree);
            degree = Math.min(MAX_ANGLE_CLOAK, degree);

            double result = renderStateAccess.getVelocity().dot(currentVelocity);

            if(result > 0) {
                this.cape.xRot = ToRad.ex(degree);
            }
        }
    }
}
