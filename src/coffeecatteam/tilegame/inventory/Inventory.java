package coffeecatteam.tilegame.inventory;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.gfx.ImageLoader;
import coffeecatteam.tilegame.gfx.SpriteSheet;
import coffeecatteam.tilegame.items.Item;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Inventory {

    private Handler handler;
    private boolean active = false;
    private ArrayList<Item> inventoryItems;

    private int maxInvSize = 15, maxStackSize = 16;

    private SpriteSheet inventory = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/inventory.png"));

    public Inventory(Handler handler) {
        this.handler = handler;
        inventoryItems = new ArrayList<>();
    }

    public void tick() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
            active = !active;
        if (!active)
            return;

        System.out.println("Inventory:");
        for (Item i : inventoryItems) {
            System.out.println(i.getName() + " - " + i.getCount()); // P.34
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

        g.drawImage(inventory.crop(0, 0, width / multiplier, height / multiplier), x, y, width, height, null);
        g.drawImage(Assets.PLAYER_IDLE[0], x + player.getWidth() / 2, y + player.getHeight() / 2, player.getWidth(), player.getHeight(), null);
    }

    public void addItem(Item item) {
        for (Item i : inventoryItems) {
            if (i.getId() == item.getId()) {
                i.setCount(i.getCount() + item.getCount());
                return;
            }
        }
        inventoryItems.add(item);
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
