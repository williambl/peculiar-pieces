package amymialee.peculiarpieces.blocks;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SolidGlassBlock extends AbstractGlassBlock {
    private final boolean tinted;
    private final boolean inverted;

    public SolidGlassBlock(boolean tinted, boolean inverted, Settings settings) {
        super(settings);
        this.tinted = tinted;
        this.inverted = inverted;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof PlayerEntity playerEntity) {
            if (!playerEntity.isSneaking()) {
                return getShape(false);
            }
        }
        return getShape(true);
    }

    private VoxelShape getShape(boolean filled) {
        if (inverted) {
            filled = !filled;
        }
        if (filled) {
            return VoxelShapes.fullCube();
        } else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !tinted && super.isTranslucent(state, world, pos);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return tinted ? world.getMaxLightLevel() : super.getOpacity(state, world, pos);
    }
}