package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author CoffeeCatRailway
 * Created: 26/12/2018
 */
public abstract class OverlapTile extends Tile {

    protected Image[] overlap;
    private ArrayList<String> connect = new ArrayList<>(), ignore = new ArrayList<>();
    protected int alts;

    public OverlapTile(MaewilEngine maewilEngine, Image[] overlap, String id, boolean isSolid, TileType tileType, int alts) {
        super(maewilEngine, id, isSolid, tileType);
        this.overlap = overlap;
        this.alts = alts;
    }

    protected void setConnect(String... ids) {
        this.connect.addAll(Arrays.asList(ids));
    }

    protected void setIgnore(String... ids) {
        this.ignore.addAll(Arrays.asList(ids));
    }

    @Override
    public void render(Graphics g, int x, int y, int width, int height) {
        super.render(g, x, y, width, height);

        if (worldLayer != null) {
            Image overlay = Assets.GUI_DEAD_OVERLAY;
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

    private boolean connect(TilePos pos) {
        return connect.contains(getTileAt(pos).getId());
    }

    private boolean ignore(TilePos pos) {
        return ignore.contains(getTileAt(pos).getId()) || !connect.contains(getTileAt(pos).getId());
    }

    private boolean topLeftCorner() {
        return connect(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                ignore(position.downRight());
    }

    private boolean leftSide() {
        return connect(position.left()) &&
                ignore(position.up()) &&
                ignore(position.right()) &&
                ignore(position.down());
    }

    private boolean bottomLeftCorner() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                connect(position.down()) &&
                ignore(position.upRight());
    }

    private boolean topSide() {
        return ignore(position.left()) &&
                connect(position.up()) &&
                ignore(position.right()) &&
                ignore(position.down());
    }

    private boolean none() {
        return ignore(position.upLeft()) &&
                ignore(position.up()) &&
                ignore(position.upRight()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.downLeft()) &&
                ignore(position.down()) &&
                ignore(position.downRight());
    }

    private boolean bottomSide() {
        return ignore(position.left()) &&
                ignore(position.up()) &&
                ignore(position.right()) &&
                connect(position.down());
    }

    private boolean topRightCorner() {
        return connect(position.up()) &&
                connect(position.right()) &&
                ignore(position.left()) &&
                ignore(position.down()) &&
                ignore(position.downLeft());
    }

    private boolean rightSide() {
        return ignore(position.left()) &&
                ignore(position.up()) &&
                connect(position.right()) &&
                ignore(position.down());
    }

    private boolean bottomRightCorner() {
        return ignore(position.up()) &&
                connect(position.right()) &&
                ignore(position.left()) &&
                connect(position.down()) &&
                ignore(position.upLeft());
    }

    private boolean topLeftCornerSmall() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                ignore(position.downRight()) &&
                connect(position.upLeft());
    }

    private boolean bottomLeftCornerSmall() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                ignore(position.upRight());
    }

    private boolean topRightCornerSmall() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                ignore(position.downLeft()) &&
                connect(position.upRight());
    }

    private boolean bottomRightCornerSmall() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downRight()) &&
                ignore(position.upLeft());
    }

    private boolean topBottomRight() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downRight()) &&
                connect(position.upRight());
    }

    private boolean bottomLeftRight() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                connect(position.downRight());
    }

    private boolean topBottomLeft() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                connect(position.upLeft());
    }

    private boolean topLeftRight() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.upRight());
    }

    private boolean topLeftCornerInner() {
        return connect(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downRight());
    }

    private boolean bottomLeftCornerInner() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                connect(position.down()) &&
                connect(position.upRight());
    }

    private boolean topRightCornerInner() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft());
    }

    private boolean bottomRightCornerInner() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                connect(position.down()) &&
                connect(position.upLeft());
    }

    private boolean allSides() {
        return connect(position.up()) &&
                connect(position.left()) &&
                connect(position.right()) &&
                connect(position.down());
    }

    private boolean endUp() {
        return connect(position.up()) &&
                connect(position.left()) &&
                connect(position.right()) &&
                ignore(position.down());
    }

    private boolean straightUpDown() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                connect(position.right()) &&
                ignore(position.down());
    }

    private boolean endDown() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                connect(position.right()) &&
                connect(position.down());
    }

    private boolean endLeft() {
        return connect(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                connect(position.down());
    }

    private boolean straightLeftRight() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                connect(position.down());
    }

    private boolean endRight() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                connect(position.down());
    }

    private boolean allCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.upRight()) &&
                connect(position.downLeft()) &&
                connect(position.downRight());
    }

    private boolean downLeftUpRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upRight()) &&
                connect(position.downLeft());
    }

    private boolean upLeftDownRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downRight()) &&
                connect(position.upLeft());
    }

    private boolean leftUpRightDownRight() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upRight()) &&
                connect(position.downRight());
    }

    private boolean leftDownRight() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                ignore(position.upRight()) &&
                connect(position.downRight());
    }

    private boolean leftUpRight() {
        return ignore(position.up()) &&
                connect(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upRight()) &&
                ignore(position.downRight());
    }

    private boolean rightUpLeftDownLeft() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.downLeft());
    }

    private boolean rightDownLeft() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                ignore(position.down()) &&
                ignore(position.upLeft()) &&
                connect(position.downLeft());
    }

    private boolean rightUpLeft() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                connect(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                ignore(position.downLeft());
    }

    private boolean upDownLeftDownRight() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                connect(position.downRight());
    }

    private boolean upDownRight() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                ignore(position.downLeft()) &&
                connect(position.downRight());
    }

    private boolean upDownLeft() {
        return connect(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                ignore(position.downRight());
    }

    private boolean downUpLeftUpRight() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                connect(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.upRight());
    }

    private boolean downUpLeft() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                connect(position.down()) &&
                connect(position.upLeft()) &&
                ignore(position.upRight());
    }

    private boolean downUpRight() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                connect(position.down()) &&
                ignore(position.upLeft()) &&
                connect(position.upRight());
    }

    private boolean downLeftUpRightDownRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.downLeft()) &&
                connect(position.upRight()) &&
                connect(position.downRight());
    }

    private boolean upLeftUpRightDownRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.upRight()) &&
                connect(position.downRight());
    }

    private boolean upLeftDownLeftDownRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.downLeft()) &&
                connect(position.downRight());
    }

    private boolean upLeftDownLeftUpRightCorners() {
        return ignore(position.up()) &&
                ignore(position.left()) &&
                ignore(position.right()) &&
                ignore(position.down()) &&
                connect(position.upLeft()) &&
                connect(position.downLeft()) &&
                connect(position.upRight());
    }
}
