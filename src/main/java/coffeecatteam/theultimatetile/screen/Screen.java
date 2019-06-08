package coffeecatteam.theultimatetile.screen;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.manager.UIManager;
import coffeecatteam.theultimatetile.objs.tiles.Tile;
import coffeecatteam.theultimatetile.objs.tiles.TilePos;
import coffeecatteam.theultimatetile.objs.tiles.Tiles;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.world.TileList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public abstract class Screen {

    protected CatLogger logger;

    protected UIManager uiManager;
    protected TutEngine tutEngine;

    private int bgWidth = TutLauncher.WIDTH_TILE_SIZE + (TutLauncher.WIDTH_TILE_SIZE * Tile.TILE_SIZE < TutLauncher.WIDTH ? 1 : 0);
    private int bgHeight = TutLauncher.HEIGHT_TILE_SIZE + (TutLauncher.HEIGHT_TILE_SIZE * Tile.TILE_SIZE < TutLauncher.HEIGHT ? 1 : 0);
    private TileList bgTiles;
    private Tile[] centre, border;
    protected static final Tile[] CENTRE_GRASS = new Tile[]{Tiles.GRASS}, BORDER_STONE_BROKEN = new Tile[]{Tiles.STONE, Tiles.BROKEN_STONE};

    public Screen(TutEngine tutEngine) {
        this(tutEngine, CENTRE_GRASS);
    }

    public Screen(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, BORDER_STONE_BROKEN);
    }

    public Screen(TutEngine tutEngine, Tile[] centre, Tile[] border) {
        this.tutEngine = tutEngine;
        this.logger = TutLauncher.LOGGER;
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

    public abstract void update(GameContainer container, StateBasedGame game, int delta);

    public abstract void render(GameContainer container, StateBasedGame game, Graphics g);

    public void postRender(GameContainer container, StateBasedGame game, Graphics g) {
        uiManager.postRender(container, game, g);
    }

    protected void renderBG(GameContainer container, StateBasedGame game, Graphics g) {
        for (int y = 0; y < this.bgHeight; y++) {
            for (int x = 0; x < this.bgWidth; x++) {
                this.bgTiles.getTileAtPos(x, y).forcedUpdate(null, 0);
                this.bgTiles.getTileAtPos(x, y).render(container, game, g);
            }
        }
    }
}
