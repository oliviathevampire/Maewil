package coffeecatteam.theultimatetile.game.state.game;

import coffeecatteam.coffeecatutils.ArgUtils;
import coffeecatteam.coffeecatutils.NumberUtils;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.game.state.State;
import coffeecatteam.theultimatetile.game.state.StateAbstractMenu;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.jsonparsers.world.WorldJsonLoader;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import coffeecatteam.theultimatetile.utils.Utils;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StateSelectGame extends StateAbstractMenu {

    public StateSelectGame(Engine engine) {
        super(engine);
        init();

        int btnHeight = 64;
        int y = engine.getHeight() / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++) {
            uiManager.addObject(new WorldButton(y + yOff * i, i));
        }
    }

    private static String getWorldname(String defaultName) {
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

    private class WorldButton extends UIButton {

        private String path;
        private String savesPath = "./saves/";

        public WorldButton(float y, int index) {
            this(y, index, "");
        }

        public WorldButton(float y, int index, String path) {
            super(State.engine, true, (int) y, "New Game", null);
            this.path = savesPath + path;
            this.listener = new ClickListenerWorld(this.path, this.savesPath, index);
        }

        @Override
        public void tick() {
            super.tick();
            ClickListenerWorld listener = (ClickListenerWorld) this.listener;
            if (listener.isSaved())
                this.setText(listener.getText());
        }
    }

    class ClickListenerWorld implements ClickListener {

        private String path, savesPath;
        private int index;
        private boolean isSaved = false;

        private SavedGamesJSONParser gamesJSONParser;

        public ClickListenerWorld(String path, String savesPath, int index) {
            this.path = path;
            this.savesPath = savesPath;
            this.index = index;

            gamesJSONParser = new SavedGamesJSONParser(engine);
            try {
                gamesJSONParser.load();
            } catch (IOException | ParseException e) {
                logger.print(e);
            }
        }

        @Override
        public void onClick() {
            String worldName;
            if (!isSaved) {
                worldName = getWorldname("New World");
                path = savesPath + worldName;
                new File(path).mkdir();

                String ogWorld = "/assets/worlds/starter/world_01";
                WorldJsonLoader.copyFiles(ogWorld, path);
                SavedGamesJSONParser.GAMES.set(index, "true:" + worldName);

                try {
                    gamesJSONParser.save();
                } catch (IOException e) {
                    logger.print(e);
                }
            } else {
                worldName = SavedGamesJSONParser.GAMES.get(index).split(":")[1];
                path = savesPath + worldName;
            }

            String username;
            if (ArgUtils.hasArgument(engine.getArgs(), "-username")) {
                username = ArgUtils.getArgument(engine.getArgs(), "-username");
            } else {
                if (!isSaved) {
                    username = Utils.getUsername();
                } else {
                    username = "HAKUNA MATATA!";
                }
            }
            GameEngine.getGameEngine().setUsername(username);
            logger.print("Set username: " + GameEngine.getGameEngine().getUsername());

            logger.print("Loading game [" + path + "]!");

            DiscordHandler.INSTANCE.updatePresence("In Game - " + GameEngine.getGameEngine().getUsername(),
                    "World: " + worldName, true);
            StateGame game = new StateGame(engine, path, worldName);
            game.saveWorld(username);
            State.setState(game);
        }

        @Override
        public void tick() {
            String[] vals = SavedGamesJSONParser.GAMES.get(index).split(":");
            isSaved = Boolean.valueOf(vals[0]);
        }

        public String getPath() {
            return path;
        }

        public void setText(WorldButton button) {
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
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, engine.getWidth() / 2 - w / 2, 20, w, h, null);
    }
}
