package io.github.vampirestudios.tdg.gfx.ui.hyperlink;

import coffeecatteam.coffeecatutils.position.Vector2D;
import io.github.vampirestudios.tdg.gfx.Text;
import io.github.vampirestudios.tdg.gfx.assets.Assets;
import io.github.vampirestudios.tdg.gfx.ui.ClickListener;
import io.github.vampirestudios.tdg.start.TutLauncher;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.net.URI;

/**
 * @author CoffeeCatRailway
 * Created: 3/12/2018
 */
public class WidgetHyperlinkCopyright extends WidgetHyperlink {

    private static String text = "Copyright (C) Vampire Studios 2020";
    private static Font font = Assets.FONTS.get("20");

    public WidgetHyperlinkCopyright(Vector2D position) {
        super(position, Text.getHeight(text, font), text, font, new ClickListener() {
            @Override
            public void onClick() {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI link = new URI("https://github.com/oliviathevampire/TheUltimateTile/blob/master/LICENSE.md");
                    desktop.browse(link);
                } catch (Exception e) {
                    TutLauncher.LOGGER.error(e);
                }
            }

            @Override
            public void update(GameContainer container, StateBasedGame game, int delta) {
            }
        });
    }
}
