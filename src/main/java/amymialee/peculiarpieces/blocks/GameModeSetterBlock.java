package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.GameModePlayerWrapper;
import amymialee.visiblebarriers.VisibleBarriers;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class GameModeSetterBlock extends AbstractStructureVoidBlock {
    private final GameMode gameMode;

    public GameModeSetterBlock(GameMode gameMode, FabricBlockSettings settings) {
        super(settings);
        this.gameMode = gameMode;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isClient()) {
            if (entity instanceof ServerPlayerEntity player) {
                if (player instanceof GameModePlayerWrapper wrapper) {
                    if (gameMode == wrapper.getStoredGameMode()) {
                        return;
                    }
                    GameMode playerMode = player.interactionManager.getGameMode();
                    if (playerMode != gameMode && playerMode != GameMode.SPECTATOR && playerMode != GameMode.CREATIVE) {
                        if (wrapper.getGameModeDuration() == 0) {
                            wrapper.setStoredGameMode(playerMode);
                        }
                        player.changeGameMode(gameMode);
                        wrapper.setGameModeDuration(3);
                        return;
                    }
                    if (wrapper.getGameModeDuration() > 0) {
                        wrapper.setGameModeDuration(3);
                    }
                }
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            if (MinecraftClient.getInstance().player != null) {
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state), 0.5, 0.5, 0.5, 0.0, 0.0, 0.0);
            }
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state), 0.5, 0.5, 0.5, 0.0, 0.0, 0.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (VisibleBarriers.isVisible()) return BlockRenderType.MODEL;
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }
}