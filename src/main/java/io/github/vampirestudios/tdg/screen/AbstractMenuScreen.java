package io.github.vampirestudios.tdg.screen;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import org.mini2Dx.core.graphics.Graphics;

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
                public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
                }
            }));

            uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, MaewilLauncher.HEIGHT - 20)));
        }
    }

    public void initBack() {
    }

    @Override
    public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
        uiManager.update(container, delta);
    }

    @Override
    public void render(org.mini2Dx.core.game.GameContainer container, Graphics g) {
        this.renderBG(container, g);
        uiManager.render(container, g);
    }
}
