package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.blocks.PotionPadBlock;
import amymialee.peculiarpieces.items.HiddenPotionItem;
import amymialee.peculiarpieces.mixin.StatusEffectInstanceAccessor;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.screens.PotionPadScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.potion.PotionUtil;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PotionPadBlockEntity extends LockableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;

    public PotionPadBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.POTION_PAD_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public void onEntityCollided(LivingEntity entity) {
        ItemStack stack = getPotion();
        if (stack.getItem() instanceof MilkBucketItem) {
            entity.clearStatusEffects();
        } else {
            List<StatusEffectInstance> list = PotionUtil.getPotionEffects(stack);
            for (StatusEffectInstance statusEffectInstance : list) {
                if (!statusEffectInstance.getEffectType().isInstant()) {
                    StatusEffectInstance statusEffect = new StatusEffectInstance(statusEffectInstance);
                    ((StatusEffectInstanceAccessor) statusEffect).setDuration(statusEffect.getDuration() / 2);
                    ((StatusEffectInstanceAccessor) statusEffect).setAmbient(true);
                    if (stack.getItem() instanceof HiddenPotionItem) {
                        ((StatusEffectInstanceAccessor) statusEffect).setShowParticles(false);
                    }
                    entity.addStatusEffect(statusEffect);
                }
            }
        }
    }

    public ItemStack getPotion() {
        return inventory.get(0);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = super.toInitialChunkDataNbt();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        return nbtCompound;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.potion_pad");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new PotionPadScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= 0 && slot < this.inventory.size()) {
            return this.inventory.get(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
        updateState();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(this.inventory, slot);
        updateState();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
        updateState();
    }

    @Override
    public void clear() {
        this.inventory.clear();
        updateState();
    }

    public void updateState() {
        ItemStack stack = inventory.get(0);
        BlockState blockState = getCachedState();
        if (!(blockState.getBlock() instanceof PotionPadBlock) || world == null) {
            return;
        }
        boolean update = false;
        if (stack.isEmpty() && !(blockState.get(PotionPadBlock.POTION) == PotionPadBlock.PotionStates.EMPTY)) {
            blockState = blockState.with(PotionPadBlock.POTION, PotionPadBlock.PotionStates.EMPTY);
            update = true;
        } else if (stack.getItem() instanceof PotionItem && !(blockState.get(PotionPadBlock.POTION) == PotionPadBlock.PotionStates.POTION)) {
            blockState = blockState.with(PotionPadBlock.POTION, PotionPadBlock.PotionStates.POTION);
            update = true;
        } else if (stack.getItem() instanceof MilkBucketItem && !(blockState.get(PotionPadBlock.POTION) == PotionPadBlock.PotionStates.MILK)) {
            blockState = blockState.with(PotionPadBlock.POTION, PotionPadBlock.PotionStates.MILK);
            update = true;
        }
        if (update) {
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            markDirty();
        }
    }
}