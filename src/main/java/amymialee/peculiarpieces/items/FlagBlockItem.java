package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class FlagBlockItem extends BlockItem {
    public FlagBlockItem(Block block, FabricItemSettings settings) {
        super(block, settings);
        Validate.isInstanceOf(FlagBlock.class, block);
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null && nbtCompound.contains(FlagBlockEntity.TEXTURE_KEY, 8)) {
            return Text.translatable("block.%s.%s_flag".formatted(PeculiarPieces.MOD_ID, nbtCompound.getString(FlagBlockEntity.TEXTURE_KEY).toLowerCase()));
        }
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null && nbtCompound.contains(FlagBlockEntity.TEXTURE_KEY, 8)) {
            if (Objects.equals(nbtCompound.getString(FlagBlockEntity.TEXTURE_KEY), "labrys")) {
                tooltip.add(Text.literal("Fuck TERFs!").formatted(Formatting.LIGHT_PURPLE));
            }
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}