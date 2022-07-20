package amymialee.peculiarpieces.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;

public class RedstoneRandomizerBlock extends AbstractRedstoneComparisonBlock {
    public static final IntProperty OUTPUT = IntProperty.of("output", 1, 15);

    public RedstoneRandomizerBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OUTPUT, 1).with(POWERED, false));
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
        if (!bl2) {
            world.setBlockState(pos, state.with(POWERED, false).with(OUTPUT, 0), Block.NOTIFY_LISTENERS);
        } else if (!bl) {
            world.setBlockState(pos, state.with(POWERED, true).with(OUTPUT, random.nextBetween(1, getPower(world, pos, state))), Block.NOTIFY_LISTENERS);
            world.createAndScheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
        }
    }

    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return state.get(OUTPUT);
    }

    @Override
    protected boolean isValidInput(BlockState state) {
        return RepeaterBlock.isRedstoneGate(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, OUTPUT);
    }
}