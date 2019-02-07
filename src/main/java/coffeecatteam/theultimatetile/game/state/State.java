package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.manager.UIManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.Random;

public abstract class State {

    protected CatLogger logger;

    protected UIManager uiManager;
    protected Engine engine;

    private int bgWidth, bgHeight;
    private Tile[][] bgTiles;
    protected static final Tile[] CENTRE_GRASS = new Tile[]{Tiles.GRASS}, BORDER_STONE_BROKEN = new Tile[]{Tiles.STONE, Tiles.BROKEN_STONE};

    public State(Engine engine) {
        this(engine, CENTRE_GRASS);
    }

    public State(Engine engine, Tile[] centre) {
        this(engine, centre, BORDER_STONE_BROKEN);
    }

    public State(Engine engine, Tile[] centre, Tile[] border) {
        this.engine = engine;
        this.logger = engine.getLogger();
        this.uiManager = new UIManager(engine);

        this.bgWidth = engine.getWidth() / Tile.TILE_WIDTH + 1;
        this.bgHeight = engine.getHeight() / Tile.TILE_HEIGHT + 1;
        this.bgTiles = new Tile[this.bgWidth][this.bgHeight];

        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                Tile tile = centre[new Random().nextInt(centre.length)];
                if (x == 0 || y == 0 || x >= this.bgWidth - 1 || y >= this.bgHeight - 1)
                    tile = border[new Random().nextInt(border.length)];

                tile = tile.newTile().setPos(new TilePos(x, y));
                this.bgTiles[x][y] = tile;
            }
        }

        for (int y = 0; y < this.bgHeight; y++)
            for (int x = 0; x < this.bgWidth; x++)
                this.bgTiles[x][y].setWorldLayer(this.bgTiles);
    }

    public void init() {
    }

    public abstract void update(GameContainer container, int delta);

    public abstract void render(Graphics g);

    public void postRender(Graphics g) {
        uiManager.postRender(g);
    }

    protected void renderBG(Graphics g) {
        for (int y = 0; y < bgHeight; y++) {
            for (int x = 0; x < bgWidth; x++) {
                bgTiles[x][y].forcedUpdate(null, 0);
                bgTiles[x][y].render(g);
            }
        }
    }
}
