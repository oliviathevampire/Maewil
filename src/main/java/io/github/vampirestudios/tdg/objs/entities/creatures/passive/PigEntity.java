package io.github.vampirestudios.tdg.objs.entities.creatures.passive;

import io.github.vampirestudios.tdg.objs.entities.Entity;
import io.github.vampirestudios.tdg.objs.entities.ai.EatCropsGoal;
import io.github.vampirestudios.tdg.objs.entities.creatures.EntityPassive;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.mini2Dx.core.game.GameContainer;

public class PigEntity extends EntityPassive {

    private EatCropsGoal aiEatCrops;

    public PigEntity(MaewilEngine maewilEngine) {
        super(maewilEngine, "pig");
        this.drop = Items.RAW_PORK;
        aiEatCrops = new EatCropsGoal(maewilEngine, this);
    }

    @Override
    public void update(GameContainer container, float delta) {
        xMove = 0;
        yMove = 0;

        if (TAGS.hasKey("eatCrops"))
            aiEatCrops.setCropIds(TAGS.getTagList("eatCrops"));

        // Movement
        if (maewilEngine.getPlayer().isActive())
            if (!aiEatCrops.update(container, delta))
                aiWander.update(container, delta);
        move();
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new PigEntity(maewilEngine));
    }
}
