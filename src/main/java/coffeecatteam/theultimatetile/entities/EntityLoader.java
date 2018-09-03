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
<<<<<<< HEAD
    
    private static Map<String, Entity> ENTITIES = new HashMap<>();
    
    public static void init() {
        ENTITIES.clear();
        // TREES
        registerEntity(new EntityTree("tree_small", EntityTree.TreeType.SMALL));
        registerEntity(new EntityTree("tree_medium", EntityTree.TreeType.MEDIUM));
        registerEntity(new EntityTree("tree_large", EntityTree.TreeType.LARGE));
        
        // ROCKS
        registerEntity(new EntityRock("rock_medium", Assets.ROCK_V1));
        registerEntity(new EntityRock("rock_small", Assets.ROCK_V2));
        
        // UNDEAD
        registerEntity(new EntityZombie("zombie"));
        registerEntity(new EntitySkeleton("skeleton"));
        registerEntity(new EntityBouncer("bouncer"));
        registerEntity(new EntityThing("thing"));
        
        // BUSHES
        registerEntity(new EntityBush("bush_small", Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH));
        registerEntity(new EntityBush("bush_large", Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2));
        
        // CROPS
        registerEntity(new EntityCrop("carrot_crop", Assets.CARROT_CROP, Items.CARROT));
        
        // OTHER
        registerEntity(new EntityUltimateTile("ultimate"));
    }
    
    private static void registerEntity(Entity entity) {
        String id = entity.getId();
        entity.setTheUltimateTile(TheUltimateTile.getTheUltimateTile());
        ENTITIES.put(id, entity);
    }
=======
>>>>>>> parent of a7a9dc5... New entity registry PART-1

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
