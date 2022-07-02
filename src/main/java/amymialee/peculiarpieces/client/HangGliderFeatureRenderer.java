package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.PeculiarPiecesClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HangGliderFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = PeculiarPieces.id("textures/entity/hang_glider.png");
    private final HangGliderEntityModel<T> glider;

    public HangGliderFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.glider = new HangGliderEntityModel<>(loader.getModelPart(PeculiarPiecesClient.HANG_GLIDER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        if (livingEntity.getMainHandStack().getOrCreateNbt().getBoolean("pp:gliding") || livingEntity.getOffHandStack().getOrCreateNbt().getBoolean("pp:gliding")) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 0.125);
            this.getContextModel().copyStateTo(this.glider);
            this.glider.setAngles(livingEntity, f, g, j, k, l);
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(SKIN), false, false);
            this.glider.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }
}