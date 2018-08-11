package coffeecatteam.tilegame.worlds;

import coffeecatteam.tilegame.Handler;
import coffeecatteam.tilegame.entities.EntityManager;
import coffeecatteam.tilegame.entities.creatures.EntityPlayer;
import coffeecatteam.tilegame.entities.creatures.EntityZombie;
import coffeecatteam.tilegame.entities.statics.EntityRock;
import coffeecatteam.tilegame.entities.statics.EntityTree;
import coffeecatteam.tilegame.entities.statics.EntityUltimateTile;
import coffeecatteam.tilegame.items.ItemManager;
import coffeecatteam.tilegame.tiles.Tile;
import coffeecatteam.tilegame.tiles.Tiles;
import coffeecatteam.tilegame.utils.Utils;

import java.awt.*;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;

    private EntityManager entityManager;
    private ItemManager itemManager;

    public World(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new EntityPlayer(handler, 0, 0));
        itemManager = new ItemManager(handler);

        entityManager.addEntity(new EntityTree(handler, 10 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT, EntityTree.TreeType.SMALL));
        entityManager.addEntity(new EntityTree(handler, 11 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT, EntityTree.TreeType.MEDIUM));
        entityManager.addEntity(new EntityRock(handler, 12.5f * Tile.TILE_WIDTH, 11.5f * Tile.TILE_HEIGHT));
        entityManager.addEntity(new EntityTree(handler, 13 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT, EntityTree.TreeType.LARGE));

        entityManager.addEntity(new EntityZombie(handler, 1 * Tile.TILE_WIDTH, 15 * Tile.TILE_HEIGHT));
        entityManager.addEntity(new EntityZombie(handler, 1 * Tile.TILE_WIDTH, 16 * Tile.TILE_HEIGHT));
        entityManager.addEntity(new EntityZombie(handler, 1 * Tile.TILE_WIDTH, 17 * Tile.TILE_HEIGHT));

        entityManager.addEntity(new EntityUltimateTile(handler, 2 * Tile.TILE_WIDTH, 1 * Tile.TILE_HEIGHT));

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
                getTile(x, y).tick();
            }
        }

        itemManager.tick();
        entityManager.tick();
    }

    public void render(Graphics g) {
        int xStart = (int) Math.max(0, handler.getCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getCamera().getxOffset()), (int) (y * Tile.TILE_HEIGHT - handler.getCamera().getyOffset()));
            }
        }

        itemManager.render(g);
        entityManager.render(g);
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height)
            return Tiles.DIRT;

        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null)
            return Tiles.DIRT;
        return t;
    }

    private void loadWorld(String path) {
    	System.out.println("Loading World: " + path);
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
            }
            float loaded = ((y + 1) * 100.0f) / height;
            if (y % 2 == 0)
                System.out.println(loaded + "% Loaded!");
        }
        System.out.println("World Loaded!\n");
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
