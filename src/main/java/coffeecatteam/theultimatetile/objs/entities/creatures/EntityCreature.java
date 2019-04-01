package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AI;
import coffeecatteam.theultimatetile.objs.items.Item;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.state.StateOptions;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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
        super(tutEngine, id, width, height, HitType.CREATURE);
        init();
        setCurrentTexture("idle");

        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    protected void init() {
    }

    @Override
    public void updateA(GameContainer container, StateBasedGame game, int delta) {
        super.updateA(container, game, delta);

        ais.forEach(ai -> ai.update(container, game, delta));

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
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);
        this.renderEffect(container, game, g);
        this.renderHealth(container, game, g);
    }

    @Override
    public void die() {
        if (drop != null) {
            int amt = NumberUtils.getRandomInt(4);
            for (int i = 0; i < amt; i++)
                tutEngine.getEntityManager().addItem(new ItemStack(drop.newCopy()), (float) (position.x + NumberUtils.getRandomInt(width)) / Tile.TILE_SIZE, (float) (position.y + NumberUtils.getRandomInt(height)) / Tile.TILE_SIZE);
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
            int tx = (int) (position.x + xMove + getTileBounds().x + (xMove > 0 ? getTileBounds().width : 0)) / Tile.TILE_SIZE;

            if (!isCollidingWithTile(tx, (int) (position.y + getTileBounds().y) / Tile.TILE_SIZE) && !isCollidingWithTile(tx, (int) (position.y + getTileBounds().y + getTileBounds().height) / Tile.TILE_SIZE))
                position.add(new Vector2D(xMove, 0));
            else
                position.x = tx * Tile.TILE_SIZE + (xMove > 0 ? -getTileBounds().width - 1 : Tile.TILE_SIZE) - getTileBounds().x;
        }
    }

    public void moveY() {
        if (yMove != 0) {
            yMove *= waterSpeed;
            int ty = (int) (position.y + yMove + getTileBounds().y + (yMove > 0 ? getTileBounds().height : 0)) / Tile.TILE_SIZE;

            if (!isCollidingWithTile((int) (position.x + getTileBounds().x) / Tile.TILE_SIZE, ty) && !isCollidingWithTile((int) (position.x + getTileBounds().x + getTileBounds().width) / Tile.TILE_SIZE, ty))
                position.add(new Vector2D(0, yMove));
            else
                position.y = ty * Tile.TILE_SIZE + (yMove > 0 ? -getTileBounds().height - 1 : Tile.TILE_SIZE) - getTileBounds().y;
        }
    }

    protected boolean isCollidingWithTile(int x, int y) {
        if (this.getId().equals("player") && StateOptions.OPTIONS.debugMode()) {
            TutLauncher.LOGGER.warn("X: " + x + " Y: " + y);
            TutLauncher.LOGGER.warn(tutEngine.getWorld().getFGTile(x, y).getId() + " - " + tutEngine.getWorld().getFGTile(x, y).isSolid());
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
