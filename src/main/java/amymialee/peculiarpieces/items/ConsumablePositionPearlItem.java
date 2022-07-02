package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ConsumablePositionPearlItem extends Item {
    public ConsumablePositionPearlItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null && player.isSneaking() && (stack.getNbt() == null || (NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:stone")).equals(BlockPos.ORIGIN)) || player.isCreative())) {
            writeStone(stack, context.getBlockPos().add(0, 1, 0));
            player.getItemCooldownManager().set(this, 20);
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockPos pos = readStone(stack);
        if (pos.getSquaredDistance(0, 0, 0) > 1) {
            if (!world.isClient && !pos.equals(BlockPos.ORIGIN)) {
                WarpManager.queueTeleport(user, Vec3d.ofBottomCenter(pos));
            }
            user.getItemCooldownManager().set(this, 20);
            stack.decrement(1);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.consume(stack);
    }

    public static void writeStone(ItemStack stack, BlockPos pos) {
        stack.getOrCreateNbt().put("pp:stone", NbtHelper.fromBlockPos(pos));
    }

    public static BlockPos readStone(ItemStack stack) {
        if (stack.getNbt() != null) {
            return NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:stone"));
        }
        return BlockPos.ORIGIN;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getNbt() != null) {
            BlockPos pos = NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:stone"));
            if (!pos.equals(BlockPos.ORIGIN)) {
                tooltip.add(Text.translatable("Position: x%d, y%d, z%d".formatted(pos.getX(), pos.getY(), pos.getZ())).formatted(Formatting.GRAY));
            } else {
                tooltip.add(Text.translatable("peculiarpieces.pearl.empty").formatted(Formatting.GRAY));
                tooltip.add(Text.translatable("peculiarpieces.pearl.bind").formatted(Formatting.GRAY));
            }
        } else {
            tooltip.add(Text.translatable("peculiarpieces.pearl.empty").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("peculiarpieces.pearl.bind").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}