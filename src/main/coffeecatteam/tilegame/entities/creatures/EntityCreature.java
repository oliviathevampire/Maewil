package coffeecatteam.tilegame.entities.creatures;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.tiles.Tile;

public abstract class EntityCreature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f;

    protected float speed;
    protected float xMove, yMove;

    public EntityCreature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    public void move() {
        if (!checkEntityCollisions(xMove, 0.0f))
            moveX();
        if (!checkEntityCollisions(0.0f, yMove))
            moveY();
    }

    public void moveX() {
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

            if (!collisionWidthTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) && !collisionWidthTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                x += xMove;
            else
                x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
        } else if (xMove < 0) {
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

            if (!collisionWidthTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) && !collisionWidthTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                x += xMove;
            else
                x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
        }
    }

    public void moveY() {
        if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            if (!collisionWidthTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWidthTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                y += yMove;
            else
                y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
        } else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            if (!collisionWidthTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWidthTile((int) (x + bounds.x+ bounds.width) / Tile.TILE_WIDTH, ty))
                y += yMove;
            else
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
        }
    }

    protected boolean collisionWidthTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }
}
