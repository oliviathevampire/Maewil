package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.Entity;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.items.ItemStack;
import coffeecatteam.tilegame.items.Items;
import coffeecatteam.tilegame.tiles.Tile;
import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class EntityTree extends EntityStatic {

    private TreeType type;

    public EntityTree(Handler handler, String id, TreeType type) {
        super(handler, id, type.getWidth(), type.getHeight());
        this.type = type;

        bounds.x = 20 + type.getBoundsOffset();
        bounds.y = 64;
        bounds.width = 28;
        bounds.height = 64;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(type.getTexture(), (int) (this.x - handler.getCamera().getxOffset()), (int) (this.y - handler.getCamera().getyOffset()), width, height, null);
    }

    @Override
    public void die(Iterator<Entity> it) {
        super.die(it);
        for (int i = 0; i < Utils.getRandomInt(1, 3); i++) {
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.STICK), (int) (this.x + new Random().nextInt(3)), (int) (this.y + new Random().nextInt(3)));
            handler.getWorld().getItemManager().addItem(new ItemStack(Items.LEAF), (int) (this.x + new Random().nextInt(3)), (int) (this.y + new Random().nextInt(3)));
        }
        //if (Utils.getRandomInt(0, 1000) < 900)
        handler.getWorld().getItemManager().addItem(new ItemStack(Items.APPLE), (int) (this.x + new Random().nextInt(3)), (int) (this.y + new Random().nextInt(3)));
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
