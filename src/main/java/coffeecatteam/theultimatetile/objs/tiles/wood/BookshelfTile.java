package coffeecatteam.theultimatetile.objs.tiles.wood;

import coffeecatteam.theultimatetile.objs.items.ItemStack;
import coffeecatteam.theultimatetile.objs.items.Items;
import coffeecatteam.theultimatetile.start.TutEngine;

/**
 * @author CoffeeCatRailway
 * Created: 21/02/2019
 */
public class BookshelfTile extends WoodTile {

    public BookshelfTile(TutEngine tutEngine) {
        super(tutEngine, "bookshelf");
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        tutEngine.getPlayer().getInventoryPlayer().addItem(new ItemStack(Items.BOOK.newCopy()));
    }

    @Override
    public WoodTile newCopy() {
        return super.newCopy(new BookshelfTile(tutEngine));
    }
}
