package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.PeculiarPiecesClient;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class FlagBlockEntityRenderer implements BlockEntityRenderer<FlagBlockEntity> {
    public static final Identifier TEXTURE = PeculiarPieces.id("textures/entity/flags/base_flag.png");
    public static final Identifier FLAGPOLE = PeculiarPieces.id("textures/entity/flags/flagpole.png");
    public static final String FLAG = "flag";
    private static final String POLE = "pole";
    private final ModelPart flag;
    private final ModelPart pole;
    private boolean init = true;

    public FlagBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(PeculiarPiecesClient.FLAG);
        this.flag = modelPart.getChild(FLAG);
        this.pole = modelPart.getChild(POLE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(FLAG, ModelPartBuilder.create().uv(0, 0).cuboid(0f, -7f, -0.5f, 22f, 14f, 1f), ModelTransform.NONE);
        modelPartData.addChild(POLE, ModelPartBuilder.create().uv(0, 0).cuboid(-1.0f, -8.0f, -1.0f, 2.0f, 16.0f, 2.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 16);
    }

    @Override
    public void render(FlagBlockEntity flagBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrixStack.push();
        BlockState blockState = flagBlockEntity.getCachedState();
        long l = 0L;
        boolean walled = false;
        if (flagBlockEntity.getWorld() == null) {
            matrixStack.translate(0.5, 0.5, 0.5);
        } else {
            l = flagBlockEntity.getWorld().getTime();
            if (blockState.getBlock() instanceof FlagBlock) {
                matrixStack.translate(0.5, 0.5, 0.5);
                if (blockState.get(FlagBlock.FACE) == FlagBlock.FlagMountLocation.WALL) {
                    Direction direction = FlagBlock.getDirection(blockState);
                    if (direction.getAxis() == Direction.Axis.X) {
                        direction = direction.getOpposite();
                    }
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(direction.asRotation() + 90));
                    this.flag.roll = (float) Math.toRadians(202.5f);
                    this.flag.pivotX = 6;
                    this.flag.pitch = (float) Math.toRadians(0f);
                    this.pole.roll = (float) Math.toRadians(202.5f);
                    this.pole.pivotX = 6;
                    this.pole.pivotY = 1;
                    this.pole.pitch = (float) Math.toRadians(0f);
                    walled = true;
                } else if (blockState.get(FlagBlock.FACE) == FlagBlock.FlagMountLocation.FLAT) {
                    Direction direction = FlagBlock.getDirection(blockState);
                    if (direction.getAxis() == Direction.Axis.X) {
                        direction = direction.getOpposite();
                    }
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(direction.asRotation() + 90));
                    this.flag.yaw = (float) Math.toRadians(90f);
                    this.flag.roll = (float) Math.toRadians(180f);
                    this.flag.pivotX = 7.5f;
                    this.flag.pivotZ = 11f;
                    walled = true;
                    this.pole.visible = false;
                } else {
                    float h = (float) (-blockState.get(FlagBlock.ROTATION) * 360) / 16.0f;
                    matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
                }
            }
        }
        matrixStack.push();
        BlockPos blockPos = flagBlockEntity.getPos();
        if (blockState.get(FlagBlock.FACE) != FlagBlock.FlagMountLocation.FLAT) {
            float k = ((float) Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + l, 100L) + f) / 100.0f;
            switch (blockState.get(FlagBlock.FACE)) {
                case FLOOR -> this.flag.pivotY = 0.5f;
                case CEILING -> this.flag.pivotY = -0.5f;
                default -> this.flag.pivotY = 1.5f;
            }
            this.flag.yaw = (0.01f * MathHelper.cos((float) Math.PI * 2 * k)) * (float) Math.PI;
        } else {
            float k = ((float) Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + l, 100L) + f) / 100.0f;
            this.flag.pivotY = 0f;
            this.flag.pitch = (0.01f * MathHelper.cos((float) Math.PI * 2 * k)) * (float) Math.PI / 2;
        }
        String textureName = flagBlockEntity.getTexture();
        if (textureName != null) {
            textureName = textureName.toLowerCase();
        }
        float[] components = {1f, 1f, 1f};
        DyeColor color = DyeColor.byName(textureName, null);
        if (color != null) {
            components = color.getColorComponents();
        }
        Identifier flag = TEXTURE;
        if (textureName != null && Identifier.isValid(textureName)) {
            Identifier identifier = PeculiarPieces.id("textures/entity/flags/%s_flag.png".formatted(textureName));
            if (MinecraftClient.getInstance().getResourceManager().getResource(identifier).isPresent()) {
                flag = identifier;
            }
        }
        {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntitySolid(flag), true, false);
            this.flag.render(matrixStack, vertexConsumer, light, overlay, components[0], components[1], components[2], 1.0f);
        }
        {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getEntitySolid(FLAGPOLE), true, false);
            this.pole.render(matrixStack, vertexConsumer, light, overlay);
        }
        matrixStack.pop();
        matrixStack.pop();
        if (walled || init) {
            this.flag.pitch = (float) Math.toRadians(180f);
            this.flag.yaw = 0;
            this.flag.roll = 0;
            this.flag.pivotX = 0;
            this.flag.pivotY = 0;
            this.flag.pivotZ = 0;
            this.pole.visible = true;
            this.pole.pitch = 0;
            this.pole.roll = 0;
            this.pole.pivotX = 0;
            this.pole.pivotY = 0;
            this.pole.pivotZ = 0;
            init = false;
        }
    }
}