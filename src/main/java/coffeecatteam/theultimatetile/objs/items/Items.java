package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.objs.ItemDataParser;
import coffeecatteam.theultimatetile.objs.items.tool.*;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Items {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();
    public static final List<Item> UPDATABLE_TIEMS = new ArrayList<>();

    /*
     * General
     */
    public static Item STICK, WOOD, BARK, LEAF, ROCK;
    public static Item ROTTEN_FLESH, BONE, BOUNCY_BALL, WOOL_BUNDLE;
    public static Item BOOK;

    public static Item COIN_PENNY, COIN_IRON, COIN_GOLD;

    /*
     * Tools/Weapons
     */
    public static Item WOODEN_SWORD, WOODEN_AXE, WOODEN_PICK, WOODEN_SHOVEL, WOODEN_HOE;
    public static Item STONE_SWORD, STONE_AXE, STONE_PICK, STONE_SHOVEL, STONE_HOE;

    /*
     * Minerals
     */
    public static Item COAL, IRON_INGOT, GOLD_INGOT, DIAMOND;

    /*
     * Foods
     */
    public static Item CARROT, WHEAT, POTATO, TOMATO, CORN;
    public static Item BREAD, APPLE;
    public static Item RAW_PORK, COOKED_PORK, RAW_STEAK, COOKED_STEAK;

    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        ItemDataParser parser = new ItemDataParser();

        /*
         * General
         */
        register(STICK = parser.loadData(new BasicItem(tutEngine, "stick")));
        register(WOOD = parser.loadData(new BasicItem(tutEngine, "wood")));
        register(BARK = parser.loadData(new BasicItem(tutEngine, "bark")));

        register(LEAF = parser.loadData(new BasicItem(tutEngine, "leaf")));
        register(ROCK = parser.loadData(new BasicItem(tutEngine, "rock")));
        register(ROTTEN_FLESH = parser.loadData(new BasicItem(tutEngine, "rotten_flesh")));
        register(BONE = parser.loadData(new BasicItem(tutEngine, "bone")));
        register(BOUNCY_BALL = parser.loadData(new BasicItem(tutEngine, "bouncy_ball")));
        register(WOOL_BUNDLE = parser.loadData(new BasicItem(tutEngine, "wool_bundle")));
        register(BOOK = parser.loadData(new BasicItem(tutEngine, "book")));

        register(COIN_PENNY = parser.loadData(new CoinItem(tutEngine, "coin_penny", 0.25f)));
        register(COIN_IRON = parser.loadData(new CoinItem(tutEngine, "coin_iron", 0.50f)));
        register(COIN_GOLD = parser.loadData(new CoinItem(tutEngine, "coin_gold", 1.00f)));

        /*
         * Tools/Weapons
         */
        register(WOODEN_SWORD = parser.loadData(new SwordItem(tutEngine, "wooden_sword", 5)));
        register(WOODEN_AXE = parser.loadData(new AxeItem(tutEngine, "wooden_axe", 6)));
        register(WOODEN_PICK = parser.loadData(new PickaxeItem(tutEngine, "wooden_pick", 7)));
        register(WOODEN_SHOVEL = parser.loadData(new ShovelItem(tutEngine, "wooden_shovel", 5)));
        register(WOODEN_HOE = parser.loadData(new HoeItem(tutEngine, "wooden_hoe", 4)));

        register(STONE_SWORD = parser.loadData(new SwordItem(tutEngine, "stone_sword", 7)));
        register(STONE_AXE = parser.loadData(new AxeItem(tutEngine, "stone_axe", 8)));
        register(STONE_PICK = parser.loadData(new PickaxeItem(tutEngine, "stone_pick", 9)));
        register(STONE_SHOVEL = parser.loadData(new ShovelItem(tutEngine, "stone_shovel", 7)));
        register(STONE_HOE = parser.loadData(new HoeItem(tutEngine, "stone_hoe", 6)));

        /*
         * Minerals
         */
        register(COAL = parser.loadData(new BasicItem(tutEngine, "coal")));
        register(IRON_INGOT = parser.loadData(new BasicItem(tutEngine, "iron_ingot")));
        register(GOLD_INGOT = parser.loadData(new BasicItem(tutEngine, "gold_ingot")));
        register(DIAMOND = parser.loadData(new BasicItem(tutEngine, "diamond")));

        /*
         * Foods
         */
        register(CARROT = parser.loadData(new FoodItem(tutEngine, "carrot", 4)));
        register(WHEAT = parser.loadData(new FoodItem(tutEngine, "wheat", 2)));
        register(POTATO = parser.loadData(new FoodItem(tutEngine, "potato", 4)));
        register(TOMATO = parser.loadData(new FoodItem(tutEngine, "tomato", 4)));
        register(CORN = parser.loadData(new FoodItem(tutEngine, "corn", 5)));

        register(BREAD = parser.loadData(new FoodItem(tutEngine, "bread", 5)));
        register(APPLE = parser.loadData(new FoodItem(tutEngine, "apple", 6)));

        register(RAW_PORK = parser.loadData(new FoodItem(tutEngine, "raw_pork", 2)));
        register(COOKED_PORK = parser.loadData(new FoodItem(tutEngine, "cooked_pork", 4)));
        register(RAW_STEAK = parser.loadData(new FoodItem(tutEngine, "raw_steak", 3)));
        register(COOKED_STEAK = parser.loadData(new FoodItem(tutEngine, "cooked_steak", 6)));

        TutLauncher.LOGGER.info("Items registered!");
    }

    private static void register(Item item) {
        ITEMS.put(item.getId(), item);
    }

    public static Item getItemById(String id) {
        return ITEMS.get(id).newCopy();
    }

    public static List<Item> getItems() {
        return new ArrayList<>(ITEMS.values());
    }
}
