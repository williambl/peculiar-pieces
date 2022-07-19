package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class FlagBlock extends BlockWithEntity {
    public static final EnumProperty<WallMountLocation> FACE = Properties.WALL_MOUNT_LOCATION;
    public static final IntProperty ROTATION = Properties.ROTATION;
    protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(6, 1, 11, 10, 16, 16);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(6, 1, 0, 10, 16, 5);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(11, 1, 6, 16, 16, 10);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0, 1, 6, 5, 16, 10);
    protected static final VoxelShape VERTICAL_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);

    public FlagBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0).with(FACE, WallMountLocation.FLOOR));
    }

    @Override
    public boolean canMobSpawnInside() {
        return true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FlagBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, PeculiarBlocks.FLAG_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.readFrom(itemStack));
        } else if (itemStack.hasCustomName()) {
            world.getBlockEntity(pos, PeculiarBlocks.FLAG_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.setCustomName(itemStack.getName()));
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FlagBlockEntity flagBlock) {
            return flagBlock.getPickStack();
        }
        return super.getPickStack(world, pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACE) == WallMountLocation.WALL) {
            switch (getDirection(state)) {
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
        }
        return VERTICAL_SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        switch (state.get(FACE)) {
            case CEILING -> {
                return canPlaceAt(world, pos, Direction.UP);
            }
            case WALL -> {
                return canPlaceAt(world, pos, getDirection(state).getOpposite());
            }
            case FLOOR -> {
                return canPlaceAt(world, pos, Direction.DOWN);
            }
        }
        return false;
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite());
    }

    protected static Direction getDirection(BlockState state) {
        switch (state.get(ROTATION)) {
            case 15, 0, 1, 2 -> {
                return Direction.SOUTH;
            }
            case 3, 4, 5, 6 -> {
                return Direction.WEST;
            }
            case 7, 8, 9, 10 -> {
                return Direction.NORTH;
            }
            default -> {
                return Direction.EAST;
            }
        }
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState = direction.getAxis() == Direction.Axis.Y ?
                    this.getDefaultState()
                            .with(FACE, direction == Direction.UP ? WallMountLocation.CEILING : WallMountLocation.FLOOR)
                            .with(ROTATION, MathHelper.floor((double)((180.0f + ctx.getPlayerYaw()) * 16.0f / 360.0f) + 0.5) & 0xF) :
                    this.getDefaultState().with(FACE, WallMountLocation.WALL)
                            .with(ROTATION, MathHelper.floor((double)((180.0f + ctx.getPlayerYaw()) * 16.0f / 360.0f) + 0.5) & 0xF);
            if (!blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (getDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate(state.get(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror(state.get(ROTATION), 16));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, FACE);
    }
}