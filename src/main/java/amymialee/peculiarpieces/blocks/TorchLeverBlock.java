package amymialee.peculiarpieces.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TorchLeverBlock extends WallFacingBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;
    protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(5.5, 3.0, 11.0, 10.5, 13.0, 16.0);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(5.5, 3.0, 0.0, 10.5, 13.0, 5.0);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(11.0, 3.0, 5.5, 16.0, 13.0, 10.5);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0.0, 3.0, 5.5, 5.0, 13.0, 10.5);
    protected static final VoxelShape FLOOR_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
    protected static final VoxelShape CEILING_SHAPE = Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 16.0, 10.0);
    protected final ParticleEffect particle;

    public TorchLeverBlock(AbstractBlock.Settings settings, ParticleEffect particle) {
        super(settings);
        this.particle = particle;
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.DOWN).with(POWERED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case UP -> {
                return CEILING_SHAPE;
            }
            case EAST -> {
                return EAST_WALL_SHAPE;
            }
            case WEST -> {
                return WEST_WALL_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_WALL_SHAPE;
            }
            case NORTH -> {
                return NORTH_WALL_SHAPE;
            }
        }
        return FLOOR_SHAPE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockState blockState = this.togglePower(state, world, pos);
        float f = blockState.get(POWERED) ? 0.6f : 0.5f;
        world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, f);
        world.emitGameEvent(player, blockState.get(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        return ActionResult.CONSUME;
    }

    public BlockState togglePower(BlockState state, World world, BlockPos pos) {
        state = state.cycle(POWERED);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
        this.updateNeighbors(state, world, pos);
        return state;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        boolean powered = state.get(POWERED);
        double d = (double) pos.getX() + 0.5;
        double e = (double) pos.getY() + 0.7;
        double f = (double) pos.getZ() + 0.5;
        switch (state.get(FACING)) {
            case DOWN -> {
                world.addParticle(powered ? new DustParticleEffect(DustParticleEffect.RED, 1) : ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
                if (!powered) {
                    world.addParticle(this.particle, d, e, f, 0.0, 0.0, 0.0);
                }
            }
            case EAST, WEST, NORTH, SOUTH -> {
                Direction direction = state.get(FACING).getOpposite();
                world.addParticle(powered ? new DustParticleEffect(DustParticleEffect.RED, 1) : ParticleTypes.SMOKE, d + (powered ? 0.05 : 0.27) * (double) direction.getOffsetX(), e + (powered ? 0.07 : 0.22), f + (powered ? 0.05 : 0.27) * (double) direction.getOffsetZ(), 0.0, 0.0, 0.0);
                if (!powered) {
                    world.addParticle(this.particle, d + 0.27 * (double) direction.getOffsetX(), e + 0.22, f + 0.27 * (double) direction.getOffsetZ(), 0.0, 0.0, 0.0);
                }
            }
            case UP -> {
                world.addParticle(powered ? new DustParticleEffect(DustParticleEffect.RED, 1) : ParticleTypes.SMOKE, d, e - 0.4, f, 0.0, 0.0, 0.0);
                if (!powered) {
                    world.addParticle(this.particle, d, e - 0.4, f, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (moved || state.isOf(newState.getBlock())) {
            return;
        }
        if (state.get(POWERED)) {
            this.updateNeighbors(state, world, pos);
        }
        super.onStateReplaced(state, world, pos, newState, false);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(POWERED) && state.get(FACING) == direction) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(state.get(FACING).getOpposite()), this);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }
}