package coffeecatteam.theultimatetile.objs.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.objs.entities.ai.AIWander;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public abstract class EntityUndead extends EntityCreature {

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
    protected int dmgModifier = 0;

    // AI
    private AIWander aiWander;
    private AIFollowFlee aiFollowFlee;

    public EntityUndead(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(tutEngine, this, 1.5f);
        aiFollowFlee = new AIFollowFlee(tutEngine, this, tutEngine.getPlayer());
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(width / 4f, height - height / 2f, width / 2, height / 2);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (tutEngine.getPlayer().isActive()) {
            if (!aiFollowFlee.update(container, game, delta)) {
                aiWander.update(container, game, delta);
            }
        }
        move();

        // Attack arSizeAABB
        checkAttacks();
    }

    private void checkAttacks() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown)
            return;

        AABB cb = getCollisionBounds(0, 0);
        AABB ar = new AABB();
        int arSize = width * 2;
        ar.x = cb.x - arSize / 2;
        ar.y = cb.y - arSize / 2;
        ar.width = arSize;
        ar.height = arSize;

        attackTimer = 0;

        for (Entity e : tutEngine.getEntityManager().getEntities())
            if (e.equals(tutEngine.getPlayer()))
                if (e.getCollisionBounds(0, 0).intersects(ar))
                    e.hurt(NumberUtils.getRandomInt(1, 3) + dmgModifier);
    }

    protected void setMaxFollowDistance(float distance) {
        aiFollowFlee.setMaxDistance(distance);
    }
}
