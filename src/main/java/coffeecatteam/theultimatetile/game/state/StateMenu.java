package coffeecatteam.theultimatetile.game.state;

import coffeecatteam.theultimatetile.game.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
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

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(engine.getGraphics(), crText, font);
        int crHeight = Text.getHeight(engine.getGraphics(), font);
        int crx = 5;
        int cry = engine.getHeight() - 10;
        uiManager.addObject(new UIHyperlink(crx, cry, crWidth, crHeight, crText, true, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, engine.getWidth(), engine.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, engine.getWidth() / 2 - w / 2, 20, w, h, null);
    }
}
