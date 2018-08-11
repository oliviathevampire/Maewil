package coffeecatteam.tilegame.inventory;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.Text;
import coffeecatteam.tilegame.items.Item;
import coffeecatteam.tilegame.items.Items;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;
    private boolean active = false;
    private ArrayList<Item> inventory;

    private int maxInvSize = 12, maxStackSize = 16;
    private int selectedIndex = 0;

    public Inventory(Handler handler) {
        this.handler = handler;
        inventory = new ArrayList<>();

        addItem(Items.STICK.createNew(5));
        addItem(Items.ROCK.createNew(maxStackSize));
        addItem(Items.ROCK.createNew(maxStackSize));
        addItem(Items.ROCK.createNew(5));
        addItem(Items.ROTTEN_FLESH.createNew(3));
        addItem(Items.LEAF.createNew(maxStackSize));
        addItem(Items.COAL.createNew(5));
        addItem(Items.IRON_INGOT.createNew(3));
        addItem(Items.GOLD_INGOT.createNew(3));
        addItem(Items.DIAMOND.createNew(3));
        addItem(Items.WOODEN_SWORD.createNew(1));
        addItem(Items.WOODEN_PICK.createNew(1));
        addItem(Items.CARROT.createNew(10));
        //addItem(Items.APPLE.createNew(10));
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
            Item i = inventory.get(selectedIndex);
            // Check if item was interacted with
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
                if (i.onInteracted(handler.getWorld().getEntityManager().getPlayer()))
                    i.setCount(i.getCount() - 1);
            }

            // Check if item count is 0
            if (i.getCount() <= 0)
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
                Item item = inventory.get(i);
                int xPos = x + 12 + 54 * i;
                int yPos = y + height - 115;
                if (i > maxInvSize / 2 - 1) {
                    xPos -= itemWidth * 7 - 12;
                    yPos += 55;
                }
                g.drawImage(item.getTexture(), xPos, yPos, itemWidth, itemHeight, null);
                Text.drawString(g, String.valueOf(item.getCount()), xPos, yPos + 15, Color.white, Assets.FONT);
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

    public void addItem(Item item) {
        for (Item i : inventory) {
            if (i.getId() == item.getId()) {
                int size = i.getCount() + item.getCount();
//                if (size > maxStackSize) {
//                    int stacks = size / maxStackSize;
//                    int extra = size - maxStackSize * stacks;
//                    for (int s = 0; s < stacks; s++) {
//                        Item newItem = i;
//                        newItem.setCount(maxStackSize);
//                        inventory.add(newItem);
//                    }
//                    Item extraItems = i;
//                    extraItems.setCount(extra);
//                    inventory.add(extraItems);
//                    return;
//                }
                i.setCount(size);
                return;
            }
        }
        inventory.add(item);
    }

    public boolean isFull() {
        return inventory.size() == maxInvSize;
    }

    public ArrayList<Item> getItems() {
        return inventory;
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
