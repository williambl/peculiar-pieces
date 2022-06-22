package amymialee.peculiarpieces.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Collections;

public class WarpManager {
    private static final ArrayList<Pair<Entity, Vec3d>> dueTeleports = new ArrayList<>();

    public static void tick() {
        for (int i = 0; i < dueTeleports.size();) {
            Pair<Entity, Vec3d> pair = dueTeleports.get(i);
            Entity entity = pair.getLeft();
            Vec3d pos = pair.getRight();
            entity.dismountVehicle();
            if (entity instanceof LivingEntity livingEntity) {
                teleport(livingEntity, pos.x, pos.y, pos.z, true);
            } else {
                entity.teleport(pos.x, pos.y, pos.z);
            }
            if (pos instanceof EntityPos entityPos) {
                if (entity instanceof ServerPlayerEntity playerEntity) {
                    playerEntity.setHeadYaw(entityPos.yaw);
                    playerEntity.setPitch(entityPos.pitch);
                    playerEntity.networkHandler.sendPacket(new PlayerPositionLookS2CPacket(pos.x, pos.y, pos.z, entityPos.yaw, entityPos.pitch, Collections.emptySet(), 0, true));
                }
            }
            dueTeleports.remove(pair);
        }
    }

    public static void queueTeleport(Entity entity, Vec3d pos) {
        dueTeleports.add(new Pair<>(entity, pos));
    }

    public static void queueTeleport(Entity entity, BlockPos pos) {
        dueTeleports.add(new Pair<>(entity, Vec3d.ofBottomCenter(pos)));
    }

    public static void teleport(Entity entity, double x, double y, double z, boolean particleEffects) {
        if (particleEffects) {
            entity.world.sendEntityStatus(entity, (byte)46);
        }
        entity.requestTeleport(x, y, z);
        if (particleEffects) {
            entity.world.sendEntityStatus(entity, (byte)46);
        }
        if (entity instanceof PathAwareEntity) {
            ((PathAwareEntity)entity).getNavigation().stop();
        }
    }
}