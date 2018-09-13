package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.ai.AI;
import coffeecatteam.theultimatetile.gfx.*;
import coffeecatteam.theultimatetile.inventory.items.Item;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class EntityCreature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f;

    /* Animations - 500 = 0.5 second */
    protected Animation animIdle, animUp, animDown, animLeft, animRight;
    protected Animation currentAnim;
    protected int animSpeed = 135;
    protected int animUpDownSpeed = animSpeed + 115;

    private SpriteSheet HEALTH_BAR = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/overlay/health_bar.png"));

    protected Item drop = null;

    protected float speed;
    protected float xMove, yMove;

    private List<AI> ais = new ArrayList<>();

    public EntityCreature(TheUltimateTile theUltimateTile, String id, int width, int height) {
        super(theUltimateTile, id, width, height);
        init();
        currentAnim = animIdle;

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    protected abstract void init();

    @Override
    public void tickA() {
        super.tickA();
        ais.forEach(AI::tick);

        // Animation
        currentAnim.tick();
        updateAnim();
    }

    private void updateAnim() {
        if (yMove < 0)
            currentAnim = animUp;
        if (yMove > 0)
            currentAnim = animDown;

        if (xMove < 0)
            currentAnim = animLeft;
        if (xMove > 0)
            currentAnim = animRight;

        if (xMove == 0 && yMove == 0)
            currentAnim = animIdle;
    }

    @Override
    public void render(Graphics g) {
        int x = (int) (this.x - theUltimateTile.getCamera().getxOffset());
        int y = (int) (this.y - theUltimateTile.getCamera().getyOffset());
        g.drawImage(currentAnim.getCurrentFrame(), x, y, width, height, null);

        int barWidth = 16;
        g.drawImage(HEALTH_BAR.crop(0, 9, barWidth, 2), x, y - 8, width, 4, null);

        int ht = (int) Utils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        g.drawImage(HEALTH_BAR.crop(0, 5, barWidth, 2), x, y - 8, ht, 4, null);

        Font font = Assets.FONT_20;
        String textHealth = "HP: " + currentHealth;
        int xOff = Text.getWidth(g, textHealth, font) / 2 - width / 2;
        Text.drawString(g, textHealth, x - xOff, y - Text.getHeight(g, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        if (drop != null) {
            int amt = new Random().nextInt(3) + 1;
            for (int i = 0; i < amt; i++)
                theUltimateTile.getItemManager().addItem(new ItemStack(drop), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
        }
    }

    protected void addAI(AI ai) {
        ais.add(ai);
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

            if (!collisionWidthTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWidthTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                y += yMove;
            else
                y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
        }
    }

    protected boolean collisionWidthTile(int x, int y) {
        return theUltimateTile.getWorld().getTile(x, y).isSolid();
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
