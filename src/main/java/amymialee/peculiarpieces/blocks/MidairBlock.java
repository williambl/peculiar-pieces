package amymialee.peculiarpieces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MidairBlock extends Block {
    public MidairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        player.incrementStat(Stats.MINED.getOrCreateStat(this));
        Block.getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, player, stack).forEach(stack3 -> {
            if (!player.giveItemStack(stack3)) {
                Block.dropStack(world, pos, stack);
                state.onStacksDropped((ServerWorld) world, pos, stack, true);
            }
        });
    }
}