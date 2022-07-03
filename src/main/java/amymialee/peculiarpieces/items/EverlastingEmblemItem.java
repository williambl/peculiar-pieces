package amymialee.peculiarpieces.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemStack;

public class EverlastingEmblemItem extends DispensableTrinketItem {
    public EverlastingEmblemItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}