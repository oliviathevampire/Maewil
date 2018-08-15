package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;

    public ItemManager(Handler handler) {
        this.handler = handler;
        items = new ArrayList<>();
    }

    public void tick() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item i = it.next();
            i.tick();
            if (i.isPickedUp())
                it.remove();
        }
    }

    public void render(Graphics g) {
        for (Item i : items)
            i.render(g);
    }

    public void addItem(ItemStack stack, int x, int y) {
        stack.setPosition(x + Tile.TILE_WIDTH, y + Tile.TILE_HEIGHT);
        addItem(stack);
    }

    public void addItem(ItemStack stack) {
        stack.setHandler(handler);
        items.add(stack.getItem());
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
