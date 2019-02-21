package coffeecatteam.theultimatetile.objs.tiles;

import coffeecatteam.theultimatetile.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 21/02/2019
 */
public class TileBookshelf extends TileWood {

    public TileBookshelf(TutEngine tutEngine) {
        super(tutEngine, "bookshelf");
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);


    }

    @Override
    public TileWood newTile() {
        return super.newTile(new TileBookshelf(tutEngine));
    }
}
