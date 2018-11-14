package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.Engine;

import java.awt.*;

public class CreatorEngine extends Engine {

    private static CreatorEngine creatorEngine;

    private MainMenu mainMenu;


    public CreatorEngine(String[] args, String title, int width, int height) {
        super(args, title, width, height);
        initOptionsUI = false;
        creatorEngine = this;
    }

    @Override
    public void init() {
        super.init();
        mainMenu = new MainMenu(this);
    }

    @Override
    public void tick() {
        super.tick();
        mainMenu.tick();
    }

    @Override
    public void render(Graphics g) {
        mainMenu.render(g);
    }

    public static CreatorEngine getCreatorEngine() {
        return creatorEngine;
    }
}
