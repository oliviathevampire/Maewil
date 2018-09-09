package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.player.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.inventory.items.IInteractable;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.tiles.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class InventoryPlayer extends Inventory {
    protected EntityPlayer player;

    private boolean active = false;
    private List<ItemStack> hotbar;

    private int maxInvSize = 12, maxHotbarSize = 3;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    public InventoryPlayer(TheUltimateTile theUltimateTile, EntityPlayer player) {
        super(theUltimateTile);
        this.player = player;

        hotbar = new ArrayList<>();

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
        int xd = 161, yd = 330, x, y;
        int width = 48, height = 48;
        for (int i = 0; i < 12; i++) {
            x = xd + 54 * i;
            y = yd;
            if (i > maxInvSize / 2 - 1) {
                x -= width * 7 - 12;
                y += height + 5;
            }
            addSlot(i, x, y, width, height);
        }
    }

    @Override
    public void tick() {
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_E))
            active = !active;
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && active)
            active = !active;

        if (active) {
            // Change select item
            boolean up = theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_W);
            boolean down = theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_S);
            boolean left = theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_A);
            boolean right = theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_D);

            if (up || down) {
                inventorySelectedIndex += 6;
                if (inventorySelectedIndex > maxInvSize - 1)
                    inventorySelectedIndex -= maxInvSize;
            }
            if (left)
                inventorySelectedIndex -= 1;
            if (right)
                inventorySelectedIndex += 1;

            if (inventorySelectedIndex < 0)
                inventorySelectedIndex = maxInvSize - 1;
            if (inventorySelectedIndex > maxInvSize - 1)
                inventorySelectedIndex = 0;

            if (inventorySelectedIndex < slots.size()) {
                ItemStack stack = slots.get(inventorySelectedIndex).getStack();

                if (stack != null) {
                    // Check if item was interacted with
                    if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
                        if (stack.getItem() instanceof IInteractable)
                            if (((IInteractable) stack.getItem()).onInteracted(player))
                                stack.setCount(stack.getCount() - 1);
                    }

                    // Put item in inventory back into hotbar
                    if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
                        if (!isHotbarFull()) {
                            addStackToHotbar(stack);
                        } else {
                            for (ItemStack s : hotbar)
                                if (s.getId().equals(stack.getId()))
                                    if (s.getItem().isStackable())
                                        addStackToHotbar(stack);
                        }
                    }

                    // Check if item count is 0
                    if (stack.getCount() <= 0)
                        slots.get(inventorySelectedIndex).remove();
                }
            }

            // Put item in hotbar into inventory
            if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
                if (hotbarIsntEmpty()) {
                    ItemStack hStack = hotbar.get(hotbarSelectedIndex);
                    if (!isFull()) {
                        addItem(hStack);
                        hotbar.remove(hStack);
                    } else {
                        for (Slot s : slots) {
                            if (s.getStack().getId().equals(hStack.getId())) {
                                if (s.getStack().getItem().isStackable()) {
                                    addItem(hStack);
                                    hotbar.remove(hStack);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_Q))
            dropItem(active, inventorySelectedIndex, hotbarSelectedIndex);

        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_1))
            hotbarSelectedIndex = 0;
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_2))
            hotbarSelectedIndex = 1;
        if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_3))
            hotbarSelectedIndex = 2;
        if (hotbarSelectedIndex < hotbar.size()) {
            player.setEquippedItem(hotbar.get(hotbarSelectedIndex));

            if (player.getEquippedItem().getCount() <= 0)
                hotbar.remove(player.getEquippedItem());
        }
    }

    private void dropItem(boolean active, int inventorySelectedIndex, int hotbarSelectedIndex) {
        float xOff = Tile.TILE_WIDTH / 4;
        float yOff = Tile.TILE_HEIGHT + Tile.TILE_HEIGHT / 4;
        if (active) {
            slots.get(inventorySelectedIndex).getStack().getItem().setPickedUp(false);
            theUltimateTile.getItemManager().addItem(slots.get(inventorySelectedIndex).remove(), player.getX() + xOff, player.getY() + yOff);
        } else {
            hotbar.get(inventorySelectedIndex).getItem().setPickedUp(false);
            theUltimateTile.getItemManager().addItem(hotbar.remove(hotbarSelectedIndex), player.getX() + xOff, player.getY() + yOff);
        }
    }

    @Override
    public void render(Graphics g) {
        if (!active)
            return;

        int multiplier = 6;
        int width = 57 * multiplier;
        int height = 41 * multiplier;
        int x = theUltimateTile.getWidth() / 2 - width / 2;
        int y = theUltimateTile.getHeight() / 2 - height / 2;

        g.drawImage(Assets.INVENTORY, x, y, width, height, null);
        g.drawImage(Assets.PLAYER_IDLE[0], x + player.getWidth() / 2, y + player.getHeight() / 2, player.getWidth(), player.getHeight(), null);

        int itemWidth = 48;
        int itemHeight = 48;

        // RENDER SELECT SQUARE
        int c = 156;
        Color hover = new Color(c, c, c, 127);
        g.setColor(hover);
        int xPos = x + 12 + 54 * inventorySelectedIndex;
        int yPos = y + height - 115;
        if (inventorySelectedIndex > maxInvSize / 2 - 1) {
            xPos -= itemWidth * 7 - 12;
            yPos += 55;
        }
        int off = 12;
        g.drawImage(Assets.SLOT_SELECTER, xPos - off / 2, yPos - off / 2, itemWidth + off, itemHeight + off, null);
        super.render(g);
    }

    public void renderHotbar(Graphics g) {
        int barWidth = 28;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = theUltimateTile.getHeight() - height - 5;

        g.drawImage(Assets.HOTBAR, theUltimateTile.getWidth() / 2 - width / 2, y, width, height, null);

        int selectedWidth = 10;
        int selectedHeight = 10;
        int sWidth = selectedWidth * multiplier;
        int sHeight = selectedHeight * multiplier;

        int x = theUltimateTile.getWidth() / 2 - width / 2;
        g.drawImage(Assets.HOTBAR_SELECTER, x + hotbarSelectedIndex * (sWidth - multiplier), y, sWidth, sHeight, null);

        int itemWidth = 32;
        int itemHeight = 32;
        if (hotbar.size() > 0) {
            for (int i = 0; i < hotbar.size(); i++) {
                ItemStack stack = hotbar.get(i);
                int xPos = x + 14 + 54 * i;
                int yPos = theUltimateTile.getHeight() - itemHeight - itemHeight / 2;
                g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, false, false, Color.white, Assets.FONT_20);
            }
        }
    }

    public void addItem(ItemStack stackIn) {
        if (!isFull()) {
            for (Slot slot : slots) {
                if (slot.getStack() != null) {
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

    private void add(ItemStack stack) {
        for (Slot slot : slots) {
            if (slot.getStack() == null) {
                slot.setStack(stack);
                return;
            }
        }
    }

    public void addStackToHotbar(ItemStack stack) {
        for (ItemStack s : hotbar) {
            if (s.getId().equals(stack.getId())) {
                if (s.getItem().isStackable()) {
                    int size = s.getCount() + stack.getCount();
                    s.setCount(size);
                    stack.setCount(stack.getCount() - size);
                    return;
                }
            }
        }
        hotbar.add(stack);
        remove(stack);
    }

    private void remove(ItemStack stack) {
        for (Slot slot : slots)
            if (slot.getStack() == stack)
                slot.remove();
    }

    public boolean isFull() {
        int size = 0;
        for (Slot slot : slots)
            if (slot.getStack() != null)
                size++;
        return size >= maxInvSize;
    }

    public boolean isHotbarFull() {
        return hotbar.size() == maxHotbarSize;
    }

    public List<ItemStack> getHotbar() {
        return hotbar;
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
            return hotbar.get(hotbarSelectedIndex);
        return null;
    }

    public boolean hotbarIsntEmpty() {
        return hotbarSelectedIndex <= hotbar.size() - 1;
    }

    public void resetInventory() {
        for (Slot slot : slots) {
            slot.setStack(null);
        }
    }

    public void resetHotbar() {
        hotbar.clear();
    }

    public void resetAll() {
        resetInventory();
        resetHotbar();
    }
}
