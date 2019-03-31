package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.objs.ItemDataParser;
import coffeecatteam.theultimatetile.objs.items.tool.*;
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
        register(STICK = parser.loadData(new ItemBasic(tutEngine, "stick")));
        register(WOOD = parser.loadData(new ItemBasic(tutEngine, "wood")));
        register(BARK = parser.loadData(new ItemBasic(tutEngine, "bark")));

        register(LEAF = parser.loadData(new ItemBasic(tutEngine, "leaf")));
        register(ROCK = parser.loadData(new ItemBasic(tutEngine, "rock")));
        register(ROTTEN_FLESH = parser.loadData(new ItemBasic(tutEngine, "rotten_flesh")));
        register(BONE = parser.loadData(new ItemBasic(tutEngine, "bone")));
        register(BOUNCY_BALL = parser.loadData(new ItemBasic(tutEngine, "bouncy_ball")));
        register(WOOL_BUNDLE = parser.loadData(new ItemBasic(tutEngine, "wool_bundle")));
        register(BOOK = parser.loadData(new ItemBasic(tutEngine, "book")));

        register(COIN_PENNY = parser.loadData(new ItemCoin(tutEngine, "coin_penny", 0.25f)));
        register(COIN_IRON = parser.loadData(new ItemCoin(tutEngine, "coin_iron", 0.50f)));
        register(COIN_GOLD = parser.loadData(new ItemCoin(tutEngine, "coin_gold", 1.00f)));

        /*
         * Tools/Weapons
         */
        register(WOODEN_SWORD = parser.loadData(new ItemSword(tutEngine, "wooden_sword", 5)));
        register(WOODEN_AXE = parser.loadData(new ItemAxe(tutEngine, "wooden_axe", 6)));
        register(WOODEN_PICK = parser.loadData(new ItemPickaxe(tutEngine, "wooden_pick", 7)));
        register(WOODEN_SHOVEL = parser.loadData(new ItemShovel(tutEngine, "wooden_shovel", 5)));
        register(WOODEN_HOE = parser.loadData(new ItemHoe(tutEngine, "wooden_hoe", 4)));

        register(STONE_SWORD = parser.loadData(new ItemSword(tutEngine, "stone_sword", 7)));
        register(STONE_AXE = parser.loadData(new ItemAxe(tutEngine, "stone_axe", 8)));
        register(STONE_PICK = parser.loadData(new ItemPickaxe(tutEngine, "stone_pick", 9)));
        register(STONE_SHOVEL = parser.loadData(new ItemShovel(tutEngine, "stone_shovel", 7)));
        register(STONE_HOE = parser.loadData(new ItemHoe(tutEngine, "stone_hoe", 6)));

        /*
         * Minerals
         */
        register(COAL = parser.loadData(new ItemBasic(tutEngine, "coal")));
        register(IRON_INGOT = parser.loadData(new ItemBasic(tutEngine, "iron_ingot")));
        register(GOLD_INGOT = parser.loadData(new ItemBasic(tutEngine, "gold_ingot")));
        register(DIAMOND = parser.loadData(new ItemBasic(tutEngine, "diamond")));

        /*
         * Foods
         */
        register(CARROT = parser.loadData(new ItemFood(tutEngine, "carrot", 4)));
        register(WHEAT = parser.loadData(new ItemFood(tutEngine, "wheat", 2)));
        register(POTATO = parser.loadData(new ItemFood(tutEngine, "potato", 4)));
        register(TOMATO = parser.loadData(new ItemFood(tutEngine, "tomato", 4)));
        register(CORN = parser.loadData(new ItemFood(tutEngine, "corn", 5)));

        register(BREAD = parser.loadData(new ItemFood(tutEngine, "bread", 5)));
        register(APPLE = parser.loadData(new ItemFood(tutEngine, "apple", 6)));

        register(RAW_PORK = parser.loadData(new ItemFood(tutEngine, "raw_pork", 2)));
        register(COOKED_PORK = parser.loadData(new ItemFood(tutEngine, "cooked_pork", 4)));
        register(RAW_STEAK = parser.loadData(new ItemFood(tutEngine, "raw_steak", 3)));
        register(COOKED_STEAK = parser.loadData(new ItemFood(tutEngine, "cooked_steak", 6)));

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
