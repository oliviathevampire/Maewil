package io.github.vampirestudios.tdg.utils;

import coffeecatteam.coffeecatutils.position.Vector2D;

public class PositionOutOfBoundsException extends Exception {

    public PositionOutOfBoundsException(Vector2D position) {
        super("Position out of bounds at: [" + position.x + "," + position.y + "]");
    }
}
