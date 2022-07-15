package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.mixin.EntityAccessor;
import amymialee.peculiarpieces.mixin.EntityBucketItemAccessor;
import amymialee.peculiarpieces.mixin.EntityRenderDispatcherAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.CodEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PufferfishEntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.render.entity.TropicalFishEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Bucketable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class FishTankBlockEntityRenderer implements BlockEntityRenderer<FishTankBlockEntity> {
    private Entity entity;
    private ItemStack cachedStack;

    public FishTankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FishTankBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (blockEntity.getStack(0) != cachedStack) {
            cachedStack = blockEntity.getStack(0);
            entity = null;
        }
        if (cachedStack != null && cachedStack.getItem() instanceof EntityBucketItem entityBucketItem) {
            EntityType<?> type = ((EntityBucketItemAccessor) entityBucketItem).getEntityType();
            if (entity == null) {
                entity = type.create(blockEntity.getWorld());
                if (entity != null) {
                    BlockPos pos = blockEntity.getPos();
                    entity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), blockEntity.yaw, 0);
                    if (entity instanceof WaterCreatureEntity) {
                        ((EntityAccessor) entity).setTouchingWater(true);
                    }
                    if (entity instanceof Bucketable bucketable) {
                        bucketable.copyDataFromNbt(cachedStack.getOrCreateNbt());
                        bucketable.setFromBucket(true);
                    }
                }
            } else if (blockEntity.getWorld() != null) {
                //entity.age = ((int) blockEntity.getWorld().getTime());
                EntityRenderer<?> entityRenderer = ((EntityRenderDispatcherAccessor) MinecraftClient.getInstance().getEntityRenderDispatcher()).getRenderers().get(entity.getType());
                float yaw = blockEntity.yaw;
                if (entityRenderer instanceof CodEntityRenderer fishRenderer && entity instanceof CodEntity fish) {
                    fishRenderer.render(fish, yaw, tickDelta, matrices, vertexConsumers, light);
                } else if (entityRenderer instanceof SalmonEntityRenderer fishRenderer && entity instanceof SalmonEntity fish) {
                    fishRenderer.render(fish, yaw, tickDelta, matrices, vertexConsumers, light);
                } else if (entityRenderer instanceof PufferfishEntityRenderer fishRenderer && entity instanceof PufferfishEntity fish) {
                    fishRenderer.render(fish, yaw, tickDelta, matrices, vertexConsumers, light);
                } else if (entityRenderer instanceof TropicalFishEntityRenderer fishRenderer && entity instanceof TropicalFishEntity fish) {
                    fishRenderer.render(fish, yaw, tickDelta, matrices, vertexConsumers, light);
                }
            }
        }
        matrices.pop();
    }
}