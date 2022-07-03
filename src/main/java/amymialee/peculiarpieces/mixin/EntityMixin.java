package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$DisablePushing(Entity entity, CallbackInfo ci) {
        if (!entity.world.getGameRules().getBoolean(PeculiarPieces.NO_MOB_PUSHING)) {
            ci.cancel();
        }
    }
}