package coffeecatteam.theultimatetile.screen.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.WidgetButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import coffeecatteam.theultimatetile.start.TutEngine;
import coffeecatteam.theultimatetile.start.TutLauncher;
import coffeecatteam.theultimatetile.screen.Screen;
import coffeecatteam.theultimatetile.screen.ScreenManager;
import coffeecatteam.theultimatetile.utils.DiscordHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;

public abstract class AbstractOptions extends Screen {

    public AbstractOptions(TutEngine tutEngineIn) {
        super(tutEngineIn, OptionsScreen.BG);

        uiManager.addObject(new WidgetButton(tutEngineIn, new Vector2D(15, TutLauncher.HEIGHT - 95), "Back", new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(tutEngine.stateOptions);
                try {
                    OptionsScreen.OPTIONS.saveJson();
                } catch (IOException e) {
                    logger.error(e);
                }

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        }));

        uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, TutLauncher.HEIGHT - 20)));
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) {
        uiManager.update(container, game, delta);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) {
        this.renderBG(container, game, g);
        uiManager.render(container, game, g);
    }
}
