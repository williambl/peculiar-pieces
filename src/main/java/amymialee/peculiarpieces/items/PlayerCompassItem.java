package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlayerCompassItem extends Item {
    public static final String PLAYER_KEY = "pp:player";
    public static final String PLAYER_POS_KEY = "pp:pos";

    public PlayerCompassItem(Item.Settings settings) {
        super(settings);
    }

    @Nullable
    public static GlobalPos createPlayerPos(World world, NbtCompound nbt) {
        if (nbt.contains(PLAYER_POS_KEY)) {
            BlockPos blockPos = NbtHelper.toBlockPos(nbt.getCompound(PLAYER_POS_KEY));
            return GlobalPos.create(world.getRegistryKey(), blockPos);
        }
        return null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            ItemStack stack = user.getStackInHand(hand);
            user.getItemCooldownManager().set(stack.getItem(), 120);
            if (stack.getItem() instanceof PlayerCompassItem) {
                Vec3d vec3d = user.getCameraPosVec(1);
                Vec3d vec3d2 = user.getRotationVec(1.0f);
                Vec3d vec3d3 = vec3d.add(vec3d2.x * 8, vec3d2.y * 8, vec3d2.z * 8);
                Box box = user.getBoundingBox().stretch(vec3d2.multiply(8)).expand(1.0, 1.0, 1.0);
                EntityHitResult entityHitResult = ProjectileUtil.raycast(user, vec3d, vec3d3, box, entity -> entity instanceof PlayerEntity && !entity.isSpectator() && entity.collides(), 8);
                if (entityHitResult != null) {
                    if (entityHitResult.getEntity() instanceof PlayerEntity player) {
                        if (!world.isClient()) {
                            stack.getOrCreateNbt().putUuid(PLAYER_KEY, player.getUuid());
                            return TypedActionResult.success(stack);
                        }
                    }
                }
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().contains(PLAYER_POS_KEY);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient || !(entity.age % 20 == 0)) {
            return;
        }
        NbtCompound compound = stack.getOrCreateNbt();
        if (compound.contains(PLAYER_KEY)) {
            LivingEntity trackedPlayer = world.getPlayerByUuid(compound.getUuid(PLAYER_KEY));
            if (trackedPlayer != null && !trackedPlayer.hasStatusEffect(StatusEffects.INVISIBILITY) && !trackedPlayer.hasStatusEffect(PeculiarPieces.CONCEALMENT_EFFECT)) {
                compound.put(PLAYER_POS_KEY, NbtHelper.fromBlockPos(trackedPlayer.getBlockPos()));
            } else {
                compound.remove(PLAYER_POS_KEY);
            }
        }
    }
}