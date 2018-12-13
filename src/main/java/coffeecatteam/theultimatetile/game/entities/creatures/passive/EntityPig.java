package coffeecatteam.theultimatetile.game.entities.creatures.passive;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.ai.AIEatCrops;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.gfx.Animation;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

public class EntityPig extends EntityPassive {

    private AIEatCrops aiEatCrops;

    public EntityPig(Engine engine, String id) {
        super(engine, id);
        this.drop = ItemManager.RAW_PORK;
        aiEatCrops = new AIEatCrops(this);
    }

    @Override
    public void tick() {
        xMove = 0;
        yMove = 0;

        // Movement
        if (((GameEngine) engine).getPlayer().isActive())
            if (!aiEatCrops.tick())
                aiWander.tick();
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

        if (TAGS.containsKey("eatCrops"))
            aiEatCrops.setCropIds(TAGS.get("eatCrops").split(","));
        Logger.print(TAGS.get("eatCrops"));
    }
}
