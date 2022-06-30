package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.BigDropperBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BigDropperBlock extends DropperBlock {
    public BigDropperBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BigDropperBlockEntity(pos, state);
    }
}