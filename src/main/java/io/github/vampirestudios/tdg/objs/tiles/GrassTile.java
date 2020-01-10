package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 22/12/2018
 */
public class GrassTile extends Tile {

    public GrassTile(TutEngine tutEngine) {
        super(tutEngine, "grass", false, Tile.TileType.GROUND);
        this.setMapColor(WorldColors.GRASS);
    }

    @Override
    public GrassTile newCopy() {
        return super.newCopy(new GrassTile(tutEngine));
    }
}
