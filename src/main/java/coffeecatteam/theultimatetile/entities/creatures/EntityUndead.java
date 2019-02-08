package coffeecatteam.theultimatetile.game.entities.creatures;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.game.entities.ai.AIWander;
import org.newdawn.slick.GameContainer;

public abstract class EntityUndead extends EntityCreature {

    private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
    protected int dmgModifier = 0;

    // AI
    private AIWander aiWander;
    private AIFollowFlee aiFollowFlee;

    public EntityUndead(TutEngine tutEngine, String id) {
        super(tutEngine, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(tutEngine, this, 1.5f);
        aiFollowFlee = new AIFollowFlee(tutEngine, this, ((TutEngine) tutEngine).getEntityManager().getPlayer());

        bounds.x = width / 4f;
        bounds.y = height - height / 2f;
        bounds.width = width / 2;
        bounds.height = height / 2;
    }

    @Override
    public void update(GameContainer container, int delta) {
        xMove = 0;
        yMove = 0;

        // Movement
        if (((TutEngine) TutEngine).getEntityManager().getPlayer().isActive()) {
            if (!aiFollowFlee.update(container, delta)) {
                aiWander.update(container, delta);
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

        for (Entity e : ((TutEngine) TutEngine).getEntityManager().getEntities())
            if (e.equals(((TutEngine) TutEngine).getEntityManager().getPlayer()))
                if (e.getCollisionBounds(0, 0).intersects(ar))
                    e.hurt(NumberUtils.getRandomInt(1, 3) + dmgModifier);
    }

    protected void setMaxFollowDistance(float distance) {
        aiFollowFlee.setMaxDistance(distance);
    }
}
