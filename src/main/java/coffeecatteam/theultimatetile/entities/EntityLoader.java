package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.creatures.*;
import coffeecatteam.theultimatetile.entities.statics.*;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.items.Items;

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
            case "skeleton":
                return new EntitySkeleton(handler, id);
            case "bouncer":
                return new EntityBouncer(handler, id);
            case "thing":
                return new EntityThing(handler, id);
            case "ultimate":
                return new EntityUltimateTile(handler, id);
            case "bush_small":
                return new EntityBush(handler, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(handler, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);
            case "carrot_crop":
                return new EntityCrop(handler, id, Assets.CARROT_CROP, Items.CARROT);
        }
        return new EntityRock(handler, "rock", Assets.ROCK_V1);
    }
}
