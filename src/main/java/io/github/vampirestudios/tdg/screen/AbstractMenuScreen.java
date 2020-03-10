package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class AbstractMenuScreen extends Screen {

    public AbstractMenuScreen(MaewilEngine maewilEngine, Tile[] centre) {
        this(maewilEngine, centre, true);
    }

    public AbstractMenuScreen(MaewilEngine maewilEngine, Tile[] centre, Tile[] border) {
        this(maewilEngine, centre, border, true);
    }

    public AbstractMenuScreen(MaewilEngine maewilEngine, Tile[] centre, boolean initUI) {
        this(maewilEngine, centre, TileBackgrounds.STONE_BORDER.getTiles(), initUI);
    }

    public AbstractMenuScreen(MaewilEngine maewilEngine, Tile[] centre, Tile[] border, boolean initUI) {
        super(maewilEngine, centre, border);

        if (initUI) {
            uiManager.addObject(new WidgetButton(maewilEngine, new Vector2D(15, 20), "Back", new ClickListener() {
                @Override
                public void onClick() {
                    initBack();
                    ScreenManager.setCurrentScreen(maewilEngine.stateMenu);

//                    DiscordHandler.INSTANCE.updatePresence("Main Menu");
                }

                @Override
                public void update(GameContainer container, StateBasedGame game, int delta) {
                }
            }));

            uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, MaewilLauncher.HEIGHT - 20)));
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
