package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class FishTankBlock extends BlockWithEntity {
    public FishTankBlock(Settings settings) {
        super(settings);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FishTankBlockEntity(pos, state);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (itemStack.hasCustomName()) {
            if (blockEntity instanceof FishTankBlockEntity fishTank) {
                fishTank.setCustomName(itemStack.getName());
            }
        }
        if (blockEntity instanceof FishTankBlockEntity fishTank) {
            fishTank.yaw = placer.getHeadYaw();
        }
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FishTankBlockEntity fishTank) {
                if (player.isSneaking() && player.getAbilities().allowModifyWorld) {
                    player.openHandledScreen(fishTank);
                }
            }
        }
        return ActionResult.CONSUME;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FishTankBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}