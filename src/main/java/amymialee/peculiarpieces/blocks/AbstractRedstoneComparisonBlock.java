package amymialee.peculiarpieces.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public abstract class AbstractRedstoneComparisonBlock extends AbstractRedstoneGateBlock {
    public AbstractRedstoneComparisonBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 1;
    }

    @Override
    protected abstract int getOutputLevel(BlockView world, BlockPos pos, BlockState state);

    @Override
    protected boolean isValidInput(BlockState state) {
        return RepeaterBlock.isRedstoneGate(state);
    }

    protected static int getPower(BlockView world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction);
        int i = getEmittedRedstonePower(world, blockPos, direction);
        if (i >= 15) {
            return i;
        }
        BlockState blockState = world.getBlockState(blockPos);
        return Math.max(i, blockState.isOf(Blocks.REDSTONE_WIRE) ? blockState.get(RedstoneWireBlock.POWER) : 0);
    }

    public static int getEmittedRedstonePower(BlockView world, BlockPos pos, Direction direction) {
        BlockState blockState = world.getBlockState(pos);
        int i = blockState.getWeakRedstonePower(world, pos, direction);
        if (blockState.isSolidBlock(world, pos)) {
            return Math.max(i, getReceivedStrongRedstonePower(world, pos));
        }
        return i;
    }

    public static int getReceivedStrongRedstonePower(BlockView world, BlockPos pos) {
        int i = 0;
        if ((i = Math.max(i, getStrongRedstonePower(world, pos.down(), Direction.DOWN))) >= 15) {
            return i;
        }
        if ((i = Math.max(i, getStrongRedstonePower(world, pos.up(), Direction.UP))) >= 15) {
            return i;
        }
        if ((i = Math.max(i, getStrongRedstonePower(world, pos.north(), Direction.NORTH))) >= 15) {
            return i;
        }
        if ((i = Math.max(i, getStrongRedstonePower(world, pos.south(), Direction.SOUTH))) >= 15) {
            return i;
        }
        if ((i = Math.max(i, getStrongRedstonePower(world, pos.west(), Direction.WEST))) >= 15) {
            return i;
        }
        return Math.max(i, getStrongRedstonePower(world, pos.east(), Direction.EAST));
    }

    public static int getStrongRedstonePower(BlockView world, BlockPos pos, Direction direction) {
        return world.getBlockState(pos).getStrongRedstonePower(world, pos, direction);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}