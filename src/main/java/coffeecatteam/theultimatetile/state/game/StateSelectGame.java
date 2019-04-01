package coffeecatteam.theultimatetile.state.game;

import coffeecatteam.theultimatetile.TutEngine;
import coffeecatteam.theultimatetile.TutLauncher;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.button.world.UIButtonWorld;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.state.StateAbstractMenu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class StateSelectGame extends StateAbstractMenu {

    public StateSelectGame(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        int btnHeight = 64;
        int y = TutLauncher.HEIGHT / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++)
            uiManager.addObject(new UIButtonWorld(tutEngine, y + yOff * i, i));
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        int w = 80 * 6;
        int h = 48 * 6;
        Assets.TITLE_BIG.draw(TutLauncher.WIDTH / 2f - w / 2f, 20, w, h);
    }
}
