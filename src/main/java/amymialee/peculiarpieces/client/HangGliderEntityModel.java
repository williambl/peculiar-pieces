package amymialee.peculiarpieces.client;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class HangGliderEntityModel<T extends LivingEntity> extends AnimalModel<T> {
    public static final String GLIDER = "glider";
    private final ModelPart glider;

    public HangGliderEntityModel(ModelPart root) {
        this.glider = root.getChild(GLIDER);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(1.5f);
        modelPartData.addChild(GLIDER, ModelPartBuilder.create().uv(0, 0).cuboid(-16.0f, -12.0f, 2.0f, 32.0f, 32.0f, 0.0f, dilation), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.glider);
    }

    @Override
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
        float k = 0.0f;
        float m = 0.0f;
        if (livingEntity.isFallFlying()) {
            float o = 1.0f;
            Vec3d vec3d = livingEntity.getVelocity();
            if (vec3d.y < 0.0) {
                Vec3d vec3d2 = vec3d.normalize();
                o = 1.0f - (float)Math.pow(-vec3d2.y, 1.5);
            }
            k = o * 0.34906584f + (1.0f - o) * k;
        } else if (livingEntity.isInSneakingPose()) {
            k = 0.6981317f;
            m = 3.0f;
        }
        this.glider.pivotY = m;
        if (livingEntity instanceof AbstractClientPlayerEntity player) {
            player.elytraPitch += (k - player.elytraPitch) * 0.1f;
            this.glider.pitch = player.elytraPitch;
        } else {
            this.glider.pitch = k;
        }
    }
}