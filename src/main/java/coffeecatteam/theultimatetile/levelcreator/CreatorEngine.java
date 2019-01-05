package coffeecatteam.theultimatetile.levelcreator;

import coffeecatteam.theultimatetile.Engine;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CreatorEngine extends Engine {

    private static CreatorEngine creatorEngine;

    private MainMenu mainMenu;

    public CreatorEngine(String[] args, String title, int width, int height) {
        super(args, title, width, height);
        initOptionsUI = false;
        playBGMusic = false;
        creatorEngine = this;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        super.init(container);
        mainMenu = new MainMenu(this);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        super.update(container, delta);
        mainMenu.update(container, delta);
    }

    @Override
    public void rendera(GameContainer container, Graphics g) throws SlickException {
        mainMenu.render(g);
    }

    public static CreatorEngine getCreatorEngine() {
        return creatorEngine;
    }
}
