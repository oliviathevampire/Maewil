package coffeecatteam.theultimatetile.objs.entities.creatures.passive;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.ai.AIEatCrops;
import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPassive;
import coffeecatteam.theultimatetile.objs.items.Items;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class EntityPig extends EntityPassive {

    private AIEatCrops aiEatCrops;

    public EntityPig(TutEngine tutEngine) {
        super(tutEngine, "pig");
        this.drop = Items.RAW_PORK;
        aiEatCrops = new AIEatCrops(tutEngine, this);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        xMove = 0;
        yMove = 0;

        if (TAGS.hasKey("eatCrops"))
            aiEatCrops.setCropIds(TAGS.getTagList("eatCrops"));

        // Movement
        if (tutEngine.getPlayer().isActive())
            if (!aiEatCrops.update(container, game, delta))
                aiWander.update(container, game, delta);
        move();
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityPig(tutEngine));
    }
}
