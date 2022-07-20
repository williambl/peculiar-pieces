package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.PeculiarHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class RedstoneClampBlock extends AbstractRedstoneComparisonBlock {
    public static final IntProperty CLAMP = IntProperty.of("clamp", 0, 15);

    public RedstoneClampBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(CLAMP, 15).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        }
        int filter = PeculiarHelper.clampLoop(0, 15, state.get(CLAMP) + (player.isSneaking() ? -1 : 1));
        world.setBlockState(pos, state.with(CLAMP, filter), Block.NOTIFY_ALL);
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, (float) (3 * filter) / 15);
        return ActionResult.success(world.isClient);
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return Math.min(state.get(CLAMP), getPower(world, pos, state));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, CLAMP);
    }
}