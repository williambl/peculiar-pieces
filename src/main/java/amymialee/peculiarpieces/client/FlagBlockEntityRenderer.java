package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPiecesClient;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {
    public static final String FLAG = "flag";
    private final ModelPart flag;

    public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(PeculiarPiecesClient.FLAG);
        this.flag = modelPart.getChild(FLAG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(FLAG, ModelPartBuilder.create().uv(0, 0).cuboid(0f, 0f, 0f, 22f, 14f, 1f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(FlagBlockEntity flagBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        long l;
        boolean bl = flagBlockEntity.getWorld() == null;
        matrixStack.push();
        if (bl) {
            l = 0L;
            matrixStack.translate(0.5, 0.5, 0.5);
        } else {
            l = flagBlockEntity.getWorld().getTime();
            BlockState blockState = flagBlockEntity.getCachedState();
            if (blockState.getBlock() instanceof FlagBlock) {
                matrixStack.translate(0.5, 0.5, 0.5);
                float h = (float)(-blockState.get(FlagBlock.ROTATION) * 360) / 16.0f;
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            }
        }
        matrixStack.push();
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        BlockPos blockPos = flagBlockEntity.getPos();
        float k = ((float)Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + l, 100L) + f) / 100.0f;
        this.flag.pitch = (-0.0125f + 0.01f * MathHelper.cos((float)Math.PI * 2 * k)) * (float)Math.PI;
        this.flag.pivotY = -32.0f;
        renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.flag, ModelLoader.BANNER_BASE);
        matrixStack.pop();
        matrixStack.pop();
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite) {
        renderCanvas(matrices, vertexConsumers, light, overlay, canvas, baseSprite, false);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean glint) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, glint), light, overlay);
    }
}