package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import io.github.vampirestudios.tdg.manager.UIManager;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.TilePos;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.world.TileList;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import java.util.Random;

public abstract class Screen {

    protected CatLogger logger;

    protected UIManager uiManager;
    protected MaewilEngine maewilEngine;

    private int bgWidth = MaewilLauncher.WIDTH_TILE_SIZE + (MaewilLauncher.WIDTH_TILE_SIZE * Tile.TILE_SIZE < MaewilLauncher.WIDTH ? 1 : 0);
    private int bgHeight = MaewilLauncher.HEIGHT_TILE_SIZE + (MaewilLauncher.HEIGHT_TILE_SIZE * Tile.TILE_SIZE < MaewilLauncher.HEIGHT ? 1 : 0);
    private TileList bgTiles;
    private Tile[] centre, border;

    public Screen(MaewilEngine maewilEngine) {
        this(maewilEngine, TileBackgrounds.GRASS.getTiles());
    }

    public Screen(MaewilEngine maewilEngine, Tile[] centre) {
        this(maewilEngine, centre, TileBackgrounds.STONE_BORDER.getTiles());
    }

    public Screen(MaewilEngine maewilEngine, Tile[] centre, Tile[] border) {
        this.maewilEngine = maewilEngine;
        this.logger = MaewilLauncher.LOGGER;
        this.uiManager = new UIManager();

        this.centre = centre;
        this.border = border;

        this.bgTiles = new TileList(this.bgWidth, this.bgHeight);
    }

    public void init() {
        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                Tile tile = centre[new Random().nextInt(centre.length)];
                if (x == 0 || y == 0 || x >= this.bgWidth - 1 || y >= this.bgHeight - 1)
                    tile = border[new Random().nextInt(border.length)];

                this.bgTiles.setTileAtPos(x, y, tile.newCopy().pos(new TilePos(x, y)));
            }
        }

        for (int y = 0; y < this.bgHeight; y++)
            for (int x = 0; x < this.bgWidth; x++)
                this.bgTiles.getTileAtPos(x, y).setWorldLayer(bgTiles);
    }

    public abstract void update(org.mini2Dx.core.game.GameContainer container, float delta);

    public abstract void render(org.mini2Dx.core.game.GameContainer container, org.mini2Dx.core.graphics.Graphics g);

    public void postRender(org.mini2Dx.core.game.GameContainer container, org.mini2Dx.core.graphics.Graphics g) {
        uiManager.postRender(container, g);
    }

    protected void renderBG(GameContainer container, Graphics g) {
        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                this.bgTiles.getTileAtPos(x, y).forcedUpdate(null, 0);
                this.bgTiles.getTileAtPos(x, y).render(container, g);
            }
        }
    }
}
