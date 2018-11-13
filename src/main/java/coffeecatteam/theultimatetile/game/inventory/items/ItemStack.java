package coffeecatteam.theultimatetile.game.inventory.items;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.awt.image.BufferedImage;

public class ItemStack {

    public static final int MAX_STACK_COUNT = 128;

    private Item item;
    private int count;

    public ItemStack(Item item) {
        this(item, 1);
    }

    public ItemStack(Item item, int count) {
        try {
            this.item = (Item) item.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            this.item = ItemManager.STICK;
        }
        setCount(count);
    }

    public ItemStack setPosition(int x, int y) {
        this.item.setPosition(x, y);
        return new ItemStack(this.item, this.count);
    }

    public ItemStack copy() {
        ItemStack itemStack = new ItemStack(this.item, this.count);
        return itemStack;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BufferedImage getTexture() {
        return this.item.getTexture();
    }

    public void setTexture(BufferedImage texture) {
        this.item.setTexture(texture);
    }

    public String getId() {
        return this.item.getId();
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
        if (this.count > MAX_STACK_COUNT)
            this.count = MAX_STACK_COUNT;
    }

    public void setTheUltimateTile(GameEngine gameEngine) {
        this.item.setGameEngine(gameEngine);
    }

    public void setPickedUp(boolean pickedUp) {
        this.item.setPickedUp(pickedUp);
    }
}
