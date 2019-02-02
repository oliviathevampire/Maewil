package coffeecatteam.theultimatetile.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.Vector2D;
import coffeecatteam.theultimatetile.Engine;
import coffeecatteam.theultimatetile.gfx.Text;
import coffeecatteam.theultimatetile.gfx.assets.Assets;
import coffeecatteam.theultimatetile.gfx.ui.ClickListener;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.net.URI;

/**
 * @author CoffeeCatRailway
 * Created: 3/12/2018
 */
public class UIHyperlinkCopyright extends UIHyperlink {

    private static String text = "Copyright (C) CoffeeCatTeam 2019";
    private static Font font = Assets.FONTS.get("20");

    public UIHyperlinkCopyright(Vector2D position) {
        super(position, Text.getHeight(text, font), text, font, new ClickListener() {
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
            public void update(GameContainer container, int delta) {
            }
        });
    }
}
