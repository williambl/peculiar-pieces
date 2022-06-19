package amymialee.peculiarpieces.callbacks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface PlayerCrouchConsumingBlock {
    void onCrouch(BlockState state, World world, BlockPos pos, PlayerEntity player);
}