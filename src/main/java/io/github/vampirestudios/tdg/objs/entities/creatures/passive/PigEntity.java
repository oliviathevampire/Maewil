package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ai.EatCropsGoal;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class PigEntity extends EntityPassive {

    private EatCropsGoal aiEatCrops;

    public PigEntity(TutEngine tutEngine) {
        super(tutEngine, "pig");
        this.drop = Items.RAW_PORK;
        aiEatCrops = new EatCropsGoal(tutEngine, this);
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
        return super.newCopy(new PigEntity(tutEngine));
    }
}
