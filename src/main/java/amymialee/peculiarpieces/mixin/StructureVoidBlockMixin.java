package amymialee.peculiarpieces.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StructureVoidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureVoidBlock.class)
public class StructureVoidBlockMixin extends Block {
    public StructureVoidBlockMixin(Settings settings) {
        super(settings);
    }

    @Unique
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.isHolding(this.asItem())) {
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state), 0.5, 0.5, 0.5, 0.0, 0.0, 0.0);
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }
}
