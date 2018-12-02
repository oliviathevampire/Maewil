package coffeecatteam.theultimatetile.game.inventory.items;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.inventory.Slot;

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

    protected AABB bounds;

    protected Vector2D position = new Vector2D();
    protected boolean pickedUp = false;
    protected boolean isStackable = true;

    public Item(BufferedImage texture, String id) {
        this.texture = texture;
        this.id = id;

        this.bounds = new AABB(this.position, WIDTH, HEIGHT);
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

        this.bounds = new AABB(this.position, WIDTH, HEIGHT);
    }

    public void render(Graphics2D g) {
        if (this.gameEngine == null)
            return;
        render(g, (int) (this.position.x - this.gameEngine.getCamera().getxOffset()), (int) (this.position.y - this.gameEngine.getCamera().getyOffset()));
    }

    public void render(Graphics2D g, int x, int y) {
        g.drawImage(this.texture, x, y, WIDTH, HEIGHT, null);
    }

    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
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

    public AABB getBounds() {
        return bounds;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
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
