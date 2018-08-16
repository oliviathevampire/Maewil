package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.items.IInteractable;
import coffeecatteam.theultimatetile.items.ItemStack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private Handler handler;
    protected EntityPlayer player;

    private boolean active = false;
    private List<ItemStack> inventory;
    private List<ItemStack> hotbar;

    private int maxInvSize = 12, maxHotbarSize = 3;
    private int inventorySelectedIndex = 0;
    private int hotbarSelectedIndex = 0;

    public Inventory(Handler handler, EntityPlayer player) {
        this.handler = handler;
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
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
            active = !active;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE) && active)
            active = !active;

        if (active) {
            // Change select item
            boolean up = handler.getKeyManager().keyJustPressed(KeyEvent.VK_W);
            boolean down = handler.getKeyManager().keyJustPressed(KeyEvent.VK_S);
            boolean left = handler.getKeyManager().keyJustPressed(KeyEvent.VK_A);
            boolean right = handler.getKeyManager().keyJustPressed(KeyEvent.VK_D);

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
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)) {
                    if (stack.getItem() instanceof IInteractable)
                        if (((IInteractable) stack.getItem()).onInteracted(player))
                            stack.setCount(stack.getCount() - 1);
                }

                // Put item in inventory back into hotbar
                if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z)) {
                    if (!isHotbarFull()) {
                        addStackToHotbar(stack);
                    } else {
                        for (ItemStack s : hotbar)
                            if (s.getId() == stack.getId())
                                if (s.getItem().isStackable())
                                    addStackToHotbar(stack);
                    }
                }

                // Check if item count is 0
                if (stack.getCount() <= 0)
                    inventory.remove(inventorySelectedIndex);
            }

            // Put item in hotbar into inventory
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
                if (hotbarIsntEmpty()) {
                    ItemStack hStack = hotbar.get(hotbarSelectedIndex);
                    if (!isFull()) {
                        addItem(hStack);
                        hotbar.remove(hStack);
                    } // else {
//                            for (ItemStack s : inventory) {
//                                if (s.getId() == hStack.getId()) {
//                                    if (s.getItem().isStackable()) {
//                                        addItem(hStack);
//                                        hotbar.remove(hStack);
//                                    }
//                                }
//                            }
//                        }
                }
            }
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1))
            hotbarSelectedIndex = 0;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_2))
            hotbarSelectedIndex = 1;
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_3))
            hotbarSelectedIndex = 2;
        if (hotbarSelectedIndex < player.getInventory().getHotbar().size()) {
            player.setEquippedItem(player.getInventory().getHotbar().get(hotbarSelectedIndex));

            if (player.getEquippedItem().getCount() <= 0)
                hotbar.remove(player.getEquippedItem());
        }
    }

    public void render(Graphics g) {
        if (!active)
            return;

        int multiplier = 6;
        int width = 57 * multiplier;
        int height = 41 * multiplier;
        int x = handler.getWidth() / 2 - width / 2;
        int y = handler.getHeight() / 2 - height / 2;

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
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, Color.white, Assets.FONT_20);
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
        int y = handler.getHeight() - height;

        g.drawImage(Assets.INVENTORY.crop(10, 54, barWidth, barHeight), handler.getWidth() / 2 - width / 2, y, width, height, null);

        int selectedWidth = 10;
        int selectedHeight = 10;
        int sWidth = selectedWidth * multiplier;
        int sHeight = selectedHeight * multiplier;

        int x = handler.getWidth() / 2 - width / 2;
        g.drawImage(Assets.INVENTORY.crop(0, 54, selectedWidth, selectedHeight), x + hotbarSelectedIndex * (sWidth - multiplier), y, sWidth, sHeight, null);

        int itemWidth = 32;
        int itemHeight = 32;
        if (handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().size() > 0) {
            for (int i = 0; i < handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().size(); i++) {
                ItemStack stack = handler.getWorld().getEntityManager().getPlayer().getInventory().getHotbar().get(i);
                int xPos = x + 14 + 54 * i;
                int yPos = handler.getHeight() - itemHeight - itemHeight / 2;
                g.drawImage(stack.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
                Text.drawString(g, String.valueOf(stack.getCount()), xPos, yPos + 15, Color.white, Assets.FONT_20);
            }
        }
    }

    public void addItem(ItemStack stack) {
        for (ItemStack s : inventory) {
            if (s.getId() == stack.getId()) {
                if (s.getItem().isStackable()) {
                    int size = s.getCount() + stack.getCount();
                    s.setCount(size);
                    return;
                }
            }
        }
        inventory.add(stack);
    }

    public void addStackToHotbar(ItemStack stack) {
        for (ItemStack s : hotbar) {
            if (s.getId() == stack.getId()) {
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

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ItemStack getSelectedHotbarItemStack() {
        if (hotbarIsntEmpty())
            return hotbar.get(hotbarSelectedIndex);
        return null;
    }

    public boolean hotbarIsntEmpty() {
        return hotbarSelectedIndex <= hotbar.size() - 1;
    }
}
