package amymialee.peculiarpieces.client;

import amymialee.kofitable.KofiTable;
import amymialee.kofitable.supporter.Supporter;
import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.PeculiarPiecesClient;
import amymialee.peculiarpieces.items.GliderItem;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HangGliderFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = PeculiarPieces.id("textures/entity/gliders/hang_glider.png");
    private final HangGliderEntityModel<T> glider;

    public HangGliderFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.glider = new HangGliderEntityModel<>(loader.getModelPart(PeculiarPiecesClient.HANG_GLIDER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l) {
        if (GliderItem.hasGlider(playerEntity)) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, (GliderItem.isGliding(playerEntity) && GliderItem.isDescending(playerEntity) ? 0 : 1) * 0.1);
            this.getContextModel().copyStateTo(this.glider);
            this.glider.setAngles(playerEntity, f, g, j, k, l);
            Identifier identifier = SKIN;
            Supporter supporter = KofiTable.CONTRIBUTORS.get(playerEntity.getUuid());
            try {
                if (supporter != null) {
                    String supportGlider = supporter.getString("peculiarpieces:glider").toLowerCase();
                    if (supporter.has("peculiarpieces:glider") && Identifier.isValid(supportGlider)) {
                        Identifier supportIdentifier = PeculiarPieces.id("textures/entity/gliders/%s_glider.png".formatted(supportGlider));
                        if (MinecraftClient.getInstance().getResourceManager().getResource(supportIdentifier).isPresent()) {
                            identifier = supportIdentifier;
                        }
                    }
                    if (supporter.getBoolean("peculiarpieces:anyglider")) {
                        ItemStack glider = GliderItem.getGlider(playerEntity);
                        if (glider != null && glider.hasCustomName()) {
                            String renameGlider = glider.getName().getString().toLowerCase().split(" ")[0];
                            if (Identifier.isValid(renameGlider)) {
                                Identifier supportIdentifier = PeculiarPieces.id("textures/entity/gliders/%s_glider.png".formatted(renameGlider));
                                if (MinecraftClient.getInstance().getResourceManager().getResource(supportIdentifier).isPresent()) {
                                    identifier = supportIdentifier;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), false, false);
            this.glider.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }
}