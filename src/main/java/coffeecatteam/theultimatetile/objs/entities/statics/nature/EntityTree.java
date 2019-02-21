package coffeecatteam.theultimatetile.objs.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.objs.items.Items;
import org.newdawn.slick.Image;

import java.util.List;

public class EntityTree extends EntityNature {

    public EntityTree(TutEngine tutEngine, String id, TreeType type) {
        super(tutEngine, id, type.getTexture(), type.getWidth(), type.getHeight(), EntityHitType.WOOD);

        bounds.x = width / 4f;
        bounds.y = height - height / 4f;
        bounds.width = width / 2;
        bounds.height = height / 4;

        drops.add(Items.LEAF);
        drops.add(Items.STICK);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        tutEngine.getItems().addItem(new ItemStack(Items.APPLE), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
    }

    public enum TreeType {

        SMALL(Assets.TREE_SMALL, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2),
        MEDIUM(Assets.TREE_MEDIUM, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2),
        LARGE(Assets.TREE_LARGE, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2),
        EXTRA_LARGE(Assets.TREE_EXTRA_LARGE, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 4);

        private Image texture;
        private int width, height;

        TreeType(Image texture, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
        }

        public Image getTexture() {
            return texture;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
