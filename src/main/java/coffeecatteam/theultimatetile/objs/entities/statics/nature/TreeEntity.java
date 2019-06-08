package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.AABB;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.NatureEntity;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.start.TutEngine;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class TreeEntity extends NatureEntity {

    private TreeType type;

    public TreeEntity(TutEngine tutEngine, TreeType type) {
        super(tutEngine, "tree", type.getWidth(), type.getHeight(), HitType.WOOD);
        this.type = type;
        setCurrentTexture(this.type.getId());
//        setCurrentTextureId(this.type.getId());

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
        drops.add(NumberUtils.getRandomBoolean() ? Items.APPLE : Items.STICK);
    }

    @Override
    public String getUnlocalizedName() {
        return getId() + "_" + this.type.getId();
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        super.update(container, game, delta);
    }

    @Override
    public AABB getTileBounds() {
        return new AABB(width / 4f, height - height / 4f, width / 2, height / 4);
    }

    public enum TreeType {

        SMALL("small", Tile.TILE_SIZE, Tile.TILE_SIZE * 2),
        MEDIUM("medium", Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2),
        LARGE("large", Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 2),
        EXTRA_LARGE("extra_large", Tile.TILE_SIZE * 2, Tile.TILE_SIZE * 4);

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
        return super.newCopy(new TreeEntity(tutEngine, type));
    }
}
