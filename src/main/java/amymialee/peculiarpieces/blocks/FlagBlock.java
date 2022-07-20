package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class FlagBlock extends BlockWithEntity {
    public static final EnumProperty<FlagMountLocation> FACE = EnumProperty.of("face", FlagMountLocation.class);
    public static final IntProperty ROTATION = Properties.ROTATION;
    protected static final VoxelShape NORTH_WALL_SHAPE = Block.createCuboidShape(6, 1, 11, 10, 16, 16);
    protected static final VoxelShape SOUTH_WALL_SHAPE = Block.createCuboidShape(6, 1, 0, 10, 16, 5);
    protected static final VoxelShape WEST_WALL_SHAPE = Block.createCuboidShape(11, 1, 6, 16, 16, 10);
    protected static final VoxelShape EAST_WALL_SHAPE = Block.createCuboidShape(0, 1, 6, 5, 16, 10);
    protected static final VoxelShape FLAT_NORTH_WALL_SHAPE = Block.createCuboidShape(0, 1, 15, 16, 15, 16);
    protected static final VoxelShape FLAT_SOUTH_WALL_SHAPE = Block.createCuboidShape(0, 1, 0, 16, 15, 1);
    protected static final VoxelShape FLAT_WEST_WALL_SHAPE = Block.createCuboidShape(15, 1, 0, 16, 15, 16);
    protected static final VoxelShape FLAT_EAST_WALL_SHAPE = Block.createCuboidShape(0, 1, 0, 1, 15, 16);
    protected static final VoxelShape VERTICAL_SHAPE = Block.createCuboidShape(6, 0, 6, 10, 16, 10);

    public FlagBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0).with(FACE, FlagMountLocation.FLOOR));
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
            world.getBlockEntity(pos, PeculiarBlocks.FLAG_BLOCK_ENTITY).ifPresent(blockEntity -> {
                blockEntity.readFrom(itemStack);
                blockEntity.setOffset(this.calculateOffset(world, pos, state));
            });
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
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        for (DyeColor color : DyeColor.values()) {
            addStack(color.getName().toLowerCase(), stacks);
        }
        for (ExtraFlag flag : ExtraFlag.values()) {
            addStack(flag.name().toLowerCase(), stacks);
        }
    }

    public void addStack(String name, DefaultedList<ItemStack> stacks) {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString(FlagBlockEntity.TEXTURE_KEY, name);
        BlockItem.setBlockEntityNbt(stack, PeculiarBlocks.FLAG_BLOCK_ENTITY, nbtCompound);
        stacks.add(stack);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(FACE) == FlagMountLocation.WALL) {
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
        if (state.get(FACE) == FlagMountLocation.FLAT) {
            switch (getDirection(state)) {
                case EAST -> {
                    return FLAT_EAST_WALL_SHAPE;
                }
                case WEST -> {
                    return FLAT_WEST_WALL_SHAPE;
                }
                case SOUTH -> {
                    return FLAT_SOUTH_WALL_SHAPE;
                }
                case NORTH -> {
                    return FLAT_NORTH_WALL_SHAPE;
                }
            }
        }
        return VERTICAL_SHAPE;
    }

    public static Direction getDirection(BlockState state) {
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
        PlayerEntity player = ctx.getPlayer();
        if (ctx.getSide().getAxis() != Direction.Axis.Y) {
            switch (ctx.getSide()) {
                case SOUTH -> {
                    return this.getDefaultState().with(FACE, (player != null && player.isSneaking()) ? FlagMountLocation.FLAT : FlagMountLocation.WALL).with(ROTATION, 0);
                }
                case WEST -> {
                    return this.getDefaultState().with(FACE, (player != null && player.isSneaking()) ? FlagMountLocation.FLAT : FlagMountLocation.WALL).with(ROTATION, 4);
                }
                case NORTH -> {
                    return this.getDefaultState().with(FACE, (player != null && player.isSneaking()) ? FlagMountLocation.FLAT : FlagMountLocation.WALL).with(ROTATION, 8);
                }
                case EAST -> {
                    return this.getDefaultState().with(FACE, (player != null && player.isSneaking()) ? FlagMountLocation.FLAT : FlagMountLocation.WALL).with(ROTATION, 12);
                }
            }
        }
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState = this.getDefaultState()
                    .with(FACE, direction == Direction.UP ? FlagMountLocation.CEILING : FlagMountLocation.FLOOR)
                    .with(ROTATION, MathHelper.floor((double)((180.0f + ((player != null && player.isSneaking() ? 90 : 0) + ctx.getPlayerYaw())) * 16.0f / 360.0f) + 0.5) & 0xF);
            if (!blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState;
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (getDirection(state).getOpposite() == direction) {
            if (!state.canPlaceAt(world, pos)) {
                return Blocks.AIR.getDefaultState();
            }

            world.getBlockEntity(pos, PeculiarBlocks.FLAG_BLOCK_ENTITY).ifPresent(blockEntity -> blockEntity.setOffset(this.calculateOffset(world, pos, state)));
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

    private int calculateOffset(WorldAccess world, BlockPos flagPos, BlockState flagState) {
        var flagMount = flagState.get(FACE);
        if (flagMount != FlagMountLocation.WALL) {
            return 0;
        }

        Direction dir = getDirection(flagState);
        return this.calculateOffset(world, flagPos.offset(dir.getOpposite()), dir);
    }

    private int calculateOffset(WorldAccess world, BlockPos mountingBlockPos, Direction mountingDir) {
        var state = world.getBlockState(mountingBlockPos);
        if (state.isSideSolid(world, mountingBlockPos, mountingDir, SideShapeType.CENTER)) {
            return 0;
        }

        var shape = state.getCollisionShape(world, mountingBlockPos);
        if (shape.isEmpty()) {
            return 0;
        }

        double maxInDirection = switch (mountingDir) {
            case NORTH, EAST -> 1 - shape.getMax(mountingDir.getAxis());
            default -> shape.getMin(mountingDir.getAxis());
        };

        if (maxInDirection > 1 || maxInDirection < 0) {
            return 0;
        }

        return (int) Math.ceil(maxInDirection);
    }

    public enum FlagMountLocation implements StringIdentifiable {
        FLOOR("floor"),
        WALL("wall"),
        FLAT("flat"),
        CEILING("ceiling");

        private final String name;

        FlagMountLocation(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }

    public enum ExtraFlag implements StringIdentifiable {
        PRIDE("pride"),
        LESBIAN("lesbian"),
        GAY("gay"),
        BI("bi"),
        TRANS("trans"),
        PAN("pan"),
        NB("nb"),
        ARO("aro"),
        ACE("ace"),
        INTER("inter"),
        LABRYS("labrys"),
        TEMPLATE("template");

        private final String name;

        ExtraFlag(String name) {
            this.name = name;
        }

        public String toString() {
            return this.asString();
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}