package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.inventory.Slot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Item implements Cloneable {

    public static Map<String, Item> items = new HashMap<>();

    public static final int WIDTH = 32, HEIGHT = 32;

    protected GameEngine gameEngine;
    protected BufferedImage texture;
    protected final String id;

    protected Rectangle bounds;

    protected float x, y;
    protected boolean pickedUp = false;
    protected boolean isStackable = true;

    public Item(BufferedImage texture, String id) {
        this.texture = texture;
        this.id = id;

        this.bounds = new Rectangle((int) this.x, (int) this.y, WIDTH, HEIGHT);
    }

    public void tick(int count) {
        if (this.gameEngine.getEntityManager().getPlayer().isActive()) {
            if (this.gameEngine.getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.bounds)) {
                if (!this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().isFull()) {
                    this.pickedUp = true;
                } else {
                    int hotbar = 0;
                    for (Slot slot : this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlots()) {
                        if (slot.getStack() != null) {
                            if (slot.getStack().getId().equals(this.id)) {
                                if (slot.getStack().getItem().isStackable()) {
                                    this.pickedUp = true;
                                }
                            } else {
                                hotbar++;
                            }
                        }
                    }

                    if (hotbar >= this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().size()) {
                        if (!this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().isHotbarFull()) {
                            this.pickedUp = true;
                        } else {
                            for (Slot slot : this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().getSlots()) {
                                if (slot.getStack() != null) {
                                    if (slot.getStack().getId().equals(this.id)) {
                                        if (slot.getStack().getItem().isStackable()) {
                                            this.pickedUp = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.pickedUp) {
            if (!this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().isFull())
                this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().addItem(new ItemStack(this, count));
            else
                this.gameEngine.getEntityManager().getPlayer().getInventoryPlayer().addStackToHotbar(new ItemStack(this, count));
        }

        this.bounds = new Rectangle((int) this.x, (int) this.y, WIDTH, HEIGHT);
    }

    public void render(Graphics g) {
        if (this.gameEngine == null)
            return;
        render(g, (int) (this.x - this.gameEngine.getCamera().getxOffset()), (int) (this.y - this.gameEngine.getCamera().getyOffset()));
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(this.texture, x, y, WIDTH, HEIGHT, null);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        this.bounds.x = x;
        this.bounds.y = y;
    }

    public GameEngine getGameEngine() {
        return this.gameEngine;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public BufferedImage getTexture() {
        return this.texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getId() {
        return this.id;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isPickedUp() {
        return this.pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isStackable() {
        return isStackable;
    }

    public void setStackable(boolean stackable) {
        isStackable = stackable;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
