package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.entity.TeleportItemEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class PositionTrapItem extends Item {
    public PositionTrapItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (player != null && player.isSneaking()) {
            writeStone(stack, context.getBlockPos().add(0, 1, 0));
            player.getItemCooldownManager().set(this, 20);
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            return TypedActionResult.consume(stack);
        }
        BlockPos pos = readStone(stack);
        if (pos.getSquaredDistance(0, 0, 0) > 1) {
            if (!world.isClient && !pos.equals(BlockPos.ORIGIN)) {
                dropTrap(stack, user, hand);
            }
            user.getItemCooldownManager().set(this, 20);
            stack.decrement(1);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.consume(stack);
    }

    public void dropTrap(ItemStack stack, PlayerEntity user, Hand hand) {
        if (stack.isEmpty()) {
            return;
        }
        double d = user.getEyeY() - (double)0.3f;
        TeleportItemEntity teleportItemEntity = new TeleportItemEntity(user.world, user.getX(), d, user.getZ(), user.getStackInHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND).copy(), stack);
        teleportItemEntity.setThrower(user);
        teleportItemEntity.setDelay(40);
        float g = MathHelper.sin(user.getPitch() * ((float)Math.PI / 180));
        float h = MathHelper.cos(user.getPitch() * ((float)Math.PI / 180));
        float i = MathHelper.sin(user.getYaw() * ((float)Math.PI / 180));
        float j = MathHelper.cos(user.getYaw() * ((float)Math.PI / 180));
        float k = user.getRandom().nextFloat() * ((float)Math.PI * 2);
        float l = 0.02f * user.getRandom().nextFloat();
        teleportItemEntity.setVelocity((double)(-i * h * 0.3f) + Math.cos(k) * (double)l, -g * 0.3f + 0.1f + (user.getRandom().nextFloat() - user.getRandom().nextFloat()) * 0.1f, (double)(j * h * 0.3f) + Math.sin(k) * (double)l);
        user.world.spawnEntity(teleportItemEntity);
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
    public boolean hasGlint(ItemStack stack) {
        return (stack.getNbt() != null && stack.getNbt().contains("pp:stone")) || super.hasGlint(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getNbt() != null) {
            BlockPos pos = NbtHelper.toBlockPos(stack.getNbt().getCompound("pp:stone"));
            if (!pos.equals(BlockPos.ORIGIN)) {
                tooltip.add(Text.translatable("Position: x%d, y%d, z%d".formatted(pos.getX(), pos.getY(), pos.getZ())).formatted(Formatting.RED));
            } else {
                tooltip.add(Text.translatable("peculiarpieces.pearl.empty").formatted(Formatting.RED));
                tooltip.add(Text.translatable("peculiarpieces.pearl.bind").formatted(Formatting.RED));
            }
        } else {
            tooltip.add(Text.translatable("peculiarpieces.pearl.empty").formatted(Formatting.RED));
            tooltip.add(Text.translatable("peculiarpieces.pearl.bind").formatted(Formatting.RED));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}