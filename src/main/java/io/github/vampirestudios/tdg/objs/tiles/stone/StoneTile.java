package io.github.vampirestudios.tdg.objs.tiles.stone;

import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.objs.items.Items;
import io.github.vampirestudios.tdg.objs.tiles.OverlapTile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 24/12/2018
 */
public class StoneTile extends OverlapTile {

    public StoneTile(TutEngine tutEngine) {
        this(tutEngine, "stone", true);
    }

    public StoneTile(TutEngine tutEngine, String id, boolean overlap) {
        super(tutEngine, Assets.BROKEN_STONE, id, true, TileType.STONE, Assets.BROKEN_STONE_ALTS);
        this.setMapColor(WorldColors.STONE);

        if (overlap) {
            this.setConnect("broken_stone");
            this.setIgnore("stone");
        }

        this.setDrop(Items.ROCK);
    }

    @Override
    public StoneTile newCopy() {
        return super.newCopy(new StoneTile(tutEngine));
    }
}
