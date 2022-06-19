package amymialee.peculiarpieces.items;

import dev.emi.trinkets.api.SlotReference;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class BlazingGlidersItem extends DispensableTrinketItem {
    public BlazingGlidersItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.setNoDrag(true);
        super.tick(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.setNoDrag(false);
        super.onUnequip(stack, slot, entity);
    }
}