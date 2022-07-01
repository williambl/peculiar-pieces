package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.PeculiarPiecesClient;
import amymialee.peculiarpieces.blockentities.PotionPadBlockEntity;
import amymialee.peculiarpieces.blocks.PotionPadBlock;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.PotionUtil;

public class PotionPadBlockEntityRenderer implements BlockEntityRenderer<PotionPadBlockEntity> {
    private final ModelPart potionBody;

    public PotionPadBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(PeculiarPiecesClient.POTION_PAD_LAYER);
        this.potionBody = modelPart.getChild("potion");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        modelData.getRoot().addChild("potion", ModelPartBuilder.create().uv(0, 0).cuboid(0, 0, 0, 16, 1, 16), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void render(PotionPadBlockEntity potionPadBlock, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        if (potionPadBlock.getCachedState().get(PotionPadBlock.POWERED)) {
            return;
        }
        VertexConsumer vertexConsumer;
        if (potionPadBlock.getPotion().getItem() instanceof MilkBucketItem) {
            vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(PeculiarPieces.id("textures/block/potion_pad_milk.png")));
        } else {
            vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(PeculiarPieces.id("textures/block/potion_pad_potion.png")));
            vertexConsumer.color(PotionUtil.getColor(potionPadBlock.getStack(0)));
        }
        this.potionBody.render(matrixStack, vertexConsumer, i, j);
    }
}

