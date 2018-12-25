package coffeecatteam.theultimatetile.game.tile.tiles;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Type;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class TileDirt extends Tile {

    public TileDirt(Engine engine, BufferedImage texture, String id, boolean isSolid, TileType tileType) {
        super(engine, texture, id, isSolid, tileType);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        BufferedImage overlay = Assets.DEAD_OVERLAY;
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
        g.drawImage(overlay, (int) (position.getX() * Tile.TILE_WIDTH - ((GameEngine) engine).getCamera().getxOffset()), (int) (position.getY() * Tile.TILE_HEIGHT - ((GameEngine) engine).getCamera().getyOffset()), TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getOverlay(int index) {
        return Assets.GRASS[Assets.GRASS_OVERLAY_INDEX + index];
    }

    private boolean isGrass(TilePos pos) {
        return getTileAt(pos) instanceof TileGrass;
    }

    private boolean isDirt(TilePos pos) {
        return getTileAt(pos) instanceof TileDirt || !(getTileAt(pos) instanceof TileGrass);
    }

    private boolean topLeftCorner() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.downRight());
        return patteren;
    }

    private boolean leftSide() {
        boolean patteren = isGrass(position.left()) &&
                isDirt(position.up()) &&
                isDirt(position.right()) &&
                isDirt(position.down());
        return patteren;
    }

    private boolean bottomLeftCorner() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down()) &&
                isDirt(position.upRight());
        return patteren;
    }

    private boolean topSide() {
        boolean patteren = isDirt(position.left()) &&
                isGrass(position.up()) &&
                isDirt(position.right()) &&
                isDirt(position.down());
        return patteren;
    }

    private boolean none() {
        boolean patteren = isDirt(position.upLeft()) &&
                isDirt(position.up()) &&
                isDirt(position.upRight()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.downLeft()) &&
                isDirt(position.down()) &&
                isDirt(position.downRight());
        return patteren;
    }

    private boolean bottomSide() {
        boolean patteren = isDirt(position.left()) &&
                isDirt(position.up()) &&
                isDirt(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean topRightCorner() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.right()) &&
                isDirt(position.left()) &&
                isDirt(position.down()) &&
                isDirt(position.downLeft());
        return patteren;
    }

    private boolean rightSide() {
        boolean patteren = isDirt(position.left()) &&
                isDirt(position.up()) &&
                isGrass(position.right()) &&
                isDirt(position.down());
        return patteren;
    }

    private boolean bottomRightCorner() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.right()) &&
                isDirt(position.left()) &&
                isGrass(position.down()) &&
                isDirt(position.upLeft());
        return patteren;
    }

    private boolean topLeftCornerSmall() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.downRight()) &&
                isGrass(position.upLeft());
        return patteren;
    }

    private boolean bottomLeftCornerSmall() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isDirt(position.upRight());
        return patteren;
    }

    private boolean topRightCornerSmall() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.downLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean bottomRightCornerSmall() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downRight()) &&
                isDirt(position.upLeft());
        return patteren;
    }

    private boolean topBottomRight() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downRight()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean bottomLeftRight() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean topBottomLeft() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.upLeft());
        return patteren;
    }

    private boolean topLeftRight() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean topLeftCornerInner() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean bottomLeftCornerInner() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean topRightCornerInner() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft());
        return patteren;
    }

    private boolean bottomRightCornerInner() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isGrass(position.down()) &&
                isGrass(position.upLeft());
        return patteren;
    }

    private boolean allSides() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.left()) &&
                isGrass(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean endUp() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down());
        return patteren;
    }

    private boolean straightUpDown() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down());
        return patteren;
    }

    private boolean endDown() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isGrass(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean endLeft() {
        boolean patteren = isGrass(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean straightLeftRight() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean endRight() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isGrass(position.down());
        return patteren;
    }

    private boolean allCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.upRight()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean downLeftUpRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upRight()) &&
                isGrass(position.downLeft());
        return patteren;
    }

    private boolean upLeftDownRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downRight()) &&
                isGrass(position.upLeft());
        return patteren;
    }

    private boolean leftUpRightDownRight() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upRight()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean leftDownRight() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.upRight()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean leftUpRight() {
        boolean patteren = isDirt(position.up()) &&
                isGrass(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upRight()) &&
                isDirt(position.downRight());
        return patteren;
    }

    private boolean rightUpLeftDownLeft() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.downLeft());
        return patteren;
    }

    private boolean rightDownLeft() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.upLeft()) &&
                isGrass(position.downLeft());
        return patteren;
    }

    private boolean rightUpLeft() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isGrass(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isDirt(position.downLeft());
        return patteren;
    }

    private boolean upDownLeftDownRight() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean upDownRight() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isDirt(position.downLeft()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean upDownLeft() {
        boolean patteren = isGrass(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isDirt(position.downRight());
        return patteren;
    }

    private boolean downUpLeftUpRight() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean downUpLeft() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down()) &&
                isDirt(position.upLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean downUpRight() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isGrass(position.down()) &&
                isDirt(position.upLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }

    private boolean downLeftUpRightDownRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.upRight()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean upLeftUpRightDownRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.upRight()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean upLeftDownLeftDownRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.downRight());
        return patteren;
    }

    private boolean upLeftDownLeftUpRightCorners() {
        boolean patteren = isDirt(position.up()) &&
                isDirt(position.left()) &&
                isDirt(position.right()) &&
                isDirt(position.down()) &&
                isGrass(position.upLeft()) &&
                isGrass(position.downLeft()) &&
                isGrass(position.upRight());
        return patteren;
    }
}
