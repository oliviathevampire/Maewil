package coffeecatteam.tilegame.tiles;

import coffeecatteam.tilegame.gfx.Assets;

public class TileRock extends Tile {

    public TileRock(int id) {
        super(Assets.STONE, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
