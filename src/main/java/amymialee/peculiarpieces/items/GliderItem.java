package amymialee.peculiarpieces.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GliderItem extends Item {
    public GliderItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        toggle(stack);
        return TypedActionResult.success(stack);
    }

    private void toggle(ItemStack stack) {
        NbtCompound compound = stack.getOrCreateNbt();
        compound.putBoolean("pp:gliding", !compound.getBoolean("pp:gliding"));
    }

    public static boolean hasGlider(PlayerEntity player) {
        return player.getMainHandStack().getOrCreateNbt().getBoolean("pp:gliding") || player.getOffHandStack().getOrCreateNbt().getBoolean("pp:gliding");
    }

    public static boolean isGliding(PlayerEntity player) {
        if (!player.isOnGround() && !player.isSubmergedInWater() && !player.isSleeping()) {
            return hasGlider(player);
        }
        return false;
    }

    public static boolean isDescending(PlayerEntity player) {
        Vec3d velocity = player.getVelocity();
        return velocity.getY() < 0;
    }
}