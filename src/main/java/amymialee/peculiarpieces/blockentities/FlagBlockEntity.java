package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FlagBlockEntity extends BlockEntity implements Nameable {
    public static final String TEXTURE_KEY = "Texture";
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
        if (nbtCompound != null && nbtCompound.contains(TEXTURE_KEY, NbtElement.STRING_TYPE)) {
            return nbtCompound.getString(TEXTURE_KEY);
        }
        return null;
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.texture != null) {
            nbt.putString(TEXTURE_KEY, texture);
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
        if (nbt.contains(TEXTURE_KEY, NbtElement.STRING_TYPE)) {
            this.texture = nbt.getString(TEXTURE_KEY);
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(PeculiarBlocks.FLAG);
        if (this.texture != null) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putString(TEXTURE_KEY, texture);
            BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);
        }
        if (this.customName != null) {
            itemStack.setCustomName(this.customName);
        }
        return itemStack;
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

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }
}