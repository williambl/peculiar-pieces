package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract boolean isClimbing();

    @Shadow public abstract boolean isHoldingOntoLadder();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"))
    public float PeculiarPieces$SlipperyShoesSlipping(float p) {
        if (((Entity) this) instanceof LivingEntity livingEntity) {
            Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
            if (optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.SLIPPERY_SHOES)) {
                return 1f / 0.91f;
            }
            if (optionalComponent.isPresent() && optionalComponent.get().isEquipped(PeculiarItems.STEADY_SNEAKERS)) {
                return 0.5f;
            }
        }
        return p;
    }

    @Inject(method = "applyClimbingSpeed", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$MoreScaffolds(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        if (this.isClimbing()) {
            this.onLanding();
            double d = MathHelper.clamp(motion.x, -0.15f, 0.15f);
            double e = MathHelper.clamp(motion.z, -0.15f, 0.15f);
            double g = Math.max(motion.y, -0.15f);
            if (g < 0.0 && !this.getBlockStateAtPos().isIn(PeculiarPieces.SCAFFOLDING)) {
                this.isHoldingOntoLadder();
            }
            motion = new Vec3d(d, g, e);
        }
        cir.setReturnValue(motion);
    }

    @Inject(method = "tryUseTotem", at = @At("RETURN"), cancellable = true)
    private void PeculiarPieces$TotemTrinkets(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            if (source.isOutOfWorld()) {
                return;
            }
            if (((Entity) this) instanceof LivingEntity livingEntity) {
                Optional<TrinketComponent> optionalComponent = TrinketsApi.getTrinketComponent(livingEntity);
                if (optionalComponent.isPresent()) {
                    if (optionalComponent.get().isEquipped(PeculiarItems.TOKEN_OF_UNDYING)) {
                        List<Pair<SlotReference, ItemStack>> equipped = optionalComponent.get().getEquipped(PeculiarItems.TOKEN_OF_UNDYING);
                        ItemStack stack = equipped.get(0).getRight();
                        useTotem(livingEntity, stack);
                        stack.decrement(1);
                        cir.setReturnValue(true);
                    } else if (optionalComponent.get().isEquipped(PeculiarItems.EVERLASTING_EMBLEM)) {
                        if (livingEntity instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(PeculiarItems.EVERLASTING_EMBLEM)) {
                            List<Pair<SlotReference, ItemStack>> equipped = optionalComponent.get().getEquipped(PeculiarItems.EVERLASTING_EMBLEM);
                            ItemStack stack = equipped.get(0).getRight();
                            useTotem(player, stack);
                            player.getItemCooldownManager().set(PeculiarItems.EVERLASTING_EMBLEM, (source.getAttacker() instanceof PlayerEntity ? 2 : 1) * 6 * 60 * 20);
                            cir.setReturnValue(true);
                        }
                    }
                }
            }
        }
    }

    private void useTotem(LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
            Criteria.USED_TOTEM.trigger(serverPlayer, stack);
        }
        this.setHealth(1.0f);
        this.clearStatusEffects();
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10 * 20, 3));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 16 * 20, 3));
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 32 * 20, 0));
        this.world.sendEntityStatus(this, EntityStatuses.USE_TOTEM_OF_UNDYING);
    }
}