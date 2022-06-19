package amymialee.peculiarpieces.callbacks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlayerJumpConsumingBlock {
    void onJump(BlockState state, World world, BlockPos pos, PlayerEntity player);
}