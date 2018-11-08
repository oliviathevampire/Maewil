package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.manager.InventoryManager;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Inventory {

    protected TheUltimateTile theUltimateTile;
    protected EntityPlayer player;
    protected String invName;

    protected List<Slot> slots;
    public static int maxSize = 12;

    protected boolean active = false;

    public Inventory(TheUltimateTile theUltimateTile, EntityPlayer player, String invName) {
        this.theUltimateTile = theUltimateTile;
        this.player = player;
        this.invName = invName;
        slots = new ArrayList<>();

        InventoryManager.INVENTORIES.add(this);
    }

    public void swapSlots(Slot slot1, Slot slot2) {
        ItemStack s1 = slot1.getStack() != null ? slot1.getStack().copy() : null;
        ItemStack s2 = slot2.getStack() != null ? slot2.getStack().copy() : null;
        slot1.setStack(s2);
        slot2.setStack(s1);
    }

    public void dropItem(boolean active, int selectedIndex) {
        float xOff = Tile.TILE_WIDTH / 4;
        float yOff = Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 4;
        if (getSlot(selectedIndex).getStack() != null) {
            if (active) {
                getSlot(selectedIndex).getStack().getItem().setPickedUp(false);
                theUltimateTile.getItemManager().addItem(getSlot(selectedIndex).remove(), player.getX() + xOff, player.getY() + yOff);
            }
        }
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
                s = slot;
        return s;
    }

    protected Slot addSlot(int index, int x, int y) {
        return addSlot(index, x, y, 48, 48);
    }

    protected Slot addSlot(int index, int x, int y, int width, int height) {
        return addSlot(new Slot(index, x, y, width, height));
    }

    protected Slot addSlot(Slot slot) {
        if (!slot.isSelected())
            slot.setSelected(false);
        slots.add(slot);
        return slots.get(slots.size() - 1);
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;

        if (this.active)
            onOpen();
        else
            onClose();
    }

    public void onOpen() {
    }

    public void onClose() {
    }

    public String getInvName() {
        return invName;
    }
}
