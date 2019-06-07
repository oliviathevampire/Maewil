package coffeecatteam.theultimatetile.objs.tiles.stone;

import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.world.colormap.WorldColors;

/**
 * @author CoffeeCatRailway
 * Created: 3/02/2019
 */
public class ObsidianTile extends StoneTile {

    public ObsidianTile(TutEngine tutEngine) {
        super(tutEngine, "obsidian", true);
        this.setMapColor(WorldColors.STONE);
        this.setDrop(null);
    }

    @Override
    public ObsidianTile newCopy() {
        return super.newCopy(new ObsidianTile(tutEngine));
    }
}
