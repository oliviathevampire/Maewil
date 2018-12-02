package coffeecatteam.theultimatetile.game.tiles;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected Engine engine;
    protected BufferedImage texture;
    protected final String id;

    protected AABB bounds;
    protected Vector2D position = new Vector2D();

    private boolean isSolid;
    private TileType tileType;

    public Tile(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        this.engine = engine;
        this.texture = texture;
        this.id = id;

        bounds = new AABB(this.position, TILE_WIDTH, TILE_HEIGHT);
        this.isSolid = isSolid;
        this.tileType = tileType;
    }

    public void updateBounds() {
        bounds = new AABB((int) (position.x * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.y * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public void tick() {
        updateBounds();
    }

    public void forcedTick() {
    }

    public void render(Graphics2D g) {
        g.drawImage(texture, (int) (position.x * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.y * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
    }

    public Tile setPos(Vector2D position) {
        this.position.x = position.x;
        this.position.y = position.y;
        return this;
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

    public Vector2D getPosition() {
        return position;
    }

    public BufferedImage getTexture() {
        return texture;
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
