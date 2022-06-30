package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.BigDispenserBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BigDispenserBlock extends DispenserBlock {
    public BigDispenserBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BigDispenserBlockEntity(pos, state);
    }
}