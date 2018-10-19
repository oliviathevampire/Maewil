package coffeecatteam.theultimatetile.state;

import coffeecatteam.theultimatetile.TheUltimateTile;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;
import coffeecatteam.theultimatetile.gfx.ui.UIButton;
import coffeecatteam.theultimatetile.gfx.ui.UIHyperlink;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.awt.*;
import java.net.URI;

public class StateAbstractMenu extends State {

    public StateAbstractMenu(TheUltimateTile theUltimateTileIn) {
        super(theUltimateTileIn);
        int exBtnWidth = 5 * 64;
        int exBtnHeight = 64;
        uiManager.addObject(new UIButton(15, theUltimateTile.getHeight() - exBtnHeight - 35, exBtnWidth, exBtnHeight, "Main Menu", new ClickListener() {
            @Override
            public void onClick() {
                initBack();
                State.setState(theUltimateTile.stateMenu);

                DiscordRichPresence rich = new DiscordRichPresence.Builder("")
                        .setDetails("Main Menu")
                        .setBigImage("ultimatebg", "TUT")
                        .build();
                DiscordRPC.discordUpdatePresence(rich);
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

    public void initBack() {
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.BACKGROUND, 0, 0, theUltimateTile.getWidth(), theUltimateTile.getHeight(), null);
        uiManager.render(g);
    }
}
