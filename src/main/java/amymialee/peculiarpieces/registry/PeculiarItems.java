package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.items.BlazingGlidersItem;
import amymialee.peculiarpieces.items.CheckpointPearlItem;
import amymialee.peculiarpieces.items.ConsumablePositionPearlItem;
import amymialee.peculiarpieces.items.FlightRingItem;
import amymialee.peculiarpieces.items.MountingStickItem;
import amymialee.peculiarpieces.items.PositionPearlItem;
import amymialee.peculiarpieces.items.SlipperyShoesItem;
import amymialee.peculiarpieces.items.SpawnpointPearlItem;
import amymialee.peculiarpieces.items.TransportPearlItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PeculiarItems {
    public static final ArrayList<Item> MOD_ITEMS = new ArrayList<>();

    public static final Item CHECKPOINT_PEARL = registerItem("checkpoint_pearl", new CheckpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item TRANS_PEARL = registerItem("transport_pearl", new TransportPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item POS_PEARL = registerItem("position_pearl", new PositionPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item CONSUMABLE_POS_PEARL = registerItem("consumable_position_pearl", new ConsumablePositionPearlItem(new FabricItemSettings().rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item SPAWNPOINT_PEARL = registerItem("spawnpoint_pearl", new SpawnpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item MOUNTING_STICK = registerItem("mounting_stick", new MountingStickItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item FLIGHT_RING = registerItem("flight_ring", new FlightRingItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item SLIPPERY_SHOES = registerItem("slippery_shoes", new SlipperyShoesItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item BLAZING_GLIDERS = registerItem("blazing_gliders", new BlazingGlidersItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static void init() {}

    public static Item registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, PeculiarPieces.id(name), item);
        MOD_ITEMS.add(item);
        return item;
    }

    public static ItemStack getRecipeKindIcon() {
        return MOD_ITEMS.get(PeculiarPieces.RANDOM.nextInt(MOD_ITEMS.size() - 1)).getDefaultStack();
    }
}