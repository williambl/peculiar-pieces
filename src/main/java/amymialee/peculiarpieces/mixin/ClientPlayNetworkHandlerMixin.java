package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.registry.PeculiarItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "getActiveTotemOfUndying", at = @At("HEAD"), cancellable = true)
    private static void PeculiarPieces$MoreTotems(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) return;
        }
        Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(player);
        if (optionalComponent.isPresent()) {
            if (optionalComponent.get().isEquipped(PeculiarItems.TOKEN_OF_UNDYING)) {
                List<Pair<SlotReference, ItemStack>> equipped = optionalComponent.get().getEquipped(PeculiarItems.TOKEN_OF_UNDYING);
                cir.setReturnValue(equipped.get(0).getRight());
            } else if (optionalComponent.get().isEquipped(PeculiarItems.EVERLASTING_EMBLEM)) {
                if (!player.getItemCooldownManager().isCoolingDown(PeculiarItems.EVERLASTING_EMBLEM)) {
                    List<Pair<SlotReference, ItemStack>> equipped = optionalComponent.get().getEquipped(PeculiarItems.EVERLASTING_EMBLEM);
                    cir.setReturnValue(equipped.get(0).getRight());
                }
            }
        }
    }
}