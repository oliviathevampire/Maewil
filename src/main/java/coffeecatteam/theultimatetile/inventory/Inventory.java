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

public class Inventory {

    private TheUltimateTile theUltimateTile;
    protected EntityPlayer player;

    private boolean active = false;
    private List<ItemStack> inventory;
    private List<ItemStack> hotbar;

    private int maxInvSize = 12, maxHotbarSize = 3;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    public Inventory(TheUltimateTile theUltimateTile, EntityPlayer player) {
        this.theUltimateTile = theUltimateTile;
        this.player = player;

        inventory = new ArrayList<>();
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
    }

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

            if (inventorySelectedIndex < inventory.size()) {
                ItemStack stack = inventory.get(inventorySelectedIndex);

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
                    inventory.remove(inventorySelectedIndex);
            }

            // Put item in hotbar into inventory
            if (theUltimateTile.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
                if (hotbarIsntEmpty()) {
                    ItemStack hStack = hotbar.get(hotbarSelectedIndex);
                    if (!isFull()) {
                        addItem(hStack);
                        hotbar.remove(hStack);
                    } else {
                        for (ItemStack s : inventory) {
                            if (s.getId().equals(hStack.getId())) {
                                if (s.getItem().isStackable()) {
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
            inventory.get(inventorySelectedIndex).getItem().setPickedUp(false);
            theUltimateTile.getTheUltimateTile().getItemManager().addItem(inventory.remove(inventorySelectedIndex), player.getX() + xOff, player.getY() + yOff);
        } else {
            hotbar.get(inventorySelectedIndex).getItem().setPickedUp(false);
            theUltimateTile.getTheUltimateTile().getItemManager().addItem(hotbar.remove(hotbarSelectedIndex), player.getX() + xOff, player.getY() + yOff);
        }
    }

    public void render(Graphics g) {
        if (!active)
            return;

        int multiplier = 6;
        int width = 57 * multiplier;
        int height = 41 * multiplier;
        int x = theUltimateTile.getWidth() / 2 - width / 2;
        int y = theUltimateTile.getHeight() / 2 - height / 2;

        g.drawImage(Assets.INVENTORY.crop(0, 0, width / multiplier, height / multiplier), x, y, width, height, null);
        g.drawImage(Assets.PLAYER_IDLE[0], x + player.getWidth() / 2, y + player.getHeight() / 2, player.getWidth(), player.getHeight(), null);

        int itemWidth = 48;
        int itemHeight = 48;
        if (inventory.size() > 0) {
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.get(i);
                int xPos = x + 12 + 54 * i;
                int yPos = y + height - 115;
                if (i > maxInvSize / 2 - 1) {
                    xPos -= itemWidth * 7 - 12;
                    yPos += 55;
                }
                g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, false, false, Color.white, Assets.FONT_20);
            }
        }

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
        g.drawImage(Assets.INVENTORY.crop(48, 48, 16, 16), xPos - off / 2, yPos - off / 2, itemWidth + off, itemHeight + off, null);
    }

    public void renderHotbar(Graphics g) {
        int barWidth = 28;
        int barHeight = 10;

        int multiplier = 6;
        int width = barWidth * multiplier;
        int height = barHeight * multiplier;
        int y = theUltimateTile.getHeight() - height;

        g.drawImage(Assets.INVENTORY.crop(10, 54, barWidth, barHeight), theUltimateTile.getWidth() / 2 - width / 2, y, width, height, null);

        int selectedWidth = 10;
        int selectedHeight = 10;
        int sWidth = selectedWidth * multiplier;
        int sHeight = selectedHeight * multiplier;

        int x = theUltimateTile.getWidth() / 2 - width / 2;
        g.drawImage(Assets.INVENTORY.crop(0, 54, selectedWidth, selectedHeight), x + hotbarSelectedIndex * (sWidth - multiplier), y, sWidth, sHeight, null);

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
            for (ItemStack stack : inventory) {
                if (stack.getId().equals(stackIn.getId())) {
                    if (stack.getItem().isStackable()) {
                        if (stack.getCount() != ItemStack.MAX_STACK_COUNT) {
                            int size = stack.getCount() + stackIn.getCount();
                            if (size > ItemStack.MAX_STACK_COUNT) {
                                int extra = size - ItemStack.MAX_STACK_COUNT;
                                ItemStack extraStack = new ItemStack(stack.getItem(), extra);
                                if (!isFull()) {
                                    inventory.add(extraStack);
                                    stack.setCount(size);
                                }
                            } else
                                stack.setCount(size);
                            return;
                        }
                    }
                }
            }
            inventory.add(stackIn);
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
        inventory.remove(stack);
    }

    public boolean isFull() {
        return inventory.size() == maxInvSize;
    }

    public boolean isHotbarFull() {
        return hotbar.size() == maxHotbarSize;
    }

    public List<ItemStack> getItems() {
        return inventory;
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
        inventory.clear();
    }

    public void resetHotbar() {
        hotbar.clear();
    }

    public void resetAll() {
        resetInventory();
        resetHotbar();
    }
}
