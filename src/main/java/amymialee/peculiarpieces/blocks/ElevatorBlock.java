package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.callbacks.PlayerCrouchConsumingBlock;
import amymialee.peculiarpieces.callbacks.PlayerJumpConsumingBlock;
import amymialee.peculiarpieces.util.WarpInstance;
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
            if (state2.getBlock() instanceof ElevatorBlock block) {
                block.receiveTeleport(state2, pos2, player);
                break;
            }
        }
    }

    @Override
    public void onJump(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        for(int i = pos.getY() + 1; i <= world.getTopY(); i++) {
            BlockPos pos2 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() instanceof ElevatorBlock block) {
                block.receiveTeleport(state2, pos2, player);
                break;
            }
        }
    }

    public void receiveTeleport(BlockState state, BlockPos pos, PlayerEntity player) {
        WarpManager.queueTeleport(WarpInstance.of(player).position(pos.add(0, 1, 0)).particles());
    }
}