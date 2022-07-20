package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity {
    protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$ExtraInteractions(PlayerEntity player2, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (this.isBaby()) {
            if (!this.world.isClient) {
                if (this.getCustomName() != null && this.getCustomName().toString().contains("Engineer")) {
                    this.world.playSoundFromEntity(null, this, PeculiarPieces.ENTITY_SHEEP_YIPPEE_ENGINEER, SoundCategory.PLAYERS, getSoundVolume(), getSoundPitch());
                } else {
                    this.world.playSoundFromEntity(null, this, PeculiarPieces.ENTITY_SHEEP_YIPPEE, SoundCategory.PLAYERS, getSoundVolume(), getSoundPitch());
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}