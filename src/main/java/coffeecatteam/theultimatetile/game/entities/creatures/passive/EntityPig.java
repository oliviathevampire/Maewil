package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.ai.AIEatCrops;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.GameContainer;

public class EntityPig extends EntityPassive {

    private AIEatCrops aiEatCrops;

    public EntityPig(Engine engine, String id) {
        super(engine, id);
        this.drop = ItemManager.RAW_PORK;
        aiEatCrops = new AIEatCrops(engine, this);
    }

    @Override
    public void update(GameContainer container, int delta) {
        xMove = 0;
        yMove = 0;

        if (TAGS.hasKey("eatCrops")) {
            aiEatCrops.setCropIds(TAGS.getTagList("eatCrops"));
        }

        // Movement
        if (((GameEngine) engine).getPlayer().isActive())
            if (!aiEatCrops.update(container, delta))
                aiWander.update(container, delta);
        move();
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
