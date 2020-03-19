package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class ConnectingTile extends Tile {

    protected Image[] overlap;
    private ArrayList<String> connect = new ArrayList<>(), ignore = new ArrayList<>();
    protected int alts;

    public ConnectingTile(MaewilEngine maewilEngine, Image[] connectingTextures, String id, boolean isSolid, TileType tileType, int alts) {
        super(maewilEngine, id, isSolid, tileType);
        this.overlap = connectingTextures;
        this.alts = alts;
    }

    public ConnectingTile(MaewilEngine maewilEngine, TileSettings settings, Image[] overlap, int alts) {
        super(maewilEngine, settings);
        this.overlap = overlap;
        this.alts = alts;
    }

    protected void connectsTo(String... ids) {
        this.connect.addAll(Arrays.asList(ids));
    }

    protected void ignores(String... ids) {
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

    private boolean connectToTile(TilePos pos) {
        return connect.contains(getTileAt(pos).getId());
    }

    private boolean ignoreTile(TilePos pos) {
        return ignore.contains(getTileAt(pos).getId()) || !connect.contains(getTileAt(pos).getId());
    }

    private boolean topLeftCorner() {
        return connectToTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downRight());
    }

    private boolean leftSide() {
        return connectToTile(position.left()) &&
                ignoreTile(position.up()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down());
    }

    private boolean bottomLeftCorner() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down()) &&
                ignoreTile(position.upRight());
    }

    private boolean topSide() {
        return ignoreTile(position.left()) &&
                connectToTile(position.up()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down());
    }

    private boolean none() {
        return ignoreTile(position.upLeft()) &&
                ignoreTile(position.up()) &&
                ignoreTile(position.upRight()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.downLeft()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downRight());
    }

    private boolean bottomSide() {
        return ignoreTile(position.left()) &&
                ignoreTile(position.up()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean topRightCorner() {
        return connectToTile(position.up()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downLeft());
    }

    private boolean rightSide() {
        return ignoreTile(position.left()) &&
                ignoreTile(position.up()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down());
    }

    private boolean bottomRightCorner() {
        return ignoreTile(position.up()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.down()) &&
                ignoreTile(position.upLeft());
    }

    private boolean topLeftCornerSmall() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downRight()) &&
                connectToTile(position.upLeft());
    }

    private boolean bottomLeftCornerSmall() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                ignoreTile(position.upRight());
    }

    private boolean topRightCornerSmall() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downLeft()) &&
                connectToTile(position.upRight());
    }

    private boolean bottomRightCornerSmall() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downRight()) &&
                ignoreTile(position.upLeft());
    }

    private boolean topBottomRight() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downRight()) &&
                connectToTile(position.upRight());
    }

    private boolean bottomLeftRight() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.downRight());
    }

    private boolean topBottomLeft() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.upLeft());
    }

    private boolean topLeftRight() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.upRight());
    }

    private boolean topLeftCornerInner() {
        return connectToTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downRight());
    }

    private boolean bottomLeftCornerInner() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down()) &&
                connectToTile(position.upRight());
    }

    private boolean topRightCornerInner() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft());
    }

    private boolean bottomRightCornerInner() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                connectToTile(position.down()) &&
                connectToTile(position.upLeft());
    }

    private boolean allSides() {
        return connectToTile(position.up()) &&
                connectToTile(position.left()) &&
                connectToTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean endUp() {
        return connectToTile(position.up()) &&
                connectToTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down());
    }

    private boolean straightUpDown() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down());
    }

    private boolean endDown() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                connectToTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean endLeft() {
        return connectToTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean straightLeftRight() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean endRight() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                connectToTile(position.down());
    }

    private boolean allCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.upRight()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.downRight());
    }

    private boolean downLeftUpRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upRight()) &&
                connectToTile(position.downLeft());
    }

    private boolean upLeftDownRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downRight()) &&
                connectToTile(position.upLeft());
    }

    private boolean leftUpRightDownRight() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upRight()) &&
                connectToTile(position.downRight());
    }

    private boolean leftDownRight() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.upRight()) &&
                connectToTile(position.downRight());
    }

    private boolean leftUpRight() {
        return ignoreTile(position.up()) &&
                connectToTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upRight()) &&
                ignoreTile(position.downRight());
    }

    private boolean rightUpLeftDownLeft() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.downLeft());
    }

    private boolean rightDownLeft() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.upLeft()) &&
                connectToTile(position.downLeft());
    }

    private boolean rightUpLeft() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                connectToTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                ignoreTile(position.downLeft());
    }

    private boolean upDownLeftDownRight() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.downRight());
    }

    private boolean upDownRight() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                ignoreTile(position.downLeft()) &&
                connectToTile(position.downRight());
    }

    private boolean upDownLeft() {
        return connectToTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                ignoreTile(position.downRight());
    }

    private boolean downUpLeftUpRight() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.upRight());
    }

    private boolean downUpLeft() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                ignoreTile(position.upRight());
    }

    private boolean downUpRight() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                connectToTile(position.down()) &&
                ignoreTile(position.upLeft()) &&
                connectToTile(position.upRight());
    }

    private boolean downLeftUpRightDownRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.upRight()) &&
                connectToTile(position.downRight());
    }

    private boolean upLeftUpRightDownRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.upRight()) &&
                connectToTile(position.downRight());
    }

    private boolean upLeftDownLeftDownRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.downRight());
    }

    private boolean upLeftDownLeftUpRightCorners() {
        return ignoreTile(position.up()) &&
                ignoreTile(position.left()) &&
                ignoreTile(position.right()) &&
                ignoreTile(position.down()) &&
                connectToTile(position.upLeft()) &&
                connectToTile(position.downLeft()) &&
                connectToTile(position.upRight());
    }
}
