package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.utils.Logger;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class StateSelectGame extends State {

    public StateSelectGame(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        int btnWidth = 5 * 64;
        int btnHeight = 64;
        uiManager.addObject(new WorldButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 - 32 * 3, 1));
        uiManager.addObject(new WorldButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2, 2));
        uiManager.addObject(new WorldButton(theUltimateTile.getWidth() / 2 - btnWidth / 2, theUltimateTile.getHeight() / 2 - btnHeight / 2 + 32 * 3, 3));
    }

    private static String getWorldname(String defaultName) {
        String username;
        int nameLength = 16;
        try {
            username = JOptionPane.showInputDialog("Please enter a world name\nMust be max " + nameLength + " characters", defaultName);
            if (username.length() > nameLength || username.equalsIgnoreCase(""))
                username = getWorldname(username);
        } catch (NullPointerException e) {
            username = getWorldname("");
        }
        return username.replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]+", "");
    }

    private class WorldButton extends UIButton {

        private String path;
        private String savesPath = "./saves/";

        public WorldButton(float x, float y, int index) {
            this(x, y, index, "");
        }

        public WorldButton(float x, float y, int index, String path) {
            super(x, y, 5 * 64, 64, "Game " + index, null);
            this.path = savesPath + path;
            this.listener = new ClickListenerWorld(this.path, this.savesPath, index);
        }

        @Override
        public UIButton setTooltip(List<String> tooltip) {
            if (!path.equals(savesPath))
                tooltip.add("Current world: " + path.replace(savesPath, ""));
            return super.setTooltip(tooltip);
        }
    }

    private class ClickListenerWorld implements ClickListener {

        private String path;
        private String savesPath;
        private int index;

        public ClickListenerWorld(String path, String savesPath, int index) {
            this.path = path;
            this.savesPath = savesPath;
            this.index = index - 1;
        }

        @Override
        public void onClick() {
            SavedGamesJSONParser gamesJSONParser = new SavedGamesJSONParser(theUltimateTile);
            try {
                gamesJSONParser.load();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            boolean isSaved = Boolean.valueOf(SavedGamesJSONParser.GAMES.get(index).split(":")[0]);

            String worldName;
            if (!isSaved) { // (new File(path + "/world.json").exists()) || !(new File(path + "/objects.json").exists())
                worldName = getWorldname("New World");
                path = savesPath + worldName;
                new File(path).mkdir();
                copy(StateSelectGame.class.getResourceAsStream("/assets/worlds/starter/world_01/world.json"), path + "/world.json");
                copy(StateSelectGame.class.getResourceAsStream("/assets/worlds/starter/world_01/objects.json"), path + "/objects.json");
                SavedGamesJSONParser.GAMES.set(index, "true:" + worldName);
                try {
                    gamesJSONParser.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                worldName = SavedGamesJSONParser.GAMES.get(index).split(":")[1];
                path = savesPath + worldName;
            }
            State.setState(new StateGame(theUltimateTile, path, worldName));
            Logger.print("Loading game [" + path + "]!");
        }

        @Override
        public void tick() {
        }
    }

    /**
     * Copy a file from source to destination.
     *
     * @param source      the source
     * @param destination the destination
     * @return True if succeeded , False if not
     */
    public static boolean copy(InputStream source, String destination) {
        boolean success = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            success = false;
        }

        return success;

    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);
    }
}
