package coffeecatteam.tilegame.entities;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.creatures.EntityZombie;
import coffeecatteam.tilegame.entities.statics.EntityRock;
import coffeecatteam.tilegame.entities.statics.EntityTree;
import coffeecatteam.tilegame.entities.statics.EntityUltimateTile;
import coffeecatteam.tilegame.gfx.Assets;

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
        }
        return new EntityRock(handler, "rock", Assets.ROCK_V1);
    }
}
