package io.github.vampirestudios.tdg.screen;

import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;

public enum TileBackgrounds {

    GRASS(Tiles.GRASS),
    STONE_BORDER(Tiles.STONE, Tiles.ANDESITE, Tiles.DIORITE);

    private Tile[] tiles;

    TileBackgrounds(Tile... tiles) {
        this.tiles = tiles;
    }

    public Tile[] getTiles() {
        return tiles;
    }

}
