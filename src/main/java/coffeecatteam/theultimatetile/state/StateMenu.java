package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;
import java.net.URI;

public class StateMenu extends State {

    public StateMenu(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        int yOff = 20;

        int spBtnWidth = 6 * 64;
        int spBtnHeight = 64;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - spBtnWidth / 2, theUltimateTile.getHeight() / 2 - spBtnHeight / 2 + spBtnHeight - 50 + yOff, spBtnWidth, spBtnHeight, "Single Player", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(new StateGame(theUltimateTile));
            }

            @Override
            public void tick() {
            }
        }));

        int opBtnWidth = 4 * 64;
        int opBtnHeight = 64;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - opBtnWidth / 2, theUltimateTile.getHeight() / 2 - opBtnHeight / 2 + opBtnHeight + 35 + yOff, opBtnWidth, opBtnHeight, "Options", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(theUltimateTile.stateOptions);
            }

            @Override
            public void tick() {
            }
        }));

        int quitBtnWidth = 192;
        int quitBtnHeight = 64;
        uiManager.addObject(new UIButton(theUltimateTile.getWidth() / 2 - quitBtnWidth / 2, theUltimateTile.getHeight() / 2 - quitBtnHeight / 2 + quitBtnHeight + 120 + yOff, quitBtnWidth, quitBtnHeight, "Quit", new ClickListener() {
            @Override
            public void onClick() {
                Logger.print("Exiting...");
                System.exit(0);
            }

            @Override
            public void tick() {
            }
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(theUltimateTile.getGraphics(), crText, font);
        int crHeight = Text.getHeight(theUltimateTile.getGraphics(), font);
        int crx = 5;
        int cry = theUltimateTile.getHeight() - 10;
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
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);

        int w = 80 * 6;
        int h = 48 * 6;
        g.drawImage(Assets.TITLE, w / 6, 20, w, h, null);
    }
}
