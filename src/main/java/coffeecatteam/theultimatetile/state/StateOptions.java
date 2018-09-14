package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
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
        uiManager.addObject(new UIButton(15, 15, diBtnWidth, diBtnHeight, "Debug Info", () -> {
            DEBUG = !DEBUG;
            Logger.print("Degbug info mode " + (DEBUG ? "enabled" : "disabled"));
        }));

        int coBtnWidth = 6 * 64;
        int coBtnHeight = 64;
        uiManager.addObject(new UIButton(15, 94, coBtnWidth, coBtnHeight, "Controls", () -> {
            Logger.print("CONTROLS");
        }));

        int exBtnWidth = 5 * 64;
        int exBtnHeight = 64;
        uiManager.addObject(new UIButton(15, theUltimateTile.getHeight() - exBtnHeight - 35, exBtnWidth, exBtnHeight, "Main Menu", () -> {
            State.setState(theUltimateTile.stateMenu);
        }));

        Font font = Assets.FONT_20;
        String crText = "Copyright (C) CoffeeCatTeam 2018";
        int crWidth = Text.getWidth(theUltimateTile.getGraphics(), crText, font);
        int crHeight = Text.getHeight(theUltimateTile.getGraphics(), font);
        int crx = 5;
        int cry = theUltimateTile.getHeight() - 10;
        uiManager.addObject(new UIHyperlink(crx, cry, crWidth, crHeight, crText, true, font, () -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                URI link = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                desktop.browse(link);
            } catch (Exception e) {
                e.printStackTrace();
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
    }
}
