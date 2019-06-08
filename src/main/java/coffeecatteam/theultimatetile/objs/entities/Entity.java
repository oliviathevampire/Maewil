package coffeecatteam.theultimatetile.objs.entities;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.IHasData;
import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.LavaTile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import coffeecatteam.theultimatetile.objs.tiles.WaterTile;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.screen.options.OptionsScreen;
import coffeecatteam.theultimatetile.tags.CompoundTag;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity implements IHasData<Entity> {

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_WIDTH = 48, DEFAULT_HEIGHT = 48;

    private String id;
    protected TutEngine tutEngine;
    protected Vector2D position = new Vector2D();

    protected int width, height;
    protected int currentHealth, maxHealth = DEFAULT_HEALTH;
    private boolean active = true;

    protected boolean isCollidable = true, interacted = false;
    private int extraDmg = 0;

    protected CompoundTag TAGS = new CompoundTag();
    private HitType hitType;

    protected float renderX, renderY;
    private Map<String, Animation> textures = new HashMap<>();
    private Animation currentTexture = new Animation(Assets.MISSING_TEXTURE);
    private String currentTextureId = "";

    private Animation splashEffect;

    public Entity(TutEngine tutEngine, String id, int width, int height, HitType hitType) {
        this.tutEngine = tutEngine;
        this.id = id;

        this.width = width;
        this.height = height;
        currentHealth = maxHealth;

        this.hitType = hitType;
        splashEffect = new Animation(50, Assets.SPLASH_EFFECT);
    }

    public Entity setTags(CompoundTag tags) {
        this.TAGS = tags.copy();
        return this;
    }

    public CompoundTag getTags() {
        return TAGS;
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
    }

    public void updateA(GameContainer container, StateBasedGame game, int delta) {
        update(container, game, delta);

        this.renderX = (int) (this.position.x - this.tutEngine.getCamera().getxOffset());
        this.renderY = (int) (this.position.y - this.tutEngine.getCamera().getyOffset());

        if (this.interacted)
            this.interact();
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        getCurrentTexture().getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        if (OptionsScreen.OPTIONS.debugMode()) {
            g.setColor(Color.red);
            g.drawRect(getEntityBounds().x - tutEngine.getCamera().getxOffset(), getEntityBounds().y - tutEngine.getCamera().getyOffset(), getEntityBounds().width, getEntityBounds().height);

            g.setColor(Color.blue);
            g.drawRect((int) (position.x + getTileBounds().x - tutEngine.getCamera().getxOffset()), (int) (position.y + getTileBounds().y - tutEngine.getCamera().getyOffset()), getTileBounds().width, getTileBounds().height);
        }
    }

    protected void renderHealth(Graphics g) {
        Assets.HEALTH_BAR[1].draw(this.renderX, this.renderY - 8, width, 4);
        int ht = (int) NumberUtils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        Assets.HEALTH_BAR[0].draw(this.renderX, this.renderY - 8, ht, 4);

        Font font = Assets.FONTS.get("20");
        String textHealth = "HP: " + currentHealth;
        float xOff = Text.getWidth(textHealth, font) / 2 - width / 2f;
        Text.drawString(g, textHealth, this.renderX - xOff, this.renderY - Text.getHeight(textHealth, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    public void renderEffect(GameContainer container, StateBasedGame game, Graphics g) {
        if (inWater())
            splashEffect.getCurrentFrame().draw(this.renderX, this.renderY, width, height);
    }

    public TilePos getTilePosAtMid() {
        float x = (float) ((position.x + width / 2) / Tile.TILE_SIZE);
        float y = (float) ((position.y + height / 2f + height / 4f) / Tile.TILE_SIZE);
        return new TilePos((int) x, (int) y);
    }

    private Tile getTileAtMid() {
        return tutEngine.getWorld().getForegroundTile(getTilePosAtMid().getX(), getTilePosAtMid().getY());
    }

    protected boolean inWater() {
        return getTileAtMid() instanceof WaterTile;
    }

    protected boolean inLava() {
        return getTileAtMid() instanceof LavaTile;
    }

    public void die() {
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

    protected boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : tutEngine.getEntityManager().getEntities()) {
            if (e.equals(this))
                continue;
            if (e instanceof PlayerEntity && this instanceof PlayerEntity)
                continue;
            if (e.isCollidable)
                if (e.getCollisionBounds(0.0f, 0.0f).intersects(getCollisionBounds(xOffset, yOffset)))
                    return false;
        }
        return true;
    }

    public AABB getTileBounds() {
        return new AABB(new Vector2D(), width, height);
    }

    private AABB getEntityBounds() {
        float x = (float) (position.x + getTileBounds().x);
        float y = (float) (position.y + getTileBounds().y);
        return new AABB(x, y, getTileBounds().width, getTileBounds().height);
    }

    public AABB getCollisionBounds(float xOffset, float yOffset) {
        return new AABB((int) (getEntityBounds().x + xOffset), (int) (getEntityBounds().y + yOffset), getEntityBounds().width, getEntityBounds().height);
    }

    public boolean isTouching(Entity entity) {
        return this.getEntityBounds().contains(entity.getPosition().x + entity.getWidth() / 2d, entity.getPosition().y + entity.getHeight() / 2d);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public LangType getType() {
        return LangType.ENTITY;
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

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }

    public HitType getHitType() {
        return hitType;
    }

    void setHitType(HitType hitType) {
        this.hitType = hitType;
    }

    public enum HitType {
        CREATURE(0),
        WOOD(1),
        STONE(2),
        BUSH(3),
        NONE(4);

        private int index;

        HitType(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public static HitType getHitType(int index) {
            for (HitType type : values())
                if (type.getIndex() == index)
                    return type;
            return NONE;
        }
    }

    public Map<String, Animation> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, Animation> textures) {
        this.textures = textures;
    }

    protected Animation getCurrentTexture() {
        if (currentTexture == null || currentTexture.getCurrentFrame() == null)
            return new Animation(Assets.MISSING_TEXTURE);
        return currentTexture;
    }

    public void setCurrentTexture(String id) {
        this.currentTexture = this.textures.get(id);
        currentTextureId = id;
    }

    String getCurrentTextureId() {
        return currentTextureId;
    }

    void setCurrentTextureId(String currentTextureId) {
        this.currentTextureId = currentTextureId;
    }

    public Image getTexture(String id) {
        return this.textures.get(id).getCurrentFrame();
    }

    @Override
    public <T extends Entity> T newCopy(T obj) {
        obj.setActive(active);
        obj.setCurrentHealth(currentHealth);
        obj.setMaxHealth(maxHealth);
        obj.setHitType(hitType);
        obj.setPosition(position);
        obj.setTags(TAGS);
        obj.setTextures(textures);
        obj.setCurrentTexture(currentTextureId);
        obj.setCurrentTextureId(currentTextureId);
        return obj;
    }
}
