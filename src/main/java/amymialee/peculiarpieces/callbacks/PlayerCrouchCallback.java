package amymialee.peculiarpieces.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface PlayerCrouchCallback {
    Event<PlayerCrouchCallback> EVENT = EventFactory.createArrayBacked(PlayerCrouchCallback.class,
            (listeners) -> (player, world) -> {
                for (PlayerCrouchCallback event : listeners) {
                    event.onCrouch(player, world);
                }
            }
    );

    void onCrouch(PlayerEntity player, World world);
}