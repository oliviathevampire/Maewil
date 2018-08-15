package coffeecatteam.theultimatetile.worlds;

import coffeecatteam.theultimatetile.Handler;
import coffeecatteam.theultimatetile.entities.EntityLoader;
import coffeecatteam.theultimatetile.entities.EntityManager;
import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;
import coffeecatteam.theultimatetile.gfx.overlays.OverlayManager;
import coffeecatteam.theultimatetile.items.ItemManager;
import coffeecatteam.theultimatetile.tiles.Tile;
import coffeecatteam.theultimatetile.tiles.Tiles;
import coffeecatteam.theultimatetile.utils.Utils;

import java.awt.*;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;

    private int[][] bg_tiles;
    private int[][] tiles;

    // Managers
    private EntityManager entityManager;
    private ItemManager itemManager;
    private OverlayManager overlayManager;

    public World(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new EntityPlayer(handler, "player"));
        itemManager = new ItemManager(handler);
        overlayManager = new OverlayManager(handler, entityManager.getPlayer());

        loadWorld(path);
        entityManager.getPlayer().setX(spawnX * Tile.TILE_WIDTH);
        entityManager.getPlayer().setY(spawnY * Tile.TILE_HEIGHT);
    }

    public void tick() {
        int xStart = (int) Math.max(0, handler.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                // getTile(bg_tiles, x, y).tick(); <-- Not 100% sure if this should be here -CoffeeCat
                getTile(tiles, x, y).tick();
            }
        }

        itemManager.tick();
        entityManager.tick();
        overlayManager.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, handler.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(bg_tiles, x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - handler.getCamera().getyOffset()), new Color(63, 63, 63, 127));
                getTile(tiles, x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - handler.getCamera().getyOffset()));
            }
        }

        itemManager.render(g);
        entityManager.render(g);
        overlayManager.render(g);
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x >= width)
            x = width;
        if ( y >= height)
            y = height;

        tiles[x][y] = tile.getId();
    }

    public Tile getTile(int x, int y) {
        return getTile(tiles, x, y);
    }

    public Tile getTile(int[][] grid, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tiles.DIRT;

        Tile t = Tile.tiles[grid[x][y]];
        if (t == null)
            return Tiles.DIRT;
        t.setPos(x, y);
        return t;
    }

    private void loadWorld(String path) {
        String prop = Utils.loadFileAsString(path + "/world.prop");
        String bg = Utils.loadFileAsString(path + "/id_bg.wd");
        String tile = Utils.loadFileAsString(path + "/id_tile.wd");
        String entity = Utils.loadFileAsString(path + "/id_entity.wd");

        String spacer = "\\s+";
        String[] propTokens = prop.split(spacer);
        String[] bgTokens = bg.split(spacer);
        String[] tileTokens = tile.split(spacer);
        String[] entityTokens = entity.split(spacer);

        String worldName = propTokens[0].replace("_", " ");
        System.out.println("Loading World: " + worldName);

        width = Utils.parseInt(propTokens[1]);
        height = Utils.parseInt(propTokens[2]);
        spawnX = Utils.parseInt(propTokens[3]);
        spawnY = Utils.parseInt(propTokens[4]);

        loadBG(bgTokens, worldName);
        loadTiles(tileTokens, worldName);
        if (!entity.equals(""))
            loadEntities(entityTokens, worldName);
        System.out.println("World Loaded!\n");
    }

    private void loadBG(String[] bgTokens, String worldName) {
        worldName = "World: " + worldName + "\\id_bg.wd";
        System.out.println(worldName);

        bg_tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bg_tiles[x][y] = Utils.parseInt(bgTokens[(x + y * width)]);
            }
            float loaded = ((y + 1) * 100.0f) / height;
            if (y % 2 == 0)
                System.out.println(loaded + "% Loaded!");
        }
    }

    private void loadTiles(String[] tileTokens, String worldName) {
        worldName = "World: " + worldName + "\\id_tile.wd";
        System.out.println(worldName);

        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tileTokens[(x + y * width)]);
            }
            float loaded = ((y + 1) * 100.0f) / height;
            if (y % 2 == 0)
                System.out.println(loaded + "% Loaded!");
        }
    }

    private void loadEntities(String[] entityTokens, String worldName) {
        worldName = "World: " + worldName + "\\id_entity.wd";
        System.out.println(worldName);
        int height = entityTokens.length;
        String spliter = ",";

        for (int i = 0; i < height; i++) {
            String entityId = entityTokens[i].split(spliter)[0];
            float x = Utils.parseFloat(entityTokens[i].split(spliter)[1]);
            float y = Utils.parseFloat(entityTokens[i].split(spliter)[2]);

            entityManager.addEntity(EntityLoader.loadEntity(handler, entityId), x, y);

            float loaded = ((i + 1) * 100.0f) / height;
            if (i % 2 == 0)
                System.out.println(loaded + "% Loaded!");
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }
}
