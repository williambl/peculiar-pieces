package amymialee.peculiarpieces.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity {
    @Shadow @Final private static TrackedData<Boolean> IGNITED;
    @SuppressWarnings("WrongEntityDataParameterClass")
    private static final TrackedData<Boolean> DEFUSED = DataTracker.registerData(CreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$ExplosionCancel(CallbackInfo ci) {
        if (this.dataTracker.get(DEFUSED)) ci.cancel();
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void PeculiarPieces$DefuseTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(DEFUSED, false);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void PeculiarPieces$DefuseWrite(NbtCompound nbt, CallbackInfo ci) {
        if (this.dataTracker.get(DEFUSED)) {
            nbt.putBoolean("defused", true);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void PeculiarPieces$DefuseRead(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(DEFUSED, nbt.getBoolean("defused"));
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$ExtraInteractions(PlayerEntity player2, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS) && !this.dataTracker.get(DEFUSED)) {
            if (!this.world.isClient) {
                this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.dataTracker.set(DEFUSED, true);
                ItemEntity itemEntity = this.dropStack(new ItemStack(Items.STRING), 1);
                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
                }
                this.emitGameEvent(GameEvent.SHEAR, player2);
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        } else if (itemStack.isOf(Items.STRING) && this.dataTracker.get(DEFUSED)) {
            if (!this.world.isClient) {
                this.world.playSoundFromEntity(null, this, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.dataTracker.set(DEFUSED, false);
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        } else if (itemStack.isOf(Items.WATER_BUCKET) && this.dataTracker.get(IGNITED)) {
            if (!this.world.isClient) {
                itemStack.decrement(1);
                player2.setStackInHand(hand, BucketItem.getEmptiedStack(itemStack, player2));
                this.world.playSoundFromEntity(null, this, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.dataTracker.set(IGNITED, false);
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}