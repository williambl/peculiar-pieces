package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.util.CheckpointPlayerWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class CheckpointRemoverBlock extends AbstractStructureVoidBlock {
    public CheckpointRemoverBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity player) {
            if (((CheckpointPlayerWrapper) player).getCheckpointPos() != null) {
                ((CheckpointPlayerWrapper) player).setCheckpointPos(null);
                player.sendMessage(Text.translatable("%s.checkpoint_cleared".formatted(PeculiarPieces.MOD_ID)).formatted(Formatting.GRAY), true);
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}