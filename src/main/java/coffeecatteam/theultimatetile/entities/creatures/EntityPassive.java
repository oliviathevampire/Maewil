package coffeecatteam.theultimatetile.entities.creatures;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.ai.AIWander;
import coffeecatteam.theultimatetile.inventory.items.Item;

public abstract class EntityPassive extends EntityCreature {

    protected Item drop = null;

    // AI
    private AIWander aiWander;

    public EntityPassive(TheUltimateTile theUltimateTile, String id) {
        super(theUltimateTile, id, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT);
        aiWander = new AIWander(this, 1.5f);
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (theUltimateTile.getEntityManager().getPlayer().isActive()) {
            aiWander.tick();
        }
        move();
    }
}
