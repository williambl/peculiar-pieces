package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.registry.PeculiarItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void PeculiarPieces$SteadySnow(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
            if (optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.STEADY_SNEAKERS)) {
                cir.setReturnValue(true);
            }
        }
    }
}