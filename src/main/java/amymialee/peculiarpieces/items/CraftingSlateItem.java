package amymialee.peculiarpieces.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CraftingSlateItem extends Item {
    private static final Text TITLE = Text.translatable("container.crafting");

    public CraftingSlateItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.consume(stack);
        }
        user.openHandledScreen(createScreenHandlerFactory(world));
        user.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
        return TypedActionResult.pass(stack);
    }

    public static NamedScreenHandlerFactory createScreenHandlerFactory(World world) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new CraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, player.getBlockPos())) {
            @Override
            public boolean canUse(PlayerEntity player) {
                return true;
            }
        }, TITLE);
    }
}
