package amymialee.peculiarpieces.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class WarpInstance {
    private final Entity entity;
    @Nullable
    private Vec3d position;
    private boolean hasPitch;
    private float pitch;
    private boolean hasYaw;
    private float yaw;
    private boolean particles;

    public WarpInstance(Entity entity) {
        this.entity = entity;
    }

    public static WarpInstance of(Entity entity) {
        return new WarpInstance(entity);
    }

    public WarpInstance position(Vec3d position) {
        this.position = position;
        return this;
    }

    public WarpInstance position(BlockPos position) {
        this.position = Vec3d.ofBottomCenter(position);
        return this;
    }

    public WarpInstance x(double x) {
        if (position == null) {
            position = new Vec3d(x, 0, 0);
        } else {
            position = new Vec3d(x, position.y, position.z);
        }
        return this;
    }

    public WarpInstance y(double y) {
        if (position == null) {
            position = new Vec3d(0, y, 0);
        } else {
            position = new Vec3d(position.x, y, position.z);
        }
        return this;
    }

    public WarpInstance z(double z) {
        if (position == null) {
            position = new Vec3d(0, 0, z);
        } else {
            position = new Vec3d(position.x, position.y, z);
        }
        return this;
    }

    public WarpInstance pitch(float pitch) {
        this.hasPitch = true;
        this.pitch = pitch;
        return this;
    }

    public WarpInstance yaw(float yaw) {
        this.hasYaw = true;
        this.yaw = yaw;
        return this;
    }

    public WarpInstance particles() {
        this.particles = true;
        return this;
    }

    public Entity getEntity() {
        return entity;
    }

    public @Nullable Vec3d getPosition() {
        return position;
    }

    public boolean hasPitch() {
        return hasPitch;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean hasYaw() {
        return hasYaw;
    }

    public float getYaw() {
        return yaw;
    }

    public boolean hasParticles() {
        return particles;
    }
}