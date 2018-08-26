package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Item implements Cloneable {

    public static Map<String, Item> items = new HashMap<>();

    public static final int WIDTH = 32, HEIGHT = 32;

    protected Handler handler;
    protected BufferedImage texture;
    protected final String id;

    protected Rectangle bounds;

    protected int x, y;
    protected boolean pickedUp = false;
    protected boolean isStackable = true;

    public Item(BufferedImage texture, String id) {
        this.texture = texture;
        this.id = id;

        this.bounds = new Rectangle(this.x, this.y, WIDTH, HEIGHT);

        items.put(id, this);
    }

    public void tick(int count) {
        if (this.handler.getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.bounds)) {
            if (!this.handler.getEntityManager().getPlayer().getInventory().isFull()) {
                this.pickedUp = true;
            } else {
                for (ItemStack stack : this.handler.getEntityManager().getPlayer().getInventory().getItems()) {
                    if (stack.getId().equals(this.id)) {
                        if (stack.getItem().isStackable()) {
                            this.pickedUp = true;
                        }
                    }
                }
            }
        }
        if (this.pickedUp)
            this.handler.getEntityManager().getPlayer().getInventory().addItem(new ItemStack(this, count));

        this.bounds = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    public void render(Graphics g) {
        if (this.handler == null)
            return;
        render(g, (int) (this.x - this.handler.getCamera().getxOffset()), (int) (this.y - this.handler.getCamera().getyOffset()));
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

    public Handler getHandler() {
        return this.handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
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

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
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