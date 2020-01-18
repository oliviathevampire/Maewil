package io.github.vampirestudios.tdg.objs.tiles.wood;

import io.github.vampirestudios.tdg.objs.items.ItemStack;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.start.MaewilEngine;

/**
 * @author CoffeeCatRailway
 * Created: 21/02/2019
 */
public class BookshelfTile extends WoodTile {

    public BookshelfTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "bookshelf");
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        maewilEngine.getPlayer().getInventoryPlayer().addItem(new ItemStack(Items.BOOK.newCopy()));
    }

    @Override
    public WoodTile newCopy() {
        return super.newCopy(new BookshelfTile(maewilEngine));
    }
}
