package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.BigDispenserBlockEntity;
import amymialee.peculiarpieces.blockentities.BigDropperBlockEntity;
import amymialee.peculiarpieces.blockentities.EntangledScaffoldingBlockEntity;
import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.blockentities.PotionPadBlockEntity;
import amymialee.peculiarpieces.blockentities.WarpBlockEntity;
import amymialee.peculiarpieces.blocks.*;
import amymialee.peculiarpieces.items.CustomScaffoldingItem;
import amymialee.peculiarpieces.items.MidairBlockItem;
import amymialee.peculiarpieces.items.ToggleableBlockItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;

import java.util.ArrayList;

@SuppressWarnings({"unused", "SameParameterValue"})
public class PeculiarBlocks {
    // Blocks
    //Torch Levers
    public static final Block TORCH_LEVER = registerBlock("torch_lever", PeculiarItems.MOD_ITEMS, new TorchLeverBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> state.get(TorchLeverBlock.POWERED) ? 2 : 14).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME));
    public static final Block SOUL_TORCH_LEVER = registerBlock("soul_torch_lever", PeculiarItems.MOD_ITEMS, new TorchLeverBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly().luminance(state -> state.get(TorchLeverBlock.POWERED) ? 2 : 10).sounds(BlockSoundGroup.WOOD), ParticleTypes.SOUL_FIRE_FLAME));
    //Plates
    public static final Block GRABBING_TRAP = registerBlock("grabbing_trap", PeculiarItems.MOD_ITEMS, new GrabbingTrapBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block POTION_PAD = registerBlock("potion_pad", PeculiarItems.MOD_ITEMS, new PotionPadBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block JUMP_PAD = registerBlock("jump_pad", PeculiarItems.MOD_ITEMS, new ToggleableBlockItem(3, new JumpPadBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)), new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));
    public static final Block PUSH_PAD = registerBlock("push_pad", PeculiarItems.MOD_ITEMS, new ToggleableBlockItem(3, new PushPadBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)), new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));
    //Pressure Plates
    public static final Block PLAYER_PLATE = registerBlock("player_plate", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(false, 2, FabricBlockSettings.of(Material.STONE, Blocks.OBSIDIAN.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.STONE)));
    public static final Block INVISIBLE_PLATE_LIGHT = registerBlock("invisible_plate_light", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 0, FabricBlockSettings.of(Material.WOOD, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    public static final Block INVISIBLE_PLATE_HEAVY = registerBlock("invisible_plate_heavy", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 1, FabricBlockSettings.of(Material.STONE, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    public static final Block INVISIBLE_PLAYER_PLATE = registerBlock("invisible_player_plate", PeculiarItems.MOD_ITEMS, new OpenPressurePlate(true, 2, FabricBlockSettings.of(Material.STONE, Blocks.TINTED_GLASS.getDefaultMapColor()).noCollision().strength(0.5F).sounds(BlockSoundGroup.GLASS)));
    //Misc
    public static final Block SLIPPERY_STONE = registerBlock("slippery_stone", PeculiarItems.MOD_ITEMS, new Block(FabricBlockSettings.of(Material.STONE).requiresTool().strength(1.25F, 4.0F).slipperiness(1f / 0.91f)));
    public static final Block INVERTED_REDSTONE_LAMP = registerBlock("inverted_redstone_lamp", PeculiarItems.MOD_ITEMS, new InvertedRedstoneLampBlock(FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(state -> state.get(Properties.LIT) ? 15 : 0).strength(0.3f).sounds(BlockSoundGroup.GLASS).allowsSpawning((a, b, c, d) -> true)));
    public static final Block WARP_BLOCK = registerBlock("warp_block", PeculiarItems.MOD_ITEMS, new WarpBlock(FabricBlockSettings.copy(Blocks.LODESTONE)));
    public static final Block FISH_TANK = registerBlock("fish_tank", PeculiarItems.MOD_ITEMS, new FishTankBlock(FabricBlockSettings.copy(Blocks.GLASS)));
    public static final Block TOUGHENED_SCAFFOLDING = registerBlock("toughened_scaffolding", PeculiarItems.MOD_ITEMS, new CustomScaffoldingItem(24, new ToughenedScaffoldingBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.IRON_GRAY).noCollision().sounds(BlockSoundGroup.METAL).dynamicBounds()), new FabricItemSettings().rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Block ENTANGLED_SCAFFOLDING = registerBlock("entangled_scaffolding", PeculiarItems.MOD_ITEMS, new CustomScaffoldingItem(24, new EntangledScaffoldingBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.PALE_PURPLE).noCollision().sounds(BlockSoundGroup.AMETHYST_CLUSTER).dynamicBounds()), new FabricItemSettings().rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Block SHOCK_ABSORBER = registerBlock("shock_absorber", PeculiarItems.MOD_ITEMS, new ShockAbsorberBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BURNING_SPONGE = registerBlock("burning_sponge", PeculiarItems.MOD_ITEMS, new BurningSpongeBlock(FabricBlockSettings.of(Material.SPONGE).strength(0.6f).sounds(BlockSoundGroup.GRASS)));
    public static final Block MIDAIR_BLOCK = registerBlock("midair_block", PeculiarItems.MOD_ITEMS, new MidairBlockItem(new MidairBlock(FabricBlockSettings.of(Material.STONE).breakInstantly().luminance(3)), new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));
    //Target Blocks
    public static final Block FAST_TARGET_BLOCK = registerBlock("fast_target", PeculiarItems.MOD_ITEMS, new FastTargetBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    public static final Block DARK_FAST_TARGET_BLOCK = registerBlock("dark_fast_target", PeculiarItems.MOD_ITEMS, new FastTargetBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.OFF_WHITE).strength(0.5f).sounds(BlockSoundGroup.GRASS)));
    //Gadgets
    public static final Block BIG_DROPPER = registerBlock("big_dropper", PeculiarItems.MOD_ITEMS, new BigDropperBlock(FabricBlockSettings.copy(Blocks.DROPPER)));
    public static final Block BIG_DISPENSER = registerBlock("big_dispenser", PeculiarItems.MOD_ITEMS, new BigDispenserBlock(FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static final Block IGNITION = registerBlock("ignition", PeculiarItems.MOD_ITEMS, new IgnitionBlock(FabricBlockSettings.copy(Blocks.DROPPER)));
    public static final Block BLOCK_BREAKER = registerBlock("block_breaker", PeculiarItems.MOD_ITEMS, new BlockBreakerBlock(FabricBlockSettings.copy(Blocks.DISPENSER)));
    //Glass
    public static final Block ENTITY_GLASS = registerBlock("entity_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(false, false, FabricBlockSettings.copy(Blocks.GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block TINTED_ENTITY_GLASS = registerBlock("tinted_entity_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(true, false, FabricBlockSettings.copy(Blocks.TINTED_GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block PLAYER_GLASS = registerBlock("player_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(false, true, FabricBlockSettings.copy(Blocks.GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    public static final Block TINTED_PLAYER_GLASS = registerBlock("tinted_player_glass", PeculiarItems.MOD_ITEMS, new SolidGlassBlock(true, true, FabricBlockSettings.copy(Blocks.TINTED_GLASS).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)));
    //Elevators
    public static final Block WHITE_ELEVATOR = registerBlock("white_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.WHITE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ELEVATOR = registerBlock("orange_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ELEVATOR = registerBlock("magenta_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.MAGENTA).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ELEVATOR = registerBlock("light_blue_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ELEVATOR = registerBlock("yellow_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.YELLOW).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ELEVATOR = registerBlock("lime_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIME).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ELEVATOR = registerBlock("pink_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PINK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ELEVATOR = registerBlock("gray_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ELEVATOR = registerBlock("light_gray_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ELEVATOR = registerBlock("cyan_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.CYAN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ELEVATOR = registerBlock("purple_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PURPLE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ELEVATOR = registerBlock("blue_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ELEVATOR = registerBlock("brown_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ELEVATOR = registerBlock("green_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GREEN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ELEVATOR = registerBlock("red_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.RED).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ELEVATOR = registerBlock("black_elevator", PeculiarItems.MOD_ITEMS, new ElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    //Rotating Elevators
    public static final Block WHITE_ROTATING_ELEVATOR = registerBlock("white_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.WHITE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block ORANGE_ROTATING_ELEVATOR = registerBlock("orange_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block MAGENTA_ROTATING_ELEVATOR = registerBlock("magenta_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.MAGENTA).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_BLUE_ROTATING_ELEVATOR = registerBlock("light_blue_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block YELLOW_ROTATING_ELEVATOR = registerBlock("yellow_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.YELLOW).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIME_ROTATING_ELEVATOR = registerBlock("lime_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIME).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PINK_ROTATING_ELEVATOR = registerBlock("pink_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PINK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GRAY_ROTATING_ELEVATOR = registerBlock("gray_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block LIGHT_GRAY_ROTATING_ELEVATOR = registerBlock("light_gray_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.LIGHT_GRAY).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block CYAN_ROTATING_ELEVATOR = registerBlock("cyan_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.CYAN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block PURPLE_ROTATING_ELEVATOR = registerBlock("purple_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.PURPLE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLUE_ROTATING_ELEVATOR = registerBlock("blue_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLUE).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BROWN_ROTATING_ELEVATOR = registerBlock("brown_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BROWN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block GREEN_ROTATING_ELEVATOR = registerBlock("green_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.GREEN).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block RED_ROTATING_ELEVATOR = registerBlock("red_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.RED).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));
    public static final Block BLACK_ROTATING_ELEVATOR = registerBlock("black_rotating_elevator", PeculiarItems.MOD_ITEMS, new RotatingElevatorBlock(FabricBlockSettings.of(Material.WOOL, MapColor.BLACK).strength(0.8f).sounds(BlockSoundGroup.LODESTONE)));


    // Creative Items
    //Checkpoints
    public static final Block CHECKPOINT = registerBlock("checkpoint", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block CHECKPOINT_REMOVER = registerBlock("checkpoint_remover", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointRemoverBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block CHECKPOINT_RETURNER = registerBlock("checkpoint_returner", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointReturnerBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block CHECKPOINT_DAMAGER = registerBlock("checkpoint_damager", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new CheckpointDamagerBlock(FabricBlockSettings.of(Material.STRUCTURE_VOID).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    //Entity Barriers
    public static final Block ENTITY_BARRIER = registerBlock("entity_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new EntityBarrierBlock(false, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block PLAYER_BARRIER = registerBlock("player_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new EntityBarrierBlock(true, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    //Gamemode Blocks
    public static final Block ADVENTURE_BLOCKER = registerBlock("adventure_blocker", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBlockerBlock(GameMode.ADVENTURE, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block ADVENTURE_BARRIER = registerBlock("adventure_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBarrierBlock(GameMode.ADVENTURE, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block ADVENTURE_SETTER = registerBlock("adventure_setter", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeSetterBlock(GameMode.ADVENTURE, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block SURVIVOR_BLOCKER = registerBlock("survivor_blocker", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBlockerBlock(GameMode.SURVIVAL, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block SURVIVOR_BARRIER = registerBlock("survivor_barrier", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeBarrierBlock(GameMode.SURVIVAL, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).nonOpaque().solidBlock(PeculiarBlocks::never).suffocates(PeculiarBlocks::never).blockVision(PeculiarBlocks::never)), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    public static final Block SURVIVOR_SETTER = registerBlock("survivor_setter", PeculiarItems.CREATIVE_ITEMS, new BlockItem(new GameModeSetterBlock(GameMode.SURVIVAL, FabricBlockSettings.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noCollision()), new FabricItemSettings().rarity(Rarity.EPIC).group(PeculiarPieces.CREATIVE_GROUP)));
    //Redstone Items
    public static final Block REDSTONE_CLAMP = registerBlock("redstone_clamp", PeculiarItems.CREATIVE_ITEMS, new RedstoneClampBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_FILTER = registerBlock("redstone_filter", PeculiarItems.CREATIVE_ITEMS, new RedstoneFilterBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_HURDLE = registerBlock("redstone_hurdle", PeculiarItems.CREATIVE_ITEMS, new RedstoneHurdleBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_STATIC = registerBlock("redstone_static", PeculiarItems.CREATIVE_ITEMS, new RedstoneStaticBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_MONO = registerBlock("redstone_mono", PeculiarItems.CREATIVE_ITEMS, new RedstoneMonoBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));
    public static final Block REDSTONE_RANDOM = registerBlock("redstone_random", PeculiarItems.CREATIVE_ITEMS, new RedstoneRandomizerBlock(FabricBlockSettings.of(Material.DECORATION).breakInstantly().sounds(BlockSoundGroup.WOOD)));


    // Block Entities
    public static BlockEntityType<WarpBlockEntity> WARP_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "warp_block", FabricBlockEntityTypeBuilder.create(WarpBlockEntity::new, WARP_BLOCK).build(null));
    public static BlockEntityType<EntangledScaffoldingBlockEntity> ENTANGLED_SCAFFOLDING_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "entangled_scaffolding", FabricBlockEntityTypeBuilder.create(EntangledScaffoldingBlockEntity::new, ENTANGLED_SCAFFOLDING).build(null));
    public static BlockEntityType<BigDropperBlockEntity> BIG_DROPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "big_dropper", FabricBlockEntityTypeBuilder.create(BigDropperBlockEntity::new, BIG_DROPPER).build(null));
    public static BlockEntityType<BigDispenserBlockEntity> BIG_DISPENSER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "big_dispenser", FabricBlockEntityTypeBuilder.create(BigDispenserBlockEntity::new, BIG_DISPENSER).build(null));
    public static BlockEntityType<PotionPadBlockEntity> POTION_PAD_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "potion_pad", FabricBlockEntityTypeBuilder.create(PotionPadBlockEntity::new, POTION_PAD).build(null));
    public static BlockEntityType<FishTankBlockEntity> FISH_TANK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "fish_tank", FabricBlockEntityTypeBuilder.create(FishTankBlockEntity::new, FISH_TANK).build(null));

    public static void init() {}

    private static Block registerBlock(String name, ArrayList<Item> list, Block block) {
        ItemGroup group = PeculiarPieces.PIECES_GROUP;
        if (list == PeculiarItems.CREATIVE_ITEMS) {
            group = PeculiarPieces.CREATIVE_GROUP;
        }
        return registerBlock(name, list, new BlockItem(block, new FabricItemSettings().group(group)));
    }

    private static Block registerBlock(String name, ArrayList<Item> list, BlockItem block) {
        Registry.register(Registry.BLOCK, PeculiarPieces.id(name), block.getBlock());
        PeculiarItems.registerItem(name, list, block);
        return block.getBlock();
    }

    private static Block registerBlock(String name, Block block) {
        Registry.register(Registry.BLOCK, PeculiarPieces.id(name), block);
        return block;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
}