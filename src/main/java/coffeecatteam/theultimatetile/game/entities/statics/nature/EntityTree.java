package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

import org.newdawn.slick.Image;
import java.util.List;

public class EntityTree extends EntityNature {

    public EntityTree(Engine engine, String id, TreeType type) {
        super(engine, id, type.getTexture(), type.getWidth(), type.getHeight(), EntityHitType.WOOD);

        bounds.x = width / 4f;
        bounds.y = height - height / 4f;
        bounds.width = width / 2;
        bounds.height = height / 4;

        drops.add(ItemManager.LEAF);
        drops.add(ItemManager.STICK);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        ((GameEngine) engine).getItemManager().addItem(new ItemStack(ItemManager.APPLE), (float) position.x + NumberUtils.getRandomInt(width), (float) position.y + NumberUtils.getRandomInt(height));
    }

    public enum TreeType {

        SMALL(Assets.TREE_SMALL, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2, 0),
        MEDIUM(Assets.TREE_MEDIUM, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2, 32),
        LARGE(Assets.TREE_LARGE, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 2, 32);

        private Image texture;
        private int width, height, boundsOffset;

        TreeType(Image texture, int width, int height, int boundsOffset) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.boundsOffset = boundsOffset;
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

        public int getBoundsOffset() {
            return boundsOffset;
        }
    }
}
