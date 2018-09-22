package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import coffeecatteam.theultimatetile.state.options.Keybind;
import coffeecatteam.theultimatetile.state.options.OptionsJsonParser;
import coffeecatteam.theultimatetile.utils.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class StateOptions extends State {

    public static OptionsJsonParser options;

    public StateOptions(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        init();

        options = new OptionsJsonParser("./options.json", theUltimateTile);
        try {
            options.loadOptions();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        int diBtnWidth = 6 * 64;
        int diBtnHeight = 64;
        uiManager.addObject(new UIButton(15, 15, diBtnWidth, diBtnHeight, "Debug Info", new ClickListener() {
            @Override
            public void onClick() {
                options.setDEBUG_MODE(!options.DEBUG_MODE());
                Logger.print("Degbug info mode " + (options.DEBUG_MODE() ? "enabled" : "disabled"));
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
                try {
                    StateOptions.options.loadOptions();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void tick() {
            }
        }));

        int fpsBtnWidth = 6 * 64;
        int fpsBtnHeight = 64;
        uiManager.addObject(new UIButton(15, 173, fpsBtnWidth, fpsBtnHeight, "FPS counter", new ClickListener() {
            @Override
            public void onClick() {
                options.setFPS_COUNTER(!options.FPS_COUNTER());
                Logger.print("FPS counter " + (options.FPS_COUNTER() ? "enabled" : "disabled"));
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
                StateOptions.options.saveOptions();
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

        g.drawImage(options.DEBUG_MODE() ? Assets.ICON_ON : Assets.ICON_OFF, 15 + 6 * 64, 15, 64, 64, null);

        Text.drawString(g, "EXPERIMENTAL!", theUltimateTile.getWidth() / 2, theUltimateTile.getHeight() / 2, true, true, Color.red, Assets.FONT_80);
    }
}
