package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleItem.class)
public class BundleItemMixin {
    @Inject(method = "getItemOccupancy", at = @At("TAIL"), cancellable = true)
    private static void getItemOccupancy(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.isOf(PeculiarItems.PACKED_POUCH)) {
            cir.setReturnValue(2048);
        } else {
            cir.setReturnValue(1);
        }
    }
}