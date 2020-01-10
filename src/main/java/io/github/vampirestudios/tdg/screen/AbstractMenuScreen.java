package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.TutEngine;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class AbstractMenuScreen extends Screen {

    public AbstractMenuScreen(TutEngine tutEngine, Tile[] centre) {
        this(tutEngine, centre, true);
    }

    public AbstractMenuScreen(TutEngine tutEngine, Tile[] centre, boolean initUI) {
        super(tutEngine, centre);

        if (initUI) {
            uiManager.addObject(new WidgetButton(tutEngine, new Vector2D(15, TutLauncher.HEIGHT - 95), "Main Menu", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    ScreenManager.setCurrentScreen(tutEngine.stateMenu);

//                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, TutLauncher.HEIGHT - 20)));
        }
    }

    public void initBack() {
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
