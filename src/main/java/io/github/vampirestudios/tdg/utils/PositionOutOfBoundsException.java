package io.github.vampirestudios.tdg.utils;

import coffeecatteam.coffeecatutils.position.Vector2D;

/**
 * @author CoffeeCatRailway
 * Created: 23/12/2018
 */
public class PositionOutOfBoundsException extends Exception {

    public PositionOutOfBoundsException(Vector2D position) {
        super("Position out of bounds at: [" + position.x + "," + position.y + "]");
    }
}
