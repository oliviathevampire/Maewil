package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.state.options.Keybinds;
import coffeecatteam.theultimatetile.utils.Logger;

import java.awt.*;
import java.net.URI;

public class StateOptions extends State {

    public static boolean DEBUG = false;

    public StateOptions(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        int diBtnWidth = 6 * 64;
        int diBtnHeight = 64;
        uiManager.addObject(new UIButton(15, 15, diBtnWidth, diBtnHeight, "Debug Info", new ClickListener() {
            @Override
            public void onClick() {
                DEBUG = !DEBUG;
                Logger.print("Degbug info mode " + (DEBUG ? "enabled" : "disabled"));
            }

            @Override
            public void tick() {
            }
        }));

        int coBtnWidth = 6 * 64;
        int coBtnHeight = 64;
        uiManager.addObject(new UIButton(15, 94, coBtnWidth, coBtnHeight, "Controls", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(theUltimateTile.optionsControls);
                Keybinds.load();
            }

            @Override
            public void tick() {
            }
        }));

        int exBtnWidth = 5 * 64;
        int exBtnHeight = 64;
        uiManager.addObject(new UIButton(15, theUltimateTile.getHeight() - exBtnHeight - 35, exBtnWidth, exBtnHeight, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                State.setState(theUltimateTile.stateMenu);
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

        g.drawImage(DEBUG ? Assets.ICON_ON : Assets.ICON_OFF, 15 + 6 * 64, 15, 64, 64, null);

        Text.drawString(g, "EXPERIMENTAL!", theUltimateTile.getWidth() / 2, theUltimateTile.getHeight() / 2, true, true, Color.red, Assets.FONT_80);
    }
}
