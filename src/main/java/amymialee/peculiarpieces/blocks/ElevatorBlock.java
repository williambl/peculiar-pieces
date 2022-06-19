package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.callbacks.PlayerCrouchConsumingBlock;
import amymialee.peculiarpieces.callbacks.PlayerJumpConsumingBlock;
import amymialee.peculiarpieces.util.WarpManager;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElevatorBlock extends Block implements PlayerCrouchConsumingBlock, PlayerJumpConsumingBlock {
    public ElevatorBlock(FabricBlockSettings settings) {
        super(settings);
    }

    @Override
    public void onCrouch(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        for(int i = pos.getY() - 1; i >= world.getBottomY(); i--) {
            BlockPos pos2 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() instanceof ElevatorBlock) {
                WarpManager.queueTeleport(player, pos2.add(0, 1, 0));
                break;
            }
        }
    }

    @Override
    public void onJump(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        for(int i = pos.getY() + 1; i <= world.getTopY(); i++) {
            BlockPos pos2 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() instanceof ElevatorBlock) {
                WarpManager.queueTeleport(player, pos2.add(0, 1, 0));
                break;
            }
        }
    }
}