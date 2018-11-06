package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity {

    public static Map<String, Entity> entities = new HashMap<>();

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_WIDTH = 64;
    public static final int DEFAULT_HEIGHT = 64;

    private String id;
    protected TheUltimateTile theUltimateTile;
    protected float x, y;

    protected int width, height;
    protected int currentHealth, maxHealth = DEFAULT_HEALTH;
    protected boolean active = true;
    protected Rectangle bounds;

    protected boolean showHitbox = false, isCollidable = true, interacted = false;
    private int extraDmg = 0;

    protected Map<String, String> TAGS = new HashMap<>();
    private EntityHitType entityHitType;

    protected int renderX, renderY;
    protected BufferedImage texture;

    public Entity(TheUltimateTile theUltimateTile, String id, int width, int height, EntityHitType entityHitType) {
        this.id = id;

        this.width = width;
        this.height = height;
        currentHealth = maxHealth;

        bounds = new Rectangle(0, 0, width, height);

        entities.put(id, this);
        entities.get(id).setTheUltimateTile(theUltimateTile);
        this.entityHitType = entityHitType;
    }

    public Entity loadTags(Map<String, String> tags) {
        this.TAGS = tags;
        return this;
    }

    public Map<String, String> saveTags() {
        return TAGS;
    }

    public boolean hasTag(String tag) {
        if (TAGS == null)
            return false;
        for (String key : TAGS.keySet())
            if (key.equals(tag))
                return true;
        return false;
    }

    public void tick() {
    }

    public void tickA() {
        tick();

        this.renderX = (int) (this.x - this.theUltimateTile.getCamera().getxOffset());
        this.renderY = (int) (this.y - this.theUltimateTile.getCamera().getyOffset());

        if (this.interacted)
            this.interact();
    }

    public void preRender(Graphics g) {
        if (showHitbox) {
            g.setColor(Color.red);
            g.fillRect((int) (x + bounds.x - theUltimateTile.getCamera().getxOffset()), (int) (y + bounds.y - theUltimateTile.getCamera().getyOffset()), bounds.width, bounds.height);
        }
    }

    public void render(Graphics g) {
        if (texture != null)
            g.drawImage(texture, this.renderX, this.renderY, width, height, null);
    }

    public void postRender(Graphics g) {
    }

    public void die(List<Entity> entities, int index) {
        entities.remove(index);
        theUltimateTile.getEntityManager().getPlayer().setGlubel(theUltimateTile.getEntityManager().getPlayer().getGlubel() + Utils.getRandomInt(1, 5));
    }

    public void interact() {
        this.hurt(Utils.getRandomInt(5, 10) + this.extraDmg);
        this.interacted = false;
    }

    public void isInteracted(int extraDmg) {
        this.interacted = true;
        this.extraDmg = extraDmg;
    }

    public void hurt(int damage) {
        currentHealth -= damage;
        if (currentHealth <= 0) {
            active = false;
            currentHealth = 0;
        }
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : theUltimateTile.getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e instanceof EntityPlayer && this instanceof EntityPlayer)
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

    public TheUltimateTile getTheUltimateTile() {
        return theUltimateTile;
    }

    public void setTheUltimateTile(TheUltimateTile theUltimateTile) {
        this.theUltimateTile = theUltimateTile;
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public Entity setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
        return this;
    }

    public void heal(int amt) {
        currentHealth += amt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Entity setPos(int x, int y) {
        setX(x);
        setY(y);
        return this;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }

    public EntityHitType getEntityHitType() {
        return entityHitType;
    }

    public void setEntityHitType(EntityHitType entityHitType) {
        this.entityHitType = entityHitType;
    }

    public enum EntityHitType {
        CREATURE, WOOD, STONE, BUSH, NONE
    }
}
