package io.github.vampirestudios.tdg.screen.options;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.gfx.ui.button.WidgetButton;
import io.github.vampirestudios.tdg.gfx.ui.hyperlink.WidgetHyperlinkCopyright;
import io.github.vampirestudios.tdg.objs.tiles.Tile;
import io.github.vampirestudios.tdg.objs.tiles.Tiles;
import io.github.vampirestudios.tdg.screen.Screen;
import io.github.vampirestudios.tdg.screen.ScreenManager;
import io.github.vampirestudios.tdg.start.MaewilEngine;
import io.github.vampirestudios.tdg.start.MaewilLauncher;
import io.github.vampirestudios.tdg.utils.DiscordHandler;
import org.mini2Dx.core.graphics.Graphics;

import java.io.IOException;

public abstract class AbstractOptions extends Screen {

    public AbstractOptions(MaewilEngine maewilEngineIn, boolean leftButton) {
        super(maewilEngineIn, OptionsScreen.BG, new Tile[]{Tiles.STONE});

        Vector2D pos = leftButton ? new Vector2D(MaewilLauncher.WIDTH - 120, 20) : new Vector2D(15, 20);
        uiManager.addObject(new WidgetButton(maewilEngineIn, pos, "Back", new ClickListener() {
            @Override
            public void onClick() {
                ScreenManager.setCurrentScreen(maewilEngine.stateOptions);
                try {
                    OptionsScreen.OPTIONS.save();
                } catch (IOException e) {
                    logger.error(e);
                }

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void update(org.mini2Dx.core.game.GameContainer container, float delta) {
            }
        }));

        uiManager.addObject(new WidgetHyperlinkCopyright(new Vector2D(5, MaewilLauncher.HEIGHT - 20)));
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
