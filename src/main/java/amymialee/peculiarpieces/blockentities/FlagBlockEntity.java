package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity implements Nameable {
    public static final String COLOR_KEY = "Color";
    @Nullable
    private Text customName;
    private String texture;

    public FlagBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.FLAG_BLOCK_ENTITY, pos, state);
    }

    public void readFrom(ItemStack stack) {
        this.texture = getTexture(stack);
        this.customName = stack.hasCustomName() ? stack.getName() : null;
    }

    @Nullable
    public static String getTexture(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null && nbtCompound.contains(COLOR_KEY)) {
            return nbtCompound.getString(COLOR_KEY);
        }
        return null;
    }

    public Text getName() {
        return this.customName != null ? this.customName : Text.translatable("block.minecraft.banner");
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.texture != null) {
            nbt.putString(COLOR_KEY, texture);
        }
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
        if (nbt.contains(COLOR_KEY)) {
            this.texture = nbt.getString(COLOR_KEY);
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }
//
//    public static void loadFromItemStack(ItemStack stack) {
//        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
//        if (nbtCompound != null && nbtCompound.contains("Patterns", 9)) {
//            NbtList nbtList = nbtCompound.getList("Patterns", 10);
//            if (!nbtList.isEmpty()) {
//                nbtList.remove(nbtList.size() - 1);
//                if (nbtList.isEmpty()) {
//                    nbtCompound.remove("Patterns");
//                }
//
//                BlockItem.setBlockEntityNbt(stack, BlockEntityType.BANNER, nbtCompound);
//            }
//        }
//    }

    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(PeculiarBlocks.FLAG);
        if (this.texture != null) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putString(COLOR_KEY, texture);
            BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);
        }
        if (this.customName != null) {
            itemStack.setCustomName(this.customName);
        }
        return itemStack;
    }
}