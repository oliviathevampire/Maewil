package coffeecatteam.theultimatetile.tiles;

import coffeecatteam.theultimatetile.GameEngine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected GameEngine gameEngine;
    protected BufferedImage texture;
    protected final String id;

    protected Rectangle bounds;
    protected int x, y;

    private boolean isSolid;
    private TileType tileType;

    public Tile(GameEngine gameEngine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        this.gameEngine = gameEngine;
        this.texture = texture;
        this.id = id;

        bounds = new Rectangle(0, 0, TILE_WIDTH, TILE_HEIGHT);
        this.isSolid = isSolid;
        this.tileType = tileType;
    }

    public void updateBounds() {
        bounds = new Rectangle((int) (x * Tile.TILE_WIDTH - gameEngine.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - gameEngine.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public void tick() {
        updateBounds();
    }

    public void render(Graphics g) {
        render(g, new Color(255, 255, 255, 0));
    }

    public void render(Graphics g, Color tint) {
        g.drawImage(texture, (int) (x * Tile.TILE_WIDTH - gameEngine.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - gameEngine.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
        g.setColor(tint);
        g.fillRect((int) (x * Tile.TILE_WIDTH - gameEngine.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - gameEngine.getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT);
    }

    public Tile setPos(int x, int y) {
        this.x = x;
        this.y = y;
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

    public Rectangle getBounds() {
        return bounds;
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
