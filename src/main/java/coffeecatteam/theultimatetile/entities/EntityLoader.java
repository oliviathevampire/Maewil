package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.entities.statics.*;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

public class EntityLoader {

    public static Entity loadEntity(TheUltimateTile theUltimateTile, String id) {
        switch (id) {
            case "tree_small":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.SMALL);
            case "tree_medium":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.MEDIUM);
            case "tree_large":
                return new EntityTree(theUltimateTile, id, EntityTree.TreeType.LARGE);
            case "rock_medium":
                return new EntityRock(theUltimateTile, id, Assets.ROCK_V1);
            case "rock_small":
                return new EntityRock(theUltimateTile, id, Assets.ROCK_V2);
            case "zombie":
                return new EntityZombie(theUltimateTile, id);
            case "skeleton":
                return new EntitySkeleton(theUltimateTile, id);
            case "bouncer":
                return new EntityBouncer(theUltimateTile, id);
            case "thing":
                return new EntityThing(theUltimateTile, id);
            case "ultimate":
                return new EntityUltimateTile(theUltimateTile, id);
            case "bush_small":
                return new EntityBush(theUltimateTile, id, Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH);
            case "bush_large":
                return new EntityBush(theUltimateTile, id, Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2);
            case "carrot_crop":
                return new EntityCrop(theUltimateTile, id, Assets.CARROT_CROP, Items.CARROT);
        }
        return new EntityRock(theUltimateTile, "rock", Assets.ROCK_V1);
    }
}
