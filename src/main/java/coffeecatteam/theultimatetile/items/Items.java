package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.gfx.Assets;

public class Items {

    public static Item STICK = new Item(Assets.ITEM_STICK, "stick", 0);
    public static Item LEAF = new Item(Assets.ITEM_LEAF, "leaf", 1);
    public static Item ROCK = new Item(Assets.ITEM_ROCK, "rock", 2);

    public static Item ROTTEN_FLESH = new Item(Assets.ITEM_ROTTEN_FLESH, "rotten_flesh", 3);
    public static Item BONE = new Item(Assets.ITEM_BONE, "bone", 4);

    public static Item WOODEN_SWORD = new ItemTool(Assets.ITEM_WOODEN_SWORD, "wooden_sword", 5, 5);
    public static Item WOODEN_PICK = new ItemTool(Assets.ITEM_WOODEN_PICK, "wooden_pick", 6, 7);
    public static Item WOODEN_HOE = new ItemTool(Assets.ITEM_WOODEN_HOE, "wooden_hoe", 7, 4);

    public static Item STONE_SWORD = new ItemTool(Assets.ITEM_STONE_SWORD, "stone_sword", 8, 7);
    public static Item STONE_PICK = new ItemTool(Assets.ITEM_STONE_PICK, "stone_pick", 9, 9);
    public static Item STONE_HOE = new ItemTool(Assets.ITEM_STONE_HOE, "stone_hoe", 10, 6);

    public static Item COAL = new Item(Assets.ITEM_COAL, "coal", 11);
    public static Item IRON_INGOT = new Item(Assets.ITEM_IRON_INGOT, "iron_ingot", 12);
    public static Item GOLD_INGOT = new Item(Assets.ITEM_GOLD_INGOT, "gold_ingot", 13);
    public static Item DIAMOND = new Item(Assets.ITEM_DIAMOND, "diamond", 14);

    public static Item CARROT = new ItemFood(Assets.ITEM_CARROT, "carrot", 15, 4);
    public static Item APPLE = new ItemFood(Assets.ITEM_APPLE, "apple", 16, 6);

    public static Item COIN_IRON = new Item(Assets.ITEM_COIN_IRON, "coin_iron", 17);
    public static Item COIN_GOLD = new Item(Assets.ITEM_COIN_GOLD, "coin_gold", 18);
}
