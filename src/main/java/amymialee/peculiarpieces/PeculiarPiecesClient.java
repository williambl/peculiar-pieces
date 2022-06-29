package amymialee.peculiarpieces;

import amymialee.peculiarpieces.items.TransportPearlItem;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarItems;
import amymialee.peculiarpieces.screens.WarpScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.MathHelper;

public class PeculiarPiecesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_REMOVER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_RETURNER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.CHECKPOINT_DAMAGER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLATE_HEAVY, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLATE_LIGHT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.INVISIBLE_PLAYER_PLATE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_CLAMP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_FILTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_HURDLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_STATIC, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_MONO, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.REDSTONE_RANDOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_BLOCKER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_BLOCKER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ADVENTURE_SETTER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SURVIVOR_SETTER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TORCH_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.SOUL_TORCH_LEVER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTITY_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TINTED_ENTITY_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.PLAYER_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.TINTED_PLAYER_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTITY_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.PLAYER_BARRIER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(PeculiarBlocks.ENTANGLED_SCAFFOLDING, RenderLayer.getCutout());
        HandledScreens.register(PeculiarPieces.WARP_SCREEN_HANDLER, WarpScreen::new);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> MathHelper.hsvToRgb(((float)(TransportPearlItem.getSlot(stack) + 1) / 8), 1.0F, 1.0F), PeculiarItems.TRANS_PEARL);
    }
}