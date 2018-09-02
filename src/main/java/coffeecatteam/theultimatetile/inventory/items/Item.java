package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.TheUltimateTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Item implements Cloneable {

    public static Map<String, Item> items = new HashMap<>();

    public static final int WIDTH = 32, HEIGHT = 32;

    protected TheUltimateTile theUltimateTile;
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
    }

    public void tick(int count) {
        if (this.theUltimateTile.getEntityManager().getPlayer().isActive()) {
            if (this.theUltimateTile.getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.bounds)) {
                if (!this.theUltimateTile.getEntityManager().getPlayer().getInventory().isFull()) {
                    this.pickedUp = true;
                } else {
                    for (ItemStack stack : this.theUltimateTile.getEntityManager().getPlayer().getInventory().getItems()) {
                        if (stack.getId().equals(this.id)) {
                            if (stack.getItem().isStackable()) {
                                this.pickedUp = true;
                            }
                        }
                    }
                }
            }
        }
        if (this.pickedUp)
            this.theUltimateTile.getEntityManager().getPlayer().getInventory().addItem(new ItemStack(this, count));

        this.bounds = new Rectangle(this.x, this.y, WIDTH, HEIGHT);
    }

    public void render(Graphics g) {
        if (this.theUltimateTile == null)
            return;
        render(g, (int) (this.x - this.theUltimateTile.getCamera().getxOffset()), (int) (this.y - this.theUltimateTile.getCamera().getyOffset()));
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

    public TheUltimateTile getTheUltimateTile() {
        return this.theUltimateTile;
    }

    public void setTheUltimateTile(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
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
