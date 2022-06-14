package amymialee.peculiarpieces.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

import java.util.Random;

public class RedstoneRandomizerBlock extends AbstractRedstoneComparisonBlock {
    public static final IntProperty OUTPUT = IntProperty.of("output", 0, 15);
    public static final BooleanProperty MIN = BooleanProperty.of("min");

    public RedstoneRandomizerBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(OUTPUT, 0).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        }
        float f = (state = state.cycle(MIN)).get(MIN) ? 0.55f : 0.5f;
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        return ActionResult.success(world.isClient);
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
            world.setBlockState(pos, state.with(POWERED, true).with(OUTPUT, random.nextInt(state.get(MIN) ? 1 : 0, getPower(world, pos, state) + 1)), Block.NOTIFY_LISTENERS);
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
        builder.add(FACING, MIN, OUTPUT, POWERED);
    }
}