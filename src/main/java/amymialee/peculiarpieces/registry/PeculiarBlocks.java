package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.WarpBlockEntity;
import amymialee.peculiarpieces.blocks.*;
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
    public static final Block PLAYER_PLATE = registerBlock("player_plate", new OpenPressurePlate(false, 2, FabricBlockSettings.of(Material.STONE, Blocks.OBSIDIAN.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.STONE)));

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

    public static final Block BIG_DROPPER = registerBlock("big_dropper", new BigDropperBlock(FabricBlockSettings.copy(Blocks.DROPPER)));
    public static BlockEntityType<BigDropperBlockEntity> BIG_DROPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "big_dropper", FabricBlockEntityTypeBuilder.create(BigDropperBlockEntity::new, BIG_DROPPER).build(null));
    public static final Block BIG_DISPENSER = registerBlock("big_dispenser", new BigDispenserBlock(FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static BlockEntityType<BigDispenserBlockEntity> BIG_DISPENSER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "big_dispenser", FabricBlockEntityTypeBuilder.create(BigDispenserBlockEntity::new, BIG_DISPENSER).build(null));

    public static final Block SURVIVOR_BLOCKER = registerBlock("survivor_blocker", new BlockItem(new SurvivorBarrierBlock(FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.PIECES_GROUP)));

    public static final Block WHITE_ELEVATOR = registerBlock("white_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.WHITE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ELEVATOR = registerBlock("orange_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ELEVATOR = registerBlock("magenta_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.MAGENTA).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ELEVATOR = registerBlock("light_blue_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ELEVATOR = registerBlock("yellow_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.YELLOW).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ELEVATOR = registerBlock("lime_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIME).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ELEVATOR = registerBlock("pink_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PINK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ELEVATOR = registerBlock("gray_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ELEVATOR = registerBlock("light_gray_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ELEVATOR = registerBlock("cyan_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.CYAN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ELEVATOR = registerBlock("purple_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PURPLE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ELEVATOR = registerBlock("blue_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ELEVATOR = registerBlock("brown_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ELEVATOR = registerBlock("green_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GREEN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ELEVATOR = registerBlock("red_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.RED).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ELEVATOR = registerBlock("black_elevator", new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));

    public static final Block WHITE_ROTATING_ELEVATOR = registerBlock("white_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.WHITE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ROTATING_ELEVATOR = registerBlock("orange_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ROTATING_ELEVATOR = registerBlock("magenta_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.MAGENTA).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ROTATING_ELEVATOR = registerBlock("light_blue_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ROTATING_ELEVATOR = registerBlock("yellow_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.YELLOW).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ROTATING_ELEVATOR = registerBlock("lime_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIME).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ROTATING_ELEVATOR = registerBlock("pink_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PINK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ROTATING_ELEVATOR = registerBlock("gray_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ROTATING_ELEVATOR = registerBlock("light_gray_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ROTATING_ELEVATOR = registerBlock("cyan_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.CYAN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ROTATING_ELEVATOR = registerBlock("purple_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PURPLE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ROTATING_ELEVATOR = registerBlock("blue_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ROTATING_ELEVATOR = registerBlock("brown_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ROTATING_ELEVATOR = registerBlock("green_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GREEN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ROTATING_ELEVATOR = registerBlock("red_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.RED).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ROTATING_ELEVATOR = registerBlock("black_rotating_elevator", new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));

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