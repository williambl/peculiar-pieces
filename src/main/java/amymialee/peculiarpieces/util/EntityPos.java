package amymialee.peculiarpieces.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public class EntityPos extends Vec3d {
    float pitch;
    float yaw;

    public EntityPos(double x, double y, double z, float pitch, float yaw) {
        super(x, y, z);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public EntityPos(Vec3f vec, float pitch, float yaw) {
        super(vec);
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public EntityPos(BlockPos vec, float pitch, float yaw) {
        super((double)vec.getX() + 0.5, vec.getY(), (double)vec.getZ() + 0.5);
        this.pitch = pitch;
        this.yaw = yaw;
    }
}