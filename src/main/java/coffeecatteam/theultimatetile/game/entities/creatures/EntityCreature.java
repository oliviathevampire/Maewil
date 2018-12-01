package coffeecatteam.theultimatetile.game.entities.creatures;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.ai.AI;
import coffeecatteam.theultimatetile.game.inventory.items.Item;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.game.state.StateOptions;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.game.tiles.TileAnimated;
import coffeecatteam.theultimatetile.gfx.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityCreature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f;
    public static final float DEFAULT_WATER_SPEED = 1f, DEFAULT_IN_WATER_SPEED = 0.6f;
    protected float waterSpeed = DEFAULT_WATER_SPEED;

    /* Animations - 500 = 0.5 second */
    protected Animation animIdle, animUp, animDown, animLeft, animRight;
    protected Animation currentAnim;
    protected int animSpeed = 135;
    protected int animUpDownSpeed = animSpeed + 115;

    private SpriteSheet HEALTH_BAR = new SpriteSheet(ImageLoader.loadImage("/assets/textures/gui/overlay/health_bar.png"));
    private Animation splashEffect;

    protected Item drop = null;

    protected float speed;
    protected float xMove, yMove;

    protected List<AI> ais = new ArrayList<>();

    public EntityCreature(Engine engine, String id, int width, int height) {
        super(engine, id, width, height, EntityHitType.CREATURE);
        init();
        splashEffect = new Animation(50, Assets.SPLASH_EFFECT);
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
        splashEffect.tick();
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
    public void render(Graphics2D g) {
        g.drawImage(currentAnim.getCurrentFrame(), this.renderX, this.renderY, width, height, null);

        this.renderEffect(g);

        int barWidth = 16;
        g.drawImage(HEALTH_BAR.crop(0, 9, barWidth, 2), this.renderX, this.renderY - 8, width, 4, null);

        int ht = (int) NumberUtils.map(currentHealth, 0, maxHealth, 0, width); // (currentHealth * 100.0f) / 15
        g.drawImage(HEALTH_BAR.crop(0, 5, barWidth, 2), this.renderX, this.renderY - 8, ht, 4, null);

        Font font = Assets.FONT_20;
        String textHealth = "HP: " + currentHealth;
        int xOff = Text.getWidth(g, textHealth, font) / 2 - width / 2;
        Text.drawString(g, textHealth, this.renderX - xOff, this.renderY - Text.getHeight(g, font) / 2, false, false, new Color(0, 255, 0), font);
    }

    public void renderEffect(Graphics2D g) {
        if (inWater())
            g.drawImage(splashEffect.getCurrentFrame(), this.renderX, this.renderY, width, height, null);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        if (drop != null) {
            int amt = NumberUtils.getRandomInt(4);
            for (int i = 0; i < amt; i++)
                ((GameEngine) engine).getItemManager().addItem(new ItemStack(drop), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
        }
    }

    public boolean inWater() {
        float x = (float) ((position.x + width / 2) / Tile.TILE_WIDTH);
        float y = (float) ((position.y + height / 2 + height / 4) / Tile.TILE_HEIGHT);
        Tile t = ((GameEngine) engine).getWorld().getFGTile((int) x, (int) y);

        return t instanceof TileAnimated;
    }

    public void move() {
        if (this.inWater())
            waterSpeed = DEFAULT_IN_WATER_SPEED;
        else
            waterSpeed = DEFAULT_WATER_SPEED;

        if (!checkEntityCollisions(xMove, 0.0f))
            moveX();
        if (!checkEntityCollisions(0.0f, yMove))
            moveY();
    }

    public void moveX() {
        if (xMove != 0)
            xMove *= waterSpeed;

        if (xMove > 0) {
            int tx = (int) (position.x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

            if (!collisionWidthTile(tx, (int) (position.y + bounds.y) / Tile.TILE_HEIGHT) && !collisionWidthTile(tx, (int) (position.y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                position.add(new Vector2D(xMove, 0));
            else
                position.x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
        } else if (xMove < 0) {
            int tx = (int) (position.x + xMove + bounds.x) / Tile.TILE_WIDTH;

            if (!collisionWidthTile(tx, (int) (position.y + bounds.y) / Tile.TILE_HEIGHT) && !collisionWidthTile(tx, (int) (position.y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                position.add(new Vector2D(xMove, 0));
            else
                position.x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
        }
    }

    public void moveY() {
        if (yMove != 0)
            yMove *= waterSpeed;

        if (yMove < 0) {
            int ty = (int) (position.y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            if (!collisionWidthTile((int) (position.x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWidthTile((int) (position.x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                position.add(new Vector2D(0, yMove));
            else
                position.y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
        } else if (yMove > 0) {
            int ty = (int) (position.y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            if (!collisionWidthTile((int) (position.x + bounds.x) / Tile.TILE_WIDTH, ty) && !collisionWidthTile((int) (position.x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                position.add(new Vector2D(0, yMove));
            else
                position.y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
        }
    }

    protected boolean collisionWidthTile(int x, int y) {
        if (this.getId().equals("player") && StateOptions.OPTIONS.debugMode()) {
            Logger.print("X: " + x + " Y: " + y);
            Logger.print(((GameEngine) engine).getWorld().getFGTile(x, y).getId() + " - " + ((GameEngine) engine).getWorld().getFGTile(x, y).isSolid());
        }
        return ((GameEngine) engine).getWorld().getFGTile(x, y).isSolid();
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
