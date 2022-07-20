package amymialee.peculiarpieces.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SculkShriekerBlock.class)
public class SculkShriekerBlockMixin {
    @Shadow @Final public static BooleanProperty CAN_SUMMON;

    @Unique
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult ignoredHit) {
        if (!state.get(CAN_SUMMON) && player.getAbilities().allowModifyWorld) {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.isOf(Items.ECHO_SHARD)) {
                world.setBlockState(pos, state.with(CAN_SUMMON, true));
                stack.decrement(1);
                world.playSound(player, pos, SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.BLOCKS, 2f, (world.random.nextFloat() - world.random.nextFloat()) * 0.2f + 1.0f);
            }
        }
        return ActionResult.success(world.isClient);
    }
}