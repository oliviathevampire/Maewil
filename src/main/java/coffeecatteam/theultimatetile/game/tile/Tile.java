package coffeecatteam.theultimatetile.game.tile;

import coffeecatteam.coffeecatutils.position.AABB;
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
    protected TilePos position = new TilePos();
    protected Tile[][] worldLayer;

    private boolean isSolid;
    private TileType tileType;

    public Tile(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        this.engine = engine;
        this.texture = texture;
        this.id = id;

        bounds = new AABB(this.position.toVector2D(), TILE_WIDTH, TILE_HEIGHT);
        this.isSolid = isSolid;
        this.tileType = tileType;
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
        g.drawImage(texture, (int) (position.getX() * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
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
