package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.state.State;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
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

public class StateSelectGame extends StateAbstractMenu {

    public StateSelectGame(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        int btnWidth = 5 * 64;
        int btnHeight = 64;
        int x = theUltimateTile.getWidth() / 2 - btnWidth / 2;
        int y = theUltimateTile.getHeight() / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;
        uiManager.addObject(new WorldButton(x, y, 1));
        uiManager.addObject(new WorldButton(x, y + yOff, 2));
        uiManager.addObject(new WorldButton(x, y + yOff * 2, 3));
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
            hasTooltip = true;
        }

        @Override
        public void tick() {
            super.tick();
            ((ClickListenerWorld) this.listener).setText(this);
        }

        @Override
        public UIButton setTooltip(List<String> tooltip) {
            tooltip.add("Current world: " + ((ClickListenerWorld) this.listener).getText());
            return this;
        }
    }

    private class ClickListenerWorld implements ClickListener {

        private String path, savesPath, worldName;
        private int index;

        private SavedGamesJSONParser gamesJSONParser;

        public ClickListenerWorld(String path, String savesPath, int index) {
            this.path = path;
            this.savesPath = savesPath;
            this.index = index - 1;

            gamesJSONParser = new SavedGamesJSONParser(theUltimateTile);
            try {
                gamesJSONParser.load();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick() {
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
    public void render(Graphics g) {
        super.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, w / 6, 20, w, h, null);
    }
}
