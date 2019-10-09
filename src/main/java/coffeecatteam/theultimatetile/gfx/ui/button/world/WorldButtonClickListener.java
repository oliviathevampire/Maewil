package coffeecatteam.theultimatetile.gfx.ui.button.world;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.objs.entities.Entity;
import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;
import coffeecatteam.theultimatetile.objs.tiles.*;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.screen.ScreenManager;
import coffeecatteam.theultimatetile.screen.game.GameScreen;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.utils.UtilsIdk;
import coffeecatteam.theultimatetile.world.TileList;
import coffeecatteam.theultimatetile.world.World;
import coffeecatteam.theultimatetile.world.WorldGenerator;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author CoffeeCatRailway
 * Created: 10/01/2019
 */
public class WorldButtonClickListener implements ClickListener {

    private String path, savesPath;
    private int index;
    private boolean isSaved = false;
    private TutEngine engine;

    private long seed;
    private float sizeMod;
    private int worldSize;

    private TileList bgTiles, fgTiles;

    WorldButtonClickListener(TutEngine tutEngine, String path, String savesPath, int index) {
        this.path = path;
        this.savesPath = savesPath;
        this.index = index;
        this.engine = tutEngine;

        long range = 1000000000L;
        seed = generateSeed(-range, range);
        sizeMod = NumberUtils.getRandomFloat(0.0f, 50.0f);

        int sizeMin = Math.min((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeMax = Math.max((int) -(sizeMod * 2), (int) (sizeMod * 2));
        int sizeModExtra;
        if (sizeMin < sizeMax)
            sizeModExtra = NumberUtils.getRandomInt(sizeMin, sizeMax);
        else
            sizeModExtra = NumberUtils.getRandomInt(sizeMax, sizeMin);
        int minWorldSize = 400;
        worldSize = minWorldSize + sizeModExtra;

        SavedGamesJSONParser gamesJSONParser = new SavedGamesJSONParser(tutEngine);
        try {
            gamesJSONParser.loadJson();
        } catch (IOException | ParseException e) {
            TutLauncher.LOGGER.error(e);
        }
    }

    @Override
    public void onClick() {
        String worldName = getWorldname();
        if (!isSaved) {
            generateWorld(worldName);
        } else {
            worldName = SavedGamesJSONParser.GAMES.get(index).split(":")[1];
            path = savesPath + worldName;
        }

        String username;
        if (ArgUtils.hasArgument("-username")) {
            username = ArgUtils.getArgument("-username");
        } else {
            if (!isSaved) {
                username = UtilsIdk.getUsername();
            } else {
                username = "HAKUNA MATATA!";
            }
        }
        engine.setUsername(username);
        TutLauncher.LOGGER.info("Set username: " + engine.getUsername());

        TutLauncher.LOGGER.info("Loading game [" + path + "]!");

        DiscordHandler.INSTANCE.updatePresence("In Game - " + engine.getUsername(), "World: " + worldName, true);
        GameScreen game = new GameScreen(engine, path, worldName);
        game.saveWorld(username);
        ScreenManager.setCurrentScreen(game);
    }

    private void generateWorld(String worldName) {
        /*
         * Tiles, entities, etc..
         */
        TutLauncher.LOGGER.info("Generating world...");
        engine.getPlayer().reset();

        double blendSize = 25.0d + sizeMod;
        WorldGenerator generator = new WorldGenerator(engine, seed, worldSize);
        generator.setBlendSize(blendSize);
        generator.generate();

        while (generator.isGenerating()) {
            if (generator.isGenerated()) {
                bgTiles = generator.getBgTiles();
                fgTiles = generator.getFgTiles();
            }
        }

        /*
         * Set world & username
         */
        World world = new World(engine, worldName, worldSize, worldSize, getPlayerSpawn(), bgTiles, fgTiles);
        ScreenManager.setCurrentScreen(new GameScreen(engine, "./data/saves/Test_World", worldName, world));

        if (ArgUtils.hasArgument("-username"))
            engine.setUsername(ArgUtils.getArgument("-username"));
        else
            engine.setUsername("TEST"); // TODO: Add text box to enter username on startup

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        String[] vals = SavedGamesJSONParser.GAMES.get(index).split(":");
        isSaved = Boolean.valueOf(vals[0]);
    }

    public String getPath() {
        return path;
    }

    public void setText(WidgetButtonWorld button) {
        boolean isSaved = Boolean.valueOf(SavedGamesJSONParser.GAMES.get(index).split(":")[0]);
        if (isSaved)
            button.setText(SavedGamesJSONParser.GAMES.get(index).split(":")[1].replace("_", " "));
    }

    private long generateSeed(long min, long max) {
        long s1 = randomLong(min, max);
        long s2 = randomLong(min, max);

        while (s2 <= s1)
            s2 = randomLong(min, max);

        return randomLong(s1, s2);
    }

    private long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(max - min + 1) + min;
    }

    private Vector2D getPlayerSpawn() {
        int x = NumberUtils.getRandomInt(400 - 1);
        int y = NumberUtils.getRandomInt(400 - 1);
        Tile tile = fgTiles.getTileAtPos(x, y);

        for (Entity entity : engine.getEntityManager().getEntities()) {
            if (!(entity instanceof PlayerEntity)) {
                int nx = x * Tile.TILE_SIZE;
                int ny = y * Tile.TILE_SIZE;
                if (engine.getEntityManager().isEntityInView(new Vector2D(nx, ny), entity.getPosition(), 2))
                    return getPlayerSpawn();
            }
        }

        if (tile.isSolid() || tile.isUnbreakable())
            return getPlayerSpawn();
        else {
            if (tile instanceof SandTile || tile instanceof GrassTile || tile instanceof DirtTile || tile instanceof AirTile)
                return new Vector2D(x, y);
            else
                return getPlayerSpawn();
        }
    }


    public String getText() {
        boolean isSaved = Boolean.valueOf(SavedGamesJSONParser.GAMES.get(index).split(":")[0]);
        if (isSaved)
            return SavedGamesJSONParser.GAMES.get(index).split(":")[1].replace("_", " ");
        return "";
    }

    public boolean isSaved() {
        return isSaved;
    }

    private String getWorldname() {
        String defaultName = "New World";
        String username;
        int nameLength = 16;
        defaultName += "_" + NumberUtils.getRandomInt(1000);
        try {
            username = JOptionPane.showInputDialog("Please enter a world name\nMust be max " + nameLength + " characters", defaultName);
            if (username.length() > nameLength || username.equalsIgnoreCase(""))
                username = defaultName;
        } catch (NullPointerException e) {
            username = defaultName;
        }
        return username.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]+", "");
    }
}
