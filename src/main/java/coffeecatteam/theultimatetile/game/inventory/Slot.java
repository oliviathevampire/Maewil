package coffeecatteam.theultimatetile.game.inventory;

import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Slot {

    private ItemStack stack;
    private int index, x, y, width, height;
    private float scale;

    private boolean isSelected = false;
    private BufferedImage selector = Assets.SLOT_SELECTER;

    public Slot(int index, int x, int y, int width, int height) {
        this(index, x, y, width, height, 1f);
    }

    public Slot(int index, int x, int y, int width, int height, float scale) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public void render(Graphics g) {
        g.drawImage(Assets.SLOT, x, y, width, height, null);
        int itemWidth = (int) ((width / 2 + width / 4) * scale);
        int itemHeight = (int) ((height / 2 + height / 4) * scale);
        int xPos = x + itemWidth / 4;
        int yPos = y + itemHeight / 4;

        if (stack != null) {
            g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
            if (stack.getCount() > 1)
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, false, false, Color.white, Assets.FONT_20);
        }

        if (isSelected) {
            int off = 12;
            g.drawImage(selector, x - off / 2, y - off / 2, width + off, height + off, null);
        }
    }

    public Slot setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public Slot setSelector(BufferedImage selector) {
        this.selector = selector;
        return this;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public int getIndex() {
        return index;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Slot setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }

    public ItemStack remove() {
        if (getStack() != null) {
            ItemStack c = getStack().copy();
            setStack(null);
            return c;
        }
        return null;
    }

    public Slot copy() {
        Slot slot = new Slot(this.index, this.x, this.y, this.width, this.height, this.scale);
        return slot;
    }
}
