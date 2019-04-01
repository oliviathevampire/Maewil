package coffeecatteam.theultimatetile.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.world.TileList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public abstract class State {

    protected CatLogger logger;

    protected UIManager uiManager;
    protected TutEngine tutEngine;

    private int bgWidth, bgHeight;
    private TileList bgTiles;
    private Tile[] centre, border;
    protected static final Tile[] CENTRE_GRASS = new Tile[]{Tiles.GRASS}, BORDER_STONE_BROKEN = new Tile[]{Tiles.STONE, Tiles.BROKEN_STONE};

    public State(TutEngine tutEngine) {
        this(tutEngine, CENTRE_GRASS);
    }

    public State(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, BORDER_STONE_BROKEN);
    }

    public State(TutEngine tutEngine, Tile[] centre, Tile[] border) {
        this.tutEngine = tutEngine;
        this.logger = TutLauncher.LOGGER;
        this.uiManager = new UIManager(tutEngine);

        this.centre = centre;
        this.border = border;

        this.bgWidth = TutLauncher.WIDTH / Tile.TILE_SIZE;
        this.bgHeight = TutLauncher.HEIGHT / Tile.TILE_SIZE;

        if (this.bgWidth * Tile.TILE_SIZE < TutLauncher.WIDTH) this.bgWidth += 1;
        if (this.bgHeight * Tile.TILE_SIZE < TutLauncher.HEIGHT) this.bgHeight += 1;

        this.bgTiles = new TileList(this.bgWidth, this.bgHeight);
    }

    public void init() {
        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                Tile tile = centre[new Random().nextInt(centre.length)];
                if (x == 0 || y == 0 || x >= this.bgWidth - 1 || y >= this.bgHeight - 1)
                    tile = border[new Random().nextInt(border.length)];

                this.bgTiles.setTile(x, y, tile.newCopy().setPos(new TilePos(x, y)));
            }
        }

        for (int y = 0; y < this.bgHeight; y++)
            for (int x = 0; x < this.bgWidth; x++)
                this.bgTiles.getTile(x, y).setWorldLayer(bgTiles);
    }

    public abstract void update(GameContainer container, StateBasedGame game, int delta);

    public abstract void render(Graphics g);

    public void postRender(Graphics g) {
        uiManager.postRender(g);
    }

    protected void renderBG(Graphics g) {
        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                this.bgTiles.getTile(x, y).forcedUpdate(null, 0);
                this.bgTiles.getTile(x, y).render(g);
            }
        }
    }
}
