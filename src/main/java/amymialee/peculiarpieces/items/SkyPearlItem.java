package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public class SkyPearlItem extends Item {
    public SkyPearlItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        WarpManager.queueTeleport(user, new Vec3d(user.getX(), world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, user.getBlockPos()).getY(), user.getZ()));
        user.getItemCooldownManager().set(this, 4);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}