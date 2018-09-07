package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.inventory.items.Items;

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
        stack = new ItemStack(Items.APPLE, ItemStack.MAX_STACK_COUNT);
    }

    public void render(Graphics g) {

        g.drawImage(Assets.SLOT, x, y, width, height, null);
        int itemWidth = (int) ((width / 2 + width / 4) * scale);
        int itemHeight = (int) ((height / 2 + height / 4) * scale);
        int xPos = x + itemWidth / 4;
        int yPos = y + itemHeight / 4;

        g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
        Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, false, false, Color.white, Assets.FONT_20);
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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
