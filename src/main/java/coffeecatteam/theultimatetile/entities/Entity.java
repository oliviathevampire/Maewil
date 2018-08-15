package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.Handler;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class Entity {

    public static Map<String, Entity> entities = new HashMap<>();

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_WIDTH = 64;
    public static final int DEFAULT_HEIGHT = 64;

    private String id;
    protected Handler handler;
    protected float x, y;

    protected int width, height;
    protected int health;
    protected boolean active = true;
    protected Rectangle bounds;

    protected boolean showHitbox = false, isCollidable = true;

    public Entity(Handler handler, String id, int width, int height) {
        this.handler = handler;
        this.id = id;

        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;

        bounds = new Rectangle(0, 0, width, height);

        entities.put(id, this);
        entities.get(id).setHandler(handler);
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
            if (e.isCollidable)
                if (e.getCollisionBounds(0.0f, 0.0f).intersects(getCollisionBounds(xOffset, yOffset)))
                    return true;
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getId() {
        return this.id;
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

    public void heal(int amt) {
        health += amt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
