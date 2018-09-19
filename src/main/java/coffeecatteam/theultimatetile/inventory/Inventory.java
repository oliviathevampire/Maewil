package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {

    protected TheUltimateTile theUltimateTile;
    protected List<Slot> slots;
    protected int maxSize = 12;

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

    public Slot getSlot(ItemStack stack) {
        Slot s = null;
        for (Slot slot : slots)
            if (slot.getStack() == stack)
                return s = slot;
        return s;
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

    public List<Slot> getSlots() {
        return slots;
    }

    protected void add(ItemStack stack) {
        for (Slot slot : slots) {
            if (slot.getStack() == null) {
                slot.setStack(stack);
                return;
            }
        }
    }

    protected void remove(ItemStack stack) {
        for (Slot slot : slots)
            if (slot.getStack() == stack)
                slot.remove();
    }

    public boolean isFull() {
        int size = 0;
        for (Slot slot : slots)
            if (slot.getStack() != null)
                size++;
        return size > maxSize;
    }

    public void clearInventory() {
        for (Slot slot : slots) {
            slot.setStack(null);
        }
    }
}
