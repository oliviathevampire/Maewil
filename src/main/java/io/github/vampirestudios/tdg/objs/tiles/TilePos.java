package io.github.vampirestudios.tdg.objs.tiles;

import coffeecatteam.coffeecatutils.position.Vector2D;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class TilePos {

    private Vector2D pos;

    public TilePos() {
        this(new Vector2D());
    }

    public TilePos(int x, int y) {
        this(new Vector2D(x, y));
    }

    public TilePos(Vector2D pos) {
        this.pos = pos;
    }

    public static TilePos convertEntityPosition(Vector2D pos) {
        return new TilePos((int) (pos.x / Tile.TILE_SIZE), (int) (pos.y / Tile.TILE_SIZE));
    }

    public int getX() {
        return (int) pos.x;
    }

    public void setX(int x) {
        pos.x = x;
    }

    public int getY() {
        return (int) pos.y;
    }

    public void setY(int y) {
        pos.y = y;
    }

    public TilePos up() {
        return new TilePos((int) pos.x, (int) pos.y - 1);
    }

    public TilePos upLeft() {
        return new TilePos((int) pos.x - 1, (int) pos.y - 1);
    }

    public TilePos upRight() {
        return new TilePos((int) pos.x + 1, (int) pos.y - 1);
    }

    public TilePos down() {
        return new TilePos((int) pos.x, (int) pos.y + 1);
    }

    public TilePos downLeft() {
        return new TilePos((int) pos.x - 1, (int) pos.y + 1);
    }

    public TilePos downRight() {
        return new TilePos((int) pos.x + 1, (int) pos.y + 1);
    }

    public TilePos left() {
        return new TilePos((int) pos.x - 1, (int) pos.y);
    }

    public TilePos right() {
        return new TilePos((int) pos.x + 1, (int) pos.y);
    }

    public Vector2D toVector2D() {
        return pos;
    }

    public String toString() {
        return "TilePos(" + pos.x + ", " + pos.y + ")";
    }
}
