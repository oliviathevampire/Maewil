package coffeecatteam.theultimatetile.game.entities;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ImageLoader;
import coffeecatteam.theultimatetile.gfx.SpriteSheet;
import coffeecatteam.theultimatetile.gfx.Text;

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
    protected Engine engine;
    protected Vector2D position = new Vector2D();

    protected int width, height;
    protected int currentHealth, maxHealth = DEFAULT_HEALTH;
    protected boolean active = true;
    protected AABB bounds;

    protected boolean showHitbox = false, isCollidable = true, interacted = false;
    private int extraDmg = 0;

    protected Map<String, String> TAGS = new HashMap<>();
    private EntityHitType entityHitType;

    protected int renderX, renderY;
    protected BufferedImage texture;
    private SpriteSheet HEALTH_BAR = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/overlay/health_bar.png"));

    public Entity(Engine engine, String id, int width, int height, EntityHitType entityHitType) {
        this.id = id;

        this.width = width;
        this.height = height;
        currentHealth = maxHealth;

        bounds = new AABB(this.position, width, height);

        entities.put(id, this);
        entities.get(id).setEngine(engine);
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

        this.renderX = (int) (this.position.x - ((GameEngine) this.engine).getCamera().getxOffset());
        this.renderY = (int) (this.position.y - ((GameEngine) this.engine).getCamera().getyOffset());

        if (this.interacted)
            this.interact();
    }

    public void preRender(Graphics2D g) {
    }

    public void render(Graphics2D g) {
        if (texture != null)
            g.drawImage(texture, this.renderX, this.renderY, width, height, null);
    }

    public void postRender(Graphics2D g) {
        if (showHitbox) {
            g.setColor(Color.red);
            g.drawRect((int) (position.x + bounds.x - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.y + bounds.y - ((GameEngine) engine).getCamera().getyOffset()), bounds.width, bounds.height);
        }
    }

    public void renderHealth(Graphics2D g) {

        int barWidth = 16;
        g.drawImage(HEALTH_BAR.crop(0, 9, barWidth, 2), this.renderX, this.renderY - 8, width, 4, null);

        int ht = (int) NumberUtils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        g.drawImage(HEALTH_BAR.crop(0, 5, barWidth, 2), this.renderX, this.renderY - 8, ht, 4, null);

        Font font = Assets.FONTS.get("20");
        String textHealth = "HP: " + currentHealth;
        int xOff = Text.getWidth(g, textHealth, font) / 2 - width / 2;
        Text.drawString(g, textHealth, this.renderX - xOff, this.renderY - Text.getHeight(g, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    public void die(List<Entity> entities, int index) {
        entities.remove(index);
        ((GameEngine) engine).getEntityManager().getPlayer().setGlubel(((GameEngine) engine).getEntityManager().getPlayer().getGlubel() + NumberUtils.getRandomInt(1, 5));
    }

    public void interact() {
        this.hurt(NumberUtils.getRandomInt(5, 10) + this.extraDmg);
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
        for (Entity e : ((GameEngine) engine).getEntityManager().getEntities()) {
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

    public AABB getCollisionBounds(float xOffset, float yOffset) {
        return new AABB((int) (position.x + bounds.x + xOffset), (int) (position.y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getId() {
        return this.id;
    }

    public float getX() {
        return (float) position.x;
    }

    public void setX(float x) {
        position.x = x;
    }

    public float getY() {
        return (float) position.y;
    }

    public void setY(float y) {
        position.y = y;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
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

    public Entity setShowHitbox(boolean showHitbox) {
        this.showHitbox = showHitbox;
        return this;
    }
}
