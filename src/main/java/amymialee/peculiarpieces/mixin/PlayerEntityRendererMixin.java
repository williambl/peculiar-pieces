package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.client.HangGliderFeatureRenderer;
import amymialee.peculiarpieces.items.GliderItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void PeculiarPieces$ExtraFeatures(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new HangGliderFeatureRenderer<>(this, ctx.getModelLoader()));
    }

    @Inject(method = "setupTransforms(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V", at = @At("HEAD"), cancellable = true)
    protected void setupTransforms(AbstractClientPlayerEntity player, MatrixStack matrixStack, float f, float g, float h, CallbackInfo ci) {
        Vec3d velocity = player.getVelocity();
        if (GliderItem.isGliding(player) && velocity.getY() < 0) {
            player.bodyYaw = player.headYaw;
            player.prevBodyYaw = player.prevHeadYaw;
            super.setupTransforms(player, matrixStack, f, g, h);
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-95.0f));
            matrixStack.translate(0, -1, 0.85f);
            ci.cancel();
        }
    }

    @Inject(method = "getArmPose", at = @At("RETURN"), cancellable = true)
    private static void getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (GliderItem.hasGlider(player) && cir.getReturnValue() == BipedEntityModel.ArmPose.EMPTY) {
            cir.setReturnValue(BipedEntityModel.ArmPose.ITEM);
        }
    }
}