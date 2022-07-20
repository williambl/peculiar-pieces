package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class EntangledScaffoldingBlockEntity extends BlockEntity {
    private int time;
    private UUID owner;

    public EntangledScaffoldingBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.ENTANGLED_SCAFFOLDING_BLOCK_ENTITY, pos, state);
        this.time = 200;
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.time = nbt.getInt("pp:time");
        this.owner = nbt.getUuid("pp:owner");
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("pp:time", this.time);
        if (this.owner != null) {
            nbt.putUuid("pp:owner", this.owner);
        }
    }

    public static void tick(World world, BlockPos pos, EntangledScaffoldingBlockEntity blockEntity) {
        if (blockEntity.owner == null) {
            PlayerEntity newOwner = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false);
            if (newOwner != null) {
                blockEntity.owner = newOwner.getUuid();
            }
        }
        blockEntity.time--;
        if (blockEntity.time <= 0) {
            PlayerEntity owner = world.getPlayerByUuid(blockEntity.owner);
            if (owner != null) {
                world.breakBlock(pos, !owner.giveItemStack(new ItemStack(PeculiarBlocks.ENTANGLED_SCAFFOLDING)));
            } else {
                world.breakBlock(pos, true);
            }
            blockEntity.markRemoved();
        }
    }
}