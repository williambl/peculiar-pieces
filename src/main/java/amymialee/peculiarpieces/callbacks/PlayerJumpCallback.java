package amymialee.peculiarpieces.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface PlayerJumpCallback {
    Event<PlayerJumpCallback> EVENT = EventFactory.createArrayBacked(PlayerJumpCallback.class,
            (listeners) -> (player, world) -> {
                for (PlayerJumpCallback event : listeners) {
                    event.onJump(player, world);
                }
            }
    );

    void onJump(PlayerEntity player, World world);
}