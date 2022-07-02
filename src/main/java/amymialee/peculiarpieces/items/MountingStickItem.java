package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MountingStickItem extends Item {
    public MountingStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity livingEntity, Hand hand) {
        if (!livingEntity.getType().isIn(PeculiarPieces.MOUNT_BLACKLIST) && !user.getItemCooldownManager().isCoolingDown(this)) {
            if (user.hasPassengers() && user.getFirstPassenger() != null) {
                if (!livingEntity.hasPassengers()) {
                    if (user.getFirstPassenger() != livingEntity) {
                        user.getFirstPassenger().startRiding(livingEntity, true);
                    } else {
                        livingEntity.dismountVehicle();
                    }
                }
            } else {
                livingEntity.dismountVehicle();
                if (livingEntity != user) {
                    livingEntity.startRiding(user, true);
                }
            }
            user.getItemCooldownManager().set(this, 4);
        }
        return super.useOnEntity(stack, user, livingEntity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        if (user != null) {
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                Entity passenger = user.getFirstPassenger();
                if (passenger != null) {
                    passenger.dismountVehicle();
                    WarpManager.queueTeleport(passenger, context.getHitPos());
                }
                user.getItemCooldownManager().set(this, 4);
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking() && user.hasPassengers()) {
            user.removeAllPassengers();
        }
        return super.use(world, user, hand);
    }
}