package coffeecatteam.theultimatetile.entities.statics.nature;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.entities.Entity;
import coffeecatteam.theultimatetile.entities.statics.EntityStatic;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.inventory.items.ItemStack;
import coffeecatteam.theultimatetile.inventory.items.Items;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class EntityTree extends EntityStatic {

    private TreeType type;

    public EntityTree(TheUltimateTile theUltimateTile, String id, TreeType type) {
        super(theUltimateTile, id, type.getWidth(), type.getHeight());
        this.type = type;

        bounds.x = 20 + type.getBoundsOffset();
        bounds.y = 96;
        bounds.width = 28;
        bounds.height = 32;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(type.getTexture(), (int) (this.x - theUltimateTile.getCamera().getxOffset()), (int) (this.y - theUltimateTile.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(List<Entity> entities, int index) {
        super.die(entities, index);
        for (int i = 0; i < Utils.getRandomInt(1, 3); i++) {
            theUltimateTile.getItemManager().addItem(new ItemStack(Items.STICK), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
            theUltimateTile.getItemManager().addItem(new ItemStack(Items.LEAF), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
        }
        theUltimateTile.getItemManager().addItem(new ItemStack(Items.APPLE), x + Utils.getRandomInt(0, width), y + Utils.getRandomInt(0, height));
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
