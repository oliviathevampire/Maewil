package io.github.vampirestudios.tdg.screen.game;

import coffeecatteam.coffeecatutils.NumberUtils;
import io.github.vampirestudios.tdg.gfx.ui.button.world.WidgetButtonWorld;
import io.github.vampirestudios.tdg.jsonparsers.SavedGamesJSONParser;
import io.github.vampirestudios.tdg.screen.AbstractMenuScreen;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SelectGameScreen extends AbstractMenuScreen {

    public SelectGameScreen(TutEngine tutEngine) {
        super(tutEngine, CENTRE_GRASS);

        int btnHeight = 64;
        int y = TutLauncher.HEIGHT / 2 - btnHeight / 2 + 25;
        float yOff = 25 * 3f;

        for (int i = 0; i < SavedGamesJSONParser.SAVE_CAPACITY; i++) {
            String worldName = "New_World_" + NumberUtils.getRandomInt(1000);
            uiManager.addObject(new WidgetButtonWorld(tutEngine, y + yOff * i, i, worldName));
        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        super.render(container, game, g);

        int w = 80 * 6;
        int h = 48 * 6;
//        Assets.GUI_TITLE_BIG.draw(TutLauncher.WIDTH / 2f - w / 2f, 20, w, h);
    }
}
