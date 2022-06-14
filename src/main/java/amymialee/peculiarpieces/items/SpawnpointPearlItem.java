package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Optional;

public class SpawnpointPearlItem extends Item {
    public SpawnpointPearlItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (world instanceof ServerWorld serverWorld) {
                ServerPlayerEntity player = ((ServerPlayerEntity) user);
                Optional<Vec3d> spawnpoint = PlayerEntity.findRespawnPosition(serverWorld, player.getSpawnPointPosition(), 0, false, true);

                if (spawnpoint.isPresent()) {
                    RegistryKey<World> spawnDim = player.getSpawnPointDimension();
                    if (spawnDim != player.getWorld().getRegistryKey()) {
                        ServerWorld level = serverWorld.getServer().getWorld(spawnDim);
                        if (!(level == null)) {
                            player.moveToWorld(level);
                        }
                    }
                    WarpManager.queueTeleport(user, spawnpoint.get());
                } else {
                    WarpManager.queueTeleport(user, serverWorld.getSpawnPos());
                }
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        user.getItemCooldownManager().set(this, 2);
        return TypedActionResult.consume(stack);
    }
}
