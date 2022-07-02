package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;

public class PackedPouchScreenHandler extends ScreenHandler {
    private final ItemStack bundle;
    public final Inventory bundleInv = new SimpleInventory(54);

    public PackedPouchScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack bundle) {
        super(PeculiarPieces.BUSTLING_SCREEN_HANDLER, syncId);
        this.bundle = bundle;
        int k;
        int j;
        readNBT();
        bundleInv.onOpen(playerInventory.player);
        playerInventory.player.world.playSoundFromEntity(null, playerInventory.player, SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.PLAYERS, 0.5f, playerInventory.player.getRandom().nextFloat() * 0.1f + 0.9f);
        for (j = 0; j < 6; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(bundleInv, k + j * 9, 8 + k * 18, 18 + j * 18) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return !stack.isOf(PeculiarItems.PACKED_POUCH) && !stack.isOf(Items.BUNDLE);
                    }
                });
            }
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 139 + j * 18));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 197));
        }
    }

    private void readNBT() {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(54, ItemStack.EMPTY);
        Inventories.readNbt(bundle.getOrCreateNbt(), stacks);
        for (int i = 0; i < stacks.size(); i++) {
            bundleInv.setStack(i, stacks.get(i));
        }
    }

    private void writeNBT() {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(54, ItemStack.EMPTY);
        double capacity = 0;
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack item = bundleInv.getStack(i);
            stacks.set(i, item);
            capacity += (double) item.getCount() / item.getMaxCount();
        }
        Inventories.writeNbt(bundle.getOrCreateNbt(), stacks, true);
        bundle.getOrCreateNbt().putDouble("pp:capacity", capacity);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.bundleInv.onClose(player);
        player.world.playSoundFromEntity(null, player, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.PLAYERS, 0.5f, player.getRandom().nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < 54 ? !this.insertItem(itemStack2, 54, this.slots.size(), true) : !this.insertItem(itemStack2, 0, 54, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void sendContentUpdates() {
        writeNBT();
        super.sendContentUpdates();
    }

    public Inventory getInventory() {
        return this.bundleInv;
    }
}