package coffeecatteam.theultimatetile.inventory;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.items.ItemStack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private Handler handler;
    private boolean active = false;
    private List<ItemStack> inventory;
    private List<ItemStack> hotbar;

    private int maxInvSize = 12, maxHotbarSize = 3;
    private int selectedIndex = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventory = new ArrayList<>();
        hotbar = new ArrayList<>();

//        addItem(new ItemStack(Items.STICK, 5));
//        addItem(new ItemStack(Items.ROCK, maxStackSize));
//        addItem(new ItemStack(Items.ROTTEN_FLESH,3));
//        addItem(new ItemStack(Items.LEAF, maxStackSize));
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
        if (!active)
            return;

        // Change select item
        boolean up = handler.getKeyManager().keyJustPressed(KeyEvent.VK_W);
        boolean down = handler.getKeyManager().keyJustPressed(KeyEvent.VK_S);
        boolean left = handler.getKeyManager().keyJustPressed(KeyEvent.VK_A);
        boolean right = handler.getKeyManager().keyJustPressed(KeyEvent.VK_D);

        if (up || down) {
            selectedIndex += 6;
            if (selectedIndex > maxInvSize - 1)
                selectedIndex -= maxInvSize;
        }
        if (left)
            selectedIndex -= 1;
        if (right)
            selectedIndex += 1;

        if (selectedIndex < 0)
            selectedIndex = maxInvSize - 1;
        if (selectedIndex > maxInvSize - 1)
            selectedIndex = 0;

        if (selectedIndex < inventory.size()) {
            ItemStack stack = inventory.get(selectedIndex);

            // Check if item was interacted with
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
                if (stack.getItem().onInteracted(handler.getWorld().getEntityManager().getPlayer()))
                    stack.setCount(stack.getCount() - 1);
            }

            // Check if item was put into hotbar
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q)) {
                if (hotbar.size() <= maxHotbarSize - 1) {
                    hotbar.add(stack);
                    inventory.remove(stack);
                }
            }

            // Check if item count is 0
            if (stack.getCount() <= 0)
                inventory.remove(selectedIndex);
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
        EntityPlayer player = handler.getWorld().getEntityManager().getPlayer();

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
        int xPos = x + 12 + 54 * selectedIndex;
        int yPos = y + height - 115;
        if (selectedIndex > maxInvSize / 2 - 1) {
            xPos -= itemWidth * 7 - 12;
            yPos += 55;
        }
        int off = 12;
        g.drawImage(Assets.INVENTORY.crop(48, 48, 16, 16), xPos - off / 2, yPos - off / 2, itemWidth + off, itemHeight + off, null);
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

    public boolean isFull() {
        return inventory.size() == maxInvSize;
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
}
