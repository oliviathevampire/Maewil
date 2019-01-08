package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.gfx.assets.Assets;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import java.util.Arrays;
import java.util.List;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public abstract class TileOverlap extends Tile {

    private Image[] overlap;
    private List<Class<? extends Tile>> connect;
    private Class<? extends Tile> ignore;
    private int alts;

    public TileOverlap(Engine engine, Image texture, Image[] overlap, String id, boolean isSolid, TileType tileType, int alts) {
        super(engine, texture, id, isSolid, tileType);
        this.overlap = overlap;
        this.alts = alts;
    }

    protected void setConnect(Class<? extends Tile>... connect) {
        this.connect = Arrays.asList(connect);
    }

    protected void setIgnore(Class<? extends Tile> ignore) {
        this.ignore = ignore;
    }

    @Override
    public void render(Graphics g, int x, int y, int width, int height) {
        super.render(g, x, y, width, height);

        if (worldLayer != null) {
            Image overlay = Assets.DEAD_OVERLAY;
            if (topLeftCorner())
                overlay = getOverlay(0);
            if (leftSide())
                overlay = getOverlay(1);
            if (bottomLeftCorner())
                overlay = getOverlay(2);

            if (topSide())
                overlay = getOverlay(3);
            if (none())
                overlay = getOverlay(4);
            if (bottomSide())
                overlay = getOverlay(5);

            if (topRightCorner())
                overlay = getOverlay(6);
            if (rightSide())
                overlay = getOverlay(7);
            if (bottomRightCorner())
                overlay = getOverlay(8);

            if (topLeftCornerSmall())
                overlay = getOverlay(9);
            if (bottomLeftCornerSmall())
                overlay = getOverlay(10);
            if (topRightCornerSmall())
                overlay = getOverlay(11);
            if (bottomRightCornerSmall())
                overlay = getOverlay(12);

            if (topBottomRight())
                overlay = getOverlay(13);
            if (bottomLeftRight())
                overlay = getOverlay(14);
            if (topBottomLeft())
                overlay = getOverlay(15);
            if (topLeftRight())
                overlay = getOverlay(16);

            if (topLeftCornerInner())
                overlay = getOverlay(17);
            if (bottomLeftCornerInner())
                overlay = getOverlay(18);
            if (topRightCornerInner())
                overlay = getOverlay(19);
            if (bottomRightCornerInner())
                overlay = getOverlay(20);

            if (allSides())
                overlay = getOverlay(21);

            if (endUp())
                overlay = getOverlay(22);
            if (straightUpDown())
                overlay = getOverlay(23);
            if (endDown())
                overlay = getOverlay(24);

            if (endLeft())
                overlay = getOverlay(25);
            if (straightLeftRight())
                overlay = getOverlay(26);
            if (endRight())
                overlay = getOverlay(27);

            if (allCorners())
                overlay = getOverlay(28);

            if (downLeftUpRightCorners())
                overlay = getOverlay(29);
            if (upLeftDownRightCorners())
                overlay = getOverlay(30);

            if (leftUpRightDownRight())
                overlay = getOverlay(31);
            if (leftDownRight())
                overlay = getOverlay(32);
            if (leftUpRight())
                overlay = getOverlay(33);

            if (rightUpLeftDownLeft())
                overlay = getOverlay(34);
            if (rightDownLeft())
                overlay = getOverlay(35);
            if (rightUpLeft())
                overlay = getOverlay(36);

            if (upDownLeftDownRight())
                overlay = getOverlay(37);
            if (upDownRight())
                overlay = getOverlay(38);
            if (upDownLeft())
                overlay = getOverlay(39);

            if (downUpLeftUpRight())
                overlay = getOverlay(40);
            if (downUpLeft())
                overlay = getOverlay(41);
            if (downUpRight())
                overlay = getOverlay(42);

            if (downLeftUpRightDownRightCorners())
                overlay = getOverlay(43);
            if (upLeftUpRightDownRightCorners())
                overlay = getOverlay(44);
            if (upLeftDownLeftDownRightCorners())
                overlay = getOverlay(45);
            if (upLeftDownLeftUpRightCorners())
                overlay = getOverlay(46);

            if (allCorners())
                overlay = getOverlay(47);
            overlay.draw(x, y, width, height);
        }
    }

    private Image getOverlay(int index) {
        return overlap[alts + index];
    }

    private boolean isConnected(TilePos pos) {
        return connect.contains(getTileAt(pos).getClass());
    }

    private boolean notConnected(TilePos pos) {
        return getTileAt(pos).getClass().equals(ignore) || !connect.contains(getTileAt(pos).getClass());
    }

    private boolean topLeftCorner() {
        return isConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.downRight());
    }

    private boolean leftSide() {
        return isConnected(position.left()) &&
                notConnected(position.up()) &&
                notConnected(position.right()) &&
                notConnected(position.down());
    }

    private boolean bottomLeftCorner() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down()) &&
                notConnected(position.upRight());
    }

    private boolean topSide() {
        return notConnected(position.left()) &&
                isConnected(position.up()) &&
                notConnected(position.right()) &&
                notConnected(position.down());
    }

    private boolean none() {
        return notConnected(position.upLeft()) &&
                notConnected(position.up()) &&
                notConnected(position.upRight()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.downLeft()) &&
                notConnected(position.down()) &&
                notConnected(position.downRight());
    }

    private boolean bottomSide() {
        return notConnected(position.left()) &&
                notConnected(position.up()) &&
                notConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean topRightCorner() {
        return isConnected(position.up()) &&
                isConnected(position.right()) &&
                notConnected(position.left()) &&
                notConnected(position.down()) &&
                notConnected(position.downLeft());
    }

    private boolean rightSide() {
        return notConnected(position.left()) &&
                notConnected(position.up()) &&
                isConnected(position.right()) &&
                notConnected(position.down());
    }

    private boolean bottomRightCorner() {
        return notConnected(position.up()) &&
                isConnected(position.right()) &&
                notConnected(position.left()) &&
                isConnected(position.down()) &&
                notConnected(position.upLeft());
    }

    private boolean topLeftCornerSmall() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.downRight()) &&
                isConnected(position.upLeft());
    }

    private boolean bottomLeftCornerSmall() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                notConnected(position.upRight());
    }

    private boolean topRightCornerSmall() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.downLeft()) &&
                isConnected(position.upRight());
    }

    private boolean bottomRightCornerSmall() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downRight()) &&
                notConnected(position.upLeft());
    }

    private boolean topBottomRight() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downRight()) &&
                isConnected(position.upRight());
    }

    private boolean bottomLeftRight() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.downRight());
    }

    private boolean topBottomLeft() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.upLeft());
    }

    private boolean topLeftRight() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.upRight());
    }

    private boolean topLeftCornerInner() {
        return isConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downRight());
    }

    private boolean bottomLeftCornerInner() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down()) &&
                isConnected(position.upRight());
    }

    private boolean topRightCornerInner() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft());
    }

    private boolean bottomRightCornerInner() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                isConnected(position.down()) &&
                isConnected(position.upLeft());
    }

    private boolean allSides() {
        return isConnected(position.up()) &&
                isConnected(position.left()) &&
                isConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean endUp() {
        return isConnected(position.up()) &&
                isConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down());
    }

    private boolean straightUpDown() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down());
    }

    private boolean endDown() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                isConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean endLeft() {
        return isConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean straightLeftRight() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean endRight() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                isConnected(position.down());
    }

    private boolean allCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.upRight()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.downRight());
    }

    private boolean downLeftUpRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upRight()) &&
                isConnected(position.downLeft());
    }

    private boolean upLeftDownRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downRight()) &&
                isConnected(position.upLeft());
    }

    private boolean leftUpRightDownRight() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upRight()) &&
                isConnected(position.downRight());
    }

    private boolean leftDownRight() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.upRight()) &&
                isConnected(position.downRight());
    }

    private boolean leftUpRight() {
        return notConnected(position.up()) &&
                isConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upRight()) &&
                notConnected(position.downRight());
    }

    private boolean rightUpLeftDownLeft() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.downLeft());
    }

    private boolean rightDownLeft() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.upLeft()) &&
                isConnected(position.downLeft());
    }

    private boolean rightUpLeft() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                isConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                notConnected(position.downLeft());
    }

    private boolean upDownLeftDownRight() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.downRight());
    }

    private boolean upDownRight() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                notConnected(position.downLeft()) &&
                isConnected(position.downRight());
    }

    private boolean upDownLeft() {
        return isConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                notConnected(position.downRight());
    }

    private boolean downUpLeftUpRight() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.upRight());
    }

    private boolean downUpLeft() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                notConnected(position.upRight());
    }

    private boolean downUpRight() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                isConnected(position.down()) &&
                notConnected(position.upLeft()) &&
                isConnected(position.upRight());
    }

    private boolean downLeftUpRightDownRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.upRight()) &&
                isConnected(position.downRight());
    }

    private boolean upLeftUpRightDownRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.upRight()) &&
                isConnected(position.downRight());
    }

    private boolean upLeftDownLeftDownRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.downRight());
    }

    private boolean upLeftDownLeftUpRightCorners() {
        return notConnected(position.up()) &&
                notConnected(position.left()) &&
                notConnected(position.right()) &&
                notConnected(position.down()) &&
                isConnected(position.upLeft()) &&
                isConnected(position.downLeft()) &&
                isConnected(position.upRight());
    }
}
