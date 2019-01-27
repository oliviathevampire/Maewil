package coffeecatteam.theultimatetile.game.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.logger.CatLogger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.game.tile.Tile;
import coffeecatteam.theultimatetile.game.tile.TilePos;
import coffeecatteam.theultimatetile.game.world.colormap.WorldMapGenerator;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.manager.OverlayManager;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.BufferedImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    private Engine engine;
    private String worldName;
    private int width, height;
    private float spawnX;
    private float spawnY;

    private Tile[][] bg_tiles;
    private Tile[][] fg_tiles;

    private OverlayManager overlayManager;

    private Image mapCursor = Assets.MAP_CURSOR[0];
    private Color halfTransparent = new Color(1.0f, 1.0f, 1.0f, 0.5f);

    private boolean isForcedUpdate = false;
    private Thread forcedUpdateThread;

    public World(Engine engine, String path, String worldName) {
        this.engine = engine;
        this.worldName = worldName;
        overlayManager = new OverlayManager(engine, ((GameEngine) engine).getEntityManager().getPlayer());

        try {
            loadWorld(path);
        } catch (IOException | ParseException e) {
            engine.getLogger().print(e);
        }
        ((GameEngine) engine).getEntityManager().getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        ((GameEngine) engine).getEntityManager().getPlayer().setY(spawnY * Tile.TILE_HEIGHT);
    }

    public World(Engine engine, String worldName, int width, int height, Vector2D spawn, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        this(engine, worldName, width, height, (int) spawn.x, (int) spawn.y, bg_tiles, fg_tiles);
    }

    public World(Engine engine, String worldName, int width, int height, int spawnX, int spawnY, Tile[][] bg_tiles, Tile[][] fg_tiles) {
        this.engine = engine;
        this.worldName = worldName;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.bg_tiles = bg_tiles;
        this.fg_tiles = fg_tiles;

        overlayManager = new OverlayManager(engine, ((GameEngine) engine).getEntityManager().getPlayer());
    }

    private void forcedUpdateInit(GameContainer container, int delta) {
        if (!isForcedUpdate) {
            isForcedUpdate = true;

            forcedUpdateThread = new Thread(() -> {
                CatLogger logger = new CatLogger();
                logger.print("Forced update initialized");

                while (isForcedUpdate) for (int y = 0; y < height; y++)
                    for (int x = 0; x < width; x++) {
                        getBGTile(x, y).forcedUpdate(container, delta);
                        getFGTile(x, y).forcedUpdate(container, delta);
                    }

                logger.print(new Exception("Something is wrong with the world!"));
            }, "WorldForcedUpdate-Thread");

            forcedUpdateThread.start();
        }
    }

    public void update(GameContainer container, int delta) {
        int xStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (((GameEngine) engine).getCamera().getxOffset() + engine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (((GameEngine) engine).getCamera().getyOffset() + engine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBGTile(x, y).updateBounds();
                getBGTile(x, y).setWorldLayer(bg_tiles);

                getFGTile(x, y).update(container, delta);
                getFGTile(x, y).setWorldLayer(fg_tiles);
            }
        }

        forcedUpdateInit(container, delta);

        ((GameEngine) engine).getItemManager().update(container, delta);
        ((GameEngine) engine).getEntityManager().update(container, delta);
        overlayManager.update(container, delta);
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (((GameEngine) engine).getCamera().getxOffset() + engine.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, ((GameEngine) engine).getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (((GameEngine) engine).getCamera().getyOffset() + engine.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getBGTile(x, y).render(g);


        g.setColor(new Color(63, 63, 63, 127));
        g.fillRect(0, 0, engine.getWidth(), engine.getHeight());

        for (int y = yStart; y < yEnd; y++)
            for (int x = xStart; x < xEnd; x++)
                getFGTile(x, y).render(g);

        ((GameEngine) engine).getItemManager().render(g);
        ((GameEngine) engine).getEntityManager().render(g);
        overlayManager.render(g);

        /*
         * Mini Map
         */
        float padding = 10;
        float x = padding, y = padding, mapSize = 180;
        g.setColor(Color.white);

        int viewSize = 100;
        BufferedImage map = new BufferedImage(viewSize, viewSize, BufferedImage.TYPE_INT_ARGB);
        int pwx = (int) (((GameEngine) engine).getPlayer().getPosition().x / Tile.TILE_WIDTH);
        int pwy = (int) (((GameEngine) engine).getPlayer().getPosition().y / Tile.TILE_HEIGHT);

        /*
         * Get world viewing coords
         */
        int mxStart = pwx - viewSize / 2;
        int mxEnd = pwx + viewSize / 2;
        int myStart = pwy - viewSize / 2;
        int myEnd = pwy + viewSize / 2;

        if (mxStart < 0) {
            mxStart = 0;
            mxEnd = viewSize;
        }
        if (mxEnd > width - 1) {
            mxStart = width - 1 - viewSize;
            mxEnd = width - 1;
        }
        if (myStart < 0) {
            myStart = 0;
            myEnd = viewSize;
        }
        if (myEnd > height - 1) {
            myStart = height - 1 - viewSize;
            myEnd = height - 1;
        }

        /*
         * Generate map image
         */
        for (int my = myStart; my < myEnd; my++) {
            for (int mx = mxStart; mx < mxEnd; mx++) {
                int c = WorldMapGenerator.getRGBA(getFGTile(mx, my).getMapColor().getRGB());
                int pixelX = (int) NumberUtils.map(mx, mxStart, mxEnd, 0, viewSize - 1);
                int pixelY = (int) NumberUtils.map(my, myStart, myEnd, 0, viewSize - 1);
                map.setRGB(pixelX, pixelY, c);
            }
        }

        int mapSizeOff = 14;
        float mapBorderSize = mapSize + padding;
        EntityPlayer player = GameEngine.getGameEngine().getPlayer();
        Color trans = ((player.getPosition().x + player.getWidth() / 2f < mapBorderSize && player.getPosition().y + player.getHeight() / 2f < mapBorderSize) ? halfTransparent : Color.white);
        try {
            Image mapDraw = new Image(BufferedImageUtil.getTexture("", map));
            mapDraw.setFilter(Image.FILTER_NEAREST);
            mapDraw.draw(x + mapSizeOff / 2f, y + mapSizeOff / 2f, mapSize - mapSizeOff + 2, mapSize - mapSizeOff + 2, trans);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Convert player world coords to map coords
         */
        float cursorSize = 20f;
        float pxd = x + mapSize / 2f - cursorSize / 2f;
        float pyd = y + mapSize / 2f - cursorSize / 2f;

        float px = pxd;
        float py = pyd;

        if (mxStart <= 0)
            px = NumberUtils.map((float) ((GameEngine) engine).getPlayer().getPosition().x, 0, (viewSize + cursorSize) * Tile.TILE_WIDTH, x, x + mapSize);
        if (mxEnd >= width - 1)
            px = NumberUtils.map((float) ((GameEngine) engine).getPlayer().getPosition().x, (width - (viewSize - cursorSize / 2)) * Tile.TILE_WIDTH, width * Tile.TILE_WIDTH, x, x + mapSize - cursorSize / 2);
        if (myStart <= 0)
            py = NumberUtils.map((float) ((GameEngine) engine).getPlayer().getPosition().y, 0, (viewSize + cursorSize) * Tile.TILE_HEIGHT, y, y + mapSize);
        if (myEnd >= height - 1)
            py = NumberUtils.map((float) ((GameEngine) engine).getPlayer().getPosition().y, (height - (viewSize - cursorSize / 2)) * Tile.TILE_HEIGHT, height * Tile.TILE_HEIGHT, y, y + mapSize - cursorSize / 2);

        /*
         * Draw cursor (arrow) and border
         */
        updateMapCursor();
        mapCursor.draw(px, py, cursorSize, cursorSize);

        Assets.MAP_BORDER.draw(x - padding / 2, y - padding / 2, mapBorderSize, mapBorderSize, trans);
    }

    private void updateMapCursor() {
        if (engine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[0];
        if (engine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[1];
        if (engine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[2];
        if (engine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[3];

        if (engine.getKeyManager().moveUp && engine.getKeyManager().moveRight)
            mapCursor = Assets.MAP_CURSOR[4];
        if (engine.getKeyManager().moveRight && engine.getKeyManager().moveDown)
            mapCursor = Assets.MAP_CURSOR[5];
        if (engine.getKeyManager().moveDown && engine.getKeyManager().moveLeft)
            mapCursor = Assets.MAP_CURSOR[6];
        if (engine.getKeyManager().moveLeft && engine.getKeyManager().moveUp)
            mapCursor = Assets.MAP_CURSOR[7];
    }

    public Tile getBGTile(int x, int y) {
        return getBGTile(new TilePos(x, y));
    }

    public Tile getBGTile(TilePos pos) {
        checkTilePos(pos, false);

        return bg_tiles[pos.getX()][pos.getY()];
    }

    public void setBGTile(int x, int y, Tile tile) {
        setBGTile(new TilePos(x, y), tile);
    }

    public void setBGTile(TilePos pos, Tile tile) {
        checkTilePos(pos, true);

        bg_tiles[pos.getX()][pos.getY()] = tile.setPos(pos);
    }

    public Tile getFGTile(int x, int y) {
        return getFGTile(new TilePos(x, y));
    }

    public Tile getFGTile(TilePos pos) {
        checkTilePos(pos, false);

        return fg_tiles[pos.getX()][pos.getY()];
    }

    public void setFGTile(int x, int y, Tile tile) {
        setFGTile(new TilePos(x, y), tile);
    }

    public void setFGTile(TilePos pos, Tile tile) {
        checkTilePos(pos, true);

        fg_tiles[pos.getX()][pos.getY()] = tile.setPos(pos);
    }

    private void checkTilePos(TilePos pos, boolean wh_exact) {
        if (pos.getX() < 0)
            pos.setX(0);
        if (pos.getY() < 0)
            pos.setY(0);

        if (wh_exact) {
            if (pos.getX() >= width)
                pos.setX(width);
            if (pos.getY() >= height)
                pos.setY(height);
        } else {
            if (pos.getX() > width)
                pos.setX(width);
            if (pos.getY() > height)
                pos.setY(height);
        }
    }

    private void loadWorld(String path) throws IOException, ParseException {
        WorldJsonLoader worldJsonLoader = new WorldJsonLoader(path, engine); // "/assets/worlds/dev_tests/json_format"

        worldJsonLoader.load();
        engine.getLogger().print("Loading world [" + worldJsonLoader.getName() + "]!");

        worldName = worldJsonLoader.getName();

        width = worldJsonLoader.getWidth();
        height = worldJsonLoader.getHeight();
        spawnX = worldJsonLoader.getSpawnX();
        spawnY = worldJsonLoader.getSpawnY();

        Tile[][] bg_tile_ids = worldJsonLoader.getBg_tiles().clone();
        Tile[][] fg_tile_ids = worldJsonLoader.getFg_tiles().clone();

        bg_tiles = new Tile[width][height];
        fg_tiles = new Tile[width][height];

        GameEngine.getGameEngine().setUsername(worldJsonLoader.getUsername());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = bg_tile_ids[x][y].setPos(new TilePos(x, y)).setSolid(false);
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                bg_tiles[x][y] = tile;
            }
        }
        engine.getLogger().print("Loaded background tiles!");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = fg_tile_ids[x][y].setPos(new TilePos(x, y));
                if (x <= 0 || y <= 0 || x >= width - 1 || y >= height - 1) {
                    tile.setSolid(true);
                    tile.setUnbreakable(true);
                }
                fg_tiles[x][y] = tile;
            }
        }
        engine.getLogger().print("Loaded foreground tiles!");
    }

    public String getWorldName() {
        return worldName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public Tile[][] getBg_tiles() {
        return bg_tiles;
    }

    public Tile[][] getFg_tiles() {
        return fg_tiles;
    }
}
