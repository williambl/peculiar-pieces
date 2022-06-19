package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.EntityPos;
import amymialee.peculiarpieces.util.WarpManager;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class RotatingElevatorBlock extends ElevatorBlock {
    private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public RotatingElevatorBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public void onCrouch(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        for(int i = pos.getY() - 1; i >= world.getBottomY(); i--) {
            BlockPos pos2 = new BlockPos(pos.getX(), i, pos.getZ());
            BlockState state2 = world.getBlockState(pos2);
            if (state2.getBlock() instanceof ElevatorBlock) {
                WarpManager.queueTeleport(player, new EntityPos(pos2.add(0, 1, 0), player.getPitch(), state2.get(FACING).getOpposite().asRotation()));
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
                WarpManager.queueTeleport(player, new EntityPos(pos2.add(0, 1, 0), player.getPitch(), state2.get(FACING).getOpposite().asRotation()));
                break;
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}