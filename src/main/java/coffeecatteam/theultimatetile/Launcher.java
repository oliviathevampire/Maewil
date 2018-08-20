package coffeecatteam.theultimatetile;

import coffeecatteam.theultimatetile.utils.Logger;

public class Launcher {

    public static void main(String[] args) {
        int width = 640;
        int height = 640;
        Logger.init();
        Game game = new Game("The Ultimate Tile", width, height);
        game.start();
    }
}
