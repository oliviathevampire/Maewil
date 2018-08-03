package coffeecatteam.tilegame.entities.statics;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.gfx.Assets;
import coffeecatteam.tilegame.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntityTree extends EntityStatic {

    private  TreeType type;

    public EntityTree(Handler handler, float x, float y, TreeType type) {
        super(handler, x, y, type.getWidth(), type.getHeight());
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

//        g.setColor(Color.red);
//        g.fillRect((int) (x + bounds.x - handler.getCamera().getxOffset()), (int) (y + bounds.y - handler.getCamera().getyOffset()), bounds.width, bounds.height);
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
