package amymialee.peculiarpieces.blocks;

import amymialee.visiblebarriers.VisibleBarriers;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class GameModeBarrierBlock extends Block {
    private final GameMode gameMode;

    public GameModeBarrierBlock(GameMode gameMode, FabricBlockSettings settings) {
        super(settings);
        this.gameMode = gameMode;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isHolding(this.asItem())) {
            return VoxelShapes.fullCube();
        }
        if (VisibleBarriers.isVisible()) {
            return VoxelShapes.fullCube();
        }
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getGameProfile().getId());
                if (playerListEntry != null && playerListEntry.getGameMode() == gameMode) {
                    return VoxelShapes.fullCube();
                }
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null) {
            PlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null) {
                PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getGameProfile().getId());
                if (playerListEntry != null && playerListEntry.getGameMode() == gameMode) {
                    return VoxelShapes.fullCube();
                }
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!world.isClient()) {
            if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.isHolding(this.asItem())) {
                world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK_MARKER, state), 0.5, 0.5, 0.5, 0.0, 0.0, 0.0);
            }
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