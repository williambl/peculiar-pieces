package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.items.TransportPearlItem;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @Shadow public abstract NbtCompound getOrCreateNbt();

    @Shadow public abstract @Nullable NbtCompound getNbt();

    @Inject(method = "setCustomName", at = @At("TAIL"))
    public void PeculiarPieces$TransPearlNameStorage(Text name, CallbackInfoReturnable<ItemStack> cir) {
        if (this.getItem() == PeculiarItems.TRANS_PEARL && name != null) {
            getOrCreateNbt().putString("pp:stone_name_%d".formatted(TransportPearlItem.getSlot((ItemStack) ((Object) this))), name.getString());
        }
    }

    @Inject(method = "removeCustomName", at = @At("TAIL"))
    public void PeculiarPieces$TransPearlNameRemoval(CallbackInfo ci) {
        if (this.getItem() == PeculiarItems.TRANS_PEARL && getNbt() != null) {
            getNbt().remove("pp:stone_name_%d".formatted(TransportPearlItem.getSlot((ItemStack) ((Object) this))));
        }
    }
}