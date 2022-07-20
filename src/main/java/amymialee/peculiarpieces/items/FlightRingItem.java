package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.PeculiarHelper;
import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FlightRingItem extends DispensableTrinketItem {
    public FlightRingItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);
        if (entity instanceof PlayerEntity player) {
            PeculiarHelper.updateFlight(player);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if (entity instanceof PlayerEntity player) {
            PeculiarHelper.updateFlight(player);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}