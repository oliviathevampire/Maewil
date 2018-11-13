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

        int spBtnWidth = 5 * 64;
        int spBtnHeight = 64;
        uiManager.addObject(new UIButton(gameEngine.getWidth() / 2 - spBtnWidth / 2, gameEngine.getHeight() / 2 - spBtnHeight / 2 + spBtnHeight - 50 + yOff, spBtnWidth, spBtnHeight, "Select Game", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(gameEngine.stateSelectGame);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Selecting A Game");
            }

            @Override
            public void tick() {
            }
        }));

        int opBtnWidth = 4 * 64;
        int opBtnHeight = 64;
        uiManager.addObject(new UIButton(gameEngine.getWidth() / 2 - opBtnWidth / 2, gameEngine.getHeight() / 2 - opBtnHeight / 2 + opBtnHeight + 35 + yOff, opBtnWidth, opBtnHeight, "Options", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(gameEngine.stateOptions);

                DiscordHandler.INSTANCE.updatePresence("Main Menu", "Options");
            }

            @Override
            public void tick() {
            }
        }));

        int quitBtnWidth = 192;
        int quitBtnHeight = 64;
        uiManager.addObject(new UIButton(gameEngine.getWidth() / 2 - quitBtnWidth / 2, gameEngine.getHeight() / 2 - quitBtnHeight / 2 + quitBtnHeight + 120 + yOff, quitBtnWidth, quitBtnHeight, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                gameEngine.setRunning(false);
            }

            @Override
            public void tick() {
            }
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(gameEngine.getGraphics(), crText, font);
        int crHeight = Text.getHeight(gameEngine.getGraphics(), font);
        int crx = 5;
        int cry = gameEngine.getHeight() - 10;
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
        g.drawImage(Assets.BACKGROUND, 0, 0, gameEngine.getWidth(), gameEngine.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, gameEngine.getWidth() / 2 - w / 2, 20, w, h, null);
    }
}
