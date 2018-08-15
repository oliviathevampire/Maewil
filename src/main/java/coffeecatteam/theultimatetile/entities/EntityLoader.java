package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.EntityZombie;
import coffeecatteam.theultimatetile.entities.statics.EntityBush;
import coffeecatteam.theultimatetile.entities.statics.EntityRock;
import coffeecatteam.theultimatetile.entities.statics.EntityTree;
import coffeecatteam.theultimatetile.entities.statics.EntityUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;

public class EntityLoader {

    public static Entity loadEntity(Handler handler, String id) {
        switch (id) {
            case "tree_small":
                return new EntityTree(handler, id, EntityTree.TreeType.SMALL);
            case "tree_medium":
                return new EntityTree(handler, id, EntityTree.TreeType.MEDIUM);
            case "tree_large":
                return new EntityTree(handler, id, EntityTree.TreeType.LARGE);
            case "rock_medium":
                return new EntityRock(handler, id, Assets.ROCK_V1);
            case "rock_small":
                return new EntityRock(handler, id, Assets.ROCK_V2);
            case "zombie":
                return new EntityZombie(handler, id);
            case "ultimate":
                return new EntityUltimateTile(handler, id);
            case "bush_small":
                return new EntityBush(handler, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(handler, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);
        }
        return new EntityRock(handler, "rock", Assets.ROCK_V1);
    }
}
