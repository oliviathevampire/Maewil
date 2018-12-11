package coffeecatteam.theultimatetile.game.entities.statics.nature;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.Entity;
import coffeecatteam.theultimatetile.game.entities.statics.EntityNature;
import coffeecatteam.theultimatetile.game.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.game.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.game.tiles.Tile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.manager.ItemManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class EntityTree extends EntityNature {

    public EntityTree(Engine engine, String id, TreeType type) {
        super(engine, id, type.getTexture(), type.getWidth(), type.getHeight(), EntityHitType.WOOD);

        bounds.x = 20 + type.getBoundsOffset();
        bounds.y = 96;
        bounds.width = 28;
        bounds.height = 32;

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

        private BufferedImage texture;
        private int width, height, boundsOffset;

        TreeType(BufferedImage texture, int width, int height, int boundsOffset) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.boundsOffset = boundsOffset;
        }

        public BufferedImage getTexture() {
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
