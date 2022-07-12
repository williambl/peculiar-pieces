package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsItemMixin extends Item {
    public ShearsItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getMiningSpeedMultiplier", at = @At("TAIL"), cancellable = true)
    public void PeculiarPieces$ShearsMineable(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(state.isIn(PeculiarPieces.SHEARS_MINEABLE) ? 12.0f : super.getMiningSpeedMultiplier(stack, state));
    }
}