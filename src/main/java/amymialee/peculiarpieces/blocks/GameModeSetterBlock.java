package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import amymialee.visiblebarriers.VisibleBarriers;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

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
                if (player instanceof ExtraPlayerDataWrapper wrapper) {
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