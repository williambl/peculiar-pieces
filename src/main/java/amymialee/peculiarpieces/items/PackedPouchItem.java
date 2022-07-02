package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.screens.PackedPouchScreenHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class PackedPouchItem extends Item implements DyeableItem {
    private static final int ITEM_BAR_COLOR = MathHelper.packRgb(1.0f, 0.4f, 0.4f);

    public PackedPouchItem(FabricItemSettings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            openMenu((ServerPlayerEntity) user, new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return stack.getName();
                }

                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new PackedPouchScreenHandler(syncId, playerInventory, stack);
                }
            });
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    public void openMenu(ServerPlayerEntity player, NamedScreenHandlerFactory menu) {
        var menuProvider = new NamedScreenHandlerFactory() {
            @Nullable
            @Override
            public ScreenHandler createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                return menu.createMenu(id, inventory, player);
            }
            @Override
            public Text getDisplayName() {
                return menu.getDisplayName();
            }
        };
        player.openHandledScreen(menuProvider);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getOrCreateNbt().getDouble("pp:capacity") > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return (int) Math.min(1 + 12 * stack.getOrCreateNbt().getDouble("pp:capacity") / 54, 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ITEM_BAR_COLOR;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(54, ItemStack.EMPTY);
        Inventories.readNbt(stack.getOrCreateNbt(), stacks);
        DefaultedList<ItemStack> stacksFiltered = DefaultedList.of();
        stacks.stream().filter(itemStack -> !itemStack.isEmpty()).forEach((stacksFiltered::add));
        return Optional.of(new BundleTooltipData(stacksFiltered, stacksFiltered.size()));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(54, ItemStack.EMPTY);
        Inventories.readNbt(stack.getOrCreateNbt(), stacks);
        int filled = stacks.stream().mapToInt(itemStack -> itemStack.isEmpty() ? 0 : 1).sum();
        tooltip.add(Text.translatable("item.minecraft.bundle.fullness", filled, 54).formatted(Formatting.GRAY));
    }
}