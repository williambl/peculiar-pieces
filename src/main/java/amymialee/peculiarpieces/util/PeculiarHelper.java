package amymialee.peculiarpieces.util;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

public class PeculiarHelper {
    public static int clampLoop(int start, int end, int input) {
        if (input < start) {
            return clampLoop(start, end, (end - start + 1) + input);
        } else if (input > end) {
            return clampLoop(start, end, input - (end - start + 1));
        }
        return input;
    }

    public static boolean shouldFly(PlayerEntity player) {
        if (player.isCreative() || player.isSpectator()) {
            return true;
        }
        if (player.hasStatusEffect(PeculiarPieces.FLIGHT_EFFECT)) {
            return true;
        }
        Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(player);
        return optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.FLIGHT_RING);
    }

    public static void updateFlight(PlayerEntity player) {
        player.getAbilities().allowFlying = shouldFly(player);
        if (!player.getAbilities().allowFlying) {
            player.getAbilities().flying = false;
        }
        player.sendAbilitiesUpdate();
    }
}