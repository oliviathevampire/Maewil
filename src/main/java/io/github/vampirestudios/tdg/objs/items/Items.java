package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.objs.ItemDataParser;
import io.github.vampirestudios.tdg.objs.items.tool.*;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.Identifier;
import io.github.vampirestudios.tdg.utils.registry.Registries;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Items {

    public static final List<Item> UPDATABLE_ITEMS = new ArrayList<>();

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
    public static Item COAL, IRON_INGOT, GOLD_INGOT, DIAMOND, EMERALD, AMETHYST;

    /*
     * Foods
     */
    public static Item CARROT, WHEAT, POTATO, CORN;
    public static Item BREAD, APPLE;
    public static Item RAW_PORK, COOKED_PORK, RAW_STEAK, COOKED_STEAK;

    public static void init(MaewilEngine maewilEngine) throws IOException, ParseException {
        ItemDataParser parser = new ItemDataParser();

        /*
         * General
         */
        register(STICK = parser.loadData(new BasicItem(maewilEngine, "stick")));
        register(WOOD = parser.loadData(new BasicItem(maewilEngine, "wood")));
        register(BARK = parser.loadData(new BasicItem(maewilEngine, "bark")));

        register(LEAF = parser.loadData(new BasicItem(maewilEngine, "leaf")));
        register(ROCK = parser.loadData(new BasicItem(maewilEngine, "rock")));
        register(ROTTEN_FLESH = parser.loadData(new BasicItem(maewilEngine, "rotten_flesh")));
        register(BONE = parser.loadData(new BasicItem(maewilEngine, "bone")));
        register(BOUNCY_BALL = parser.loadData(new BasicItem(maewilEngine, "bouncy_ball")));
        register(WOOL_BUNDLE = parser.loadData(new BasicItem(maewilEngine, "wool_bundle")));
        register(BOOK = parser.loadData(new BasicItem(maewilEngine, "book")));

        register(COIN_PENNY = parser.loadData(new CoinItem(maewilEngine, "coin_penny", 0.25f)));
        register(COIN_IRON = parser.loadData(new CoinItem(maewilEngine, "coin_iron", 0.50f)));
        register(COIN_GOLD = parser.loadData(new CoinItem(maewilEngine, "coin_gold", 1.00f)));

        /*
         * Tools/Weapons
         */
        register(WOODEN_SWORD = parser.loadData(new SwordItem(maewilEngine, "wooden_sword", 5)));
        register(WOODEN_AXE = parser.loadData(new AxeItem(maewilEngine, "wooden_axe", 6)));
        register(WOODEN_PICK = parser.loadData(new PickaxeItem(maewilEngine, "wooden_pick", 7)));
        register(WOODEN_SHOVEL = parser.loadData(new ShovelItem(maewilEngine, "wooden_shovel", 5)));
        register(WOODEN_HOE = parser.loadData(new HoeItem(maewilEngine, "wooden_hoe", 4)));

        register(STONE_SWORD = parser.loadData(new SwordItem(maewilEngine, "stone_sword", 7)));
        register(STONE_AXE = parser.loadData(new AxeItem(maewilEngine, "stone_axe", 8)));
        register(STONE_PICK = parser.loadData(new PickaxeItem(maewilEngine, "stone_pick", 9)));
        register(STONE_SHOVEL = parser.loadData(new ShovelItem(maewilEngine, "stone_shovel", 7)));
        register(STONE_HOE = parser.loadData(new HoeItem(maewilEngine, "stone_hoe", 6)));

        /*
         * Minerals
         */
        register(COAL = parser.loadData(new BasicItem(maewilEngine, "coal")));
        register(IRON_INGOT = parser.loadData(new BasicItem(maewilEngine, "iron_ingot")));
        register(GOLD_INGOT = parser.loadData(new BasicItem(maewilEngine, "gold_ingot")));
        register(DIAMOND = parser.loadData(new BasicItem(maewilEngine, "diamond")));
        register(EMERALD = parser.loadData(new BasicItem(maewilEngine, "emerald")));
        register(AMETHYST = parser.loadData(new BasicItem(maewilEngine, "amethyst")));

        /*
         * Foods
         */
        register(CARROT = parser.loadData(new FoodItem(maewilEngine, "carrot", 4)));
        register(WHEAT = parser.loadData(new FoodItem(maewilEngine, "wheat", 2)));
        register(POTATO = parser.loadData(new FoodItem(maewilEngine, "potato", 4)));
        register(CORN = parser.loadData(new FoodItem(maewilEngine, "corn", 5)));

        register(BREAD = parser.loadData(new FoodItem(maewilEngine, "bread", 5)));
        register(APPLE = parser.loadData(new FoodItem(maewilEngine, "apple", 6)));

        register(RAW_PORK = parser.loadData(new FoodItem(maewilEngine, "raw_pork", 2)));
        register(COOKED_PORK = parser.loadData(new FoodItem(maewilEngine, "cooked_pork", 4)));
        register(RAW_STEAK = parser.loadData(new FoodItem(maewilEngine, "raw_steak", 3)));
        register(COOKED_STEAK = parser.loadData(new FoodItem(maewilEngine, "cooked_steak", 6)));

        MaewilLauncher.LOGGER.info("Items registered!");
    }

    private static void register(Item item) {
        Registries.ITEMS.register(new Identifier(item.getId()), item);
    }

    public static Item getItemById(String id) {
        return Registries.ITEMS.get(new Identifier(id)).newCopy();
    }

}
