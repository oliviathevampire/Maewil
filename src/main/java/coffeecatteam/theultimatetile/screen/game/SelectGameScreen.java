package coffeecatteam.theultimatetile.screen.game;

import coffeecatteam.theultimatetile.gfx.ui.button.world.WidgetButtonWorld;
import coffeecatteam.theultimatetile.jsonparsers.SavedGamesJSONParser;
import coffeecatteam.theultimatetile.screen.AbstractMenuScreen;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SelectGameScreen extends AbstractMenuScreen {

    public SelectGameScreen(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        int btnHeight = 64;
        int y = TutLauncher.HEIGHT / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++)
            uiManager.addObject(new WidgetButtonWorld(tutEngine, y + yOff * i, i));
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        int w = 80 * 6;
        int h = 48 * 6;
//        Assets.GUI_TITLE_BIG.draw(TutLauncher.WIDTH / 2f - w / 2f, 20, w, h);
    }
}
