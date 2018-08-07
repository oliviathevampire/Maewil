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

    public Inventory(Handler handler) {
        this.handler = handler;
        inventory = new ArrayList<>();

        addItem(Items.STICK.createNew(5));
        addItem(Items.ROCK.createNew(maxStackSize));
        addItem(Items.ROCK.createNew(maxStackSize));
        addItem(Items.ROCK.createNew(5));
        addItem(Items.ROTTEN_FLESH.createNew(3));
        addItem(Items.COAL.createNew(5));
        addItem(Items.IRON_INGOT.createNew(3));
        addItem(Items.GOLD_INGOT.createNew(3));
        addItem(Items.DIAMOND.createNew(3));
        addItem(Items.WOODEN_SWORD.createNew(1));
        addItem(Items.STONE_SWORD.createNew(1));
    }

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
            active = !active;
        if (!active)
            return;
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

//        for (int j = 0; j < 2; j++) {
//            for (int i = 0; i < 6; i++) {
//                g.fillRect(x + 12 + 54 * i, y + height - 115 + j * 55, 48, 48);
//            }
//        }

        if (inventory.size() > 0) {
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                int xPos = x + 12 + 54 * i;
                int yPos = y + height - 115;
                if (i > inventory.size() / 2 + 1) {
                    xPos -= 48 * 7 - 12;
                    yPos += 55;
                }
                g.drawImage(item.getTexture(), xPos, yPos, 48, 48, null);
                Text.drawString(g, String.valueOf(item.getCount()), xPos, yPos + 15, Color.white, Assets.FONT);
            }
        }
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
