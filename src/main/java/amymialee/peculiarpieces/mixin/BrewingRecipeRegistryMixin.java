package amymialee.peculiarpieces.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(Potions.class)
public class BrewingRecipeRegistryMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "register", at = @At("TAIL"))
    private static void register(String name, Potion potion, CallbackInfoReturnable<Potion> cir) {
        boolean should = false;
        ArrayList<StatusEffectInstance> effects = new ArrayList<>();
        for (StatusEffectInstance instance : potion.getEffects()) {
            StatusEffectInstance instance2 = new StatusEffectInstance(instance);
            ((StatusEffectInstanceAccessor) instance2).setShowParticles(false);
            effects.add(instance2);
            if (instance.shouldShowParticles()) {
                should = true;
            }
        }
        StatusEffectInstance[] array = new StatusEffectInstance[0];
        if (should) {
            Potion potion2 = new Potion(null, effects.toArray(array));
            Registry.register(Registry.POTION, name + "_hidden", potion2);
        }
    }
}
