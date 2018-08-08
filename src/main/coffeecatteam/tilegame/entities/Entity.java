package coffeecatteam.tilegame.entities;

import coffeecatteam.tilegame.Handler;

import java.awt.*;
import java.util.Iterator;

public abstract class Entity {

    public static final int DEFAULT_HEALTH = 100;

    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected int health;
    protected boolean active = true;
    protected Rectangle bounds;

    protected boolean showHitbox = false;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;

        bounds = new Rectangle(0, 0, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public void renderA(Graphics g) {
        render(g);

        if (showHitbox) {
            g.setColor(Color.red);
            g.fillRect((int) (x + bounds.x - handler.getCamera().getxOffset()), (int) (y + bounds.y - handler.getCamera().getyOffset()), bounds.width, bounds.height);
        }
    }

    public void die(Iterator<Entity> it) {
        it.remove();
    }

    public void hurt(int damage) {
        health -= damage;
        if (health <= 0) {
            active = false;
            health = 0;
        }
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e.getCollisionBounds(0.0f, 0.0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
