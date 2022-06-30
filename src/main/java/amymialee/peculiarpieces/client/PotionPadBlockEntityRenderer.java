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
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class PotionPadBlockEntityRenderer implements BlockEntityRenderer<PotionPadBlockEntity> {
    public static final SpriteIdentifier POTION_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, PeculiarPieces.id("entity/bell/bell_body"));//PeculiarPieces.id("textures/block/potion_pad_potion"));
    public static final SpriteIdentifier MILK_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("entity/bell/bell_body"));//PeculiarPieces.id("block/potion_pad_milk"));
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
        System.out.println(potionPadBlock.getStack(0));
        if (potionPadBlock.getPotion().getItem() instanceof MilkBucketItem) {
            vertexConsumer = MILK_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);
        } else {
            vertexConsumer = POTION_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);
            vertexConsumer.color(PotionUtil.getColor(potionPadBlock.getStack(0)));
        }
        this.potionBody.render(matrixStack, vertexConsumer, i, j);
    }
}

