package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Items {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();

    /*
     * General
     */
    public static Item STICK, LEAF, ROCK;
    public static Item ROTTEN_FLESH, BONE, BOUNCY_BALL, WOOL_BUNDLE;
    public static Item BOOK;

    public static ItemCoin COIN_PENNY, COIN_IRON, COIN_GOLD;

    /*
     * Tools/Weapons
     */
    public static ItemTool WOODEN_SWORD, WOODEN_PICK, WOODEN_HOE;
    public static ItemTool STONE_SWORD, STONE_PICK, STONE_HOE;

    /*
     * Minerals
     */
    public static Item COAL, IRON_INGOT, GOLD_INGOT, DIAMOND;

    /*
     * Foods
     */
    public static ItemFood CARROT, WHEAT, POTATO, TOMATO, CORN;
    public static ItemFood BREAD, APPLE;
    public static ItemFood RAW_PORK, COOKED_PORK, RAW_STEAK, COOKED_STEAK;

    public static void init(TutEngine tutEngine) throws IOException, ParseException {
        /*
         * General
         */
        register(STICK = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "stick")));
        register(LEAF = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "leaf")));
        register(ROCK = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "rock")));
        register(ROTTEN_FLESH = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "rotten_flesh")));
        register(BONE = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "bone")));
        register(BOUNCY_BALL = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "bouncy_ball")));
        register(WOOL_BUNDLE = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "wool_bundle")));
        register(BOOK = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "book")));

        register(COIN_PENNY = ItemDataParser.loadItemData(new ItemCoin(tutEngine, "coin_penny", 0.25f)));
        register(COIN_IRON = ItemDataParser.loadItemData(new ItemCoin(tutEngine, "coin_iron", 0.50f)));
        register(COIN_GOLD = ItemDataParser.loadItemData(new ItemCoin(tutEngine, "coin_gold", 1.00f)));

        /*
         * Tools/Weapons
         */
        register(WOODEN_SWORD = ItemDataParser.loadItemData(new ItemTool(tutEngine, "wooden_sword", 5, ItemTool.ToolType.SOWRD)));
        register(WOODEN_PICK = ItemDataParser.loadItemData(new ItemTool(tutEngine, "wooden_pick", 7, ItemTool.ToolType.PICKAXE)));
        register(WOODEN_HOE = ItemDataParser.loadItemData(new ItemTool(tutEngine, "wooden_hoe", 4, ItemTool.ToolType.HOE)));

        register(STONE_SWORD = ItemDataParser.loadItemData(new ItemTool(tutEngine, "stone_sword", 7, ItemTool.ToolType.SOWRD)));
        register(STONE_PICK = ItemDataParser.loadItemData(new ItemTool(tutEngine, "stone_pick", 9, ItemTool.ToolType.PICKAXE)));
        register(STONE_HOE = ItemDataParser.loadItemData(new ItemTool(tutEngine, "stone_hoe", 6, ItemTool.ToolType.HOE)));

        /*
         * Minerals
         */
        register(COAL = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "coal")));
        register(IRON_INGOT = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "iron_ingot")));
        register(GOLD_INGOT = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "gold_ingot")));
        register(DIAMOND = ItemDataParser.loadItemData(new ItemBasic(tutEngine, "diamond")));

        /*
         * Foods
         */
        register(CARROT = ItemDataParser.loadItemData(new ItemFood(tutEngine, "carrot", 4)));
        register(WHEAT = ItemDataParser.loadItemData(new ItemFood(tutEngine, "wheat", 2)));
        register(POTATO = ItemDataParser.loadItemData(new ItemFood(tutEngine, "potato", 4)));
        register(TOMATO = ItemDataParser.loadItemData(new ItemFood(tutEngine, "tomato", 4)));
        register(CORN = ItemDataParser.loadItemData(new ItemFood(tutEngine, "corn", 5)));

        register(BREAD = ItemDataParser.loadItemData(new ItemFood(tutEngine, "bread", 5)));
        register(APPLE = ItemDataParser.loadItemData(new ItemFood(tutEngine, "apple", 6)));

        register(RAW_PORK = ItemDataParser.loadItemData(new ItemFood(tutEngine, "raw_pork", 2)));
        register(COOKED_PORK = ItemDataParser.loadItemData(new ItemFood(tutEngine, "cooked_pork", 4)));
        register(RAW_STEAK = ItemDataParser.loadItemData(new ItemFood(tutEngine, "raw_steak", 3)));
        register(COOKED_STEAK = ItemDataParser.loadItemData(new ItemFood(tutEngine, "cooked_steak", 6)));

        TutEngine.getTutEngine().getLogger().print("Items registered!");
    }

    private static void register(Item item) {
        ITEMS.put(item.getId(), item);
    }

    public static Item getItemById(String id) {
        return ITEMS.get(id);
    }

    /*
     * Item Manager
     */
    private TutEngine tutEngine;
    private static final List<ItemStack> ITEM_STACKS = new ArrayList<>();

    public Items(TutEngine tutEngine) {
        this.tutEngine = tutEngine;
    }

    public void update(GameContainer container, int delta) {
        Iterator<ItemStack> it = ITEM_STACKS.iterator();
        while (it.hasNext()) {
            ItemStack stack = it.next();
            stack.getItem().update(stack.getCount());
            if (stack.getItem().isPickedUp())
                it.remove();
        }
    }

    public void render(Graphics g) {
        for (ItemStack stack : ITEM_STACKS) {
            stack.getItem().render(g);
            if (stack.getCount() > 1)
                Text.drawString(g, String.valueOf(stack.getCount()), (int) (stack.getItem().getPosition().x - this.tutEngine.getCamera().getxOffset()), (int) (stack.getItem().getPosition().y + 15 - this.tutEngine.getCamera().getyOffset()), false, false, Color.white, Assets.FONTS.get("20"));
        }
    }

    public void addItem(ItemStack stack, float x, float y) {
        addItem(stack, (int) x, (int) y);
    }

    public void addItem(ItemStack stack, int x, int y) {
        stack.setPosition(x, y);
        addItem(stack);
    }

    public void addItem(ItemStack stack) {
        ITEM_STACKS.add(stack);
    }

    public List<ItemStack> getITEMS() {
        return ITEM_STACKS;
    }

    public void reset() {
        ITEM_STACKS.clear();
    }
}
