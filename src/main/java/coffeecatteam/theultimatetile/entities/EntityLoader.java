package coffeecatteam.theultimatetile.entities;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityBouncer;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntitySkeleton;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityThing;
import coffeecatteam.theultimatetile.entities.creatures.undead.EntityZombie;
import coffeecatteam.theultimatetile.entities.statics.*;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.Items;

import java.util.Map;
import java.util.HashMap;

public class EntityLoader {
    
    private static Map<String, Entity> ENTITIES = new HashMap<>();
    
    public static void init(TheUltimateTile theUltimateTile) {
        // TREES
        registerEntity(new EntityTree(theUltimateTile, "tree_small", EntityTree.TreeType.SMALL));
        registerEntity(new EntityTree(theUltimateTile, "tree_medium", EntityTree.TreeType.MEDIUM));
        registerEntity(new EntityTree(theUltimateTile, "tree_large", EntityTree.TreeType.LARGE));
        
        // ROCKS
        registerEntity(new EntityRock(theUltimateTile, "rock_medium", Assets.ROCK_V1));
        registerEntity(new EntityRock(theUltimateTile, "rock_small", Assets.ROCK_V2));
        
        // UNDEAD
        registerEntity(new EntityZombie(theUltimateTile, "zombie"));
        registerEntity(new EntitySkeleton(theUltimateTile, "skeleton"));
        registerEntity(new EntityBouncer(theUltimateTile, "bouncer"));
        registerEntity(new EntityThing(theUltimateTile, "thing"));
        
        // BUSHES
        registerEntity(new EntityBush(theUltimateTile, "bush_small", Assets.BUSH_SMALL, Entity.DEFAULT_WIDTH));
        registerEntity(new EntityBush(theUltimateTile, "bush_large", Assets.BUSH_LARGE, Entity.DEFAULT_WIDTH * 2));
        
        // CROPS
        registerEntity(new EntityCrop(theUltimateTile, "carrot_crop", Assets.CARROT_CROP, Items.CARROT));
        
        // OTHER
        registerEntity(new EntityUltimateTile(theUltimateTile, "ultimate"));
    }
    
    private static void registerEntity(Entity entity) {
        ENTITIES.put(entity.getId(), entity);
    }

    public static Entity loadEntity(String id) {
        return ENTITIES.get(id);
    }
}
