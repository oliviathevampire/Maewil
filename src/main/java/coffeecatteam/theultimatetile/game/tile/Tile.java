package coffeecatteam.theultimatetile.game.tile;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected Engine engine;
    protected BufferedImage texture;
    protected final String id;

    protected AABB bounds;
    protected TilePos position = new TilePos();
    protected Tile[][] worldLayer;

    protected boolean isSolid, unbreakable = false;
    protected TileType tileType;

    protected Item drop;
    protected int health, maxHealth = 300;

    public Tile(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        this.engine = engine;
        this.texture = texture;
        this.id = id;

        bounds = new AABB(this.position.toVector2D(), TILE_WIDTH, TILE_HEIGHT);
        this.isSolid = isSolid;
        this.tileType = tileType;

        this.health = this.maxHealth;
    }

    public void updateBounds() {
        bounds = new AABB((int) (position.getX() * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    protected Tile getTileAt(TilePos pos) {
        if (pos.getX() < 0) pos.setX(0);
        if (pos.getY() < 0) pos.setY(0);
        if (pos.getX() > worldLayer.length - 1) pos.setX(worldLayer.length - 1);
        if (pos.getY() > worldLayer[0].length - 1) pos.setY(worldLayer[0].length - 1);
        return worldLayer[pos.getX()][pos.getY()];
    }

    public void tick() {
        updateBounds();
    }

    public void forcedTick() {
    }

    public void render(Graphics2D g) {
        render(g, (int) (position.getX() * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public void render(Graphics2D g, int x, int y, int width, int height) {
        g.drawImage(texture, x, y, width, height, null);

        if (drop != null) {
            if (isSolid && !unbreakable) {
                int index = (int) NumberUtils.map(this.health, 0, this.maxHealth, 0, Assets.TILE_CRACKING.length - 1);
                if (index < 0)
                    index = 0;
                BufferedImage currentFrame = Assets.TILE_CRACKING[index];
                g.drawImage(currentFrame, x, y, width, height, null);
            }
        }
    }

    public void damage(int damage) {
        if (drop != null) {
            if (isSolid && !unbreakable) {
                this.health -= damage;
                if (this.health <= 0) {
                    if (position.getX() == 0 || position.getX() == ((GameEngine) engine).getWorld().getWidth() || position.getY() == 0 || position.getY() == ((GameEngine) engine).getWorld().getHeight())
                        return;
                    ((GameEngine) engine).getWorld().setFGTile(position.getX(), position.getY(), Tiles.AIR);
                    ((GameEngine) engine).getItemManager().addItem(new ItemStack(drop), position.getX() * Tile.TILE_WIDTH, (position.getY() * Tile.TILE_HEIGHT));
                    this.health = this.maxHealth;
                }
            }
        }
    }

    public abstract <T extends Tile> T copy();

    public Item getDrop() {
        return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
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

    public Tile[][] getWorldLayer() {
        return worldLayer;
    }

    public void setWorldLayer(Tile[][] worldLayer) {
        this.worldLayer = worldLayer;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Tile setSolid(boolean solid) {
        isSolid = solid;
        return this;
    }

    public String getId() {
        return id;
    }

    public AABB getBounds() {
        return bounds;
    }

    public TilePos getPosition() {
        return position;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
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
}
