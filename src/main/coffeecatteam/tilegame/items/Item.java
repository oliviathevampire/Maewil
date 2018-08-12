package coffeecatteam.tilegame.items;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item implements Cloneable {

    public static Item[] items = new Item[256];

    public static final int WIDTH = 32, HEIGHT = 32;

    protected Handler handler;
    protected BufferedImage texture;
    protected String name;
    protected final int id;

    protected Rectangle bounds;

    protected int x, y;
    protected boolean pickedUp = false;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;

        this.bounds = new Rectangle(this.x, this.y, WIDTH, HEIGHT);

        this.items[id] = this;
    }

    public boolean onInteracted(EntityPlayer player) {
        return false;
    }

    public void tick() {
        if (this.handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(this.bounds)) {
            if (!this.handler.getWorld().getEntityManager().getPlayer().getInventory().isFull()) {
                this.pickedUp = true;
                this.handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(new ItemStack(this));
            } else {
                for (ItemStack stack : this.handler.getWorld().getEntityManager().getPlayer().getInventory().getItems()) {
                    if (stack.getId() == this.getId()) {
                        this.pickedUp = true;
                        this.handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(new ItemStack(this));
                    }
                }
            }
        }

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
