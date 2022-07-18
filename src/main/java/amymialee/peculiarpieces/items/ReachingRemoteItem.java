package amymialee.peculiarpieces.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ReachingRemoteItem extends Item implements DyeableItem {
    public ReachingRemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null && player.isSneaking()) {
            writeTarget(stack, context.getBlockPos());
            player.getItemCooldownManager().set(this, 12);
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockPos pos = readTarget(stack);
        if (pos.getSquaredDistance(0, 0, 0) > 1) {
            if (!world.isClient && !pos.equals(BlockPos.ORIGIN)) {
                world.getBlockState(pos).onUse(world, user, hand, new BlockHitResult(Vec3d.ofCenter(pos), Direction.DOWN, pos, true));
            }
            user.getItemCooldownManager().set(this, 1);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.consume(stack);
    }

    public static void writeTarget(ItemStack stack, BlockPos pos) {
        stack.getOrCreateNbt().put("pp:target", NbtHelper.fromBlockPos(pos));
    }

    public static BlockPos readTarget(ItemStack stack) {
        if (stack.getNbt() != null) {
            return NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:target"));
        }
        return BlockPos.ORIGIN;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getNbt() != null) {
            BlockPos pos = NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:target"));
            tooltip.add(Text.translatable("Target: x%d, y%d, z%d".formatted(pos.getX(), pos.getY(), pos.getZ())).formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}