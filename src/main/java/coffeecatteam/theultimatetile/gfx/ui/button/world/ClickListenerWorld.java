package coffeecatteam.theultimatetile.gfx.ui.button.world;

import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import org.json.simple.parser.ParseException;
import org.newdawn.slick.GameContainer;

import javax.swing.*;
import java.io.IOException;

/**
 * @author CoffeeCatRailway
 * Created: 10/01/2019
 */
public class ClickListenerWorld implements ClickListener {

    private TutEngine tutEngine;

    private String path, savesPath;
    private int index;
    private boolean isSaved = false;

    private SavedGamesJSONParser gamesJSONParser;

    public ClickListenerWorld(TutEngine tutEngine, String path, String savesPath, int index) {
        this.tutEngine = tutEngine;
        this.path = path;
        this.savesPath = savesPath;
        this.index = index;

        gamesJSONParser = new SavedGamesJSONParser(tutEngine);
        try {
            gamesJSONParser.load();
        } catch (IOException | ParseException e) {
            tutEngine.getLogger().print(e);
        }
    }

    @Override
    public void onClick() {
//        String worldName;
//        Tiles.init(engine);
//        if (!isSaved) {
//            worldName = getWorldname("New World");
//            path = savesPath + worldName;
//            new File(path).mkdir();
//
//            String ogWorld = "/assets/world";
//            WorldJsonLoader.copyFiles(engine, ogWorld, path);
//
//            int wSizeType = NumberUtils.getRandomInt(1, 3);
//            int wSize;
//            if (wSizeType == 1) wSize = 100;
//            else if (wSizeType == 2) wSize = 120;
//            else wSize = 200;
//            WorldGenerator worldGenerator = new WorldGenerator(wSize, wSize);
//            World w = new World(engine, worldName, wSize, wSize, wSize / 2, wSize / 2, worldGenerator.getBg_tiles(), worldGenerator.getFg_tiles());
//            WorldJsonSaver saver = new WorldJsonSaver(path, w, engine);
//            try {
//                saver.save("");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            SavedGamesJSONParser.GAMES.set(index, "true:" + worldName);
//
//            try {
//                gamesJSONParser.save();
//            } catch (IOException e) {
//                engine.getLogger().print(e);
//            }
//        } else {
//            worldName = SavedGamesJSONParser.GAMES.get(index).split(":")[1];
//            path = savesPath + worldName;
//        }
//
//        String username;
//        if (ArgUtils.hasArgument(engine.getArgs(), "-username")) {
//            username = ArgUtils.getArgument(engine.getArgs(), "-username");
//        } else {
//            if (!isSaved) {
//                username = Utils.getUsername();
//            } else {
//                username = "HAKUNA MATATA!";
//            }
//        }
//        tutEngine.getTutEngine().setUsername(username);
//        engine.getLogger().print("Set username: " + tutEngine.getTutEngine().getUsername());
//
//        engine.getLogger().print("Loading game [" + path + "]!");
//
//        DiscordHandler.INSTANCE.updatePresence("In Game - " + tutEngine.getTutEngine().getUsername(), "World: " + worldName, true);
//        StateGame game = new StateGame(engine, path, worldName);
//        game.saveWorld(username);
//        State.setState(game);
    }

    @Override
    public void update(GameContainer container, int delta) {
        String[] vals = SavedGamesJSONParser.GAMES.get(index).split(":");
        isSaved = Boolean.valueOf(vals[0]);
    }

    public String getPath() {
        return path;
    }

    public void setText(UIButtonWorld button) {
        boolean isSaved = Boolean.valueOf(SavedGamesJSONParser.GAMES.get(index).split(":")[0]);
        if (isSaved)
            button.setText(SavedGamesJSONParser.GAMES.get(index).split(":")[1].replace("_", " "));
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

    private String getWorldname(String defaultName) {
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
