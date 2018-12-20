package coffeecatteam.theultimatetile.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Assets;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;

import java.awt.*;
import java.net.URI;

/**
 * @author CoffeeCatRailway
 * Created: 3/12/2018
 */
public class UIHyperlinkCopyright extends UIHyperlink {

    private static String text = "Copyright (C) CoffeeCatTeam 2018";
    private static Font font = Assets.FONTS.get("20");

    public UIHyperlinkCopyright(Vector2D position, boolean underlined) {
        super(position, Text.getHeight((Graphics2D) Engine.getEngine().getGraphics(), font), text, underlined, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/CoffeeCatRailway/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    Engine.getEngine().getLogger().print(e);
                }
            }

            @Override
            public void tick() {
            }
        });
    }
}
