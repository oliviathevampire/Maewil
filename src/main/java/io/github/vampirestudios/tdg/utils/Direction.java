package io.github.vampirestudios.tdg.utils;

public enum Direction {
    NONE(0, 0),

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),

    LEFT_UP(-1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_UP(1, 1),
    RIGHT_DOWN(1, -1);

    public static final Direction[] DIRECTIONS = values();
    public static final Direction[] ADJACENT = new Direction[]{UP, RIGHT, DOWN, LEFT};
    public static final Direction[] ADJACENT_INCLUDING_NONE = new Direction[]{NONE, UP, RIGHT, DOWN, LEFT};
    public static final Direction[] DIAGONAL = new Direction[]{LEFT_UP, RIGHT_UP, RIGHT_DOWN, LEFT_DOWN};
    public static final Direction[] DIAGONAL_INCLUDING_NONE = new Direction[]{NONE, LEFT_UP, RIGHT_UP, RIGHT_DOWN, LEFT_DOWN};
    public static final Direction[] SURROUNDING = new Direction[]{LEFT_UP, UP, RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN, LEFT};
    public static final Direction[] SURROUNDING_INCLUDING_NONE = new Direction[]{NONE, LEFT_UP, UP, RIGHT_UP, RIGHT, RIGHT_DOWN, DOWN, LEFT_DOWN, LEFT};
    public final int x;
    public final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getOpposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case LEFT_UP:
                return RIGHT_DOWN;
            case RIGHT_DOWN:
                return LEFT_UP;
            case LEFT_DOWN:
                return RIGHT_UP;
            case RIGHT_UP:
                return LEFT_DOWN;
            default:
                return NONE;
        }
    }

    public static Direction getHorizontal(int side) {
        if (side < 0) return LEFT;
        if (side > 0) return RIGHT;
        return NONE;
    }
  
    public static Direction getVertical(int side) {
        if (side < 0) return DOWN;
        if (side > 0) return UP;
        return NONE;
    }
}