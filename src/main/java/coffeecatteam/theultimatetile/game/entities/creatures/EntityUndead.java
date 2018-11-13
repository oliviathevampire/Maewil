package coffeecatteam.theultimatetile.game.entities.creatures;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.game.entities.ai.AIWander;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public abstract class EntityUndead extends EntityCreature {

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
    protected int dmgModifier = 0;

    // AI
    private AIWander aiWander;
    private AIFollowFlee aiFollowFlee;

    public EntityUndead(GameEngine gameEngine, String id) {
        super(gameEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(this, 1.5f);
        aiFollowFlee = new AIFollowFlee(this, gameEngine.getEntityManager().getPlayer());
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (gameEngine.getEntityManager().getPlayer().isActive()) {
            if (!aiFollowFlee.tick()) {
                aiWander.tick();
            }
        }
        move();

        // Attack arSizeRectangle
        checkAttacks();
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        Rectangle cb = getCollisionBounds(0, 0);
        Rectangle ar = new Rectangle();
        int arSize = width * 2;
        ar.x = cb.x - arSize / 2;
        ar.y = cb.y - arSize / 2;
        ar.width = arSize;
        ar.height = arSize;

        attackTimer = 0;

        for (Entity e : gameEngine.getEntityManager().getEntities())
            if (e.equals(gameEngine.getEntityManager().getPlayer()))
                if (e.getCollisionBounds(0, 0).intersects(ar))
                    e.hurt(Utils.getRandomInt(1, 3) + dmgModifier);
    }

    protected void setMaxFollowDistance(float distance) {
        aiFollowFlee.setMaxDistance(distance);
    }
}
