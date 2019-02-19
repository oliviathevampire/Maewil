package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.tile.Tile;
import coffeecatteam.theultimatetile.tile.TilePos;
import coffeecatteam.theultimatetile.tile.Tiles;
import coffeecatteam.theultimatetile.manager.UIManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.Random;

public abstract class State {

    protected CatLogger logger;

    protected UIManager uiManager;
    protected TutEngine tutEngine;

    private int bgWidth, bgHeight;
    private Tile[][] bgTiles;
    protected static final Tile[] CENTRE_GRASS = new Tile[]{Tiles.GRASS}, BORDER_STONE_BROKEN = new Tile[]{Tiles.STONE, Tiles.BROKEN_STONE};

    public State(TutEngine tutEngine) {
        this(tutEngine, CENTRE_GRASS);
    }

    public State(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, BORDER_STONE_BROKEN);
    }

    public State(TutEngine tutEngine, Tile[] centre, Tile[] border) {
        this.tutEngine = tutEngine;
        this.logger = tutEngine.getLogger();
        this.uiManager = new UIManager(tutEngine);

        this.bgWidth = tutEngine.getWidth() / Tile.TILE_WIDTH + 1;
        this.bgHeight = tutEngine.getHeight() / Tile.TILE_HEIGHT + 1;
        this.bgTiles = new Tile[this.bgWidth][this.bgHeight];

        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                Tile tile = centre[new Random().nextInt(centre.length)];
                if (x == 0 || y == 0 || x >= this.bgWidth - 1 || y >= this.bgHeight - 1)
                    tile = border[new Random().nextInt(border.length)];

//                tile = Tiles.getTile(tile.getId()).setPos(new TilePos(x, y));
                    this.bgTiles[x][y] = tile.newTile().setPos(new TilePos(x, y));
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
