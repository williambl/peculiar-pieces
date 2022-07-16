package amymialee.peculiarpieces.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class OpenDamageSource extends DamageSource {
    private Entity source;

    public OpenDamageSource(String name) {
        super(name);
    }

    public OpenDamageSource setSource(Entity attacker) {
        this.source = attacker;
        return this;
    }

    public OpenDamageSource setBypassesDefense() {
        this.setBypassesArmor();
        return this;
    }

    public OpenDamageSource setBypassesAll() {
        this.setBypassesArmor();
        this.setBypassesProtection();
        return this;
    }

    public OpenDamageSource setNeverBlocked() {
        this.setUnblockable();
        return this;
    }

    public OpenDamageSource setVoid() {
        this.setOutOfWorld();
        return this;
    }

    @Nullable
    @Override
    public Entity getAttacker() {
        return this.source;
    }


    @Override
    public Text getDeathMessage(LivingEntity entity) {
        ItemStack itemStack = this.source instanceof LivingEntity ? ((LivingEntity)this.source).getMainHandStack() : ItemStack.EMPTY;
        String string = "death.attack." + this.name;
        if (!itemStack.isEmpty() && itemStack.hasCustomName()) {
            return Text.translatable(string + ".item", entity.getDisplayName(), this.source.getDisplayName(), itemStack.toHoverableText());
        }
        return Text.translatable(string, entity.getDisplayName(), this.source.getDisplayName());
    }

    @Override
    public boolean isScaledWithDifficulty() {
        return this.source instanceof LivingEntity && !(this.source instanceof PlayerEntity);
    }

    @Override
    @Nullable
    public Vec3d getPosition() {
        return this.source.getPos();
    }

    @Override
    public String toString() {
        return "OpenDamageSource (" + this.source + ")";
    }
}