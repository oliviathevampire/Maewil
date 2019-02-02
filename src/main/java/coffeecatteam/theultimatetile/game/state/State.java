package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.game.tile.Tiles;
import coffeecatteam.theultimatetile.manager.UIManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class State {

    protected CatLogger logger;

    private static State currentState = null;

    protected UIManager uiManager;
    protected static Engine engine;

    private List<Tile> tiles;
    protected static final Tile[] DEFAULT_CENTRE = new Tile[]{Tiles.GRASS}, DEFAULT_BORDER = new Tile[]{Tiles.STONE, Tiles.BROKEN_STONE};

    public State(Engine engine) {
        this(engine, DEFAULT_CENTRE, DEFAULT_BORDER);
    }

    public State(Engine engine, Tile[] centre) {
        this(engine, centre, DEFAULT_BORDER);
    }

    public State(Engine engine, Tile[] centre, Tile[] border) {
        State.engine = engine;
        this.logger = engine.getLogger();
        uiManager = new UIManager(engine);

        int width = engine.getWidth() / Tile.TILE_WIDTH + 1;
        int height = engine.getHeight() / Tile.TILE_HEIGHT + 1;
        tiles = new ArrayList<>(width * height);
        Tile[][] wl = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = centre[new Random().nextInt(centre.length)];
                if (x == 0 || y == 0 || x >= width - 1 || y >= height - 1)
                    tile = border[new Random().nextInt(border.length)];
                tile = tile.newTile().setPos(new TilePos(x, y));
                tiles.add(tile);
                wl[x][y] = tile;
            }
        }

        tiles.forEach(t -> t.setWorldLayer(wl));
    }

    public void init() {
    }

    public abstract void update(GameContainer container, int delta);

    public abstract void render(Graphics g);

    protected void renderBG(Graphics g) {
        tiles.forEach(t -> {
            t.forcedUpdate(null, 0);
            t.render(g);
        });
    }

    public static void setState(State state) {
        currentState = state;
        currentState.init();
    }

    public static State getState() {
        return currentState;
    }
}
