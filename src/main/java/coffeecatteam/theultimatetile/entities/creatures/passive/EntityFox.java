package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

public class EntityFox extends EntityPassive {

    public EntityFox(TutEngine tutEngine, String id) {
        super(tutEngine, id);
    }

    @Override
    protected void init() {
        animIdle = new Animation(animSpeed, Assets.FOX_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.FOX_UP);
        animDown = new Animation(animUpDownSpeed, Assets.FOX_DOWN);
        animLeft = new Animation(animSpeed, Assets.FOX_LEFT);
        animRight = new Animation(animSpeed, Assets.FOX_RIGHT);
    }
}
