package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.awt.image.BufferedImage;

public class EntityRock extends EntityNature {

    public EntityRock(Engine engine, String id, BufferedImage texture) {
        super(engine, id, texture, Entity.DEFAULT_WIDTH, Entity.DEFAULT_HEIGHT, EntityHitType.STONE);

        bounds.x = 0;
        bounds.y = height / 2f + height / 3f;
        bounds.width = width;
        bounds.height = height / 3;

        drops.add(ItemManager.ROCK);
    }
}
