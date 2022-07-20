package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {
    @Shadow @Final private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    @Unique
    private final FlagBlockEntity renderFlag = new FlagBlockEntity(BlockPos.ORIGIN, PeculiarBlocks.FLAG.getDefaultState());

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        Item item = stack.getItem();
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof FlagBlock) {
                this.renderFlag.readFrom(stack);
                this.blockEntityRenderDispatcher.renderEntity(this.renderFlag, matrices, vertexConsumers, light, overlay);
                ci.cancel();
            }
        }
    }
}