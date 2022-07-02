package amymialee.peculiarpieces.screens;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class EnderPouchScreenHandler extends GenericContainerScreenHandler {
    public EnderPouchScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ScreenHandlerType.GENERIC_9X3, syncId, playerInventory, inventory, 3);
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        player.world.playSoundFromEntity(null, player, SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.PLAYERS, 0.5f, player.getRandom().nextFloat() * 0.1f + 0.9f);
    }
}