package coffeecatteam.tilegame.items;

import coffeecatteam.tilegame.gfx.Assets;

public class Items {

    public static Item STICK = new Item(Assets.ITEM_STICK, "stick", 0);
    public static Item ROCK = new Item(Assets.ITEM_ROCK, "rock", 1);
    public static Item ROTTEN_FLESH = new Item(Assets.ITEM_ROTTEN_FLESH, "rotten_flesh", 2);
    public static Item LEAF = new Item(Assets.ITEM_LEAF, "leaf", 3);

    public static Item WOODEN_SWORD = new Item(Assets.ITEM_WOODEN_SWORD, "wooden_sword", 4);
    public static Item STONE_SWORD = new Item(Assets.ITEM_STONE_SWORD, "stone_sword", 5);

    public static Item WOODEN_PICK = new Item(Assets.ITEM_WOODEN_PICK, "wooden_pick", 6);
    public static Item STONE_PICK = new Item(Assets.ITEM_STONE_PICK, "stone_pick", 7);

    public static Item COAL = new Item(Assets.ITEM_COAL, "coal", 8);
    public static Item IRON_INGOT = new Item(Assets.ITEM_IRON_INGOT, "iron_ingot", 9);
    public static Item GOLD_INGOT = new Item(Assets.ITEM_GOLD_INGOT, "gold_ingot", 10);
    public static Item DIAMOND = new Item(Assets.ITEM_DIAMOND, "diamond", 11);

    public static Item CARROT = new ItemFood(Assets.ITEM_CARROT, "carrot", 12, 4);
    public static Item APPLE = new ItemFood(Assets.ITEM_APPLE, "apple", 13, 6);
}
