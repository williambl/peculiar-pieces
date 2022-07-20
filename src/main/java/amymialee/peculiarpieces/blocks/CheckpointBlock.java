package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CheckpointBlock extends AbstractStructureVoidBlock {
    public CheckpointBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity player) {
            ((ExtraPlayerDataWrapper) player).setCheckpointPos(Vec3d.ofBottomCenter(pos));
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}