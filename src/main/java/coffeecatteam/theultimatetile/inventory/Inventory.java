package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {

    protected TheUltimateTile theUltimateTile;
    private List<Slot> slots;

    public Inventory(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
        slots = new ArrayList<>();
    }

    public ItemStack getSlotStack(int index) {
        return getSlot(index).getStack();
    }

    public Slot getSlot(int index) {
        return slots.get(index);
    }

    protected void addSlot(Slot slot) {
        slot.setSelected(false);
        slots.add(slot);
    }

    protected void addSlot(int index, int x, int y, int width, int height) {
        Slot slot = new Slot(index, x, y, width, height);
        slot.setSelected(false);
        slots.add(slot);
    }

    public abstract void tick();

    public void render(Graphics g) {
        slots.forEach(s -> s.render(g));
    }
}
