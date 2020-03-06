package io.github.vampirestudios.tdg.objs.tiles;

import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.world.colormap.WorldColors;

public class GrassTile extends Tile {

    public GrassTile(MaewilEngine maewilEngine) {
        super(maewilEngine, "grass", false, Tile.TileType.GROUND);
//        super(maewilEngine, Assets.GRASS, "grass", false, Tile.TileType.GROUND, Assets.DARK_GRASS_ALTS);
        this.setMapColor(WorldColors.GRASS);
//        this.setConnect("dark_grass");
//        this.setConnect("dirt", "sand");
//        this.setIgnore("grass");
    }

    @Override
    public GrassTile newCopy() {
        return super.newCopy(new GrassTile(maewilEngine));
    }
}
