package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PeculiarItems {
    public static final ArrayList<Item> MOD_ITEMS = new ArrayList<>();
    public static final ArrayList<Item> CREATIVE_ITEMS = new ArrayList<>();
    public static final ArrayList<Item> POTION_ITEMS = new ArrayList<>();

    //Book
    public static final Item PECULIAR_BOOK = registerItem("peculiar_book", MOD_ITEMS, new PeculiarBookItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    //Misc
    public static final Item SLIME = registerItem("slime", MOD_ITEMS, new SlimeItem(new FabricItemSettings().group(PeculiarPieces.PIECES_GROUP)));
    public static final Item CRAFTING_SLATE = registerItem("crafting_slate", MOD_ITEMS, new CraftingSlateItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item GLIDER_WING = registerItem("glider_wing", MOD_ITEMS, new Item(new FabricItemSettings().maxCount(2).rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item HANG_GLIDER = registerItem("hang_glider", MOD_ITEMS, new GliderItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item PACKED_POUCH = registerItem("packed_pouch", MOD_ITEMS, new PackedPouchItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item ENDER_POUCH = registerItem("ender_pouch", MOD_ITEMS, new EnderPouchItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item MOUNTING_STICK = registerItem("mounting_stick", MOD_ITEMS, new MountingStickItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item FLIGHT_RING = registerItem("flight_ring", MOD_ITEMS, new FlightRingItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    //Shoes
    public static final Item SLIPPERY_SHOES = registerItem("slippery_shoes", MOD_ITEMS, new DispensableTrinketItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item BLAZING_GLIDERS = registerItem("blazing_gliders", MOD_ITEMS, new BlazingGlidersItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item BOUNCY_BOOTS = registerItem("bouncy_boots", MOD_ITEMS, new DispensableTrinketItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    //Pearls
    public static final Item CONSUMABLE_POS_PEARL = registerItem("consumable_position_pearl", MOD_ITEMS, new ConsumablePositionPearlItem(new FabricItemSettings().rarity(Rarity.UNCOMMON).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item POS_PEARL = registerItem("position_pearl", MOD_ITEMS, new PositionPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item TRANS_PEARL = registerItem("transport_pearl", MOD_ITEMS, new TransportPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item SPAWNPOINT_PEARL = registerItem("spawnpoint_pearl", MOD_ITEMS, new SpawnpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    public static final Item SKY_PEARL = registerItem("sky_pearl", MOD_ITEMS, new SkyPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.PIECES_GROUP)));
    //Creative Items
    public static final Item CHECKPOINT_PEARL = registerItem("checkpoint_pearl", CREATIVE_ITEMS, new CheckpointPearlItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE).group(PeculiarPieces.CREATIVE_GROUP)));
    //Potions
    public static final Item HIDDEN_POTION = registerItem("hidden_potion", POTION_ITEMS, new HiddenPotionItem(new FabricItemSettings().maxCount(1).group(PeculiarPieces.POTION_GROUP)));

    public static void init() {}

    public static Item registerItem(String name, ArrayList<Item> list, Item item) {
        Registry.register(Registry.ITEM, PeculiarPieces.id(name), item);
        list.add(item);
        return item;
    }

    public static ItemStack getRecipeKindIcon(ArrayList<Item> arrayList) {
        if (arrayList.size() == 1) {
            return arrayList.get(0).getDefaultStack();
        }
        return arrayList.get(PeculiarPieces.RANDOM.nextInt(arrayList.size() - 1)).getDefaultStack();
    }

    public static ItemStack getCreativeIcon() {
        return PeculiarBlocks.CHECKPOINT.asItem().getDefaultStack();
    }

    public static ItemStack getPotionIcon() {
        return PotionUtil.setPotion(HIDDEN_POTION.getDefaultStack().copy(), Potions.WATER_BREATHING);
    }
}