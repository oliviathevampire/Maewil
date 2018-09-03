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

    public static Entity loadEntity(String id) {
        return ENTITIES.get(id);
    }
}
