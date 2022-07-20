package amymialee.peculiarpieces.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

public interface ExtraPlayerDataWrapper {
    int getGameModeDuration();
    void setGameModeDuration(int duration);
    GameMode getStoredGameMode();
    void setStoredGameMode(GameMode gameMode);
    Vec3d getCheckpointPos();
    void setCheckpointPos(Vec3d vec3d);
    double getBouncePower();
    void setBouncePower(double bouncePower);
}