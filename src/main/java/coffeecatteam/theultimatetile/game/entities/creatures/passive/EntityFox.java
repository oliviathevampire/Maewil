package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.ai.AIFollowFlee;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityFox extends EntityPassive {

    public EntityFox(Engine engine, String id) {
        super(engine, id);
    }

    @Override
    protected void init() {
        bounds.x = 0;
        bounds.y = 28;
        bounds.width = 64;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.FOX_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.FOX_UP);
        animDown = new Animation(animUpDownSpeed, Assets.FOX_DOWN);
        animLeft = new Animation(animSpeed, Assets.FOX_LEFT);
        animRight = new Animation(animSpeed, Assets.FOX_RIGHT);
    }
}
