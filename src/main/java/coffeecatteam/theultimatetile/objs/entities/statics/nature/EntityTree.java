package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;

public class EntityTree extends EntityNature {

    private TreeType type;

    public EntityTree(TutEngine tutEngine, TreeType type) {
        super(tutEngine, "tree", type.getWidth(), type.getHeight(), EntityHitType.WOOD);
        setCurrentTexture(type.getId());
        this.type = type;

        bounds.x = width / 4f;
        bounds.y = height - height / 4f;
        bounds.width = width / 2;
        bounds.height = height / 4;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
        drops.add(NumberUtils.getRandomBoolean() ? Items.APPLE : Items.STICK);
    }

    public enum TreeType {

        SMALL("small", Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2),
        MEDIUM("medium", Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2),
        LARGE("large", Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2),
        EXTRA_LARGE("extra_large", Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 4);

        private String id;
        private int width, height;

        TreeType(String id, int width, int height) {
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
        return super.newCopy(new EntityTree(tutEngine, type));
    }
}
