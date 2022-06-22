package amymialee.peculiarpieces.util;

import net.minecraft.world.GameMode;

public interface GameModePlayerWrapper {
    int getGameModeDuration();
    void setGameModeDuration(int duration);
    GameMode getStoredGameMode();
    void setStoredGameMode(GameMode gameMode);
}
