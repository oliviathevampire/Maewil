package coffeecatteam.theultimatetile.tile.tiles;

import coffeecatteam.theultimatetile.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class TileDiorite extends TileStone {

    public TileDiorite(TutEngine tutEngine) {
        super(tutEngine, "diorite", true);
    }

    @Override
    public TileDiorite newTile() {
        return super.newTile(new TileDiorite(tutEngine));
    }
}
