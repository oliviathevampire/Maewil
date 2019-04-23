package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.IHasData;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.TileList;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Tile implements IHasData<Tile> {

    public static final int TILE_SIZE = 48;
    public static final int DEFAULT_ALT_CHANCE = 850;

    protected TutEngine tutEngine;

    protected Animation texture;
    protected ArrayList<Image> textureAlts = new ArrayList<>();
    private int altAmt;
    private boolean hasAlts = false;
    private int altChance = DEFAULT_ALT_CHANCE;

    protected final String id;
    protected Color mapColor = WorldColors.STONE;

    protected AABB bounds;
    protected TilePos position = new TilePos();
    protected TileList worldLayer;
    protected int worldWidth;

    protected boolean isSolid, unbreakable = false;
    protected TileType tileType;

    protected Item drop;
    private int health, maxHealth = 300;
    private boolean beingDamaged = false;

    public Tile(TutEngine tutEngine, String id, boolean isSolid, TileType tileType) {
        this.tutEngine = tutEngine;
        this.id = id;

        bounds = new AABB(this.position.toVector2D(), TILE_SIZE, TILE_SIZE);
        this.isSolid = isSolid;
        this.tileType = tileType;

        this.health = this.maxHealth;
    }

    protected void chooseAltTexture() {
        if (hasAlts) {
            int max = 1000;
            int i = NumberUtils.getRandomInt(max);
            if (i < altChance)
                this.texture = new Animation(textureAlts.get(0));
            else
                this.texture = new Animation(textureAlts.get((int) NumberUtils.map(i, altChance, max, 1, altAmt - 1)));
        }
    }

    public void updateBounds() {
        bounds = new AABB((int) (position.getX() * Tile.TILE_SIZE - tutEngine.getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_SIZE - tutEngine.getCamera().getyOffset()), TILE_SIZE, TILE_SIZE);
    }

    protected Tile getTileAt(TilePos pos) {
        if (pos.getX() < 0) pos.setX(0);
        if (pos.getY() < 0) pos.setY(0);
        if (pos.getX() > worldLayer.getWidth() - 1) pos.setX(worldLayer.getWidth() - 1);
        if (pos.getY() > worldLayer.getHeight() - 1) pos.setY(worldLayer.getHeight() - 1);
//        return worldLayer[pos.getX()][pos.getY()];
        return worldLayer.getTile(pos.getX(), pos.getY());
    }

    public void update(GameContainer container, StateBasedGame game, int delta) {
        updateBounds();
        if (!beingDamaged) {
            this.health += NumberUtils.getRandomInt(1, 3);
            if (this.health > this.maxHealth)
                this.health = this.maxHealth;
        }
        beingDamaged = false;
    }

    public void forcedUpdate(GameContainer container, int delta) {
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        render(g, (int) (position.getX() * Tile.TILE_SIZE - tutEngine.getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_SIZE - tutEngine.getCamera().getyOffset()), TILE_SIZE, TILE_SIZE);
    }

    public void render(Graphics g, int x, int y, int width, int height) {
        getTexture().draw(x, y, width, height);

        if (drop != null) {
            if (isSolid && !unbreakable) {
                int index = (int) NumberUtils.map(this.health, 0, this.maxHealth, 0, Assets.TILE_CRACKING.length - 1);
                if (index < 0)
                    index = 0;
                Image currentFrame = Assets.TILE_CRACKING[index];
                currentFrame.draw(x, y, width, height);
            }
        }
    }

    public void damage(int damage) {
        if (drop != null) {
            if (isSolid && !unbreakable) {
                this.health -= damage;
                if (this.health <= 0) {
                    if (position.getX() == 0 || position.getX() == tutEngine.getWorld().getWidth() || position.getY() == 0 || position.getY() == tutEngine.getWorld().getHeight())
                        return;
                    tutEngine.getWorld().setFGTile(position.getX(), position.getY(), Tiles.AIR);
                    tutEngine.getEntityManager().addItem(new ItemStack(drop.newCopy()), position.getX(), position.getY());
                    this.health = this.maxHealth;
                }
                beingDamaged = true;
            }
        }
    }

    public Item getDrop() {
        return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public Tile setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public Color getMapColor() {
        return mapColor;
    }

    public Tile setMapColor(Color mapColor) {
        this.mapColor = mapColor;
        return this;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public Tile setPos(TilePos position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        return this;
    }

    public TileList getWorldLayer() {
        return worldLayer;
    }

    public void setWorldLayer(TileList worldLayer) {
        this.worldLayer = worldLayer;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Tile setSolid(boolean solid) {
        isSolid = solid;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LangType getType() {
        return LangType.TILE;
    }

    public AABB getBounds() {
        return bounds;
    }

    public TilePos getPosition() {
        return position;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public enum TileType {
        GROUND, STONE, WOOD, FLUID, AIR
    }

    /*
     * TEXTURE/S
     */
    // Get/set texture
    public Image getTexture() {
        if (texture != null)
            return texture.getCurrentFrame();
        else
            return Assets.MISSING_TEXTURE;
    }

    public void setTexture(Image texture) {
        this.texture = new Animation(texture);
    }

    public void setTexture(Image[] textures) {
        this.texture = new Animation(textures);
    }


    // Get all textures
    public Image[] getAllTextures() {
        return texture.getFrames();
    }


    // Get/set animation
    public Animation getAnimation() {
        return texture;
    }

    public Tile setAnimation(Animation animation) {
        this.texture = animation;
        return this;
    }


    // Get/set has alts
    public boolean hasAlts() {
        return hasAlts;
    }

    public void setHasAlts(boolean hasAlts) {
        this.hasAlts = hasAlts;
    }


    // Get/set texture alts
    public ArrayList<Image> getTextureAlts() {
        return textureAlts;
    }

    public void setTextureAlts(Image[] textureAlts) {
        setTextureAlts(Arrays.asList(textureAlts), textureAlts.length);
    }

    public void setTextureAlts(Image[] textureAlts, int altAmt) {
        setTextureAlts(Arrays.asList(textureAlts), altAmt);
    }

    public void setTextureAlts(List<Image> textureAlts) {
        setTextureAlts(textureAlts, textureAlts.size());
    }

    public void setTextureAlts(List<Image> textureAlts, int altAmt) {
        this.textureAlts = new ArrayList<>();
        for (int i = 0; i < altAmt; i++)
            this.textureAlts.add(textureAlts.get(i));
        this.altAmt = altAmt;
        chooseAltTexture();
    }


    // Get/set alt chance
    public int getAltChance() {
        return altChance;
    }

    public void setAltChance(int altChance) {
        this.altChance = altChance;
        chooseAltTexture();
    }

    @Override
    public <T extends Tile> T newCopy(T tile) {
        T t = tile;
        t.setMapColor(mapColor);
        t.setPos(position);
        t.setSolid(isSolid);
        t.setUnbreakable(unbreakable);
        t.setAnimation(texture);
        t.setHasAlts(hasAlts);
        t.setTextureAlts(textureAlts);
        t.setAltChance(altChance);
        t.setDrop(drop);
        t.setTileType(tileType);
        return t;
    }
}
