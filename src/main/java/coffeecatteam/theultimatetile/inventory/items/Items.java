package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.gfx.Assets;

public class Items {

    public static Item STICK = new Item(Assets.ITEM_STICK, "stick");
    public static Item LEAF = new Item(Assets.ITEM_LEAF, "leaf");
    public static Item ROCK = new Item(Assets.ITEM_ROCK, "rock");

    public static Item ROTTEN_FLESH = new Item(Assets.ITEM_ROTTEN_FLESH, "rotten_flesh");
    public static Item BONE = new Item(Assets.ITEM_BONE, "bone");
    public static Item BOUNCY_BALL = new Item(Assets.ITEM_BOUNCY_BALL, "bouncy_ball");

    public static Item WOODEN_SWORD = new ItemTool(Assets.ITEM_WOODEN_SWORD, "wooden_sword", 5, ItemTool.ToolType.SOWRD);
    public static Item WOODEN_PICK = new ItemTool(Assets.ITEM_WOODEN_PICK, "wooden_pick", 7, ItemTool.ToolType.PICKAXE);
    public static Item WOODEN_HOE = new ItemTool(Assets.ITEM_WOODEN_HOE, "wooden_hoe", 4, ItemTool.ToolType.HOE);

    public static Item STONE_SWORD = new ItemTool(Assets.ITEM_STONE_SWORD, "stone_sword", 7, ItemTool.ToolType.SOWRD);
    public static Item STONE_PICK = new ItemTool(Assets.ITEM_STONE_PICK, "stone_pick", 9, ItemTool.ToolType.PICKAXE);
    public static Item STONE_HOE = new ItemTool(Assets.ITEM_STONE_HOE, "stone_hoe", 6, ItemTool.ToolType.HOE);

    public static Item COAL = new Item(Assets.ITEM_COAL, "coal");
    public static Item IRON_INGOT = new Item(Assets.ITEM_IRON_INGOT, "iron_ingot");
    public static Item GOLD_INGOT = new Item(Assets.ITEM_GOLD_INGOT, "gold_ingot");
    public static Item DIAMOND = new Item(Assets.ITEM_DIAMOND, "diamond");

    public static Item CARROT = new ItemFood(Assets.ITEM_CARROT, "carrot", 4);
    public static Item WHEAT = new ItemFood(Assets.ITEM_WHEAT, "wheat", 2);
    public static Item BREAD = new ItemFood(Assets.ITEM_BREAD, "bread", 5);
    public static Item APPLE = new ItemFood(Assets.ITEM_APPLE, "apple", 6);
    public static Item RAW_PORK = new ItemFood(Assets.ITEM_RAW_PORK, "raw_pork", 2);
    public static Item COOKED_PORK = new ItemFood(Assets.ITEM_COOKED_PORK, "cooked_pork", 4);
    public static Item RAW_STEAK = new ItemFood(Assets.ITEM_RAW_STEAK, "raw_steak", 3);
    public static Item COOKED_STEAK = new ItemFood(Assets.ITEM_COOKED_STEAK, "cooked_steak", 6);

    public static Item WOOL_BUNDLE = new Item(Assets.ITEM_WOOL_BUNDLE, "wool_bundle");

    public static Item COIN_PENNY = new ItemCoin(Assets.ITEM_COIN_PENNY, "coin_penny", 0.25f);
    public static Item COIN_IRON = new ItemCoin(Assets.ITEM_COIN_IRON, "coin_iron", 0.50f);
    public static Item COIN_GOLD = new ItemCoin(Assets.ITEM_COIN_GOLD, "coin_gold", 1.00f);

    public static void init() {
        registerItem(STICK);
        registerItem(LEAF);
        registerItem(ROCK);

        registerItem(ROTTEN_FLESH);
        registerItem(BONE);
        registerItem(BOUNCY_BALL);

        registerItem(WOODEN_SWORD, WOODEN_PICK, WOODEN_HOE);
        registerItem(STONE_SWORD, STONE_PICK, STONE_HOE);

        registerItem(COAL, IRON_INGOT, GOLD_INGOT, DIAMOND);

        registerItem(CARROT, WHEAT, BREAD, APPLE);
        registerItem(RAW_PORK, COOKED_PORK, RAW_STEAK, COOKED_STEAK);
        registerItem(WOOL_BUNDLE);

        registerItem(COIN_PENNY, COIN_IRON, COIN_GOLD);
    }

    private static void registerItem(Item... items) {
        for (Item item : items)
            registerItem(item);
    }

    private static void registerItem(Item item) {
        Item.items.put(item.getId(), item);
    }
}
