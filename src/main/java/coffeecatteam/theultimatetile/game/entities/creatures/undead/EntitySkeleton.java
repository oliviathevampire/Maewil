package coffeecatteam.theultimatetile.game.entities.creatures.undead;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntitySkeleton extends EntityUndead {

    public EntitySkeleton(Engine engine, String id) {
        super(engine, id);
        this.drop = ItemManager.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
        this.dmgModifier = 2;
    }

    @Override
    protected void init() {
        bounds.x = 13;
        bounds.y = 28;
        bounds.width = 34;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.SKELETON_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.SKELETON_UP);
        animDown = new Animation(animUpDownSpeed, Assets.SKELETON_DOWN);
        animLeft = new Animation(animSpeed, Assets.SKELETON_LEFT);
        animRight = new Animation(animSpeed, Assets.SKELETON_RIGHT);
    }
}
