package amymialee.peculiarpieces.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MidairBlockItem extends BlockItem {
    public MidairBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        float yaw = user.getHeadYaw();
        float pitch = user.getPitch();
        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        float g = -MathHelper.sin(pitch * ((float)Math.PI / 180));
        float h = MathHelper.cos(yaw * ((float)Math.PI / 180)) * MathHelper.cos(pitch * ((float)Math.PI / 180));
        BlockPos blockPos = new BlockPos(user.getPos().add(f * 2.4f, (g * 2.4f) + 1.8f, h * 2.4f));
        place(new ItemPlacementContext(user, hand, user.getStackInHand(hand), new BlockHitResult(user.getPos(), Direction.UP, blockPos, true)));
        return super.use(world, user, hand);
    }
}