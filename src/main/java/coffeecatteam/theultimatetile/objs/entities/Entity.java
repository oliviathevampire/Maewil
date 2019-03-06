package coffeecatteam.theultimatetile.objs.entities;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.image.SpriteSheet;
import coffeecatteam.theultimatetile.objs.IHasData;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TileLava;
import coffeecatteam.theultimatetile.objs.tiles.TileWater;
import coffeecatteam.theultimatetile.tags.TagCompound;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity implements IHasData<Entity> {

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_WIDTH = 48, DEFAULT_HEIGHT = 48;

    private String id;
    protected TutEngine tutEngine;
    protected Vector2D position = new Vector2D();

    protected int width, height;
    protected int currentHealth, maxHealth = DEFAULT_HEALTH;
    protected boolean active = true;
    protected AABB bounds;

    protected boolean showHitbox = false, isCollidable = true, interacted = false;
    private int extraDmg = 0;

    protected TagCompound TAGS = new TagCompound();
    private EntityHitType entityHitType;

    protected int renderX, renderY;
    private Map<String, Animation> textures = new HashMap<>();
    private Animation currentTexture = new Animation(Assets.MISSING_TEXTURE);

    private Animation splashEffect;
    private SpriteSheet HEALTH_BAR = new SpriteSheet("/assets/textures/gui/overlay/health_bar.png");

    public Entity(TutEngine tutEngine, String id, int width, int height, EntityHitType entityHitType) {
        this.tutEngine = tutEngine;
        this.id = id;

        this.width = width;
        this.height = height;
        currentHealth = maxHealth;

        bounds = new AABB(this.position, width, height);

        this.entityHitType = entityHitType;
        splashEffect = new Animation(50, Assets.SPLASH_EFFECT);
    }

    public Entity setTags(TagCompound tags) {
        this.TAGS = tags.copy();
        return this;
    }

    public TagCompound getTags() {
        return TAGS;
    }

    public void update(GameContainer container, int delta) {
    }

    public void updateA(GameContainer container, int delta) {
//        bounds = new AABB(this.position, width, height);
        update(container, delta);

        this.renderX = (int) (this.position.x - this.tutEngine.getCamera().getxOffset());
        this.renderY = (int) (this.position.y - this.tutEngine.getCamera().getyOffset());
        splashEffect.update();

        if (currentTexture != null)
            currentTexture.update();
        textures.values().forEach(Animation::update);

        if (this.interacted)
            this.interact();
    }

    public void preRender(Graphics g) {
    }

    public void render(Graphics g) {
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    public void postRender(Graphics g) {
        if (showHitbox) {
            g.setColor(Color.red);
            g.drawRect((int) (position.x + bounds.x - tutEngine.getCamera().getxOffset()), (int) (position.y + bounds.y - tutEngine.getCamera().getyOffset()), bounds.width, bounds.height);
        }
    }

    public void renderHealth(Graphics g) {

        int barWidth = 16;
        HEALTH_BAR.crop(0, 9, barWidth, 2).draw(this.renderX, this.renderY - 8, width, 4);

        int ht = (int) NumberUtils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        HEALTH_BAR.crop(0, 5, barWidth, 2).draw(this.renderX, this.renderY - 8, ht, 4);

        Font font = Assets.FONTS.get("20");
        String textHealth = "HP: " + currentHealth;
        int xOff = Text.getWidth(textHealth, font) / 2 - width / 2;
        Text.drawString(g, textHealth, this.renderX - xOff, this.renderY - Text.getHeight(textHealth, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    public void renderEffect(Graphics g) {
        if (inWater())
            splashEffect.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    public Tile getTileAtMid() {
        float x = (float) ((position.x + width / 2) / Tile.TILE_WIDTH);
        float y = (float) ((position.y + height / 2f + height / 4f) / Tile.TILE_HEIGHT);
        Tile t = tutEngine.getWorld().getFGTile((int) x, (int) y);
        return t;
    }

    public boolean inWater() {
        return getTileAtMid() instanceof TileWater;
    }

    public boolean inLava() {
        return getTileAtMid() instanceof TileLava;
    }

    public void die(List<Entity> entities, int index) {
        entities.remove(index);
        tutEngine.getPlayer().setGlubel(tutEngine.getPlayer().getGlubel() + NumberUtils.getRandomInt(1, 5));
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

    public void heal(int health) {
        currentHealth += health;
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : tutEngine.getEntityManager().getEntities()) {
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

    public boolean isTouching(Entity entity) {
        return this.bounds.contains(entity.getPosition().x + entity.getWidth() / 2d, entity.getPosition().y + entity.getHeight() / 2d);
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

    public boolean isShowHitbox() {
        return showHitbox;
    }

    public Entity setShowHitbox(boolean showHitbox) {
        this.showHitbox = showHitbox;
        return this;
    }

    public AABB getBounds() {
        return bounds;
    }

    public void setBounds(AABB bounds) {
        this.bounds = bounds;
    }

    public Map<String, Animation> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, Animation> textures) {
        this.textures = textures;
    }

    public Animation getCurrentTexture() {
        return currentTexture;
    }

    public void setCurrentTexture(String id) {
        this.currentTexture = this.textures.get(id);
    }

    public Image getTexture(String id) {
        return this.textures.get(id).getCurrentFrame();
    }

    @Override
    public <T extends Entity> T newCopy(T obj) {
        T entity = obj;
        entity.setActive(active);
        entity.setCurrentHealth(currentHealth);
        entity.setMaxHealth(maxHealth);
        entity.setEntityHitType(entityHitType);
        entity.setPosition(position);
        entity.setShowHitbox(showHitbox);
        entity.setTags(TAGS);
        entity.setTextures(textures);
        entity.setCurrentTexture(new ArrayList<>(textures.keySet()).get(0));
        return entity;
    }
}
