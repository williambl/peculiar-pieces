package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.WarpBlockEntity;
import amymialee.peculiarpieces.blocks.CheckpointBlock;
import amymialee.peculiarpieces.blocks.CheckpointRemoverBlock;
import amymialee.peculiarpieces.blocks.CheckpointReturnerBlock;
import amymialee.peculiarpieces.blocks.FastTargetBlock;
import amymialee.peculiarpieces.blocks.InvertedRedstoneLampBlock;
import amymialee.peculiarpieces.blocks.OpenPressurePlate;
import amymialee.peculiarpieces.blocks.RedstoneClampBlock;
import amymialee.peculiarpieces.blocks.RedstoneFilterBlock;
import amymialee.peculiarpieces.blocks.RedstoneHurdleBlock;
import amymialee.peculiarpieces.blocks.RedstoneMonoBlock;
import amymialee.peculiarpieces.blocks.RedstoneRandomizerBlock;
import amymialee.peculiarpieces.blocks.RedstoneStaticBlock;
import amymialee.peculiarpieces.blocks.WarpBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class PeculiarBlocks {
    public static final Block CHECKPOINT = registerBlock("checkpoint", new BlockItem(new CheckpointBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.PIECES_GROUP)));
    public static final Block CHECKPOINT_REMOVER = registerBlock("checkpoint_remover", new BlockItem(new CheckpointRemoverBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.PIECES_GROUP)));
    public static final Block CHECKPOINT_RETURNER = registerBlock("checkpoint_returner", new BlockItem(new CheckpointReturnerBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.PIECES_GROUP)));

    public static final Block INVISIBLE_PLATE_LIGHT = registerBlock("invisible_plate_light", new OpenPressurePlate(true, 0, FabricBlockSettings.of(Material.WOOD, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    public static final Block INVISIBLE_PLATE_HEAVY = registerBlock("invisible_plate_heavy", new OpenPressurePlate(true, 1, FabricBlockSettings.of(Material.STONE, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    public static final Block INVISIBLE_PLAYER_PLATE = registerBlock("invisible_player_plate", new OpenPressurePlate(true, 2, FabricBlockSettings.of(Material.STONE, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    public static final Block PLAYER_PLATE = registerBlock("player_plate", new OpenPressurePlate(false, 2, FabricBlockSettings.of(Material.STONE, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));

    public static final Block SLIPPERY_STONE = registerBlock("slippery_stone", new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.25F, 4.0F).slipperiness(1f / 0.91f)));
    public static final Block INVERTED_REDSTONE_LAMP = registerBlock("inverted_redstone_lamp", new InvertedRedstoneLampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(state -> state.get(Properties.LIT) ? 15 : 0).strength(0.3f).sounds(BlockSoundGroup.GLASS).allowsSpawning((a, b, c, d) -> true)));

    public static final Block WARP_BLOCK = registerBlock("warp_block", new WarpBlock(FabricBlockSettings.copy(Blocks.LODESTONE)));
    public static BlockEntityType<WarpBlockEntity> WARP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "warp_block", FabricBlockEntityTypeBuilder.create(WarpBlockEntity::new, WARP_BLOCK).build(null));

    public static final Block FAST_TARGET_BLOCK = registerBlock("fast_target", new FastTargetBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block DARK_FAST_TARGET_BLOCK = registerBlock("dark_fast_target", new FastTargetBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));

    public static final Block REDSTONE_CLAMP = registerBlock("redstone_clamp", new RedstoneClampBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_FILTER = registerBlock("redstone_filter", new RedstoneFilterBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_HURDLE = registerBlock("redstone_hurdle", new RedstoneHurdleBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_STATIC = registerBlock("redstone_static", new RedstoneStaticBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_MONO = registerBlock("redstone_mono", new RedstoneMonoBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_RANDOM = registerBlock("redstone_random", new RedstoneRandomizerBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));

    public static void init() {}

    private static Block registerBlock(String name, Block block) {
        return registerBlock(name, new BlockItem(block, new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));
    }

    private static Block registerBlock(String name, BlockItem block) {
        Registry.register(Registry.BLOCK, PeculiarPieces.id(name), block.getBlock());
        PeculiarItems.registerItem(name, block);
        return block.getBlock();
    }
}