package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.items.GliderItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> {
    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void PeculiarPieces$GlidingHeadAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity player && GliderItem.isGliding(player) && player.getVelocity().getY() < 0) {
            boolean bl = livingEntity.getRoll() > 4;
            this.head.pitch = (bl ? -0.7853982f : (this.leaningPitch > 0.0f ? (this.lerpAngle(this.leaningPitch, this.head.pitch, j * ((float) Math.PI / 180))) : j * ((float) Math.PI / 180))) * 0.3f + 0.38f;
            this.hat.copyTransform(this.head);
        }
    }
}