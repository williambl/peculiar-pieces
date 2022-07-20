package amymialee.peculiarpieces.entity;

import amymialee.peculiarpieces.items.PositionPaperItem;
import amymialee.peculiarpieces.registry.PeculiarEntities;
import amymialee.peculiarpieces.registry.PeculiarItems;
import amymialee.peculiarpieces.util.WarpInstance;
import amymialee.peculiarpieces.util.WarpManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TeleportItemEntity extends Entity {
    private static final TrackedData<ItemStack> STACK = DataTracker.registerData(TeleportItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> PEARL = DataTracker.registerData(TeleportItemEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<Integer> PICKUP_DELAY = DataTracker.registerData(TeleportItemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final int DESPAWN_AGE = 64000;
    private static final int CANNOT_PICK_UP_DELAY = Short.MAX_VALUE;
    private static final int NEVER_DESPAWN_AGE = Short.MIN_VALUE;
    private int itemAge;
    private int health = 150;
    private UUID thrower;

    public TeleportItemEntity(EntityType<? extends TeleportItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public TeleportItemEntity(World world, double x, double y, double z, ItemStack stack, ItemStack pearl, double velocityX, double velocityY, double velocityZ) {
        this(PeculiarEntities.TELEPORT_ITEM_ENTITY, world);
        this.setPosition(x, y, z);
        this.setVelocity(velocityX, velocityY, velocityZ);
        if (!stack.isEmpty()) {
            this.setStack(stack);
        }
        this.setPearl(pearl);
    }

    public TeleportItemEntity(World world, double x, double y, double z, ItemStack stack, ItemStack pearl) {
        this(world, x, y, z, stack, pearl, world.random.nextDouble() * 0.2 - 0.1, 0.2, world.random.nextDouble() * 0.2 - 0.1);
    }

    @Override
    @Nullable
    public Entity getEventSource() {
        return Util.map(this.thrower, this.world::getPlayerByUuid);
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(STACK, ItemStack.EMPTY);
        this.getDataTracker().startTracking(PEARL, ItemStack.EMPTY);
        this.getDataTracker().startTracking(PICKUP_DELAY, 0);
    }

    @Override
    public void tick() {
        super.tick();
        int delay = this.getDelay();
        if (delay > 0 && delay != CANNOT_PICK_UP_DELAY) {
            this.setDelay(delay - 1);
        }
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        Vec3d vec3d = this.getVelocity();
        float f = this.getStandingEyeHeight() - 0.11111111f;
        if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > (double)f) {
            this.applyWaterBuoyancy();
        } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double)f) {
            this.applyLavaBuoyancy();
        } else if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        if (this.world.isClient) {
            this.noClip = false;
        } else {
            this.noClip = !this.world.isSpaceEmpty(this, this.getBoundingBox().contract(1.0E-7));
            if (this.noClip) {
                this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
            }
        }
        if (!this.onGround || this.getVelocity().horizontalLengthSquared() > (double)1.0E-5f || (this.age + this.getId()) % 4 == 0) {
            this.move(MovementType.SELF, this.getVelocity());
            float g = 0.99f;
            if (this.onGround) {
                g = this.world.getBlockState(new BlockPos(this.getX(), this.getY() - 1.0, this.getZ())).getBlock().getSlipperiness() * 0.99f;
            }
            this.setVelocity(this.getVelocity().multiply(g, 0.99, g));
            if (this.onGround) {
                Vec3d vec3d2 = this.getVelocity();
                if (vec3d2.y < 0.0) {
                    this.setVelocity(vec3d2.multiply(1.0, -0.5, 1.0));
                }
            }
        }
        if (this.itemAge != NEVER_DESPAWN_AGE) {
            ++this.itemAge;
        }
        this.velocityDirty |= this.updateWaterState();
        if (!this.world.isClient && this.getVelocity().subtract(vec3d).lengthSquared() > 0.01) {
            this.velocityDirty = true;
        }
        if (!this.world.isClient && this.itemAge >= DESPAWN_AGE) {
            this.discard();
        }
    }

    private void applyWaterBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * (double)0.99f, vec3d.y + (double)(vec3d.y < (double)0.06f ? 5.0E-4f : 0.0f), vec3d.z * (double)0.99f);
    }

    private void applyLavaBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * (double)0.95f, vec3d.y + (double)(vec3d.y < (double)0.06f ? 5.0E-4f : 0.0f), vec3d.z * (double)0.95f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!this.world.isClient()) {
            this.scheduleVelocityUpdate();
            this.health = (int)((float)this.health - amount);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        }
        if (this.health <= 0) {
            if (!this.world.isClient()) {
                double r = this.getPos().getX() + 0.5;
                double s = this.getPos().getY();
                double d = this.getPos().getZ() + 0.5;
                for (int t = 0; t < 8; ++t) {
                    this.world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(PeculiarItems.POS_PAPER)), r, s, d, random.nextGaussian() * 0.15, random.nextDouble() * 0.2, random.nextGaussian() * 0.15);
                }
                for (double e = 0.0; e < Math.PI * 2; e += 0.15707963267948966) {
                    this.world.addParticle(ParticleTypes.PORTAL, r + Math.cos(e) * 5.0, s - 0.4, d + Math.sin(e) * 5.0, Math.cos(e) * -5.0, 0.0, Math.sin(e) * -5.0);
                    this.world.addParticle(ParticleTypes.PORTAL, r + Math.cos(e) * 5.0, s - 0.4, d + Math.sin(e) * 5.0, Math.cos(e) * -7.0, 0.0, Math.sin(e) * -7.0);
                }
            }
            this.discard();
        }
        return true;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putShort("Health", (short)this.health);
        nbt.putShort("Age", (short)this.itemAge);
        nbt.putShort("PickupDelay", (short)this.getDelay());
        if (this.thrower != null) {
            nbt.putUuid("Thrower", this.thrower);
        }
        if (!this.getStack().isEmpty()) {
            nbt.put("Item", this.getStack().writeNbt(new NbtCompound()));
        }
        if (!this.getPearl().isEmpty()) {
            nbt.put("Pearl", this.getPearl().writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.health = nbt.getShort("Health");
        this.itemAge = nbt.getShort("Age");
        if (nbt.contains("PickupDelay")) {
            this.setDelay(nbt.getShort("PickupDelay"));
        }
        if (nbt.containsUuid("Thrower")) {
            this.thrower = nbt.getUuid("Thrower");
        }
        if (nbt.contains("Item")) {
            this.setStack(ItemStack.fromNbt(nbt.getCompound("Item")));
        }
        if (nbt.contains("Pearl")) {
            this.setPearl(ItemStack.fromNbt(nbt.getCompound("Pearl")));
        }
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.world.isClient) {
            return;
        }
        if (this.getDelay() == 0) {
            player.sendPickup(this, 1);
            BlockPos pearl = PositionPaperItem.readStone(this.getPearl());
            if (pearl != BlockPos.ORIGIN) {
                WarpManager.queueTeleport(new WarpInstance(player).position(pearl).particles());
            }
            this.discard();
        }
    }

    @Override
    public Text getName() {
        Text text = this.getCustomName();
        if (text != null) {
            return text;
        }
        return Text.translatable(this.getStack().getTranslationKey());
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public ItemStack getStack() {
        return this.getDataTracker().get(STACK);
    }

    public void setStack(ItemStack stack) {
        this.getDataTracker().set(STACK, stack);
    }

    public ItemStack getPearl() {
        return this.getDataTracker().get(PEARL);
    }

    public void setPearl(ItemStack stack) {
        this.getDataTracker().set(PEARL, stack);
    }

    public int getDelay() {
        return this.getDataTracker().get(PICKUP_DELAY);
    }

    public void setDelay(int delay) {
        this.getDataTracker().set(PICKUP_DELAY, delay);
    }

    public void setThrower(PlayerEntity thrower) {
        this.thrower = thrower.getUuid();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (STACK.equals(data)) {
            this.getStack().setHolder(this);
        }
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.AMBIENT;
    }

    public float getRotation(float tickDelta) {
        return -(((float)this.itemAge + tickDelta) / 20.0f);
    }

    @Override
    public float getBodyYaw() {
        return 180.0f - this.getRotation(0.5f) / ((float)Math.PI * 2) * 360.0f;
    }
}