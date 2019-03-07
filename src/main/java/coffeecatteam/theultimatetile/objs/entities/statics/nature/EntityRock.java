package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

public class EntityRock extends EntityNature {

    private RockType type;

    public EntityRock(TutEngine tutEngine, RockType type) {
        super(tutEngine, "rock", type.getWidth(), type.getHeight(), EntityHitType.STONE);
        setCurrentTexture(type.getId());
        this.type = type;

        drops.add(Items.ROCK);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(0, height / 2f + height / 3f, width, height / 3);
    }

    public enum RockType {

        SMALL("small", Tile.TILE_WIDTH, Tile.TILE_HEIGHT),
        MEDIUM("medium", Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT);

        private String id;
        private int width, height;

        RockType(String id, int width, int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }

        public String getId() {
            return id;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    @Override
    public Entity newCopy() {
        return super.newCopy(new EntityRock(tutEngine, type));
    }
}
