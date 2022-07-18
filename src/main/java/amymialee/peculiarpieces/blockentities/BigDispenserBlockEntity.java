package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BigDispenserBlockEntity extends DispenserBlockEntity {
    public BigDispenserBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(PeculiarBlocks.BIG_DISPENSER_ENTITY, blockPos, blockState);
        setInvStackList(DefaultedList.ofSize(54, ItemStack.EMPTY));
    }

    @Override
    public int size() {
        return 54;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.big_dispenser");
    }
}