package amymialee.peculiarpieces.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;

public class RedstoneMonoBlock extends AbstractRedstoneComparisonBlock {
    public static final BooleanProperty EXPIRED = BooleanProperty.of("expired");

    public RedstoneMonoBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(EXPIRED, false).with(POWERED, false));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return 1;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isLocked(world, pos, state)) {
            return;
        }
        boolean bl = state.get(POWERED);
        boolean bl2 = this.hasPower(world, pos, state);
        if (bl2) {
            if (!bl) {
                world.setBlockState(pos, state.with(POWERED, true), Block.NOTIFY_LISTENERS);
                world.createAndScheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
            } else if (!state.get(EXPIRED)) {
                world.setBlockState(pos, state.with(EXPIRED, true), Block.NOTIFY_LISTENERS);
                world.createAndScheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
            }
        } else {
            world.setBlockState(pos, state.with(POWERED, false).with(EXPIRED, false), Block.NOTIFY_LISTENERS);
        }
    }

    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return state.get(EXPIRED) ? 0 : getPower(world, pos, state);
    }

    @Override
    protected boolean isValidInput(BlockState state) {
        return RepeaterBlock.isRedstoneGate(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, EXPIRED);
    }
}