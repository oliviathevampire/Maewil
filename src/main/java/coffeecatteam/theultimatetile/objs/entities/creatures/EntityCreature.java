package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AI;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.state.StateOptions;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityCreature extends Entity {

    private long lastLavaTimer, lavaCooldown = 800, lavaTimer = lavaCooldown;
    public static final float DEFAULT_SPEED = 3.0f;
    public static final float DEFAULT_WATER_SPEED = 1f, DEFAULT_IN_WATER_SPEED = 0.6f;
    protected float waterSpeed = DEFAULT_WATER_SPEED;

    protected Item drop = null;

    protected float speed;
    protected float xMove, yMove;

    protected List<AI> ais = new ArrayList<>();

    public EntityCreature(TutEngine tutEngine, String id, int width, int height) {
        super(tutEngine, id, width, height, EntityHitType.CREATURE);
        init();
        setCurrentTexture("idle");

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    protected void init() {
    }

    @Override
    public void updateA(GameContainer container, int delta) {
        super.updateA(container, delta);

        ais.forEach(ai -> ai.update(container, delta));

        // Animation
        updateAnim();

        if (this.inLava()) {
            lavaTimer += System.currentTimeMillis() - lastLavaTimer;
            lastLavaTimer = System.currentTimeMillis();
            if (lavaTimer < lavaCooldown)
                return;
            this.hurt(1);
        }
    }

    private void updateAnim() {
        if (yMove < 0)
            setCurrentTexture("walkUp");
        if (yMove > 0)
            setCurrentTexture("walkDown");

        if (xMove < 0)
            setCurrentTexture("walkLeft");
        if (xMove > 0)
            setCurrentTexture("walkRight");

        if (xMove == 0 && yMove == 0)
            setCurrentTexture("idle");
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        this.renderEffect(g);
        this.renderHealth(g);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        if (drop != null) {
            int amt = NumberUtils.getRandomInt(4);
            for (int i = 0; i < amt; i++)
                tutEngine.getEntityManager().addItem(new ItemStack(drop.newCopy()), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height), false);
        }
    }

    public void move() {
        if (this.inWater() || this.inLava())
            waterSpeed = DEFAULT_IN_WATER_SPEED;
        else
            waterSpeed = DEFAULT_WATER_SPEED;

        if (!checkEntityCollisions(xMove, 0.0f))
            moveX();
        if (!checkEntityCollisions(0.0f, yMove))
            moveY();
    }

    public void moveX() {
        if (xMove != 0) {
            xMove *= waterSpeed;
            int tx = (int) (position.x + xMove + bounds.x + (xMove > 0 ? bounds.width : 0)) / Tile.TILE_WIDTH;

            if (!isCollidingWithTile(tx, (int) (position.y + bounds.y) / Tile.TILE_HEIGHT) && !isCollidingWithTile(tx, (int) (position.y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
                position.add(new Vector2D(xMove, 0));
            else
                position.x = tx * Tile.TILE_WIDTH + (xMove > 0 ? -bounds.width - 1 : Tile.TILE_WIDTH) - bounds.x;
        }
    }

    public void moveY() {
        if (yMove != 0) {
            yMove *= waterSpeed;
            int ty = (int) (position.y + yMove + bounds.y + (yMove > 0 ? bounds.height : 0)) / Tile.TILE_HEIGHT;

            if (!isCollidingWithTile((int) (position.x + bounds.x) / Tile.TILE_WIDTH, ty) && !isCollidingWithTile((int) (position.x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
                position.add(new Vector2D(0, yMove));
            else
                position.y = ty * Tile.TILE_HEIGHT + (yMove > 0 ? -bounds.height - 1 : Tile.TILE_HEIGHT) - bounds.y;
        }
    }

    protected boolean isCollidingWithTile(int x, int y) {
        if (this.getId().equals("player") && StateOptions.OPTIONS.debugMode()) {
            tutEngine.getLogger().warn("X: " + x + " Y: " + y);
            tutEngine.getLogger().warn(tutEngine.getWorld().getFGTile(x, y).getId() + " - " + tutEngine.getWorld().getFGTile(x, y).isSolid());
        }
        return tutEngine.getWorld().getFGTile(x, y).isSolid();
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
