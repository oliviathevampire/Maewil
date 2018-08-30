package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.gfx.Assets;

public class Items {

    public static Item STICK = new Item(Assets.ITEM_STICK, "stick");
    public static Item LEAF = new Item(Assets.ITEM_LEAF, "leaf");
    public static Item ROCK = new Item(Assets.ITEM_ROCK, "rock");

    public static Item ROTTEN_FLESH = new Item(Assets.ITEM_ROTTEN_FLESH, "rotten_flesh");
    public static Item BONE = new Item(Assets.ITEM_BONE, "bone");
    public static Item BOUNCY_BALL = new Item(Assets.ITEM_BOUNCY_BALL, "bouncy_ball");

    public static Item WOODEN_SWORD = new ItemTool(Assets.ITEM_WOODEN_SWORD, "wooden_sword", 5);
    public static Item WOODEN_PICK = new ItemTool(Assets.ITEM_WOODEN_PICK, "wooden_pick", 7);
    public static Item WOODEN_HOE = new ItemTool(Assets.ITEM_WOODEN_HOE, "wooden_hoe", 4);

    public static Item STONE_SWORD = new ItemTool(Assets.ITEM_STONE_SWORD, "stone_sword", 7);
    public static Item STONE_PICK = new ItemTool(Assets.ITEM_STONE_PICK, "stone_pick", 9);
    public static Item STONE_HOE = new ItemTool(Assets.ITEM_STONE_HOE, "stone_hoe", 6);

    public static Item COAL = new Item(Assets.ITEM_COAL, "coal");
    public static Item IRON_INGOT = new Item(Assets.ITEM_IRON_INGOT, "iron_ingot");
    public static Item GOLD_INGOT = new Item(Assets.ITEM_GOLD_INGOT, "gold_ingot");
    public static Item DIAMOND = new Item(Assets.ITEM_DIAMOND, "diamond");

    public static Item CARROT = new ItemFood(Assets.ITEM_CARROT, "carrot", 4);
    public static Item APPLE = new ItemFood(Assets.ITEM_APPLE, "apple", 6);

    public static Item COIN_IRON = new Item(Assets.ITEM_COIN_IRON, "coin_iron");
    public static Item COIN_GOLD = new Item(Assets.ITEM_COIN_GOLD, "coin_gold");

    public static void init() {
        registerItem(STICK);
        registerItem(LEAF);
        registerItem(ROCK);

        registerItem(ROTTEN_FLESH);
        registerItem(BONE);
        registerItem(BOUNCY_BALL);

        registerItem(WOODEN_SWORD);
        registerItem(WOODEN_PICK);
        registerItem(WOODEN_HOE);

        registerItem(STONE_SWORD);
        registerItem(STONE_PICK);
        registerItem(STONE_HOE);

        registerItem(COAL);
        registerItem(IRON_INGOT);
        registerItem(GOLD_INGOT);
        registerItem(DIAMOND);

        registerItem(CARROT);
        registerItem(APPLE);

        registerItem(COIN_IRON);
        registerItem(COIN_GOLD);
    }

    private static void registerItem(Item item) {
        Item.items.put(item.getId(), item);
    }
}
