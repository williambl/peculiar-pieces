package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import amymialee.peculiarpieces.util.WarpInstance;
import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CheckpointReturnerBlock extends AbstractStructureVoidBlock {
    public CheckpointReturnerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isClient() && entity instanceof PlayerEntity player && player instanceof ExtraPlayerDataWrapper checkPlayer) {
            Vec3d checkpointPos = checkPlayer.getCheckpointPos();
            if (checkpointPos != null && checkpointPos.distanceTo(entity.getPos()) > 2) {
                WarpManager.queueTeleport(WarpInstance.of(player).position(checkpointPos).particles());
                player.sendMessage(Text.translatable("%s.checkpoint_returned".formatted(PeculiarPieces.MOD_ID)).formatted(Formatting.GRAY), true);
            }
        }
    }
}