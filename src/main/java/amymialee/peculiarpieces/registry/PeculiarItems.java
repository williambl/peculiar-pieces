package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.items.BlazingGlidersItem;
import amymialee.peculiarpieces.items.CheckpointPearlItem;
import amymialee.peculiarpieces.items.ConsumablePositionPearlItem;
import amymialee.peculiarpieces.items.FlightRingItem;
import amymialee.peculiarpieces.items.MountingStickItem;
import amymialee.peculiarpieces.items.PeculiarBookItem;
import amymialee.peculiarpieces.items.PositionPearlItem;
import amymialee.peculiarpieces.items.SkyPearlItem;
import amymialee.peculiarpieces.items.SlimeItem;
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
    public static final ArrayList<Item> CREATIVE_ITEMS = new ArrayList<>();

    public static final Item PECULIAR_BOOK = registerItem("peculiar_book", MOD_ITEMS, new PeculiarBookItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item CHECKPOINT_PEARL = registerItem("checkpoint_pearl", CREATIVE_ITEMS, new CheckpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.CREATIVE_GROUP)));

    public static final Item SKY_PEARL = registerItem("sky_pearl", MOD_ITEMS, new SkyPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item TRANS_PEARL = registerItem("transport_pearl", MOD_ITEMS, new TransportPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item POS_PEARL = registerItem("position_pearl", MOD_ITEMS, new PositionPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item CONSUMABLE_POS_PEARL = registerItem("consumable_position_pearl", MOD_ITEMS, new ConsumablePositionPearlItem(new FabricItemSettings().rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item SPAWNPOINT_PEARL = registerItem("spawnpoint_pearl", MOD_ITEMS, new SpawnpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item MOUNTING_STICK = registerItem("mounting_stick", MOD_ITEMS, new MountingStickItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item FLIGHT_RING = registerItem("flight_ring", MOD_ITEMS, new FlightRingItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item SLIPPERY_SHOES = registerItem("slippery_shoes", MOD_ITEMS, new SlipperyShoesItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item BLAZING_GLIDERS = registerItem("blazing_gliders", MOD_ITEMS, new BlazingGlidersItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));

    public static final Item SLIME = registerItem("slime", MOD_ITEMS, new SlimeItem(new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));

    public static void init() {}

    public static Item registerItem(String name, ArrayList<Item> list, Item item) {
        Registry.register(Registry.ITEM, PeculiarPieces.id(name), item);
        list.add(item);
        return item;
    }

    public static ItemStack getRecipeKindIcon(ArrayList<Item> arrayList) {
        return arrayList.get(PeculiarPieces.RANDOM.nextInt(arrayList.size() - 1)).getDefaultStack();
    }
}