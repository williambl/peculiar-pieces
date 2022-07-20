package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.entity.TeleportItemEntity;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;

public class TeleportItemEntityRenderer extends EntityRenderer<TeleportItemEntity> {
    private final ItemRenderer itemRenderer;
    private final Random random = Random.create();
    private static final ItemStack paper;

    public TeleportItemEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
    }

    @Override
    public void render(TeleportItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        ItemStack itemStack = paper;
        if (itemEntity.getDelay() == 0) {
            ItemStack input = itemEntity.getStack();
            if (input != null && !input.isEmpty()) {
                itemStack = itemEntity.getStack();
            } else {
                itemStack = Items.DIAMOND.getDefaultStack();
            }
        }
        int j = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed(j);
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, itemEntity.world, null, itemEntity.getId());
        float l = MathHelper.sin(((float) itemEntity.age + g) / 10.0f) * 0.1f + 0.1f;
        float m = bakedModel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        matrixStack.translate(0.0, l + 0.25f * m, 0.0);
        float n = itemEntity.getRotation(g);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n));
        float o = bakedModel.getTransformation().ground.scale.getX();
        float p = bakedModel.getTransformation().ground.scale.getY();
        float q = bakedModel.getTransformation().ground.scale.getZ();
        matrixStack.push();
        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV, bakedModel);
        matrixStack.pop();
        matrixStack.translate(0.0f * o, 0.0f * p, 0.09375f * q);
        matrixStack.pop();
        super.render(itemEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(TeleportItemEntity itemEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    static {
        paper = PeculiarItems.POS_TRAP.getDefaultStack();
        paper.addEnchantment(Enchantments.FIRE_ASPECT, 1);
    }
}