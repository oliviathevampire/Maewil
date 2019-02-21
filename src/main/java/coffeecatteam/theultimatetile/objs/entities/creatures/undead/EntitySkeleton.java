package coffeecatteam.theultimatetile.objs.entities.creatures.undead;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityUndead;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.items.Items;

public class EntitySkeleton extends EntityUndead {

    public EntitySkeleton(TutEngine tutEngine, String id) {
        super(tutEngine, id);
        this.drop = Items.BONE;
        this.setMaxHealth(Entity.DEFAULT_HEALTH / 2);
        setMaxFollowDistance(250f);
        this.dmgModifier = 2;
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.SKELETON_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.SKELETON_UP);
        animDown = new Animation(animUpDownSpeed, Assets.SKELETON_DOWN);
        animLeft = new Animation(animSpeed, Assets.SKELETON_LEFT);
        animRight = new Animation(animSpeed, Assets.SKELETON_RIGHT);
    }
}
