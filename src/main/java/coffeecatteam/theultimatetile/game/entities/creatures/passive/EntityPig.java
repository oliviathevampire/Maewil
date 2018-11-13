package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityPig extends EntityPassive {

    public EntityPig(GameEngine gameEngine, String id) {
        super(gameEngine, id);
        this.drop = ItemManager.RAW_PORK;
    }

    @Override
    protected void init() {
        bounds.x = 0;
        bounds.y = 28;
        bounds.width = 64;
        bounds.height = 35;

        animIdle = new Animation(animSpeed, Assets.PIG_IDLE);
        animUp = new Animation(animUpDownSpeed, Assets.PIG_UP);
        animDown = new Animation(animUpDownSpeed, Assets.PIG_DOWN);
        animLeft = new Animation(animSpeed, Assets.PIG_LEFT);
        animRight = new Animation(animSpeed, Assets.PIG_RIGHT);
    }
}
