package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.screens.EnderPouchScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EnderPouchItem extends Item {
    private static final Text CONTAINER_NAME = Text.translatable("container.enderchest");

    public EnderPouchItem(FabricItemSettings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        EnderChestInventory enderChestInventory = user.getEnderChestInventory();
        if (enderChestInventory == null) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        if (world.isClient) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        user.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new EnderPouchScreenHandler(syncId, inventory, enderChestInventory), CONTAINER_NAME));
        world.playSoundFromEntity(null, user, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 0.5f, world.random.nextFloat() * 0.1f + 0.9f);
        user.incrementStat(Stats.OPEN_ENDERCHEST);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}