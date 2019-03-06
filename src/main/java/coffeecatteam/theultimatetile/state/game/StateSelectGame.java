package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.button.world.UIButtonWorld;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
import org.newdawn.slick.Graphics;

public class StateSelectGame extends StateAbstractMenu {

    public StateSelectGame(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        int btnHeight = 64;
        int y = tutEngine.getHeight() / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++)
            uiManager.addObject(new UIButtonWorld(tutEngine, y + yOff * i, i));
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        Assets.TITLE.draw(tutEngine.getWidth() / 2f - w / 2f, 20, w, h);
    }
}
