package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.IInteractable;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.state.StateOptions;
import coffeecatteam.theultimatetile.state.options.Keybind;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;

public class InventoryPlayer extends Inventory {
    protected EntityPlayer player;

    private boolean active = false;

    private int maxHotbarSize = 3;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    public InventoryPlayer(TheUltimateTile theUltimateTile, EntityPlayer player) {
        super(theUltimateTile);
        this.player = player;

        // Add inventory slots
        int xd = 161, yd = 330, x, y;
        int width = 48, height = 48;
        for (int i = 0; i < maxSize; i++) {
            x = xd + 54 * i;
            y = yd;
            if (i > maxSize / 2 - 1) {
                x -= width * 7 - 12;
                y += height + 5;
            }
            addSlot(i, x, y, width, height);
            getSlot(i).setSelector(Assets.SLOT_SELECTER);
        }

        // Add hotbar slots
        int hxd = (theUltimateTile.getWidth() / 2 - width / 2) - width - 6, hx;
        for (int i = 0; i < maxHotbarSize; i++) {
            hx = hxd + 54 * i;
            addSlot(i, hx, theUltimateTile.getHeight() - height - height / 2 + 13, width, height);
            getSlot(i).setSelector(Assets.HOTBAR_SELECTER);
        }

//        addItem(new ItemStack(Items.STICK, 5));
//        addItem(new ItemStack(Items.ROCK));
//        addItem(new ItemStack(Items.ROTTEN_FLESH,3));
//        addItem(new ItemStack(Items.LEAF));
//        addItem(new ItemStack(Items.COAL,5));
//        addItem(new ItemStack(Items.IRON_INGOT,3));
//        addItem(new ItemStack(Items.GOLD_INGOT,3));
//        addItem(new ItemStack(Items.DIAMOND,3));
//        addItem(new ItemStack(Items.WOODEN_SWORD,1));
//        addItem(new ItemStack(Items.WOODEN_PICK,1));
//        addItem(new ItemStack(Items.CARROT,10));
//        addItem(new ItemStack(Items.APPLE, 1));
    }

    @Override
    public void tick() {
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.E).getKeyCode()))
            active = !active;
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ESCAPE).getKeyCode()) && active)
            active = !active;

        if (active) {
            // Change select item
            boolean up = theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.W).getKeyCode());
            boolean down = theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.S).getKeyCode());
            boolean left = theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.A).getKeyCode());
            boolean right = theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.D).getKeyCode());

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
                    if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.R).getKeyCode())) {
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
            if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.Z).getKeyCode()))
                swapSlots(getSlot(inventorySelectedIndex), getSlot(maxSize + hotbarSelectedIndex));
        }
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.Q).getKeyCode()))
            dropItem(active, inventorySelectedIndex, hotbarSelectedIndex);

        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.ONE).getKeyCode()))
            hotbarSelectedIndex = 0;
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.TWO).getKeyCode()))
            hotbarSelectedIndex = 1;
        if (theUltimateTile.getKeyManager().keyJustPressed(StateOptions.OPTIONS.controls().get(Keybind.THREE).getKeyCode()))
            hotbarSelectedIndex = 2;
        if (hotbarSelectedIndex < maxSize + maxHotbarSize) {
            player.setEquippedItem(getSlot(maxSize + hotbarSelectedIndex).getStack());

            if (player.getEquippedItem() != null && player.getEquippedItem().getCount() <= 0)
                getSlot(maxSize + hotbarSelectedIndex).remove();
        }
    }

    private void swapSlots(Slot slot1, Slot slot2) {
        ItemStack s1 = slot1.getStack() != null ? slot1.getStack().copy() : null;
        ItemStack s2 = slot2.getStack() != null ? slot2.getStack().copy() : null;
        slot1.setStack(s2);
        slot2.setStack(s1);
    }

    private void dropItem(boolean active, int inventorySelectedIndex, int hotbarSelectedIndex) {
        float xOff = Tile.TILE_WIDTH / 4;
        float yOff = Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 4;
        if (getSlot(inventorySelectedIndex).getStack() != null) {
            if (active) {
                getSlot(inventorySelectedIndex).getStack().getItem().setPickedUp(false);
                theUltimateTile.getItemManager().addItem(getSlot(inventorySelectedIndex).remove(), player.getX() + xOff, player.getY() + yOff);
            } else {
                getSlot(hotbarSelectedIndex + maxHotbarSize).getStack().getItem().setPickedUp(false);
                theUltimateTile.getItemManager().addItem(getSlot(hotbarSelectedIndex + maxHotbarSize).remove(), player.getX() + xOff, player.getY() + yOff);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            // Render inventory backgorund
            int multiplier = 6;
            int width = 57 * multiplier;
            int height = 41 * multiplier;
            int x = theUltimateTile.getWidth() / 2 - width / 2;
            int y = theUltimateTile.getHeight() / 2 - height / 2;

            g.drawImage(Assets.INVENTORY, x, y, width, height, null);
            g.drawImage(Assets.PLAYER_IDLE[0], x + player.getWidth() / 2, y + player.getHeight() / 2, player.getWidth(), player.getHeight(), null);

            // Render inventory slots
            for (int i = 0; i < maxSize; i++) {
                getSlot(i).setSelected(i == inventorySelectedIndex);

                getSlot(i).render(g);
            }
        }

        // Render hotbar backgorund
        int barWidth = 28;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = theUltimateTile.getHeight() - height - 5;

        g.drawImage(Assets.HOTBAR, theUltimateTile.getWidth() / 2 - width / 2, y, width, height, null);

        for (int i = maxSize; i < maxSize + maxHotbarSize; i++) {
            // Render hotbar slots
            int selected = i - maxSize;
            getSlot(i).setSelected(selected == hotbarSelectedIndex);

            getSlot(i).render(g);
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

    public boolean isHotbarFull() {
        int size = 0;
        for (int i = 0; i < maxSize + maxHotbarSize; i++)
            if (getSlot(i).getStack() != null)
                size++;
        return size >= maxSize + maxHotbarSize;
    }

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }

    public void setTheUltimateTile(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
    }

    public boolean isActive() {
        return active;
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
}
