package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.items.Items;

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
        tutEngine.getPlayer().getInventoryPlayer().addItem(new ItemStack(Items.BOOK.newCopy()));
    }

    @Override
    public TileWood newCopy() {
        return super.newCopy(new TileBookshelf(tutEngine));
    }
}
