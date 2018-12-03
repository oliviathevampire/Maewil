package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.coffeecatutils.Logger;
import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.button.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlink;
import coffeecatteam.theultimatetile.gfx.ui.hyperlink.UIHyperlinkCopyright;
import coffeecatteam.theultimatetile.utils.DiscordHandler;

import java.awt.*;
import java.net.URI;

public class StateMenu extends State {

    public StateMenu(GameEngine gameEngineIn) {
        super(gameEngineIn);
        init();

        int yOff = 20;

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff, "Select Game", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(((GameEngine) engine).stateSelectGame);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting A Game");
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff + 80, "Options", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(engine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIButton(gameEngineIn, true, engine.getHeight() / 2 - yOff + 160, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                engine.setRunning(false);
            }

            @Override
            public void tick() {
            }
        }));

        uiManager.addObject(new UIHyperlinkCopyright(new Vector2D(5, engine.getHeight() - 10), true));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, engine.getWidth(), engine.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, engine.getWidth() / 2 - w / 2, 20, w, h, null);
    }
}
