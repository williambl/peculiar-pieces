package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.callbacks.PlayerCrouchCallback;
import amymialee.peculiarpieces.callbacks.PlayerJumpCallback;
import amymialee.peculiarpieces.util.CheckpointPlayerWrapper;
import amymialee.peculiarpieces.util.GameModePlayerWrapper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements CheckpointPlayerWrapper, GameModePlayerWrapper {
    @Unique Vec3d checkpointPos;
    @Unique boolean wasSneaky = false;
    @Unique int gameModeDuration = 0;
    @Unique GameMode storedGameMode = null;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override public Vec3d getCheckpointPos() {
        return checkpointPos;
    }

    @Override public void setCheckpointPos(Vec3d vec3d) {
        checkpointPos = vec3d;
    }

    @Override public int getGameModeDuration() {
        return gameModeDuration;
    }

    @Override public void setGameModeDuration(int duration) {
        gameModeDuration = duration;
    }

    @Override public GameMode getStoredGameMode() {
        return storedGameMode;
    }

    @Override public void setStoredGameMode(GameMode gameMode) {
        storedGameMode = gameMode;
    }

    @Unique
    public double getMountedHeightOffset() {
        return ((EntityAccessor) this).getDimensions().height * 0.9f;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void PeculiarPieces$WriteCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (checkpointPos != null && checkpointPos.distanceTo(Vec3d.ZERO) > 1) {
            nbt.put("pp:checkpos", NbtHelper.fromBlockPos(new BlockPos(checkpointPos)));
        }
        if (storedGameMode != null) {
            nbt.putInt("pp:gamemode", storedGameMode.getId() + 1);
        }
        if (gameModeDuration != 0) {
            nbt.putInt("pp:gamemode_duration", gameModeDuration);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void PeculiarPieces$ReadCustomData(NbtCompound nbt, CallbackInfo ci) {
        Vec3d vec3d = Vec3d.ofBottomCenter(NbtHelper.toBlockPos(nbt.getCompound("pp:checkpos")));
        if (vec3d.distanceTo(Vec3d.ZERO) > 1) {
            checkpointPos = vec3d;
        }
        int gameMode = nbt.getInt("pp:gamemode");
        if (gameMode != 0) {
            storedGameMode = GameMode.byId(gameMode - 1);
        } else {
            storedGameMode = null;
        }
        gameModeDuration = nbt.getInt("pp:gamemode_duration");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {
        if (((PlayerEntity) ((Object) this)) instanceof ServerPlayerEntity serverPlayer) {
            if (gameModeDuration > 0) {
                gameModeDuration--;
                if (gameModeDuration == 0) {
                    if (storedGameMode != null) {
                        serverPlayer.changeGameMode(storedGameMode);
                        storedGameMode = null;
                    }
                }
            }
        }
    }

    @Inject(method = "jump", at = @At("TAIL"))
    public void PeculiarPieces$JumpCallback(CallbackInfo ci) {
        if (!world.isClient()) {
            PlayerJumpCallback.EVENT.invoker().onJump(((PlayerEntity) ((Object) this)), world);
        }
    }

    @Unique
    public boolean isSneaking() {
        if (!world.isClient()) {
            boolean sneaking = super.isSneaking();
            if (sneaking != wasSneaky) {
                if (sneaking) {
                    wasSneaky = true;
                    PlayerCrouchCallback.EVENT.invoker().onCrouch(((PlayerEntity) ((Object) this)), world);
                } else {
                    wasSneaky = false;
                }
            }
            return sneaking;
        } else {
            return super.isSneaking();
        }
    }
}