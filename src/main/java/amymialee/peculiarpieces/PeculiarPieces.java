package amymialee.peculiarpieces;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarItems;
import amymialee.peculiarpieces.screens.WarpScreenHandler;
import amymialee.peculiarpieces.util.WarpManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Random;

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "unused"})
public class PeculiarPieces implements ModInitializer {
    public static final String MOD_ID = "peculiarpieces";
    public static final Random RANDOM = new Random();
    public static final ItemGroup PIECES_GROUP = FabricItemGroupBuilder.create(id("peculiarpieces_group")).icon(PeculiarItems::getRecipeKindIcon).build();

    public static final ScreenHandlerType<WarpScreenHandler> WARP_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, "warp_block", new ScreenHandlerType<>(WarpScreenHandler::new));
    public static final TagKey<EntityType<?>> MOUNT_BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, id("mount_blacklist"));
    public static final TagKey<Block> WARP_BINDABLE = TagKey.of(Registry.BLOCK_KEY, id("warp_bindable"));

    @Override
    public void onInitialize() {
        PeculiarItems.init();
        PeculiarBlocks.init();
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> WarpManager.tick());
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
