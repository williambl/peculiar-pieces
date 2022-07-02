package amymialee.peculiarpieces.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow public float fallDistance;
//
//    @Inject(method = "fall", at = @At("HEAD"), cancellable = true)
//    protected void PeculiarPieces$FallReverse(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {
//        if (onGround) {
//            Entity entity = ((Entity) ((Object) this));
//            if (entity instanceof LivingEntity livingEntity) {
//                Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
//                if (optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.BOUNCY_BOOTS)) {
//                    if (this.fallDistance > 0.0f) {
//                        Vec3d vec3d = livingEntity.getVelocity();
//                        livingEntity.setVelocity(vec3d.x, Math.abs(vec3d.y), vec3d.z);
//                        livingEntity.velocityModified = true;
//                    }
//                    ci.cancel();
//                }
//            }
//        }
//    }
//
//
//    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
//        if (entity instanceof LivingEntity livingEntity) {
//            Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
//            if (optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.BOUNCY_BOOTS)) {
//                Vec3d vec3d = livingEntity.getVelocity();
//                System.out.println(vec3d.y);
//                livingEntity.setVelocity(vec3d.x, Math.abs(vec3d.y) + 0.5, vec3d.z);
//                livingEntity.velocityModified = true;
//                ci.cancel();
//            }
//        }
//
//
//        LivingEntity entity = event.getEntityLiving();
//        BounceInfo info = BOUNCING_ENTITIES.get(entity);
//
//        // if we have info for this entity, time to work
//        if (info != null) {
//
//            // if its the bounce tick, time to bounce. This is to circumvent the logic that resets y motion after landing
//            if (entity.age == info.bounceTick) {
//                if (info.bounce != null) {
//                    entity.setDeltaMovement(info.bounce);
//                    info.bounce = null;
//                }
//                info.bounceTick = 0;
//            }
//
//            boolean isInAir = !entity.isOnGround() && !entity.isInWater() && !entity.onClimbable();
//
//            // preserve motion
//            if (isInAir && info.lastMagSq > 0) {
//                // figure out how much motion has reduced
//                Vec3 motion = entity.getDeltaMovement();
//                double motionSq = motion.x * motion.x + motion.z * motion.z;
//                // if not moving, cancel velocity preserving in 5 ticks
//                if (motionSq == 0) {
//                    if (info.stopMagTick == 0) {
//                        info.stopMagTick = entity.tickCount + 5;
//                    } else if (entity.tickCount > info.stopMagTick) {
//                        info.lastMagSq = 0;
//                    }
//                } else if (motionSq < info.lastMagSq) {
//                    info.stopMagTick = 0;
//                    // preserve 95% of former speed
//                    double boost = Math.sqrt(info.lastMagSq / motionSq) * 0.975f;
//                    if (boost > 1) {
//                        entity.setDeltaMovement(motion.x * boost, motion.y, motion.z * boost);
//                        entity.hasImpulse = true;
//                        info.lastMagSq = info.lastMagSq * 0.975f * 0.975f;
//                        // play sound if we had a big angle change
//                        double newAngle = Mth.atan2(motion.z, motion.x);
//                        if (Math.abs(newAngle - info.lastAngle) > 1) {
//                            entity.playSound(Sounds.SLIMY_BOUNCE.getSound(), 1.0f, 1.0f);
//                        }
//                        info.lastAngle = newAngle;
//                    } else {
//                        info.lastMagSq = motionSq;
//                        info.lastAngle = Mth.atan2(motion.z, motion.x);
//                    }
//                }
//            }
//
//            // timing the effect out
//            if (info.wasInAir && !isInAir) {
//                if (info.endHandler == 0) {
//                    info.endHandler = entity.tickCount + 5;
//                } else if (entity.tickCount > info.endHandler) {
//                    BOUNCING_ENTITIES.remove(entity);
//                }
//            } else {
//                info.endHandler = 0;
//                info.wasInAir = true;
//            }
//        }
//    }
//
//    @Mixin(Mixin.class)
//    private static class BounceInfo {
//        /** Velocity the entity should have, unused if null */
//        @Nullable
//        private Vec3d bounce;
//        /** Time to update the entities velocity */
//        private int bounceTick;
//        /** Tick to stop entity magnitude changes */
//        private int stopMagTick;
//        /** Magnitude of the X/Z motion last tick */
//        private double lastMagSq;
//        /** If true, the entity was in air last tick */
//        private boolean wasInAir = false;
//        /** Time when motion should stop */
//        private int endHandler = 0;
//        /** Last angle of motion, used for sound effects */
//        private double lastAngle;
//
//        public BounceInfo(LivingEntity entity, @Nullable Vec3d bounce) {
//            this.bounce = bounce;
//            if (bounce != null) {
//                // add one to the tick as there is a 1 tick delay between falling and ticking for many entities
//                this.bounceTick = entity.age + 1;
//            } else {
//                this.bounceTick = 0;
//            }
//            Vec3d motion = entity.getVelocity();
//            this.lastMagSq = motion.x * motion.x + motion.z * motion.z;
//        }
//    }
}
