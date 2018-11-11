package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.GameEngine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.utils.DiscordHandler;

import java.awt.*;
import java.net.URI;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(GameEngine gameEngineIn) {
        super(gameEngineIn);
        int exBtnWidth = 5 * 64;
        int exBtnHeight = 64;
        uiManager.addObject(new UIButton(15, gameEngine.getHeight() - exBtnHeight - 35, exBtnWidth, exBtnHeight, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                initBack();
                State.setState(gameEngine.stateMenu);

                DiscordHandler.INSTANCE.updatePresence("Main Menu");
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

    public void initBack() {
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, gameEngine.getWidth(), gameEngine.getHeight(), null);
        uiManager.render(g);
    }
}
