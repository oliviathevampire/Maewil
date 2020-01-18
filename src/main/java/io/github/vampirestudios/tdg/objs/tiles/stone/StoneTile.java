package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.OverlapTile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class StoneTile extends OverlapTile {

    public StoneTile(MaewilEngine maewilEngine) {
        this(maewilEngine, "stone", true);
    }

    public StoneTile(MaewilEngine maewilEngine, String id, boolean overlap) {
        super(maewilEngine, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);
        this.setMapColor(WorldColors.STONE);

        if (overlap) {
            this.setConnect("broken_stone");
            this.setIgnore("stone");
        }

        this.setDrop(Items.ROCK);
    }

    @Override
    public StoneTile newCopy() {
        return super.newCopy(new StoneTile(maewilEngine));
    }
}
