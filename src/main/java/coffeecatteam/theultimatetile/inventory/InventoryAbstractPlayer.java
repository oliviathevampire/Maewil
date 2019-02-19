package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.inventory.items.IInteractable;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.controls.Keybind;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class InventoryAbstractPlayer extends Inventory {

    private static int maxHotbarSize = 3;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    protected boolean isDefault = false;

    public InventoryAbstractPlayer(TutEngine tutEngine, EntityPlayer player, String invName) {
        this(tutEngine, player, invName, 190, 360);
    }

    public InventoryAbstractPlayer(TutEngine tutEngine, EntityPlayer player, String invName, int xOff, int yOff) {
        super(tutEngine, player, invName);

        // Add inventory slots
        int xd = xOff, yd = yOff, x, y;
        int width = 48, height = 48;
        for (int i = 0; i < maxSize; i++) {
            x = xd + 54 * i;
            y = yd;
            if (i > maxSize / 2 - 1) {
                x -= width * 7 - 12;
                y += height + 5;
            }
            Slot s = new Slot(i, x, y, width, height);
            addSlot(s).setSelector(Assets.SLOT_SELECTER);
        }

        // Add hotbar slots
        int hxd = (this.tutEngine.getWidth() / 2 - width / 2) - width - 6, hx;
        for (int i = 0; i < maxHotbarSize; i++) {
            hx = hxd + 54 * i;
            Slot s = new Slot(i, hx, this.tutEngine.getHeight() - height - height / 2 + 13, width, height);
            addSlot(s).setSelector(Assets.HOTBAR_SELECTER);
        }
    }

    public static void copyItems(InventoryAbstractPlayer from, InventoryAbstractPlayer to, boolean includeNull) {
        for (int i = 0; i < maxSize + maxHotbarSize; i++) {
            Slot s1 = from.getSlot(i);
            Slot s2 = to.getSlot(i);

            if (includeNull)
                s2.setStack(s1.getStack());
            else {
                if (s1.getStack() != null) {
                    s2.setStack(s1.getStack().copy());
                }
            }
        }
    }

    @Override
    public void update(GameContainer container, int delta) {
//        if (!isDefault)
//            if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.E).getKeyCode() | StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()))
//                player.openCloseInventory(this);
//                //player.closeAllInventories();

        if (active) {
            // Change select item
            boolean up = tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.W).getKeyCode());
            boolean down = tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.S).getKeyCode());
            boolean left = tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.A).getKeyCode());
            boolean right = tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.D).getKeyCode());

            if (up || down) {
                inventorySelectedIndex += 6;
                if (inventorySelectedIndex > maxSize - 1)
                    inventorySelectedIndex -= maxSize;
            }
            if (left)
                inventorySelectedIndex -= 1;
            if (right)
                inventorySelectedIndex += 1;

            if (inventorySelectedIndex < 0)
                inventorySelectedIndex = maxSize - 1;
            if (inventorySelectedIndex > maxSize - 1)
                inventorySelectedIndex = 0;

            if (inventorySelectedIndex < slots.size()) {
                ItemStack stack = getSlot(inventorySelectedIndex).getStack();

                if (stack != null) {
                    // Check if item was interacted with
                    if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.R).getKeyCode())) {
                        if (stack.getItem() instanceof IInteractable)
                            if (((IInteractable) stack.getItem()).onInteracted(player))
                                stack.setCount(stack.getCount() - 1);
                    }

                    // Check if item count is 0
                    if (stack.getCount() <= 0)
                        getSlot(inventorySelectedIndex).remove();
                }
            }

            // Swap selected stacks
            if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.Z).getKeyCode()))
                swapSlots(getSlot(inventorySelectedIndex), getSlot(maxSize + hotbarSelectedIndex));
        }
        if (this.active)
            if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.Q).getKeyCode()))
                dropItem(active, inventorySelectedIndex, hotbarSelectedIndex);

        if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ONE).getKeyCode()))
            hotbarSelectedIndex = 0;
        if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.TWO).getKeyCode()))
            hotbarSelectedIndex = 1;
        if (tutEngine.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.THREE).getKeyCode()))
            hotbarSelectedIndex = 2;
        if (hotbarSelectedIndex < maxSize + maxHotbarSize) {
            player.setEquippedItem(getSlot(maxSize + hotbarSelectedIndex).getStack());

            if (player.getEquippedItem() != null && player.getEquippedItem().getCount() <= 0)
                getSlot(maxSize + hotbarSelectedIndex).remove();
        }
    }

    // Render inventory slots
    public void renderInventorySlots(Graphics g) {
        for (int i = 0; i < maxSize; i++) {
            getSlot(i).setSelected(i == inventorySelectedIndex);

            getSlot(i).render(g);
        }
    }

    public void renderHotbar(Graphics g) {
        // Render hotbar backgorund
        int barWidth = 28;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = tutEngine.getHeight() - height - 5;

        Assets.HOTBAR.draw(tutEngine.getWidth() / 2f - width / 2f, y, width, height);

        // Render hotbar slots
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++) {
            // Render hotbar slots
            int selected = i - maxSize;
            getSlot(i).setSelected(selected == hotbarSelectedIndex);

            getSlot(i).render(g);
        }
    }

    public void dropItem(boolean active, int inventorySelectedIndex, int hotbarSelectedIndex) {
        float xOff = Tile.TILE_WIDTH / 4f;
        float yOff = Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 4f;
        if (getSlot(inventorySelectedIndex).getStack() != null) {
            if (active) {
                getSlot(inventorySelectedIndex).getStack().getItem().setPickedUp(false);
                tutEngine.getItemManager().addItem(getSlot(inventorySelectedIndex).remove(), player.getX() + xOff, player.getY() + yOff);
            } else {
                getSlot(hotbarSelectedIndex + maxHotbarSize).getStack().getItem().setPickedUp(false);
                tutEngine.getItemManager().addItem(getSlot(hotbarSelectedIndex + maxHotbarSize).remove(), player.getX() + xOff, player.getY() + yOff);
            }
        }
    }

    public void addItem(ItemStack stackIn) {
        if (!isFull()) {
            for (Slot slot : getSlots()) {
                if (slot.getStack() != null && stackIn != null) {
                    if (slot.getStack().getId().equals(stackIn.getId())) {
                        if (slot.getStack().getItem().isStackable()) {
                            if (slot.getStack().getCount() != ItemStack.MAX_STACK_COUNT) {
                                int size = slot.getStack().getCount() + stackIn.getCount();
                                if (size > ItemStack.MAX_STACK_COUNT) {
                                    int extra = size - ItemStack.MAX_STACK_COUNT;
                                    ItemStack extraStack = new ItemStack(slot.getStack().getItem(), extra);
                                    if (!isFull()) {
                                        add(extraStack);
                                        slot.getStack().setCount(size);
                                    }
                                } else
                                    slot.getStack().setCount(size);
                                return;
                            }
                        }
                    }
                }
            }
            add(stackIn);
        }
    }

    public void addStackToHotbar(ItemStack stack) {
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++) {
            ItemStack stack1 = getSlot(i).getStack();

            if (stack1 != null) {
                if (stack1.getId().equals(stack.getId())) {
                    if (stack1.getItem().isStackable()) {
                        int size = stack1.getCount() + stack.getCount();
                        stack1.setCount(size);
                        stack.setCount(stack.getCount() - size);
                        return;
                    }
                }
            } else {
                getSlot(i).setStack(stack);
                removeFromInventory(stack);
                return;
            }
        }
    }

    private void removeFromInventory(ItemStack stack) {
        for (int i = 0; i < maxSize; i++)
            if (getSlot(i).getStack() == stack)
                getSlot(i).remove();
    }

    public boolean isPInventoryFull() {
        int size = 0;
        for (int i = 0; i < maxSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size >= maxSize;
    }

    public boolean isHotbarFull() {
        int size = 0;
        for (int i = maxSize; i < maxSize + maxHotbarSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size >= maxSize + maxHotbarSize;
    }

    public ItemStack getSelectedHotbarItemStack() {
        if (hotbarIsntEmpty())
            return getSlot(maxSize + hotbarSelectedIndex).getStack();
        return null;
    }

    public boolean hotbarIsntEmpty() {
        int size = 0;
        for (int i = 0; i < maxSize + maxHotbarSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size <= maxSize + maxHotbarSize;
    }

    public int size() {
        int size = 0;
        for (Slot slot : slots)
            if (slot.getStack() != null)
                size++;
        return size;
    }

    public int getInventorySelectedIndex() {
        return inventorySelectedIndex;
    }

    public void setInventorySelectedIndex(int inventorySelectedIndex) {
        this.inventorySelectedIndex = inventorySelectedIndex;
    }

    public int getHotbarSelectedIndex() {
        return hotbarSelectedIndex;
    }

    public void setHotbarSelectedIndex(int hotbarSelectedIndex) {
        this.hotbarSelectedIndex = hotbarSelectedIndex;
    }

    @Override
    public void onOpen() {
        copyItems(tutEngine.getEntityManager().getPlayer().getInventoryPlayer(), this, false);
    }

    @Override
    public void onClose() {
        copyItems(this, tutEngine.getEntityManager().getPlayer().getInventoryPlayer(), false);
    }
}
