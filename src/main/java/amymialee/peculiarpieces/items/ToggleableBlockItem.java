package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.PeculiarHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ToggleableBlockItem extends BlockItem {
    private final int options;

    public ToggleableBlockItem(int options, Block block, Settings settings) {
        super(block, settings);
        this.options = options;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            ItemStack stack = user.getStackInHand(hand);
            toggle(stack);
            return TypedActionResult.success(stack);
        }
        return super.use(world, user, hand);
    }

    private void toggle(ItemStack stack) {
        NbtCompound compound = stack.getOrCreateNbt();
        int old = compound.getInt("pp:variant");
        compound.putInt("pp:variant", PeculiarHelper.clampLoop(0, options, old + 1));
    }
}