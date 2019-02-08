package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.manager.ItemManager;
import org.newdawn.slick.Image;

public class EntityRock extends EntityNature {

    public EntityRock(TutEngine tutEngine, String id, Image texture) {
        super(tutEngine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.STONE);

        bounds.x = 0;
        bounds.y = height / 2f + height / 3f;
        bounds.width = width;
        bounds.height = height / 3;

        drops.add(ItemManager.ROCK);
    }
}
