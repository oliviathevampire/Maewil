package io.github.vampirestudios.tdg.inventory;

import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.items.ItemStack;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Slot {

    private ItemStack stack;
    private int index, x, y, width, height;
    private int xPos, yPos, itemWidth, itemHeight;
    private float scale;

    private boolean isSelected = false;
    private Image selector = Assets.GUI_SLOT_SELECTER;

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
//        Assets.GUI_SLOT.draw(x, y, width, height);
        itemWidth = (int) ((width / 2 + width / 4) * scale);
        itemHeight = (int) ((height / 2 + height / 4) * scale);
        xPos = x + itemWidth / 4;
        yPos = y + itemHeight / 4;

        if (stack != null) {
            stack.getTexture().draw(xPos, yPos, itemWidth, itemHeight);
            if (stack.getCount() > 1)
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, false, false, Color.white, Assets.FONTS.get("20"));
        }

        if (isSelected) {
            selector.draw(x - 27, y - 10, width * 1.4F, height * 1.4F);
        }
    }

    public void postRender(Graphics g) {
        if (isSelected && stack != null)
            Text.drawString(g, stack.getItem().getName(), xPos + itemWidth / 2f, yPos + itemHeight, true, Color.white, Assets.FONTS.get("20"));
    }

    public Slot setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public Slot setSelector(Image selector) {
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
        return new Slot(this.index, this.x, this.y, this.width, this.height, this.scale);
    }
}
