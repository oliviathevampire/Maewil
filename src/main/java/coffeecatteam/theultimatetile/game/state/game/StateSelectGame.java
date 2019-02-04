package coffeecatteam.theultimatetile.game.state.game;

import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.game.state.StateAbstractMenu;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.button.world.UIButtonWorld;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import org.newdawn.slick.Graphics;

public class StateSelectGame extends StateAbstractMenu {

    public StateSelectGame(Engine engine) {
        super(engine, DEFAULT_CENTRE);

        int btnHeight = 64;
        int y = engine.getHeight() / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++)
            uiManager.addObject(new UIButtonWorld(engine, y + yOff * i, i));
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        Assets.TITLE.draw(engine.getWidth() / 2f - w / 2f, 20, w, h);
    }
}
