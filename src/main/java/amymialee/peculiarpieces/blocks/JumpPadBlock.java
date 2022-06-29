package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.callbacks.PlayerJumpConsumingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class JumpPadBlock extends CarpetBlock implements PlayerJumpConsumingBlock {
    public static final IntProperty POWER = IntProperty.of("power", 0, 3);
    public static final BooleanProperty POWERED = Properties.POWERED;

    public JumpPadBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(POWER, 0).with(POWERED, false));
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}

    @Override
    public void onJump(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (state.get(POWERED)) {
            return;
        }
        int power = state.get(POWER) + 1;
        double d = (0.42f * power) + player.getJumpBoostVelocityModifier();
        Vec3d vec3d = player.getVelocity();
        player.setVelocity(vec3d.x, vec3d.y + d, vec3d.z);
        if (player.isSprinting()) {
            float f = player.getYaw() * ((float)Math.PI / 180);
            player.setVelocity(player.getVelocity().add(-MathHelper.sin(f) * 0.2f * power, 0.0, MathHelper.cos(f) * 0.2f * power));
        }
        player.velocityModified = true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld || !player.isSneaking()) {
            return ActionResult.PASS;
        }
        world.setBlockState(pos, state.cycle(POWER), Block.NOTIFY_ALL);
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, (float) (3 * state.get(POWER)) / 15);
        return ActionResult.success(world.isClient);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(POWERED)) {
            world.setBlockState(pos, state.with(POWERED, bl), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWER, POWERED);
    }
}
